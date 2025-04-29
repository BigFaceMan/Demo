package ssp.jucUtils;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        int threadCount = 3;
        int waitCount = 2;
        CountDownLatch latch = new CountDownLatch(waitCount);

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 执行任务");
                    latch.countDown(); // 任务完成，计数器-1
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, "Worker-" + i).start();
        }

        latch.await(); // 主线程等待所有子线程完成任务
        System.out.println("所有任务已完成");
    }
}