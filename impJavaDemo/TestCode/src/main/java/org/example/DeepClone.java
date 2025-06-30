package org.example;

import java.io.*;

// Person 类支持序列化（用于深拷贝）
class Person implements Serializable, Cloneable{
    private String name;
    private int age;
    private Address address;

    public Person(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() { return name; }
    public Address getAddress() { return address; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setAddress(Address address) { this.address = address; }

//    clone 只能clone一层
    @Override
    protected Object clone() throws CloneNotSupportedException {
//        深拷贝
//        Person p = (Person) super.clone();
//        p.address = (Address) address.clone(); // 深拷贝地址对象
//        return p;
//        浅拷贝
        return super.clone();
    }

    public String toString() {
        return name + " (" + age + "), Address: " + address;
    }
}

// 嵌套对象，也要实现 Serializable
class Address implements Serializable, Cloneable {
    private String city;

    public Address(String city) {
        this.city = city;
    }

    public void setCity(String city) { this.city = city; }

    public String toString() {
        return city;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Address ad = (Address) super.clone();
        return ad;
    }
}

public class DeepClone {

    // 利用序列化实现深拷贝
    public static <T> T deepClone(T obj) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // 装饰器模式
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);

            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Deep clone failed", e);
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Address address = new Address("Shanghai");
        Person original = new Person("Tom", 25, address);

        // 深拷贝对象
//        Person clone = deepClone(original);
//        浅拷贝
        Person clone = (Person) original.clone();

//        System.out.println(clone.getName().hashCode());
//        System.out.println(original.getName().hashCode());

        System.out.println(clone.getAddress().hashCode());
        System.out.println(original.getAddress().hashCode());

        // 修改原对象的地址
        original.setName("Jerry");
        original.setAge(30);
        address.setCity("Beijing");

//        System.out.println(clone.getName().hashCode());
//        System.out.println(original.getName().hashCode());
        System.out.println(clone.getAddress().hashCode());
        System.out.println(original.getAddress().hashCode());
        System.out.println("Original: " + original); // Jerry, Beijing
        System.out.println("Clone   : " + clone);    // Tom, Shanghai
    }
}
