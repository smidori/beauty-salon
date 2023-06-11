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


	public User(String name, String login, String password, String email, UserType type) {
		this.name = name;
		this.login = login;
		this.password = password;
		this.email = email;
		this.type = type;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    //@Column(length = 50, nullable = false)
	@NotNull(message = "Name is required")
	private String name;

	@Column
	private String login;

	@Column
	private String password;

	@Column(length = 100, nullable = false)
	private String email;

	@Enumerated(EnumType.STRING) // save the string value
	private UserType type;

}
