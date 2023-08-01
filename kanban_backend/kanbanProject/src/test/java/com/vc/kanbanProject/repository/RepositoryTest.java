package com.vc.kanbanProject.repository;


import com.vc.kanbanProject.domain.Project;
import com.vc.kanbanProject.domain.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class RepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;
    private Project project;
    private Task task;

    @BeforeEach
    public void setUp(){
        Date date = new Date();
        List<Task> list = new ArrayList<>();
        task = new Task("Task1","Productivity","To-Do","Low");
        list.add(task);
        project = new Project(101,"TEAM-LEASE","Update All the Methods", date, list, null, "xyz.com");
    }

    @AfterEach
    public void tearDown(){
        project = null;
        task = null;
    }

    @Test
    @DisplayName("Saving Details in Repository [TRUE]")
    public void checkSaveDetailsReturnTrue(){
        projectRepository.save(project);
        Project project1 = projectRepository.findById(project.getProject_id());
        System.out.println(project1);
        assertNotNull(project1);
        assertEquals(project.getProject_id(), project1.getProject_id());
    }

    @Test
    @DisplayName("Saving Details in Repository [FALSE]")
    public void checkSaveDetailsReturnFalse(){
        projectRepository.save(project);
        Project project1 = projectRepository.findById(1);
        System.out.println(project1);
        assertNull(project1);
        assertNotEquals(project.getProject_id(), 0);
    }

    @Test
    @DisplayName("Delete Details From Repository [TRUE]")
    public void checkForDeleteDetailsTrue(){
        projectRepository.save(project);
        projectRepository.deleteById(project.getProject_id());
        assertEquals(projectRepository.findById(project.getProject_id()), null);
    }

    @Test
    @DisplayName("Delete Details From Repository [FALSE]")
    public void checkForDeleteDetailsFalse(){
        projectRepository.save(project);
        projectRepository.deleteById(project.getProject_id());
        assertNotEquals(projectRepository.findById(project.getProject_id()), 101);
    }
}
