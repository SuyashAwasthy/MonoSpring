package com.techlabs.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.dto.PersonDTO;
import com.techlabs.app.dto.ResponseMessageDTO;
import com.techlabs.app.entity.Person;
import com.techlabs.app.service.PersonService;

@RestController
@RequestMapping("/api/person")
public class PersonController {
	@Autowired
    private PersonService personService;

    @PostMapping("/register")
    public ResponseEntity<ResponseMessageDTO> register(@RequestBody PersonDTO personDTO) {
        personService.registerPerson(personDTO);
        return new ResponseEntity<>(new ResponseMessageDTO("Registration successful"), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        Person person = personService.getPersonById(id);
        PersonDTO personDTO = new PersonDTO(person.getId(), person.getUsername(), person.getEmail());
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = personService.getAllPersons();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessageDTO> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return new ResponseEntity<>(new ResponseMessageDTO("Person deleted successfully"), HttpStatus.OK);
    }
}
