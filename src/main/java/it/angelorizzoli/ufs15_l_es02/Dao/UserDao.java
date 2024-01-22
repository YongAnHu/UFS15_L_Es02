package it.angelorizzoli.ufs15_l_es02.Dao;

import it.angelorizzoli.ufs15_l_es02.Model.Book;
import it.angelorizzoli.ufs15_l_es02.Model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDao extends CrudRepository<User, Long> {
	List<User> findByUsername(String username);
	User findById(long id);
	@Query("select s from User s where username= :username and password = :password")
	public User login(String username, String password);
}