package ssp.miniSpring.DIClass;

import ssp.miniSpring.Autowired;
import ssp.miniSpring.Component;
import ssp.miniSpring.PostConstruct;

//@Component(name = "Cat")
@Component()
public class Dog {

    @Autowired
    private Cat cat;

    @PostConstruct
    public void sayHello() {
        System.out.println("Dog init Suc and get Cat : " + cat);
    }
}
