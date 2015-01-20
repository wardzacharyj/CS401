import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;

/**
 * Class: CS-0401
 * Quiz.java
 * Purpose: This class acts as the blueprint to create
 * 			Player objects. Player objects contain a name,
 * 			correct answers, wrong answers, and quiz percentage
 * 			scores. It stores these objects in PLAYERS.txt
 *
 * @author Zach Ward
 * @version 1.0
 */
public class Player {
	private String name;
	private int correct;
	private int wrong;
	private double avg;

	Player(String n, int c, int w){
		name = n;
		correct = c;
		wrong = w;
	}
	/**
	 * This method calculates the average score based
	 * on the correct answers divided by the total questions
	 */
	public void calcAverage(){
		avg =  ((double)(correct)/ ((double)correct + (double)wrong));
	}
	/**
	 * Mutator method for correct answers
	 * @param i value passed used to update correct
	 */
	public void setCorrect(int i){
		correct = i;
	}
	/**
	 * Mutator method for wrong answers
	 * @param i	value passed used to update wrong
	 */
	public void setWrong(int i){
		wrong = i;
	}
	/**
	 * Returns name
	 */
	public String getName(){
		return name;
	}
	/**
	 * Returns correct answers
	 */
	public int getCorrect(){
		return correct;
	}
	/**
	 * Returns wrong answers
	 */
	public int getWrong(){
		return wrong;
	}
	/**
	 * Returns test average
	 */
	public double getAvg(){
		return avg;
	}
	/**
	 * returns a formated string of all information
	 * regarding the player's quiz results
	 */
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append("\t    ||=============================||\n");
		s.append("\t    ||                             ||\n");
		s.append("\t    ||        Quiz Results         ||\n");
		s.append("\t    ||                             ||\n");
		s.append("\t    ||=============================||\n\n");
		s.append(String.format("\t    %s%20s\n\n", "Name:      ", getName()));
		s.append(String.format("\t    %s%20s\n\n", "Correct:   ",getCorrect()));
		s.append(String.format("\t    %s%20s\n\n", "Wrong:     ",getWrong()));
		calcAverage();
		s.append(String.format("\t    %s%20s\n\n", "Percentage:",
				MessageFormat.format("{0,number,#.##%}", getAvg())));
		s.append("\t    ---------------------------------\n");
		return s.toString();
	}
	/**
	 * This method writes the player object 
	 * @param f
	 */
	public void writeToFile(String f){
			PrintWriter pw;
			try {
				pw = new PrintWriter(new FileWriter(f, true));
				pw.println(getName());
				pw.println(getCorrect());
				pw.println(getWrong());
				pw.close();
			} catch (IOException e) {
				System.out.println("An error occured because the program was unable to write to " + f);
			}
	}
}
