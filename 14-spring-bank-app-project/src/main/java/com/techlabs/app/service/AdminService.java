package com.techlabs.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Person;
import com.techlabs.app.exception.ResourceNotFoundException;
import com.techlabs.app.repository.AccountRepository;
import com.techlabs.app.repository.PersonRepository;

@Service
public class AdminService {
	@Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PersonRepository personRepository;

    public Account createAccountForPerson(Long personId) {
        Person person = personRepository.findById(personId).orElseThrow(() -> new ResourceNotFoundException("Person not found"));

        Account account = new Account();
        account.setBalance(1000.0); // Initial balance
        account.setPerson(person);

        return accountRepository.save(account);
    }
}
