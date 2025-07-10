package org.example.springbootconfigdemo.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("/home")
public class HomeController implements Controller {
//    @Override
//    public ModelAndView handleRequest(HttpServletRequest request,
//                                      HttpServletResponse response) throws Exception {
//        System.out.println("home ...");
//        return null;
//    }

    // 这地方考虑个问题：怎么样实现类似@ResponseBody的功能呢？
    // 就是想实现直接向body里写数据，而不是返回一个页面。

    // 如果想直接在处理器/控制器里使用response向客户端写回数据,
    // 可以通过返回null来告诉	DispatcherServlet我们已经写出响应了,
    // 不需要它进行视图解析。像下面这样
    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        System.out.println("home ...");
        response.getWriter().write("home controller from body");
        return null; // 返回null告诉视图渲染  直接把body里面的内容输出浏览器即可
    }

}

