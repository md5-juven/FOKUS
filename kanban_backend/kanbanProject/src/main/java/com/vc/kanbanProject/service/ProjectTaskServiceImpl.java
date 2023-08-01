package com.vc.kanbanProject.service;

import com.vc.kanbanProject.domain.Project;
import com.vc.kanbanProject.domain.Task;
import com.vc.kanbanProject.domain.User;
import com.vc.kanbanProject.exception.DuplicateTask;
import com.vc.kanbanProject.exception.ProjectNotFound;
import com.vc.kanbanProject.exception.TaskLimited;
import com.vc.kanbanProject.proxy.ProjectProxy;
import com.vc.kanbanProject.rebbitMQ.EmailProducer;
import com.vc.kanbanProject.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {
    private ProjectProxy employeeProxy;
    private ProjectRepository projectRepository;
    private EmailProducer emailProducer;
    private SequenceGeneratorPRJ generator;

    @Value("${mg.sequenceName}")
    private String seq_name;

    @Autowired
    public ProjectTaskServiceImpl(ProjectProxy employeeProxy, ProjectRepository projectRepository,
                                  EmailProducer emailProducer, SequenceGeneratorPRJ generator) {
        this.employeeProxy = employeeProxy;
        this.projectRepository = projectRepository;
        this.emailProducer = emailProducer;
        this.generator = generator;
    }
    @Override
    public Project deleteTaskFromProject(int project_id, String email, String task) {
        Project project = projectRepository.findById(project_id);
        if (project.getEmail().equals(email)) {
            List<Task> list = project.getTaskList();
            list.removeIf(x -> x.getName().equals(task));
            project.setTaskList(list);
        }

        return projectRepository.save(project);
    }
    @Override
    public Project addTask(Task task, int project_id) throws ProjectNotFound, DuplicateTask {
        Project project =  projectRepository.findById(project_id);

        if(project.getTaskList() ==null){
            project.setTaskList(Arrays.asList(task));
        }else {
            List<Task> tasks = project.getTaskList();
            for (Task tempTask: project.getTaskList()
            ) {
                if(tempTask.getName().equals(task.getName())){
                    throw new DuplicateTask();
                }
            }
            tasks.add(task);
            project.setTaskList(tasks);
        }
        return projectRepository.save(project);
    }
    @Override
    public Project updateTaskInfo(int project_id, Task task) throws ProjectNotFound {
        if(task.getMemberList()!=null){
            for (User obj:
                    task.getMemberList()) {
                System.out.println(obj.getEmail()+"     "+obj.getUsername());
            }
        }
        Project project = projectRepository.findById(project_id);
        if(project == null){
            throw  new ProjectNotFound();
        }
        List<Task> list = project.getTaskList();
        for(Task task1 : list){
            if(task1.getName().equals(task.getName())){
                task1.setStatus(task.getStatus());
                task1.setPriority(task.getPriority());
                task1.setDescription(task.getDescription());
                task1.setLastChangedBy(task.getLastChangedBy());
                if(task.getMemberList()!=null){
                    task1.setMemberList(task.getMemberList());
                }
                if(task.getStatus().equals("Completed")){
                    task1.setMemberList(new ArrayList<>());
                }
                System.out.println("after setting member");
                if(task1.getMemberList()!=null){
                    for (User obj:
                            task1.getMemberList()) {
                        System.out.println(obj.getEmail()+"     "+obj.getUsername());
                    }
                }

                break;
            }
        }
        project.setTaskList(list);
        return projectRepository.save(project);
    }
    @Override
    public String addMembersToTask(int project_id, String email, String task_name) throws TaskLimited {
        int counter= 0;
        Project project =  projectRepository.findById(project_id);
        List<Task> taskList = project.getTaskList();
//        counter = Collections.frequency(taskList,taskList);
        for (Task task:
                taskList) {
            if(task.getEmail()!=null){
                if (task.getEmail().equals(email)){
                    counter++;
                }
            }
        }
        if(counter<3){
            for (Task task1:
                    taskList) {
                if (task1.getName().equals(task_name)){
                    task1.setEmail(email);
                }
            }
            projectRepository.save(project);
        }
        else
            throw new TaskLimited();
        return "Task added Successfully to the member";
    }
    @Override
    public String removeMemberFromTask(int project_id, String email, String task_name) {
        Project project =  projectRepository.findById(project_id);
        List<Task> taskList = project.getTaskList();
        for (Task task: taskList) {
            if(task.getName().equals(task_name)){
                task.setEmail(null);
            }
        }
        projectRepository.save(project);
        return "task Has been removed";
    }

    @Override
    public List<Task> getAllTask(int project_id) {
        Project project= projectRepository.findById(project_id);
        return project.getTaskList();
    }
}
