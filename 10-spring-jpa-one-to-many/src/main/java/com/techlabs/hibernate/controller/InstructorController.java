package com.techlabs.hibernate.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.hibernate.entity.Instructor;
import com.techlabs.hibernate.service.InstructorService;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

	private InstructorService instructorService;

	public InstructorController(InstructorService instructorService) {
		super();
		this.instructorService = instructorService;
	}

	@GetMapping
	public List<Instructor> getAllInstructors() {
		return instructorService.findAllInstructors();
	}

	@PostMapping("/add")
	public Instructor addNewInstructor(@RequestBody Instructor instructor) {
		return instructorService.saveInstructor(instructor);
	}

	@GetMapping("/id/{id}")
	public Instructor getInstructorByID(@PathVariable int id) {
		return instructorService.findById(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteInstructorByID(@PathVariable int id) {
		instructorService.deleteById(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@PutMapping("/{instId}/course/{courseId}")
	public ResponseEntity<Instructor> addCourseToInstructor(@PathVariable(name = "instId") int instructorId,
			@PathVariable(name = "courseId") int courseId) {
		Instructor instructor = instructorService.addCourseToInstructor(instructorId, courseId);
		return new ResponseEntity<Instructor>(instructor, HttpStatus.OK);
	}
	
	@PutMapping("/{instId}/remove-course/{courseId}")
	public ResponseEntity<Instructor> removeCourseFromInstructor(@PathVariable(name = "instId") int instructorId,
			@PathVariable(name = "courseId") int courseId) {
		Instructor instructor = instructorService.removeCourseFromInstructor(instructorId, courseId);
		return new ResponseEntity<Instructor>(instructor, HttpStatus.OK);
	}

	@PutMapping("/update")
	public Instructor updateInstructor(@RequestBody Instructor instructor) {
		Instructor tempInstructor = instructorService.findById(instructor.getId());
		if (tempInstructor != null) {
			return instructorService.saveInstructor(instructor);
		}
		return null;
	}

}