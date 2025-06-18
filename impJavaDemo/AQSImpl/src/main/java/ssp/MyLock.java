package ssp;

public interface MyLock {
    public void lock() throws InterruptedException;
    public void unlock();
}
