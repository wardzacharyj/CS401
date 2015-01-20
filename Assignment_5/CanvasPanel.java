import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

/**
 * This class handles all user interraction with the drawing surface(JPanel)
 * from Right-clicks to draging and strectching shapes
 * @Zach
 */


public class CanvasPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public static int CHOICE = ToolBox.MOVER;
	private static ArrayList<MyShape> myShapes = new ArrayList<MyShape>();
	private static ArrayList<Integer> loc = new ArrayList<Integer>();
	private static JPopupMenu editBox;
	int foundLoc = 0;
	int x,y,x1,y1;
	int fx,fy;
	int w,h;
	private MyShape currentShape;
	private MyShape cuedShape;
	private int currentLoc;
	int xMoved, yMoved;
	private static JMenuItem delete;
	private static JMenuItem resize;
	private static JMenuItem cut;
	private static JMenuItem copy;
	private JMenuItem paste;
	boolean isDrawn = true;
	boolean reset = false;
	MyTextBox text;
	
	CanvasPanel(){
		MyMouseListener l = new MyMouseListener();
		addMouseListener(l);
		addMouseMotionListener(l);
		editBox = new JPopupMenu();
		setComponentPopupMenu(editBox);
		add(editBox);
        buildMenu();
	}
	public void updatePop(boolean b){
		if(b)
			setComponentPopupMenu(editBox);
		else
			setComponentPopupMenu(null);
	}
	public static void changeTool(int i){
		CHOICE = i;
		loc = new ArrayList<Integer>();
		if(CHOICE != ToolBox.MOVER){
			copy.setEnabled(false);
			cut.setEnabled(false);
			resize.setEnabled(false);
			delete.setEnabled(false);
		}
		else{
			copy.setEnabled(true);
			cut.setEnabled(true);
			resize.setEnabled(true);
			delete.setEnabled(true);
		}
	}
	private void buildMenu(){
		add(editBox);
		
		delete = new JMenuItem("Delete");
		resize = new JMenuItem("Resize");
		cut = new JMenuItem("Cut");
		copy = new JMenuItem("Copy");
		paste = new JMenuItem("Paste");
		
		delete.setEnabled(false);
		resize.setEnabled(false);
		cut.setEnabled(false);
		copy.setEnabled(false);
		paste.setEnabled(false);
		
		editBox.add(delete);
		editBox.add(resize);
		editBox.add(cut);
		editBox.add(copy);
		editBox.add(paste);
		RightClickMenuListener right = new RightClickMenuListener();
		delete.addActionListener(right);
		resize.addActionListener(right);
		cut.addActionListener(right);
		copy.addActionListener(right);
		paste.addActionListener(right);
		
	}
	//Handles Right Clcik Menu
	class RightClickMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JMenuItem m = (JMenuItem)e.getSource();
			if(m == delete){ //Working Delte
				myShapes.remove(currentLoc);
				repaint();
			}
			if(m == resize){ //Working Resize
				String w = JOptionPane.showInputDialog("Please Enter new Width");
				String h = JOptionPane.showInputDialog("Please Enter new Height");
				try{
					myShapes.get(currentLoc).resize(Integer.parseInt(w), Integer.parseInt(h));
					repaint();
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null,"Invalid Resize Parameters");
				}
			}
			if(m == cut){				
				currentShape = null;
				cuedShape = myShapes.get(currentLoc);
				myShapes.remove(currentLoc);
				repaint();
			}
			if(m == copy){
				cuedShape = null;
				cuedShape = myShapes.get(currentLoc);
			}
			if(m == paste){
				//cuedShape 
				cuedShape = cuedShape.moveShape(xMoved, yMoved);
				myShapes.add(cuedShape);
				repaint();
				cuedShape = null;
				paste.setEnabled(false);
			}
		}
		
	}
	//Finds rightClicked SHape
	public boolean findSahpe(int x, int y){
		for(int i = 0; i< myShapes.size();i++){ //Finds SHape
			if(myShapes.get(i).contains(x, y)){
				currentLoc = i;
				myShapes.get(i).highlight(true);
				repaint();
				return true;
			}
		}
		return false;
	}
	//Handles move/dragging/strectching of shapes
	public class MyMouseListener  implements MouseListener,MouseMotionListener{

		private boolean found;
		public void mouseClicked(MouseEvent e) {
			//Handle Right Click Here
			if (SwingUtilities.isRightMouseButton(e) && CHOICE == ToolBox.MOVER){
				xMoved = e.getX();
				yMoved = e.getY();
				if(cuedShape != null)
					paste.setEnabled(true);
				if(findSahpe(e.getX(), e.getY())){
					copy.setEnabled(true);
					cut.setEnabled(true);
					resize.setEnabled(true);
					delete.setEnabled(true);
				}
				else{
					copy.setEnabled(false);
					cut.setEnabled(false);
					resize.setEnabled(false);
					delete.setEnabled(false);
				}
			}
			
		}
		public void mousePressed(MouseEvent e) {
			if(CHOICE == ToolBox.MOVER){
				int x = e.getX();
				int y = e.getY();

				found = false;
				for(int i = 0; i< myShapes.size();i++){ //Finds Overlapping shapes and locations
					if(myShapes.get(i).contains(x, y)){
						loc.add(i);
						found = true;
						repaint();
					}
				}
				for(int z = 0; z < loc.size();){
					myShapes.get(loc.get(z)).highlight(true);
					foundLoc = loc.get(z);
					loc.remove(z);
					break;
				}
			}
			else{
				x=e.getX();
		        y=e.getY();
		        isDrawn = true;
			}
		}
		public void mouseDragged(MouseEvent e) {
			if(CHOICE == ToolBox.MOVER){
				if(found){
					myShapes.get(foundLoc).move(e.getX(), e.getY());
					loc = new ArrayList<Integer>();
				}
			}
			else{
				x1= e.getX();
		        y1= e.getY();
		        isDrawn = false;
			}
	        repaint();	
		}
		public void mouseReleased(MouseEvent e) {
			if(CHOICE == ToolBox.MOVER){
				if(!SwingUtilities.isRightMouseButton(e)){
					if(found){
						myShapes.get(foundLoc).highlight(false);
						repaint();
					}
				}
			}
			else{
				if(CHOICE == ToolBox.RECTANGLE) 
					myShapes.add(new MyRectangle( fx, fy, w, h ));
				if(CHOICE == ToolBox.OVAL) 
					myShapes.add(new MyOval(fx,fy,w,h));
				if(CHOICE == ToolBox.TREE) 
					myShapes.add(new MyTree(fx, fy, w, h));
				if(CHOICE == ToolBox.SNOWFLAKE)
					myShapes.add(new MySnowflake(fx, fy, w,h));
				if(CHOICE == ToolBox.CABIN)
					myShapes.add(new MyCabin(fx, fy, w, h));
				if(CHOICE == ToolBox.CLOUD)
					myShapes.add(new MyCloud(fx,fy,w,h));
				if(CHOICE == ToolBox.MOUNTAIN)
					myShapes.add(new MyMountain(fx,fy,w,h));
				if(CHOICE == ToolBox.TEXTBOX){
					text = new MyTextBox(fx, fy, w, h);
					String s = JOptionPane.showInputDialog("Enter Text");
					if(s != null){
						text.setText(s);
					}
					myShapes.add(text);
				}
					
					
		    	repaint();
			}
			
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseMoved(MouseEvent e) {}	
	}
	//Flips shapes across Axis by getting absolute values of height and width changes
	private void stretch(){
		int width = x - x1;
    	int height = y - y1;
    	w = Math.abs( width );
    	h = Math.abs( height );
    	//Allows for Y Axis Flip
    	
    	if(width < 0)
    		fx = x;
    	else
    		fx = x1;
    	//Allows for X Axis Flip
    	if(height < 0)
    		fy = y;
    	else
    		fy = y1;
	}
	//Paints Panel according to tool selected and current State of mySHapes Array
	public void paintComponent(Graphics g){
		super.paintComponent(g); // clear the frame surface 
    	
		Graphics2D g2 = (Graphics2D) g;
    	
    	g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    	g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    	if(!reset){
		    for(MyShape e: myShapes){
	    		e.draw(g2);
	    	}
	    	if(CHOICE != ToolBox.MOVER){
	    		stretch();
		    	MyShape temp = null;
	    		if(CHOICE == ToolBox.RECTANGLE)
		    		temp = new MyRectangle(fx, fy, w, h);
		    	if(CHOICE == ToolBox.OVAL)
		    		temp = new MyOval(fx, fy, w, h);
		    	if(CHOICE == ToolBox.TREE)
		    		temp =  new MyTree(fx, fy, w, h);
		    	if(CHOICE == ToolBox.SNOWFLAKE)
		    		temp = new MySnowflake(fx, fy, w,h);
		    	if(CHOICE == ToolBox.CABIN)
		    		temp = new MyCabin(fx, fy, w, h);
		    	if(CHOICE == ToolBox.CLOUD)
		    		temp = new MyCloud(fx, fy, w, h);
		    	if(CHOICE == ToolBox.MOUNTAIN)
		    		temp = new MyMountain(fx, fy, w, h);
		    	if(CHOICE == ToolBox.TEXTBOX){
		    		if(text != null)
		    			temp = text;
		    		else
		    			temp = new MyTextBox(fx, fy, w, h);
		    		
		    		text = null;
		    	}
		    	
		    	if(temp != null)
		    		temp.draw(g2);
	    	}
    	}
    	else
    		reset = false;
	}
	//Checks if JPanel is Blank
	public boolean isBlank(){
		if(myShapes.size() < 1)
			return false;
		else
			return true;
	}
	//resets entire Panel
	public void clear() {
		myShapes = new ArrayList<MyShape>();
		loc = new ArrayList<Integer>();
		reset = true;
		validate();
		repaint();
	}
	//called when myShapes needs to be formatted and saved to file
	public StringBuilder save(){
		return ShapeFileHandler.saveNewCanvas(myShapes);
	}
	//Loads a txt file by building new nySHape array from old coor
	public void load(String fileName){
		myShapes = ShapeFileHandler.openCanvas(fileName);
		loc = new ArrayList<Integer>();
		validate();
		repaint();
	}
	//Saves JPanel to PNG file format
	public void saveAsPNG(){
		String fileName;
		BufferedImage bi = new BufferedImage(this.getSize().width, this.getSize().height, BufferedImage.TYPE_INT_ARGB); 
		Graphics g = bi.createGraphics();
		this.paint(g);  //this == JComponent
		g.dispose();
		
		JFileChooser chooser = new JFileChooser();
		File workingDirectory = new File(System.getProperty("user.dir"));
		chooser.setCurrentDirectory(workingDirectory);
	    int retrival = chooser.showSaveDialog(null);
    
	    if (retrival == JFileChooser.APPROVE_OPTION) {
	    	try {
	            fileName = chooser.getSelectedFile() + "";
	            try{ImageIO.write(bi,"png",new File(fileName));}catch (Exception e) {}
	    	} catch (Exception ex) {
	    		ex.printStackTrace();
        	}
    	}
	    
		
	}
}

