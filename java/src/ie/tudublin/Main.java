package ie.tudublin;
import java.util.Arrays;
import java.lang.Thread;
public class Main
{
    
    public static void helloProcessing()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new HelloProcessing());
    }
    public static void bugZap()
    {
        String[] a = {"Main"};
        processing.core.PApplet.runSketch(a, new BugZap());
    }

    public static void loops(){
        String[] a = {"Main"};
        processing.core.PApplet.runSketch(a, new Loops());
    }
    public void cat()
    {
        System.out.println("Hello world");

        Animal misty = new Animal("Misty");
        Animal greg = new Animal("Lucy");

        greg = misty;
        misty.setName("Greg");

        System.out.println(misty);
        System.out.println(greg);

        
        Cat cat = new Cat("Ginger");

        while(cat.getNumLives() > 0)
        {
            cat.kill();
        }
        cat.kill();

    } 

    public static void arrays()
    {
        String[] a = {"Main"};
        //processing.core.PApplet.runSketch(a, new Arrays());
    }

    public static void main(String[] args)
    {
        //arrays();
        //bugZap();
        // Queue q = new Queue();
        // q.enQueue(10);
        // q.enQueue(15);
        // q.enQueue(200);
        // q.enQueue(250);

        // if(q.isMember(10)){
        //     System.out.println("is member");
        // }
        // System.out.println(q.deQueue());
        // System.out.println(q.deQueue());         
        // System.out.println(q.deQueue());       
        // System.out.println(q.deQueue());         
        Stack s = new Stack();
        s.push(10);
        s.push(20);

        System.out.println("");
        s.display();

        
        // System.out.println(s.pop());
        // System.out.println(s.pop());
        // System.out.println(s.pop());
        

    }
}