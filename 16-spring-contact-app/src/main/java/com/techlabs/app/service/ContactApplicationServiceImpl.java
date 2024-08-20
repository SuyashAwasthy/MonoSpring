package com.techlabs.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.techlabs.app.dto.ContactDetailRequestDto;
import com.techlabs.app.dto.ContactDetailResponseDto;
import com.techlabs.app.dto.ContactRequestDto;
import com.techlabs.app.dto.ContactResponseDto;
import com.techlabs.app.dto.UserRequestDto;
import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.entity.Contact;
import com.techlabs.app.entity.ContactDetail;
import com.techlabs.app.entity.ContactType;
import com.techlabs.app.entity.PagedResponse;
import com.techlabs.app.entity.User;
import com.techlabs.app.exception.ContactApiException;
import com.techlabs.app.exception.NoContactDetailRecordFoundException;
import com.techlabs.app.exception.NoContactFoundException;
import com.techlabs.app.exception.NoUserRecordFoundException;
import com.techlabs.app.repository.ContactDetailRepository;
import com.techlabs.app.repository.ContactRepository;
import com.techlabs.app.repository.UserRepository;

@Service
public class ContactApplicationServiceImpl implements ContactApplicationService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private ContactDetailRepository contactDetailRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public PagedResponse<UserResponseDto> getAllUsers(int page, int size, String sortBy, String direction) {
		if (!findByUserNameAndActive()) {
			throw new ContactApiException(HttpStatus.BAD_REQUEST, "User is not active");
		}
		Sort sort = Sort.by(sortBy);
		if (Sort.Direction.DESC.equals(direction)) {
			sort.descending();
		} else {
			sort.ascending();
		}
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		Page<User> users = userRepository.findAll(pageRequest);
		return new PagedResponse<UserResponseDto>(convertUserToUserResponseDto(users.getContent()), users.getNumber(),
				users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isLast());
	}

	@Override
	public UserResponseDto createAndUpdateUser(UserRequestDto userRequestDto) {
		if (userRepository.existsByEmail(userRequestDto.getEmail())) {
			throw new ContactApiException(HttpStatus.BAD_REQUEST, "Email already exists!");
		}
		if (findByUserNameAndActive()) {
			User user = convertUserRequestDtoToUser(userRequestDto);
			User userResponse = null;
			if (userRequestDto.getUserId() == 0) {
				return convertUserToUserResponseDto(userRepository.save(user));
			}
			User userFound = userRepository.findById(userRequestDto.getUserId()).orElse(null);
			if (userFound == null) {
				throw new NoUserRecordFoundException("User with id " + user.getUserId() + " not found");
			}
			if (userRequestDto.getEmail() != null && userRequestDto.getEmail() != "") {
				userFound.setEmail(userRequestDto.getEmail());
			}
			if (userRequestDto.getFirstName() != null && userRequestDto.getFirstName() != "") {
				userFound.setFirstName(userRequestDto.getFirstName());
			}
			if (userRequestDto.getLastName() != null && userRequestDto.getLastName() != "") {
				userFound.setLastName(userRequestDto.getLastName());
			}
			if (userRequestDto.isActive() != userFound.isActive()) {
				userFound.setActive(userRequestDto.isActive());
			}
			userResponse = userRepository.save(userFound);
			return convertUserToUserResponseDto(userResponse);
		}
		throw new NoUserRecordFoundException("Admin is inactive.");
	}

	private boolean findByUserNameAndActive() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
			if (user != null && user.isActive()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String deleteContactDetail(long contactId, long contactDetailId) {
		if (!findByUserNameAndActive()) {
			throw new ContactApiException(HttpStatus.BAD_REQUEST, "User is inactive!");
		}
		Contact contac = contactRepository.findById(contactId).orElse(null);
		if(contac==null) {
			throw new NoContactFoundException("Contact with id "+contactId + " not found.");
		}
		if(!contac.isActive()) {
			throw new NoContactFoundException("Contact is inactive.");
		}
		List<Contact> contacts = userRepository.findByEmail(findByEmail()).orElse(null).getContacts();
		for(Contact contac:contacts) {
			if(contac.getContactId()==contactId) {
				for(ContactDetail contactDetail:contac.getContactDetails()) {
					if(contactDetail.getContactDetailsId()==contactDetailId) {
						contac.getContactDetails().remove(contactDetail);
						contactRepository.save(contac);
						contactDetailRepository.delete(contactDetail);
						return "Deleted successfully!";
					}
				}
			}
		}
		throw new NoContactDetailRecordFoundException("Contact with id "+contactDetailId + " not found.");
	}

	@Override
	public UserResponseDto getUserById(long id) {
		if (!findByUserNameAndActive()) {
			throw new ContactApiException(HttpStatus.BAD_REQUEST, "User is not active");
		}
		User user = userRepository.findById(id).orElse(null);
		if (user == null) {
			throw new NoUserRecordFoundException("User with id " + id + " not found");
		}
		return convertUserToUserResponseDto(user);
		
	}

	@Override
	public String deleteUser(long id) {
		if (findByUserNameAndActive()) {
			User user = userRepository.findById(id).orElse(null);
			if (user.isActive() == false) {
				throw new NoUserRecordFoundException("User with id " + id + " not active");
			}
			user.setActive(false);
			userRepository.save(user);
		}

		return "User deleted successfully!";
	}

	private UserResponseDto convertUserToUserResponseDto(User user) {
		UserResponseDto userResponseDto = new UserResponseDto();
		userResponseDto.setUserId(user.getUserId());
		userResponseDto.setFirstName(user.getFirstName());
		userResponseDto.setLastName(user.getLastName());
		userResponseDto.setActive(user.isActive());
		userResponseDto.setAdmin(user.isAdmin());
		userResponseDto.setEmail(user.getEmail());
		return userResponseDto;
	}

	private User convertUserRequestDtoToUser(UserRequestDto userRequestDto) {
		User user = new User();
		user.setAdmin(userRequestDto.isAdmin());
		user.setActive(userRequestDto.isActive());
		user.setEmail(userRequestDto.getEmail());
		user.setFirstName(userRequestDto.getFirstName());
		user.setLastName(userRequestDto.getLastName());
		if (userRequestDto.getPassword() != null && !userRequestDto.getPassword().isEmpty()) {
	        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
	    }
		return user;
	}

	private List<UserResponseDto> convertUserToUserResponseDto(List<User> content) {
		List<UserResponseDto> list = new ArrayList<UserResponseDto>();
		for (User user : content) {
			UserResponseDto userResponseDto = convertUserToUserResponseDto(user);
			System.out.println(userResponseDto);
			list.add(userResponseDto);
		}
		return list;
	}

	@Override
	public ContactResponseDto createAndUpdateContact(ContactRequestDto contactRequestDto) {
		if (!findByUserNameAndActive()) {
			throw new ContactApiException(HttpStatus.BAD_REQUEST, "User is inactive.");
		}
		if (contactRequestDto.getContactId() == 0) {
			Contact contact = convertContactRequestDtoToContact(contactRequestDto);
			
			return convertContactToContactResponseDto(contactRepository.save(contact));
		}
		Contact contact = contactRepository.findById(contactRequestDto.getContactId()).orElse(null);
		if (contact == null) {
			throw new NoContactFoundException(
					"Contact with id " + contactRequestDto.getContactId() + " not found");
		}
		if (contactRequestDto.isActive() != contact.isActive()) {
			contact.setActive(contactRequestDto.isActive());
		}
		if (contactRequestDto.getFirstName() != null && contactRequestDto.getFirstName() != "") {
			contact.setFirstName(contactRequestDto.getFirstName());
		}
		if (contactRequestDto.getLastName() != null && contactRequestDto.getLastName() != "") {
			contact.setLastName(contactRequestDto.getLastName());
		}
		
		return convertContactToContactResponseDto(contactRepository.save(contact));
		
	}

	private ContactResponseDto convertContactToContactResponseDto(Contact save) {
		ContactResponseDto responseDto = new ContactResponseDto();
		responseDto.setFirstName(save.getFirstName());
		responseDto.setLastName(save.getLastName());
		responseDto.setContactId(save.getContactId());
		responseDto.setActive(save.isActive());
		responseDto.setUser(convertUserToUserResponseDto(save.getUser()));

		return responseDto;
	}

	private Contact convertContactRequestDtoToContact(ContactRequestDto contactRequestDto) {
		String userName = findByEmail();
		User user = userRepository.findByEmail(userName).orElse(null);
		Contact contact = new Contact();
		contact.setFirstName(contactRequestDto.getFirstName());
		contact.setLastName(contactRequestDto.getLastName());
		contact.setUser(user);
		contact.setActive(contactRequestDto.isActive());
		return contact;
	}

	@Override
	public PagedResponse<ContactResponseDto> getAllContacts(int page, int size, String sortBy, String direction) {
		if (!findByUserNameAndActive()) {
			throw new ContactApiException(HttpStatus.BAD_REQUEST, "User is inactive.");
		}
		String email = findByEmail();
		User user = userRepository.findByEmail(email).orElse(null);
		Sort sort = Sort.by(sortBy);
		if (Sort.Direction.DESC.equals(direction)) {
			sort.descending();
		} else {
			sort.ascending();
		}
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		Page<Contact> contacts = contactRepository.findByUser(user, pageRequest);
		return new PagedResponse<>(convertContactToContactResponseDto(contacts.getContent()), contacts.getNumber(),
				contacts.getSize(), contacts.getTotalElements(), contacts.getTotalPages(), contacts.isLast());
	}

	private List<ContactResponseDto> convertContactToContactResponseDto(List<Contact> contacts) {
		List<ContactResponseDto> contactresponseDtos = new ArrayList<>();
		for (Contact contact : contacts) {
			contactresponseDtos.add(convertContactToContactResponseDtoWithoutUser(contact));
		}
		return contactresponseDtos;
	}

	private ContactResponseDto convertContactToContactResponseDtoWithoutUser(Contact contact) {
		ContactResponseDto responseDto = new ContactResponseDto();
		responseDto.setFirstName(contact.getFirstName());
		responseDto.setLastName(contact.getLastName());
		responseDto.setContactId(contact.getContactId());
		responseDto.setActive(contact.isActive());
		return responseDto;
	}

	@Override
	public ContactResponseDto getContactById(long id) {
		if (!findByUserNameAndActive()) {
			throw new ContactApiException(HttpStatus.BAD_REQUEST, "User is iactive.");
		}
		User user = userRepository.findByEmail(findByEmail()).orElse(null);
		List<Contact> contacts = user.getContacts();
		for (Contact contact : contacts) {
			if(contact.getContactId()==id) {
				
				return convertContactToContactResponseDto(contact);
			}
		}
		throw new NoContactFoundException("No contact with id " + id + " not found"); 
	}
	
	private String findByEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			return userDetails.getUsername();
		}
		return null;
	}

	@Override
	public String deleteContact(long Id) {
		if (!findByUserNameAndActive()) {
			throw new ContactApiException(HttpStatus.BAD_REQUEST, "User is inactive.");
		}
		Contact contact= contactRepository.findById(Id).orElse(null);
		if(contact.isActive()==false) {
			throw new NoContactFoundException("Contact is inactive.");
		}
		User user=userRepository.findByEmail(findByEmail()).orElse(null);
		user.deleteContact(contact);
		userRepository.save(user);
		return "Contact deleted sucessfully";
	}

	@Override
	public String createAndUpdateContactDetail(long id,ContactDetailRequestDto contactDetailRequestDto) {
		if (!findByUserNameAndActive()) {
			throw new ContactApiException(HttpStatus.BAD_REQUEST, "User is inactive.");
		}
		if(contactRepository.findById(id).orElse(null)==null) {
			throw new NoContactFoundException("contact not found "+id);
		}
		User user = userRepository.findByEmail(findByEmail()).orElse(null);
		List<Contact> contacts = user.getContacts();
		ContactDetail contactDetail= convertContactDetailRequestDto(contactDetailRequestDto);
		if(contactDetailRequestDto.getContactDetailsId()==0) {
			for(Contact contact:contacts) {
				if(contact.getContactId()==id) {
					
					contactDetail.setContact(contact);
					contact.addContactDetail(contactDetail);
					contactRepository.save(contact);
					return "contact details added successfully";
				}
			}
			throw new ContactApiException(HttpStatus.BAD_REQUEST, "You do not have this contact.");
		}
		contactDetailRepository.findById(contactDetailRequestDto.getContactDetailsId()).orElseThrow(()->new NoContactDetailRecordFoundException("Contact Detail not found"));
		for(Contact contac:contacts) {
			if(contact.getContactId()==id) {
				for(ContactDetail contactDetail:contac.getContactDetails()) {
					if(contactDetail.getContactDetailsId()==contactDetailRequestDto.getContactDetailsId()) {
						contactDetail.setContactType(contactDetailRequestDto.getContactType());
						contactRepository.save(contac);
						return "Contact updated successfully!";
					}	
				}	
			}
		}
		throw new NoContactDetailRecordFoundException("Contact detail with id "+contactDetailRequestDto.getContactDetailsId()+" not found.");
	}

	private ContactDetail convertContactDetailRequestDto(ContactDetailRequestDto contactDetailRequestDto) {
		ContactDetail contactDetail=new ContactDetail();
		contactDetail.setContactType(contactDetailRequestDto.getContactType());	
		return contactDetail;
	}

	@Override
	public PagedResponse<ContactDetailResponseDto> getAllContactDetails(long id,int page, int size, String sortBy, String direction) {
		if (!findByUserNameAndActive()) {
			throw new ContactApiException(HttpStatus.BAD_REQUEST, "User is inactive!");
		}
		Sort sort=Sort.by(sortBy);
		if(Sort.Direction.DESC.name().equalsIgnoreCase(direction)) {
			sort=sort.descending();
		}
		else {
			sort=sort.ascending();
		}
		Contact contact = contactRepository.findById(id).orElse(null);
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		Page<ContactDetail> contactDetails=contactDetailRepository.findByContact(contact,pageRequest);
		return new PagedResponse<ContactDetailResponseDto>(convertContactDetailToContactDetailResponseDto(contactDetails.getContent()), contactDetails.getNumber(), contactDetails.getSize(), contactDetails.getTotalElements(), contactDetails.getTotalPages(), contactDetails.isLast());
	}

	private List<ContactDetailResponseDto> convertContactDetailToContactDetailResponseDto(List<ContactDetail> content) {
		List<ContactDetailResponseDto> contactDetails=new ArrayList<ContactDetailResponseDto>();
		for(ContactDetail contactDetail:content) {
			
			contactDetails.add(convertContactDetailDtoToContactDetailResponseDto(contactDetail));
		}
		
		return contactDetails;
	}

	@Override
	public ContactDetailResponseDto getContactDetailById(long contactId, long contactDetailId) {
		
		if (!findByUserNameAndActive()) {
			throw new ContactApiException(HttpStatus.BAD_REQUEST, "User is inactive!");
		}
		
		Contact contact = contactRepository.findById(contactId).orElse(null);
		
		if(!contact.isActive()) {
			throw new NoContactFoundException("Contact is inactive.");
		}
		
		if(contact==null) {
			throw new NoContactFoundException("Contact with id "+contactId + " not found.");
		}
		
		List<Contact> contacts = userRepository.findByEmail(findByEmail()).orElse(null).getContacts();
		for(Contact contac:contacts) {
			if(contac.getContactId()==contactId) {
				for(ContactDetail contactDetail:contac.getContactDetails()) {
					if(contactDetail.getContactDetailsId()==contactDetailId) {
						return convertContactDetailDtoToContactDetailResponseDto(contactDetail);
					}
				}
			}
		}
		throw new NoContactDetailRecordFoundException("Contact detail with id "+contactDetailId + " not found!");
	}

	private ContactDetailResponseDto convertContactDetailDtoToContactDetailResponseDto(ContactDetail contactDetail) {
		
		ContactDetailResponseDto dto=new ContactDetailResponseDto();
		dto.setContactDetailsId(contactDetail.getContactDetailsId());
		dto.setContactType(contactDetail.getContactType());

		dto.setContact(convertContactToContactResponseDto(contactDetail.getContact()));
		
		return dto;
	}

	

}