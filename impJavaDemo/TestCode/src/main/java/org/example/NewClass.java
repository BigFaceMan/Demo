package org.example;

import java.lang.reflect.InvocationTargetException;

public class NewClass {
    private static int instanceCount = 0;
    NewClass() {
        instanceCount++;
        System.out.println("NewClass instance created. Total instances: " + instanceCount);
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        NewClass newClass1 = new NewClass();
        NewClass newClass2 = (NewClass) Class.forName("org.example.NewClass").newInstance();
        NewClass newClass3 = (NewClass) Class.forName("org.example.NewClass").getDeclaredConstructor().newInstance();

    }
}
