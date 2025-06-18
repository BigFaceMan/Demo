package ssp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class MyThreadPool {
    private final int corePoolSize;
    private final int maxSize;
    private final int timeout;
    private final TimeUnit timeUnit;
    public final BlockingQueue<Runnable> blockingQueue;
    private final RejectHandle rejectHandle;

    public MyThreadPool(int corePoolSize, int maxSize, int timeout, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue, RejectHandle rejectHandle) {
        this.corePoolSize = corePoolSize;
        this.maxSize = maxSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.blockingQueue = blockingQueue;
        this.rejectHandle = rejectHandle;
    }

    class CoreThread extends Thread {
        private final Runnable firstTask;

        public CoreThread(Runnable firstTask, String name) {
            super(name);
            this.firstTask = firstTask;
        }

        @Override
        public void run() {
            firstTask.run();
            while (true) {
                try {
                    Runnable task = blockingQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    class SupportThread extends Thread {
        private final Runnable firstTask;

        public SupportThread(Runnable firstTask, String name) {
            super(name);
            this.firstTask = firstTask;
        }

        @Override
        public void run() {
            firstTask.run();
            while (true) {
                try {
                    Runnable task = blockingQueue.poll(timeout, timeUnit);
                    if (task == null) {
                        break;
                    }
                    task.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(Thread.currentThread().getName() + " support线程结束了!!!");
            supportList.remove(Thread.currentThread());
        }
    }

    List<CoreThread> coreList = new ArrayList<>();
    List<SupportThread> supportList = new ArrayList<>();

    public synchronized void execute(Runnable task) {
        if (coreList.size() < corePoolSize) {
            CoreThread nCoreThread = new CoreThread(task, "coreThread_" + coreList.size());
            coreList.add(nCoreThread);
            nCoreThread.start();
        }
        if (blockingQueue.offer(task)) return;
        if (coreList.size() + supportList.size() < maxSize) {
            SupportThread nCoreThread = new SupportThread(task, "supportThread_" + supportList.size());
            supportList.add(nCoreThread);
            nCoreThread.start();
        } else if (!blockingQueue.offer(task)) {
            rejectHandle.reject(task, this);
        }
    }
}
