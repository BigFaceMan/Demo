package ssp.miniSpring;


import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationContext {
    private final HashMap<String, Object> ioc = new HashMap<>();
    private final HashMap<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private final HashMap<String, Object> loadingMap = new HashMap<>();
    private final List<BeanPostProcessor> postProcessors = new ArrayList<>();

    public ApplicationContext(String packageName) throws Exception {
        initContext(packageName);
    }

    private void initContext(String packageName) throws Exception {
//        List<Class<?>> classList = scanPackage(packageName);
        scanPackage(packageName).stream().filter(this::canCreate).forEach(this::wrapper);
        initBeanPostProcessor();
        beanDefinitionMap.values().stream().forEach(this::createBean);
//        for (Class cl : classList)
//            System.out.println("get class : " + cl);
    }

    private void initBeanPostProcessor() {
        beanDefinitionMap.values().stream()
                .filter(bd -> BeanPostProcessor.class.isAssignableFrom(bd.getType()))
                .map(this::createBean)
                .map(BeanPostProcessor.class::cast)
                .forEach(postProcessors::add);
    }

    private Object initializeBean(Object bean, BeanDefinition beanDefinition) throws Exception {
        for (BeanPostProcessor beanPostProcessor : postProcessors) {
            bean = beanPostProcessor.beforeInitializeBean(bean, beanDefinition.getName());
        }

        Object finalBean = bean;
        beanDefinition.getPostMethod().stream().forEach(bd -> {
            try {
                bd.invoke(finalBean);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

        for (BeanPostProcessor beanPostProcessor : postProcessors) {
            bean = beanPostProcessor.afterInitializeBean(bean, beanDefinition.getName());
        }
        return bean;
    }
    private void autowiredBean(Object ob, List<Field> autowiredFields) {
        for (Field fd : autowiredFields) {
            fd.setAccessible(true);
            Object findBean = getBean(fd.getType());
//            System.out.println("get bean : " + findBean);
            try  {
                fd.set(ob, findBean);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected Object createBean(BeanDefinition beanDefinition) {
        String name = beanDefinition.getName();
        if (name == null) return null;
        if (ioc.containsKey(name)) return ioc.get(name);
        if (loadingMap.containsKey(name)) return loadingMap.get(name);
        return doCreate(beanDefinition);
    }

    protected BeanDefinition wrapper(Class<?> cl){
        BeanDefinition beanDefinition = new BeanDefinition(cl);
        if (beanDefinitionMap.containsKey(beanDefinition.getName())) {
            throw new RuntimeException("Bean名字重复");
        }
        beanDefinitionMap.put(beanDefinition.getName(), beanDefinition);
        return beanDefinition;
    }
    private Object doCreate(BeanDefinition beanDefinition){
        Constructor<?> cs = beanDefinition.getConstructor();
        Object instance = null;
        try {
            instance = cs.newInstance();
            loadingMap.put(beanDefinition.getName(),  instance);
            autowiredBean(instance, beanDefinition.getAutowiredFields());
            initializeBean(instance, beanDefinition);
            ioc.put(beanDefinition.getName(), loadingMap.remove(beanDefinition.getName()));
//            Object finalInstance = instance;
//            beanDefinition.getPostMethod().stream().forEach(md -> {
//                try {
//                    md.invoke(finalInstance);
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                } catch (InvocationTargetException e) {
//                    throw new RuntimeException(e);
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    private boolean canCreate(Class<?> cl) {
        return cl.isAnnotationPresent(Component.class);
    }
    private List<Class<?>> scanPackage(String packageName) throws Exception {
        List<Class<?>> classList = new ArrayList<>();
        URL resources = this.getClass().getClassLoader().getResource(packageName.replace('.', File.separatorChar));
        Path path = Paths.get(resources.toURI());
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                System.out.println("get dir file : " + file.toString());
                Path absolutePath = file.toAbsolutePath();
//                System.out.println("get dir abs file : " + absolutePath.toString());
                if (file.toString().endsWith(".class")) {
                    String classFullPath = file.toString().replace(File.separatorChar, '.');
                    int packageIndex = classFullPath.indexOf(packageName);
                    String classLoadPath = classFullPath.substring(packageIndex, classFullPath.length() - ".class".length());
                    try {
                        classList.add(Class.forName(classLoadPath));
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return classList;
    }
    public Object getBean(String name) {
        Object ob = beanDefinitionMap.get(name);
//        System.out.println("beanDefinitionMap : " + beanDefinitionMap);
//        System.out.println("iocMap : " + ioc);
        if (ob != null) {
            if (ioc.get(name) == null) {
                ob = createBean((BeanDefinition) ob);
            } else {
                ob = ioc.get(name);
            }
        }
        return ob;
    }

    public <T> T getBean(Class<T> beanType) {
        BeanDefinition findBd = beanDefinitionMap.values().stream()
                .filter(bd -> beanType.isAssignableFrom(bd.getType()))
                .findFirst()
                .orElse(null);
        if (findBd != null) {
            return (T) getBean(findBd.getName());
        }
        return null;
    }

    public <T> List<T> getBeans(Class<T> beanType) {
        List<BeanDefinition> findBds = beanDefinitionMap.values().stream()
                .filter(bd -> beanType.isAssignableFrom(bd.getType()))
                .collect(Collectors.toList());
        List<T> beans = new ArrayList<>();
        if (findBds != null) {
            for (BeanDefinition findBd : findBds) {
                beans.add((T) getBean(findBd.getName()));
            }
            return beans;
        }
        return null;
    }
}
