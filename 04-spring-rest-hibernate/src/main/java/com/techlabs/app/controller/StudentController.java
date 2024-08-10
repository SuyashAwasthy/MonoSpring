package com.techlabs.app.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.entity.Student;
import com.techlabs.app.exception.StudentErrorResponse;
import com.techlabs.app.exception.StudentNotFoundException;
import com.techlabs.app.service.StudentService;

@RestController
public class StudentController {
	
//	private studentService studentService;

	private StudentService studentService;
	
	public StudentController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}

	@GetMapping("/students")
	public ResponseEntity<List<Student>> getStudents() {
		
		List<Student> studentList = studentService.getAllStudents();
		return new ResponseEntity<List<Student>>(studentList,HttpStatus.OK);
//		return studentList;
		
//		Student student = new Student("A","B","A@B");
//		Student student = new Student("Suyash","Awasthy","suyash@awasthy");		
//		List<Student> studentList = new ArrayList<>();
//		studentList.add(new Student("Rachit","Singh","rachit@singh"));
//		studentList.add(new Student("Shivansh","Sondhi","shivansh@sondhi"));
//
//		return studentList;
//		return "hello";
	}
	
	@GetMapping("/students/{sid}")
	public Student getStudentById(@PathVariable(name="sid") int id) {
		Student student = studentService.getStudentByID(id);
		if(student==null) {
			throw new StudentNotFoundException("Student not found");
		}
		return student;
		
	}
	
	@PostMapping("/students")
	public ResponseEntity<Student> addStudent(@RequestBody Student student) {
		Student addedStudent = studentService.saveAndUpdate(student);
		return new ResponseEntity<Student>(addedStudent,HttpStatus.CREATED);
//		return addedStudent;
	}
	
	@PutMapping("/students")
	public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
		Student tempStudent = studentService.getStudentByID(student.getId());
		if(tempStudent==null) {
			throw new StudentNotFoundException("Student with id:"+student.getId()+"not found");
//			return updatedstudent;	
		}
		Student updatedstudent = studentService.saveAndUpdate(student);
		return new ResponseEntity<Student>(updatedstudent,HttpStatus.OK);
//		System.out.println("Student does not exist");
//		return null;
		
	}
	
	@DeleteMapping("/students/{sid}")
	public ResponseEntity<HttpStatus> deleteStudent(@PathVariable(name="sid") int id) {
		Student student = studentService.getStudentByID(id);
		
		if(student==null) {
			throw new StudentNotFoundException("Student with id: "+id+" is not found");
		}
		
		studentService.deleteStudent(student);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	//	return new ResponseEntity<Student>();
		
	}
	
//	@ExceptionHandler
//	public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException ex){
//		StudentErrorResponse error = new StudentErrorResponse();
//	
//		error.setStatus(HttpStatus.NOT_FOUND.value());
//		error.setMessage(ex.getMessage());
//		error.setTimeStamp(LocalDateTime.now());
//		
//		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
//	}
//	
//	@ExceptionHandler
//	public ResponseEntity<StudentErrorResponse> handleException(Exception ex){
//		System.out.println("Exception handler called");
//		
//		StudentErrorResponse error = new StudentErrorResponse();
//	
//		error.setStatus(HttpStatus.NOT_FOUND.value());
//		error.setMessage(ex.getMessage());
//		error.setTimeStamp(LocalDateTime.now());
//		
//		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
//	}
	
}
