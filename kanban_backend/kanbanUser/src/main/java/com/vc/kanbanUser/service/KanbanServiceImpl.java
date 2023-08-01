package com.vc.kanbanUser.service;
import com.vc.kanbanUser.domain.ContactUs;
import com.vc.kanbanUser.domain.Employee;
import com.vc.kanbanUser.exception.EmployeeAlreadyExists;
import com.vc.kanbanUser.exception.EmployeeNotFound;
import com.vc.kanbanUser.exception.ProjectAlreadyExists;
import com.vc.kanbanUser.proxy.EmployeeProxy;
import com.vc.kanbanUser.rabbitmq.EmailDTO;
import com.vc.kanbanUser.rabbitmq.EmailProducer;
import com.vc.kanbanUser.repository.ContactRepos;
import com.vc.kanbanUser.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class KanbanServiceImpl implements KanbanService{
    private EmployeeRepository employeeRepository;
    private EmployeeProxy employeeProxy;
    private EmailProducer emailProducer;
    private SequenceGenerator generator;
    private ContactRepos contactRepos;

    @Value("${mg.sequenceName}")
    private String seq_name;

//    @Value("classpath:welcomeEmail.txt")
//    Resource resource;

    @Autowired
    public KanbanServiceImpl(EmployeeRepository employeeRepository, EmployeeProxy employeeProxy,EmailProducer emailProducer,
                             SequenceGenerator generator,ContactRepos contactRepos) {
        this.employeeRepository = employeeRepository;
        this.employeeProxy = employeeProxy;
        this.emailProducer = emailProducer;
        this.generator = generator;
        this.contactRepos =contactRepos;
    }

    @Override
    public Employee saveEmployee(Employee employee) throws EmployeeAlreadyExists, IOException {
        welcomeEmail(employee);
        if(employeeRepository.findById(employee.getEmail()).isPresent()){
            throw new EmployeeAlreadyExists();
        }
        int num = 1000+generator.getSequenceNumber(seq_name);
        employee.setEmp_id("EMP"+num);
        System.out.println(employee);
        ResponseEntity<?> r = employeeProxy.registerEmployee(employee);
        Employee savedEmployee = employeeRepository.save(employee);

        return savedEmployee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee saveProjectIdInList(int project_id, String email) throws ProjectAlreadyExists {
        System.out.println(email);
       Employee employee = employeeRepository.findByEmail(email);

        System.out.println(employee);
       if(employee.getProject_id_list()==null){
           employee.setProject_id_list(Arrays.asList(project_id));
       }else{
           List<Integer> projectList = employee.getProject_id_list();
           projectList.add(project_id);
           employee.setProject_id_list(projectList);
       }
       return  employeeRepository.save(employee);
    }

    @Override
    public Employee findByEmail(String email) throws EmployeeNotFound {
        if (employeeRepository.findById(email).isEmpty()) {
            throw new EmployeeNotFound();
        }
        return employeeRepository.findByEmail(email);
    }

    @Override
    public List<Integer> getAssignedProjects(String email) {
        Employee employee = employeeRepository.findByEmail(email);
        return employee.getProject_id_list();
    }

    @Override
    public ContactUs saveSuggestion(ContactUs contactUs) {
        String id = "CONTACT_"+generator.getSequenceNumberContact(seq_name);
        contactUs.setContact_id(id);
        String sug = "Dear "+","+"<b>"+contactUs.getName()+"</b>"
                    +"<br> Your Suggestion is : "+"<b>"+contactUs.getMessage()+"</b>"
                    +"<br> Thank you for your suggestion we will get back too you soon !";
        EmailDTO dto = new EmailDTO(contactUs.getEmail(), sug,"FOKUS reply");
        emailProducer.contactUS(dto);
        return contactRepos.save(contactUs);
    }

    @Override
    public List<ContactUs> getAllSuggestion() {
        List<ContactUs> contactUsList = contactRepos.findAll();
        return contactUsList;
    }

    @Override
    public boolean deleteProjectFromMembers(List<String> email,int project_id) {
        for (String s:
             email) {
            if(employeeRepository.findById(s).isPresent()){
                Employee temp = employeeRepository.findById(s).get();
                System.out.println(temp);
                List<Integer> projects = temp.getProject_id_list();
                if(projects!=null){
                    projects.remove(Integer.valueOf(project_id));
                    temp.setProject_id_list(projects);
                    System.out.println(projects);
                    employeeRepository.save(temp);
                }
            }
        }
        return true;
    }

    @Override
    public Map<String,String> titleUpdate(String title, String email) {
        Employee employee = employeeRepository.findByEmail(email);
        ResponseEntity<?> response;
        response = employeeProxy.updateTitle(email,title);
        Map<String,String> data = (Map<String, String>) response.getBody();
        employee.setTitle(title);
        employeeRepository.save(employee);
        return data;
    }

    @Override
    public boolean deleteMemberFromProject(int project_id, String email) {
        Employee employee = employeeRepository.findByEmail(email);
        List<Integer> projectList = employee.getProject_id_list();
        projectList.remove(Integer.valueOf(project_id));
        employee.setProject_id_list(projectList);
        employeeRepository.save(employee);
        return true;
    }

    @Override
    public TreeSet<String> emailStartWith(String emailStr) {
        List<Employee> employee = employeeRepository.findByEmailStartingWith(emailStr);
        TreeSet<String> emails = new TreeSet<>();
        for (Employee obj:
                employee) {
            emails.add(obj.getEmail());
        }
        System.out.println(emails);
        return emails;
    }

    @Override
    public Map<String, String> updateProfile(Employee employee) {
        Employee tempEmp = employeeRepository.findByEmail(employee.getEmail());
        if(employee.getUsername()!=null){
            tempEmp.setUsername(employee.getUsername());
        }
        if(employee.getProfile_pic()!= null){
            tempEmp.setProfile_pic(employee.getProfile_pic());
        }
        ResponseEntity<?> response =  employeeProxy.updateProfile(employee);
        Map<String,String> data = (Map<String, String>) response.getBody();
        employeeRepository.save(tempEmp);
        tempEmp.setPassword(null);
        return data;
    }

    @Override
    public Employee getUser(String email) {
        return employeeRepository.findByEmail(email);
    }

    public void welcomeEmail(Employee employee) throws IOException {

        String fileName ="/usr/lib/welcomeEmail.txt";
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        reader.close();
        String content = stringBuilder.toString();
        String username = employee.getUsername().toUpperCase();
        content =  content.replace("*UserName_InPut_Swap*",username);
        EmailDTO dto = new EmailDTO(employee.getEmail(),content,"Welcome to fokus");
        emailProducer.welcomeEmail(dto);

    }
}
