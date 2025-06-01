package Creational;

import java.util.ArrayList;
import java.util.List;

class LogSingleton {
/*
1. 饿汉式 类加载就创建实例    不会有并发安全问题，浪费资源
2. 懒汉式 第一次用到才创建    有并发安全问题
3. 懒汉式 + synchronized   直接对方法加锁，线程安全， 但是慢
4. DCL                    先==判断，再上锁 判断== 创建  效率高 要用volatile防止指令重排列
5. 内部静态类               也是第一次使用才加载
 */
    // 私有静态成员变量，用于保存单例实例
    private static LogSingleton instance;
    private static int cnt;


    // 私有构造方法，防止外部实例化
    private LogSingleton() {
        cnt ++;
        // 初始化操作
    }

    // 公共静态方法，用于获取单例实例
//    并发问题
    public static LogSingleton getInstance() {
        if (instance == null) {
            // 如果实例为空，则创建一个新实例
            instance = new LogSingleton();
        }
        return instance;
}

//加锁
//    public static synchronized LogSingleton getInstance() {
//        if (instance == null) {
//            // 如果实例为空，则创建一个新实例
//            instance = new LogSingleton();
//        }
//        return instance;
//    }

//    双锁
//    public static LogSingleton getInstance() {
//        if (instance == null) {
//            synchronized(LogSingleton.class) {
//                if (instance == null) {
//                    instance = new LogSingleton();
//                }
//            }
//        }
//        return instance;
//    }

//   内部静态类，利用类加载的机制
//    private static class Holder {
//        private static final LogSingleton logSingleTon = new LogSingleton();
//    }
//    public static LogSingleton getInstance() {
//        return Holder.logSingleTon;
//    }

    public void showMessage() {
        System.out.println("Hello, I am a Singleton : " + cnt + " !");
    }
}
// 这个示例演示了如何创建一个简单的单例模式
// 但请注意，这个实现并不是线程安全的。
// 在多线程环境中，可能会出现多个线程同时访问getInstance()方法，导致创建多个实例的情况。
// 为了实现线程安全的单例模式，可以使用双重检查锁定或其他同步机制。
public class Singleton {
    public static void main(String[] args) throws InterruptedException {
        List<Thread> getInstanceList = new ArrayList<>();
        int numThread = 1000;
        for (int i = 0; i < numThread; i ++) {
            getInstanceList.add(
            new Thread(()-> {
                for (int j = 0; j < 100000; j ++) {
                    LogSingleton singleton = LogSingleton.getInstance();
                }
            }));
        }
        long startT = System.currentTimeMillis();
        for (Thread t : getInstanceList) {
            t.start();
        }

        for (Thread t : getInstanceList) {
            t.join();
        }

        long endT = System.currentTimeMillis();
        System.out.println("cost Time : " + (endT - startT));
        // 获取单例实例
        LogSingleton singleton = LogSingleton.getInstance();
        // 调用成员方法
        singleton.showMessage();
    }
}
