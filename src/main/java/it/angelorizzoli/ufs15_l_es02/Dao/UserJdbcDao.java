package it.angelorizzoli.ufs15_l_es02.Dao;

import it.angelorizzoli.ufs15_l_es02.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserJdbcDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<User> login(String username, String password) {
		return jdbcTemplate.query(
				"select * from utenti join auth on u.id = a.id where username = ? and password = ?",
				new Object[]{username, password},
				(rs, rowNum) ->
						new User(
								rs.getLong("id"),
								rs.getString("name"),
								rs.getString("surname"),
								rs.getString("username"),
								rs.getString("password")
						)
		);
	}
}
