package ie.tudublin;

public class Bug extends Entity
{

    public float health;;
    
    public Bug(Coordinate coords, float health, int WIDTH, int HEIGHT, float radius){
        super(coords.x, coords.y, health, WIDTH, HEIGHT, radius);
        mass = 100; 
    }
    public void moveBugX(float amount){
        if((getX() - amount) > 0  && (getX() + amount) < WIDTH ){
            setX(getX() + amount);
        }else if((getX() - amount) >= 0){
            setX(WIDTH);
        } else  if((getX() + amount) <= WIDTH){
            setX(0);
        }
    }public static void moveBugY(float amount){

    }
 };
