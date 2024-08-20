package com.techlabs.app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techlabs.app.entity.ContactDetail;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
	
	public class ContactResponseDto {
	
	private long contactId;
	
	private String firstName;
	
	private String lastName;
	
	private boolean active;
	
	private List<ContactDetail> contactDetails;
	
	private UserResponseDto user;

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

	public UserResponseDto getUser() {
		return user;
	}

	public void setUser(UserResponseDto user) {
		this.user = user;
	}
	
	
}