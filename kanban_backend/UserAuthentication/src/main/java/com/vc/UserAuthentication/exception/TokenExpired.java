package com.vc.UserAuthentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, code = HttpStatus.UNPROCESSABLE_ENTITY,reason = "token expired")
public class TokenExpired extends Exception{
}
