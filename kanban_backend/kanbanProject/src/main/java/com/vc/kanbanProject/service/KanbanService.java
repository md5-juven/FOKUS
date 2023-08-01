package com.vc.kanbanProject.service;

import com.vc.kanbanProject.domain.Project;
import com.vc.kanbanProject.domain.Task;
import com.vc.kanbanProject.domain.User;
import com.vc.kanbanProject.exception.*;

import java.util.List;

public interface KanbanService {

  Project createProject(Project project ,String title) throws ProjectAlreadyExists, ProjectLimiter;


  Project updateProject(String email, Project project) throws ProjectNotFound;

    Project findById(int project_id);

    List<Project> findByEmail(String email);

    Project assignMember(int project_id, User user) throws EmployeeNotFound,ProjectNotFound;

    boolean archiveProject(int project_id, String email,String archive);

    boolean deleteProject(int project_id, String email) throws ProjectNotFound;
    List<Project> getAssignedProjectList(String email);
    Boolean removeMemberFromProject(int project_id,String email);
}
