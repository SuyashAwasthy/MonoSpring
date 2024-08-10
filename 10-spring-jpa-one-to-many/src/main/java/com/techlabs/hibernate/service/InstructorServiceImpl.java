package com.techlabs.hibernate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techlabs.hibernate.entity.Course;
import com.techlabs.hibernate.entity.Instructor;
import com.techlabs.hibernate.repository.CourseRepository;
import com.techlabs.hibernate.repository.InstructorRepository;

@Service
public class InstructorServiceImpl implements InstructorService{

	private InstructorRepository instructorRepository;
	private CourseRepository courseRepository;
	
	public InstructorServiceImpl(InstructorRepository instructorRepository, CourseRepository courseRepository) {
		super();
		this.instructorRepository = instructorRepository;
		this.courseRepository = courseRepository;
	}

	@Override
	public Instructor saveInstructor(Instructor instructor) {
		return instructorRepository.save(instructor);
	}

	@Override
	public List<Instructor> findAllInstructors() {
		return instructorRepository.findAll();
	}

	@Override
	public Instructor findById(int id) {
		Optional<Instructor> optional = instructorRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get()
;		}
		return null;
	}

	@Override
	public void deleteById(int id) {
		instructorRepository.deleteById(id);
	}

	@Override
	public Instructor addCourseToInstructor(int instructorId, int courseId) {
		Instructor instructor = instructorRepository.findById(instructorId).orElse(null);
		if(instructor!=null) {
			Course course = courseRepository.findById(courseId).orElse(null);
			if(course!=null) {
				if(course.getInstructor()==null) {
					instructor.addCourse(course);
					course.setInstructor(instructor);
					instructorRepository.save(instructor);
					return instructor;
				}
				else {
					System.out.println("Instructor already assigned to the course.");
				}
			}
		}
		return null;
	}
	
	  @Override
	  public Instructor removeCourseFromInstructor(int instructorId, int courseId) {
	    Instructor instructor = instructorRepository.findById(instructorId).orElse(null);
	    if (instructor != null) {
	      Course course = courseRepository.findById(courseId).orElse(null);
	      if (course != null) {
	        instructor.removeCourse(course);
	        courseRepository.save(course);
	        return instructorRepository.save(instructor);
	      } else {
	        System.out.println("Instructor does not teach this course");
	      }

	    }
	    return null;
	  }
	
}