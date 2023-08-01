package com.api.ApiGateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@CrossOrigin
public class ApiRouter {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder){
//        return routeLocatorBuilder.routes()
//                .route(p->p //userAuthService
//                        .path("/employee/**")
//                        .uri("http://localhost:8081"))
//                .route(p->p //kanbanProjectService
//                        .path("/kan/**")
//                        .uri("http://localhost:8082"))
//                .route(p->p //KanbanUserService
//                        .path("/kanban/**")
//                        .uri("http://localhost:8083"))
//                .route(p->p //EmailNotification
//                        .path("/emailProcess/**")
//                        .uri("http://localhost:8084"))
//                .build();

            return routeLocatorBuilder.routes()
                    .route(p->p //userAuthService
                            .path("/employee/**")
                            .uri("http://localhost:8081"))
                    .route(p->p //kanbanProjectService
                            .path("/kan/**")
                            .uri("http://localhost:8082"))
                    .route(p->p //KanbanUserService
                            .path("/kanban/**")
                            .uri("http://localhost:8083"))
                    .route(p->p //EmailNotification
                            .path("/emailProcess/**")
                            .uri("http://localhost:8084"))
                    .route(p->p //ChatSupport
                            .path("/support/**")
                            .uri("http://localhost:8087"))
                    .build();

    }
}