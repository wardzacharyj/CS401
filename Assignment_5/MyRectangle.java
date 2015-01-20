import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * This class creates an modified Rectangle object that implements
 * MyShape and acts accordingly
 * @author Zach
 *
 */
public class MyRectangle implements MyShape{
	private int X,Y,W,H;
	private boolean isHighlighted;
	private Color color; 
	private Rectangle r = new Rectangle();
	public MyRectangle(int startX, int startY, int w, int h) {
		color = ColorPanel.getCurrentColor();
		X = startX;
		Y = startY;
		W = w;
		H = h;
	}
	public MyRectangle(int startX, int startY, int w, int h, Color c) {
		X = startX;
		Y = startY;
		W = w;
		H = h;
		color = c;
	}
	public void draw(Graphics2D g) {
		r = new Rectangle(X,Y,W,H);
		if(isHighlighted)
			g.draw(r);
		else{
			g.setColor(color);
			g.fill(r);
		}
	}

	@Override
	public void move(int x, int y) {
		r = new Rectangle(X,Y,W,H);
		X = x;
		Y = y;
	}

	@Override
	public void highlight(boolean b) {
		isHighlighted = b;
	}

	@Override
	public boolean contains(double x, double y) {	
		return r.contains(x,y);
	}
	@Override
	public void resize(int newW, int newH) {
		W = newW;
		H = newH;
	}

	@Override
	public String saveData() {
		return "R:" + X + ":" + Y +":" + W + ":" + H + ":"+color.getRed()+":"+color.getGreen()+":"+color.getBlue();
	}
	public MyShape moveShape(int x, int y) {
		move(x,y);
		return new MyRectangle(X, Y, W, H);
	}

}
