// CS 401 Fall 2014
// Tree class as another implementation of the MyShape interface.
// This class also uses composition, with 2 Polygons being the primary
// components of a Tree object.  For more information on Polygons, see
// the Java API.

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 * This class created Tree objects for drawing onto JPanel
 * It implements the MyShape interface
 * @author Zach
 *
 */

class MyTree  implements MyShape
{
	// Represent a Tree in two parts -- a Polygon for the top part 
	// (the branches) and another Polygon for the trunk.  Since the
	// trunk is rectangular, a Rectangle2D could have been used, but
	// to keep consistent (especially with the move() method) I used
	// Polygon objects for both.
	private Polygon canopy;
	private Polygon trunk;

	// X, Y and size instance variables
	private int X, Y, W, H;
	private int size;
	private Color color;
	private boolean isHighlighted;
	
	// Create a new Tree object.  Note how the Polygons are built,
	// adding one point at a time to each.  If you plot the points out
	// on paper you will see how the shapes are formed.  This method
	// uses a setUp method as shown below to allow for the Tree to
	// be regenerated internally (i.e. outside the constructor)
	public MyTree(int startX, int startY, int w, int h)
	{
		W = w;
		H = h;
		X = startX;
		Y = startY;
		color = ColorPanel.getCurrentColor();
		setUp();
	}
	public MyTree(int startX, int startY, int w, int h, Color c) {
		X = startX;
		Y = startY;
		W = w;
		H = h;
		color = c;
		setUp();
	}
	
	// Create the actual parts of the Tree.  For your shapes you
	// will likely use trial and error to get nice looking results
	// (I used a LOT of trial and error for mine).
	private void setUp()
	{
		//WIDTH
		//HEIGHT
		//START &END
		
		canopy = new Polygon();
		canopy.addPoint(X, (((H * 3)/4) + Y)); //Left
		canopy.addPoint(X+W,(((H *3)/4) +Y) ); // Right
		canopy.addPoint((W/2) + X,Y); // Top Point of Triangle
		trunk = new Polygon();
		trunk.addPoint( (W /3)+X,(((H * 3)/4) + Y));
		trunk.addPoint( ((2*W)/3)+X, (((H * 3)/4) + Y));
		trunk.addPoint( ((2*W)/3)+X, H+Y);
		trunk.addPoint((W /3)+X , H+Y);
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
		g.setColor(color);
		if (isHighlighted)
			g.draw(canopy);
		else
			g.fill(canopy);
		g.setColor(new Color(61,43,31));
		if (isHighlighted)
			g.draw(trunk);
		else
			g.fill(trunk);
		
		
	}

	// Looking at the API, we see that Polygon has a translate() method
	// which can be useful to us.  All we have to do is calculate the
	// difference of the new (x,y) and the old (X,Y) and then call
	// translate() for both parts of the tree.
	public void move(int x, int y)
	{
		int deltaX = x - X;
		int deltaY = y - Y;
		canopy.translate(deltaX, deltaY);
		trunk.translate(deltaX, deltaY);
		X = x;
		Y = y;
	}

	// Polygon also has a contains() method, so this method is also
	// simple
	public boolean contains(double x, double y)
	{
		if (canopy.contains(x,y))
			return true;
		if (trunk.contains(x,y))
			return true;
		return false;
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
		return "T:" + X + ":" + Y +":" + W + ":" + H + ":" +color.getRed()+":"+color.getGreen()+":"+color.getBlue();
	}
	@Override
	public MyShape moveShape(int x, int y) {
		move(x,y);
		return new MyTree(X, Y, W, H);
	}
	
}