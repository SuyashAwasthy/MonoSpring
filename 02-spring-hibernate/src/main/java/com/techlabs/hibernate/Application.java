package com.techlabs.hibernate;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.techlabs.hibernate.dao.StudentDao;
import com.techlabs.hibernate.entity.Student;

@SpringBootApplication
public class Application implements CommandLineRunner{

	private StudentDao studentDao;
	
	public Application(StudentDao studentDao) {
		super();
		this.studentDao = studentDao;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello World");
	//	addStudent();
	//	getAllStudents();
	//	getStudentById();
	//	getStudentByFirstName();
	//	getStudentsByFirstNameAndLastName();
	//	updateStudent();
		updateStudentWithoutMerge();
	//	deleteStudent();
	}
	
	private void updateStudentWithoutMerge() {
		Student student = new Student(20,"rohit","sharma","rohit@sharma.com");
		studentDao.updateStudent(student);
	}

	private void deleteStudent() {
		studentDao.deleteStudent(13);
	}

	private void updateStudent() {
		Student student = new Student(20,"virat","kohli","virat@kohli.com");
		studentDao.updateStudent(student);
	}

	private void getStudentsByFirstNameAndLastName() {
		List<Student> studentList = studentDao.getStudentsByFirstNameAndLastName("Suyash","Awasthy");
		System.out.println(studentList);
	}

	private void getStudentByFirstName() {
		List<Student> studentList = studentDao.getStudentByFirstName("Suyash");
		System.out.println(studentList);
	}

	private void getStudentById() {
		Student student = studentDao.getStudentById(25);
		if(student!=null)
			System.out.println(student);
		else
			System.out.println("Student not found");
	}

	private void getAllStudents() {
		List<Student> studentList = studentDao.getAllStudents();
		for(Student s : studentList) {
			System.out.println(s);
		}
	}

	private void addStudent() {
		Student student = new Student("Suyash","Awasthy","suyash@gmail.com");
		studentDao.save(student);
	}

}
