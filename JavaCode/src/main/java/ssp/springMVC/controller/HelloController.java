package ssp.springMVC.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // 等价于 @Controller + @ResponseBody
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }
}
