package ssp.jvmTest;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClassLoaderTest {
    class MyClassLoader extends ClassLoader {
        private String classDir;

        public MyClassLoader(String classDir) {
            this.classDir = classDir;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                // 将类名转换成路径，例如 com.example.Hello -> com/example/Hello.class
                String classFilePath = classDir + File.separator + name.replace(".", File.separator) + ".class";
                byte[] classBytes = Files.readAllBytes(Paths.get(classFilePath));

                // 将字节数组转换成 Class 对象
                return defineClass(name, classBytes, 0, classBytes.length);
            } catch (IOException e) {
                throw new ClassNotFoundException("Class not found: " + name, e);
            }
        }
    }
    public void TestMyClassLoader() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        String loadClassPath = "D:\\research\\repositories\\Demo\\JavaCode\\src\\main\\java";
        MyClassLoader loader = new MyClassLoader(loadClassPath);
        Class<?> cl = loader.loadClass("ssp.jvmTest.Hello");
        Object instance = cl.getConstructor().newInstance();
        cl.getMethod("sayHello").invoke(instance);
    }
    public void Test() {
        ClassLoader cld = this.getClass().getClassLoader();
        StringBuilder split = new StringBuilder("|--");
        while (cld != null) {
            System.out.println(split.toString() + cld);
            cld = cld.getParent();
            split.insert(0, "\t");
        }
        System.out.println(split.toString() + cld);
    }
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        ClassLoaderTest clT = new ClassLoaderTest();
//        双亲委派
        clT.Test();
//        自定义类加载器
        clT.TestMyClassLoader();
    }
}
