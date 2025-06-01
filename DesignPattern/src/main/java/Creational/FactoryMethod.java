package Creational;

// 首先，我们需要定义一个图形接口
interface Shape {
    void draw();
}
// 然后，我们实现两个具体的图形类，分别是 Circle（圆形）和 Rectangle（矩形）
class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a circle");
    }
}

class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a rectangle");
    }
}
// 接下来，我们创建一个抽象工厂类 ShapeFactory
// 它定义了一个抽象的工厂方法 createShape，子类将实现这个方法来创建具体的图形对象
abstract class ShapeFactory {
    abstract Shape createShape();
}
// 然后，我们创建两个具体的工厂类，分别是 CircleFactory 和 RectangleFactory
// 它们分别实现了 ShapeFactory 并重写了 createShape 方法来返回相应的图形对象
class CircleFactory extends ShapeFactory {
    @Override
    Shape createShape() {
        return new Circle();
    }
}

class RectangleFactory extends ShapeFactory {
    @Override
    Shape createShape() {
        return new Rectangle();
    }
}
// 我们可以使用这些工厂类来创建图形对象
public class FactoryMethod {
    public static void main(String[] args) {
        ShapeFactory circleFactory = new CircleFactory();
        Shape circle = circleFactory.createShape();
        circle.draw();

        ShapeFactory rectangleFactory = new RectangleFactory();
        Shape rectangle = rectangleFactory.createShape();
        rectangle.draw();
    }
}
