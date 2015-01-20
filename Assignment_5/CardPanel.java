import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

/**
 * Class: CS-0401
 * CardPanel.java
 * Purpose: Hangles custom background of ToolBox JPanel
 * @author Zach Ward
 * @version 1.0
 */
public class CardPanel extends JPanel {
	private static final long serialVersionUID = 1L;
		/*
		 * Initializes the CardPanel Size
		 */
		public int getWidth(){
			return 82;
		}
		public int getHeight(){
			return 400;
		}
		public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        setBackground(Color.WHITE);
	        
	        //Builds 1st rectangle
	        Area area1 = new Area(new Rectangle(0, 0, getWidth(), getHeight()));
	        area1.subtract(new Area(new RoundRectangle2D.Float(10, 10, getWidth() - 20, getHeight() - 20, 20, 20)));
	        
	        //Builds 2nd rectangle
	        Area area2 = new Area(new Rectangle(0, 0, getWidth(), getHeight()));
	        area2.subtract(new Area(new RoundRectangle2D.Float(10, 10, getWidth() - 20, getHeight() - 17, 20, 20)));
	        
	        //Fills area 1s
	        Graphics2D g2d = (Graphics2D) g.create();
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.setColor(new Color(0xBDBDBD));
	        g2d.fill(area1);
	        //Sets stroke thicker than default
	   
	        //Fill area 2s
	        Graphics2D shadow = (Graphics2D)g.create();
	        shadow.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        
	        shadow.setColor(new Color(0xE0E0E0));
	        shadow.fill(area2);
	    }
    }