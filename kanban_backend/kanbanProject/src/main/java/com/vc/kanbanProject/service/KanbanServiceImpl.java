package com.vc.kanbanProject.service;

import com.vc.kanbanProject.domain.Project;
import com.vc.kanbanProject.domain.Task;
import com.vc.kanbanProject.domain.User;
import com.vc.kanbanProject.exception.*;
import com.vc.kanbanProject.proxy.ProjectProxy;
import com.vc.kanbanProject.rebbitMQ.EmailDTO;
import com.vc.kanbanProject.rebbitMQ.EmailProducer;
import com.vc.kanbanProject.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KanbanServiceImpl implements KanbanService{


    private ProjectProxy employeeProxy;
    private ProjectRepository projectRepository;
    private EmailProducer emailProducer;
    private SequenceGeneratorPRJ generator;

    @Value("${mg.sequenceName}")
    private String seq_name;

    @Autowired //constructor Autowired
    public KanbanServiceImpl( ProjectProxy employeeProxy, ProjectRepository projectRepository, EmailProducer emailProducer,
                              SequenceGeneratorPRJ generator) {
        this.projectRepository = projectRepository;
        this.employeeProxy = employeeProxy;
        this.emailProducer = emailProducer;
        this.generator = generator;
    }

    /*@Override
    public Employee findByEmail(String email) throws EmployeeNotFound {
        if (employeeRepository.findById(email).isEmpty()) {
            throw new EmployeeNotFound();
        }
        return employeeRepository.findByEmail(email);
    }*/

    @Override
    public Project findById(int project_id) {
        return projectRepository.findById(project_id);
    }

    @Override
    public Project createProject(Project project,String title) throws ProjectAlreadyExists, ProjectLimiter {
        List<Project> projects = projectRepository.findByEmail(project.getEmail());
        System.out.println(title);
        if(title.equals("FREE")){
            System.out.println(projects.size());
            if(projects.size() <3){
                int num = 1000+generator.getSequenceNumber(seq_name);
                project.setProject_id(num);
                Project savedProject = projectRepository.save(project);
                System.out.println(project.getEmail());
//                employeeProxy.addProjectId(project.getProject_id(), project.getEmail());
                return  savedProject;
            }else {
                throw new ProjectLimiter();
            }
        }else {
            int num = 1000+generator.getSequenceNumber(seq_name);
            project.setProject_id(num);
            Project savedProject = projectRepository.save(project);
            System.out.println(project.getEmail());
//            employeeProxy.addProjectId(project.getProject_id(), project.getEmail());
            return  savedProject;
        }
    }

    @Override
    public Project updateProject(String email, Project project) throws ProjectNotFound {
        return null;
    }

    @Override
    public Project assignMember(int project_id, User user) throws EmployeeNotFound, ProjectNotFound {
        User tempUser = employeeProxy.getUserDetail(user.getEmail());
        Project project =  projectRepository.findById(project_id);
        if(project.getAssigned_empl()==null){
            project.setAssigned_empl(Collections.singletonList(tempUser));
        }else {
            List<User> userList = project.getAssigned_empl();
            userList.add(tempUser);
            project.setAssigned_empl(userList);
        }
//        User flotuser =new User();
//        flotuser.setEmail(user.getEmail().replace(".","_"));
//        flotuser.setProfile_pic(user.getProfile_pic());
//       if(project.getAssigned_empe() == null){
//           project.setAssigned_empe(Map.of(flotuser.getEmail(),flotuser.getProfile_pic()));
//       }else {
//           Map<String,String> map = project.getAssigned_empe();
//           map.put(flotuser.getEmail(), flotuser.getProfile_pic());
//           project.setAssigned_empe(map);
//       }

//       LIST of only mail
//        if(project.getAssigned_emp() ==null){
//            project.setAssigned_emp(Arrays.asList(user.getEmail()));
//        }else {
//            List<String> assigned_employees = project.getAssigned_emp();
//            assigned_employees.add(user.getEmail());
//            project.setAssigned_emp(assigned_employees);
//        }
//        return null;
        projectAssignEmailBuilder(project,user);
        employeeProxy.addProjectId(project_id, user.getEmail());
        return projectRepository.save(project);
    }

    @Override
    public boolean archiveProject(int project_id, String email,String archive) {
        boolean flag=false;
        Project project = projectRepository.findById(project_id);
        if(project.getEmail().equals(String.valueOf(email))){
            if(archive.equals("ARCHIVE")){
                project.setArchive(archive);
                project.setArchiveData(new Date());
            }else{
                project.setArchive(archive);
                project.setArchiveData(null);
            }
            projectRepository.save(project);
            flag = true;
        }
        return flag;
    }


    @Override
    public List<Project> findByEmail(String email) {

        return projectRepository.findByEmail(email);
    }
    private void projectAssignEmailBuilder(Project project, User user) {
        String subject = "New Project Assigned by"+user.getUsername();
        String bodyOfMail = "Dear "+user.getUsername()+"\n You have be assigned with a new Project"
                +"\n Title : **"+project.getName()+"**"
                +"\n Description : "+project.getDescription();
        EmailDTO dto = new EmailDTO();
        dto.setEmail(user.getEmail());
        dto.setEmail_adm(project.getEmail());
        dto.setSubject(subject);
        dto.setMsgBody(bodyOfMail);
        emailProducer.ProjectAssignedEmail(dto);
    }

    @Override
    public boolean deleteProject(int project_id, String email) throws ProjectNotFound {
        Project project = projectRepository.findById(project_id);
        if(project == null){
            throw  new ProjectNotFound();
        }
        if(project.getEmail().equals(email)){
            List<User> userList = project.getAssigned_empl();
            if(userList != null){
                List<String> employeList = new ArrayList<>();
                for (User obj:userList){
                    employeList.add(obj.getEmail());
                }
                employeeProxy.deleteProjectFormMembers(employeList,project_id);
            }
//            if(employeList != null){

//                employeList.add(email);
//                System.out.println(rg.getBody());
//                System.out.println(rg);
//            }
            projectRepository.deleteById(project_id);
            return  true;
        }

        return false ;
    }

    @Override
    public List<Project> getAssignedProjectList(String email) {
        ResponseEntity<List<Integer>> responseEntity = (ResponseEntity<List<Integer>>) employeeProxy.getAssignedProjects(email);
        List<Integer>  projectIdsList =  responseEntity.getBody();

        List<Project>  projectsList = new ArrayList<>();

        if(projectIdsList != null){
            System.out.println(projectIdsList+" SDSDSDSDHKSDH");
            for( int project_id : projectIdsList){
                Project project = projectRepository.findById(project_id);
                System.out.println(project+"yessssssss");
                if(project.getArchive().equals("LIVE"))
                    projectsList.add(project);
            }
        }
        return projectsList;
    }

    @Override
    public Boolean removeMemberFromProject(int project_id, String email) {
        Project project = projectRepository.findById(project_id);
        // member delete in project
        List<User> memberList = project.getAssigned_empl();
        if(memberList!=null){
            memberList.removeIf(userObj ->userObj.getEmail().equals(String.valueOf(email)));
            project.setAssigned_empl(memberList);
        }
        // member delete in task
        List<Task> tasks = project.getTaskList();
        if(tasks != null){
            for(Task taskObj:tasks){
                List<User> userList = taskObj.getMemberList();
                if(userList !=null){
                    userList.removeIf(userObj -> userObj.getEmail().equals(String.valueOf(email)));
                    taskObj.setMemberList(userList);
                }
            }
            project.setTaskList(tasks);
        }
        employeeProxy.removeMemberFromProject(project_id,email);
        projectRepository.save(project);
        return true;
    }
}
