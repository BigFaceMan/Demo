package ssp;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class AQSLock implements MyLock {
    AtomicInteger state = new AtomicInteger(0);
    Thread owner = null;
    class Node {
        Thread t;
        Node pre;
        Node next;
        public Node(Thread t) {
            this.t = t;
        }
    }

    //    head tail
//    head -> T1 -> T2(tail)
    AtomicReference<Node> head = new AtomicReference<>(new Node(null));
    AtomicReference<Node> tail = new AtomicReference<>(head.get());

    @Override
    public void lock() {
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

        Node current = new Node(Thread.currentThread());
        while (true) {
            Node currentTail = tail.get();
            if (tail.compareAndSet(currentTail, current)) {
                System.out.println(Thread.currentThread().getName() + " block");
                current.pre = currentTail;
                currentTail.next = current;
                break;
            }
        }
//        while (true) {
//            if (current.pre == head.get() && state.compareAndSet(0, 1)) {
//                head.set(current);
//                owner = current.t;
////                    gc
//                current.pre.next = null;
//                current.pre = null;
//                break;
//            }
//            LockSupport.park();
//        }


        while (true) {
            if (current.pre == head.get() && state.compareAndSet(0, 1)) {
                head.set(current);
                owner = current.t;
//                    gc
                current.pre.next = null;
                current.pre = null;
                break;
            }

            LockSupport.park();
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

        Node turnNode = head.get().next;
        System.out.println(Thread.currentThread().getName() + "  unlock, remain times : " + cnt);
        owner = null;
        state.set(0);
        if (turnNode != null) {
            System.out.println(Thread.currentThread().getName() + " unblock : " + turnNode.t.getName());
            LockSupport.unpark(turnNode.t);
        }
    }
}
