package com.vc.kanbanUser.proxy;

import com.vc.kanbanUser.domain.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@FeignClient(name = "user-auth",url = "localhost:8081")
public interface EmployeeProxy {

    @PostMapping("/employee/register")
    public ResponseEntity<?> registerEmployee(Employee employee);

    @PutMapping("/employee/feature/{account}/upgrade/{status}")
    public ResponseEntity<?> updateTitle(@PathVariable String account, @PathVariable String status);
    @PutMapping("/employee/account/update/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Employee employee);
}
