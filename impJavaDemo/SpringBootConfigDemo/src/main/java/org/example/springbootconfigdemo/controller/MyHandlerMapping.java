package org.example.springbootconfigdemo.controller;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

public class MyHandlerMapping implements HandlerMapping {
    private MyHandler myHandler;
    MyHandlerMapping(MyHandler myHandler) {
        this.myHandler = myHandler;
    }
    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();

        // 这里你可以写任何路由规则
        if ("/myHandler".equals(uri)) {
            return new HandlerExecutionChain(myHandler);
        }

        return null; // 找不到就交给其他 HandlerMapping
    }
}
