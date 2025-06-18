package ssp;

import java.util.concurrent.atomic.AtomicInteger;

public class SpinLock implements MyLock {
    AtomicInteger state = new AtomicInteger(0);
    Thread owner = null;
    @Override
    public void lock() {
        while (true) {
            if (state.get() == 0) {
                if (state.compareAndSet(0, 1)) {
                    System.out.println(Thread.currentThread().getName() + " lock");
                    owner = Thread.currentThread();
                    return ;
                }
            } else if (owner == Thread.currentThread()) {
                System.out.println(Thread.currentThread().getName() + " relock, times " + state.incrementAndGet());
                return ;
            }
        }
    }

    @Override
    public void unlock() {
        if (owner != Thread.currentThread()) {
            System.out.println("not your lock!!!");
            return ;
        }
        int cnt = state.get();
        cnt --;
        if (cnt > 0) {
            System.out.println(Thread.currentThread().getName() + "  unlock, remain times : " + cnt);
            state.set(cnt);
            return ;
        } else if (cnt < 0) {
            throw new IllegalStateException("not times, unlock error");
        }

        System.out.println(Thread.currentThread().getName() + "  unlock, remain times : " + cnt);
        owner = null;
        state.set(0);
    }
}
