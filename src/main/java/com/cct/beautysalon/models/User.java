package com.cct.beautysalon.models;


import com.cct.beautysalon.enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString(exclude = {"password"})
@Data //getters and setters
@NoArgsConstructor //Without args constructor
@AllArgsConstructor //with all args constructor
@Entity
@Table(name="user")
public class User {


	public User(String firstName, String lastName, String login, String password, String email, UserType type) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.password = password;
		this.email = email;
		this.type = type;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name", length = 30, nullable = false)
	@NotNull(message = "First is required")
	private String firstName;

	@Column(name = "last_name", length = 30, nullable = false)
	@NotNull(message = "Last is required")
	private String lastName;

	@Column
	@NotNull(message = "Login is required")
	private String login;

	@Column
	@NotNull(message = "Password is required")
	private String password;

	@Column(length = 100, nullable = false)
	private String email;

	@Enumerated(EnumType.STRING) // save the string value
	private UserType type;

}
