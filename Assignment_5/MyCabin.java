import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 * This class created Cabin objects for drawing onto JPanel
 * It implements the MyShape interface
 * @author Zach
 *
 */

public class MyCabin implements MyShape {

	// X, Y and size instance variables
	private int X, Y, W, H;
	private Color color; 
	private Rectangle[] rec = new Rectangle[6];
	private Rectangle door;
	private Polygon roof;
	private Rectangle2D perimeter;
	
	private boolean isHighlighted;
	MyCabin(int startX, int startY, int w, int h){
		color = ColorPanel.getCurrentColor();
		X = startX;
		Y = startY;
		W = w;
		H = h;
		setUp();
	}
	public MyCabin(int startX, int startY, int w, int h, Color c) {
		X = startX;
		Y = startY;
		W = w;
		H = h;
		color = c;
		setUp();
	}
	public void setUp(){
		perimeter = new Rectangle2D.Double(X, Y, W, H);
		rec[0] = new Rectangle(X,Y+H - H/8 ,W,H/8 + 1/8);
		rec[1] = new Rectangle(X+10, Y+H - 2*(H/8) ,W-20,H/8 + 2/8);
		rec[2] = new Rectangle(X,Y+H-3*(H/8) ,W,H/8 + 3/8);
		rec[3] = new Rectangle(X+10, Y+H - 4*(H/8) ,W-20,H/8 + 4/8);
		rec[4] = new Rectangle(X, Y+H - 5*(H/8) ,W,H/8 + 5/8);
		rec[5] = new Rectangle(X+10,Y+H,W-20, H/4);
		roof = new Polygon();
		roof.addPoint(X + (W/2),Y);
		roof.addPoint(X, Y + 4 *(H/8));
		roof.addPoint(X + W, Y + 4 *(H/8));
		
		door = new Rectangle(X+W/3, Y+H - H/3, W/8, H/3);		
	}
	public void draw(Graphics2D g) {
		
		//Hex colors are preset for ceartain parts however roof is customizable
		if(!isHighlighted){
			g.setColor(new Color(0x795548));
			g.fill(rec[0]);
			g.fill(rec[2]);
			g.setColor(new Color(0x5D4037));
			g.fill(rec[4]);
			g.fill(rec[5]);
			g.setColor(new Color(0x8D6E63));
			g.fill(rec[1]);
			g.fill(rec[3]);
			g.setColor(new Color(0x4E342E));
			g.setColor(color);
			g.fill(roof);
			g.setColor(Color.black);
			g.fill(door);
		}
		else{
			g.setColor(new Color(0x795548));
			g.draw(rec[0]);
			g.draw(rec[2]);
			g.setColor(new Color(0x5D4037));
			g.draw(rec[4]);
			g.draw(rec[5]);
			g.setColor(new Color(0x8D6E63));
			g.draw(rec[1]);
			g.draw(rec[3]);
			g.setColor(new Color(0x4E342E));
			g.draw(roof);
			g.setColor(Color.black);
			g.draw(door);
		}		
	}
	public void move(int x, int y) {
		int deltaX = x -X;
		int deltaY = y - Y;
		
		perimeter = new Rectangle2D.Double(X + deltaX, Y+deltaY, W, H);
		rec[0] = new Rectangle(X + deltaX,(Y+deltaY)+H - H/8 ,W,H/8 + 1/8);
		rec[1] = new Rectangle((X+deltaX)+10,(Y+deltaY)+H - 2*(H/8) ,W-20,H/8 + 2/8);
		rec[2] = new Rectangle(X+deltaX,(Y+deltaY)+H-3*(H/8) ,W,H/8 + 3/8);
		rec[3] = new Rectangle((X+deltaX)+10, (Y+deltaY)+H - 4*(H/8) ,W-20,H/8 + 4/8);
		rec[4] = new Rectangle(X+deltaX, (Y+deltaX)+H - 5*(H/8) ,W,H/8 + 5/8);
		rec[5] = new Rectangle((X+deltaX)+10,(Y+deltaY)+H,W-20, H/4);
		roof = new Polygon();
		roof.addPoint((X+deltaX) + (W/2),Y+deltaY);
		
		//roof.addPoint(W, H/8 +5/8);
		roof.addPoint((X+deltaX), (Y+deltaY) + 4 *(H/8));
		roof.addPoint((X+deltaX) + W, (Y+deltaY) + 4 *(H/8));
		
		door = new Rectangle((X+deltaX)+W/3, (Y+deltaY)+H - H/3, W/8, H/3);
		
		X = x;
		Y = y;
	}
	public void highlight(boolean b) {
		isHighlighted = b;	
	}
	public boolean contains(double x, double y) {
		return perimeter.contains(x,y);
	}
	public void resize(int newW, int newH) {
		W = newW;
		H = newH;
		setUp();
		
	}
	public String saveData() {
		// TODO Auto-generated method stub
		return  "H:" + X + ":" + Y +":" + W + ":" + H + ":" +color.getRed()+":"+color.getGreen()+":"+color.getBlue();
	}
	/**
	 * Creates a new MyShape for copying and pasting
	 * solves shared address issue
	 */
	public MyShape moveShape(int x, int y) {
		move(x,y);
		return new MyCabin(X, Y, W, H);
	}

}
