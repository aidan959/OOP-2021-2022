package ie.tudublin;

import com.jogamp.opengl.util.RandomTileRenderer;

import processing.core.PApplet;

public class Arrays extends PApplet{
    float[] rainfall = {45,37,55,27,38,50,79,48,104,51,100,58};
    String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    public void settings(){
        size(500,500);
    }
    float min, max;
    int indexmin = 0;
    int indexmax = 0;
    float avg = 0;
    public void setup(){

        max = rainfall[0];
        min = rainfall[0];
        for(int i = rainfall.length - 1; i >= 0; i-- ){
            if(rainfall[i] < min){
                min = rainfall[i];
                indexmin  = i;
            }
            if(rainfall[i] > max){
                max = rainfall[i];
                indexmax = i;
            }
            avg += rainfall[i];
            println(rainfall[i] + "\t" + months[i]);

        }
        avg /= rainfall.length;
        println("minimum"+indexmin+" position " +rainfall[indexmin] + "\t" + months[indexmin]);
        println("maximum"+indexmax+" position " +rainfall[indexmax] + "\t" + months[indexmax]);
        println("average rainfall "+avg);
        background(0);
        colorMode(HSB);
    }
    public void draw()
    {
        background(0);
        float gap = width/rainfall.length;
        float height_bar = 0;
        for(int i = 0; i < rainfall.length; i++)
        {
                fill(map(i, 0, rainfall.length, 0, 255),255,255);
                noStroke();
                height_bar = map(rainfall[i],0,max,0, height-100);
                rect(gap *i, height, gap, - height_bar);
                fill(0);
                textAlign(CENTER,CENTER);
                fill(255,255,255);
                noStroke();
                text(months[i], gap*i + gap/2, height-10);
                fill(255);
                noStroke();
                textAlign(CENTER,CENTER);
                text(rainfall[i]+ " ml", gap*i + gap/2,height-height_bar  -20);
            }

    }
    
    
}
