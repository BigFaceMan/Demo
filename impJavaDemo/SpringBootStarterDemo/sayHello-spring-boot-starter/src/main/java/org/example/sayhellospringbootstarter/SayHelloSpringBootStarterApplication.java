package org.example.sayhellospringbootstarter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SayHelloSpringBootStarterApplication {
    @Autowired
    private HelloService helloService;

    public static void main(String[] args) {
        SpringApplication.run(SayHelloSpringBootStarterApplication.class, args);
    }

    @PostConstruct
    public void init() {
        System.out.println(helloService.sayHello("World"));
    }

}
