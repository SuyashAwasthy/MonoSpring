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
}