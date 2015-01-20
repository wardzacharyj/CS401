import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
/**
 * Class: CS-0401
 * WordServer.java
 * Purpose: Class Creates a WordServer Object from a textfile of
 * words specified by the user
 *
 * @author Zach Ward
 * @version 1.0
 */
public class WordServer {
	
	private String[] wordData;
	
	WordServer(){
		
	}
	/**
	 * This method populates the wordData array based on the scanner passed in
	 * @param s Scanner object used to populate the wordData array
	 * @throws FileNotFoundException
	 */
	public void loadWords(Scanner s) throws FileNotFoundException{
		wordData = new String[s.nextInt()];
		s.nextLine();
		int ct = 0;
		while(s.hasNextLine()){		
			wordData[ct] = s.nextLine().toLowerCase();
			ct++;
		}
		s.close();
	}
	/**
	 * This method gets a random word from the wordData array.
	 * It then removes the word from the wordData and resizes accordingly
	 * @return a Random word from wordData array or ""
	 */
	public String getNextWord(){
		Random r = new Random();
		int i;
		if(!(wordData.length <= 0)){
			i = r.nextInt(wordData.length);
			String s = wordData[i];
		    String[] temp = new String[wordData.length - 1];
		    //  System.arraycopy(Source Array, Source Position, Destination Array, Destination Position, length);
		    System.arraycopy(wordData, 0, temp, 0, i);
		    System.arraycopy(wordData, i+1, temp, i, wordData.length - i - 1);
		    wordData = temp.clone();
			return s;
		}
		else
			return "";
	}

}
