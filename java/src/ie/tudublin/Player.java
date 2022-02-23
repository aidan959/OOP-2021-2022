package ie.tudublin;


public class Player extends Entity
{
    public float size;
    public float playerSpeed = 1;
    public InputHandler inputHandle = new InputHandler();
    public int lastFire = 0;
    public int fireWait = 60;
    public boolean fire = false;
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
    
    public void takeInputs(int frame){
        this.acceleration.clear();
        fire = false;
        if(inputHandle.inputsDown[InputHandler.inputs.LEFT.get()] == true){
            this.acceleration.add(-playerSpeed, 0f);
            this.collision_sleeping = false;
        }
        if(inputHandle.inputsDown[InputHandler.inputs.RIGHT.get()] == true){
            this.acceleration.add(playerSpeed, 0f);
            this.collision_sleeping = false;
        }
        if(inputHandle.inputsDown[InputHandler.inputs.UP.get()] == true){
            this.acceleration.add(0, -playerSpeed);
            this.collision_sleeping = false;
        }
        if(inputHandle.inputsDown[InputHandler.inputs.DOWN.get()] == true){
            this.acceleration.add(0, playerSpeed);
            this.collision_sleeping = false;    
        }
        if(inputHandle.inputsDown[InputHandler.inputs.SPACE.get()] == true){

            if(frame - lastFire > fireWait){
                fire = true;
            } else{
                fire = false;
            }
        }
    }
    public Projectile fire(float mouseX, float mouseY, float velocity1){
        // 
        float heighttoMouse = mouseY - getY();
        float widthtoMouse = mouseX - getX();
        float angleOfProjectile = (float) Math.tanh(heighttoMouse/widthtoMouse);
        Coordinate velocity = new Coordinate((float)(velocity1 * Math.cos(angleOfProjectile)),(float)(velocity1 * Math.sin(angleOfProjectile)) );    
        return new Projectile(getX(), getY(), velocity, health, WIDTH, HEIGHT, 1);
    }
};
