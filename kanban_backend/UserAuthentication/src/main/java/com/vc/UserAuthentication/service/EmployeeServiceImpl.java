package com.vc.UserAuthentication.service;

import com.vc.UserAuthentication.domain.Employee;
import com.vc.UserAuthentication.exception.*;
import com.vc.UserAuthentication.rabbitMQ.EmailDTO;
import com.vc.UserAuthentication.rabbitMQ.EmailProducer;
import com.vc.UserAuthentication.repository.EmployeeRepository;
import com.vc.UserAuthentication.security.Encryptor;
import com.vc.UserAuthentication.security.SecurityTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepository employeeRepository;
    @Autowired
    private Encryptor encryptor;
    @Autowired
    private EmailProducer emailProducer;
    @Autowired
    private SecurityTokenGenerator tokenGenerator;

    @Value("${encryptor.alg}")
    private String alg;
    @Value("${reset.pass.link}")
    private String resetUrl;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee registerEmployee(Employee employee) throws EmployeeAlreadyExists, NoSuchAlgorithmException {
        if(employeeRepository.findById(employee.getEmail()).isPresent()){
           throw new  EmployeeAlreadyExists();
        }
        byte[] salt = encryptor.createdSalt();
        employee.setSalt(salt);
        String hash = encryptor.generateHash(employee.getPassword(), alg,salt);
        employee.setPassword(hash);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee findByEmailAndPassword(String email, String password) throws EmployeeNotFound, NoSuchAlgorithmException, IncorrectPasswordException, EmployeeNotVerified {
        Employee employee;
        if(employeeRepository.findById(email).isEmpty()){
            throw new EmployeeNotFound();
        }else{
            employee = employeeRepository.findById(email).get();
            if(employee.getTitle().equals("UN-VERIFIED")){
                throw new EmployeeNotVerified();
            }else
            {
                String reHash = encryptor.generateHash(password, alg,employee.getSalt());
                if(employee.getPassword().equals(reHash)){
                    employee.setSalt(null);
                    employee.setPassword(null);
                    return employee;
                }else {
                    throw new IncorrectPasswordException();
                }
            }

        }
    }
    @Override
    public String updateResetPassToken(String email) throws EmployeeNotFound {
        String token= stringGen();
        Employee employee = employeeRepository.findByEmail(email);
        if(employee != null){
            employee.setResetPassToken(token);
            employeeRepository.save(employee);
            String link = resetUrl+token;
            setEmailProducer(email,link);
            System.out.println(link);
        }else
            throw new EmployeeNotFound();
        return "updated";
    }

    @Override
    public Employee getEmployeeByResetPass(String token) throws TokenExpired {
        String[] data = token.split("__DD__");
        long genTime = Long.parseLong(data[1]);
        Instant now = Instant.now();
        long presentTime = Date.from(now).getTime();
        if(presentTime>=genTime){
            throw new TokenExpired();
        }
        Employee employee = employeeRepository.findByResetPassToken(token);
        Employee employee1 = new Employee();
        employee1.setEmail(employee.getEmail());
        return employee1;
    }

    @Override
    public String resetPassword(String token,Employee employee) throws NoSuchAlgorithmException, TokenExpired {
        String[] data = token.split("__DD__");
        long genTime = Long.parseLong(data[1]);
        Instant now = Instant.now();
        long presentTime = Date.from(now).getTime();
        if(presentTime>=genTime){
            throw new TokenExpired();
        }
        Employee employee1 = employeeRepository.findByResetPassToken(token);
        byte[] salt = encryptor.createdSalt();
        employee1.setSalt(salt);
        String hash = encryptor.generateHash(employee.getPassword(), alg,salt);
        employee1.setPassword(hash);
        employee1.setResetPassToken(null);
        employeeRepository.save(employee1);
        return "Password updated";
    }

    @Override
    public String passwordVerify(Employee employee) throws NoSuchAlgorithmException {
        Employee employeeTemp;
        if(employeeRepository.findById(employee.getEmail()).isEmpty()){
            return "no-user";
        }else {
            employeeTemp = employeeRepository.findById(employee.getEmail()).get();
            String reHash = encryptor.generateHash(employee.getPassword(), alg, employeeTemp.getSalt());
            if (employeeTemp.getPassword().equals(reHash)) {
                return "user-verified";
            } else {
                return "wrong-password";
            }
        }
    }

    @Override
    public TreeSet<String> emailStartingWith(String email) {
        List<Employee> employees = employeeRepository.findByEmailStartingWith(email);
        TreeSet<String> emails = new TreeSet<>();
        for (Employee obj:
             employees) {
            emails.add(obj.getEmail());
        }
        System.out.println(emails);
        return emails;
    }

    @Override
    public Map<String,String> titleUpdate(String title, String email) {
        Map<String,String> token = null;
        if(!Objects.equals(title, "FREE")){
            Employee employee = employeeRepository.findByEmail(email);
            employee.setTitle(title);
            employeeRepository.save(employee);
            token = tokenGenerator.createToken(employee);
        }
        return token;
    }

    @Override
    public Map<String,String> profileUpdate(Employee employee) throws NoSuchAlgorithmException {
        Map<String,String> token = null;
        Employee tempEmp = employeeRepository.findByEmail(employee.getEmail());
        if(employee.getUsername()!=null){
            tempEmp.setUsername(employee.getUsername());
        }
        if(employee.getPassword()!=null){
            byte[] salt = encryptor.createdSalt();
            tempEmp.setSalt(salt);
            String hash = encryptor.generateHash(employee.getPassword(), alg,salt);
            tempEmp.setPassword(hash);
        }
        employeeRepository.save(tempEmp);
        token = tokenGenerator.createToken(employee);
        return token;
    }

    private String stringGen(){
        Instant now = Instant.now();
        String test = "__DD__";
        long dateEnd = Date.from(now.plus(10, ChronoUnit.MINUTES)).getTime();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 40;
        for(int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        sb.append(test).append(dateEnd);
        return sb.toString();
    }
    private void setEmailProducer(String email,String link){
        String subject = "Here's the link to reset your password";
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";
        EmailDTO dto = new EmailDTO();
        dto.setEmail(email);
        dto.setSubject(subject);
        dto.setMsgBody(content);
        emailProducer.resetPassLink(dto);
    }
}
