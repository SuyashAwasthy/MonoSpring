package com.techlabs.app.entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long contactId;
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotNull
	private boolean active=true;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "contact")
	private List<ContactDetail> contactDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public void addContactDetail(ContactDetail contactDetail) {

		contactDetails.add(contactDetail);
	}

	public long getContactId() {
		return contactId;
	}

	public void setContactId(long contactId) {
		this.contactId = contactId;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<ContactDetail> getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(List<ContactDetail> contactDetails) {
		this.contactDetails = contactDetails;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}