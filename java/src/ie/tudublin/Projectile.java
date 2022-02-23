package ie.tudublin;
public class Projectile extends Entity{
    public Projectile(float X,float Y, Coordinate velocity, float health, int WIDTH, int HEIGHT, float radius){
        super(X, Y, health, WIDTH, HEIGHT, radius);
        setCoord(velocity);
    }
}
