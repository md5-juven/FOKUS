package com.vc.kanbanUser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Employee with given credentials not found")
public class EmployeeNotFound  extends  Throwable{
}