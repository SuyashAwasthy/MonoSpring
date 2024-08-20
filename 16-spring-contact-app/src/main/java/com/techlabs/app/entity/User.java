package com.techlabs.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@NotNull
	private boolean admin;
	@NotNull
	private boolean active = true;
	@Column(nullable = false, unique = true)
	@Email
	private String email;
	@Column(nullable = false)
	private String password;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private List<Contact> contacts;

	public void deleteContact(Contact contact) {
		if(contacts.contains(contact)) {
			contact.setActive(false);
			contacts.add(contact);
		}
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	
	
}