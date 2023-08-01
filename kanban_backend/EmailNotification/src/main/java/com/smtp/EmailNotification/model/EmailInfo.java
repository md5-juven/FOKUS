package com.smtp.EmailNotification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailInfo {
    private String email;
    private String email_adm;
    private String msgBody;
    private String subject;
    private String attachment;
}
