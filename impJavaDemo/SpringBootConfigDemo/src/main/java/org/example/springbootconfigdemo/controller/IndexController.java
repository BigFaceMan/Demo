package org.example.springbootconfigdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @RequestMapping("/index")
    @ResponseBody
    public String sayHello(){
        System.out.println("hello ...");
        return "hello";
    }
}
