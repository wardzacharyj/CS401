import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

/**
 * This class created Cloud objects for drawing onto JPanel
 * It implements the MyShape interface
 * @author Zach
 *
 */

public class MyCloud implements MyShape{
	int X,Y,W,H;
	private Ellipse2D[] ovals = new Ellipse2D[4];
	private Color color;
	private boolean isHighlighted;
	
	MyCloud(int startX, int startY, int w, int h){
		X = startX;
		Y = startY;
		color = ColorPanel.getCurrentColor();
		W = w;
		H = h;
		setUp();
	}
	public MyCloud(int startX, int startY, int w, int h, Color c) {
		X = startX;
		Y = startY;
		W = w;
		H = h;
		color = c;
		setUp();
	}
	public void setUp(){
		ovals[0] = new Ellipse2D.Double(X,Y,W-10,H-(H/3));
		ovals[1] = new Ellipse2D.Double(X+(W/4),Y+ (H/3),W/2,H/2);
		ovals[2] = new Ellipse2D.Double(X-(W/6),Y -H/3 ,W - (W/2),H/3);
		ovals[3] = new Ellipse2D.Double(X - (W/2),Y -(H/4), W- (W/4), H - (H/3));
		
	}
	public void draw(Graphics2D g) {
		if(!isHighlighted){
			g.setColor(color);
			g.fill(ovals[0]);
			g.fill(ovals[1]);
			g.fill(ovals[2]);
			g.fill(ovals[3]);
		}
		else{
			g.setColor(Color.red);
			g.fill(ovals[0]);
			g.fill(ovals[1]);
			g.fill(ovals[2]);
			g.fill(ovals[3]);
		}
		
	}
	public void move(int x, int y) {
		int deltaX = x - X;
		int deltaY = y - Y;
		
		ovals[0] = new Ellipse2D.Double((X+deltaX),(Y+deltaY),W-10,H-(H/3));
		ovals[1] = new Ellipse2D.Double((X+deltaX)+(W/4),(Y+deltaY)+(H/3),W/2,H/2);
		ovals[2] = new Ellipse2D.Double((X+deltaX)-(W/6),(Y+deltaY)-H/3 ,W - (W/2),H/3);
		ovals[3] = new Ellipse2D.Double((X+deltaX)-(W/2),(Y+deltaY)-(H/4),W- (W/4), H - (H/3));
		
		X = x;
		Y = y;
		
	}
	public void highlight(boolean b) {
		isHighlighted = b;
		
	}
	public boolean contains(double x, double y) {
		for(Ellipse2D o : ovals){
			if(o.contains(x,y))
				return true;
		}
		return false;
	}
	public void resize(int newW, int newH) {
		W = newW;
		H = newH;
		setUp();
	}
	
	public String saveData() {
		// TODO Auto-generated method stub
		return "C:" + X + ":" + Y +":" + W + ":" + H +":" +color.getRed()+":"+color.getGreen()+":"+color.getBlue();
	}
	/**
	 * Creates a new MyShape for copying and pasting
	 * solves shared address issue
	 */
	public MyShape moveShape(int x, int y) {
		move(x,y);
		return new MyCloud(X, Y, W, H);
	}

}
