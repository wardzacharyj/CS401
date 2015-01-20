import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Class: CS-0401
 * BottomPanel.java
 * Purpose: Builds the bottom Panel
 * 			and positions the three buttons accordingly
 * @author Zach Ward
 * @version 1.0
 */
public class BottomPanel extends JPanel {
	
	private JButton newPlayer,endGame, newWord;
	/**
	 * Constructor sets the Buttons for the bottom panel
	 * and adds them accordingly
	 */
	public BottomPanel(){
		newPlayer = new JButton("New Player");
		newWord = new JButton("Skip Word");
		endGame = new JButton("End Game");
		setLayout(new FlowLayout());
		add(endGame);
		add(newWord);
		add(newPlayer);
	}
	/**
	 * @return endGame The button to exit
	 */
	public JButton getExitButton(){
		return endGame;
	}
	/**
	 * @return newWord The button to skip the current word
	 */
	public JButton getNewWord(){
		return newWord;
	}
	/**
	 * @return newPlayer The button to exit and let another user play
	 */
	public JButton getNewPlayer(){
		return newPlayer;
	}
}
