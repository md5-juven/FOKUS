package com.vc.UserAuthentication.repository;

import com.vc.UserAuthentication.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String>{
    Employee findByEmail(String email);
    Employee findByResetPassToken(String token);

    List<Employee> findByEmailStartingWith(String emailStr);

}
