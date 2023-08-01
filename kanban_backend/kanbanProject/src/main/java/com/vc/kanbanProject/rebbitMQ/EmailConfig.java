package com.vc.kanbanProject.rebbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    private final String exchange = "kanban-emails";
    private final String queue_project_asg = "assigning_project";

    @Bean
    public DirectExchange directExchange(){return new DirectExchange(exchange);}

    @Bean
    public Queue projectAssigned(){return new Queue(queue_project_asg);}

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(){return new Jackson2JsonMessageConverter();}

    @Bean
    public Binding bindingProjectAssigned(DirectExchange exchange){
        return BindingBuilder.bind(projectAssigned()).to(exchange).with("email-projectAssigned");
    }
}
