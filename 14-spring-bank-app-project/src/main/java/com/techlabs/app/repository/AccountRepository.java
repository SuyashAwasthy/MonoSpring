package com.techlabs.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.app.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

}