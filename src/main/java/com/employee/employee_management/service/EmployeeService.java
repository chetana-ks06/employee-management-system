
package com.employee.employee_management.service;

import com.employee.employee_management.repository.EmployeeRepository;

import com.employee.employee_management.entity.Employee;
import com.employee.employee_management.exception.EmployeeNotFoundException;
import com.employee.employee_management.dto.EmployeeResponseDTO;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.employee.employee_management.entity.Department;

import com.employee.employee_management.service.DepartmentService;



import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentService departmentService;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentService departmentService) {

        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
    }

    private String getDepartmentName(Employee employee) {
        return employee.getDepartmentEntity() == null
                ? null
                : employee.getDepartmentEntity().getName();
    }
    public EmployeeResponseDTO addEmployee(Employee employee) {

        Long departmentId = employee.getDepartmentEntity().getId();

        Department department = departmentService.getDepartmentById(departmentId);

        employee.setDepartmentEntity(department);

        Employee savedEmployee = employeeRepository.save(employee);

        return new EmployeeResponseDTO(
                savedEmployee.getId(),
                savedEmployee.getName(),
                savedEmployee.getEmail(),
                getDepartmentName(savedEmployee),
                savedEmployee.getSalary()
        );
    }

    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employee -> new EmployeeResponseDTO(
                        employee.getId(),
                        employee.getName(),
                        employee.getEmail(),
                        getDepartmentName(employee),
                        employee.getSalary()
                ))
                .toList();
    }

    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee not found with ID: " + id));

        return new EmployeeResponseDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                getDepartmentName(employee),
                employee.getSalary()
        );
    }

    public EmployeeResponseDTO updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee not found with ID: " + id));

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        Long departmentId = updatedEmployee.getDepartmentEntity().getId();

        Department department = departmentService.getDepartmentById(departmentId);

        existingEmployee.setDepartmentEntity(department);
        existingEmployee.setSalary(updatedEmployee.getSalary());

        Employee savedEmployee = employeeRepository.save(existingEmployee);

        return new EmployeeResponseDTO(
                savedEmployee.getId(),
                savedEmployee.getName(),
                savedEmployee.getEmail(),
                getDepartmentName(savedEmployee),
                savedEmployee.getSalary()
        );
    }

    public void deleteEmployee(Long id) {
        employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee not found with ID: " + id));

        employeeRepository.deleteById(id);
    }

    public Page<EmployeeResponseDTO> getEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(employee -> new EmployeeResponseDTO(
                        employee.getId(),
                        employee.getName(),
                        employee.getEmail(),
                        getDepartmentName(employee),
                        employee.getSalary()
                ));
    }

    public List<EmployeeResponseDTO> searchEmployees(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(employee -> new EmployeeResponseDTO(
                        employee.getId(),
                        employee.getName(),
                        employee.getEmail(),
                        getDepartmentName(employee),
                        employee.getSalary()
                ))
                .toList();


    }
}
