package ssp.aqsLock;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static  MyLock lock = new SpinLock();
    public static long Test() throws InterruptedException {
        int[] count = new int[]{1000};
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < 10; j ++) {
//                    增加多个线程同时访问count[0]的概率
//                    try {
//                        Thread.sleep(2);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
                    try {
                        lock.lock();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + " --");
                    count[0] --;
                    lock.unlock();
                }
            }, "Thread_"+i));
        }
        long start = System.currentTimeMillis();
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
        long end = System.currentTimeMillis();
        System.out.println(count[0]);
        System.out.println("cost Time : " + (end - start) + " millis");
        return end - start;
    }

    public static long TestReLock() throws InterruptedException {
        int[] count = new int[]{1000};
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < 10; j ++) {
//                    增加多个线程同时访问count[0]的概率
//                    try {
//                        Thread.sleep(2);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
                    try {
                        lock.lock();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + " --");
                    count[0] --;
                }
                for (int j = 0; j < 10; j ++) {
                    lock.unlock();
                }
            }, "Thread_"+i));
        }
        long start = System.currentTimeMillis();
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
        long end = System.currentTimeMillis();
        System.out.println(count[0]);
        System.out.println("cost Time : " + (end - start) + " millis");
        return end - start;
    }
    public static void main(String[] args) throws InterruptedException {
        int test = 5;
        long sum = 0;
        for (int i = 1; i <= test; i ++) {
            long costTime = Test();
//            long costTime = TestReLock();
            sum += costTime;
        }
        System.out.println("ave cost : " + (double)sum / test);
    }
}
