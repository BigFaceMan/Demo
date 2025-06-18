package ssp;


import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class myThread extends Thread {
    @Override
    public void run() {
        System.out.println("继承Thread类创建线程");
    }
}

public class ThreadCreate {

    @Test
    public void createThread() {
        Thread t1 = new Thread(() -> {
            System.out.println("Lambda表达式创建线程");
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("匿名内部类创建线程");
            }
        });
        t2.start();

        Thread t3 = new myThread();
        t3.start();
        FutureTask<Integer> futureTask = new FutureTask<>(()-> {
            System.out.println("FutureTask创建线程");
            return 1;
        });
        Thread t4 = new Thread(futureTask);
        t4.start();
        try {
            Integer result = futureTask.get();
            System.out.println("FutureTask的结果: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(() -> {
            System.out.println("线程池创建线程");
        });

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2, 4, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2),
                new ThreadPoolExecutor.DiscardPolicy());
        threadPoolExecutor.execute(() -> {
            System.out.println("自定义线程池创建线程");
        });

        threadPoolExecutor.shutdown();
    }
}
