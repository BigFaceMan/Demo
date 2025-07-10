package org.example;

import org.example.beanT.User;
import org.example.proxy.MyBean;
import org.example.proxy.UserService;
import org.example.transactional.UserServiceDeclarative;
import org.example.transactional.UserServiceProgrammatic;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Method;

/**
 * Hello world!
 *
 */
public class App {
    public void singleton(ApplicationContext context) {
//        singleton
//        System.out.println("----------------Singleton----------------");
        MyBean b1 = context.getBean(MyBean.class);
        MyBean b2 = context.getBean(MyBean.class);
        System.out.println(b1 == b2);  // false，不是单例
        b1.doSomething();
        b2.doSomething();
    }
    public void aop(ApplicationContext context) {
//        aop
//        System.out.println("----------------AOP----------------");
        UserService userService = context.getBean(UserService.class);
        userService.createUser();
    }
    public void trasactional(ApplicationContext context) {
//        System.out.println("----------------事务----------------");
        try {
            UserServiceDeclarative service = context.getBean(UserServiceDeclarative.class);
            service.createUser(); // 抛异常，事务回滚
        } catch (Exception e) {
            System.out.println("声明式事务异常捕获：" + e.getMessage());
        }

        try {
            UserServiceProgrammatic service2 = context.getBean(UserServiceProgrammatic.class);
            service2.createUser(); // 抛异常，事务回滚
        } catch (Exception e) {
            System.out.println("编程式事务异常捕获：" + e.getMessage());
        }

        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
        System.out.println("当前用户数：" + count); // 应该为 0
    }
    public void beanT(ApplicationContext context) {
//        System.out.println("----------------beanT----------------");
        User user = context.getBean(User.class);
        System.out.println("User bean: " + user);
    }

    public static void main( String[] args ) {
        System.out.println("--------------Spring Demo--------------");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        App app = new App();
        Method[] methods = App.class.getDeclaredMethods();
        for (Method method : methods) {
            if (!method.getName().equals("main")) {
                System.out.println("--------------执行 Demo : " + method.getName() + "--------------");
                try {
                    method.invoke(app, context);
                } catch (Exception e) {
                    System.err.println("Error executing method " + method.getName() + ": " + e.getMessage());
                }
            }
        }
    }
}
