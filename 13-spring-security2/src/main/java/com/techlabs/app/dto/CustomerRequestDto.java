package com.techlabs.app.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class CustomerRequestDto {
	private long customer_id;

	@NotBlank(message = "First name cannot be blank")
	@Size(min = 2, max = 50)
	private String firstName;

	@NotBlank(message = "Last name cannot be blank")
	@Size(min = 2, max = 50)
	private String lastName;

	@NotNull
	private double totalBalance;

	private List<AccountRequestDto> accounts;

	public long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(double totalBalance) {
		this.totalBalance = totalBalance;
	}

	public List<AccountRequestDto> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountRequestDto> accounts) {
		this.accounts = accounts;
	}
	
	
}
