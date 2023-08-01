package com.smtp.EmailNotification.services;

import com.smtp.EmailNotification.model.EmailInfo;

public interface EmailService {
    String sendEmail(EmailInfo emailInfo);

    String sendToEmail(EmailInfo emailInfo);

    String contactUs(EmailInfo emailInfo);
}
