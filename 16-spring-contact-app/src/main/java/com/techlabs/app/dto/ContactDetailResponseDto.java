package com.techlabs.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techlabs.app.entity.ContactType;
import lombok.Data;

@Data
@JsonInclude(content = Include.NON_NULL)
public class ContactDetailResponseDto {
	private long contactDetailsId;
	private ContactType contactType;
	private ContactResponseDto contact;
	public long getContactDetailsId() {
		return contactDetailsId;
	}
	public void setContactDetailsId(long contactDetailsId) {
		this.contactDetailsId = contactDetailsId;
	}
	public ContactType getContactType() {
		return contactType;
	}
	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}
	public ContactResponseDto getContact() {
		return contact;
	}
	public void setContact(ContactResponseDto contact) {
		this.contact = contact;
	}
	
	
}
