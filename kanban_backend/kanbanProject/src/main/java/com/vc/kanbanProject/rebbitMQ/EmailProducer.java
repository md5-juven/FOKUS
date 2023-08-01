package com.vc.kanbanProject.rebbitMQ;

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

    public void ProjectAssignedEmail(EmailDTO dto){ template.convertAndSend(exchange.getName(),"email-projectAssigned",dto);}
}
