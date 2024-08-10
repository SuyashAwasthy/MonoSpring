package com.techlabs.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.hibernate.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

	
}
