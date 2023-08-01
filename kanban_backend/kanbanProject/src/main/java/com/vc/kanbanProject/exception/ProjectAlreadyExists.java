package com.vc.kanbanProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Project Already Exists")
public class ProjectAlreadyExists extends Exception{
}
