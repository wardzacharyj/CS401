import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;

/**
 * This class creates a modified Rectangle objects for drawing onto JPanel
 * It implements the MyShape interface
 * @author Zach
 *
 */

public class MyMountain implements MyShape {
	private int X,Y,W,H;
	private Polygon mountain;
	private boolean isHighlighted;
	private Polygon snowcap;
	private Color color;
	private Rectangle2D perimeter;
	MyMountain(int startX, int startY, int w, int h){
		X = startX;
		Y = startY;
		W = w;
		H = h;
		color = ColorPanel.getCurrentColor();
		setUp();
	}
	MyMountain(int startX, int startY, int w, int h, Color c){
		X = startX;
		Y = startY;
		W = w;
		H = h;
		color = c;
		setUp();
	}
	public void setUp(){
		mountain = new Polygon();
		mountain.addPoint(X + (W/2), Y);
		mountain.addPoint(X+W, Y+H );
		mountain.addPoint(X,Y+H);
		snowcap = new Polygon();
		
		//int left = X + ((H/3) *W)/3;
		snowcap.addPoint(X +(W/2),Y);
		snowcap.addPoint(X +(W/3) , Y+(H/3)); //Left
		snowcap.addPoint(X +(W/2), Y+(H/4));
		snowcap.addPoint(X+2*(W/3), Y+(H/3)); //Right
		perimeter = new Rectangle2D.Double(X, Y, W, H);
		
	}
	public void draw(Graphics2D g) {
		if(!isHighlighted){
			g.setColor(color);
			g.fill(mountain);
			g.setColor(Color.white);
			g.fill(snowcap);
		}
		else{
			g.setColor(Color.black);
			g.draw(mountain);
			g.setColor(Color.white);
			g.draw(snowcap);
		}
			
	}

	@Override
	public void move(int x, int y) {
		int deltaX = x - X;
		int deltaY = y - Y;
		
		mountain = new Polygon();
		mountain.addPoint((X+deltaX)+ (W/2), (Y+deltaY));
		mountain.addPoint((X+deltaX)+W, (Y+deltaY)+H );
		mountain.addPoint((X+deltaX),(Y+deltaY)+H);
		snowcap = new Polygon();
		snowcap.addPoint((X+deltaX)+(W/2),(Y+deltaY));
		snowcap.addPoint((X+deltaX)+(W/3) , (Y+deltaY)+(H/3)); //Left
		snowcap.addPoint((X+deltaX) +(W/2), (Y+deltaY) + (H/4));
		snowcap.addPoint((X+deltaX)+2*(W/3), (Y+deltaY)+(H/3)); //Right	
		
		
		
		X = x;
		Y = y;
		perimeter = new Rectangle2D.Double(X,Y,W,H);
		//perimeter = new Rectangle2D.Double((X+deltaX),(Y+deltaY),W,H);
		
	}
	public void highlight(boolean b) {
		isHighlighted = b;	
	}
	public boolean contains(double x, double y) {
		return perimeter.contains(x, y);
	}
	public void resize(int newW, int newH) {
		W = newW;
		H = newH;
		setUp();
		
	}
	public String saveData() {
		return "M:" + X + ":" + Y +":" + W + ":" + H + ":" +color.getRed()+":"+color.getGreen()+":"+color.getBlue();
	}
	public MyShape moveShape(int x, int y) {
		move(x,y);
		return new MyMountain(X, Y, W, H);
	}
}
