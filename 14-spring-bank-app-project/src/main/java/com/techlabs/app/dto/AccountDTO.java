package com.techlabs.app.dto;

public class AccountDTO {
	private Long id;
    private Double balance;

    public AccountDTO() {
    }

    public AccountDTO(Long id, Double balance) {
        this.id = id;
        this.balance = balance;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
    
    
}
