package com.techlabs.app.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.dto.AccountResponseDto;
import com.techlabs.app.dto.ProfileRequestDto;
import com.techlabs.app.dto.TransactionResponseDto;
import com.techlabs.app.service.BankAppService;
import com.techlabs.app.util.PagedResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	private BankAppService bankAppService;

	public CustomerController(BankAppService bankAppService) {
		super();
		this.bankAppService = bankAppService;
	}

	@PostMapping("/transaction")
	@Operation(summary = "Perform transaction")
	public ResponseEntity<TransactionResponseDto> performTransaction(
			@RequestParam(name = "senderAccountNumber") long senderAccountNumber,
			@RequestParam(name = "receiverAccountNumber") long receiverAccountNumber,
			@RequestParam(name = "amount") double amount) {
		return new ResponseEntity<TransactionResponseDto>(
				bankAppService.performTransaction(senderAccountNumber, receiverAccountNumber, amount), HttpStatus.OK);
	}

	@GetMapping("/passbook/{accountNumber}")
	@Operation(summary = "Get passbook")
	public ResponseEntity<PagedResponse<TransactionResponseDto>> getPassbook(
			@PathVariable(name = "accountNumber") long accountNumber,
			@RequestParam(name = "from", defaultValue = "#{T(java.time.LocalDateTime).now().minusDays(30).toString()}") String from,
		    @RequestParam(name = "to", defaultValue = "#{T(java.time.LocalDateTime).now().toString()}") String to,
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

	@PutMapping("/profile")
	@Operation(summary = "Update customer profile")
	public ResponseEntity<String> updateProfile(@RequestBody ProfileRequestDto profileRequestDto) {
		return new ResponseEntity<String>(bankAppService.updateProfile(profileRequestDto), HttpStatus.OK);
	}

	@PutMapping("/{accountNumber}/deposit")
	@Operation(summary = "Deposit in given accountNumber")
	public ResponseEntity<AccountResponseDto> deposit(@PathVariable(name = "accountNumber") long accountNumber,
			@RequestParam(name = "amount") double amount) {
		return new ResponseEntity<AccountResponseDto>(bankAppService.depositAmount(accountNumber, amount),
				HttpStatus.OK);
	}

	@GetMapping("/account")
	@Operation(summary = "Get all accounts of a customer")
	public ResponseEntity<List<AccountResponseDto>> getAllAccounts() {
		return new ResponseEntity<List<AccountResponseDto>>(bankAppService.getAccounts(), HttpStatus.OK);
	}

	@GetMapping("account/{accountNumber}/view-balance")
	@Operation(summary = "View balance of a customer by accountNumber")
	public ResponseEntity<AccountResponseDto> viewBalance(@PathVariable(name = "accountNumber") long accountNumber) {
		return new ResponseEntity<AccountResponseDto>(bankAppService.viewBalance(accountNumber), HttpStatus.OK);
	}
}
