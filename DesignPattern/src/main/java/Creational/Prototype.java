package Creational;

// 创建一个实现 Cloneable 接口的原型类
class ShapePrototype implements Cloneable {
    private String type;

    public ShapePrototype(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public ShapePrototype clone() {
        try {
            return (ShapePrototype) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}

// 测试原型模式
public class Prototype {
    public static void main(String[] args) {
        // 创建原型对象
        ShapePrototype circle = new ShapePrototype("Circle");

        // 克隆原型对象来创建新对象
        ShapePrototype clonedCircle = circle.clone();
        clonedCircle.setType("Cloned Circle");

        // 输出原型对象和克隆对象的类型
        System.out.println("Original Shape Type: " + circle.getType());
        System.out.println("Cloned Shape Type: " + clonedCircle.getType());
    }
}
