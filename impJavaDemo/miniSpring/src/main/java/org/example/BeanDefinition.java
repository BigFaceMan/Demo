package org.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BeanDefinition {
    private Constructor constructor;
    private List<Method> postConstructMethod = null;
    private Class<?> type;
    private String name;
    private List<Field> autowiredFields = null;

    public BeanDefinition(Class<?> cl) {
        try {
            this.constructor = cl.getConstructor();
            this.type = cl;
            this.name = cl.getDeclaredAnnotation(Component.class).name().isEmpty() ? cl.getSimpleName() : cl.getDeclaredAnnotation(Component.class).name();
            this.autowiredFields = Arrays.stream(cl.getDeclaredFields())
                    .filter(fd -> fd.isAnnotationPresent(Autowired.class))
                    .collect(Collectors.toList());
            this.postConstructMethod = Arrays.stream(cl.getDeclaredMethods())
                    .filter(md -> md.isAnnotationPresent(PostConstruct.class))
                    .collect(Collectors.toList());

        } catch(NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    public Constructor getConstructor() {
        return this.constructor;
    }
    public String getName() {
        return this.name;
    }
    public Class<?> getType() {
        return this.type;
    }
    public List<Field> getAutowiredFields() {
        return this.autowiredFields;
    }

    public List<Method> getPostConstructMethod() {
        return this.postConstructMethod;
    }
}
