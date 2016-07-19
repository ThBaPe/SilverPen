package de.pentasys.SilverPen.model;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
@Table(name="USER")
public class User implements Serializable {

	   
	@Id
	@Column(unique=true, nullable=false, precision=80)
	private String email;
	@Column(unique=true, nullable=false, precision=20)
	private String username;
	@Column(nullable=false)
	private String password;
	private static final long serialVersionUID = 1L;

	public User() {
		super();
	}   
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}   
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}   
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
   
}
