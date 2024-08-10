package com.techlabs.hibernate.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.hibernate.entity.Employee;
import com.techlabs.hibernate.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	
	@GetMapping
	public List<Employee> getAllEmployees(){
		return employeeService.findAllEmployees();
	}
	
	@PostMapping
	public Employee addNewEmployee(@RequestBody Employee employee) {
		return employeeService.saveEmployee(employee);
	}
	
	@GetMapping("/{id}")
	public Employee getEmployeeByID(@PathVariable int id) {
		return employeeService.findById(id);
	}
	
	@DeleteMapping("/{id}")
	public void deleteEmployeeByID(@PathVariable int id) {
		employeeService.deleteById(id);
	}
	
}
