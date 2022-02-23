package ie.tudublin;
import java.util.Vector;
import java.lang.Runnable;
import java.util.Queue;
import java.util.concurrent.*;

import com.jogamp.nativewindow.util.Rectangle;

import java.util.LinkedList;
public class Koleada implements Runnable{
    public Vector<Entity> detecting;
    public Queue<Collision> collisionQueue = new LinkedList<Collision>();
    int tick=0;
    int i,j;
    public Semaphore entityLock;
    int gameTick=0;
    Vector<Collision> collisions = new Vector<Collision>();
    public Koleada(Vector<Entity> list, Semaphore lock){
        detecting = list;
        entityLock = lock;
        
    }
    public void run(){
        try{
            entityLock.acquire();
        
            for(i = 0; i < detecting.size(); i++){
                if(detecting.elementAt(i).collision_sleeping){
                    // 
                    for(j=0; j < detecting.size(); j++){

                        if(i==j){

                        } else{
                            if(detecting.elementAt(i).model instanceof Circle && detecting.elementAt(j).model instanceof Circle ){
                                if(detecting.elementAt(i).handled){

                                } else{
                                    if(checkEdges(detecting.elementAt(i).model, detecting.elementAt(j).model)){
                                        Collision collision = new Collision(detecting.elementAt(i), detecting.elementAt(j));
                                        if(!collision.to.handled){
                                            collisionQueue.offer(collision);
                                        }

                                    }
                            }
                            }
                        }
                    }
                }
            }
            if(tick++ % 60 == 0){
                System.out.println("Collision queue");
                
                System.out.println(Thread.currentThread().getName());
            }
        }
        
        catch(InterruptedException exc){
            System.out.println(exc);
        }

        entityLock.release();
    }
    public boolean checkEdges(Circle shape1, Circle shape2){

        double shape1coordx = shape1.points.elementAt(0).x;
        double shape1coordy = shape1.points.elementAt(0).y;

        double shape2coordx = shape2.points.elementAt(0).x;
        double shape2coordy = shape2.points.elementAt(0).y;


        double xsquared = Math.pow((shape2coordx - shape1coordx), 2);
        double ysquared = Math.pow((shape2coordy - shape1coordy), 2);

        if (Math.sqrt(xsquared + ysquared) <= shape1.radius + shape2.radius){
            return true;
        }
            return false;
    }
    //public boolean checkEdges(Circle shape1, REct)
    class Collision{
        Entity from;
        Entity to;
        public Collision(Entity from, Entity to){
            this.from = from;
            this.to = to;
        }
        public void handle(){
            System.out.println(from.mass * from.velocity.x);
        }
    }

}
