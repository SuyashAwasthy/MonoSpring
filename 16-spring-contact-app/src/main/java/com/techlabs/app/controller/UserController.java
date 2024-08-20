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

import com.techlabs.app.dto.UserRequestDto;
import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.entity.PagedResponse;
import com.techlabs.app.service.ContactApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/users")
public class UserController {
	@Autowired
	private ContactApplicationService contactApplicationService;

	@PostMapping
	public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
		return new ResponseEntity<UserResponseDto>(contactApplicationService.createAndUpdateUser(userRequestDto),
				HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserRequestDto userRequestDto) {

		return new ResponseEntity<UserResponseDto>(contactApplicationService.createAndUpdateUser(userRequestDto),
				HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<PagedResponse<UserResponseDto>> getAllUsers(
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "sortBy", defaultValue = "userId") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction) {
		return new ResponseEntity<PagedResponse<UserResponseDto>>(
				contactApplicationService.getAllUsers(page, size, sortBy, direction), HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable(name = "id") long id) {
		return new ResponseEntity<UserResponseDto>(contactApplicationService.getUserById(id), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteUser(@PathVariable(name = "id") long id) {
		return new ResponseEntity<String>(contactApplicationService.deleteUser(id), HttpStatus.OK);
	}
}