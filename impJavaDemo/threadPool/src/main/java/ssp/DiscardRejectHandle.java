package ssp;

public class DiscardRejectHandle implements RejectHandle {
    @Override
    public void reject(Runnable task, MyThreadPool threadPool) {
        threadPool.blockingQueue.poll();
        threadPool.execute(task);
    }
}
