package org.example.DIClass;


import org.example.Autowired;
import org.example.Component;
import org.example.PostConstruct;

@Component
public class Cat {
    @Autowired
    private Dog dog;

    public Dog getDog() {
        return dog;
    }

    @PostConstruct
    public void sayHello() {
        System.out.println("Cat init Suc and get Dog : " + dog);
    }

}
