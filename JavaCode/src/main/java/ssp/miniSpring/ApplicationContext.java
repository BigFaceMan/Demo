package ssp.miniSpring;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

public class ApplicationContext {
    private final HashMap<String, Object> ioc = new HashMap<>();
    public ApplicationContext(String packageName) throws Exception {
        initContext(packageName);
    }

    private void initContext(String packageName) throws Exception {
//        List<Class<?>> classList = scanPackage(packageName);
        scanPackage(packageName).stream().filter(this::canCreate).forEach(this::doCreate);
//        for (Class cl : classList)
//            System.out.println("get class : " + cl);
    }

    private void doCreate(Class<?> cl){
        String name = cl.getAnnotation(Component.class).name().equals("") ? cl.getSimpleName() : cl.getAnnotation(Component.class).name() ;

//        String name = cl.getSimpleName();
//        System.out.println("put Object : " + name);
//        System.out.println("get simpleName : " + cl.getSimpleName());
//        System.out.println("get Annotation : " + cl.getDeclaredAnnotation(Component.class).name() == null);
        Object ob = ioc.get(name);
        if (ob == null) {
            try {
                ob = cl.getConstructor().newInstance();
                ioc.put(name, ob);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        return ob;
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
        Object ob = ioc.get(name);
        System.out.println(ioc);
        return ob;
    }

    public <T> T getBean(Class<T> beanType) {
        return null;
    }

    public <T> List<T> getBeans(Class<T> beanType) {
        return null;
    }
}
