package com.techlabs.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int employeeId;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Column(name = "name")
    private String eName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String eEmail;

    @NotBlank(message = "Designation is mandatory")
    @Column(name = "designation")
    private String eDesignation;

    @NotNull(message = "Salary is mandatory")
    @Column(name = "salary")
    private double eSalary;

    @NotNull(message = "Active status is mandatory")
    @Column(name = "active")
    private boolean eActive;

    public Employee() {
    }

    public Employee(int employeeId, String eName, String eEmail, String eDesignation, double eSalary, boolean eActive) {
        this.employeeId = employeeId;
        this.eName = eName;
        this.eEmail = eEmail;
        this.eDesignation = eDesignation;
        this.eSalary = eSalary;
        this.eActive = eActive;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String geteEmail() {
        return eEmail;
    }

    public void seteEmail(String eEmail) {
        this.eEmail = eEmail;
    }

    public String geteDesignation() {
        return eDesignation;
    }

    public void seteDesignation(String eDesignation) {
        this.eDesignation = eDesignation;
    }

    public double geteSalary() {
        return eSalary;
    }

    public void seteSalary(double eSalary) {
        this.eSalary = eSalary;
    }

    public boolean iseActive() {
        return eActive;
    }

    public void seteActive(boolean eActive) {
        this.eActive = eActive;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", eName='" + eName + '\'' +
                ", eEmail='" + eEmail + '\'' +
                ", eDesignation='" + eDesignation + '\'' +
                ", eSalary=" + eSalary +
                ", eActive=" + eActive +
                '}';
    }
}