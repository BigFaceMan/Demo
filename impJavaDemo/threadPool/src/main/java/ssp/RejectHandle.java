package ssp;

public interface RejectHandle {
    void reject(Runnable task, MyThreadPool threadPool);

}
