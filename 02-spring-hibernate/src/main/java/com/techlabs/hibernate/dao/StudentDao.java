package com.techlabs.hibernate.dao;

import java.util.List;

import com.techlabs.hibernate.entity.Student;

public interface StudentDao {

	public void save(Student student);

	public List<Student> getAllStudents();

	public Student getStudentById(int id);

	public List<Student> getStudentByFirstName(String string);

	public List<Student> getStudentsByFirstNameAndLastName(String string, String string2);

	public void updateStudent(Student student);

	public void deleteStudent(int id);

	public void updateStudentWithoutMerge(Student student);

}
