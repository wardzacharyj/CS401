import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * This class created Oval objects for drawing onto JPanel
 * It implements the MyShape interface
 * @author Zach
 *
 */

public class MyOval implements MyShape{
	private int X,Y,W,H;
	private boolean isHighlighted;
	private Color color;
	private Ellipse2D.Double r;
	public MyOval(int startX, int startY, int w, int h) {
		X = startX;
		Y = startY;
		W = w;
		H = h;
		color = ColorPanel.getCurrentColor();
	}
	public MyOval(int startX, int startY, int w, int h, Color c) {
		X = startX;
		Y = startY;
		W = w;
		H = h;
		color = c;
	}
	public void draw(Graphics2D g) {
		r = new Ellipse2D.Double(X,Y,W,H);
		g.setColor(color);
		if(isHighlighted)
			g.draw(r);
		else
			g.fill(r);
	}

	@Override
	public void move(int x, int y) {
		X = x;
		Y = y;
		r = new Ellipse2D.Double(X,Y,W,H);
	}

	@Override
	public void highlight(boolean b) {
		isHighlighted = b;
	}

	@Override
	public boolean contains(double x, double y) {	
		return r.contains(x,y);
	}

	public void resize(int newW, int newH) {
		W = newW;
		H = newH;	
	}

	@Override
	public String saveData() {
		return "O:"+X+":"+Y+":"+W+":"+H+":" +color.getRed()+":"+color.getGreen()+":"+color.getBlue();
	}
	/**
	 * Creates a new MyShape for copying and pasting
	 * solves shared address issue
	 */
	public MyShape moveShape(int x, int y) {
		move(x,y);
		return new MyOval(X, Y, W, H);
	}

}
