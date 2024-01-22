package it.angelorizzoli.ufs15_l_es02.Dao;

import it.angelorizzoli.ufs15_l_es02.Model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookDao extends CrudRepository<Book, Long> {
	List<Book> findByTitle(String title); //select * from Marca where nome = :nome
	Book findById(long id);
	List<Book> getAllBy();
}
