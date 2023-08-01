package com.vc.UserAuthentication.rabbitMQ;

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
}
