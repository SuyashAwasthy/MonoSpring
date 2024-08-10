package com.techlabs.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.model.Account;
import com.techlabs.app.model.Person;
import com.techlabs.app.service.BankingService;

@RestController
@RequestMapping("/api")
public class BankingController {
	@Autowired
	private BankingService bankingService;

	@PostMapping("/persons")
	public Person createPerson(@RequestBody String name) {
		return bankingService.createPerson(name);
	}

	@PostMapping("/accounts")
	public Account createAccount(@RequestParam Long personId) {
		return bankingService.createAccount(personId);
	}

	@PostMapping("/accounts/{accountId}/deposit")
	public void deposit(@PathVariable Long accountId, @RequestParam double amount) {
		bankingService.deposit(accountId, amount);
	}

	@PostMapping("/accounts/{accountId}/withdraw")
	public void withdraw(@PathVariable Long accountId, @RequestParam double amount) {
		bankingService.withdraw(accountId, amount);
	}

	@PostMapping("/accounts/transfer")
	public void transfer(@RequestParam Long fromAccountId, @RequestParam Long toAccountId,
			@RequestParam double amount) {
		bankingService.transfer(fromAccountId, toAccountId, amount);
	}

	@GetMapping("/persons/{personId}/balance")
	public double getTotalBalance(@PathVariable Long personId) {
		return bankingService.getTotalBalance(personId);
	}

	@GetMapping("/accounts/{accountId}")
	public Account getAccount(@PathVariable Long accountId) {
		return bankingService.getAccount(accountId);
	}
}
