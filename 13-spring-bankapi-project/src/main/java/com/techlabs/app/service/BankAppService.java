package com.techlabs.app.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import com.techlabs.app.dto.AccountResponseDto;
import com.techlabs.app.dto.CustomerRequestDto;
import com.techlabs.app.dto.CustomerResponseDto;
import com.techlabs.app.dto.ProfileRequestDto;
import com.techlabs.app.dto.TransactionResponseDto;
import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.util.PagedResponse;

import jakarta.mail.MessagingException;

public interface BankAppService {

	UserResponseDto createCustomer(CustomerRequestDto customerRequestDto, long userID);

	CustomerResponseDto createAccount(long customerID,int bankID);

	List<CustomerResponseDto> getAllCustomers();

	CustomerResponseDto getCustomerById(long customerid);

	PagedResponse<TransactionResponseDto> getAllTransactions(LocalDateTime fromDate, LocalDateTime toDate, int page, int size,
			String sortBy, String direction);

	TransactionResponseDto performTransaction(long senderAccountNumber, long receiverAccountNumber, double amount);

	PagedResponse<TransactionResponseDto> getPassbook(long accountNumber, LocalDateTime fromDate, LocalDateTime toDate, int page, int size, String sortBy, String direction) throws  IOException, MessagingException;

	String updateProfile(ProfileRequestDto profileRequestDto);

	AccountResponseDto depositAmount(long accountNumber, double amount);

	List<AccountResponseDto> getAccounts();

	String deleteCustomer(long customerID);

	String activateCustomer(long customerID);

	String deleteAccount(long accountNumber);

	String activateAccount(long accountNumber);

	AccountResponseDto viewBalance(long accountNumber);
	
}
