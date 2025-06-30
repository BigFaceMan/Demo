package ssp;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@FunctionalInterface
interface Sout<T> {
    void accept(T t);
}
public class CollectionSet {
    public static void main(String[] args) {
        HashMap<String, String> mp = new HashMap<>();
        mp.put("key1", "value1");
        mp.put("key2", "value2");
        mp.put("key3", "value3");
        mp.get("key1");
        /*
        遍历 Map
        接口、你不需要记住每一个，只需要记住root接口，子类会自动实现，也就是你只需要知道输入、输出 中间的部分怎么操作，无所谓
        */
        for (Map.Entry<String, String> entry : mp.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }

        Iterator<Map.Entry<String, String>> iterator = mp.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }

        for (String key : mp.keySet()) {
            System.out.println("Key: " + key + ", Value: " + mp.get(key));
        }

        for (String value : mp.values()) {
            System.out.println("Value: " + value);
        }

        mp.forEach((key, value) -> {
            System.out.println("Key: " + key + ", Value: " + value);
        });

        CopyOnWriteArrayList<Integer> cowList = new CopyOnWriteArrayList<>();


        ArrayList<String> alist = new ArrayList<>();
        List<String> a = Collections.synchronizedList(alist);
        alist.listIterator();
        Iterator<String> iterator1 = alist.iterator();
        Sout<String> sout = (msg) -> {
            if (msg == null || msg.isEmpty()) {
                System.out.println("Message is empty");
            } else {
                System.out.println(msg);
            }
        };
        sout.accept("Hello, World!");

/*
    Queue 和 Deque 的区别 Deque extends Queue 可以在两边弄
* */
        Deque<String> deque = new java.util.ArrayDeque<>();
        Queue<String> queue = new java.util.LinkedList<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
// true的条件来swap heap，e1 - e2 < 0
        pq.add(1);
        pq.add(2);
        pq.add(3);
        System.out.println(pq.poll());

        Hashtable<Object, Object> objectObjectHashtable = new Hashtable<>();
        /*
            线程安全hashmap
        * */
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("key1", 1);



    }
}
