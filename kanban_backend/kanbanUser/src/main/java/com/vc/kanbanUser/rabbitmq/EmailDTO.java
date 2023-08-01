package com.vc.kanbanUser.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailDTO {
    private String email;
    private String msgBody;
    private String subject;

//    private String attachment;
//    public EmailDTO(String email, String msgBody, String subject) {
//        this.email = email;
//        this.msgBody = msgBody;
//        this.subject = subject;
//    }
}
