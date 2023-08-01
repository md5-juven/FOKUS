package com.vc.kanbanProject.proxy;


import com.vc.kanbanProject.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "kanban-user",url = "localhost:8083")
public interface ProjectProxy {
    @PostMapping("/kanban/employee/addProject/{project_id}")
    public ResponseEntity<?> addProjectId(@PathVariable int project_id,  String email);

    @GetMapping("kanban/employee/getProject/assigned/{email}")
    public ResponseEntity<?> getAssignedProjects(@PathVariable String email);

    @DeleteMapping("kanban/employee/delete/project/{project_id}")
    public ResponseEntity<?> deleteProjectFormMembers(@RequestBody List<String> emails, @PathVariable int project_id);

    @DeleteMapping("kanban/employee/removeProject/{project_id}/{email}")
    public  ResponseEntity<?> removeMemberFromProject(@PathVariable int project_id,@PathVariable String email);
    @GetMapping("kanban/employee/getEmployee/{email}")
    public User getUserDetail(@PathVariable String email);
}
