package com.techlabs.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techlabs.app.dto.PersonDTO;
import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Person;
import com.techlabs.app.entity.Role;
import com.techlabs.app.exception.ResourceNotFoundException;
import com.techlabs.app.repository.AccountRepository;
import com.techlabs.app.repository.PersonRepository;
import com.techlabs.app.repository.RoleRepository;

@Service
public class PersonService {
	@Autowired
    private PersonRepository personRepository;
	
	@Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Person registerPerson(PersonDTO personDTO) {
        Role userRole = roleRepository.findByName("ROLE_USER");

        Person person = new Person();
        person.setUsername(personDTO.getUsername());
        person.setPassword(passwordEncoder.encode(personDTO.getPassword()));
        person.setEmail(personDTO.getEmail());
        person.setRole(userRole);

        return personRepository.save(person);
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found"));
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
    
    public void deposit(Long accountId, Double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }
    
    public void withdraw(Long accountId, Double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }
    
    public void transfer(Long fromAccountId, Long toAccountId, Double amount) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Source account not found"));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Destination account not found"));
        
        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }



}