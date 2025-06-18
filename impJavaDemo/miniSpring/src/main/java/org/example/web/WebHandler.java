package org.example.web;

import java.lang.reflect.Method;

public class WebHandler {
    private Method method;
    private Object bean;
    enum ResultType {
        JSON, HTML, LOCAL
    }
    private ResultType resultType;


    public WebHandler(Method method, Object bean) {
        this.method = method;
        this.bean = bean;
        this.resultType = resolveResultType(method);
    }
    public ResultType resolveResultType(Method method) {
        if (method.isAnnotationPresent(ResponseBody.class)) {
            return ResultType.JSON;
        } else if (method.getReturnType() == ModelAndlView.class) {
            return ResultType.LOCAL;
        } else {
            return ResultType.HTML;
        }
    }


    public Method getMethod() {
        return method;
    }

    public Object getBean() {
        return bean;
    }

    public ResultType getResultType() {
        return resultType;
    }
}
