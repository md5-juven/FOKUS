package com.vc.UserAuthentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, code = HttpStatus.UNPROCESSABLE_ENTITY,reason = "Password Incorrect")
public class IncorrectPasswordException extends Exception{
}
