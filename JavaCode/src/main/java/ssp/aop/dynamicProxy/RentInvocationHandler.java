package ssp.aop.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RentInvocationHandler implements InvocationHandler {
    private Rent target;

    public RentInvocationHandler(Rent target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("中介收取中介费");
        return method.invoke(target, args);
    }
}
