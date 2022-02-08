package ie.tudublin;

public class InputHandler{
    public static enum inputs{
        LEFT(0),
        RIGHT(1),
        UP(2),
        DOWN(3);
        private final int value;
        private inputs(int  value){
            this.value = value;
        }
        public int get(){
            return value;
        }
    }
    public boolean[] inputsDown = {false, false, false, false};
    public InputHandler(){
        
    }
}
