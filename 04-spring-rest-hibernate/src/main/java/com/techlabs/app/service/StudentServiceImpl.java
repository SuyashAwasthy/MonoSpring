package com.techlabs.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techlabs.app.dao.StudentDao;
import com.techlabs.app.entity.Student;

@Service
public class StudentServiceImpl implements StudentService{

	private StudentDao studentDao;
	
	public StudentServiceImpl(StudentDao studentDao) {
		super();
		this.studentDao = studentDao;
	}
	
	@Override
	public List<Student> getAllStudents() {
		return studentDao.getAllStudents();
	}

	@Override
	public Student getStudentByID(int id) {
		return studentDao.getStudentByID(id);
	}

	@Override
	public Student saveAndUpdate(Student student) {
		return studentDao.saveAndUpdate(student);
	}

	@Override
	public void deleteStudent(Student student) {
		studentDao.deleteStudent(student);
	}

}
