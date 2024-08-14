package com.techlabs.app.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.techlabs.app.dto.ContactDetailResponseDto;
import com.techlabs.app.dto.ContactRequestDto;
import com.techlabs.app.dto.ContactResponseDto;
import com.techlabs.app.dto.ProfileRequestDto;
import com.techlabs.app.dto.UserRequestDto;
import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Bank;
import com.techlabs.app.entity.Customer;
import com.techlabs.app.entity.Transaction;
import com.techlabs.app.entity.TransactionType;
import com.techlabs.app.entity.User;
import com.techlabs.app.exception.NoRecordFoundException;
import com.techlabs.app.repository.AccountRepository;
import com.techlabs.app.repository.BankRepository;
import com.techlabs.app.repository.CustomerRepository;
import com.techlabs.app.repository.TransactionRepository;
import com.techlabs.app.repository.UserRepository;
import com.techlabs.app.util.EmailUtil;
import com.techlabs.app.util.MailStructure;
import com.techlabs.app.util.PagedResponse;

import jakarta.mail.MessagingException;

@Service
public class BankAppServiceImpl implements BankAppService{
	
	@Value("${spring.mail.username}")
	private String fromMail;

	@Autowired
	private EmailUtil emailUtil;
	private TransactionRepository transactionRepository;
	private CustomerRepository customerRespository;
	private AccountRepository accountRepository;
	private BankRepository bankRepository;
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public BankAppServiceImpl(TransactionRepository transactionRepository,
			CustomerRepository customerRespository, AccountRepository accountRepository, BankRepository bankRepository,
			UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.transactionRepository = transactionRepository;
		this.customerRespository = customerRespository;
		this.accountRepository = accountRepository;
		this.bankRepository = bankRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	private List<UserRequestDto> convertTransactionToTransactionResponseDTO(List<Transaction> transactions) {
		List<UserRequestDto> transactionDtos = new ArrayList<UserRequestDto>();
		for (Transaction transaction : transactions) {
			transactionDtos.add(convertTransactionToTransactionResponseDTO(transaction));
		}
		return transactionDtos;
	}

	private UserRequestDto convertTransactionToTransactionResponseDTO(Transaction transaction) {
		UserRequestDto responseDto = new UserRequestDto();
		responseDto.setId(transaction.getId());
		if (transaction.getSenderAccount() != null) {
			responseDto.setSenderAccount(convertAccountTransactionToAccountResponseDto(transaction.getSenderAccount()));
		}
		if (transaction.getReceiverAccount() != null) {
			responseDto.setReceiverAccount(
					convertAccountTransactionToAccountResponseDto(transaction.getReceiverAccount()));
		}
		responseDto.setTransactionType(transaction.getTransactionType());
		responseDto.setTransactionDate(transaction.getTransactionDate());
		responseDto.setAmount(transaction.getAmount());
		return responseDto;
	}

	private ContactDetailResponseDto convertAccountToAccountResponseDto(Account account) {
		ContactDetailResponseDto accountResponseDTO = new ContactDetailResponseDto();
		if (account != null) {
			accountResponseDTO.setAccountNumber(account.getAccountNumber());
			accountResponseDTO.setBalance(account.getBalance());
		}
		return accountResponseDTO;
	}

	private ContactDetailResponseDto convertAccountTransactionToAccountResponseDto(Account account) {
		ContactDetailResponseDto accountResponseDTO = new ContactDetailResponseDto();
		if (account != null) {
			accountResponseDTO.setAccountNumber(account.getAccountNumber());
		}
		return accountResponseDTO;
	}

	@Override
	public UserResponseDto createCustomer(ContactRequestDto customerRequestDto, long userID) {
		User user = userRepository.findById(userID).orElse(null);
		if (user == null) {
			throw new NoRecordFoundException("User: " + userID + "not found!");
		}
		if (user.getCustomer() != null) {
			throw new NoRecordFoundException("Another customer to the user cannot be created!");
		}
		Customer customer = convertCustomerRequestToCustomer(customerRequestDto);
		user.setCustomer(customer);
		String subject = "Welcome to the Spring Bank! Your Customer ID is successfully created!";
		String emailBody = "Dear " + customerRequestDto.getFirstName() + " " + customerRequestDto.getLastName()
				+ "Welcome aboard! We look forward to serving you and supporting your financial goals.\n\n"
				+ "Regards\n" + "Spring Bank!";
		MailStructure mailStructure = new MailStructure();
		mailStructure.setToEmail(user.getEmail());
		mailStructure.setEmailBody(emailBody);
		mailStructure.setSubject(subject);
		emailUtil.sendEmail(mailStructure);
		return convertUserToUserDto(userRepository.save(user));
	}

	private UserResponseDto convertUserToUserDto(User save) {
		UserResponseDto responseDto = new UserResponseDto();
		responseDto.setId(save.getId());
		responseDto.setCustomerResponseDto(convertCustomerToCustomerResponseDto(save.getCustomer()));
		responseDto.setEmail(save.getEmail());
		return responseDto;
	}

	private ContactResponseDto convertCustomerToCustomerResponseDto(Customer save) {
		ContactResponseDto customerResponseDto = new ContactResponseDto();
		customerResponseDto.setCustomer_id(save.getCustomer_id());
		customerResponseDto.setFirstName(save.getFirstName());
		customerResponseDto.setLastName(save.getLastName());
		customerResponseDto.setTotalBalance(save.getTotalBalance());
		return customerResponseDto;
	}

	private Customer convertCustomerRequestToCustomer(ContactRequestDto customerRequestDto) {
		Customer customer = new Customer();
		customer.setFirstName(customerRequestDto.getFirstName());
		customer.setLastName(customerRequestDto.getLastName());
		customer.setTotalBalance(customerRequestDto.getTotalBalance());
		return customer;
	}

	@Override
	public ContactResponseDto createAccount(long customerID, int bankID) {
		Customer customer = customerRespository.findById(customerID).orElse(null);
		if (customer == null) {
			throw new NoRecordFoundException("Customer with id " + customerID + " not found!");
		}
		Bank bank = bankRepository.findById(bankID).orElse(null);
		if (bank == null) {
			throw new NoRecordFoundException("Bank with id " + bankID + " not found!");
		}
		Account account = new Account();
		account.setBalance(1000);
		account.setCustomer(customer);
		account.setBank(bank);
		customer.addAccount(account);
		double totalSalary = 1000;
		if (accountRepository.getTotalBalance(customer) != 0) {
			totalSalary += accountRepository.getTotalBalance(customer);
		}
		User user = customer.getUser();
		customer.setTotalBalance(totalSalary);
		customerRespository.save(customer);
		String subject = "Your Account is created!";
		String emailBody = "Dear " + customer.getFirstName() + " " + customer.getLastName() + ",\n\n"
				+ "Your account has been successfully created with Customer ID: " + customer.getCustomer_id()
				+ "Regards\n" + "Spring Bank";
		MailStructure mailStructure = new MailStructure();
		mailStructure.setToEmail(user.getEmail());
		mailStructure.setEmailBody(emailBody);
		mailStructure.setSubject(subject);
		emailUtil.sendEmail(mailStructure);

		return convertCustomerAccountToCustomerResponseDto(customer);
	}

	private ContactResponseDto convertCustomerAccountToCustomerResponseDto(Customer customer) {
		ContactResponseDto customerResponseDto = new ContactResponseDto();
		customerResponseDto.setFirstName(customer.getFirstName());
		customerResponseDto.setLastName(customer.getLastName());
		customerResponseDto.setCustomer_id(customer.getCustomer_id());
		customerResponseDto.setAccounts(convertAccountToAccountResponseDto(customer.getAccounts()));
		customerResponseDto.setTotalBalance(customer.getTotalBalance());

		return customerResponseDto;
	}

	private List<ContactDetailResponseDto> convertAccountToAccountResponseDto(List<Account> accounts) {
		List<ContactDetailResponseDto> accountResponseDTOs = new ArrayList<>();
		for (Account account : accounts) {
			accountResponseDTOs.add(convertAccountToAccountResponseDto(account));
		}
		return accountResponseDTOs;
	}

	@Override
	public List<ContactResponseDto> getAllCustomers() {
		return convertCustomerToCustomerResponseDto(customerRespository.findAll());
	}

	private List<ContactResponseDto> convertCustomerToCustomerResponseDto(List<Customer> customers) {
		List<ContactResponseDto> customerResponseDtos = new ArrayList<>();
		for (Customer customer : customers) {
			ContactResponseDto customerResponseDto = new ContactResponseDto();
			customerResponseDto.setCustomer_id(customer.getCustomer_id());
			customerResponseDto.setFirstName(customer.getFirstName());
			customerResponseDto.setLastName(customer.getLastName());
			customerResponseDto.setTotalBalance(customer.getTotalBalance());
			customerResponseDto.setAccounts(convertAccountToAccountResponseDto(customer.getAccounts()));
			customerResponseDtos.add(customerResponseDto);
		}
		return customerResponseDtos;
	}

	@Override
	public ContactResponseDto getCustomerById(long customerid) {
		Customer customer = customerRespository.findById(customerid).orElse(null);
		if (customer == null) {
			throw new NoRecordFoundException("No customer found with the id " + customerid);
		}
		return convertCustomerAccountToCustomerResponseDto(customer);
	}

	@Override
	public PagedResponse<UserRequestDto> getAllTransactions(LocalDateTime fromDate, LocalDateTime toDate,
			int page, int size, String sortBy, String direction) {
		Sort sort = Sort.by(sortBy);
		if (direction.equalsIgnoreCase("desc")) {
			sort = sort.descending();
		} else {
			sort = sort.ascending();
		}
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		System.out.println("Page request: " + pageRequest);
		Page<Transaction> pagedResponse = transactionRepository.findAllByTransactionDateBetween(fromDate, toDate,
				pageRequest);

		PagedResponse<UserRequestDto> response = new PagedResponse<>(
				convertTransactionToTransactionResponseDTO(pagedResponse.getContent()), pagedResponse.getNumber(),
				pagedResponse.getSize(), pagedResponse.getTotalElements(), pagedResponse.getTotalPages(),
				pagedResponse.isLast());
		return response;
	}

	@Override
	public UserRequestDto performTransaction(long senderAccountNumber, long receiverAccountNumber,
			double amount) {
		Optional<User> user = userRepository.findByEmail(getUsernameFromSecurityContext());
		List<Account> accounts = user.get().getCustomer().getAccounts();
		for (Account account : accounts) {
			if (account.getAccountNumber() == senderAccountNumber) {
				Account senderAccount = accountRepository.findById(senderAccountNumber).orElse(null);
				Account receiverAccount = accountRepository.findById(receiverAccountNumber).orElse(null);
				if (senderAccount == null || receiverAccount == null) {
					throw new NoRecordFoundException("No record found. Check the details again!");
				}
				if (senderAccount.equals(receiverAccount)) {
					throw new NoRecordFoundException("Self transfer not possible!");
				}
				if (senderAccount.getBalance() < amount) {
					throw new NoRecordFoundException("Insufficient funds!");
				}
				senderAccount.setBalance(senderAccount.getBalance() - amount);
				receiverAccount.setBalance(receiverAccount.getBalance() + amount);
				accountRepository.save(senderAccount);
				accountRepository.save(receiverAccount);
				Customer senderCustomer = senderAccount.getCustomer();
				Customer receiverCustomer = receiverAccount.getCustomer();
				senderCustomer.setTotalBalance(senderCustomer.getTotalBalance() - amount);
				receiverCustomer.setTotalBalance(receiverCustomer.getTotalBalance() + amount);

				customerRespository.save(senderCustomer);
				customerRespository.save(receiverCustomer);
				Transaction transaction = new Transaction();
				transaction.setAmount(amount);
				transaction.setSenderAccount(senderAccount);
				transaction.setReceiverAccount(receiverAccount);
				transaction.setTransactionType(TransactionType.Transfer);
				User senderUser = senderCustomer.getUser();
				User receiverUser = receiverCustomer.getUser();
				sendMailToTheUsers(senderUser, receiverUser, senderCustomer, senderAccountNumber, transaction,
						receiverCustomer, receiverAccountNumber);

				return convertTransactionToTransactionResponseDTO(transactionRepository.save(transaction));
			}
		}
		throw new NoRecordFoundException("No records with this account number!");

	}

	private void sendMailToTheUsers(User senderUser, User receiverUser, Customer senderCustomer,
			long senderAccountNumber, Transaction transaction, Customer receiverCustomer, long receiverAccountNumber) {

		String subject = "Debit notification!";
		String body = "Dear " + senderCustomer.getFirstName() + " " + senderCustomer.getLastName() + ",\n\n"
				+ "Amount of Rs." + transaction.getAmount()
				+ " debited on " + transaction.getTransactionDate() + ".\n" + "Account Number: ****" + senderAccountNumber + "\n"
				+ "Regards\n" + "Spring Bank";
		MailStructure mailStructure = new MailStructure();
		mailStructure.setToEmail(senderUser.getEmail());
		mailStructure.setEmailBody(body);
		mailStructure.setSubject(subject);
		emailUtil.sendEmail(mailStructure);
		String cSubject = "Credit notification!";
		String cBody = "Dear " + receiverCustomer.getFirstName() + " " + receiverCustomer.getLastName() + ",\n\n"
				+ "Amount of Rs" + transaction.getAmount() 
				+ " credited on " + transaction.getTransactionDate() + ".\n" + "Account Number: ****" + receiverAccountNumber + "\n"
				+ "Regards\n" + "Spring Bank";
		mailStructure = new MailStructure();
		mailStructure.setToEmail(receiverUser.getEmail());
		mailStructure.setEmailBody(cBody);
		mailStructure.setSubject(cSubject);
		emailUtil.sendEmail(mailStructure);

	}

	@Override
	public PagedResponse<UserRequestDto> getPassbook(long accountNumber, LocalDateTime from, LocalDateTime to,
			int page, int size, String sortBy, String direction)
			throws IOException, MessagingException {
		Sort sort = Sort.by(sortBy);
		if (direction.equalsIgnoreCase(Sort.Direction.DESC.name())) {
			sort.descending();
		} else {
			sort.ascending();
		}

		String email = getUsernameFromSecurityContext();
		Optional<User> user = userRepository.findByEmail(email);
		if (user.get().getCustomer() == null) {
			throw new NoRecordFoundException("Not registered!");
		}
		List<Account> accounts = user.get().getCustomer().getAccounts();
		for (Account acc : accounts) {
			if (acc.getAccountNumber() == accountNumber) {
				Account account = accountRepository.findById(accountNumber).orElse(null);
				PageRequest pageRequest = PageRequest.of(page, size, sort);
				Page<Transaction> pagedResponse = transactionRepository.getPassbook(account, from, to, pageRequest);
				String subject = "Passbook";
				String body = "Dear " + user.get().getCustomer().getFirstName() + " "
						+ user.get().getCustomer().getLastName() + ",\n" + "PFA your passbook."
						+ "Regards\n" + "Spring Bank";

				MailStructure mailStructure = new MailStructure();
				mailStructure.setToEmail(user.get().getEmail());
				mailStructure.setEmailBody(body);
				mailStructure.setSubject(subject);
				List<UserRequestDto> responseDTO = convertTransactionToTransactionResponseDTO(pagedResponse.getContent(),accountNumber);
				return new PagedResponse<UserRequestDto>(responseDTO,
						pagedResponse.getNumber(), pagedResponse.getSize(), pagedResponse.getTotalElements(),
						pagedResponse.getTotalPages(), pagedResponse.isLast());
			}
		}
		throw new NoRecordFoundException("No record!");

	}

	private List<UserRequestDto> convertTransactionToTransactionResponseDTO(List<Transaction> passbook,
			long accountNumber) {
		List<UserRequestDto> list = new ArrayList<>();
		for (Transaction transaction : passbook) {
			UserRequestDto responseDto = new UserRequestDto();
			responseDto.setAmount(transaction.getAmount());
			if (transaction.getReceiverAccount() != null) {
				responseDto.setReceiverAccount(
						convertAccountTransactionToAccountResponseDto(transaction.getReceiverAccount()));
			}
			if (transaction.getSenderAccount() != null) {
				responseDto.setSenderAccount(
						convertAccountTransactionToAccountResponseDto(transaction.getSenderAccount()));
			}
			responseDto.setId(transaction.getId());
			responseDto.setTransactionDate(transaction.getTransactionDate());
			if (transaction.getSenderAccount() != null
					&& transaction.getSenderAccount().getAccountNumber() == accountNumber) {
				responseDto.setTransactionType(TransactionType.Debit);
			} else {
				responseDto.setTransactionType(TransactionType.Credit);
			}
			list.add(responseDto);
		}
		return list;
	}

	@Override
	public String updateProfile(ProfileRequestDto profileRequestDto) {
		User user = userRepository.findByEmail(getUsernameFromSecurityContext()).orElse(null);
		if (user.getCustomer() == null) {
			throw new NoRecordFoundException("Not registered!");
		}
		Customer customer = user.getCustomer();
		if (profileRequestDto.getEmail() != null && !profileRequestDto.getEmail().isEmpty()
				&& profileRequestDto.getEmail().length() != 0) {
			user.setEmail(profileRequestDto.getEmail());
		}
		if (profileRequestDto.getFirstName() != null && !profileRequestDto.getFirstName().isEmpty()
				&& profileRequestDto.getFirstName().length() != 0) {
			customer.setFirstName(profileRequestDto.getFirstName());
		}
		if (profileRequestDto.getLastName() != null && !profileRequestDto.getLastName().isEmpty()
				&& profileRequestDto.getLastName().length() != 0) {
			customer.setLastName(profileRequestDto.getLastName());
		}
		if (profileRequestDto.getPassword() != null && !profileRequestDto.getPassword().isEmpty()
				&& profileRequestDto.getPassword().length() != 0) {
			user.setPassword(passwordEncoder.encode(profileRequestDto.getPassword()));
		}

		userRepository.save(user);

		return "User succesfully updated!";
	}

	private String getUsernameFromSecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			return userDetails.getUsername();
		}
		return null;
	}

	@Override
	public ContactDetailResponseDto depositAmount(long accountNumber, double amount) {
		User user = userRepository.findByEmail(getUsernameFromSecurityContext()).orElse(null);
		List<Account> accounts = user.getCustomer().getAccounts();
		Customer customer = user.getCustomer();
		if (customer == null) {
			throw new NoRecordFoundException("Customer not linked with the user!");
		}
		for (Account account : accounts) {
			if (account.getAccountNumber() == accountNumber) {
				account.setBalance(account.getBalance() + amount);
				accountRepository.save(account);
				Double totalBalance = accountRepository.getTotalBalance(customer);
				customer.setTotalBalance(totalBalance);
				customerRespository.save(customer);
				Transaction transaction = new Transaction();
				transaction.setAmount(amount);
				transaction.setReceiverAccount(account);
				transaction.setTransactionType(TransactionType.Transfer);
				transactionRepository.save(transaction);
				String subject = "Credit notification";
				String emailBody = "Dear " + customer.getFirstName() + " " + customer.getLastName() + ",\n\n"
						+ "An amount of Rs." + transaction.getAmount() + " credited on " + LocalDateTime.now() + ".\n" + "Account Number: ****"
						+ accountNumber + "\n"
						+ "Regards\n" + "Spring Bank";

				MailStructure mailStructure = new MailStructure();
				mailStructure.setToEmail(user.getEmail());
				mailStructure.setEmailBody(emailBody);
				mailStructure.setSubject(subject);
				emailUtil.sendEmail(mailStructure);
				return convertAccountToAccountResponseDto(account);
			}
		}
		throw new NoRecordFoundException("No records!");

	}

	@Override
	public List<ContactDetailResponseDto> getAccounts() {
		User user = userRepository.findByEmail(getUsernameFromSecurityContext()).orElse(null);
		return convertAccountToAccountResponseDto(user.getCustomer().getAccounts());
	}

	@Override
	public String deleteCustomer(long customerID) {
		Customer customer = customerRespository.findById(customerID).orElse(null);
		if (customer == null) {
			throw new NoRecordFoundException("Customer: " + customerID + "not found!");
		}
		if (!customer.isActive()) {
			throw new NoRecordFoundException("Customer is already deleted");
		}
		customer.setActive(false);
		List<Account> accounts = customer.getAccounts();
		for (Account account : accounts) {
			account.setActive(false);
		}
		customerRespository.save(customer);
		return "Customer deleted successfully!";
	}

	@Override
	public String activateCustomer(long customerID) {
		Customer customer = customerRespository.findById(customerID).orElse(null);
		if (customer == null) {
			throw new NoRecordFoundException("Customer: " + customerID + "not found!");
		}
		if (customer.isActive()) {
			throw new NoRecordFoundException("Customer is already active");
		}
		customer.setActive(true);
		customerRespository.save(customer);
		return "Customer activated successfully!";
	}

	@Override
	public String deleteAccount(long accountNumber) {
		Account account = accountRepository.findById(accountNumber).orElse(null);
		if (account == null) {
			throw new NoRecordFoundException("Account: " + accountNumber + "not found!");
		}
		if (!account.isActive()) {
			throw new NoRecordFoundException("Account is already deleted");
		}
		account.setActive(false);
		accountRepository.save(account);
		return "Account deleted successfully!";
	}

	@Override
	public String activateAccount(long accountNumber) {
		Account account = accountRepository.findById(accountNumber).orElse(null);
		if (account == null) {
			throw new NoRecordFoundException("Account: " + accountNumber + "not found!");
		}
		if (account.isActive()) {
			throw new NoRecordFoundException("Account is already active");
		}
		account.setActive(true);
		accountRepository.save(account);
		return "Account activated successfully!";
	}

	@Override
	public ContactDetailResponseDto viewBalance(long accountNumber) {
		String email = getUsernameFromSecurityContext();
		Optional<User> user = userRepository.findByEmail(email);
		List<Account> accounts = user.get().getCustomer().getAccounts();
		for (Account account : accounts) {
			if (account.getAccountNumber() == accountNumber && isAccountActive(account)) {
				return convertAccountToAccountResponseDto(account);
			}
		}
		throw new NoRecordFoundException("No records!");
	}

	private boolean isAccountActive(Account account) {
		if (!account.isActive()) {
			return false;
		}
		return true;
	}
}
