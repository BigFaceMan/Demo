package org.example.beanT;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class User implements BeanNameAware, BeanFactoryAware, ApplicationContextAware {
    User() {
        System.out.println("User的构造方法执行了.....");
    }
    private String name;
    @Value("宋世鹏")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("User的BeanNameAware接口方法执行了，Bean的名字是：" + s);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("User的BeanFactoryAware接口方法执行了，BeanFactory是：" + beanFactory);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("User的ApplicationContextAware接口方法执行了，ApplicationContext是：" + applicationContext);
    }
    @PostConstruct
    public void init() {
        System.out.println("User的@PostConstruct注解方法执行了");
    }

    public String getName() {
        return name;
    }
}
