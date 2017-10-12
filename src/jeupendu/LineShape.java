package jeupendu;
import java.awt.Graphics;

public class LineShape extends Shape {
	
    public LineShape(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public LineShape() {
        super();
    }

    @Override
    public void draw(Graphics g) {
        g.drawLine(getX(), getY(), getWidth(), getHeight());
    }
}