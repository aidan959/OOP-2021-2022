package ie.tudublin;
import java.util.Vector;

import ie.tudublin.Koleada.Collision;
import java.util.concurrent.*;
import java.lang.Math; 

public class Physics implements Runnable{

    Vector<Entity> listObjs;

    public Semaphore entityLock;
    int frameCount = -1;
    int tick=0;
    int gameTick=0;
    public Physics(Vector<Entity> listObjs, Koleada koleada){
        this.listObjs = listObjs;
        tick = 0;
        this.koleada = koleada;
        entityLock = koleada.entityLock;
    }
    public Coordinate gravity = new Coordinate((float) 0, (float) 9.8);
    public Koleada koleada;
    public Coordinate deeceleration = new Coordinate((float) 0.01, (float) 0.01);
    
    public void run(){
        // checks if the collision queue is empty (collisions can show up the queue during this code execution)
        while(!koleada.collisionQueue.isEmpty()){
            // pull the top item from the queue
            Collision collision = koleada.collisionQueue.poll();
            // unsure why but some collisions return null - will need to figure out
            // TODO FIX
            if(collision == null){

            } else{
                // makes sure that the object is marked as handled - this is important as both objects are colliding - therefore we
                // dont want both objects to double up collision calculations
                collision.from.handled = true;
                collision.to.handled = true;
                // calculates the resultant velocity on x and y
                collision.from.velocity.x = (collision.from.mass * collision.from.velocity.x + collision.to.mass * collision.to.velocity.x)/collision.to.mass + collision.from.mass;
                collision.from.velocity.y = (collision.from.mass * collision.from.velocity.y + collision.to.mass * collision.to.velocity.y)/collision.to.mass + collision.from.mass;
                // makes sure the object being hit is woken by collision engine
                collision.to.collision_sleeping = false;
                collision.to.velocity.x = (float)1.1 * (collision.to.mass * collision.to.velocity.x + collision.from.mass * collision.from.velocity.x)/collision.to.mass + collision.from.mass;
                collision.to.velocity.y = (float)1.1 * (collision.to.mass * collision.to.velocity.y + collision.from.mass * collision.from.velocity.y)/collision.to.mass + collision.from.mass;
                // flattens out acceleration of object just incase
                collision.to.acceleration.clear();
                collision.from.acceleration.clear();
            }
        }
        try{
            // checks if we can proceed with calculations for thread synchronization
            entityLock.acquire();
        
        for(Entity obj : listObjs){
            // checks if the object is sleeping
            //if(obj.collision_sleeping){

            //} else{                
                obj.updateModel();
                obj.handled = false;
                if(obj.acceleration.hasMagnitude()){
                    // checks if acceleration is      
                    if(Math.abs(obj.acceleration.x) <= 0.01 )
                        obj.acceleration.x = 0;
                    if(Math.abs(obj.acceleration.y) <= 0.01)
                        obj.acceleration.y = 0;
                    // if(obj.velocity.magnitude() > 5){
                    //     //obj.velocity = 5;
                    // }
                    obj.collision_sleeping = false;
                    obj.velocity.add(obj.acceleration.x/60, obj.acceleration.y/60);
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
                        obj.collision_sleeping = false;
                    
                } else {
                    obj.collision_sleeping = true;
                }
                if(obj.isTouchingWall()){

                    
                    if((obj.getX() <= 0 ) ){
                        obj.setX(0);

                        obj.velocity.multiply(-0.4f, 0.4f);
                    } else if((obj.getX() >= obj.WIDTH )){
                        obj.setX(obj.WIDTH);
                        obj.velocity.multiply(-0.4f, 0.4f);
                    }
                    if(obj.getY() <= 0 ){
                        obj.setY(0);
                        obj.velocity.multiply(0.4f, -0.4f);
                    }
                    else if(obj.getY() >= obj.HEIGHT){
                        obj.setY(obj.HEIGHT);
                        obj.velocity.multiply(0.4f, -0.4f);
                    }
                }
                obj.velocity.add(obj.acceleration);
                if(obj instanceof Player){
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
                }
                //v =  (1/2)(at)^2
                //obj.velocity = EngineFeatures.addCoordinate(EngineFeatures.flip(deeceleration), obj.velocity);
                obj.moveCoord(obj.velocity);
            }
        //}
        if(tick++ % 60 == 0){
            System.out.println(Thread.currentThread().getName());
        }
        } 
        catch(InterruptedException exc){
            System.out.println(exc);

        }
        entityLock.release();
    }
}
