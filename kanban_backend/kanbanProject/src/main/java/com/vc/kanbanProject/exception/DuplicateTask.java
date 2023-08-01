package com.vc.kanbanProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN,value = HttpStatus.FORBIDDEN, reason = "Duplicate Task Name")
public class DuplicateTask extends Exception{
}
