package ssp.miniSpring.DIClass;

import ssp.miniSpring.BeanPostProcessor;
import ssp.miniSpring.Component;
import ssp.miniSpring.PostConstruct;

@Component
public class MyPostConstruct implements BeanPostProcessor {
    @Override
    public Object beforeInitializeBean(Object bean, String beanName) {
        System.out.println("MyPostConstruct run");
        return bean;
    }
}
