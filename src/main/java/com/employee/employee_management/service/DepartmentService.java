
package com.employee.employee_management.service;

import com.employee.employee_management.entity.Department;
import com.employee.employee_management.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import com.employee.employee_management.exception.DepartmentHasEmployeesException;

import com.employee.employee_management.exception.DepartmentNotFoundException;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(
                        "Department not found with ID: " + id));
    }

    public void deleteDepartment(Long id) {

        Department department = getDepartmentById(id);

        if (departmentRepository.existsByIdAndEmployeesIsNotEmpty(id)) {
            throw new DepartmentHasEmployeesException(
                    "Cannot delete department. Employees are still assigned to it."
            );
        }

        departmentRepository.delete(department);
    }
}
