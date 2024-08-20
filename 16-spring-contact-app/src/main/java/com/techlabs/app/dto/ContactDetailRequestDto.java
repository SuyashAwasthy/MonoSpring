package com.techlabs.app.dto;

import java.util.List;

import com.techlabs.app.entity.Contact;
import com.techlabs.app.entity.ContactType;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContactDetailRequestDto {
	private int contactDetailsId;
	private ContactType contactType;
	private Contact contact;
	public int getContactDetailsId() {
		return contactDetailsId;
	}
	public void setContactDetailsId(int contactDetailsId) {
		this.contactDetailsId = contactDetailsId;
	}
	public ContactType getContactType() {
		return contactType;
	}
	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	
}