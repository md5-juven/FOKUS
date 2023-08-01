package com.vc.kanbanProject.controller;


import com.vc.kanbanProject.domain.Project;
import com.vc.kanbanProject.domain.Task;
import com.vc.kanbanProject.domain.User;
import com.vc.kanbanProject.exception.*;
import com.vc.kanbanProject.proxy.ProjectProxy;
import com.vc.kanbanProject.service.KanbanService;
import com.vc.kanbanProject.service.ProjectTaskService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/kan")
public class EmployeeController {

    private KanbanService kanbanService;
    private ProjectTaskService taskService;

    private ResponseEntity<?> responseEntity;

    @Autowired
    public EmployeeController(KanbanService kanbanService,ProjectTaskService taskService) {
        this.taskService = taskService;
        this.kanbanService = kanbanService;
    }

    @PostMapping("/project/newProject")
    public ResponseEntity<?> createProject(@RequestBody Project project, HttpServletRequest request) throws ProjectAlreadyExists, ProjectLimiter {
        ResponseEntity<?> responseEntity;
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String email = claims.getSubject();
            project.setEmail(email);
            project.setArchive("LIVE");
            String title = claims.get("title").toString();
            responseEntity = new ResponseEntity<>(kanbanService.createProject(project, title), HttpStatus.OK);

        }catch (ProjectAlreadyExists e)
        {
            throw new ProjectAlreadyExists();
        } catch (ProjectLimiter e) {
            throw new ProjectLimiter();
        }
        return responseEntity;
    }

    @PostMapping("/project/assign/{project_id}")
    public ResponseEntity<?> assignMember(@RequestBody User user, @PathVariable int project_id) throws
            EmployeeNotFound, ProjectNotFound{
        ResponseEntity<?> responseEntity;
        try{
            responseEntity = new ResponseEntity<>(kanbanService.assignMember(project_id,user),HttpStatus.OK);

        }catch (ProjectNotFound e){
            throw  new ProjectNotFound();
        }
        return  responseEntity;
    }

    @PostMapping("/project/addTask/{project_id}")
    public ResponseEntity<?> addTask(@RequestBody Task task, @PathVariable int project_id) throws ProjectNotFound, DuplicateTask {
        ResponseEntity<?> responseEntity;
        try{
            responseEntity = new ResponseEntity<>(taskService.addTask(task,project_id),HttpStatus.OK);

        }catch (ProjectNotFound a){
            throw  new ProjectNotFound();
        }catch (DuplicateTask b){
            throw new DuplicateTask();
        }
        return  responseEntity;
    }

    @GetMapping("/project/getProjects")
    public ResponseEntity<?> getProjectDetails(HttpServletRequest request) {


            Claims claims = (Claims) request.getAttribute("claims");
            String  email = claims.getSubject();
            responseEntity = new ResponseEntity<>(kanbanService.findByEmail(email), HttpStatus.OK);

        return responseEntity;
    }

    @GetMapping("project/getProject/{project_id}")
    public ResponseEntity<?> getProjectWithId(@PathVariable int project_id){
        return  new ResponseEntity<>(kanbanService.findById(project_id),HttpStatus.OK);
    }

    @DeleteMapping("project/deleteTask/{project_id}")
    public ResponseEntity<?> deleteTask(@PathVariable int project_id, @RequestBody Task task,HttpServletRequest request){

        Claims claims = (Claims) request.getAttribute("claims");
        String email = claims.getSubject();
            return new ResponseEntity<>(taskService.deleteTaskFromProject(project_id,email,task.getName()),HttpStatus.OK);
    }

    @PutMapping("project/updateTask/{project_id}")
    public ResponseEntity<?> updateTaskStatus(@PathVariable int project_id, @RequestBody Task task) throws ProjectNotFound{
        ResponseEntity<?> responseEntity1;
        try {
            responseEntity1 =  new ResponseEntity<>(taskService.updateTaskInfo(project_id,task),HttpStatus.OK);
        }catch (ProjectNotFound e){
            throw  new ProjectNotFound();
        }
        return responseEntity1;

    }
    @DeleteMapping("project/deleteProject/{project_id}")
    public  ResponseEntity<?> deleteProject(@PathVariable int project_id, HttpServletRequest request) throws ProjectNotFound{
        ResponseEntity<?> responseEntity1;
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String email = claims.getSubject();
            responseEntity1 =  new ResponseEntity<>(kanbanService.deleteProject(project_id, email),HttpStatus.OK);
        }catch (ProjectNotFound e){
            throw  new ProjectNotFound();
        }
        return responseEntity1;
    }

    @GetMapping("project/getAssigned")
    public ResponseEntity<?> getAssignedProjects(HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        String email = claims.getSubject();
        return  new ResponseEntity<>(kanbanService.getAssignedProjectList(email),HttpStatus.OK);
    }

    @PostMapping("project/{project_id}/addMemberToTask/{task_name}")
    public ResponseEntity<?> assignTaskToMember(@PathVariable int project_id,@PathVariable String task_name,@RequestBody User user) throws TaskLimited {
        return new ResponseEntity<>(taskService.addMembersToTask(project_id, user.getEmail(), task_name),HttpStatus.OK);

    }
    @PostMapping("project/{project_id}/removeMemberFromTask/{task_name}")
    public ResponseEntity<?> removeMemberFromTask(@PathVariable int project_id,@PathVariable String task_name,@RequestBody User user) throws TaskLimited {
        return new ResponseEntity<>(taskService.removeMemberFromTask(project_id, user.getEmail(), task_name),HttpStatus.OK);

    }
    @DeleteMapping("project/removeMember/{project_id}")
    public  ResponseEntity<?> removeMemberFromProject(@PathVariable int project_id,HttpServletRequest request){
        String email = request.getParameter("email");
         boolean flag = kanbanService.removeMemberFromProject(project_id,email);
        return new ResponseEntity<>(flag,HttpStatus.OK);
    }
    @PostMapping("project/archive/{project_id}")
    public  ResponseEntity<?> archiveProject(@PathVariable int project_id, HttpServletRequest request){
            Claims claims = (Claims) request.getAttribute("claims");
            String email = claims.getSubject();
            String archive = request.getParameter("archiveStatus");
        System.out.println(email+"   "+project_id+"   "+archive);
        return new ResponseEntity<>(kanbanService.archiveProject(project_id, email,archive),HttpStatus.OK);
    }
    @PostMapping("/project/testingC")
    public ResponseEntity<?> TestingC(@RequestBody Project project, HttpServletRequest request) throws ProjectAlreadyExists {
            Claims claims = (Claims) request.getAttribute("claims");
            String email = claims.getSubject();
            String title = claims.get("title").toString();
//            project.setEmail(email);
        System.out.println(title);
        System.out.println(email);
        System.out.println(claims);
        return null;
    }

    @GetMapping("/project/getAllTask/{project_id}")
    public ResponseEntity<?> getAllTasks(@PathVariable int project_id){
        return new ResponseEntity<>(taskService.getAllTask(project_id), HttpStatus.OK);
    }

}
