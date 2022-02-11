package ie.tudublin;

import java.util.Vector;

public class Circle extends Shape {
    float radius;
    public Circle(Coordinate center, float radius){
        this.points = new Vector<Coordinate>(1,1);
        this.points.add(center);
        this.radius = radius;

    }
}
