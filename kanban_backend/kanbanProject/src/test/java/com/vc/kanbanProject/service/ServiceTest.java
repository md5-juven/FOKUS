package com.vc.kanbanProject.service;

import com.vc.kanbanProject.domain.Project;
import com.vc.kanbanProject.domain.Task;
import com.vc.kanbanProject.exception.DuplicateTask;
import com.vc.kanbanProject.exception.ProjectAlreadyExists;
import com.vc.kanbanProject.exception.ProjectLimiter;
import com.vc.kanbanProject.exception.ProjectNotFound;
import com.vc.kanbanProject.proxy.ProjectProxy;
import com.vc.kanbanProject.repository.ProjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private KanbanServiceImpl kanbanServiceImpl;
    @InjectMocks
    private ProjectTaskServiceImpl projectTaskServiceImpl;
    private Project project;
    private Task task;

    @BeforeEach
    public void setUp(){
        Date date = new Date();
        List<Task> list = new ArrayList<>();
        task = new Task("Task1","Productivity","To-Do","Low");
        list.add(task);
        project = new Project(101,"TEAM-LEASE","Update All the Methods", date, list, "xyz.com","ARCHIVE");
    }

    @AfterEach
    public void tearDown(){
        project = null;
        task = null;
    }

    @Test
    @DisplayName("Creating a Project")
    public void checkForCreatingProject() throws ProjectAlreadyExists, ProjectLimiter {

        //when data storing in a repository whether it's returning the data
        when(projectRepository.save(any())).thenReturn(project);
        // Calling the createProject method
        Project project1 = kanbanServiceImpl.createProject(project,"FREE");
        assertEquals(project, project1);
        verify(projectRepository, times(1)).save(any());
        verify(kanbanServiceImpl, times(1)).createProject(any(),any());
    }

    @Test
    @DisplayName("Adding a new Task")
    public void checkForAddingNewTask() throws ProjectNotFound, DuplicateTask {
        when(projectRepository.findById(project.getProject_id())).thenReturn(project);
        projectRepository.save(project);
        Project project1 = projectTaskServiceImpl.addTask(task, project.getProject_id());
        System.out.println(project1);
        assertEquals(task, project1);
        verify(projectRepository, times(1)).findById(any());
        verify(projectTaskServiceImpl, times(1)).addTask(any(), any());
    }
}
