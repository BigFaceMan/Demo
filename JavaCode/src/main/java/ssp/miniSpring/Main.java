package ssp.miniSpring;

import ssp.miniSpring.Utils.Cat;

public class Main {
    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ApplicationContext("ssp.miniSpring");
        Cat cat = (Cat)applicationContext.getBean("Cat");
//        System.out.println(cat == null);
        cat.sayHello();
    }
}
