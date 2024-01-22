package it.angelorizzoli.ufs15_l_es02.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.angelorizzoli.ufs15_l_es02.Dao.BookDao;
import it.angelorizzoli.ufs15_l_es02.Model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/API")
public class APIController {

	@Autowired
	private BookDao bookRepository;
	private RestTemplate request = new RestTemplate();
	private String URL = "https://www.googleapis.com/books/v1/volumes?q=\"\"&maxResults=40";

	@RequestMapping(value = "/getBooks")
	public List<Book> getBooks() {
		return bookRepository.getAllBy();
	}

	@RequestMapping(value = "/getBook/{title}")
	public List<Book> getBookByTitle(@PathVariable("title") String title) {
		return bookRepository.findByTitle(title);
	}

	@RequestMapping(value = "/sincronizza")
	public void syncro() throws JsonProcessingException {
		ResponseEntity<String> response = request.getForEntity(URL, String.class);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response.getBody());
		JsonNode items = root.path("items");

		items.forEach(jsonNode -> {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

			String title = jsonNode.path("volumeInfo").path("title").asText();
			String author = jsonNode.path("volumeInfo").path("authors").path(0).asText();

			Date yearPublish = null;
			try {
				String date = jsonNode.path("volumeInfo").path("publishedDate").asText().replace("-", "/");
				if (date.length() == 4) date += "/01/01";
				yearPublish = sdf.parse(date);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}

			Double price;
			if (!jsonNode.path("saleInfo").path("saleability").asText().equalsIgnoreCase("not_for_sale")) {
				price = jsonNode.path("saleInfo").path("listPrice").path("amount").asDouble();
			} else {
				price = -1.0;
			}

			Book book = new Book(title, author, yearPublish, price);
			bookRepository.save(book);
		});
	}
}
