package ssp.jvmTest;

import com.sun.javafx.UnmodifiableArrayList;
import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class GCTest {
    @Test
    public void referenceT() {
        ReferenceQueue<String> queue = new ReferenceQueue<>();
        PhantomReference<String> phantomRef = new PhantomReference<>(new String("abc"), queue);

        System.out.println(phantomRef.get()); // 永远是 null

        System.gc();
        Reference<?> ref = queue.poll(); // 如果对象已准备被回收，会被加入队列
        if (ref != null) {
            System.out.println("对象即将被回收，做一些清理工作...");
        }
    }
    public static void main(String[] args) {
        byte[] allocation1, allocation2;
        int size = 42900 * 1024;
//        int size = 1024 * 1024 * 1024 * 10;
        allocation1 = new byte[size];
        System.out.println(allocation1[size - 1]);
    }
}
