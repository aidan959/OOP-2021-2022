package ie.tudublin;
import java.util.Vector;
public class Menu
{
   
    private Vector<MenuObject> menuItems;
    enum MenuChoice{
        UNSELECTED,
        START,
        CREDITS,
        QUIT
    }
    public MenuChoice output;
    public void setOutput(){
        boolean objFound = false;
        for(MenuObject obj : menuItems){
            if(obj.selected){
                output = obj.option;
                objFound = true;
                break;
            }
        }
        if (!objFound){
            output = MenuChoice.UNSELECTED;
        }
    }
    public Menu(){
        this.menuItems = new Vector<MenuObject>();
    }
    public MenuObject createMenuObject(MenuChoice option, String menuText, Coordinate position, Coordinate size){
        MenuObject menuObj = new Menu.MenuObject(option, menuText, position, EngineFeatures.addCoordinate(position, size), size );
        menuItems.add(menuObj);
        return menuObj;
    }
    class MenuObject{
        public MenuChoice option;
        public String menuText;
        public Coordinate position;
        public Coordinate bottomRightPosition;
        public Coordinate size;
        public Runnable functionToExec;
        public Boolean selected = false;
        public Coordinate textStart;
        public Coordinate textEnd;
        public MenuObject(MenuChoice option, String menuText, Coordinate position, Coordinate bottomPosition, Coordinate size){
            this.menuText = menuText;
            this.position = position;
            this.bottomRightPosition = bottomPosition;
            this.option = option;
            this.size = size;
            textStart = new Coordinate(this.position.x + (this.size.x)/16, this.position.y + (this.size.y)/8); 
            textEnd = new Coordinate(this.bottomRightPosition.x -(this.size.x)/16, this.bottomRightPosition.y - (this.size.y) /8  );
            /**
             * System.out.println("Position 1: " + position);
             * System.out.println("Position 2: " + bottomRightPosition);
             * System.out.println("Text start: " + textStart);
             * System.out.println("Text end: " +  textEnd);
             * System.out.println("Size: " + size);
            **/

        }
        static void menuRun(Runnable toRun){
            toRun.run();
        }
        public void clicked(){
            selected = true;
        }
        
    
    }
    // returns the object - returned when displaying
    public Vector<MenuObject> returnMenuObjects(){
        return this.menuItems;
    }
}
