import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * This class creates the Color Panel to the right. Depending on the
 * selected Color parts or entire Shapes will be drawn with that color.
 * @author Zach
 *
 */
public class ColorPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JPanel panel_1 = new JPanel(new GridLayout(18,5));
	private JPanel panel_2 = new JPanel();
	//Build arrays for hex colors
	private static Color currentColor = Color.black;
	private static JButton clearButton = new JButton("Clear");
	private JPanel lastColor = new JPanel();
	private JButton[] red = new JButton[10];
	private JButton[] orange = new JButton[10];
	private JButton[] yellow = new JButton[10];
	private JButton[] green = new JButton[10];
	private JButton[] blue = new JButton[10];
	private JButton[] indigo = new JButton[10];
	private JButton[] violet = new JButton[10];
	private JButton[] brown = new JButton[10];
	private JButton[] gray = new JButton[10];
	
	ColorPanel(){
		// R G B I V
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(new Color(0xE0E0E0));
		panel_1.setBackground(new Color(0xE0E0E0));
		panel_1.setBorder(new EmptyBorder(10,10,20,10));
		panel_2.setBackground(new Color(0xE0E0E0));
		buildPallete();
		populatePallete();
		lastColor();
		add(panel_1);
		add(panel_2);
		JPanel panel_3 = new JPanel();
		panel_3.add(clearButton);
		panel_3.setBackground(new Color(0xE0E0E0));
		add(panel_3);
		panel_3.setBorder(new EmptyBorder(0, 20, 20, 20));
	}
	public void updateColor(Color c){
		currentColor = c;
		lastColor.setBackground(c);	
	}
	public static Color getCurrentColor(){
		return currentColor;
	}
	public static JButton getClearButton(){
		return clearButton;
	}
	private void lastColor() {
		lastColor.setBorder(new EmptyBorder(10,10,20,10));
		Dimension d = new Dimension(50,50);
		lastColor.setPreferredSize(d);
		lastColor.setMaximumSize(d);
		lastColor.setMinimumSize(d);
		lastColor.setBackground(Color.BLACK);
		panel_2.add(new JLabel("Selected"));
		panel_2.add(lastColor);
		
	}
	public JButton forceSize(JButton a){
		a.setMaximumSize(new Dimension(20,20));
		a.setMinimumSize(new Dimension(20,20));
		a.setPreferredSize(new Dimension(20,20));
		return a;
	}
	/**
	 * Overides some default mac restrictions on changing button color to force color
	 */
	public void buildPallete(){
		for(int i = 0; i < red.length; i++){
			red[i] = new JButton(); red[i].setOpaque(true);red[i].setBorderPainted(false); forceSize(red[i]); red[i].addActionListener(new MyColorListener());
			orange[i] = new JButton(); orange[i].setOpaque(true); orange[i].setBorderPainted(false); forceSize(orange[i]); orange[i].addActionListener(new MyColorListener());
			yellow[i] = new JButton(); yellow[i].setOpaque(true); yellow[i].setBorderPainted(false);forceSize(yellow[i]); yellow[i].addActionListener(new MyColorListener());
			green[i] = new JButton(); green[i].setOpaque(true); green[i].setBorderPainted(false); forceSize(green[i]); green[i].addActionListener(new MyColorListener());
			blue[i] = new JButton(); blue[i].setOpaque(true); blue[i].setBorderPainted(false); forceSize(blue[i]); blue[i].addActionListener(new MyColorListener());
			indigo[i] = new JButton(); indigo[i].setOpaque(true); indigo[i].setBorderPainted(false); forceSize(indigo[i]); indigo[i].addActionListener(new MyColorListener());
			violet[i] = new JButton(); violet[i].setOpaque(true); violet[i].setBorderPainted(false); forceSize(violet[i]); violet[i].addActionListener(new MyColorListener());
			brown[i] = new JButton(); brown[i].setOpaque(true); brown[i].setBorderPainted(false);forceSize(brown[i]); brown[i].addActionListener(new MyColorListener());
			gray[i] = new JButton(); gray[i].setOpaque(true); gray[i].setBorderPainted(false); forceSize(gray[i]); gray[i].addActionListener(new MyColorListener());
		}
		red[0].setBackground(new Color(0xB71C1C));
		red[1].setBackground(new Color(0xC62828));
		red[2].setBackground(new Color(0xD32F2F));
		red[3].setBackground(new Color(0xE53935));
		red[4].setBackground(new Color(0xF44336));
		red[5].setBackground(new Color(0xEF5350));
		red[6].setBackground(new Color(0xE57373));
		red[7].setBackground(new Color(0xEF9A9A));
		red[8].setBackground(new Color(0xFFCDD2));
		red[9].setBackground(new Color(0xFFEBEE));
		
		orange[0].setBackground(new Color(0xE65100));
		orange[1].setBackground(new Color(0xEF6C00));
		orange[2].setBackground(new Color(0xF57C00));
		orange[3].setBackground(new Color(0xFB8C00));
		orange[4].setBackground(new Color(0xFF9800));
		orange[5].setBackground(new Color(0xFFA726));
		orange[6].setBackground(new Color(0xFFB74D));
		orange[7].setBackground(new Color(0xFFCC80));
		orange[8].setBackground(new Color(0xFFE0B2));
		orange[9].setBackground(new Color(0xFFF3E0));
		
		yellow[0].setBackground(new Color(0xF57F17));
		yellow[1].setBackground(new Color(0xF9A825)); 
		yellow[2].setBackground(new Color(0xFBC02D)); 
		yellow[3].setBackground(new Color(0xFDD835));
		yellow[4].setBackground(new Color(0xFFEB3B));
		yellow[5].setBackground(new Color(0xFFEE58)); 
		yellow[6].setBackground(new Color(0xFFF176));
		yellow[7].setBackground(new Color(0xFFF59D)); 
		yellow[8].setBackground(new Color(0xFFF9C4));
		yellow[9].setBackground(new Color(0xFFFDE7));
		
		green[0].setBackground(new Color(0x1B5E20));
		green[1].setBackground(new Color(0x2E7D32));
		green[2].setBackground(new Color(0x388E3C));
		green[3].setBackground(new Color(0x43A047));
		green[4].setBackground(new Color(0x4CAF50));
		green[5].setBackground(new Color(0x66BB6A));
		green[6].setBackground(new Color(0x81C784));
		green[7].setBackground(new Color(0xA5D6A7));
		green[8].setBackground(new Color(0xC8E6C9));
		green[9].setBackground(new Color(0xE8F5E9));
		
		blue[0].setBackground(new Color(0x0D47A1));
		blue[1].setBackground(new Color(0x1565C0));
		blue[2].setBackground(new Color(0x1976D2));
		blue[3].setBackground(new Color(0x1E88E5));
		blue[4].setBackground(new Color(0x2196F3));
		blue[5].setBackground(new Color(0x42A5F5));
		blue[6].setBackground(new Color(0x64B5F6));
		blue[7].setBackground(new Color(0x90CAF9));
		blue[8].setBackground(new Color(0xBBDEFB));
		blue[9].setBackground(new Color(0xE3F2FD));
		
		
		indigo[0].setBackground(new Color(0x1A237E));
		indigo[1].setBackground(new Color(0x283593));
		indigo[2].setBackground(new Color(0x303F9F));
		indigo[3].setBackground(new Color(0x3949AB));
		indigo[4].setBackground(new Color(0x3F51B5));
		indigo[5].setBackground(new Color(0x5C6BC0));
		indigo[6].setBackground(new Color(0x7986CB));
		indigo[7].setBackground(new Color(0x9FA8DA));
		indigo[8].setBackground(new Color(0xC5CAE9));
		indigo[9].setBackground(new Color(0xE8EAF6));
		
		violet[0].setBackground(new Color(0x4A148C));
		violet[1].setBackground(new Color(0x6A1B9A));
		violet[2].setBackground(new Color(0x7B1FA2));
		violet[3].setBackground(new Color(0x8E24AA));
		violet[4].setBackground(new Color(0x9C27B0));
		violet[5].setBackground(new Color(0xAB47BC));
		violet[6].setBackground(new Color(0xBA68C8));
		violet[7].setBackground(new Color(0xCE93D8));
		violet[8].setBackground(new Color(0xE1BEE7));
		violet[9].setBackground(new Color(0xF3E5F5));
		
		brown[0].setBackground(new Color(0x3E2723));
		brown[1].setBackground(new Color(0x4E342E));
		brown[2].setBackground(new Color(0x5D4037));
		brown[3].setBackground(new Color(0x6D4C41));
		brown[4].setBackground(new Color(0x795548));
		brown[5].setBackground(new Color(0x8D6E63));
		brown[6].setBackground(new Color(0xA1887F));
		brown[7].setBackground(new Color(0xBCAAA4));
		brown[8].setBackground(new Color(0xD7CCC8));
		brown[9].setBackground(new Color(0xEFEBE9));
		
		gray[0].setBackground(new Color(0x212121));
		gray[1].setBackground(new Color(0x424242));
		gray[2].setBackground(new Color(0x616161));
		gray[3].setBackground(new Color(0x757575));
		gray[4].setBackground(new Color(0x9E9E9E));
		gray[5].setBackground(new Color(0xBDBDBD));
		gray[6].setBackground(new Color(0xE0E0E0));
		gray[7].setBackground(new Color(0xEEEEEE));
		gray[8].setBackground(new Color(0xF5F5F5));
		gray[9].setBackground(new Color(0xFAFAFA));	
	}
	//Populates the ColorPanel
	public void populatePallete(){
		for(JButton a : red){
			panel_1.add(a);
		}
		for(JButton b : orange){
			panel_1.add(b);
		}
		for(JButton c : yellow){
			panel_1.add(c);
		}
		for(JButton d : green){
			panel_1.add(d);
		}
		for(JButton e : blue){
			panel_1.add(e);
		}
		for(JButton f : indigo){
			panel_1.add(f);
		}
		for(JButton g : violet){
			panel_1.add(g);
		}
		for(JButton h : brown){
			panel_1.add(h);
		}
		for(JButton i : gray){
			panel_1.add(i);
		}
	}
	/**
	 * Captures Color Switches
	 * @author Zach
	 *
	 */
	private class MyColorListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			updateColor(button.getBackground());
		}
		
	}
}