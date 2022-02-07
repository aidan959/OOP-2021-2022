package ie.tudublin;
import java.util.Vector;
public class Menu
{
    private int output;
    private Vector<MenuObject> menuItems;
    enum MenuChoice{
        START,
        CREDITS,
        QUIT
    }
    public Menu(Vector<MenuObject> menuItems){
        this.menuItems = menuItems;
    }
    static class MenuObject{
        public MenuChoice option;
        public String menuText;
        public Coordinate position;
        public Coordinate bottomRightPosition;
        public Coordinate size;
        public Runnable functionToExec;
        public Boolean selected = false;
        public MenuObject(MenuChoice option, String menuText, Coordinate position, Coordinate bottomPosition, Coordinate size){
            this.menuText = menuText;
            this.position = position;
            this.bottomRightPosition = bottomPosition;
            this.option = option;
            this.size =  size;
        }
        static void menuRun(Runnable toRun){
            toRun.run();
        }
        public void clicked(){
            selected = true;
        }
        
    }
    public MenuChoice getOutput() throws Exception{
        for(MenuObject menuObj : menuItems){
            if(menuObj.selected){
                return menuObj.option;
            }
        }
        throw new Exception("");
    }
    // returns the object - returned when displaying
    public Vector<MenuObject> returnMenuObjects(){
        return this.menuItems;
    }
}
