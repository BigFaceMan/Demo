package org.example.proxy;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
//单例或者多例，但都不是线程安全的
//@Scope("prototype")
@Scope("singleton")  // 👈 设置作用域为单例
public class MyBean {
    public MyBean() {
        System.out.println("== MyBean 实例化 ==");
    }
    public void doSomething() {
        System.out.println("执行 MyBean 的 doSomething 方法");
    }
}
