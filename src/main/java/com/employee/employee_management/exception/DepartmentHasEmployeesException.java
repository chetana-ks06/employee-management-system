package com.employee.employee_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DepartmentHasEmployeesException extends RuntimeException {

    public DepartmentHasEmployeesException(String message) {
        super(message);
    }
}
