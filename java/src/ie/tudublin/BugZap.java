package ie.tudublin;
import java.util.Vector;

import ie.tudublin.Menu.MenuChoice;
import ie.tudublin.Menu.MenuObject;
import processing.core.PApplet;
import ie.tudublin.Menu;
class Coordinate{
    public float x;
    public float y;
    public Coordinate(float x, float y){
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return "X: " + this.x + ", Y: " + this.y;
    }
}
class Entity
{
    private Coordinate coordinate;
    public float health;
    public float size = 10;
    public Entity(float objX, float objY, float health){
        coordinate = new Coordinate(objX, objY);
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
}
class Bug extends Entity
{
    private Coordinate bugCoord; 
    public float health;
    private int WIDTH;
    private int HEIGHT;
    
    public Bug(Coordinate coords, float health, int WIDTH, int HEIGHT){
        super(coords.x, coords.y, health); 
        this.WIDTH = WIDTH;
        this.HEIGHT = WIDTH;
    }
    public void moveBugX(float amount){
        if((bugCoord.x - 5) > 0  && (bugCoord.x + 5) < WIDTH ){
            setX(bugCoord.x -= 5);
        }else{
        }
        setX(bugCoord.x - amount);
    }public static void moveBugY(float amount){

    }
 };
class Player extends Entity
{
    public float health;
    public float size;
    private int WIDTH;
    private int HEIGHT;
    public Player(float pX,float pY, boolean def_spawn, float health, int WIDTH, int HEIGHT){    
        super(pX, pY, health);
        if (def_spawn){
            setX(WIDTH/2);
            setY(HEIGHT/8);
        }
        this.WIDTH = WIDTH;
        this.HEIGHT = WIDTH;
    }
    public void movePlayerX(float amount){
        if((getX() + amount) > 0  && (getX() + amount) < WIDTH ){
            setX(getX()+ amount);
        }else{
        }
        setX(getX() - amount);
    }public static void movePlayerY(float amount){

    }    
};


class RandomNumbers extends PApplet{

    public float[] generateUniqueSet(float lowBound, float highBound, int size) {
        boolean passTest = false;
        float[] output = new float[size];
        for(int x = 0; x < size; x++) {
            output[x] = (float)random(lowBound, highBound); 
            // array.add((float)random(lowBound, highBound));
        }
        while(!passTest){
            int[] duplicates = checkDuplicates(output, size);
            if(duplicates[0] > 0){
                for(int i = 1; i < duplicates[0]; i++){
                    output[duplicates[i]] = (float)random(lowBound, highBound);
                }
            } else{
                passTest = true;
            }
        }
        return output;
    }
    // returns position of all duplicates - null if none
    private int[] checkDuplicates(float[] array, int size){
        int[] output = {0};
        int array_c = 0;
        for (int i = 0; i < size; i++){
            for(int j = i + 1; j < size; j++){
                if(array[i] == array[j]){
                    output[array_c++] = i;
                }
            }
        }
        // size of array is 0 value - idk how strict java is - with c you can't tell how big an array
        // is when its been passed around. assuming thats the case w generic / legacy arrays?
        output[0] = array_c;
        return output;
    }
}
public class BugZap extends PApplet{
    int WIDTH = 480;
    int HEIGHT = 480;

    enum  GameState{
        SPLASH,
        MENU,
        RUNNING
    }
    public void drawPlayer(Player player){
        ellipse(player.getX(), player.getY(), player.size,  (player.size * 2));
        stroke(0, 255, 0);
        fill(0, 200, 0);
    }

    public void drawBug(Bug bug){
        ellipse(bug.getX(), bug.getY(), bug.size, bug.size);
        stroke(255, 0 , 0);
        fill(200,0, 0);
    }
    public void drawBugs(Vector<Bug> bugs){
        for(Bug bug : bugs){
            drawBug(bug);
        }
    }
    public void drawMenu(Vector<Menu.MenuObject> menuObjects){
        
        for(Menu.MenuObject menuObject : menuObjects){
            menuObject.selected = false;
            if(mouseOver(menuObject.position, (int)menuObject.size.x, (int)menuObject.size.y)){
                fill(rectHighlight);
                if(mousePressed){
                    menuObject.clicked();
                }
            } else{
                fill(rectColor);
            }
            stroke(255);
            rect(menuObject.position.x, menuObject.position.y, menuObject.size.x, menuObject.size.y);
            textSize(30);
            text(menuObject.menuText, menuObject.position.x + (menuObject.size.x)/16 , menuObject.position.y + (menuObject.size.y)/8, menuObject.bottomRightPosition.x -(menuObject.size.x)/16, menuObject.bottomRightPosition.y - (menuObject.size.y)/8  );
            fill(0,255,255);
        }
    }
    
