package ssp.aop.dynamicProxy;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        Rent landlord = new LandLord();
        Rent proxy = (Rent) Proxy.newProxyInstance(
                landlord.getClass().getClassLoader(),
                landlord.getClass().getInterfaces(),
                new RentInvocationHandler(landlord)
        );

        proxy.rent(); // 会打印中介费 + 房东出租信息
    }
}
