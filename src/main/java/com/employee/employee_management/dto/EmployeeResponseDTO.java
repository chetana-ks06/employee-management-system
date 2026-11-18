package com.employee.employee_management.dto;

public class EmployeeResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String department;
    private double salary;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public EmployeeResponseDTO(Long id, String name, String email,
                               String department, double salary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.salary = salary;
    }
}
