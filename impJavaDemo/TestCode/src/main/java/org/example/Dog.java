package org.example;

public class Dog {
    private int weight;
    public Dog(int weight) {
        this.weight = weight;
    }

    public static String maxDog(Dog d1, Dog d2) {
        if (d1.weight > d2.weight) {
            return "d1 win";
        }
        return "d2 win";
    }

    public static void main(String[] args) {
        Dog d = new Dog(10);
        Dog d2 = new Dog(11);
        System.out.println(Dog.maxDog(d, d2));
    }

}
