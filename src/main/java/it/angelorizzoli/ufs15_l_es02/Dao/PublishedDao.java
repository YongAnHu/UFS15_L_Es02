package it.angelorizzoli.ufs15_l_es02.Dao;

import it.angelorizzoli.ufs15_l_es02.Model.Book;
import it.angelorizzoli.ufs15_l_es02.Model.Published;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PublishedDao extends CrudRepository<Published, Long> {
	Published findById(long id);
	List<Published> findByUserId(long id);
	void deleteById(long id);
}