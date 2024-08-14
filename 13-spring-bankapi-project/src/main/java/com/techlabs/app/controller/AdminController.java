package com.techlabs.app.controller;

import java.time.LocalDateTime;
import java.util.List;

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

import com.techlabs.app.dto.CustomerRequestDto;
import com.techlabs.app.dto.CustomerResponseDto;
import com.techlabs.app.dto.TransactionResponseDto;
import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.service.BankAppService;
import com.techlabs.app.util.PagedResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	private BankAppService bankAppService;

	public AdminController(BankAppService bankAppService) {
		super();
		this.bankAppService = bankAppService;
	}

	@GetMapping("/transactions")
	@Operation(summary = "View All Transactions")
	public ResponseEntity<PagedResponse<TransactionResponseDto>> getAllTransactions(
			@RequestParam(name = "page") int page, @RequestParam(name = "size") int size,
			@RequestParam(name = "sortBy") String sortBy, @RequestParam(name = "direction") String direction,
			@RequestParam(name = "from") String from, @RequestParam(name = "to") String to) {

		LocalDateTime fromDate = LocalDateTime.parse(from);
		LocalDateTime toDate = LocalDateTime.parse(to);

		return new ResponseEntity<PagedResponse<TransactionResponseDto>>(
				bankAppService.getAllTransactions(fromDate, toDate, page, size, sortBy, direction), HttpStatus.OK);
	}

	@PostMapping("/customer/{userID}")
	@Operation(summary = "Create customer by userID")
	public ResponseEntity<UserResponseDto> createCustomer(@RequestBody CustomerRequestDto customerRequestDto,
			@PathVariable(name = "userID") long userID) {
		return new ResponseEntity<UserResponseDto>(bankAppService.createCustomer(customerRequestDto, userID),
				HttpStatus.CREATED);
	}

	@PostMapping("/bank/{bankId}/customer/{customerId}/account")
	@Operation(summary = "Create account by customerID")
	public ResponseEntity<CustomerResponseDto> createAccount(@PathVariable(name = "customerId") long customerId,
			@PathVariable(name = "bankId") int bankID) {
		return new ResponseEntity<CustomerResponseDto>(bankAppService.createAccount(customerId, bankID),
				HttpStatus.CREATED);
	}

	@GetMapping("/customer")
	@Operation(summary = "View All Customers")
	public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
		return new ResponseEntity<List<CustomerResponseDto>>(bankAppService.getAllCustomers(), HttpStatus.OK);
	}

	@GetMapping("/customer/{customerId}")
	@Operation(summary = "Get customer by customerID")
	public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable(name = "customerId") long customerId) {
		return new ResponseEntity<CustomerResponseDto>(bankAppService.getCustomerById(customerId), HttpStatus.OK);
	}
	
	@DeleteMapping("/customer/{customerID}/inactive")
	@Operation(summary = "Delete a customer by customerID")
	public ResponseEntity<String> deleteCustomer(@PathVariable(name = "customerID") long customerID) {
		return new ResponseEntity<String>(bankAppService.deleteCustomer(customerID), HttpStatus.NO_CONTENT);
	}

	@PutMapping("/customer/{customerID}/active")
	@Operation(summary = "Activate a customer by customerID")
	public ResponseEntity<String> activateExistingCustomer(@PathVariable(name = "customerID") long customerID) {
		return new ResponseEntity<String>(bankAppService.activateCustomer(customerID), HttpStatus.OK);
	}

	@DeleteMapping("/customer/account/{accountNumber}/inactive")
	@Operation(summary = "Deactive an account by accountNumber")
	public ResponseEntity<String> deleteAccount(@PathVariable(name = "accountNumber") long accountNumber) {
		return new ResponseEntity<String>(bankAppService.deleteAccount(accountNumber), HttpStatus.NO_CONTENT);
	}

	@PutMapping("/customer/account/{accountNumber}/active")
	@Operation(summary = "Activate account")
	public ResponseEntity<String> activateExistingAccount(@PathVariable(name = "accountNumber") long accountNumber) {
		return new ResponseEntity<String>(bankAppService.activateAccount(accountNumber), HttpStatus.OK);
	}

}
