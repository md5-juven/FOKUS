package com.smtp.EmailNotification.controller;

import com.smtp.EmailNotification.model.EmailInfo;
import com.smtp.EmailNotification.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emailProcess")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendWelcomeMail")
    private String sendWelcomeMail(@RequestBody EmailInfo emailInfo){
        return emailService.sendEmail(emailInfo);
    }
}
