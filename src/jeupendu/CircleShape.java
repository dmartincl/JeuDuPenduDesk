package jeupendu;

import java.awt.Graphics;

public class CircleShape extends Shape {
	
	public  CircleShape() {
		super();
	}
	
	public CircleShape(int x, int y, int width, int height){
		super(x, y, width, height);
	}
	
	

	@Override
	public void draw(Graphics g) {
		g.drawOval(getX(), getY(), getWidth(), getWidth());
		
	}
	
	
	

}
