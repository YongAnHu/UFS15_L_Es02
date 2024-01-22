package it.angelorizzoli.ufs15_l_es02.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(min=2, max=50)
	private String name;

	@NotNull
	@Size(min=2, max=50)
	private String surname;

	@NotNull
	@Size(min=2, max=50)
	private String username;

	@NotNull
	@Size(min=6, max=50)
	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Published> published = new HashSet<>();

	public User() {}
	public User(@NotNull String name, @NotNull String surname, @NotNull String username, @NotNull String password) {
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
	}

	public User(Long id, @NotNull String name, @NotNull String surname, @NotNull String username, @NotNull String password) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", surname='" + surname + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
