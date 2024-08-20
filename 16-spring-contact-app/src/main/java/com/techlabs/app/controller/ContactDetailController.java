package com.techlabs.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.dto.ContactDetailRequestDto;
import com.techlabs.app.dto.ContactDetailResponseDto;
import com.techlabs.app.entity.PagedResponse;
import com.techlabs.app.service.ContactApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/contacts/{contactId}/details")
public class ContactDetailController {
	@Autowired
	private ContactApplicationService contactApplicationService;
	
	@PostMapping
	public ResponseEntity<String> createContactDetail(@PathVariable(name = "contactId") long id,
			@RequestBody ContactDetailRequestDto contactDetailRequestDto) {
		return new ResponseEntity<String>(
				contactApplicationService.createAndUpdateContactDetail(id, contactDetailRequestDto),
				HttpStatus.CREATED);
	}

	@GetMapping
	public PagedResponse<ContactDetailResponseDto> getContactDetails(@PathVariable(name = "contactId") long id,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "sortBy", defaultValue = "contactDetailsId") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction) {
		return contactApplicationService.getAllContactDetails(id, page, size, sortBy, direction);
	}

	@PutMapping
	@Operation(summary = "Update contact detail for a specific contact.", description = "Updates an existing contact detail associated with the specified contact ID.", tags = {
			"Contact Details", "Update" })
	public String updateContactDetail(@PathVariable(name = "contactId") long id,
			@RequestBody ContactDetailRequestDto contactDetailRequestDto) {
		return contactApplicationService.createAndUpdateContactDetail(id, contactDetailRequestDto);
	}

	@GetMapping("{contactDetailId}")
	public ContactDetailResponseDto getContactDetail(@PathVariable long contactId, @PathVariable long contactDetailId) {
		return contactApplicationService.getContactDetailById(contactId, contactDetailId);
	}

	@DeleteMapping("{contactDetailId}")
	public String deleteContactDetail(@PathVariable long contactId, @PathVariable long contactDetailId) {
		return contactApplicationService.deleteContactDetail(contactId, contactDetailId);
	}
	
	
	
}