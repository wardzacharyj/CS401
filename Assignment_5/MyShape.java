// CS 401 Fall 2014
// MyShape interface for Assignment 5
// See the method descriptions below and see the example implementation
// in classes Snowflake and Tree.  For the assignment you must complete
// the additional implementations in classes Cloud and Cabin.

import java.awt.*;
public interface MyShape
{                            
	// Draw the shape onto the Graphics2D context g.
	public void draw(Graphics2D g);


	public void move(int x, int y);
	
	public MyShape moveShape(int x,int y);

	public void highlight(boolean b);

	public boolean contains(double x, double y);

	public void resize(int newW, int newH);
	
	public String saveData();
}