package com.techlabs.app.repository;

import com.techlabs.app.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByENameLike(String empName);

    List<Employee> findByEEmail(String empEmail);

    List<Employee> findByEActive(boolean isActive);

    List<Employee> findByENameStartingWith(String charName);

    List<Employee> findByESalaryGreaterThanAndEDesignationLike(double salary, String designation);

    List<Employee> findByESalaryBetween(double startSalary, double endSalary);

    List<Employee> findByEActiveAndESalary(boolean isActive, double salary);

    int countByEActive(boolean isActive);

    int countByEDesignationLike(String designation);
    
    @Query("select emp from Employee emp where emp.eName=:name")
    List<Employee> getByName(@Param("name") String empName);

    @Query("select emp from Employee emp where emp.eEmail=:email")
    List<Employee> getByEmail(@Param("email") String empEmail);

    @Query("select emp from Employee emp where emp.eActive=:active")
    List<Employee> getByActive(@Param("active") boolean isActive);

    @Query("select emp from Employee emp where emp.eName like concat(:prefix, '%')")
    List<Employee> getNameStartingWith(@Param("prefix") String charName);

    @Query("select emp from Employee emp where emp.eSalary>=:salary and emp.eDesignation=:designation")
    List<Employee> getEmployeeWithSalaryGreaterThanAndDesignation(@Param("salary") double salary,
                                                                  @Param("designation") String designation);

    @Query("select emp from Employee emp where emp.eSalary between :start and :end")
    List<Employee> getEmployeeBySalaryBetween(@Param("start") double startSalary,
                                              @Param("end") double endSalary);

    @Query("select emp from Employee emp where emp.eActive=:active and emp.eSalary=:salary")
    List<Employee> getEmployeeByActiveAndSalary(@Param("active") boolean isActive,
                                                @Param("salary") double salary);

    @Query("select count(emp) from Employee emp where emp.eActive=:active")
    int countEmployeesByActive(@Param("active") boolean isActive);

    @Query("select count(emp) from Employee emp where emp.eDesignation=:designation")
    int countEmployeesByDesignation(@Param("designation") String designation);
}