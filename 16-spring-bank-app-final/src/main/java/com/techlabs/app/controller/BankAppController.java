package com.techlabs.app.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.techlabs.app.dto.AccountResponseDto;
import com.techlabs.app.dto.CustomerRequestDto;
import com.techlabs.app.dto.CustomerResponseDto;
import com.techlabs.app.dto.ProfileRequestDto;
import com.techlabs.app.dto.TransactionResponseDto;
import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.service.BankAppService;
import com.techlabs.app.util.PagedResponse;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/bank")
public class BankAppController {
	private BankAppService bankAppService;

	public BankAppController(BankAppService bankAppService) {
		this.bankAppService = bankAppService;
	}

	@GetMapping("/admin/transactions")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PagedResponse<TransactionResponseDto>> getAllTransactions(
			@RequestParam(name = "page") int page, @RequestParam(name = "size") int size,
			@RequestParam(name = "sortBy") String sortBy, @RequestParam(name = "direction") String direction,
			@RequestParam(name = "from") String from, @RequestParam(name = "to") String to) {

		LocalDateTime fromDate = LocalDateTime.parse(from);
		LocalDateTime toDate = LocalDateTime.parse(to);

		return new ResponseEntity<PagedResponse<TransactionResponseDto>>(
				bankAppService.getAllTransactions(fromDate, toDate, page, size, sortBy, direction), HttpStatus.OK);
	}

	@PostMapping("/admin/customer/{userID}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserResponseDto> createCustomer(@RequestBody CustomerRequestDto customerRequestDto,
			@PathVariable(name = "userID") long userID) {
		return new ResponseEntity<UserResponseDto>(bankAppService.createCustomer(customerRequestDto, userID),
				HttpStatus.CREATED);
	}

	@PostMapping("/admin/bank/{bankId}/customer/{customerId}/account")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CustomerResponseDto> createAccount(@PathVariable(name = "customerId") long customerId,
			@PathVariable(name = "bankID") int bankID) {
		return new ResponseEntity<CustomerResponseDto>(bankAppService.createAccount(customerId, bankID),
				HttpStatus.CREATED);
	}

	@GetMapping("/admin/customer")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
		return new ResponseEntity<List<CustomerResponseDto>>(bankAppService.getAllCustomers(), HttpStatus.OK);
	}

	@GetMapping("/admin/customer/{customerId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable(name = "customerId") long customerId) {
		return new ResponseEntity<CustomerResponseDto>(bankAppService.getCustomerById(customerId), HttpStatus.OK);
	}

	@PostMapping("/customer/transaction")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<TransactionResponseDto> performTransaction(
			@RequestParam(name = "senderAccountNumber") long senderAccountNumber,
			@RequestParam(name = "receiverAccountNumber") long receiverAccountNumber,
			@RequestParam(name = "amount") double amount) {
		return new ResponseEntity<TransactionResponseDto>(
				bankAppService.performTransaction(senderAccountNumber, receiverAccountNumber, amount), HttpStatus.OK);
	}

	@GetMapping("/customer/passbook/{accountNumber}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<PagedResponse<TransactionResponseDto>> getPassbook(
			@PathVariable(name = "accountNumber") long accountNumber,
			@RequestParam(name = "from") String from,
			@RequestParam(name = "to") String to,
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size,
			@RequestParam(name = "sortBy") String sortBy,
			@RequestParam(name = "direction") String direction)
			throws IOException, MessagingException {
		LocalDateTime fromDate = LocalDateTime.parse(from);
		LocalDateTime toDate = LocalDateTime.parse(to);
		return new ResponseEntity<PagedResponse<TransactionResponseDto>>(
				bankAppService.getPassbook(accountNumber, fromDate, toDate, page, size, sortBy, direction),
				HttpStatus.OK);
	}

	@PutMapping("/customer/profile")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> updateProfile(@RequestBody ProfileRequestDto profileRequestDto) {
		return new ResponseEntity<String>(bankAppService.updateProfile(profileRequestDto), HttpStatus.OK);
	}

	@PutMapping("/customer/{accountNumber}/deposit")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<AccountResponseDto> deposit(@PathVariable(name = "accountNumber") long accountNumber,
			@RequestParam(name = "amount") double amount) {
		return new ResponseEntity<AccountResponseDto>(bankAppService.depositAmount(accountNumber, amount),
				HttpStatus.OK);
	}

	@GetMapping("/customer/account")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<AccountResponseDto>> getAllAccounts() {
		return new ResponseEntity<List<AccountResponseDto>>(bankAppService.getAccounts(), HttpStatus.OK);
	}

	@DeleteMapping("admin/customer/{customerID}/inactive")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCustomer(@PathVariable(name = "customerID") long customerID) {
		return new ResponseEntity<String>(bankAppService.deleteCustomer(customerID), HttpStatus.NO_CONTENT);
	}

	@PutMapping("admin/customer/{customerID}/active")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> activateExistingCustomer(@PathVariable(name = "customerID") long customerID) {
		return new ResponseEntity<String>(bankAppService.activateCustomer(customerID), HttpStatus.OK);
	}

	@DeleteMapping("admin/customer/account/{accountNumber}/inactive")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteAccount(@PathVariable(name = "accountNumber") long accountNumber) {
		return new ResponseEntity<String>(bankAppService.deleteAccount(accountNumber), HttpStatus.NO_CONTENT);
	}

	@PutMapping("admin/customer/account/{accountNumber}/active")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> activateExistingAccount(@PathVariable(name = "accountNumber") long accountNumber) {
		return new ResponseEntity<String>(bankAppService.activateAccount(accountNumber), HttpStatus.OK);
	}

	@GetMapping("customer/account/{accountNumber}/view-balance")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<AccountResponseDto> viewBalance(@PathVariable(name = "accountNumber") long accountNumber) {
		return new ResponseEntity<AccountResponseDto>(bankAppService.viewBalance(accountNumber), HttpStatus.OK);
	}
}
