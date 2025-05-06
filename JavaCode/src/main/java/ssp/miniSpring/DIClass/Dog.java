package ssp.miniSpring.DIClass;

import ssp.miniSpring.Autowired;
import ssp.miniSpring.Component;

@Component
public class Dog {

    @Autowired
    private Cat cat;
}
