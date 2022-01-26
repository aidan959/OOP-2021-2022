package ie.tudublin;

public class Cat extends Animal
{
    int numLives;
    public int getNumLives()
    {
        return this.numLives;
    }

    public void setNumlives(int numLives)
    {
        this.numLives = numLives;
    }
    public Cat(String name)
    {
        super(name);
        numLives = 9;
    }
    public void killMe()
    {
        if(this.numLives <= 0)
        {
            System.out.println("Dead.");
        } else {
            System.out.println("Ouch.");
            this.numLives -= 1;
        }
    }
}