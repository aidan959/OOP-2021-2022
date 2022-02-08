package ie.tudublin;
import java.util.Vector;
import java.lang.Math;

public class Physics{
    Vector<Entity> listObjs;
    int frameCount = -1;
    public Physics(Vector<Entity> listObjs){
        this.listObjs = listObjs;
    }
    public Coordinate gravity = new Coordinate((float) 0, (float) 9.8);
    public Coordinate deeceleration = new Coordinate((float) 0.01, (float) 0.01);
    public void calculatePhys(){
        frameCount++;
        for(Entity obj : listObjs){
        
            if(obj.acceleration.hasMagnitude()){
                // checks if acceleration is      
                if(Math.abs(obj.acceleration.x) <= 0.01 )
                    obj.acceleration.x = 0;
                if(Math.abs(obj.acceleration.y) <= 0.01)
                    obj.acceleration.y = 0;
                // if(obj.velocity.magnitude() > 5){
                //     //obj.velocity = 5;
                // }
                obj.velocity.add(obj.acceleration.divide(new Coordinate(60, 60)));
            } else if(obj.velocity.hasMagnitude()){
                    if(Math.abs(obj.velocity.x) <= 0.01 )
                        obj.velocity.x = 0;
                    if(Math.abs(obj.velocity.y) <= 0.01)
                        obj.velocity.y = 0;
                    if(obj.velocity.x > 0){
                        //decellerate  c
                        obj.velocity.x -= (float) deeceleration.x;
                    }
                    else if(obj.velocity.x < 0){
                        obj.velocity.x += (float) deeceleration.x;
                    } 
                    if(obj.velocity.y > 0){
                        obj.velocity.y -= (float) deeceleration.y;
                    } else if(obj.velocity.y < 0){
                        obj.velocity.y += (float) deeceleration.y;
                    }
                
            }
            if(obj.isTouchingWall()){

                System.out.print("colliding with wall");
                if((obj.getX() <= 0 )){
                    obj.setX(0);
                    obj.velocity = new  Coordinate( -obj.velocity.x * (float)0.4, obj.velocity.y* (float)0.4);
                } else if((obj.getX() >= obj.WIDTH )){
                    obj.setX(obj.WIDTH);
                    obj.velocity = new  Coordinate(-obj.velocity.x* (float)0.4, obj.velocity.y* (float)0.4);
                }
                if(obj.getY() <= 0 ){
                    obj.setY(0);
                    obj.velocity = new Coordinate(obj.velocity.x* (float)0.4, -obj.velocity.y* (float)0.4);
                }
                else if(obj.getY() >= obj.HEIGHT){
                    obj.setY(obj.HEIGHT);
                    obj.velocity = new Coordinate(obj.velocity.x* (float)0.4, -obj.velocity.y* (float)0.4);
                }
            }
            obj.velocity = EngineFeatures.addCoordinate(obj.velocity, obj.acceleration);
            if(obj.velocity.x > 5){
                obj.velocity.x = 5;
            } else if(obj.velocity.x < -5){
                obj.velocity.x = -5;
            } 
            if(obj.velocity.y > 5){
                obj.velocity.y = 5;
            } else if(obj.velocity.y < -5){
                obj.velocity.y = -5;
            } 
            //v =  (1/2)(at)^2
            //obj.velocity = EngineFeatures.addCoordinate(EngineFeatures.flip(deeceleration), obj.velocity);
            obj.moveCoord(obj.velocity);
        }
    }
}
