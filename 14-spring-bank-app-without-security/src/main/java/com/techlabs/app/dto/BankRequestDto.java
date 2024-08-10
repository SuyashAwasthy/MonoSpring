package com.techlabs.app.dto;

import java.util.List;

import com.techlabs.app.entity.Account;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class BankRequestDto {
	private int bank_id;
	
	@NotBlank(message = "Bank name cannot be empty")
	private String fullName;
	
	@NotBlank
	private String abbreviation;
	
	
	private List<Account> accounts;
}
