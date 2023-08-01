package com.vc.UserAuthentication.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {
    private String exchangeName = "kanban-emails";
    private String queue_reset = "pass-reset";

    @Bean
    public DirectExchange directExchange(){return new DirectExchange(exchangeName);}
    @Bean
    public Queue queueResetPass(){return new Queue(queue_reset);}


    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(){ return new Jackson2JsonMessageConverter();}

    @Bean
    Binding bindingEmailPassReset(DirectExchange exchange){
        return BindingBuilder.bind(queueResetPass()).to(exchange).with("email-pass-reset");
    }
}
