package com.cct.beautysalon.models;


import com.cct.beautysalon.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
@Builder
//@ToString(exclude = {"password"})
@Data //getters and setters
@NoArgsConstructor //Without args constructor
@AllArgsConstructor //with all args constructor
@Entity
@Table(name="user")
public class User implements UserDetails {

	//construct
	public User(String firstName, String lastName, String login, String password, String email, Role role, String gender, String mobilePhone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.password = password;
		this.email = email;
		this.role = role;
		this.gender = gender;
		this.mobilePhone = mobilePhone;
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

	@Column(length = 100, nullable = false, unique = true)
	private String email;

	@Enumerated(EnumType.STRING) // save the string value
	private Role role; //TODO  private Set<Role> roles;

	private String gender;

	@NotNull
	private String mobilePhone;

	private String homePhone;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	/**
	 * Configure which field will be used as username
	 * @return
	 */
	@Override
	public String getUsername() {
		return login; //this is our username
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
