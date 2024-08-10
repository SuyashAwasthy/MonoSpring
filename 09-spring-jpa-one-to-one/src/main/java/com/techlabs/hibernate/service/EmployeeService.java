package com.techlabs.hibernate.service;

import java.util.List;

import com.techlabs.hibernate.entity.Employee;

public interface EmployeeService {

	public Employee saveEmployee(Employee employee);
	
	public List<Employee> findAllEmployees();

	public Employee findById(int id);

	void deleteById(int id);
}
