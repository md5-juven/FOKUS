package com.vc.kanbanUser.rabbitmq;

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

    public void welcomeEmail(EmailDTO dto){ template.convertAndSend(exchange.getName(),"email-routing-welcome",dto);}
    public void contactUS(EmailDTO dto){ template.convertAndSend(exchange.getName(),"email-routing-contactUS",dto);}
}
