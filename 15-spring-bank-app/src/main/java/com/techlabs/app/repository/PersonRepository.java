package com.techlabs.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.app.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
