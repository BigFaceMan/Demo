package ssp.synchronous;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class abPrintSpin {
    static Lock lock = new ReentrantLock();
//    static boolean okA = true;
    static int okA = 2;
    static int okB = 0;
    static Condition conditionA = lock.newCondition();
    static Condition conditionB = lock.newCondition();
    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i ++) {
            Thread pA = new Thread(() -> {
                for (int k = 0; k < 100; k ++) {
                    while (true) {
                        lock.lock();
                        if (okA != 0) {
                            break;
                        } else {
                            lock.unlock();
                        }
                    }
                    System.out.print("A");
                    okA --;
                    if (okA == 0) {
                        okB = 2;
                    }
                    lock.unlock();
                }
            });

            Thread pB = new Thread(() -> {
                for (int k = 0; k < 100; k ++) {
                    while (true) {
                        lock.lock();
                        if (okB != 0) {
                            break;
                        } else {
                            lock.unlock();
                        }
                    }
                    System.out.print("B");
                    okB --;
                    if (okB == 0) {
                        okA = 2;
                    }
                    lock.unlock();
                }
            });
            threads.add(pA);
            threads.add(pB);
        }

        long start = System.currentTimeMillis();
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        long end = System.currentTimeMillis();
        System.out.print("\n");
        System.out.println("程序运行时间：" + (end - start) + " 毫秒");
    }
}
