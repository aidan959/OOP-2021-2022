package ie.tudublin;
import java.util.Vector;
import processing.core.PApplet;


class EngineFeatures{
    static Coordinate addCoordinate(Coordinate a, Coordinate b){
        return new Coordinate(a.x + b.x, a.y + b.y);
    }
    static Coordinate flip(Coordinate a){
        return new Coordinate(-a.x, -a.y);
    }
}

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
        RUNNING,
        EXIT
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
    public void drawMenu(){
        
        for(Menu.MenuObject menuObject : menu.returnMenuObjects()){
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
            fill(0,255,255);
            textSize(30);
            text(menuObject.menuText,  menuObject.textStart.x, menuObject.textStart.y , menuObject.textEnd.x, menuObject.textEnd.y) ;
            
            
        }
        menu.setOutput();
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
    int frameLoop = 300;
    Player player;
    Menu menu;
    GameState state;
    int rectColor;
    int rectHighlight;
    Physics physics;
    boolean flipper= false;
    public void generateBugLocations(){
        float[] randomX = randomNumberGen.generateUniqueSet(0, WIDTH, numBugs);
        float[] tempLocationY =  randomNumberGen.generateUniqueSet(0, HEIGHT/2, numBugs);
        
        // spawns d bugs
        for(int i=0; i< numBugs; i++){
            enemyBugs.addElement(new Bug(new Coordinate(randomX[i], tempLocationY[i]), 100, WIDTH, HEIGHT));
        }
    }

    public void setupMenu(){
        // button coords
        Coordinate buttonSize = new Coordinate(2*WIDTH/3, HEIGHT/6);
        Coordinate startBtnCoord = new Coordinate(WIDTH / 6, HEIGHT/16);
        Coordinate quitBtnCoord = new Coordinate(WIDTH / 6, buttonSize.y + startBtnCoord.y + HEIGHT/6);
        Coordinate creditBtnCoord  = new Coordinate(WIDTH / 6, buttonSize.y + quitBtnCoord.y + HEIGHT/6);
    
        rectColor = color(0);
        rectHighlight = color(51);
        
        menu = new Menu();
        menu.createMenuObject(Menu.MenuChoice.START, "Start Game", startBtnCoord, buttonSize);
        menu.createMenuObject(Menu.MenuChoice.QUIT, "Quit Game", quitBtnCoord, buttonSize);
        menu.createMenuObject(Menu.MenuChoice.CREDITS, "Credits", creditBtnCoord, buttonSize);

    }
    public void setupGame(){
        
        player = new Player((float)(WIDTH/2),(float)(HEIGHT/2 + HEIGHT/4), true, 100, WIDTH, HEIGHT);
        player.size = playerSize;
        generateBugLocations();
        Vector<Entity> listObjs = new Vector<Entity>(1);
        listObjs.add(player);
        for(Bug bug : enemyBugs){
            listObjs.add(bug);
        }
        physics = new Physics(listObjs);
    }
    public void setup(){
        state = GameState.MENU;
        setupMenu();
    }
    public void draw(){
        clear();
        switch (state){
            case SPLASH:
                break;
            case MENU:
                drawMenu();
                switch(menu.output){
                    case START:
                        state = GameState.RUNNING;
                        setupGame();
                        break;
                    case QUIT:
                        state = GameState.EXIT;
                        break;
                    case CREDITS:
                        break;
                    case UNSELECTED:
                        break;
                }
                break;
            
            case RUNNING:
                background(46, 162, 200);
                player.takeInputs();
                physics.calculatePhys();
                pushMatrix();
                translate((float)(width*0.5), (float)(height*0.5));
                // rotate((float)frameCount / (float) -100.0);
                polygon(0, 0, 10, 20);  // Heptagon
                popMatrix();
                
                if(frameCount % 60 == 0){
                    flip = !flip;
                    if(flip){
                        enemyBugs.get(0).addAcceleration(new Coordinate(random(-4,4), random(-4,4)));
                    } else{
                        enemyBugs.get(0).acceleration  = new Coordinate(0, 0);
                    }
                }
                drawPlayer(player);
                if(enemyBugs.size() > 0){
                    drawBugs(enemyBugs);
                }
                
                break;
            case EXIT:
                exit();
                break;
        }
        
    }
    public boolean flip = false;
    public void polygon(float x, float y, float radius, int npoints) {
        float angle = TWO_PI / npoints;
        beginShape();
        int count = 0;
        float sx;
        float sy;
        for (float a = 0; a < TWO_PI; a += angle) {
            if(count % 2 == 1){
                sx = x + cos(a) * radius;
                sy = y + sin(a) * radius;
                
            } else {
                sx = x + cos(a) * random(2, 4) * radius;
                sy = y + sin(a) * random(2, 4) * radius;
            }
            count++;
            vertex(sx, sy);
            
            if(frameCount % 255 == 254){
                flip = !flip;
            }
            if (flip){
                fill(frameCount % 255, frameCount % 255, frameCount % 255);
            } else {
                //fill(255 - frameCount % 255,255 - frameCount % 255,255- frameCount % 255);
                fill(random(0,255), random(0,255), random(0,255));
            }

            
        }
        endShape(CLOSE);
      }
    public void keyPressed()
	{
        if(state == GameState.RUNNING){
            if (keyCode == LEFT)
            {
                player.inputHandle.inputsDown[InputHandler.inputs.LEFT.get()] = true;
                player.addVelocity(new Coordinate(-1, 0));
                //player.addAcceleration(new Coordinate((float) -1, 0));
                System.out.println("Left arrow pressed");
                System.out.println(player.getCoord().toString());
            }
            else if(keyCode == RIGHT){
                //player.addAcceleration(new Coordinate((float)1, 0));;
                player.inputHandle.inputsDown[InputHandler.inputs.RIGHT.get()] = true;
                player.addVelocity(new Coordinate(1, 0));
                System.out.println("Right arrow pressed");
                System.out.println(player.getCoord().toString());
            }
            else if(keyCode == UP){
                player.inputHandle.inputsDown[InputHandler.inputs.UP.get()] = true;
                player.addVelocity(new Coordinate(0, 1));

                System.out.println("Right arrow pressed");
                System.out.println(player.acceleration.toString());
            }
            else if(keyCode == DOWN){
                player.inputHandle.inputsDown[InputHandler.inputs.DOWN.get()] = true;
                player.addVelocity(new Coordinate(0, -1));

                System.out.println("Right arrow pressed");
                System.out.println(player.acceleration.toString());
            }
            if (key == ' ')
            {
                playerFire(player, 10);
                player.debugPhys();
                System.out.println("SPACE key pressed");
            }
            if(keyCode == ESC){
                key = 0;
                state = GameState.MENU;
            }
            if(key == 'c'){
                for(Bug bug: enemyBugs)
                    bug.moveBugX(1);
            }
        }
	}
    public void keyReleased(){
        if(state == GameState.RUNNING){
            if (keyCode == LEFT)
            {
                player.inputHandle.inputsDown[InputHandler.inputs.LEFT.get()] = false;
            } else if(keyCode == RIGHT){
                player.inputHandle.inputsDown[InputHandler.inputs.RIGHT.get()] = false;
            } else if(keyCode == UP){
                player.inputHandle.inputsDown[InputHandler.inputs.UP.get()] = false;
            } else if(keyCode == DOWN){
                player.inputHandle.inputsDown[InputHandler.inputs.DOWN.get()] = false;
            } 
        }
    }
}
