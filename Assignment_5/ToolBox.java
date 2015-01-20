import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;


public class ToolBox extends CardPanel{
	private static final long serialVersionUID = 1L;
	public static final int MOVER = 1;
	public static final int RECTANGLE = 2;
	public static final int OVAL = 3;
	public static final int TREE = 4;
	public static final int SNOWFLAKE = 5;
	public static final int CABIN = 6;
	public static final int CLOUD = 7;
	public static final int MOUNTAIN = 8;
	public static final int TEXTBOX = 9;
	
	
	private JToggleButton mover,rectangle, oval,tree,snowflake,cabin,cloud,mountain,textbox;
	JPanel holder = new JPanel();
	//Container holder = new Container();
	ToolBox(){
		setLayout(new BorderLayout());
		//Initialize ToggleButtns
		holder.setLayout(new BoxLayout(holder,BoxLayout.PAGE_AXIS));
		mover = new JToggleButton();
		rectangle = new JToggleButton();
		oval = new JToggleButton();
		tree = new JToggleButton();
		snowflake = new JToggleButton();
		cabin = new JToggleButton();
		cloud = new JToggleButton();
		mountain = new JToggleButton();
		textbox = new JToggleButton();
		
		//Initialize Holder
		holder.add(forceSize(mover));
		holder.add(forceSize(rectangle));
		holder.add(forceSize(oval));
		holder.add(forceSize(tree));
		holder.add(forceSize(snowflake));
		holder.add(forceSize(cabin));
		holder.add(forceSize(cloud));
		holder.add(forceSize(mountain));
		holder.add(forceSize(textbox));
		
		holder.setBorder(new EmptyBorder(20, 20, 0, 24));
		
		holder.setOpaque(false);
		//holder.setOpaque(false);
		//holder.setAlignmentX(Component.CENTER_ALIGNMENT);
		setIcons();
		add(holder,BorderLayout.CENTER);

		
	}
	//set pngs icon to each buttons
	public void setIcons(){
		mover.setIcon(getIcon("move_icon.png"));
		rectangle.setIcon(getIcon("rectangle.png"));
		oval.setIcon(getIcon("circle.png"));
		tree.setIcon(getIcon("tree_icon.png"));
		snowflake.setIcon(getIcon("snowflake_icon.png"));
		cabin.setIcon(getIcon("house_icon.png"));
		cloud.setIcon(getIcon("cloud_icon.png"));
		mountain.setIcon(getIcon("mountain_icon.png"));
		textbox.setIcon(getIcon("textbox_icon.png"));
	}
	//returns scales png
	public ImageIcon getIcon(String s){
		ImageIcon icon = new ImageIcon(s);
		Image img = icon.getImage() ;  
	    Image newimg = img.getScaledInstance( 20, 20,  java.awt.Image.SCALE_AREA_AVERAGING ) ; 
		return new ImageIcon(newimg);
	}
	//forces static button size
	public JToggleButton forceSize(JToggleButton a){
		a.setMaximumSize(new Dimension(40,40));
		a.setMinimumSize(new Dimension(40,40));
		a.setPreferredSize(new Dimension(40,40));
		return a;
	}
	// Toggle Getters
	
	public JToggleButton getMoverButton(){
		return mover;
	}
	public JToggleButton getRecButton(){
		return rectangle;
	}
	public JToggleButton getOvalButton(){
		return oval;
	}
	public JToggleButton getTreeButton(){
		return tree;
	}
	public JToggleButton getSnowflakeButton(){
		return snowflake;
	}
	public JToggleButton getCabinButton(){
		return cabin;
	}
	public JToggleButton getCloudButton(){
		return cloud;
	}
	public JToggleButton getMountainButton(){
		return mountain;
	}
	public JToggleButton getTextBoxButton(){
		return textbox;
	}
	
}
