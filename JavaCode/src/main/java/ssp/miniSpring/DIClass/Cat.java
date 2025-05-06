package ssp.miniSpring.DIClass;


import ssp.miniSpring.Autowired;
import ssp.miniSpring.Component;

@Component
public class Cat {
    @Autowired
    private Dog dog;
    public void sayHello() {
        System.out.println("cat say hello !!!");
    }

}
