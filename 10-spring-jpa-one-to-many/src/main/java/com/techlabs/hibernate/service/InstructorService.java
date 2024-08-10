package com.techlabs.hibernate.service;

import java.util.List;

import com.techlabs.hibernate.entity.Instructor;

public interface InstructorService {

	List<Instructor> findAllInstructors();

	Instructor saveInstructor(Instructor instructor);

	Instructor findById(int id);

	void deleteById(int id);

	Instructor addCourseToInstructor(int instructorId, int courseId);

	Instructor removeCourseFromInstructor(int instructorId, int courseId);

}
