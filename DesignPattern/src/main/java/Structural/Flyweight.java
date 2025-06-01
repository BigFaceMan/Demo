import java.util.HashMap;
import java.util.Map;

// 自定义颜色枚举
enum Color {
    RED, GREEN, BLUE, YELLOW
}

// 享元接口
interface Shape {
    void draw(int x, int y);
}

// 具体享元类
class Circle implements Shape {
    private Color color;

    public Circle(Color color) {
        this.color = color;
    }

    @Override
    public void draw(int x, int y) {
        System.out.println("Drawing a " + color + " circle at (" + x + "," + y + ")");
    }
}

// 享元工厂类
class ShapeFactory {
    private static final Map<Color, Shape> circleMap = new HashMap<>();

    public static Shape getCircle(Color color) {
        Shape circle = circleMap.get(color);

        if (circle == null) {
            circle = new Circle(color);
            circleMap.put(color, circle);
            System.out.println("Creating a new circle of color: " + color);
        }

        return circle;
    }
}

// 主类
public class Flyweight {
    public static void main(String[] args) {
        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};

        for (int i = 0; i < 20; i++) {
            Color randomColor = colors[(int) (Math.random() * colors.length)];
            Shape circle = ShapeFactory.getCircle(randomColor);
            circle.draw((int) (Math.random() * 100), (int) (Math.random() * 100));
        }
    }
}
