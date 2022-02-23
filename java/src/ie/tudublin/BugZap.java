package ie.tudublin;
import java.util.Vector;
import processing.core.PApplet;
import java.util.concurrent.*;
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
        player.updateModel();
        stroke(0, 255, 0);
        fill(0, 200, 0);
        ellipse(player.model.points.elementAt(0).x, player.model.points.elementAt(0).y, player.model.radius,  (player.model.radius * 2));
    }

    public void drawProjectile(Projectile projectile){
        stroke( 0,255,0);
        fill(0,200,0);
        ellipse(projectile.getX(), projectile.getY(), 4, 3);
    }

    public void drawBug(Bug bug){
        stroke(255, 0 , 0);
        fill(200,0, 0);
        ellipse(bug.getX(), bug.getY(), bug.size, bug.size);
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
    public void playerFire(Player player){
        if(projectiles.size() < projectileLimit ){
            projectiles.add(player.fire(mouseX, mouseY, 0.5f));
        }
    }
    public void settings(){
        size(WIDTH,HEIGHT);
    }
    int playerSize = 10;
    int bugSize = 1;
    int numBugs = 10;
    int projectileLimit = 10;
    Vector<Bug> enemyBugs = new Vector<Bug>(numBugs);
    Vector<Coordinate> bugLocations = new Vector<Coordinate>(numBugs);
    Vector<Projectile> projectiles = new Vector<Projectile>(projectileLimit);
    RandomNumbers randomNumberGen = new RandomNumbers();
    int frameLoop = 300;
    Player player;
    Menu menu;
    GameState state;
    int rectColor;
    int rectHighlight;
    Physics physics;
    boolean flipper = false;
    boolean debugStats = true;
    Koleada koleada;
    Vector<Entity> listObjs = new Vector<Entity>(1);
    ThreadPoolExecutor executor;
    Vector<Koleada.Collision> collisions_list = new Vector<Koleada.Collision>();
    BlockingQueue<Runnable> threadQueue;
    Semaphore entityListLock = new Semaphore(1);
    float frameTime;
    ForkJoinPool forkJoinPool;
    public void generateBugLocations(){
        float[] randomX = randomNumberGen.generateUniqueSet(0, WIDTH, numBugs);
        float[] tempLocationY =  randomNumberGen.generateUniqueSet(0, HEIGHT/2, numBugs);
        
        // spawns d bugs
        for(int i=0; i< numBugs; i++){
            enemyBugs.addElement(new Bug(new Coordinate(randomX[i], tempLocationY[i]), 100, WIDTH, HEIGHT, 1));
        }
    }

    public void setupMenu(){
        // button coords
        Coordinate buttonSize = new Coordinate((2*WIDTH)/3, HEIGHT/6);
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
    // initializes game options
    public void setupGame(){
        // creates our player
        player = new Player((float)(WIDTH/2),(float)(HEIGHT/2 + HEIGHT/4), true, 100, WIDTH, HEIGHT, playerSize*10);
        
        // generates bugs and random locations
        generateBugLocations();
        
        // adds new object to the list of objects
        listObjs.add(player);
        for(Bug bug : enemyBugs){
            listObjs.add(bug);
        }


        // initializes collision enginer
        koleada = new Koleada(listObjs, entityListLock);
        // passes koleada to physics engine and initializes it
        physics = new Physics(listObjs, koleada);

        threadQueue = new LinkedBlockingDeque<>(5);
        // MAKE SURE TO INCREASE THIS TO MATCH THE NUMBER OF THREADS
        executor = (ThreadPoolExecutor) new ThreadPoolExecutor(2, 2, 1, TimeUnit.MILLISECONDS, threadQueue, new ThreadPoolExecutor.AbortPolicy());
        executor.prestartAllCoreThreads();
        //forkJoinPool = executor.forkJoinPool;
        // HandlePhysics handlePhysics = new HandlePhysics();
        // HandleCollisions handleCollisions = new HandleCollisions();
    }
    public void setup(){
        state = GameState.MENU;
        setupMenu();
    }
    // TODO check all things in here are being created and recycled before creation
    // ALL USES
    public void draw(){ 
        Grass grass = new Grass();
        grass.animate(frameCount);
        if (grass.currentState == Grass.state.LEFT){

        }
        long startTime = System.nanoTime();
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
                
                try{
                    threadQueue.offer(koleada);
                    threadQueue.offer(physics);
                } catch (IllegalStateException e){
                    System.out.println("Thread failed to be added to queue");
                }

                // Thread koleadaThread = new Thread(){
                //     public void run(){
                //         for(Entity obj : listObjs){
                //             obj.updateModel();
                //             obj.handled = false;
                //         }
                //         koleada.run();
                
                //     }
                // };
                // Thread physicsThread = new Thread(){

                //     public void run(){
                //         physics.run();
                //     }
                // };
                //koleadaThread = new Thread(koleada,"Collisions");
                //physicsThread = new Thread(physics, "Physics");

                //koleada.start();
                
                
                background(46, 162, 200);
                player.takeInputs(frameCount);
                //collisions_list = koleada.detectCollision();
                // pushMatrix();
                // translate((float)(width*0.5), (float)(height*0.5));
                // // rotate((float)frameCount / (float) -100.0);
                // polygon(0, 0, 10, 20);  // Heptagon
                // popMatrix();
                // randomly adds acceleration to d bugs
                if(frameCount % 60 == 0){
                    flip = !flip;
                    if(flip){
                        enemyBugs.get(0).acceleration.x = random(-4,4);
                        enemyBugs.get(0).acceleration.y = random(-4,4);
                    } else{
                        enemyBugs.get(0).acceleration.clear();
                    }
                }
                // TODO - change this to something that checks if listObjs is > 0
                // if it is then we draw each using their own element
                // add something like entity.draw();
                // or a rendering engine which takes the entity and draws it to the screen
                // rendering.draw(entity); 
                // draws le player
                drawPlayer(player);
                // checks if there are any bugs alive
                if(enemyBugs.size() > 0){
                    drawBugs(enemyBugs);
                }
                for(Projectile projectile : projectiles){
                    drawProjectile(projectile);
                }
                // try{
                //     physicsThread.join();
                //     koleadaThread.join();
                // } catch(InterruptedException e){

                // }
                // checks if its were debugging
                if(debugStats){
                    fill(200);
                    frameTime = (System.nanoTime() - startTime) / (float)1000000.0;

                    textSize(10);
                    text("frame time: " + frameTime + "ms" , 5, 10);  // Text wraps within text box
                    
                }
                try{
                    entityListLock.acquire();
                }
                catch(InterruptedException exc){
                    System.out.println(exc);
                }
                entityListLock.release();
                

                
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
                //player.addVelocity(new Coordinate(-1, 0));
                //player.addAcceleration(new Coordinate((float) -1, 0));
                //System.out.println("Left arrow pressed");
                //System.out.println(player.getCoord().toString());
            }
            else if(keyCode == RIGHT){
                //player.addAcceleration(new Coordinate((float)1, 0));;
                player.inputHandle.inputsDown[InputHandler.inputs.RIGHT.get()] = true;
               //player.addVelocity(new Coordinate(1, 0));
                ///System.out.println("Right arrow pressed");
                //System.out.println(player.getCoord().toString());
            }
            else if(keyCode == UP){
                player.inputHandle.inputsDown[InputHandler.inputs.UP.get()] = true;
                //player.addVelocity(new Coordinate(0, 1));

                //System.out.println("Right arrow pressed");
                //System.out.println(player.acceleration.toString());
            }
            else if(keyCode == DOWN){
                player.inputHandle.inputsDown[InputHandler.inputs.DOWN.get()] = true;
                //player.addVelocity(new Coordinate(0, -1));

                //System.out.println("Right arrow pressed");
                //System.out.println(player.acceleration.toString());
            }
            else if(key == 'n' ){
                debugStats = !debugStats;
            }
            else if (key == ' ')
            {
                playerFire(player);
                player.inputHandle.inputsDown[InputHandler.inputs.SPACE.get()] = true;
                player.debugPhys();
                System.out.println("SPACE key pressed");
            }
            else if(keyCode == ESC){
                key = 0;
                state = GameState.MENU;
            }
            else if(key == 'c'){
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
