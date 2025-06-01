package Structural;

// 实现部分 - 颜色接口
interface Color {
    void applyColor();
}

class Red implements Color {
    public void applyColor() {
        System.out.println("Applying red color");
    }
}

class Blue implements Color {
    public void applyColor() {
        System.out.println("Applying blue color");
    }
}

// 抽象部分 - 形状类
abstract class Shape {
    protected Color color;

    public Shape(Color color) {
        this.color = color;
    }

    abstract void draw();
}

class Circle extends Shape {
    public Circle(Color color) {
        super(color);
    }

    public void draw() {
        System.out.print("Drawing a circle. ");
        color.applyColor();
    }
}

class Square extends Shape {
    public Square(Color color) {
        super(color);
    }

    public void draw() {
        System.out.print("Drawing a square. ");
        color.applyColor();
    }
}

// 在这个示例中，Color 接口代表颜色的实现部分，Red 和 Blue 分别是实现了颜色接口的具体颜色类。
// Shape 是形状的抽象部分，具有一个颜色引用，而 Circle 和 Square 是继承自 Shape 的具体形状类。
// 这种设计允许我们在不改变形状或颜色的情况下，独立地对它们进行扩展和变化。
public class Bridge {
    public static void main(String[] args) {
        Color redColor = new Red();
        Color blueColor = new Blue();

        Shape redCircle = new Circle(redColor);
        Shape blueSquare = new Square(blueColor);

        redCircle.draw();
        blueSquare.draw();
    }
}
