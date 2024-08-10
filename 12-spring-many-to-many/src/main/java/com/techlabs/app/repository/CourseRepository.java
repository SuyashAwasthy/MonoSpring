package com.techlabs.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.app.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{

	List<Course> findByFeeLessThan(double fee);
	
}
