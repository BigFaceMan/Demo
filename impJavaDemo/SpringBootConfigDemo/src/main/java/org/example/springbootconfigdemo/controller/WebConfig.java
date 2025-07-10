package org.example.springbootconfigdemo.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public HandlerMapping myHandlerMapping(MyHandler myHandler) {
        return new MyHandlerMapping(myHandler);
    }

    @Bean
    public HandlerAdapter myHandlerAdapter() {
        return new MyHandlerAdapter();
    }
}
