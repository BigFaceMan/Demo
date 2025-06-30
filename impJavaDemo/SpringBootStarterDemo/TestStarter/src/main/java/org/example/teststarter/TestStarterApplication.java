package org.example.teststarter;

import org.example.sayhellospringbootstarter.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
public class TestStarterApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestStarterApplication.class, args);
    }
}
