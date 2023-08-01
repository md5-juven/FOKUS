package com.vc.UserAuthentication.security;

import com.vc.UserAuthentication.domain.Employee;

import java.util.Map;

public interface SecurityTokenGenerator {

    Map<String,String> createToken(Employee employee);
 }