    public boolean mouseOver(Coordinate position, int width, int height){
        if (mouseX >= position.x && mouseX <= position.x + width && mouseY >= position.y && mouseY <= position.y + height){
            return true;
        } else{
            return  false;
        }
    }
    public void drawButton(Menu.MenuObject menuObj){

    }
    public void playerFire(Player player, float lW){
        line(player.getX(), player.getY(), player.getX(), player.getY() - HEIGHT);
        stroke(255,255,255);
        fill(255,255,255);
        
    }
    public void settings(){
        size(WIDTH,HEIGHT);
    }
    int playerSize = 10;
    int bugSize = 1;
    int numBugs = 10;
    Vector<Bug> enemyBugs = new Vector<Bug>(numBugs);
    Vector<Coordinate> bugLocations = new Vector<Coordinate>(numBugs);
    RandomNumbers randomNumberGen = new RandomNumbers();
    Player player;
    Menu menu;
    GameState state;
    int rectColor;
    int rectHighlight;
    public void generateBugLocations(){
        float[] randomX = randomNumberGen.generateUniqueSet(0, WIDTH, numBugs);
        float[] tempLocationY =  randomNumberGen.generateUniqueSet(0, HEIGHT/2, numBugs);
        
        // spawns d bugs
        for(int i=0; i< numBugs; i++){
            enemyBugs.addElement(new Bug(new Coordinate(randomX[i], tempLocationY[i]), 100, WIDTH, HEIGHT));
        }
    }
    public Coordinate addCoordinate(Coordinate a, Coordinate b){
        return new Coordinate(a.x + b.x, a.y + b.y);
    }
    public void startGame(){

    }
    public void setup(){
        Vector<Menu.MenuObject> menuObjs = new Vector<Menu.MenuObject>();
        // button coords
        Coordinate startBtnCoord = new Coordinate(WIDTH / 6, HEIGHT/16);
        Coordinate quitBtnCoord = new Coordinate(WIDTH / 6, 4 * (HEIGHT/16) );
        Coordinate buttonSize = new Coordinate(2*WIDTH/3, HEIGHT/6);
        // buttons
        Menu.MenuObject startBtn = new Menu.MenuObject(Menu.MenuChoice.START, "Start Game", startBtnCoord , addCoordinate(startBtnCoord, buttonSize), buttonSize );
        Menu.MenuObject quitBtn =  new Menu.MenuObject(Menu.MenuChoice.QUIT, "Quit Game", quitBtnCoord, addCoordinate(quitBtnCoord, buttonSize), buttonSize);
        // adds newly created buttons to menuObjs
        menuObjs.add(startBtn);
        menuObjs.add(quitBtn);

        menu = new Menu(menuObjs);
        state = GameState.MENU;

        rectColor = color(0);
        rectHighlight = color(51);

        player = new Player((float)(WIDTH/2),(float)(HEIGHT/2 + HEIGHT/4), true, 100, WIDTH, HEIGHT);
        player.size = playerSize;
        generateBugLocations();
        
         
    }

    public void draw(){
        clear();
        switch (state){
            case SPLASH:

                break;

            case MENU:
                drawMenu(menu.returnMenuObjects());
                break;
            
            case RUNNING:
                drawPlayer(player);
                if(enemyBugs.size() > 0){
                    drawBugs(enemyBugs);
                }
                break;
        }
        
    }

    public void keyPressed()
	{
		if (keyCode == LEFT)
		{   
            
            player.movePlayerX(-5);
			System.out.println("Left arrow pressed");
            System.out.println(player.getCoord().toString());
		}
        else if(keyCode == RIGHT){
            player.movePlayerX(5);

            System.out.println("Right arrow pressed");
            System.out.println(player.getCoord().toString());
        }
		if (key == ' ')
		{
            playerFire(player, 10);
			System.out.println("SPACE key pressed");
		}
	}
}
