package ie.tudublin;

public class Main
{
    public static void helloProcessing()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new HelloProcessing());
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
    public static void main(String[] args)
    {
<<<<<<< Updated upstream
        helloProcessing();

        // Tara Misty
        // Tara Tara
        
    }
=======
        // helloProcessing();
        System.out.println("Alri");
        }
>>>>>>> Stashed changes
}