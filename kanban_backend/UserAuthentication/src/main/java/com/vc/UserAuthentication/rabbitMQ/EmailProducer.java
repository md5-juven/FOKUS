package com.vc.UserAuthentication.rabbitMQ;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailProducer {
    @Autowired
    private DirectExchange exchange;
    @Autowired
    private RabbitTemplate template;

    public void resetPassLink(EmailDTO dto){
        template.convertAndSend(exchange.getName(),"email-pass-reset",dto);
    }
}
