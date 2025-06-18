package org.example.DIClass;


import org.example.BeanPostProcessor;
import org.example.Component;

@Component
public class MyPostConstruct implements BeanPostProcessor {
    @Override
    public Object beforeInitializeBean(Object bean, String beanName) {
        System.out.println("MyPostConstruct run");
        return bean;
    }
}
