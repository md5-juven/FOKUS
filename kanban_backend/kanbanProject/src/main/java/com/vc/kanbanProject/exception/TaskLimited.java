package com.vc.kanbanProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN,value = HttpStatus.FORBIDDEN, reason = "Member has maximum task")
public class TaskLimited extends Exception{
}
