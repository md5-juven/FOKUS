package com.vc.UserAuthentication.service;

import com.vc.UserAuthentication.domain.Employee;
import com.vc.UserAuthentication.exception.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public interface EmployeeService {

    Employee registerEmployee(Employee employee) throws EmployeeAlreadyExists, NoSuchAlgorithmException;
    Employee findByEmailAndPassword(String email, String password) throws EmployeeNotFound, NoSuchAlgorithmException, IncorrectPasswordException, EmployeeNotVerified;

    String updateResetPassToken(String email) throws EmployeeNotFound, NoSuchAlgorithmException;
    Employee getEmployeeByResetPass(String token) throws TokenExpired;

    String resetPassword(String token, Employee employee) throws NoSuchAlgorithmException, TokenExpired;
    TreeSet<String> emailStartingWith(String email);
    Map<String,String> titleUpdate(String title, String email);
    String passwordVerify(Employee employee) throws NoSuchAlgorithmException;
    Map<String,String> profileUpdate(Employee employee) throws NoSuchAlgorithmException;

}
