package com.techlabs.app.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import com.techlabs.app.dto.ContactDetailResponseDto;
import com.techlabs.app.dto.ContactRequestDto;
import com.techlabs.app.dto.ContactResponseDto;
import com.techlabs.app.dto.ProfileRequestDto;
import com.techlabs.app.dto.UserRequestDto;
import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.util.PagedResponse;

import jakarta.mail.MessagingException;

public interface BankAppService {

	UserResponseDto createCustomer(ContactRequestDto customerRequestDto, long userID);

	ContactResponseDto createAccount(long customerID,int bankID);

	List<ContactResponseDto> getAllCustomers();

	ContactResponseDto getCustomerById(long customerid);

	PagedResponse<UserRequestDto> getAllTransactions(LocalDateTime fromDate, LocalDateTime toDate, int page, int size,
			String sortBy, String direction);

	UserRequestDto performTransaction(long senderAccountNumber, long receiverAccountNumber, double amount);

	PagedResponse<UserRequestDto> getPassbook(long accountNumber, LocalDateTime fromDate, LocalDateTime toDate, int page, int size, String sortBy, String direction) throws  IOException, MessagingException;

	String updateProfile(ProfileRequestDto profileRequestDto);

	ContactDetailResponseDto depositAmount(long accountNumber, double amount);

	List<ContactDetailResponseDto> getAccounts();

	String deleteCustomer(long customerID);

	String activateCustomer(long customerID);

	String deleteAccount(long accountNumber);

	String activateAccount(long accountNumber);

	ContactDetailResponseDto viewBalance(long accountNumber);
	
}
