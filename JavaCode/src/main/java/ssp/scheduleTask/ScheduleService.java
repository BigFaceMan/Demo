package ssp.scheduleTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class ScheduleService {
    ExecutorService executorsService = Executors.newFixedThreadPool(6);

    class Job implements Comparable<Job> {
        Runnable task;
        long runTime;
        long waitTime;
        Job(Runnable task, long waitTime) {
            this.task = task;
            this.waitTime = waitTime;
            this.runTime = System.currentTimeMillis() + waitTime;
        }
        @Override
        public int compareTo(Job o) {
            return Long.compare(this.runTime, o.runTime);
        }
    }
    class Trigger {
        PriorityBlockingQueue<Job> queue = new PriorityBlockingQueue<>();
        Thread t = new Thread(() -> {
            while (true) {
                while (queue.isEmpty()) {
                    LockSupport.park();
                }
                Job nowJob = queue.peek();
                while (nowJob.runTime > System.currentTimeMillis()) {
                    LockSupport.parkUntil(nowJob.runTime);
                }
                Job getJob = queue.poll();
                executorsService.submit(getJob.task);
                queue.offer(new Job(getJob.task, getJob.waitTime));
            }
        });
        {
            t.start();
            System.out.println("触发器启动了");
        }
        public void wakeUp() {
            LockSupport.unpark(t);
        }

    }
    Trigger trigger = new Trigger();
    public void schedule(Runnable task, long delay) {
        trigger.queue.offer(new Job(task, delay));
        trigger.wakeUp();
    }
}
