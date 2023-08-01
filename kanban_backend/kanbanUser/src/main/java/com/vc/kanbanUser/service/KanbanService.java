package com.vc.kanbanUser.service;

import com.vc.kanbanUser.domain.ContactUs;
import com.vc.kanbanUser.domain.Employee;
import com.vc.kanbanUser.exception.EmployeeAlreadyExists;
import com.vc.kanbanUser.exception.EmployeeNotFound;
import com.vc.kanbanUser.exception.ProjectAlreadyExists;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public interface KanbanService {

    Employee saveEmployee(Employee employee) throws EmployeeAlreadyExists, IOException;
    List<Employee> getAllEmployees();
    Employee findByEmail(String email) throws EmployeeNotFound;
    Employee saveProjectIdInList(int project_id,String email) throws ProjectAlreadyExists;
    List<Integer> getAssignedProjects(String email);
    ContactUs saveSuggestion(ContactUs contactUs);
    List<ContactUs> getAllSuggestion();
    boolean deleteProjectFromMembers(List<String> email,int project_id);

    Map<String,String> titleUpdate(String title,String email);
    boolean deleteMemberFromProject(int project_id,String email);
    TreeSet<String> emailStartWith(String emailStr);
    Employee getUser(String email);
    Map<String, String> updateProfile(Employee employee);

}
