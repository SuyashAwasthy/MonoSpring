package com.techlabs.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
@Data
@JsonInclude(value = Include.NON_NULL)
public class UserResponseDto {
	private Long id;

	private String email;
	private CustomerResponseDto customerResponseDto;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public CustomerResponseDto getCustomerResponseDto() {
		return customerResponseDto;
	}
	public void setCustomerResponseDto(CustomerResponseDto customerResponseDto) {
		this.customerResponseDto = customerResponseDto;
	}
	
	
}