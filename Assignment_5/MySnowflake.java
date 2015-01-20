// CS 401 Fall 2014
// Tree class as another implementation of the MyShape interface.
// This class also uses composition, with 2 Polygons being the primary
// components of a Tree object.  For more information on Polygons, see
// the Java API.

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

class MySnowflake  implements MyShape
{
	// Represent a Tree in two parts -- a Polygon for the top part 
	// (the branches) and another Polygon for the trunk.  Since the
	// trunk is rectangular, a Rectangle2D could have been used, but
	// to keep consistent (especially with the move() method) I used
	// Polygon objects for both.
	Line2D line1,line2,line3,line4;
	private Rectangle2D perimeter;
	// X, Y and size instance variables
	private int X, Y, W, H;
	private int size;

	private boolean isHighlighted;
	
	// Create a new Tree object.  Note how the Polygons are built,
	// adding one point at a time to each.  If you plot the points out
	// on paper you will see how the shapes are formed.  This method
	// uses a setUp method as shown below to allow for the Tree to
	// be regenerated internally (i.e. outside the constructor)
	public MySnowflake(int startX, int startY, int w, int h)
	{
		W = w;
		H = h;
		X = startX;
		Y = startY;
		setUp();
	}
	
	
	// Create the actual parts of the Tree.  For your shapes you
	// will likely use trial and error to get nice looking results
	// (I used a LOT of trial and error for mine).
	private void setUp()
	{	
		perimeter = new Rectangle2D.Double(X,Y,W,H);

		line1 = new Line2D.Double(X + (W/2), Y,X + (W/2),Y+H); //Vertical Line
		line2 = new Line2D.Double(X, Y + (H/2), X + (W), Y + (H/2)); //Horizontal Line
		line3 = new Line2D.Double(X+(H/4),Y+(H/4),(X + W)-(H/4),(Y+H)-(H/4)); // Top Left
		line4 = new Line2D.Double(X+(H/4),(Y+H)-(H/4),(X+W) - (H/4),Y + (H/4)); //Top Right	
	}

	public void highlight(boolean b)
	{
		isHighlighted = b;
	}

	// The Polygon class can also be drawn with a predefined method in
	// the Graphics2D class.  There are two versions of this method:
	// 1) draw() which only draws the outline of the shape
	// 2) fill() which draws a solid shape
	// In this class the draw() method is used when the object is
	// highlighted.
	// The colors chosen are RGB colors I looked up on the Web.  Take a
	// look and use colors you like for your shapes.
	public void draw(Graphics2D g)
	{
		Graphics2D g2 = (Graphics2D) g;
	    g2.setStroke(new BasicStroke(2));

		if (!isHighlighted)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.red);
		g2.draw(line1);
		g2.draw(line2);
		g2.draw(line3);
		g2.draw(line4);
	}

	// Looking at the API, we see that Polygon has a translate() method
	// which can be useful to us.  All we have to do is calculate the
	// difference of the new (x,y) and the old (X,Y) and then call
	// translate() for both parts of the tree.
	public void move(int x, int y)
	{
		int deltaX = x - X;
		int deltaY = y - Y;
		line1 = new Line2D.Double((X+deltaX) + (W/2), (Y + deltaY),(X+deltaX) + (W/2),(Y+deltaY)+H);
		line2 = new Line2D.Double((X+deltaX), (Y+deltaY) + (H/2), (X+deltaX) + (W), (Y+deltaY) + (H/2));
		line3 = new Line2D.Double((X+deltaX),(Y+deltaY),(X+deltaX) + W,(Y+deltaY)+H); // Starting po
		line4 = new Line2D.Double((X+deltaX),(Y+deltaY)+H,(X+deltaX)+W,(Y+deltaY));
		perimeter = new Rectangle2D.Double(X + deltaX,Y+deltaY,W,H);
		X = x;
		Y = y;
	}

	// Polygon also has a contains() method, so this method is also
	// simple
	public boolean contains(double x, double y)
	{	
		return perimeter.contains(x,y);
	}

	// The move() method for the Polygons that are in Tree are not
	// reconfigured like in Snowflake, so we can't use the trick used
	// there.  Instead, we just change the size and call setUp() to
	// regenerate all of the objects.
	public void resize(int newW, int newH) {
		W = newW;
		H = newH;
		setUp();
		
	}

	// Note again the format
	public String saveData()
	{
		return ("S:" + X + ":" + Y + ":" + W + ":" + H + ":");
	}
	public MyShape moveShape(int x, int y) {
		move(x,y);
		return new MySnowflake(X, Y, W, H);
	}
}

