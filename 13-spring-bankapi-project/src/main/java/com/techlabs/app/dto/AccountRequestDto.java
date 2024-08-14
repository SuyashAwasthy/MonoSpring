package com.techlabs.app.dto;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Data
public class AccountRequestDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long accountNumber;

	private BankRequestDto bankRequestDto;

	private CustomerRequestDto customerRequestDto;

	private List<TransactionRequestDto> sentTransactions;
	
	private List<TransactionRequestDto> receiverTransactions;

	private double balance=1000.0;

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BankRequestDto getBankRequestDto() {
		return bankRequestDto;
	}

	public void setBankRequestDto(BankRequestDto bankRequestDto) {
		this.bankRequestDto = bankRequestDto;
	}

	public CustomerRequestDto getCustomerRequestDto() {
		return customerRequestDto;
	}

	public void setCustomerRequestDto(CustomerRequestDto customerRequestDto) {
		this.customerRequestDto = customerRequestDto;
	}

	public List<TransactionRequestDto> getSentTransactions() {
		return sentTransactions;
	}

	public void setSentTransactions(List<TransactionRequestDto> sentTransactions) {
		this.sentTransactions = sentTransactions;
	}

	public List<TransactionRequestDto> getReceiverTransactions() {
		return receiverTransactions;
	}

	public void setReceiverTransactions(List<TransactionRequestDto> receiverTransactions) {
		this.receiverTransactions = receiverTransactions;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
}