package com.techlabs.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.techlabs.app.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class StudentDaoImpl implements StudentDao{

	private EntityManager entityManager;
	
	public StudentDaoImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public List<Student> getAllStudents() {
		TypedQuery<Student> query = entityManager.createQuery("select s from Student s", Student.class);
		return query.getResultList();
	}

	@Override
	public Student getStudentByID(int id) {
		Student student = entityManager.find(Student.class, id);
		return student;
	}

	@Override
	@Transactional
	public Student saveAndUpdate(Student student) {
		return entityManager.merge(student);
	}

	@Override
	@Transactional
	public void deleteStudent(Student student) {
		entityManager.remove(student);
	}
	
	
	
}
