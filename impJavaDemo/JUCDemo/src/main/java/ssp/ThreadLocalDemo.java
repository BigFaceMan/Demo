package ssp;

import java.util.ArrayList;
import java.util.List;

/*
*   ThreadLocal Demo 每个Thread 拥有自己独立的副本
*
* */
public class ThreadLocalDemo {
    public void localGetData(String[] args) {
        ThreadLocal<Integer> tl = new ThreadLocal<Integer>();
        tl.set(123);
        List<Thread> threads = new ArrayList<>();
        int[] num = new int[1];
        for (int i = 0; i < 100; i ++) {
            threads.add(new Thread(() -> {
                tl.set(num[0] ++);
                System.out.println(tl.get());
            }));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Main thread value: " + tl.get());
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                // 创建 ThreadLocal 并设置值
                ThreadLocal<String> threadLocal = new ThreadLocal<>();
                threadLocal.set("Hello, WeakReference!");

                System.out.println("Before null: ");

                inspectThreadLocals();
                // 断开强引用
                threadLocal = null;

                // 强制 GC
                System.gc();

                // 等待一会让 GC 生效
                Thread.sleep(1000);

                // 用反射访问 ThreadLocalMap，看看里面的 key 是否变为 null
                inspectThreadLocals();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        t.start();
        t.join();  // 等子线程跑完
    }

    private static void inspectThreadLocals() throws Exception {
        Thread currentThread = Thread.currentThread();

       // 通过反射拿到 Thread.threadLocals
        java.lang.reflect.Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
        threadLocalsField.setAccessible(true);
        Object threadLocalMap = threadLocalsField.get(currentThread);

        // 拿到 ThreadLocalMap.table 数组
        Class<?> threadLocalMapClass = Class.forName("java.lang.ThreadLocal$ThreadLocalMap");
        java.lang.reflect.Field tableField = threadLocalMapClass.getDeclaredField("table");
        tableField.setAccessible(true);
        Object[] table = (Object[]) tableField.get(threadLocalMap);

        for (Object entry : table) {
            if (entry != null) {
                // 获取 Entry.key（弱引用中的 referent）
                java.lang.reflect.Field referentField = java.lang.ref.Reference.class.getDeclaredField("referent");
                referentField.setAccessible(true);
                Object key = referentField.get(entry);

                // 获取 Entry.value
                java.lang.reflect.Field valueField = entry.getClass().getDeclaredField("value");
                valueField.setAccessible(true);
                Object value = valueField.get(entry);

                System.out.println("Entry: key = " + key + ", value = " + value);
            }
        }
    }
}
