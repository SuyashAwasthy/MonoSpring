package com.techlabs.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.techlabs.app.entity.Student;
import com.techlabs.app.util.PagedResponse;

public interface StudentService {

//	public List<Student> getAllStudents();

	public Student getStudentByID(int id);

	public Student saveAndUpdate(Student student);

	public void deleteStudent(Student student);

	PagedResponse<Student> getAllStudents(int page, int size, String sortBy, String direction);
	
}
