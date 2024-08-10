package com.techlabs.app.controller;

import com.techlabs.app.dto.EmployeeRequestDTO;
import com.techlabs.app.dto.EmployeeResponseDTO;
import com.techlabs.app.service.EmployeeService;
import com.techlabs.app.util.PagedResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/employees")
	public ResponseEntity<PagedResponse<EmployeeResponseDTO>> getAllEmployees(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(defaultValue = "employeeId") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		System.out.println(page);
		System.out.println(size);

		PagedResponse<EmployeeResponseDTO> pagedResponseDTOList = employeeService.getAllEmployees(page, size, sortBy,
				direction);
		return new ResponseEntity<>(pagedResponseDTOList, HttpStatus.OK);
	}

	@GetMapping("/employees/{eid}")
	public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable(name = "eid") int employeeId) {
		EmployeeResponseDTO responseDTO = employeeService.getEmployeeById(employeeId);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PostMapping("/employees")
	public ResponseEntity<EmployeeResponseDTO> addEmployee(@Valid @RequestBody EmployeeRequestDTO requestDTO) {
		requestDTO.setId(0);
		EmployeeResponseDTO responseDTO = employeeService.addEmployee(requestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

	@PutMapping("/employees")
	public ResponseEntity<EmployeeResponseDTO> updateEmployee(@Valid @RequestBody EmployeeRequestDTO requestDTO) {
		EmployeeResponseDTO responseDTO = employeeService.updateEmployee(requestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@DeleteMapping("/employees/{eid}")
	public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable(name = "eid") int employeeId) {
		employeeService.deleteEmployee(employeeId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/employees/name/{name}")
	public ResponseEntity<List<EmployeeResponseDTO>> getEmployeesByName(@PathVariable(name = "name") String empName) {
		List<EmployeeResponseDTO> responseDTOList = employeeService.getEmployeesByName(empName);
		return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
	}

	@GetMapping("/employees/email/{email}")
	public ResponseEntity<List<EmployeeResponseDTO>> getEmployeesByEmail(
			@PathVariable(name = "email") String empEmail) {
		List<EmployeeResponseDTO> responseDTOList = employeeService.getEmployeesByEmail(empEmail);
		return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
	}

	@GetMapping("/employees/active/{active}")
	public ResponseEntity<List<EmployeeResponseDTO>> getActiveEmployees(
			@PathVariable(name = "active") boolean isActive) {
		List<EmployeeResponseDTO> responseDTOList = employeeService.getAllActiveEmployees(isActive);

		return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
	}

	@GetMapping("/employees/charName/{charName}")
	public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployeesStartingWithChar(
			@PathVariable(name = "charName") String charName) {
		List<EmployeeResponseDTO> responseDTOList = employeeService.getEmployeesStartingWithChar(charName);
		return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
	}

	@GetMapping("/employees/salary/{salary}/designation/{designation}")
	public ResponseEntity<List<EmployeeResponseDTO>> getEmployeesWithSalaryAndDesignation(
			@PathVariable(name = "salary") double salary, @PathVariable(name = "designation") String designation) {
		List<EmployeeResponseDTO> responseDTOList = employeeService.getEmployeesWithSalaryAndDesignation(salary,
				designation);
		return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
	}

	@GetMapping("/employees/salary/{startSalary}-{endSalary}")
	public ResponseEntity<List<EmployeeResponseDTO>> getEmployeesWithSalaryRange(
			@PathVariable(name = "startSalary") double startSalary,
			@PathVariable(name = "endSalary") double endSalary) {
		List<EmployeeResponseDTO> responseDTOList = employeeService.getEmployeesWithSalaryRange(startSalary, endSalary);
		return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
	}

	@GetMapping("/employees/active/{isActive}/salary/{salary}")
	public ResponseEntity<List<EmployeeResponseDTO>> getEmployeesActiveAndSalary(
			@PathVariable(name = "isActive") boolean isActive, @PathVariable(name = "salary") double salary) {
		List<EmployeeResponseDTO> responseDTOList = employeeService.getEmployeesActiveAndSalary(isActive, salary);
		return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
	}

	@GetMapping("/employees/count-active/{isActive}")
	public ResponseEntity<Integer> getCountEmployeesActive(@PathVariable(name = "isActive") boolean isActive) {
		int employeeCount = employeeService.getCountEmployeesActive(isActive);
		return new ResponseEntity<>(employeeCount, HttpStatus.OK);
	}

	@GetMapping("/employees/count-designation/{designation}")
	public ResponseEntity<Integer> getCountEmployeesDesignation(
			@PathVariable(name = "designation") String designation) {
		int employeeCount = employeeService.getCountEmployeesDesignation(designation);
		return new ResponseEntity<>(employeeCount, HttpStatus.OK);
	}

}