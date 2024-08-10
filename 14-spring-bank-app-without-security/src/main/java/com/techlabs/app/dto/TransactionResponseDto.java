package com.techlabs.app.dto;

import java.time.LocalDateTime;

import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.TransactionType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionResponseDto {
	
	private long id;
	
	private AccountResponseDto senderAccount;
	
	private AccountResponseDto receiverAccount;

	private TransactionType transactionType;
	
	private LocalDateTime transactionDate;

	private double amount;

}
