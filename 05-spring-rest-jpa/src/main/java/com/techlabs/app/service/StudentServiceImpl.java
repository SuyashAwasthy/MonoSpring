package com.techlabs.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.techlabs.app.entity.Student;
import com.techlabs.app.repository.StudentRepository;
import com.techlabs.app.util.PagedResponse;

@Service
public class StudentServiceImpl implements StudentService {

	private StudentRepository studentRepository;

	public StudentServiceImpl(StudentRepository studentRepository) {
		super();
		this.studentRepository = studentRepository;
	}

	@Override
	public PagedResponse<Student> getAllStudents(int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Student> page1 = studentRepository.findAll(pageable);
		List<Student> students = page1.getContent();
		return new PagedResponse<Student>(page1.getContent(), page1.getNumber(), page1.getSize(),
				page1.getTotalElements(), page1.getTotalPages(), page1.isLast());
		// return students;
		// return studentRepository.findAll();
	}

	@Override
	public Student getStudentByID(int id) {
		return studentRepository.findById(id).orElse(null);
	}

	@Override
	public Student saveAndUpdate(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public void deleteStudent(Student student) {
		studentRepository.delete(student);
	}

}
