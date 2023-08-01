package com.vc.kanbanUser.controller;


import com.vc.kanbanUser.domain.ContactUs;
import com.vc.kanbanUser.domain.Employee;
import com.vc.kanbanUser.exception.EmployeeAlreadyExists;
import com.vc.kanbanUser.exception.ProjectAlreadyExists;
import com.vc.kanbanUser.proxy.EmployeeProxy;
import com.vc.kanbanUser.service.KanbanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/kanban")
public class EmployeeController {

    private KanbanService kanbanService;

    private ResponseEntity<?> responseEntity;
    @Autowired
    private EmployeeProxy proxy;

    @Autowired
    public EmployeeController(KanbanService kanbanService) {
        this.kanbanService = kanbanService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> saveEmployee(@RequestBody Employee employee) throws EmployeeAlreadyExists, IOException {
        try {
//            employee.setTitle("FREE");
            return new ResponseEntity<>(kanbanService.saveEmployee(employee), HttpStatus.CREATED);
        }
        catch (EmployeeAlreadyExists e){
            throw new EmployeeAlreadyExists();
        }
        catch (IOException e){
            throw new IOException(e);
        }
    }

    @PostMapping("/employee/addProject/{project_id}")
    public ResponseEntity<?> addProjectId(@PathVariable int project_id,@RequestBody String email) throws ProjectAlreadyExists{

        System.out.println("kanban user controller : " + email);

        try{
            return new ResponseEntity<>(kanbanService.saveProjectIdInList(project_id, email),HttpStatus.OK);
        }catch (ProjectAlreadyExists e){
            throw  new ProjectAlreadyExists();
        }
    }



    @GetMapping("/employee/getAll")
    public ResponseEntity<?> getAllDetails(){
        List<Employee> users = kanbanService.getAllEmployees();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("employee/getProject/assigned/{email}")
    public ResponseEntity<List<Integer>> getAssignedProjects(@PathVariable String email){
       return new ResponseEntity<>(kanbanService.getAssignedProjects(email),HttpStatus.OK);
       // return kanbanService.getAssignedProjects(email);

    }


    @PostMapping("/info/contactUs")
    public ResponseEntity<?> saveSuggestion(@RequestBody ContactUs contactUs){
        return new ResponseEntity<>(kanbanService.saveSuggestion(contactUs),HttpStatus.CREATED);
    }

    //This should be made as admin where they can get all suggestionList ..????
    @GetMapping("/info/allSuggestionList")
    public ResponseEntity<?> getAllSuggestion(HttpServletRequest request){
//        Claims claims = (Claims) request.getAttribute("claims");
//        String  email = claims.getSubject();

        return new ResponseEntity<>(kanbanService.getAllSuggestion(),HttpStatus.OK);
    }

    @DeleteMapping("/employee/delete/project/{project_id}")
    public ResponseEntity<?> deleteProjectFormMembers(@RequestBody List<String> emails,@PathVariable int project_id){
        System.out.println(emails);
        System.out.println(project_id);
        return new ResponseEntity<>(kanbanService.deleteProjectFromMembers(emails,project_id),HttpStatus.OK);
    }
    @PutMapping("/employee/account/upgrade")
    public ResponseEntity<?> updateTitle(HttpServletRequest request){
        String title = request.getParameter("status");
        String email = request.getParameter("email");
        System.out.println(title+"    "+email);
        return new ResponseEntity<>(kanbanService.titleUpdate(title,email),HttpStatus.OK);
    }
    @DeleteMapping("/employee/removeProject/{project_id}/{email}")
    public  ResponseEntity<?> removeMemberFromProject(@PathVariable int project_id,@PathVariable String email){
        return new ResponseEntity<>(kanbanService.deleteMemberFromProject(project_id,email),HttpStatus.OK);
    }
    @GetMapping("/employee/getEmployee")
    public ResponseEntity<?> getUserDetails(HttpServletRequest request){
        String email = request.getParameter("email");
        return new ResponseEntity<>(kanbanService.getUser(email), HttpStatus.OK);
    }

    @GetMapping("/employee/getEmployee/{email}")
    public Employee getUserDetail(@PathVariable String email){
        return kanbanService.getUser(email);
    }
    @GetMapping("/requiredMail")
    private ResponseEntity<?> getEmailsStartWith(HttpServletRequest request){
        String emailStr = request.getParameter("StartWith");
        return new ResponseEntity<>(kanbanService.emailStartWith(emailStr),HttpStatus.OK);
    }
    @PutMapping("/employee/update/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Employee employee){
        return new ResponseEntity<>(kanbanService.updateProfile(employee),HttpStatus.OK);
    }
}
