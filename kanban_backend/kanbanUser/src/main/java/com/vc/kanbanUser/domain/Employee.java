package com.vc.kanbanUser.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Employee {

    @Id
    private String email;
    @Transient
    private String password;
    private String emp_id;
//    @Column(nullable = false,unique = true)
    private String username;
    private String profile_pic;
    private String title;
    List<Integer> project_id_list;

    public Employee(String email, String password, String emp_id, String username,
                    String title,String profile_pic) {
        this.email = email;
        this.emp_id = emp_id;
        this.username = username;
        this.password = password;
        this.title = title;
        this.profile_pic=profile_pic;
    }

    public Employee(String email, String password, String emp_id, String username, String title) {
        this.email = email;
        this.password = password;
        this.emp_id = emp_id;
        this.username = username;
        this.title = title;
    }
}
