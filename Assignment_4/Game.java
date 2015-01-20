import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Class: CS-0401
 * Game.java
 * Purpose: This class manages the HangPlayer and Wordserver class
 * @author Zach Ward
 * @version 1.0
 */
public class Game {
	private String name;
	private String fileName;
	private static HangPlayer hp;
	private static WordServer ws;
	private static ArrayList<HangPlayer> pList;
	
	/**
	 * This is the main object of the program
	 * it manages the word server and hangplayer class
	 * so they can be easily accessed together
	 * @param l Player List
	 * @param n Player's Name
	 * @param f Player's Filename
	 */
	public Game(ArrayList<HangPlayer> l, String n, String f){
		pList = l;
		name = n;
		fileName = f;
		setHangPlayer();
		ws = new WordServer();
	}
	private String getName(){
		return name;
	}
	/**
	 * This Method creates the WordServer Object used to randomly choose
	 * words to pose to the user during the hangman game
	 * @param fileName	The name of the file that holds the database of words
	 * @throws FileNotFoundException Thrown only when the default words.txt is not found
	 */
	public WordServer getWordServer(){
		
		System.out.println("Load Words");
		try {
			ws.loadWords(new Scanner(new File(fileName)));
		} catch (FileNotFoundException e) {
			System.out.println("Error in file structure");
		}
		return ws;
	}
	/**
	 * Initializes the HangPlayer object according to whether or not it exists
	 * in the Players.txt file
	 */
	public void setHangPlayer(){
		//Checks the name in the list of hp objects
		try{
				int index = HangPlayer.checkPlayer(pList,getName());
				if(index == -1)
					hp = new HangPlayer(getName(), 0, 0);
				else{
					//Removes the old hp object from the list so it can be updated and rewritten
					hp = pList.get(index);
					JOptionPane.showMessageDialog(null, hp, "Welcome Back :)", JOptionPane.INFORMATION_MESSAGE);
					pList.remove(index);	
				}
			}catch(Exception e){
				System.out.println("There was an error accessing the Player.txt file");
				e.printStackTrace();
			}
		// -1 index means the name could not be found in the list	
	}
	/**
	 * @return hp the HangPlayer object
	 */
	public HangPlayer getPlayer(){
		return hp;
	}
}
