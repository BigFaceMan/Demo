package org.example.web;

import com.alibaba.fastjson2.JSONObject;
import org.example.BeanPostProcessor;
import org.example.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class DispatcherServlet extends HttpServlet implements BeanPostProcessor {
    private static final Pattern PATTERN = Pattern.compile("ssp\\{(.*?)}");
    private Map<String, WebHandler> webHandlerMap = new HashMap<>();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pattern = req.getRequestURI();
        if (!webHandlerMap.containsKey(pattern)) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("<h1>访问的地址不存在</h1>");
            return;
        }
        WebHandler webHandler = webHandlerMap.get(pattern);
        Object[] args = resolveArgs(req, webHandler.getMethod());
        Object result = null;
        try {
            result = webHandler.getMethod().invoke(webHandler.getBean(), args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        if (webHandler.getResultType() == WebHandler.ResultType.JSON) {
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write(JSONObject.toJSONString(result));
        } else if (webHandler.getResultType() == WebHandler.ResultType.LOCAL) {
            String mv = ((ModelAndlView)result).getView();
            InputStream resourceStream =  this.getClass().getClassLoader().getResourceAsStream(mv);
            byte[] buffer = new byte[4096];
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int length;
            while ((length = resourceStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            String html = renderTemplate(outputStream.toString(), ((ModelAndlView)result).getContext());
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write(outputStream.toString());
        } else {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write((String) result);
        }

    }
    private String renderTemplate(String template, Map<String, String> context) {
        Matcher matcher = PATTERN.matcher(template);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = context.getOrDefault(key, "");
            matcher.appendReplacement(sb, Matcher.quoteReplacement(value));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    private Object[] resolveArgs(HttpServletRequest req, Method method) {
        Object[] args = new Object[method.getParameterCount()];
        for (int i = 0; i < method.getParameterCount(); i ++) {
            Parameter pm = method.getParameters()[i];
            String pmName = null;
            if (pm.isAnnotationPresent(Param.class)) {
                pmName = pm.getDeclaredAnnotation(Param.class).value();
            } else {
                pmName = pm.getName();
            }
            String value = req.getParameter(pmName);
            Class<?> paramType = pm.getType();
            if (String.class.isAssignableFrom(paramType)) {
                args[i] = value;
            } else if (Integer.class.isAssignableFrom(paramType)) {
                args[i] = Integer.parseInt(value);
            } else {
                args[i] = null;
            }
        }
        return args;
    }

    /*
        寻找所有Controller类中的RequestMapping方法，并生成WebHandler
     */
    @Override
    public Object afterInitializeBean(Object bean, String beanName) {
        if (bean.getClass().isAnnotationPresent(Controller.class)) {
            List<Method> mdList = Arrays.stream(bean.getClass().getMethods()).filter(md -> md.isAnnotationPresent(RequestMapping.class)).collect(Collectors.toList());
            for (Method md : mdList) {
                genWebhandler(md, bean);
            }
        }
        return bean;
    }
    public void genWebhandler(Method md, Object bean) {
        String pattern = md.getAnnotation(RequestMapping.class).value();
        webHandlerMap.put(pattern, new WebHandler(md, bean));
    }
}
