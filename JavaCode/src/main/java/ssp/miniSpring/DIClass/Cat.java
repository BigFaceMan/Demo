package ssp.miniSpring.DIClass;


import ssp.miniSpring.Autowired;
import ssp.miniSpring.Component;
import ssp.miniSpring.PostConstruct;

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
