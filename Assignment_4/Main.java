import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Class: CS-0401
 * Main.java
 * Purpose: Hangman!
 * @author Zach Ward
 * @version 1.0
 */

public class Main {
	private JFrame mainWindow;
	
	static Color dark = new Color(0x3b50ce);
	static Color gray = new Color(0xE0E0E0);
	static Color light = new Color(0x5677fc);
	
	private static CardPanel leftPanel;
	private TopPanel topPanel;
	private CenterPanel centerPanel;
	private BottomPanel bottomPanel;
	private static WordServer ws;
	private Container theContainer;
	private static Game game;
	
	private StringBuilder sw,w;
	private int missCount = 6;
	private static ArrayList<HangPlayer> pList;
	
	/**
	 * This constructor builds the main Jframe from four main
	 * panels. The components are handled by these JPanel classes
	 * named TopPanel, CardPanel, CenterPanel, BottomPanel
	 */
	public Main(){
		mainWindow = new JFrame("Hangman");
		ws = game.getWordServer();
		//Sets initial words
		w = new StringBuilder(ws.getNextWord().toUpperCase());
		sw = new StringBuilder(w.toString().replaceAll("[^0-9.]", "*"));
		
		//Builds top panel
		topPanel = new TopPanel();
		topPanel.setGameStatus("In Progress");
		topPanel.setGameInfo("No Letters Guessed Yet");
		
		//Builds center panel
		centerPanel = new CenterPanel();
		centerPanel.updateWord(sw.toString());
		centerPanel.getEnterButton().addActionListener(new MyListener());
		
		//Builds bottom panel
		bottomPanel = new BottomPanel();
		bottomPanel.getExitButton().addActionListener(new MyListener());
		bottomPanel.getNewPlayer().addActionListener(new MyListener());
		bottomPanel.getNewWord().addActionListener(new MyListener());
		leftPanel = new CardPanel(300,400, true, 0);
		
		//Sets orientation of panels
		theContainer = mainWindow.getContentPane();
		theContainer.add(topPanel,BorderLayout.NORTH);
		theContainer.add(leftPanel, BorderLayout.WEST);
		theContainer.add(centerPanel, BorderLayout.CENTER);
		theContainer.add(bottomPanel, BorderLayout.SOUTH);
		
		//Centers Window by retrieving screen dimensions then dividing by 2
		mainWindow.setPreferredSize(new Dimension(950,700));
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) (dimension.getWidth() - mainWindow.getWidth() / 2);
	    int y = (int) (dimension.getHeight() - mainWindow.getHeight() / 2);
	    mainWindow.getRootPane().setDefaultButton(centerPanel.getEnterButton());
	    mainWindow.setLocation(x, y);
	    
	    
		mainWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		//Overides the default exit button to confirm that the user wants to exit.
		//It allows to us to save the data before the program terminates as well
		mainWindow.addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        if (JOptionPane.showConfirmDialog(mainWindow, "Are you sure to exit?", "Exit", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		        	pList.add(game.getPlayer());
					HangPlayer.writePlayerList(pList);
		            System.exit(0);
		        }
		    }
		});
		mainWindow.pack();
		mainWindow.setMinimumSize(new Dimension(800,500));
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setVisible(true);
	}
	public static void main(String[] args) throws InterruptedException{
		pList = HangPlayer.readPlayerList("Players.txt");
		String s =  getFileName();
		String n = getName();
		game = new Game(pList,n,s);
		new Main();
	}
	/**
	 * This is a simple method to 
	 * retrieve the word File, It displays the
	 * JFileChooser to the user and returns the location/string
	 * of the file
	 * @return
	 */
	private static String getFileName(){
    	JFileChooser fChoose =new JFileChooser();
		fChoose.setDialogTitle("Please Choose a Word Text File");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
		fChoose.setFileFilter(filter);
		File workingDirectory = new File(System.getProperty("user.dir"));
		fChoose.setCurrentDirectory(workingDirectory);
		fChoose.validate();
		int returnVal = fChoose.showOpenDialog(null);
		 if(returnVal == JFileChooser.APPROVE_OPTION) 
		       return fChoose.getSelectedFile().getName(); 		
		 if(returnVal == JFileChooser.CANCEL_OPTION){
			 System.exit(0);
		 }
		 return null;
	}
	/**
	 * This is a simple method to get the user's name
	 * @return name this is the users name
	 */
	private static String getName(){
		String name;
		do{
			name = JOptionPane.showInputDialog(null,"Please Enter your name","Player Name Entry",JOptionPane.QUESTION_MESSAGE);
			if(name == null || name.trim().length() < 1)
				JOptionPane.showMessageDialog(null,"Please enter a name");
		}while(name == null || name.trim().length() < 1);
		
		return name;
	}
	/**
	 * This metod handles the user input and
	 * updates The JLabels accordingly depending whether
	 * the letter or word is found in the secret word
	 * @param letter
	 */
	private void checkInput(String letter){
		if(w.toString().contains(letter) && (missCount >= 0)){
			for(int i = 0; i < w.length(); i++)
				if(w.charAt(i) == letter.charAt(0))
					sw.setCharAt(i, letter.charAt(0));
			centerPanel.updateWord(sw.toString());
			
			topPanel.setGameInfo(letter + " was found");
			if(sw.toString().equalsIgnoreCase(w.toString()))
				grade(true);
		}
		else{
			if(missCount == 0)
				grade(false);
			else{
				//In Progress
				missCount--;
				leftPanel.setMiss(6 - missCount);
				leftPanel.repaint();
				centerPanel.getGuessLabel().setText(centerPanel.getGuessLabel().getText()+letter+" ");
				if(!(missCount== 0))
					topPanel.setGameInfo(letter + " was not found " + (missCount + 1) + " chances left");
				else
					topPanel.setGameInfo(letter + " was not found. Last try!");
			}
		}
	}
	/**
	 * This class handles wins and losses
	 * It also resets the hangman among other
	 * default settings to prepare for the next word
	 * @param test
	 */
	private void grade(boolean test) {
		topPanel.setGameInfo("Waiting For Input");
		centerPanel.clearGuessField();
		if(test){
			JOptionPane.showMessageDialog(null, "You Win, The Word Was " + w.toString().toLowerCase());
			game.getPlayer().updateWins(1);
		}
		else{
			leftPanel.setMiss(7);
			leftPanel.repaint();
			JOptionPane.showMessageDialog(null, "You Lose, The Word Was " + w.toString().toLowerCase());
			game.getPlayer().updateLoss(1);
		}
		leftPanel.setMiss(0);
		missCount = 6;
		leftPanel.repaint();
		w = new StringBuilder(ws.getNextWord().toUpperCase());
		centerPanel.resetCombo();
		if(!w.toString().equals("")){
			sw = new StringBuilder(w.toString().replaceAll("[^0-9.]", "*"));
			centerPanel.updateWord(sw.toString());
		}
		else{
			pList.add(game.getPlayer());
			HangPlayer.writePlayerList(pList);
			if (JOptionPane.showConfirmDialog(mainWindow, "There are no more words left. Would another player like to play?", "Out of Words", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		        	bottomPanel.getNewPlayer().doClick();  
		        }
			else{
				System.exit(0);
			}
		}
	}
	/**
	 * This subclass handles all the event objects generated by the
	 *  buttons on the main JFrame.
	 * @author Zach
	 *
	 */
	private class MyListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton c = (JButton) e.getSource();
			//Enter Button
			if(c == centerPanel.getEnterButton()){
				String letter = centerPanel.getLetter().toUpperCase();	
				centerPanel.clearUserField();
				if(letter.length() > 1){
					if(letter.equalsIgnoreCase(w.toString()))
						grade(true);
					else
						grade(false);
				}
				else{
					if((centerPanel.getGuessLabel().getText() + sw.toString()).contains(letter))
						JOptionPane.showMessageDialog(null, "You have already entered " + letter);
					else
						checkInput(letter);
				}
			}
			//Exit Button
			if(c == bottomPanel.getExitButton()){
				int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
				if(i == JOptionPane.YES_OPTION){
					//Adds Player back to the list and saves the new data
					pList.add(game.getPlayer());
					HangPlayer.writePlayerList(pList);
					System.exit(0);
				}
			}
			//New Word
			if(c == bottomPanel.getNewWord()){
				int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to skip this word? It will count as a loss.");
				if(i == JOptionPane.YES_OPTION){
					grade(false);
				}
			}
			//New Player
			if(c == bottomPanel.getNewPlayer()){
				pList.add(game.getPlayer());
				HangPlayer.writePlayerList(pList);
				String s =  getFileName();
				String n = getName();
				game = new Game(pList, n,s);
				ws = game.getWordServer();
				//Sets initial words
				w = new StringBuilder(ws.getNextWord().toUpperCase());
				sw = new StringBuilder(w.toString().replaceAll("[^0-9.]", "*"));
				centerPanel.updateWord(sw.toString());
			}
		}
	}
}
