package ie.tudublin;

import processing.core.PApplet;

public class Loops extends PApplet {

    int mode = 0;

    public void settings() {
        size(500, 500);
    }

    public void setup() {
        colorMode(HSB);

    }

    public void keyPressed() {
        if (key >= '0' && key <= '9') {
            mode = key - '0';
        }
        println(mode);
    }

    float magicMap(float a, float b, float c, float d, float e) {
        float output;
        a -= b;
        c -= b;
        e -= d;

        output = ((a / c) * e) + d;

        return output;
    }

    float magicMap1(float a, float b, float c, float d, float e) {
        float r1 = c - b;
        float r2 = e - d;
        float howFar = a - b;

        return d + ((howFar / r1) * r2);
    }
    float offset=0;
    public void draw() {

        switch (mode) {
            
            case 0:
                background(0);
                int bars = (int) (mouseX / 20.0f);
                float w = width / (float) bars;
                for (int i = 0; i < bars; i++) {
                    noStroke();
                    fill(map(i, 0, bars, 0, 255), 255, 255);
                    rect(map(i, 0, bars, 0, 500), 0, w, height);
                }
                break;
            case 1:
                background(0);
                int squares = (int) (mouseX / 20.0f);
                float h = width / (float) squares;
                // float h1 = 0;
                for (int i = 0; i < squares; i++) {

                    noStroke();
                    fill(map(i, 0, squares, 0, 255), 255, 255);
                    float x = map(i, 0, squares, 0, width);
                    rect(x, x, h, h);
                    rect((width - h) - x, x, h, h);

                }
                break;
            case 2:
                background(0);
                int circles = (int) (mouseX / 20.0f);
                for (int i = 0; i < circles; i++) {
                    noStroke();
                    fill(map(i, 0, circles, 0, 255), 255, 255);
                    float circleWidth = width - map(i, 0, circles, 0, width - 50);
                    ellipse(width / 2, height / 2, circleWidth, circleWidth);
                }
                break;
            case 3:
                background(0);
                int circles2 = (int) (mouseX / 20.0f);
                float size = width / (float) circles2;
                
                
                offset += mouseY/10;
                background(0);
                for (int i = 0; i < circles2; i++) {

                    for (int j = 0; j < circles2; j++) {
                        noStroke();
                        float c = map(i+j + offset,0,circles2 *2,0,255) % 256;
                        float x = map(i, 0, circles2 - 1, size/2, width - (size/2.0f));
                        float y = map(j, 0, circles2 - 1, size/2, height - (size/2.0f));
                        fill(c, 255, 255);
                        circle(x, y, size);

                        // ellipse((width - h1) - x, x, h1, h1);
                    }
                }
                
                break;
            case 4:
                background(0);
                for (int x=0; x < width; x++) {
                float noiseVal = noise((mouseX+x)*0.02f, mouseY*0.02f);
                stroke(noiseVal*255);
                line(x, mouseY+noiseVal*80, x, height);
                }
            break;
            // case 2:
            // background(0);
            // //int squares = (int) (mouseX / 20.0f);

            // float w2 = width / (float)10;
            // //float h1 = 0;
            // for(int i = 0 ; i < 10 ; i ++)
            // {
            // noStroke();
            // fill(map(i, 0, 10, 0, 255), 255, 255);
            // rect(map(i, 0, 10, 0, 500), map(i, 0, 10, 0, 500), w1, height/10);
            // noStroke();
            // fill(map(i, 0, 10, 0, 255), 255, 255);
            // rect(500-map(i, 0, 10, 0, 500), 500-map(i, 0, 10, 0, 500), w2, height/10);
            // }
            // h1 += height/10;
            // map(a,b,c,d,e);
            // a = inputvalue
            // b - c - start and end of the first range
            // d, e 0 - start and and of the end range

            // map(-2, 10, 90, 200, 233);

        }
    }
}