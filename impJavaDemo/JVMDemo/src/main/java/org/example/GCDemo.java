package org.example;

public class GCDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Process Run");
        Thread.sleep(30000);
        byte[] arrays = new byte[1024 * 1024 * 10]; // 1MB
        System.out.println("object created");
        Thread.sleep(30000);
        arrays = null; // 释放引用
        System.gc(); // 建议进行垃圾回收
        System.out.println("object released and gc");
        Thread.sleep(300000); // 等待观察GC行为
    }
}
