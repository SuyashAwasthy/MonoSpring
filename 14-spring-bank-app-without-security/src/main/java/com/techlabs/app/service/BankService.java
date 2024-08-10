package com.techlabs.app.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.techlabs.app.dto.AccountRequestDto;
import com.techlabs.app.dto.AccountResponseDto;
import com.techlabs.app.dto.CustomerRequestDto;
import com.techlabs.app.dto.CustomerResponseDto;
import com.techlabs.app.dto.TransactionResponseDto;
import com.techlabs.app.util.PagedResponse;

public interface BankService {

	PagedResponse<CustomerResponseDto> getAllCustomers();

	CustomerResponseDto addCustomer(CustomerRequestDto customerrequestdto);

	String deleteCustomerById(long id);

	CustomerResponseDto findCustomerByid(long id);

	CustomerResponseDto addAccount(long cid, int bid);

	TransactionResponseDto doTransaction(long senderAccountno, long receiverAccountno, double amount);

	PagedResponse<TransactionResponseDto> viewAllTransaction(int page, int size, String sortBy, String direction);

	List<TransactionResponseDto> viewPassbook(long accountNo);

	List<TransactionResponseDto> searchByDate(LocalDateTime fromDate, LocalDateTime toDate);

	PagedResponse<TransactionResponseDto> getAllTransactions(LocalDateTime fromDate, LocalDateTime toDate, int page,
			int size, String sortBy, String direction);

	CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto, long userID);

	AccountResponseDto depositAmount(long accountNumber, double amount);

	List<AccountResponseDto> getAccounts();

	PagedResponse<TransactionResponseDto> getPassbook(long accountNumber, LocalDateTime fromDate, LocalDateTime toDate,
			int page, int size, String sortBy, String direction);

	PagedResponse<CustomerResponseDto> getAllCustomers(int page, int size, String sortBy, String direction);

}