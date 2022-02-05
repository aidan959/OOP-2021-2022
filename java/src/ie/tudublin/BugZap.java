package ie.tudublin;

import java.util.Vector;

import processing.core.PApplet;
class Coordinate{
    public float x;
    public float y; 
 }
class Bug
{
    private Coordinate bugCoord; 
    public float health;
    private int WIDTH;
    private int HEIGHT;
    Bug(float bugX,float bugY, float health, int WIDTH, int HEIGHT){
        this.bugCoord.x = bugX;
        this.bugCoord.y = bugY;
        this.health = health; 
        this.WIDTH = WIDTH;
        this.HEIGHT = WIDTH;
    }
    public void moveBugX(float amount){
        if((bugCoord.x - 5) > 0  && (bugCoord.x + 5) < WIDTH ){
            setBugX(bugCoord.x -= 5);
        }else{
        }
        setBugX(bugCoord.x - amount);
    }public static void moveBugY(float amount){

    }
    public void setBugX(float x){
        
        bugCoord.x = x;
    }
    public float getBugX(){
        return bugCoord.x;
    }
    public void setBugY(float y){
        bugCoord.y = y;
    }
    public float getBugY(){
        return bugCoord.y;
    }
 };
class RandomNumbers extends PApplet{

    public int[] generate(float lowBound, float highBound) {
    
        float[] random = new float[10]; //this code generates numbers 1–10 inclusive
        
        float[] array = new float[10]; //another array. numbers will be translated here.
        
        for(int x = 0; x < random.length; x++)
        
        random[x] = x; //store the index as the *value*
        
        int rand; //declaration- later instantiated
        
        for(int x = 0; x < random.length; x++) {
        
        rand = random(lowBound, highBound);
        
        while(random[rand] == -1)
        
        rand = (int)(Math.random()*10); //forces to pick another value if random[rand] is already occupied
        
        if(random[rand] != -1)
        
        array[x] = random[rand]; //this stores a random number from 1–10 in the location of random[x];
        
        random[rand] = -1; //set to -1 so no future rand that results in this rand can access this index
        
        }
        
        for(int x = 0; x < array.length; x++)
        
        array[x] = array[x] + 1;
        
        System.out.print("\n" + "Randomized values 1-10 inclusive w/o repeats: ");
        
        
        return array;

    
}
public class BugZap extends PApplet{
    int WIDTH = 480;
    int HEIGHT = 480;
    float playerX = WIDTH/2;
    float playerY = HEIGHT/2 + HEIGHT/4;
    
    public void drawPlayer(float pX, float pY, float pW){
        ellipse(pX, pY, playerSize * 10,  (playerSize * 20));
        stroke(0, 255, 0);
        fill(0, 200, 0);
    }
    public void drawBug(float bX, float bY, float bS){
        ellipse(bX, bY, bS * 10, bS * 10);
        stroke(255, 0 , 0);
        fill(200,0, 0);
    }
    public void drawBugs(Vector<Bug> bugs){
        for(int i=0; i < bugs.size(); i++){
            drawBug(bugs.get(i).getBugX(), bugs.get(i).getBugY(), 10);
        }
    }
    public void playerFire(float pX, float pY, float lW){
        line(pX, pY, pX, pY - HEIGHT);
        stroke(255,255,255);
        fill(255,255,255);
        
    }
    public void settings(){
        size(WIDTH,HEIGHT);
    }
    int playerSize = 1;
    int bugSize = 1;
    int numEnemys = 10;
    Vector<Bug> enemyBugs =  new Vector<Bug>(numEnemys);
    Vector<Coordinate> bugLocations = new Vector<Coordinate>(numEnemys);
    public void generateBugLocations(){

    }
    public void setup(){
        int i;
        for(i=0; i<numEnemys; i++){
            enemyBugs.addElement(new Bug(bugLocations.getElement()));
        }
    }
   
    public void draw(){
        
        clear();
        drawPlayer(playerX, playerY, playerSize);
        drawBugs(enemyBugs);
    }
    public void keyPressed()
	{
		if (keyCode == LEFT)
		{   
            if((playerX - 5) > 0 ){
                playerX -= 5;
            }else{
                playerX = WIDTH - 5;
            }
			System.out.println("Left arrow pressed");
            System.out.println(playerX);
		}
        else if(keyCode == RIGHT){
            if((playerX + 5) < WIDTH ){
                playerX += 5;
            } else{
                playerX = 5;
            }
            System.out.println("Right arrow pressed");
            System.out.println(playerX);
        }
		if (key == ' ')
		{
            playerFire(playerX, playerY, 10);
			System.out.println("SPACE key pressed");
		}
	}
}
