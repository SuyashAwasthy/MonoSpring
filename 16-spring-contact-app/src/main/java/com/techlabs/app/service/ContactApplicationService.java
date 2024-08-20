package com.techlabs.app.service;

import com.techlabs.app.dto.ContactDetailRequestDto;
import com.techlabs.app.dto.ContactDetailResponseDto;
import com.techlabs.app.dto.ContactRequestDto;
import com.techlabs.app.dto.ContactResponseDto;
import com.techlabs.app.dto.UserRequestDto;
import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.entity.PagedResponse;

public interface ContactApplicationService {

	public PagedResponse<UserResponseDto> getAllUsers(int size, int page, String sortBy, String direction);

	public UserResponseDto createAndUpdateUser(UserRequestDto userRequestDto);

	public UserResponseDto getUserById(long id);

	public String deleteUser(long id);

	public ContactResponseDto createAndUpdateContact(ContactRequestDto contactRequestDto);

	public PagedResponse<ContactResponseDto> getAllContacts(int page, int size, String sortBy, String direction);

	public ContactResponseDto getContactById(long id);

	public String deleteContact(long id);

	public String createAndUpdateContactDetail(long id, ContactDetailRequestDto contactDetailRequestDto);

	public PagedResponse<ContactDetailResponseDto> getAllContactDetails(long id, int page, int size, String sortBy, String direction);

	public ContactDetailResponseDto getContactDetailById(long contactId, long contactDetailId);

	public String deleteContactDetail(long contactId, long contactDetailId);
}
