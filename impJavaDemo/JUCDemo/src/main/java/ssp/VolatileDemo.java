package ssp;

public class VolatileDemo {
    private volatile int count = 0;
    public void add() {
        count++;
    }

    public static void main(String[] args) {
        VolatileDemo volatileDemo = new VolatileDemo();
        volatileDemo.add();
    }
}
