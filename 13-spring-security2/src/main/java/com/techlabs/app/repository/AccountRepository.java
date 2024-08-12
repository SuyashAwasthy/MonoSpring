package com.techlabs.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Customer;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	@Query("select sum(a.balance) from Account a where a.customer = ?1")
	double getTotalBalance(Customer customer);


}
