import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Class: CS-0401
 * CardPanel.java
 * Purpose: Hangles the Graphics of the program
 * 			Draws the hangman and rounded rectangles
 * @author Zach Ward
 * @version 1.0
 */
public class CardPanel extends JPanel {
	private static final long serialVersionUID = 1L;
		private int x1;
		private int y1;
		private int miss;
		private boolean flag;
		ArrayList<Shape> man;
		/**
		 * Initializes the CardPanel
		 * @param x horizontal x
		 * @param y vertical y
		 * @param f flag to draw stickman
		 * @param m number of parts to draw
		 */
		public CardPanel(int x, int y, boolean f,int m){
			x1 = x;
			y1 = y;
			flag = f;
			miss = m;
		}
		/**
		 * This paints a rounded rectangle with a drop shadow
		 * Depending on the flag's status it will draw the stickman 
		 * according to the current number of misses
		 */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);
            setArray();
            
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
            g2d.setStroke(new BasicStroke(5.0f));
            if(flag == true){
            	for(int i = 0; i < miss; i++)
            		g2d.draw(man.get(i));
            }
            //Fill area 2s
            Graphics2D shadow = (Graphics2D)g.create();
            shadow.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            shadow.setColor(new Color(0xE0E0E0));
            shadow.fill(area2);
        }
        public Dimension getPreferredSize() {
            return new Dimension(x1, y1);
        }
        /**
         * Initializes man array in order
         */
        public void setArray(){
        	man = new ArrayList<Shape>();
        	man.add(new Ellipse2D.Double(100, 40, 100, 100));   //g2d.drawOval(100,40,100,100); 1
			man.add(new Line2D.Double(150,270, 150, 140));		//g2d.drawLine(150,270,150,140); 2	
			man.add(new Line2D.Double(150,160,75,220));			//g2d.drawLine(150,160,75,220);   3      		
			man.add(new Line2D.Double(150, 160, 225, 220));		//g2d.drawLine(150, 160, 225, 220); 4
			man.add(new Line2D.Double(150,270,75,350));			//g2d.drawLine(150,270,75,350);	5
			man.add(new Line2D.Double(150, 270, 225, 350));    //g2d.drawLine(150, 270, 225, 350); 6
			man.add(new Ellipse2D.Double(135, 90, 30, 40));	//7
        }
        /**
         * Allows one to update the number of misses
         * @param i new value of miss
         */
		public void setMiss(int i) {
			miss = i;
		}
    }