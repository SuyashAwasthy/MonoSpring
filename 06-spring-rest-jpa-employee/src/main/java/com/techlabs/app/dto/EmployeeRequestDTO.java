package com.techlabs.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EmployeeRequestDTO {
    public EmployeeRequestDTO() {
    }

    private int id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Designation is mandatory")
    private String designation;

    @NotNull(message = "Salary is mandatory")
    private double salary;

    @NotNull(message = "Active status is mandatory")
    private boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotBlank(message = "Name is mandatory") @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is mandatory") @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Email is mandatory") @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is mandatory") @Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Designation is mandatory") String getDesignation() {
        return designation;
    }

    public void setDesignation(@NotBlank(message = "Designation is mandatory") String designation) {
        this.designation = designation;
    }

    @NotNull(message = "Salary is mandatory")
    public double getSalary() {
        return salary;
    }

    public void setSalary(@NotNull(message = "Salary is mandatory") double salary) {
        this.salary = salary;
    }

    @NotNull(message = "Active status is mandatory")
    public boolean isActive() {
        return active;
    }

    public void setActive(@NotNull(message = "Active status is mandatory") boolean active) {
        this.active = active;
    }
}