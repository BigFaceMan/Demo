package org.example.beanT;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class UserBeanPostProcesser implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("user")) {
            System.out.println("UserBeanPostProcesser: Before Initialization of bean: " + beanName);
            // 可以在这里对bean进行一些处理，比如修改属性等
            System.out.println("Before UserBeanPostProcesser: User name set to: " + ((User) bean).getName());
            if (bean instanceof User) {
                User user = (User) bean;
                user.setName("段段");
            }
            System.out.println("UserBeanPostProcesser: User name set to: " + ((User) bean).getName());
        }
        return bean;
    }

    /*
    这里可以用AOP动态代理，返回代理后的对象
    * */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("user")) {
            System.out.println("UserBeanPostProcesser: After Initialization of bean: " + beanName);
            // 可以在这里对bean进行一些处理，比如打印日志等
//            AOP
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(bean.getClass());
            enhancer.setCallback(new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                    return method.invoke(bean, objects);
                }
            });
            return enhancer.create();
        }
        return bean;
    }
}
