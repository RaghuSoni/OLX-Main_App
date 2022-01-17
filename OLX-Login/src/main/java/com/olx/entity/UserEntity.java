package com.olx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class UserEntity {

	@Id
	@GeneratedValue
	private int id;

	private String username;
	private String password;
	private String roles;
	private String firstname;
	private String lastname;

	@Column(name = "active")
	private boolean active;

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	

	public UserEntity(int id, String username, String password, String roles, String firstname, String lastname,
			boolean active, String phoneNumber) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public UserEntity(String username, String password, String roles, String firstname, String lastname, boolean active,
			String phoneNumber) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;

	}

	public UserEntity() {
		super();
	}

}
