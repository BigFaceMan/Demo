package org.example;

import java.lang.reflect.Proxy;

interface HelloService {
    String sayHello(String name);
}

public class JDKDProxy {
    public static void main(String[] args) {
        HelloService proxy = (HelloService) Proxy.newProxyInstance(
                HelloService.class.getClassLoader(),
                new Class[]{HelloService.class},
                (obj, method, args1) -> {
                    System.out.println("正在调用远程服务: " + method.getName());
                    return "Hi, " + args1[0];
                }
        );

        System.out.println(proxy.sayHello("Tom")); // 打印：Hi, Tom
    }
}
