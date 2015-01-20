import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
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
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor initializes panels, layout and background
	 */
	TopPanel(){
		setLayout(new BorderLayout());
		JLabel title = new JLabel("Card Maker");
		title.setForeground(Color.WHITE);
		title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		title.setFont(new Font("Arial", Font.PLAIN, 76));
		add(title, BorderLayout.WEST);
		setBackground(new Color(0x5677fc));
	}
	
}
