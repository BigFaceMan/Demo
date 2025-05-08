package ssp.aop;

import org.springframework.stereotype.Component;

@Component
public class UserService {
    public void createUser() {
        System.out.println("执行 createUser 方法");
    }
}
