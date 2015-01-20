import java.util.*;
import java.io.*;

public class WordServerTest
{
	public static void main(String [] args) throws IOException
	{
		
		
		System.out.println("Testing with WORD_LIST.txt:");
		testWords("words.txt");
		System.out.println();
		
		System.out.println("Testing with WORD_LIST.txt:");
		testWords("words.txt");
		System.out.println();
		
		System.out.println("Testing with test_words.txt:");
		testWords("test_words.txt");
		System.out.println();
		System.out.println("Testing with test_words.txt:");
		testWords("test_words.txt");
		
	}
	
	public static void testWords(String fName) throws IOException
	{
		WordServer ws = new WordServer();
		String word;
		Scanner fScan = new Scanner(new FileInputStream(fName));
		ws.loadWords(fScan);
		word = ws.getNextWord();
		while (word != "") 
		{
			word = word.toUpperCase();
			System.out.println("The next word is: " + word);
			word = ws.getNextWord();
		}
	}
}