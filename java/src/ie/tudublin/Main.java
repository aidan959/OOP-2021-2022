package ie.tudublin;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Hello World.");
        Animal misty = new Animal("Misty");
        Animal lucy = new Animal("Lucy");

        lucy = misty;
        misty.setName("Tara");

        System.out.println(misty);
        System.out.println(lucy);
        Cat ginger = new Cat("Ginger");
        for(int i = 0; i < 10; i++ ){
            ginger.killMe();
        }
    }
}