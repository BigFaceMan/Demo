package ssp.threadPool;

public interface RejectHandle {
    void reject(Runnable task, MyThreadPool threadPool);

}
