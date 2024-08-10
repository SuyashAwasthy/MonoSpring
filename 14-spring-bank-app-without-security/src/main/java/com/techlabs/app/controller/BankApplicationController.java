package com.techlabs.app.controller;

import java.time.LocalDateTime;
import java.util.List;

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
import com.techlabs.app.dto.TransactionResponseDto;
import com.techlabs.app.service.BankService;
import com.techlabs.app.util.PagedResponse;

@RestController
@RequestMapping("/api/bank-application")
public class BankApplicationController {
	private BankService bankService;

	public BankApplicationController(BankService bankApplicationService) {
		this.bankService = bankApplicationService;
	}

	@GetMapping("/admin/transactions")
	public PagedResponse<TransactionResponseDto> getAllTransactions(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction,
			@RequestParam(name = "from", defaultValue = "#{T(java.time.LocalDateTime).now().minusDays(30).toString()}") String from,
			@RequestParam(name = "to", defaultValue = "#{T(java.time.LocalDateTime).now().toString()}") String to) {

		LocalDateTime fromDate = LocalDateTime.parse(from);
		LocalDateTime toDate = LocalDateTime.parse(to);

		return bankService.getAllTransactions(fromDate, toDate, page, size, sortBy, direction);
	}

	@PostMapping("/admin/customers/{userID}")
	public CustomerResponseDto createCustomer(@RequestBody CustomerRequestDto customerRequestDto,
			@PathVariable(name = "userID") long userID) {
		return bankService.createCustomer(customerRequestDto, userID);
	}

	@PostMapping("/admin/banks/{bankId}/customers/{customerId}/accounts")
	public CustomerResponseDto createAccount(@PathVariable(name = "customerId") long customerId,
			@PathVariable(name = "bankId") int bankId) {
		return bankService.addAccount(customerId, bankId);
	}

	@GetMapping("/admin/customers")
	public PagedResponse<CustomerResponseDto> getAllCustomers() {
		return bankService.getAllCustomers();
	}

	@GetMapping("/admin/customers/{customerId}")
	public CustomerResponseDto getCustomerById(@PathVariable(name = "customerId") long customerId) {
		return bankService.findCustomerByid(customerId);
	}

	@PostMapping("/customers/transactions")
	public TransactionResponseDto performTransaction(
			@RequestParam(name = "senderAccountNumber") long senderAccountNumber,
			@RequestParam(name = "receiverAccountNumber") long receiverAccountNumber,
			@RequestParam(name = "amount") double amount) {
		return bankService.doTransaction(senderAccountNumber, receiverAccountNumber, amount);
	}

	@GetMapping("/customers/passbook/{accountNumber}")
	public PagedResponse<TransactionResponseDto> getPassbook(@PathVariable(name = "accountNumber") long accountNumber,
			@RequestParam(name = "from", defaultValue = "#{T(java.time.LocalDateTime).now().minusDays(30).toString()}") String from,
			@RequestParam(name = "to", defaultValue = "#{T(java.time.LocalDateTime).now().toString()}") String to,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction) {
		LocalDateTime fromDate = LocalDateTime.parse(from);
		LocalDateTime toDate = LocalDateTime.parse(to);
		return bankService.getPassbook(accountNumber, fromDate, toDate, page, size, sortBy, direction);
	}

//	@PutMapping("/customers/profile")
//	public String updateProfile(@RequestBody ProfileRequestDto profileRequestDto) {
//		return bankService.updateProfile(profileRequestDto);
//	}

	@PutMapping("/customers/transactions/{accountNumber}/deposit")
	public AccountResponseDto deposit(@PathVariable(name = "accountNumber") long accountNumber,
			@RequestParam(name = "amount") double amount) {
		return bankService.depositAmount(accountNumber, amount);
	}
	@GetMapping("/customers/accounts")
	public List<AccountResponseDto> getAllAccounts() {
		return bankService.getAccounts();
	}
}
