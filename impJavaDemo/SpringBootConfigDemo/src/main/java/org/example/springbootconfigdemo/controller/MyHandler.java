package org.example.springbootconfigdemo.controller;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 这是我们自定义的 Handler（处理器），不需要实现任何接口
@Component
public class MyHandler {
    public void hello(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
        response.getWriter().write("Hello from MyHandler!");
    }
}
