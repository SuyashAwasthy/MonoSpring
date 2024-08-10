package com.techlabs.app.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class Account {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private double balance = 1000.0;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @JsonBackReference
    private Person person;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	private String generateAccountNumber() {
        return "ACC-" + UUID.randomUUID().toString().substring(0, 8);
    }

	public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient balance");
        }
    }

    public void transfer(Account toAccount, double amount) {
        if (this.balance >= amount) {
            this.withdraw(amount);
            toAccount.deposit(amount);
        } else {
            throw new IllegalArgumentException("Insufficient balance");
        }
    }
}
