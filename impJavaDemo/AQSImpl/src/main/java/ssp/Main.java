package ssp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static final Object obLock = new Object();
    static  MyLock lock = new AQSLock();
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
//                    case1: unlock
//                    count[0] --;
//                    case2: lock
                    try {
                        lock.lock();
                        System.out.println(Thread.currentThread().getName() + " --");
                        count[0] --;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        lock.unlock();
                    }

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
    public static void main(String[] args) throws InterruptedException, IOException {
        int test = 5;
        long sum = 0;
        for (int i = 1; i <= test; i ++) {
            long costTime = Test();
//            long costTime = TestReLock();
            sum += costTime;
        }
        String outputContent = "test " + test + " times, cost time : " + sum + " millis " + "ave cost : " + (double)sum / test + " millis";
        System.out.println(outputContent);
        String classPath = Main.class.getClassLoader().getResource("").getPath();
        File classFile = new File(classPath);
        String outputPath = classFile.getAbsolutePath() + File.separator + "testLog.txt";
        File file = new File(outputPath);
        file.createNewFile();
        try (OutputStream outputStream = new FileOutputStream(file, true)) {
            outputStream.write(outputContent.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.flush();
        }
    }
}
