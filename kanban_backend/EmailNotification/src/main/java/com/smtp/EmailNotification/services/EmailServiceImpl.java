package com.smtp.EmailNotification.services;

import com.smtp.EmailNotification.model.EmailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;
    @Override
    public String sendEmail(EmailInfo emailInfo) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setSubject(emailInfo.getSubject());
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(mimeMessage,true);
            helper.setFrom(sender);
            helper.setTo(emailInfo.getEmail());
            helper.setSentDate(new Date());
            helper.setText(emailInfo.getMsgBody(),true);
            javaMailSender.send(mimeMessage);
            return "success";
        }catch (Exception e){
            return "mail not sent";
        }
    }

    @Override
    public String sendToEmail(EmailInfo emailInfo) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setSubject(emailInfo.getSubject());
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(mimeMessage,true);
            helper.setFrom(sender);
            helper.setTo(emailInfo.getEmail());
            helper.setSentDate(new Date());
            helper.setText(emailInfo.getMsgBody(),true);
            javaMailSender.send(mimeMessage);
            return "success";
        }catch (Exception e){
            return "mail not sent";
        }
    }

    @Override
    public String contactUs(EmailInfo emailInfo) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setSubject(emailInfo.getSubject());
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(mimeMessage,true);
            helper.setFrom(sender);
            helper.setCc("care.fokus@gmail.com");
            String[] emails = new String[2];
            emails[0]="care.fokus@gmail.com";
            emails[1]= emailInfo.getEmail();
            helper.setTo(emails);
            helper.setSentDate(new Date());
            helper.setText(emailInfo.getMsgBody(),true);
            javaMailSender.send(mimeMessage);
            return "success";
        }catch (Exception e){
            return "mail not sent";
        }
    }
}
