package org.example;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.ReentrantLock;


public class ThreadDemo {
    private volatile int count = 0;
    public void threadVolatile() {
        /*
        1. 可见性  ( 每次读写都直接从主内存中读写，而不是线程本地缓存
        2. 指令重排 （ 满足topsort，而volatile 执行时 会加入内存屏障, 而前面的、后面的指令都会依赖这个指令
        上面这些操作是jvm做的，不是编译器做的（没有对应的字节码
        * */
        count ++;
    }
    
    public void lockAQS() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        System.out.println("lockAQS start");
        lock.unlock();
    }

    public void lockSynchronized() {
        synchronized (this) {
            System.out.println("Sychronized start");
            for (int i = 0; i < 10; i ++) {
                count ++;
            }
            System.out.println("Sychronized end");
        }
    }


    public void threadCreateFuture() {
        FutureTask<Integer> ft = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
//                Thread.sleep(1000);
                System.out.println("i'm Callable Task!!!");
                return 1;
            }
        });
        Thread thread = new Thread(ft);
        thread.start();
//        ft.cancel(true);

        try {
            Integer result = ft.get();
            System.out.println("result is " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void threadStopJoin() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            System.out.println("Thread1 start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread1 stop");

        });

        Thread thread2 = new Thread(() -> {
            try {
                thread1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread2 start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread2 stop");

        });
        thread1.start();
        thread2.start();
    }
    public void threadStopWait() {
        Object obWait = new Object();
        new Thread(() -> {
            System.out.println("threadStopWait Thread start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("threadStopWait Thread stop");
        });
    }

    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo();
        Method[] methods = threadDemo.getClass().getDeclaredMethods();

        for (Method method : methods) {
            if (method.getName().equals("main") || method.getName().startsWith("lambda$")) {
                continue ;
            }
            System.out.println("------ Excute Method " + method.getName() + " ------");
            try {
                method.invoke(threadDemo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
