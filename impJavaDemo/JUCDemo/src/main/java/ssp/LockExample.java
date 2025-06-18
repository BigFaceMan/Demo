package ssp;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {
    public void lockMethod() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<>();
        integerThreadLocal.get();
    }
}

