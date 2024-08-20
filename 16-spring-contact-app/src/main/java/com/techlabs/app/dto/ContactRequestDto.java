package com.techlabs.app.dto;

import java.util.List;
import com.techlabs.app.entity.ContactDetail;
import com.techlabs.app.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class ContactRequestDto {
	private long contactId;
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	private boolean active=true;
	
	private List<ContactDetail> contactDetails;
	
	private User user;

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
