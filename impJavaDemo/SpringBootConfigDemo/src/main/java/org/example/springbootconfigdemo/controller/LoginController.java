package org.example.springbootconfigdemo.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("/login")
public class LoginController implements HttpRequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request,
                              HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("login...");
        response.getWriter().write("login ...");
    }
}
