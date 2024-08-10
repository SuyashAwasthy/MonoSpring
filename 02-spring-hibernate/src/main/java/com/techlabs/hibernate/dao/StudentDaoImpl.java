package com.techlabs.hibernate.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.techlabs.hibernate.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class StudentDaoImpl implements StudentDao{

	EntityManager entityManager;
	
	public StudentDaoImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void save(Student student) {
		this.entityManager.persist(student);
	}

	@Override
	public List<Student> getAllStudents() {
		TypedQuery<Student> query = entityManager.createQuery("select s from Student s", Student.class);
		
		List<Student> studentList = query.getResultList();
		return studentList;
	}

	@Override
	public Student getStudentById(int id) {
		Student student = entityManager.find(Student.class, id);
		return student;
	}

	@Override
	public List<Student> getStudentByFirstName(String firstName) {
		TypedQuery<Student> query = entityManager.createQuery("select s from Student s where firstName=?1",Student.class);
		query.setParameter(1, firstName);
		return query.getResultList();
	}

	@Override
	public List<Student> getStudentsByFirstNameAndLastName(String firstName, String lastName) {
		TypedQuery<Student> query = entityManager.createQuery("select s from Student s where firstName=?1 and lastName=?2",Student.class);
		query.setParameter(1, firstName);
		query.setParameter(2, lastName);
		return query.getResultList();
	}

	@Override
	@Transactional
	public void updateStudent(Student student) {
		Student student2 = entityManager.find(Student.class, student.getId());
		
		if(student2!=null) {
			this.entityManager.merge(student);
		}
		else {
			System.out.println("Student id does not exist."+student.getId());
		}
	
	}

	@Override
	@Transactional
	public void deleteStudent(int id) {
		Student student = entityManager.find(Student.class, id);
		if(student!=null) {
			this.entityManager.remove(student);
		}
		else {
			System.out.println("Student with id: " + student.getId() + "does not exist");
		}
	}

	@Override
	public void updateStudentWithoutMerge(Student student) {
		Student student2 = entityManager.find(Student.class, student.getId());
		if(student2!=null) {
		Query query = entityManager.createQuery("update Student s set s.firstName=?1,s.lastName=?,s.email=?3 where s.id=?4");
		query.setParameter(1, student2.getFirstName());
		query.setParameter(2, student2.getLastName());
		query.setParameter(3, student2.getEmail());
		query.setParameter(4, student2.getId());
		int res = query.executeUpdate();
		System.out.println(res);
		}
		else {
			System.out.println("Student with id: " + student.getId() + "does not exist");
		}
	}

}
