package it.angelorizzoli.ufs15_l_es02.Controller;

import it.angelorizzoli.ufs15_l_es02.Dao.BookDao;
import it.angelorizzoli.ufs15_l_es02.Dao.PublishedDao;
import it.angelorizzoli.ufs15_l_es02.Model.Book;
import it.angelorizzoli.ufs15_l_es02.Model.Published;
import it.angelorizzoli.ufs15_l_es02.Model.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/books")
public class BookController {

	@Autowired
	private PublishedDao publishedRepository;

	@Autowired
	private BookDao bookRepository;

	@PostMapping(value = "/addBook")
	public String addBook(@Valid Book book, BindingResult binding, Model model, HttpSession session) {
		User user = (User) session.getAttribute("loggedUser");

		if (user == null) return "redirect:/login";
		if (binding.hasErrors()) return "HomePage";

		bookRepository.save(book);
		Published publishedBook = new Published(user, book);
		publishedRepository.save(publishedBook);

		return "redirect:/home";
	}

	@GetMapping("/cart")
	public String showCart(Model model, HttpSession session) {
		User user = (User) session.getAttribute("loggedUser");
		if (user == null) return "redirect:/login";

		List<Published> publishedBooks = publishedRepository.findByUserId(user.getId());
		HashMap<Long, Book> books = new HashMap<>();

		for(Published el : publishedBooks)
			books.put(el.getId(), el.getBook());

		model.addAttribute("books", books);

		return "Cart";
	}

	@GetMapping(value = "")
	public String showBooks(Model model, HttpSession session) {
		User user = (User) session.getAttribute("loggedUser");

		if (user == null) return "redirect:/login";

		List<Book> books = bookRepository.getAllBy();
		model.addAttribute("books", books);

		return "Books";
	}
	@GetMapping(value = "/{book_id}")
	public String showBook(@PathVariable("book_id") long book_id, Model model, HttpSession session) {
		User user = (User) session.getAttribute("loggedUser");

		if (user == null) return "redirect:/login";

		Book book = bookRepository.findById(book_id);
		if (book == null) return null;

		model.addAttribute("book", book);
		return "Book";
	}
	@GetMapping(value = "/addToCart/{book_id}")
	public String addBookCart(@PathVariable long book_id, HttpSession session) {
		User user = (User) session.getAttribute("loggedUser");
		if (user == null) return "redirect:/login";

		Book book = bookRepository.findById(book_id);
		Published publishedBook = new Published(user, book);
		publishedRepository.save(publishedBook);

		return "redirect:/books";
	}
	@GetMapping(value = "/removeFromCart/{book_id}")
	public String removeBookCart(@PathVariable long book_id, HttpSession session) {
		User user = (User) session.getAttribute("loggedUser");
		if (user == null) return "redirect:/login";

		publishedRepository.deleteById(book_id);

		return "redirect:/books/cart";
	}
}
