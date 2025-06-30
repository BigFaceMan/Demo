package org.example.teststarter;

import org.example.sayhellospringbootstarter.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class sayHelloController {
    @Autowired
    private HelloService helloService;
    @GetMapping("/sayHello")
    public void sayHello(@RequestParam String name) {
        System.out.println(helloService.sayHello(name));
    }
}
