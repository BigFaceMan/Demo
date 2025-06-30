package org.example;

import java.util.HashSet;

// 自动装箱 拆箱 Test
// 编译后有对应的指令进行装箱和拆箱
public class AutoBox {
    public static void main(String[] args) {
        Long time = System.currentTimeMillis();
        int round = 100000000;
        Integer sum = 0;
        for (int i = 0; i < round; i ++) {
            sum += i;
        }
        System.out.println("cost time have box : " + (System.currentTimeMillis() - time) + " millis");
        time = System.currentTimeMillis();
        int sumI = 0;
        for (int i = 0; i < round; i ++) {
            sumI += i;
        }
        System.out.println("cost time no box : " + (System.currentTimeMillis() - time) + " millis");
        HashSet<Object> objects = new HashSet<>();
    }
}
