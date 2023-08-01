package com.vc.kanbanProject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Project {

    @Id
    private int project_id;
    private String name;
    private String description;
    private Date startDate;
    private Date duration;
    private List<Task> taskList;
    private List<User> assigned_empl;
    private String archive;
    private Date archiveData;
    private String email; // to store email of person creating projec

    public Project(int project_id, String name, String description, Date duration, List<Task> taskList, String email,String archive) {
        this.project_id = project_id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.taskList = taskList;
        this.email = email;
        this.archive =archive;
    }
}
