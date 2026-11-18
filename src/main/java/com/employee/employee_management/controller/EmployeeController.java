package com.employee.employee_management.controller;


import com.employee.employee_management.entity.Employee;
import com.employee.employee_management.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.DeleteMapping;

import com.employee.employee_management.dto.EmployeeResponseDTO;

import org.springframework.web.bind.annotation.RequestParam;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
// post api
@PostMapping("/employees")
public EmployeeResponseDTO addEmployee(@Valid @RequestBody Employee employee) {
    return employeeService.addEmployee(employee);
}
    // Get Api
    @GetMapping("/employees")
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }


    @GetMapping("/employees/{id}")
    public EmployeeResponseDTO getEmployeeById(@PathVariable Long id) {
  return employeeService.getEmployeeById(id);
    }

    @PutMapping("/employees/{id}")
    public EmployeeResponseDTO updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }


    @DeleteMapping("/employees/{id}")
public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);

}

    @GetMapping("/employees/page")
    public Page<EmployeeResponseDTO> getEmployees(Pageable pageable) {
        return employeeService.getEmployees(pageable);
    }
    @GetMapping("/employees/search")
    public List<EmployeeResponseDTO> searchEmployees(
            @RequestParam String name) {
        return employeeService.searchEmployees(name);
    }

    @GetMapping("/employees/sort")
    public List<EmployeeResponseDTO> sortEmployees(
            @RequestParam String field,
            @RequestParam(defaultValue = "asc") String direction) {

        org.springframework.data.domain.Sort sort =
                direction.equalsIgnoreCase("desc")
                        ? org.springframework.data.domain.Sort.by(field).descending()
                        : org.springframework.data.domain.Sort.by(field).ascending();

        return employeeService.getEmployees(
                org.springframework.data.domain.PageRequest.of(0, 100, sort)
        ).getContent();
    }

}
