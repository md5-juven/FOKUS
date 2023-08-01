package com.vc.UserAuthentication.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.springframework.data.annotation.Transient;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Employee {
    private String emp_id;
    @Id
    private String email;
    private String password;
    private  String username;
    private byte[] salt;
    private String title;
    @Column(name = "reset_pass_token")
    private String resetPassToken;
}
