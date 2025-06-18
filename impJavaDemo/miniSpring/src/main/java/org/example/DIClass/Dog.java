package org.example.DIClass;

import org.example.Autowired;
import org.example.Component;
import org.example.PostConstruct;

@Component()
public class Dog {

    @Autowired
    private Cat cat;

    @PostConstruct
    public void sayHello() {
        System.out.println("Dog init Suc and get Cat : " + cat);
    }
}
