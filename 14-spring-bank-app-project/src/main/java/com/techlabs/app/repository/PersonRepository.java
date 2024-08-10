package com.techlabs.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.app.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{
    Optional<Person> findByUsername(String username);

}
