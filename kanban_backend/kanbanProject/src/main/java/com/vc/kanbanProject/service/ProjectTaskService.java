package com.vc.kanbanProject.service;

import com.vc.kanbanProject.domain.Project;
import com.vc.kanbanProject.domain.Task;
import com.vc.kanbanProject.exception.DuplicateTask;
import com.vc.kanbanProject.exception.ProjectNotFound;
import com.vc.kanbanProject.exception.TaskLimited;

import java.util.List;

public interface ProjectTaskService {

    Project deleteTaskFromProject(int project_id,String email,String task);
    Project addTask(Task task, int project_id) throws ProjectNotFound, DuplicateTask;
    Project updateTaskInfo(int project_id, Task task) throws ProjectNotFound;
    String addMembersToTask(int project_id,String email,String task) throws TaskLimited;
    String removeMemberFromTask(int project_id,String email,String task_name);

    List<Task> getAllTask(int project_id);
}
