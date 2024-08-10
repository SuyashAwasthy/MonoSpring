package com.techlabs.app.dto;

import java.time.LocalDateTime;

import com.techlabs.app.entity.TransactionType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class TransactionRequestDto {
	private long id;
	
	private AccountRequestDto senderAccount;
	
	private AccountRequestDto receiverAccount;
	
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	private LocalDateTime transactionDate;
	
	@NotNull
	private double amount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AccountRequestDto getSenderAccount() {
		return senderAccount;
	}

	public void setSenderAccount(AccountRequestDto senderAccount) {
		this.senderAccount = senderAccount;
	}

	public AccountRequestDto getReceiverAccount() {
		return receiverAccount;
	}

	public void setReceiverAccount(AccountRequestDto receiverAccount) {
		this.receiverAccount = receiverAccount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}