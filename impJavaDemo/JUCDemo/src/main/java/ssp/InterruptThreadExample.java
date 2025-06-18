package ssp;

import java.util.concurrent.locks.LockSupport;

public class InterruptThreadExample implements Runnable {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Running...");
//                Thread.sleep(500);  // 会响应中断
        }
        System.out.println("Thread exiting normally.");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new InterruptThreadExample());
        t.start();

        Thread.sleep(2000);
        t.interrupt();  // 发出中断信号
        Integer result = 1;
        t.join();
        result.wait();
        LockSupport.park();
        Thread.sleep(1000);  // 等待线程结束
    }
}
