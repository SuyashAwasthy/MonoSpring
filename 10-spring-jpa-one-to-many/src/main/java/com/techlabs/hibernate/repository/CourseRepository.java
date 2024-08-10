package com.techlabs.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.hibernate.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>{

}
