package it.angelorizzoli.ufs15_l_es02.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(min = 2)
	private String title;

	@NotNull
	@Size(min = 2)
	private String author;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date yearPublish;

	@NotNull
	private Double price;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
	private Set<Published> published = new HashSet<>();

	public Book() {}
	public Book(@NotNull String title, @NotNull String author, @NotNull Date yearPublish, @NotNull Double price) {
		this.title = title;
		this.author = author;
		this.yearPublish = yearPublish;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getYearPublish() {
		return yearPublish;
	}
	public String getYearPublishFormat() {
		return new SimpleDateFormat("dd/MM/yyyy").format(yearPublish);
	}

	public void setYearPublish(Date yearPublish) {
		this.yearPublish = yearPublish;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Book{" +
				"title='" + title + '\'' +
				", author=" + author +
				", yearPublish=" + yearPublish +
				", price=" + price +
				'}';
	}
}
