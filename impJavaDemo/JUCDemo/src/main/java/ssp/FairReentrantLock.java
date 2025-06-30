package ssp;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

public class FairReentrantLock {
    private static class Sync extends AbstractQueuedSynchronizer {

        protected boolean isHeldExclusively() {
            return Thread.currentThread() == getExclusiveOwnerThread();
        }
        @Override
        protected boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (!hasQueuedPredecessors() && compareAndSetState(0, acquires)) {
                    return true;
                }
            } else {
                if (getExclusiveOwnerThread() == current) {
                    c += acquires;
                    setState(c);
                    return true;
                }
            }
            return false;
        }
        protected boolean tryRelease(int acquired) {
            final Thread current = Thread.currentThread();
            if (current != getExclusiveOwnerThread()) {
                throw new IllegalStateException("不是你的锁");
            }
            int c = getState() - acquired;
            if (c < 0) {
                throw new IllegalStateException("释放太多了");
            }
            boolean unlock = false;
            if (c == 0) {
                unlock = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return unlock;
        }

        public Condition newCondition() {
            return new ConditionObject();
        }
    }
    private final Sync sync = new Sync();

    // 加锁方法
    public void lock() {
        sync.acquire(1);
    }

    // 解锁方法
    public void unlock() {
        sync.release(1);
    }

    // 判断当前线程是否持有锁
    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    // 提供一个条件变量，用于实现更复杂的同步需求，这里只是简单实现
    public Condition newCondition() {
        return sync.newCondition();
    }
}
