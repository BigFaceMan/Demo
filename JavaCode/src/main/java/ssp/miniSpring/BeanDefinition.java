package ssp.miniSpring;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BeanDefinition {
    private Constructor constructor;
    private List<Method> postMethod = null;
    private Class<?> type;
    private String name;
    private List<Field> autowiredFields = null;

    public BeanDefinition(Class<?> cl) {
        try {
            this.constructor = cl.getConstructor();
            this.type = cl;
            this.name = cl.getDeclaredAnnotation(Component.class).name().equals("") ? cl.getSimpleName() : cl.getDeclaredAnnotation(Component.class).name();
//            System.out.println("Beandefinition name is : " + this.name);
            this.autowiredFields = Arrays.stream(cl.getDeclaredFields()).filter(fd -> fd.isAnnotationPresent(Autowired.class)).collect(Collectors.toList());
//            System.out.println("Autowired len : " + this.autowiredFields.size());
            this.postMethod = Arrays.stream(cl.getDeclaredMethods())
                    .filter(md -> md.isAnnotationPresent(PostConstruct.class))
                    .collect(Collectors.toList());
//            System.out.println("PostConstruct len : " + postMethod.size());
        } catch(Exception e) {
            e.printStackTrace();
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

    public List<Method> getPostMethod() {
        return postMethod;
    }
}
