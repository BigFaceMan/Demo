package ssp;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {
    @Test
    public void createThreadPoolAuto() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(() -> {
            System.out.println("Executors Thread run !!!!!!!!!!!!!!");
        });
    }
    @Test
    public void createThreadPoolManual() throws InterruptedException {
        ThreadPoolExecutor executor =new ThreadPoolExecutor(
                2,
                4,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        // 提交任务
        for (int i = 0; i < 6; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("执行任务: " + taskId + "，由线程：" + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // 模拟任务执行
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // 关闭线程池
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS); // 等最多 10 秒
    }


}
