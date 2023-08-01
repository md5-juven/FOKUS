package com.vc.kanbanUser.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vc.kanbanUser.domain.Employee;
import com.vc.kanbanUser.exception.EmployeeAlreadyExists;
import com.vc.kanbanUser.exception.EmployeeNotFound;
import com.vc.kanbanUser.service.KanbanServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private KanbanServiceImpl kanbanService;

    @InjectMocks
    private EmployeeController controller;

    private Employee employee1,employee2;

    private List<Employee> employeeList;

    @BeforeEach
    void setUp(){
        employee1 = new Employee("test@gmail.com","testing","EMP156","member","free","pic");
        employee2 = new Employee("xyz@gmail.com","testxyz","EMP157","member","free","pic");
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    @AfterEach
    void tearDown(){
        employee1 = null;
        employee2 = null;
    }
    @Test
    @DisplayName("Registering Employee [Pass]")
    void registerNewEmployeeToTheList() throws EmployeeAlreadyExists, Exception {
        when(kanbanService.saveEmployee(any(Employee.class))).thenReturn(employee1);
        mockMvc.perform(post("/kanban/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(employee1)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
        verify(kanbanService,times(1)).saveEmployee(any());
    }

    @Test
    @DisplayName("adding Project to user")
    void addProjectToEmployee()throws Exception, EmployeeNotFound {
        when(kanbanService);
    }
    @Test
    @DisplayName("Get all employee")
    void gatEmployee() throws Exception{
        when(kanbanService.getAllEmployees()).thenReturn(employeeList);
        mockMvc.perform(get("/kanban/employee/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    private static String jsonToString(final Object obj){
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            result =mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            result = "JSON Failed To Process";
        }
        return result;
    }
}
