package com.vc.UserAuthentication.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.vc.UserAuthentication.domain.Employee;
import com.vc.UserAuthentication.exception.*;
import com.vc.UserAuthentication.repository.EmployeeRepository;
import com.vc.UserAuthentication.security.SecurityTokenGenerator;
import com.vc.UserAuthentication.service.EmployeeService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;


@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;
    private SecurityTokenGenerator securityTokenGenerator;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeService employeeService, SecurityTokenGenerator securityTokenGenerator,
                              EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.securityTokenGenerator = securityTokenGenerator;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@RequestBody Employee employee) throws EmployeeAlreadyExists, NoSuchAlgorithmException {
        employee.setTitle("FREE");
        return new ResponseEntity<>(employeeService.registerEmployee(employee), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Employee employee) throws EmployeeNotFound, NoSuchAlgorithmException, IncorrectPasswordException, EmployeeNotVerified {
        Map<String,String> token = null;
        Employee employee1 =  employeeService.findByEmailAndPassword(employee.getEmail(),employee.getPassword());
        token = securityTokenGenerator.createToken(employee1);
        return new ResponseEntity<>(token,HttpStatus.OK);
    }

    @PostMapping("/forget_password")
    private ResponseEntity<?> createForgetPassToken(HttpServletRequest request) throws EmployeeNotFound, NoSuchAlgorithmException {
        String email = request.getParameter("email");
        employeeService.updateResetPassToken(email);
        return new ResponseEntity<>(employeeService.updateResetPassToken(email),HttpStatus.OK);
    }

    @PostMapping("/reset_password/forget")
    private String updatePassword(HttpServletRequest request,@RequestBody Employee employee) throws NoSuchAlgorithmException, TokenExpired {
        String token = request.getParameter("key");
        employeeService.resetPassword(token,employee);
        return "password successfully updated";
    }
    @GetMapping("/reset_password/forget")
    private ResponseEntity<?> verifyToken(HttpServletRequest request) throws TokenExpired {
        String token = request.getParameter("key");
        return  new ResponseEntity<>(employeeService.getEmployeeByResetPass(token),HttpStatus.OK);
    }
    @GetMapping("/requiredMail")
    private ResponseEntity<?> getEmailsStartWith(HttpServletRequest request){
        String emailStr = request.getParameter("StartWith");
        return new ResponseEntity<>(employeeService.emailStartingWith(emailStr),HttpStatus.OK);
    }

    @PutMapping("/feature/{account}/upgrade/{status}")
    public ResponseEntity<?> updateTitle(@PathVariable String account,@PathVariable String status){

        return new ResponseEntity<>(employeeService.titleUpdate(status,account),HttpStatus.ACCEPTED);
    }
    @PostMapping("/account/verify/password")
    public String verifyPassword(@RequestBody Employee employee,HttpServletRequest request) throws NoSuchAlgorithmException {
        System.out.println(employee);
        Claims claims = (Claims) request.getAttribute("claims");
        String email = claims.getSubject();
        if(email.equals(String.valueOf(employee.getEmail()))){
            return employeeService.passwordVerify(employee);
        }else
            return "key-failed";
    }
    @PutMapping("/account/update/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Employee employee) throws NoSuchAlgorithmException {
        return new ResponseEntity<>(employeeService.profileUpdate(employee),HttpStatus.OK);
    }
}
