import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Class: CS-0401
 * Assig2.java
 * 
 * This program tests the user. The quiz's questions and answers are read in
 * from a text file and the users results and name are stored to a separate
 * text file. If the user has already taken the quiz it will display their
 * saved results.
 *
 * @author Zach Ward
 * @version 1.0
 */

public class Assig2 {
	private static Scanner sc;
	private static Scanner input;
	private static Player p;
	
	public static void main(String[] args) throws IOException {
		input = new Scanner(System.in);
		while(1 < 3){
			System.out.println("===============================================================");
			System.out.println("||                 Welcome To Random Quiz                    ||");
			System.out.println("===============================================================\n");
			
			// Populates Player p
			p = getPlayerFile(startNameEntry(), "PLAYERS.txt"); // startNameEntry gets Users Name
			
			if(p.getCorrect() == 0 && p.getWrong() == 0){ //Tests if the player object has set default values
				System.out.print("Would you like to take the test with hints?(Y/N): ");
				// Determines which class and objects to use
				if(getInput("Y", "N").equalsIgnoreCase("Y")){
					Quiz2 q = new Quiz2("HINT_QUIZ.txt");
					runQuiz(q);
				}
				else{
					Quiz q = new Quiz("QUIZ.txt");
					runQuiz(q);
				}	
				//Displays Results
				System.out.println("\n"+p);	
				printPlayerFile("PLAYERS.txt");
			}
			else{
				// Occurs if player name is found, regardless of case in PLAYERS.txt
				System.out.println("\nYou already took the quiz. Your results will be displayed below");
				System.out.println("\n"+p);	
				printPlayerFile("PLAYERS.txt");
			}			
				
			// Program Exit Prompt
			System.out.print("Does another player wish to take the quiz(Y/N): ");
			if(getInput("Y", "N").equalsIgnoreCase("N")){
				System.out.println("\n   . . . Thank you for taking random quiz, Goodbye . . .");
				System.exit(0);
			}
		}
	}
	/** 
	 * This methods cycles through the questions by checking if the quiz object
	 * q has another question. The Question object is then used to
	 * retrieve questions as well as compare user input to the correct
	 * answers.
	 * 
	 * 
	 * When all the questions have been read. The player object info is written to the
	 * PLAYERS.txt file
	 * @param q
	 */
	private static void runQuiz(Quiz q) {
		Question quest;
		String a = "";
		boolean retry = false;
		int ct = 1;
		while (q.hasAQuestion()){
			quest = q.getQuestion();
			a = pose_Question(ct, quest);
			retry = check_A(a, quest, retry);
				if(retry){
					a = pose_Question(ct, quest);
					check_A(a, quest, retry);
					retry = false;
				}	
			ct++;
		}
		int status = q.getStatus();
		if (status == 1)
			p.writeToFile("PLAYERS.txt");
		if (status == 2)
			JOptionPane.showMessageDialog(null, "Warning there was an error reading the text file","Error",2);
	}
	/** 
	 * This methods cycles through the question2 by checking if the Quiz2 object
	 * q has another question. The Question object is then used to
	 * retrieve questions and hints as well as compare user input to the correct
	 * answers.
	 * 
	 * 
	 * When all the questions have been read. The player object info is written to the
	 * PLAYERS.txt file
	 * @param q
	 */
	public static void runQuiz(Quiz2 q){
		Question2 quest;
		String a = "";
		boolean retry = false;
		int ct = 1;
		while (q.hasAQuestion()){
			quest = q.getQuestion();
			a = pose_Question(ct, quest);
			retry = check_A(a, quest, retry);
				if(retry){
					a = pose_Question(ct, quest);
					check_A(a, quest, retry);
					retry = false;
				}	
			ct++;
		}
		int status = q.getStatus();
		if (status == 1)
			p.writeToFile("PLAYERS.txt");
		if (status == 2)
			JOptionPane.showMessageDialog(null, "Warning there was an error reading the text file","Error",2);
	}
	/**
	 * This method prints the questions to the user for Question
	 * Objects. It then records the user's input and returns their
	 * answer as a String.
	 * @param q_Num Is used as a counter to let the user know what question they
	 * 		  are on
	 * @param quest refers to the Question object passed into the method
	 * @return a This is the users response to the question
	 */
	public static String pose_Question(int q_Num, Question quest){
		String a = "";
		System.out.println("\n\t*********************************************");
		System.out.println("\t|                Question " + q_Num + "                 |");
		System.out.println("\t---------------------------------------------");
		System.out.print("\tQ: " + quest.getQ() + "\n\tA: ");
		do{
			a = input.nextLine();
			if(a.trim().isEmpty())
				System.out.print("\n\t    <<<| You Entered An Empty Answer |>>>\n\tA:");
		}while(a.trim().isEmpty());
		return a;
	}
	/**
	 * This method prints the questions to the user for Question2
	 * Objects. It then records the user's input and returns their
	 * answer as a String.
	 * @param q_Num Is used as a counter to let the user know what question they
	 * 		  are on
	 * @param quest refers to the Question2 object passed into the method
	 * @return a This is the users response to the question
	 */
	public static String pose_Question(int q_Num, Question2 quest){
		String a = "";
		System.out.println("\n\t*********************************************");
		System.out.println("\t|                Question " + q_Num + "                 |");
		System.out.println("\t---------------------------------------------");
		System.out.print("\tQ: " + quest.getQ() + "\n\tA: ");
		do{
			a = input.nextLine();
			if(a.trim().isEmpty())
				System.out.print("\n\t    <<<| You Entered An Empty Answer |>>>\n\tA:");
		}while(a.trim().isEmpty());
		return a;
	}
	/**
	 * This method updates the user and the Player object
	 * respectively about whether or not they responded 
	 * correctly to the question.
	 * 
	 * 
	 * @param a if true the user question matched. If false it did not
	 * @param quest the Question object being used to compare the users
	 * 		  response to the correct answer
	 * @param retry This acts as a flag to whether or not the user can try again
	 * @return
	 */
	public static boolean check_A(String a , Question quest, boolean retry){
		if(a.equalsIgnoreCase(quest.getA())){
			p.setCorrect(p.getCorrect() + 1);
			System.out.println("\n\t\tThat's Correct! Great Job!\n");
			return false;
		}
		if(!retry)
			System.out.println("\n\tSorry That's incorrect, you have one more try\n");
		else{
			p.setWrong(p.getWrong() + 1);
			System.out.println("\n\t   <<<| Sorry You Got That One Wrong |>>>" +
			"\n\n\tCorrect Answer: " + quest.getA() + "\n");	
		}
		return true;
	}
	/**
	 * This method updates the user and the Player object
	 * respectively about whether or not they responded 
	 * correctly to the question.
	 * 
	 * 
	 * @param a if true the user question matched. If false it did not
	 * @param quest the Question2 object being used to compare the users
	 * 		  response to the correct answer
	 * @param retry This acts as a flag to whether or not the user can try again
	 * @return
	 */
	public static boolean check_A(String a , Question2 quest, boolean retry){
		if(a.equalsIgnoreCase(quest.getA())){
			p.setCorrect(p.getCorrect() + 1);
			System.out.println("\n\t\tThat's Correct! Great Job!\n");
			return false;
		}
		if(!retry){
			System.out.println("\n\tSorry That's incorrect, you have one more try\n");
			System.out.println("\tHint: " + quest.getH());
		}
		else{
			p.setWrong(p.getWrong() + 1);
			System.out.println("\n\t   <<<| Sorry You Got That One Wrong |>>>" +
			"\n\n\tCorrect Answer: " + quest.getA() + "\n");	
		}
		return true;
	}
	/**
	 * This is an overloaded method designed to gather all user input that requires
	 * picking between two string responses. The method will not return unless 
	 * the methods's input variable is equal to either option1 or option2
	 * @param option1	Valid response used to match up against input
	 * @param option2	Valid response used to match up against input
	 * @return input	This will be equal to either option1 or option2
	 */
	private static String getInput(String option1, String option2){
		//Makes Sure the user chooses a valid choice
		String in = input.nextLine();
		if(input.equals(""))
			in = input.nextLine();
		while(!(in.equalsIgnoreCase(option1) || in.equalsIgnoreCase(option2))){
			System.out.print("Enter a valid response  (" + option1 + "/" 
					+ option2 +"): "); 
			in = input.nextLine();
		}
		return in;
	}
	/**
	 * This methods simply gathers and returns the users name
	 * 
	 * @return userName This is the players name
	 */
	private static String startNameEntry(){	
		String userName;
		do{
			System.out.print("Please Enter Your Name: ");
			userName = input.nextLine();
				if(userName.trim().isEmpty()) // Tests if user does not enter name
					System.out.println("\n\t<<<| Sorry you cannot enter a blank name |>>>\n");
			}while(userName.trim().isEmpty());	
		return userName;
	}
	/**
	 * This method creates a Player Object based on the name entered
	 * by the user. If PLAYERS.txt contains the user name it populates
	 * a new Player Object from the right and wrong integers found below the name
	 * in the file.
	 * If the text file does not contain the user's name it will create a player
	 * object with zeros for right and wrong so they can be updated at a later date.
	 * 
	 * @param name Player's Name
	 * @param fileName Player file being read
	 * @return Player Object
	 * @throws IOException thrown if PLAYER.txt is not found
	 */
	private static Player getPlayerFile(String name, String fileName) throws IOException{
		File temp = new File(fileName);
		sc = new Scanner(temp);
		while(sc.hasNextLine())
			if(sc.nextLine().equalsIgnoreCase(name))
				return new Player(name, sc.nextInt(), sc.nextInt()); // Returns new Player object with values found in the file
		return new Player(name,0,0); // returns a new Player object with default values of zero
	}
	/**
	 * This method counts all users, correct and wrong answers
	 * from Players.txt and displays them to the users
	 * This method should be used after the new users player object
	 * is added to the file
	 * 
	 * @param fileName This is the text file(PLAYERS.txt) where the
	 * 					information is read in from
	 * @throws IOException Thrown if filename is not a valid filename
	 * 						or cannot be found
	 */
	private static void printPlayerFile(String fileName) throws IOException{
		sc.close();
		File temp = new File(fileName);
		sc = new Scanner(temp);
		double right = 0, wrong = 0, num_P = 0;	
		while (sc.hasNextLine()) {
			num_P++;
			sc.nextLine();
			if(sc.hasNextDouble())
				right += sc.nextDouble();
			if(sc.hasNextDouble())
				wrong += sc.nextDouble();
        }
		double total = right + wrong;
		
		StringBuilder s = new StringBuilder();
		s.append("\t    ||=============================||\n");
		s.append("\t    ||                             ||\n");
		s.append("\t    ||     Overall Quiz Stats      ||\n");
		s.append("\t    ||                             ||\n");
		s.append("\t    ||=============================||\n\n");
		s.append(String.format("\t    %s%13s\n\n", "Number of Players: ",(int)(num_P / 2)));
		s.append(String.format("\t    %s%18s\n\n", "Right Answers:",(int)right));
		s.append(String.format("\t    %s%18s\n\n", "Wrong Answers:",(int)wrong));
		s.append(String.format("\t    %s%22s\n\n", "Percentage:",
				MessageFormat.format("{0,number,#.##%}", (right/total))));
		s.append("\t    ---------------------------------\n");
		
		System.out.println(s);
	}
}
