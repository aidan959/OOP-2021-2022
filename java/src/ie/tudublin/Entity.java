package ie.tudublin;
import java.util.Vector;
class Entity
{
    public Vector<Entity> listObjs;
    private Coordinate coordinate;
    public float health;
    public float size = 10;
    public Coordinate acceleration = new Coordinate(0, 0);
    public Coordinate velocity = new Coordinate(0, 0);
    public Coordinate screenSize;
    public int WIDTH;
    public int HEIGHT;
    public float mass = 1;
    public Circle model;
    public HitBox hitbox;
    public boolean collision_sleeping;
    // set if the object has already been handled by collision detection
    public boolean handled;
    public Entity(float objX, float objY, float health, int WIDTH, int HEIGHT, float radius ){
        coordinate = new Coordinate(objX, objY);
        screenSize = new Coordinate(WIDTH, HEIGHT);
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.health = health;
        model = new Circle(this.getCoord(), radius);
        hitbox = new HitBox(new Vector<Circle>(1), new Vector<Rectangle>(1));
    }
    public void setX(float x){
        
        coordinate.x = x;
    }
    public float getX(){
        return coordinate.x;
    }
    public void setY(float y){
        coordinate.y = y;
    }
    public float getY(){
        return coordinate.y;
    }
    public Coordinate getCoord(){
        return coordinate;
    }
    public float moveX(float amount){
        setX(coordinate.x + amount);
        return coordinate.x;
    }
    public float moveY(float amount){
        setY(coordinate.y + amount);
        return coordinate.y;
    }
    public void updateModel(){
        this.model.points.set(0, this.getCoord());
    }
    public void moveCoord(Coordinate amount){
        this.coordinate = EngineFeatures.addCoordinate(this.coordinate, amount);
    }
    public void addAcceleration(Coordinate acceleration){
        this.acceleration = EngineFeatures.addCoordinate(this.acceleration, acceleration);
    }
    public void addVelocity(Coordinate velocity){
        this.velocity = EngineFeatures.addCoordinate(this.velocity, velocity);
    }
    public void debugPhys(){
        System.out.println("Player Coords: " + this.coordinate);
        System.out.println("Player Velocity: " + this.velocity);
        System.out.println("Player Acceleration: " + this.acceleration);

    }
    public boolean isTouchingWall(){
        if((getX() <= 0 )|| (getX() >= screenSize.x) || getY() <= 0 ||  getY() >= screenSize.y){
            return true;
        }
        return false;
    }
}