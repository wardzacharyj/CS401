import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class: CS-0401
 * TopPanel.java
 * Purpose: Builds the Top Panel and positions its
 * 			Components
 * @author Zach Ward
 * @version 1.0
 */
public class TopPanel extends JPanel {
	private JPanel p1,p2;
	private JLabel lbl_Status,lbl_Info;
	private static Color dark = new Color(0x3b50ce);
	//private static Color med = new Color(0xE0E0E0);
	private static Color light = new Color(0x5677fc);
	private JPanel subBar;
	/**
	 * Constructor initializes panels, layout and background
	 */
	TopPanel(){
		setLayout(new BorderLayout());
		setBackground(dark);
		p1 = new JPanel();
		p2 = new JPanel();
		init_Panel1();
		init_Panel2();
	}
	/**
	 * initializes init_Panel1
	 * adds two labels to p1 
	 */
	private void init_Panel1(){
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		
		//Place Holder text added
		p1.add(lbl_Status = new JLabel("Game Status Here"));
		lbl_Status.setAlignmentX(Component.RIGHT_ALIGNMENT);
		p1.add(lbl_Info = new JLabel("Game Info Here"));
		lbl_Info.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lbl_Status.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		lbl_Status.setFont(new Font("Arial", Font.PLAIN,18));
		lbl_Status.setForeground(Color.WHITE);
		
		lbl_Info.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		lbl_Info.setFont(new Font("Arial", Font.PLAIN,18));
		lbl_Info.setForeground(Color.WHITE);
		p1.setBackground(dark);
		
		add(getPanel_1(), BorderLayout.EAST);
	}
	/**
	 * initializes init_Panel2
	 * adds title and colors
	 */
	private void init_Panel2(){
		p2.setLayout(new BorderLayout());
		p2.setBackground(dark);
		JLabel title = new JLabel("Hangman");
		title.setForeground(Color.WHITE);
		title.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		title.setFont(new Font("Arial", Font.PLAIN, 76));
		p2.add(title, BorderLayout.WEST);
		JLabel emptyBar = new JLabel(" ");
		emptyBar.setFont(new Font("Arial", Font.PLAIN, 18));
		subBar = new JPanel();
		subBar.add(emptyBar);
		subBar.setBackground(light);
		p2.add(subBar,BorderLayout.SOUTH);
		
		add(getPanel_2(),BorderLayout.SOUTH);
	}
	/**
	 * @return p1 JPanel that holds the Game info & Game status
	 */
	public JPanel getPanel_1(){
		return p1;
	}
	/**
	 * @return p2 JPanel that holds the Main Title label
	 */
	public JPanel getPanel_2(){
		return p2;
	}
	/**
	 * @param text the string used to update lbl_info
	 */
	public void setGameInfo(String text){
		lbl_Info.setText(text);
	}
	/**
	 * @param text the string used to update lbl_Status
	 */
	public void setGameStatus(String text){
		lbl_Status.setText(text);
	}
	/**
	 * Method that would allow the user to change the colors of the program easily
	 * Is not used
	 * @param d
	 * @param l
	 */
	public void changeColors(Color d, Color l){
		setBackground(d);
		p1.setBackground(d);
		p2.setBackground(d);
		subBar.setBackground(l);
		repaint();	
	}
}
