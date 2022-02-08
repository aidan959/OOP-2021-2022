package ie.tudublin;
import java.lang.Math;
public class Coordinate{
    public float x;
    public float y;
    public Coordinate(float x, float y){
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return "X: " + this.x + ", Y: " + this.y;
    }
    public boolean hasMagnitude(){
        if(x == 0  && y == 0){
            return false;
        }
        else{
            return true;
        }
    }
    public void flip(){
        x = -x;
        y = -y;
    }
    public void add(Coordinate coord){
        x = x + coord.x;
        y = y + coord.y;
    }
    public void divide(float v){
        x = x / v;
        y = x / v;
    }
    public Coordinate divide(Coordinate c){
        return new Coordinate(x/c.x, y/c.y);
    }
    public float magnitude(){
        return((float)Math.sqrt(x*x + y*y));
    }
    
}