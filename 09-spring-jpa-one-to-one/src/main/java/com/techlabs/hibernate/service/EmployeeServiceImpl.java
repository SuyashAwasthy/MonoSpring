package com.techlabs.hibernate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techlabs.hibernate.entity.Employee;
import com.techlabs.hibernate.repository.AddressRepository;
import com.techlabs.hibernate.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	private EmployeeRepository employeeRepository;
	private AddressRepository addressRepository;
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository, AddressRepository addressRepository) {
		super();
		this.employeeRepository = employeeRepository;
		this.addressRepository = addressRepository;
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> findAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee findById(int id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get()
;		}
		return null;
	}

	@Override
	public void deleteById(int id) {
		employeeRepository.deleteById(id);
	}

}
