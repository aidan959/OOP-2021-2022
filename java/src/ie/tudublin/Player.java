package ie.tudublin;

public class Player extends Entity
{
    public float size;
    public float playerSpeed = 1;
    public InputHandler inputHandle = new InputHandler();

    
    public Player(float pX,float pY, boolean def_spawn, float health, int WIDTH, int HEIGHT, float radius){    
        super(pX, pY, health, WIDTH, HEIGHT, radius);
        if (def_spawn){
            setX(WIDTH/2);
            setY(HEIGHT - (HEIGHT/8));
        }
        this.WIDTH = WIDTH;
        this.HEIGHT = WIDTH;
        this.model = new Circle(this.getCoord(), 10);
    }
    
    public void takeInputs(){
        this.acceleration = new Coordinate(0, 0);
        if(inputHandle.inputsDown[InputHandler.inputs.LEFT.get()] == true){
            this.acceleration.add(new Coordinate(-playerSpeed, 0));
        }
        if(inputHandle.inputsDown[InputHandler.inputs.RIGHT.get()] == true){
            this.acceleration.add(new Coordinate(playerSpeed, 0));
        }
        if(inputHandle.inputsDown[InputHandler.inputs.UP.get()] == true){
            this.acceleration.add(new Coordinate(0, -playerSpeed));
        }
        if(inputHandle.inputsDown[InputHandler.inputs.DOWN.get()] == true){
            this.acceleration.add(new Coordinate(0, playerSpeed));
        }
    }
};
