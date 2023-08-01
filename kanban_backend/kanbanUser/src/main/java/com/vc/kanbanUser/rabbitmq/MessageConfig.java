package com.vc.kanbanUser.rabbitmq;

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
    private String queue_welcome = "email-welcome";
    private String queue_contactUS = "contact-us";
//    private String qu_task_status = "task-update";

    @Bean
    public DirectExchange directExchange(){return new DirectExchange(exchangeName);}
    @Bean
    public Queue queueWelcome(){return new Queue(queue_welcome);}

    @Bean
    public Queue queueContactUS(){return new Queue(queue_contactUS);}

//    @Bean
//    public Queue queueStatusUpdateEmail(){return new Queue(qu_task_status);}

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(){ return new Jackson2JsonMessageConverter();}

    @Bean
    Binding bindingEmailWelcome(DirectExchange exchange){
        return BindingBuilder.bind(queueWelcome()).to(exchange).with("email-routing-welcome");
    }
    @Bean
    Binding bindingEmailContactUs(DirectExchange exchange){
        return BindingBuilder.bind(queueWelcome()).to(exchange).with("email-routing-contactUS");
    }
//    @Bean
//    Binding bindingTaskStatus(DirectExchange exchange){
//        return BindingBuilder.bind(queueStatusUpdateEmail()).to(exchange).with("email-routing-task");
//    }
}
