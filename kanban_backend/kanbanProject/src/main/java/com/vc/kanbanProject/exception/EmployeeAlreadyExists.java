package com.vc.kanbanProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Employee with given credentials already exists")
public class EmployeeAlreadyExists extends Throwable{
}
