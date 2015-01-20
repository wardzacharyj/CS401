import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Class: CS-0401
 * HangPlayer.java
 * Purpose: Creates a HangPlayer object that includes
 * a users name and win loss record as well as static methods to write
 * or read from files
 *
 * @author Zach Ward
 * @version 1.0
 */

public class HangPlayer {
	private String name;
	private int num_Win;
	private int num_Loss;
	
	HangPlayer(String n, int w, int l){
		name = n;
		num_Win = w;
		num_Loss = l;
	}
	/**
	 * @return name HangPlayer's name
	 */
	public String getName(){
		return name;
	}
	/**
	 * @return wins HangPlayer's win
	 */
	public int getWins(){
		return num_Win;
	}
	/**
	 * updates HangPlayers object win counts
	 * @param i used to update
	 */
	public void updateWins(int i){
		num_Win = getWins() + i;
	}
	/**
	 * @return num_Loss total num_Loss
	 */
	public int getLosses(){
		return num_Loss;
	}
	/**
	 * updates HangPlayers object loss counts
	 * @param i used to update
	 */
	public void updateLoss(int i){
		num_Loss = getLosses() + i;
	}
	/**
	 *  This method prints the total wins and losses from all
	 *  players
	 * @param l list of HangPlayerObjects
	 */
	public static void displayListResults(ArrayList<HangPlayer> pList){
		int wins = 0;
		int loss = 0;
		for(HangPlayer a : pList){
			wins += a .getWins();
			loss += a.getLosses();
		}
		StringBuilder sb = new StringBuilder();
		sb.append("\n\t\t--------All Players--------\n");
		sb.append("\t\t  Wins\t\t\t"+wins+"\n");
		sb.append("\t\t  Losses\t\t"+loss+"\n");
		sb.append("\t\t-----------------------------");
		System.out.println(sb);
		
	}
	/**
	 * This method searches a list of HangPlayer objects
	 * for a name. If its found it will return the index at which
	 * it was found.
	 * @param p ArrayList of HangPlayer Objects
	 * @param p_Name Name being searched for
	 * @return the index at which it was found or -1 
	 * 			if it was not found
	 * @throws FileNotFoundException 
	 */
	public static int checkPlayer(ArrayList<HangPlayer> a, String p_Name){
		for(int i = 0; i < a.size(); i++){
			if(a.get(i).getName().equalsIgnoreCase(p_Name)){
				return i;
			}
		}		
		return -1;
	}
	/**
	 * This static method creates an ArrayList of HangPlayer Objects
	 * @param fileName where the list is generated from
	 * @return list the new list created from the file
	 * @throws FileNotFoundException
	 */
	public static ArrayList<HangPlayer> readPlayerList(String fileName){
		ArrayList<HangPlayer> list = new ArrayList<HangPlayer>();
		Scanner sc;
		try {
			sc = new Scanner(new File(fileName));
			while(sc.hasNextLine()){ 
				list.add(new HangPlayer(sc.nextLine(),Integer.parseInt(sc.nextLine()),Integer.parseInt(sc.nextLine())));
			}
			sc.close();
			return list;
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * This static method writes an ArrayList of HangPlayer objects
	 * to Players.txt
	 * @param a the list used to write to the file
	 * @throws FileNotFoundException
	 */
	public static void writePlayerList(ArrayList<HangPlayer> a){	
		try{
			PrintWriter pw = new PrintWriter(new File("Players.txt"));
			for(HangPlayer x : a){
				pw.println(x.toString2());
			}
			pw.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "There was an error locating the Player.txt file");
		}
		
	}
	/**
	 * The toString() method used for writing to the file
	 * rather that being used for being displayed to the user
	 * @return a formated String of all the information in a HangPlayer Object
	 */
	public String toString2(){
		return (name + "\n" + num_Win + "\n" + num_Loss);
	}
	/**
	 * This method overides the default toString() method
	 * to provide information to the user about a HangPlayer object
	 * Please note this is not used when writing a list to the
	 * players file
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("\n%s%10s\n\n", "Welcome Back",getName()));
		sb.append(String.format("%s%30s\n", "Number of Wins",getWins()));
		sb.append(String.format("%s%27s\n\n", "Number of Losses", getLosses()));
		
		return sb.toString();
	}
}
