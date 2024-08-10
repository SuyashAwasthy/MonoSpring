package com.techlabs.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.techlabs.app.dto.AccountRequestDto;
import com.techlabs.app.dto.AccountResponseDto;
import com.techlabs.app.dto.BankRequestDto;
import com.techlabs.app.dto.CustomerRequestDto;
import com.techlabs.app.dto.CustomerResponseDto;
import com.techlabs.app.dto.TransactionResponseDto;
import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Bank;
import com.techlabs.app.entity.Customer;
import com.techlabs.app.entity.Transaction;
import com.techlabs.app.entity.TransactionType;
import com.techlabs.app.exception.CustomerNotFoundException;
import com.techlabs.app.exception.NoRecordFoundException;
import com.techlabs.app.repository.AccountRepository;
import com.techlabs.app.repository.BankRepository;
import com.techlabs.app.repository.CustomerRepository;
import com.techlabs.app.repository.TransactionRepository;
import com.techlabs.app.util.PagedResponse;

@Service
public class BankServiceImpl implements BankService{
	CustomerRepository customerRepository;
	
	AccountRepository accountRepository;
	BankRepository bankRepository;
	TransactionRepository transactionRepository;

	


	public BankServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository,
			BankRepository bankRepository, TransactionRepository transactionRepository) {
		super();
		this.customerRepository = customerRepository;
		this.accountRepository = accountRepository;
		this.bankRepository = bankRepository;
		this.transactionRepository = transactionRepository;
	}


	@Override
	public PagedResponse<CustomerResponseDto> getAllCustomers(int page, int size, String sortBy, String direction) {
		Sort sort = Sort.by(sortBy);
        if (direction.equalsIgnoreCase(Sort.Direction.DESC.name())) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        
        
		  List<CustomerResponseDto> customerDto = convertCustomertoCustomerDto(customerPage.getContent());
		  return new PagedResponse<>(customerDto, customerPage.getNumber(), customerPage.getSize(),
				  customerPage.getTotalElements(), customerPage.getTotalPages(), customerPage.isLast());
	}


	private List<CustomerResponseDto> convertCustomertoCustomerDto(List<Customer> all) {
		List<CustomerResponseDto> customers=new ArrayList<>();
		
		for(Customer i:all) {
			CustomerResponseDto customer=new CustomerResponseDto();
			customer.setCustomer_id(i.getCustomer_id());
			customer.setFirstName(i.getFirstName());
			customer.setLastName(i.getLastName());
			customer.setTotalBalance(i.getTotalBalance());		
			customer.setAccounts(convertAccounttoAccountResponseDto(i.getAccounts()));
			customers.add(customer);
		}
		return customers;
	}

  

	private List<AccountResponseDto> convertAccounttoAccountResponseDto(List<Account> accounts) {
	
	
		List<AccountResponseDto> accountResponseDto=new ArrayList<>();
		for(Account account:accounts) {
			
			accountResponseDto.add(	convertAccounttoAccountResponseDto(account));
			
		}
		return accountResponseDto;
	}


	private AccountResponseDto convertAccounttoAccountResponseDto(Account account) {
		
		AccountResponseDto accountResponseDto= new AccountResponseDto();
		accountResponseDto.setAccountNumber(account.getAccountNumber());
		accountResponseDto.setBalance(account.getBalance());
		return accountResponseDto;
	}


	@Override
	public CustomerResponseDto addCustomer(CustomerRequestDto customerrequestdto) {
		
		Customer customer=convertcustomerRequestDtoToCustomer(customerrequestdto);
		return convertCustomerTocustomerResponseDto(customerRepository.save(customer));
		
	}


	private CustomerResponseDto convertCustomerTocustomerResponseDto(Customer customer) {
		CustomerResponseDto customer1 =  new CustomerResponseDto();
		customer1.setCustomer_id(customer.getCustomer_id());
		customer1.setFirstName(customer.getFirstName());
		customer1.setLastName(customer.getLastName());
		customer1.setTotalBalance(customer.getTotalBalance());
		return customer1;
	}


	private Customer convertcustomerRequestDtoToCustomer(CustomerRequestDto customerrequestdto) {
		Customer customer =  new Customer();
		customer.setCustomer_id(customerrequestdto.getCustomer_id());
		customer.setFirstName(customerrequestdto.getFirstName());
		customer.setLastName(customerrequestdto.getLastName());
		customer.setTotalBalance(customerrequestdto.getTotalBalance());
		return customer;
	}


	@Override
	public String deleteCustomerById(long id) {
		
		
		Customer customer=customerRepository.findById(id).orElse(null);
		if(customer!=null) {
			customerRepository.deleteById(id);
			
		}
		else {
			throw new CustomerNotFoundException("customer not found with id: "+id);
		}
		return "customer deleted Successfully";
		
	}


	@Override
	public CustomerResponseDto findCustomerByid(long id) {
		Customer customer = customerRepository.findById(id).orElse(null);
		if(customer==null) {
			throw new CustomerNotFoundException("customer not found with id: "+id);
		}
		else {
		return convertCustomerTocustomerResponseDto(customer);
		}
	}


	@Override
	public CustomerResponseDto addAccount(long cid,int bid) {
		
		 Account account = new Account();
		 Bank bank = bankRepository.findById(bid).orElse(null);
		
		 
		 if(bank != null) {
			 Customer customer = customerRepository.findById(cid).orElse(null);
			 
			 if(customer != null){
				 
				//return convertAccounttoAccountResponseDto(accountRepository.save(account));
				 account.setBalance(1000);
				 account.setBank(bank);
				 account.setCustomer(customer);
				 
				 customer.addbankAccount(account);
				 double total_salary=1000;
				 if(accountRepository.getTotalBalance(customer)!=0) {
					 total_salary+=accountRepository.getTotalBalance(customer);
				 }
				 customer.setTotalBalance(total_salary);
				 Customer save = customerRepository.save(customer);
				 return convertCustomerTocustomerResponseDto(save);
				 
			 }
			 else {
				 throw new CustomerNotFoundException("customer not found with id: "+cid);
			 }
		 }
		return null;
		
		 
	
	
	}


	private Account convertAcountResponseDtoToAccount(AccountRequestDto accountRequestDto) {
		Account account=new Account();
		account.setAccountNumber(accountRequestDto.getAccountNumber());
		account.setBalance(accountRequestDto.getBalance());
		account.setBank(convertBankDtotoBank(accountRequestDto.getBankrequestDto()));
		account.setCustomer(convertcustomerDtoToCustomer(accountRequestDto.getCustomerRequestDto()));
		return account;
		
	}


	


	private Customer convertcustomerDtoToCustomer(CustomerRequestDto customerRequestDto) {
	
		Customer customer = new Customer();
		customer.setCustomer_id(customerRequestDto.getCustomer_id());
		customer.setFirstName(customerRequestDto.getFirstName());
		customer.setLastName(customerRequestDto.getLastName());
		customer.setTotalBalance(customerRequestDto.getTotalBalance());
		return customer;
	}


	private Bank convertBankDtotoBank(BankRequestDto bank) {
		Bank bank1 = new Bank();
		bank1.setBank_id(bank.getBank_id());
		bank1.setAbbreviation(bank.getAbbreviation());
		bank1.setFullName(bank.getFullName());
		return bank1;
	}


	@Override
	public TransactionResponseDto doTransaction(long senderAccountno, long receiverAccountno, double amount) {
		
		
		Account senderAccount=accountRepository.findById(senderAccountno).orElse(null);
		Account receiverAccount=accountRepository.findById(receiverAccountno).orElse(null);
		
		if(senderAccount==null){
			throw new NoRecordFoundException("sender account number no found");
		}
		if(receiverAccount==null) {
			throw new NoRecordFoundException("recevier account number no found");
		}
		if(senderAccount==receiverAccount) {
			throw new NoRecordFoundException("both Account numbers are same");
		}
		if(senderAccount.getBalance()<amount) {
			throw new NoRecordFoundException("Insufficient Balance");
		}
		
	
			senderAccount.setBalance(senderAccount.getBalance()-amount);
			receiverAccount.setBalance(receiverAccount.getBalance()+amount);
			Customer customer = new Customer();

			Customer sender=senderAccount.getCustomer();
			sender.setTotalBalance(sender.getTotalBalance()-amount);
			
			Customer receiver=receiverAccount.getCustomer();
			receiver.setTotalBalance(receiver.getTotalBalance()+amount);
			
			customerRepository.save(sender);
			customerRepository.save(receiver);
			
			
			
			accountRepository.save(senderAccount);
			accountRepository.save(receiverAccount);
			
		Transaction transaction=new Transaction();
		transaction.setAmount(amount);
		transaction.setSenderAccount(senderAccount);
		transaction.setReceiverAccount(receiverAccount);
		transaction.setTransactionType(TransactionType.Transfer);
	
	    Transaction save = transactionRepository.save(transaction);
	 
	    
		return  convertTransactiontoTransactionDto(save);
			
		
	}


	private TransactionResponseDto convertTransactiontoTransactionDto(Transaction save) {
		
		TransactionResponseDto transactionResponseDto=new TransactionResponseDto();
		transactionResponseDto.setAmount(save.getAmount());
		transactionResponseDto.setId(save.getId());
		transactionResponseDto.setSenderAccount(convertAccounttoAccountResponseDto(save.getSenderAccount()));
		transactionResponseDto.setReceiverAccount(convertAccounttoAccountResponseDto(save.getReceiverAccount()));
		transactionResponseDto.setTransactionDate(save.getTransactionDate());
		transactionResponseDto.setTransactionType(save.getTransactionType());
		
		return transactionResponseDto;
		
	}


	


	private List<TransactionResponseDto> convertTransactiontoTransactionDto(List<Transaction> all) {
		
		List<TransactionResponseDto> transactionResponseDto=new ArrayList<>();
		for(Transaction t:all) {
			transactionResponseDto.add(convertTransactiontoTransactionDto(t));
			
		}
		return transactionResponseDto;
	}


	@Override
	public PagedResponse<TransactionResponseDto> viewAllTransaction(int page, int size, String sortBy, String direction) {
		Sort sort = Sort.by(sortBy);
        if (direction.equalsIgnoreCase(Sort.Direction.DESC.name())) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        
        
        List<TransactionResponseDto> findAll =  convertTransactiontoTransactionDto(transactions.getContent());
		  return new PagedResponse<>(findAll, transactions.getNumber(), transactions.getSize(),
				  transactions.getTotalElements(), transactions.getTotalPages(), transactions.isLast());
		
		
		

	}


	@Override
	public List<TransactionResponseDto> viewPassbook(long accountNo) {
		
		Account account= accountRepository.findById(accountNo).orElse(null);
		if(account==null) {
			throw new NoRecordFoundException("account with accountnumber: "+accountNo+" not Found");
		}
		
		
		List<TransactionResponseDto> transactionResponseDto= covertPassbooktopassbookDto(transactionRepository.viewPassbook(account),accountNo);
		return transactionResponseDto;
		
		
		
		
		
	}


	private List<TransactionResponseDto> covertPassbooktopassbookDto(List<Transaction> viewPassbook,long accountNo) {
		List<TransactionResponseDto> transactionResponseDtos=new ArrayList<>();
		for(Transaction t :viewPassbook) {
			transactionResponseDtos.add(covertPassbooktopassbookDto(t,accountNo));
		}
		return transactionResponseDtos;
	}


	private TransactionResponseDto covertPassbooktopassbookDto(Transaction t,long accountNo) {
		 TransactionResponseDto transactionDto=new TransactionResponseDto();
		 
		 if((t.getSenderAccount().getAccountNumber())==accountNo) {
			 transactionDto.setTransactionType(TransactionType.Debit);
			 
		 }
		 else {
			 transactionDto.setTransactionType(TransactionType.Credit);
		 }
		 
		 transactionDto.setAmount(t.getAmount());
		 transactionDto.setId(t.getId());
		 transactionDto.setReceiverAccount(convertAccounttoAccountResponseDto(t.getReceiverAccount()));
		 transactionDto.setSenderAccount(convertAccounttoAccountResponseDto(t.getSenderAccount()));
		 transactionDto.setTransactionDate(t.getTransactionDate());
		return transactionDto;
	}


	@Override
	public List<TransactionResponseDto> searchByDate(LocalDateTime fromDate, LocalDateTime toDate) {
		
		return convertTransactiontoTransactionDto(transactionRepository.findByTransactionDateBetween(fromDate,toDate));
	}


	@Override
	public PagedResponse<CustomerResponseDto> getAllCustomers() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PagedResponse<TransactionResponseDto> getAllTransactions(LocalDateTime fromDate, LocalDateTime toDate,
			int page, int size, String sortBy, String direction) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto, long userID) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public AccountResponseDto depositAmount(long accountNumber, double amount) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<AccountResponseDto> getAccounts() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PagedResponse<TransactionResponseDto> getPassbook(long accountNumber, LocalDateTime fromDate,
			LocalDateTime toDate, int page, int size, String sortBy, String direction) {
		// TODO Auto-generated method stub
		return null;
	}





	
}