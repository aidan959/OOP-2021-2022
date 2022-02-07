package ie.tudublin;

public class Engine
{
    Level currentLevel = new Level("map1.map");
    public void run(){
        this.loadLevel();
    }

    public void loadLevel(){
        this.loadLevel();
    }
}
class Level
{
    public Level(String MAP_NAME){
        load(MAP_NAME);
    }
    Coordinate spawnPoint;
    
    public void load(String MAP_NAME){

    }
}
class Render 
{
    public void render(){
        // hopefully handles rendering shit
    }
}