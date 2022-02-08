package ie.tudublin;

class Entity
{
    private Coordinate coordinate;
    public float health;
    public float size = 10;
    public Coordinate acceleration = new Coordinate(0, 0);
    public Coordinate velocity = new Coordinate(0, 0);
    public Coordinate screenSize;
    public int WIDTH;
    public int HEIGHT;
    public Entity(float objX, float objY, float health, int WIDTH, int HEIGHT){
        coordinate = new Coordinate(objX, objY);
        screenSize = new Coordinate(WIDTH, HEIGHT);
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.health = health;
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