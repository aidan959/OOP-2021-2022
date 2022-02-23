package ie.tudublin;

public class Grass {
    enum state{
        LEFT,
        CENTER,
        RIGHT
    }
    state currentState;
    public Grass(){
        currentState = state.LEFT;
    }
    public void animate(int tick){
        if(tick % 3 == 0){
            currentState = state.LEFT;
        } else if(tick % 3 == 1){
            currentState = state.CENTER;
        } else if(tick % 3 == 2){
            currentState = state.RIGHT;
        }
    }
    
}
