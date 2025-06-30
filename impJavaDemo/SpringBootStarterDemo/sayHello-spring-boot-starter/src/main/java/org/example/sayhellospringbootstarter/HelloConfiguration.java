package org.example.sayhellospringbootstarter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfiguration {
    @Bean
    @ConditionalOnClass(HelloService.class)
    public HelloService helloService() {
        return new HelloService();
    }
}
