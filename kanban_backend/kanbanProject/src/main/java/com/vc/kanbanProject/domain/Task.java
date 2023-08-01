package com.vc.kanbanProject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {
    @Column(unique = true)
    private String name;
    private String description;
    private String status;
    private String priority;
    private List<User> memberList;

    //not using this email property
    private String email;

    private String lastChangedBy;

    public Task(String name, String description, String status, String priority) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.priority = priority;
    }
}
