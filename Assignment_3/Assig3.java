import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class: CS-0401
 * Assig3.java
 * This is the main method for the Assig3
 *
 *This Program is a Hangman games that saves data about
 *its players and reads random words from .txt files
 *specified by the user
 *
 * @author Zach Ward
 * @version 1.0
 */
public class Assig3 {
	static Scanner sc = new Scanner(System.in);
	static WordServer ws;
	static HangPlayer hp;
	static ArrayList<HangPlayer> p;
	public static void main(String[] args) throws FileNotFoundException {
		hp = new HangPlayer();
		p = HangPlayer.getPlayerList("Players.txt");
		newPlayer();
		poseWord();			
	}
	/**
	 * This Method creates the WordServer Object used to randomly choose
	 * words to pose to the user during the hangman game
	 * @param fileName	The name of the file that holds the database of words
	 * @throws FileNotFoundException Thrown only when the default words.txt is not found
	 */
	private static void setWordServer(String fileName) throws FileNotFoundException{
		Scanner fScan;
		ws = new WordServer();
		// Defaults to words.txt will throw FileNotFound if neither txt files
		// can be found
		try{
			fScan = new Scanner(new FileInputStream(fileName));
		}catch (FileNotFoundException e){
			fScan = new Scanner(new FileInputStream("words.txt"));
		}
		ws.loadWords(fScan);
	}
	/**
	 * This is the bulk of the program it asks the user the words
	 * based from the word Server. It displays letters guessed
	 * as well as if they guessed correctly of incorrectly
	 * @throws FileNotFoundException
	 */
	private static void poseWord() throws FileNotFoundException{
		// If guess wrong lose
		ArrayList<String> letters_Guessed = new ArrayList<String>();
		String word = ws.getNextWord();
		StringBuilder w = null;
		StringBuilder sw = null;
		String letter = "";
		int miss_Count = 0;
		
		if(word.equals("")){
			System.out.println("You've gone through all the words in the file");
			runEndDialog();
		}
		else{
			//Generate words
			w = new StringBuilder(word);
			sw = new StringBuilder(word.replaceAll("[^0-9.]", "_"));
			
			// MAIN LOOP
			while (!sw.toString().equalsIgnoreCase(word) && miss_Count != 7) {
				System.out.println("\n\t\t   The Current Word : " + sw);
				System.out.println("================================================================");
				System.out.println("\t\t(Enter 0 to give up on word)");
				System.out.println("\t\t(Enter 1 to quit the game)");
				letter = getLetterInput().toLowerCase();
				if(letter.length() > 1)
					grade(letter,word);
				else if(letter.equals("0"))
					giveUpWord(word);
				else if(letter.equals("1"))
					runEndDialog();
				
				if(word.contains(letter) && !letters_Guessed.contains(letter)){
					letters_Guessed.add(letter);
					for(int i = 0; i < w.length(); i++)
							if(w.charAt(i) == letter.charAt(0))
								sw.setCharAt(i, letter.charAt(0));
					System.out.println("\nSo far you have guessed " + getWrongLetters(letters_Guessed));
				}
				else{
					if(letters_Guessed.contains(letter))
						System.out.println("You have already guessed " + letter);
					else{
						miss_Count++;
						letters_Guessed.add(letter);
						System.out.println(letter.toUpperCase() + " was not found in the word");
						System.out.println("\n\t\tYou have "+ (7 - miss_Count) + " more guesses");
						System.out.println("\nYou have guessed " + getWrongLetters(letters_Guessed));
					}
				}
			}
			grade(sw.toString(), word);	
		}	
	}
	public static void giveUpWord(String correctWord) throws FileNotFoundException{
		System.out.println("================================================================");
		System.out.println("\n\t\t\tI\'m Sorry");
		System.out.printf("%10s%50s\n","The word was:",correctWord);
		System.out.println("-------------------------------------------------------------");
		hp.updateLoss(1);
		
		System.out.print("Would you like another word (Y/N):");
		String res = getInput("Y", "N");
		if(res.equals("N"))
			runEndDialog();
		else
			poseWord();	
	}
	/**
	 * This method is called when the user chooses to exit the program by hitting q
	 * mid quiz or when the words in the WordServer run out. This method adds the updated
	 * HangPlayer Object to the HangPlayer List then updates the Players.txt file.
	 * 
	 * @throws FileNotFoundException Thrown if .txt files cannot be located
	 */
	private static void runEndDialog() throws FileNotFoundException {
		p.add(hp);
		System.out.println(hp);
		HangPlayer.writePlayerList(p);
		HangPlayer.displayListResults(p);
		System.out.print("Would another player like to play (Y/N): ");
		String res = getInput("Y","N");
		if(res.equalsIgnoreCase("Y")){
			newPlayer();
			poseWord(); // New User
		}
		else{
			System.out.println("\n\t    ---------------Goodbye---------------");
			System.exit(0);
		}
	}
	/**
	 * Simple method used to update the Incorrect Letters guessed
	 * in the ArrayList passed to it
	 * @param a ArrayList that holds guesses
	 * @return s is the contents of a with spaces in it
	 */
	public static String getWrongLetters(ArrayList<String> a){
		String s = "";
		for(String x : a){
			s += x + " ";
		}
		return s;
	}
	/**
	 * Simple method to update the HangPlayer object hp depending
	 * on  whether or not the user was able to guess the correct word
	 * 
	 * @param win 		boolean passed to indicate a win or loss
	 * @throws FileNotFoundException Thrown if word server failed to find the .txt file for words
	 */
	public static void grade(String sw, String correctWord ) throws FileNotFoundException{
		if(sw.equals(correctWord)){
			System.out.println("================================================================");
			System.out.println("\n\t\t\tCongratulations");
			System.out.printf("%10s%50s\n","The word was:",correctWord);
			System.out.println("----------------------------------------------------------------");
			hp.updateWins(1);
		}
		else{
			System.out.println("================================================================");
			System.out.println("\n\t\t\tI\'m Sorry");
			System.out.printf("%10s%50s\n","The word was:",correctWord);
			System.out.println("-------------------------------------------------------------");
			hp.updateLoss(1);
		}
		System.out.print("Would you like another word (Y/N):");
		String res = getInput("Y", "N");
		if(res.equals("N"))
			runEndDialog();
		else
			poseWord();	
	}
	/**
	 * This method prompts the user to enter their name. It will exit if a
	 * single q is entered. It then searches the arrayList of hangplayers to see if the
	 * name exists in the array. If it does its data is read into the hp HangPlayer object
	 * and is then removed from the arraylist so an updated version can be added later.
	 * If the name is found its information will be displayed.
	 * 
	 * If the name is not found the hp object is initialized with the new name
	 * 
	 */
	public static void newPlayer() throws FileNotFoundException{
		System.out.println("-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
		System.out.println("\t\t  _      __    __                  ");
		System.out.println("\t\t | | /| / /__ / /______  __ _  ___ ");
		System.out.println("\t\t | |/ |/ / -_) / __/ _ \\/  \' \\/ -_)");
		System.out.println("\t\t |__/|__/\\__/_/\\__/\\___/_/_/_/\\__/ ");
		System.out.println("-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
		System.out.println("\t\t  Welcome To Hangman");
		System.out.print("Please Enter your name: ");
		String name = sc.nextLine();
		//Escape key to close program
		if(name.equalsIgnoreCase("Q")){
			System.out.println("\t\tGoodbye Thank you for playing");
			System.exit(0);
		}
		//Allows the user to choose the file they want to get the words from
		System.out.print("Enter File Name (Default is words.txt): ");
		String fileName = sc.nextLine();
		//Checks the name in the list of hp objects
		int index = HangPlayer.checkPlayer(p, name);
		// -1 index means the name could not be found in the list
		if(index == -1)
			hp = new HangPlayer(name, 0, 0);
		else{
			//Removes the old hp object from the list so it can be updated and rewritten
			hp = p.get(index);
			System.out.println(hp);
			p.remove(index);
		}
		//Word Server is then called to initialize the WordServer Object
		setWordServer(fileName);
	}
	/**
	 * This method forces the user to enter a single valid letter.
	 * As specified in the assignment the program will exit when q is entered
	 * @return guess 			refers to the letter the user guess
	 * @throws FileNotFoundException Dependent on if file is found in previous methods
	 */
	public static String getLetterInput() throws FileNotFoundException{
		String guess;
		do{
			System.out.print("\nGuess a letter or Guess the word:");
			guess = sc.nextLine().trim();
		}while(!(guess.matches("^[a-zA-Z0-1]*$")));
		return guess;
	}
	/**
	 * This is an overloaded method designed to gather all user input that requires
	 * picking between two string responses. The method will not return unless 
	 * the methods's input variable is equal to either option1 or option2
	 * 
	 * @param option1	Valid response used to match up against input
	 * @param option2	Valid response used to match up against input
	 * @return input	This will be equal to either option1 or option2
	 */
	private static String getInput(String option1, String option2){
		//Makes Sure the user chooses a valid choice
		String input = sc.nextLine();
		if(input.equals(""))
			input = sc.nextLine();
		while(!(input.equalsIgnoreCase(option1) || input.equalsIgnoreCase(option2))){
			System.out.print("Enter a valid response  (" + option1 + "/" 
					+ option2 +"): "); 
			input = sc.nextLine();
		}
		return input;
	}
}