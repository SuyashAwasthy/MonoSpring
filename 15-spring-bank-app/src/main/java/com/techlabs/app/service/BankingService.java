package com.techlabs.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techlabs.app.model.Account;
import com.techlabs.app.model.Person;
import com.techlabs.app.repository.AccountRepository;
import com.techlabs.app.repository.PersonRepository;

@Service
public class BankingService {
	@Autowired
    private PersonRepository personRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Person createPerson(String name) {
        Person person = new Person();
        person.setName(name);
        return personRepository.save(person);
    }

    public Account createAccount(Long personId) {
        Optional<Person> personOpt = personRepository.findById(personId);
        if (personOpt.isPresent()) {
            Account account = new Account();
            account.setPerson(personOpt.get());
            return accountRepository.save(account);
        } else {
            throw new IllegalArgumentException("Person not found");
        }
    }

    public void deposit(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.deposit(amount);
        accountRepository.save(account);
    }

    public void withdraw(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.withdraw(amount);
        accountRepository.save(account);
    }

    public void transfer(Long fromAccountId, Long toAccountId, double amount) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        fromAccount.transfer(toAccount, amount);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    public double getTotalBalance(Long personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new IllegalArgumentException("Person not found"));
        return person.getTotalBalance();
    }

    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }
}