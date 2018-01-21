package brickBreaker;

import java.awt.Graphics;
import java.awt.Color;


public class Ball {
	public int x;
	public int y;
	public int xDir;
	public int yDir;
	public int diameter = 20;
	public Color color = Color.blue;
	
	public Ball(int startX, int startY, int startxDir, int startyDir) {
		x = startX;
		y = startY;
		xDir = startxDir;
		yDir = startyDir;
	}
	
	public void draw (Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, diameter, diameter);
	}

	public void moveInFrame(int frameWidth) {
		x += xDir;
		y += yDir;
		if (x < 0) {
			xDir *= -1;
		}
		if (y < 0) {
			yDir *= -1;
		}
		if (x > frameWidth) {
			xDir *= -1;
		}
	}
	
	public void stop() {
		xDir = 0;
		yDir = 0;
	}
}
