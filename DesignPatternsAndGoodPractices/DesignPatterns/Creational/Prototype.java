/*
Záměr vzoru 
- Můžeme kopírovat existující objekty, aniž by byl klientský kód závislý na procesu kopírování. 
- Protože máme proces kopírování pod kontrolou, můžeme mít také připraveno několik "prototypů" pro rychlé kopírování 


Problém, který řeší 
- Ne všechny objekty lze kopírovat přímo pomocí konstruktoru 
- Příklad: Většina polí je soukromá a zvenčí není vidět.. Knihovna neposkytla všechny gettery, takže nemáte možnost objekt zkopírovat 
- Kód se stává závislým na zkopírované třídě, protože o ní musíme znát všechny podrobnosti, 
    ale rádi bychom použili rozhraní, abychom dodrželi volnou vazbu (loose coupling) 


Kdy jej použijeme 
- Když by náš kód neměl záviset na konkrétních třídách, které chceme zkopírovat 

----------------------------

Výhody 
- Klonování objektů bez vazby na konkrétní třídy 
- Možnost klonovat předem vytvořené prototypy 
- Pohodlné vytváření složitých objektů 

Nevýhody 
- Klonování objektů s kruhovými závislostmi může být obtížné 




-------------------------------------Priklad-------------------------------------

- Mame graficky editor (napr. Malovani) a chcete pridat novou funkcionalitu - kopirovani
- geometrickych tvaru jako jsou obdelniky a kolecka.
- Implementujte funkcionalitu s pouzitim patternu Prototype
*/

//------------------------Main------------------------

public class Main {
    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle();
        rectangle.setX(30);
        rectangle.setY(50);
        rectangle.setSideA(5);
        rectangle.setSideB(10);

        Shape rectangleCopy = rectangle.clone();

        Rectangle rectangleCopy2 = (Rectangle) rectangle.clone();

        System.out.println(rectangle);
        System.out.println(rectangleCopy);
    }
}

//------------------------Shape(abstract)------------------------

public abstract class Shape {

    private int x;
    private int y;

    public Shape() {}

    public Shape(Shape shape) {
        if (shape != null) {
            this.x = shape.x;
            this.y = shape.y;
        }
    }

    public abstract Shape clone();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

//------------------------Rectangle------------------------

public class Rectangle extends Shape {

    private int sideA;

    private int sideB;

    public Rectangle() {}

    public Rectangle(Rectangle rectangle) {
        super(rectangle);
        if (rectangle != null) {
            this.sideA = rectangle.sideA;
            this.sideB = rectangle.sideB;
        }
    }

    @Override
    public Shape clone() {
        return new Rectangle(this);
    }

    public int getSideA() {
        return sideA;
    }

    public void setSideA(int sideA) {
        this.sideA = sideA;
    }

    public int getSideB() {
        return sideB;
    }

    public void setSideB(int sideB) {
        this.sideB = sideB;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "x=" + super.getX() +
                "y=" + super.getY() +
                "sideA=" + sideA +
                ", sideB=" + sideB +
                '}';
    }
}

//------------------------Circle------------------------

public class Circle extends Shape {

    private int radius;

    public Circle() {}

    public Circle(Circle circle) {
        super(circle);
        if (circle != null) {
            this.radius = circle.radius;
        }
    }

    @Override
    public Shape clone() {
        return new Circle(this);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "x=" + super.getX() +
                "y=" + super.getY() +
                "radius=" + radius +
                '}';
    }
}