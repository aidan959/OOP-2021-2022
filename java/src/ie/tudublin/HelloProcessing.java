package ie.tudublin;

import processing.core.PApplet;

public class HelloProcessing extends PApplet
{

	public void settings()
	{
		size(500, 500, P3D);
	}
	int dim;
	public void setup() {
		colorMode(HSB);
		background(0);
		dim = width/2;
		colorMode(HSB, 360, 100, 100);
		noStroke();
		ellipseMode(RADIUS);
		frameRate(60);
		x1 = random(0, width*2);
		x2 = random(0, width);
		y1 = random(0, height *2);
		y2 = random(0, height);

		float range = 5;

		x1dir = random(-range, range);
		x2dir = random(-range, range);
		y1dir = random(-range, range);
		y2dir = random(-range, range);

		smooth();
		
	}

	float x1, y1, x2, y2;
	float x1dir, x2dir, y1dir, y2dir;
	float c = 0;
	public void draw(){
		noFill();
		background(204);
		camera(70.0f, 35.0f, 120.0f, 50.0f, 50.0f, 0.0f, 
			0.0f, 1.0f, 0.0f);
		translate(50, 50, 0);
		rotateX(-PI/6);
		rotateY(PI/3);
		box(45);
	}
	public void draw1()
	{	
		strokeWeight(2);
		stroke(c, 255, 255);
		c = (c + 1f) % 255;
		line(x1, y1, x2, y2);

		x1 += x1dir;
		x2 += x2dir;
		y1 += y1dir;
		y2 += y2dir;
		
		if (x1 < 0 || x1 > width)
		{
			x1dir = - x1dir;
		}
		if (y1 < 0 || y1 > height)
		{
			y1dir = - y1dir;
		}

		if (x2 < 0 || x2 > width)
		{
			x2dir = - x2dir;
		}
		if (y2 < 0 || y2 > height)
		{
			y2dir = - y2dir;
		}
	}
	void drawGradient(float x, float y) {
		int radius = dim/2;
		float h = random(0, 360);
		for (int r = radius; r > 0; --r) {
		  fill(h, 90, 90);
		  ellipse(x, y, r, r);
		  h = (h + 1) % 360;
		}
	  }
}
