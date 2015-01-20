/**
 * Class: CS-0401
 * Quiz2.java
 * Purpose: Handles reading question and answers and hints from the specified
 * 			Quiz text file
 *
 * @author Zach Ward
 * @version 1.0
 */
import java.io.*;
import java.util.*;

public class Quiz2
{
	private String fName;     // Name of the file you will be reading from
	private Scanner theFile;  // Scanner use to read from the file
	public int status;

	
	// Create a new Quiz object by opening the associated file.  Note that this
	// method throws an IOException, so in the method that calls it you must also
	// have in the header "throws IOException".  We will discuss how to handle
	// these exceptions later.
	public Quiz2(String f) throws IOException
	{
		fName = f;
		File file = new File(fName);
        theFile = new Scanner(file);
	}
	
	// First check the status.  If it is 1 or 2 simply return false.
	// If it is 0, check the file:
	//		If there is a line in the file, return true
	//		If there is no line in the file, set status to 1 and
	//			return false.
	public boolean hasAQuestion()
	{
		if(status == 1 || status == 2 || status == 3)
			return false;
		if(status == 0){
			if(theFile.hasNext())
				return true;
			else{
				status = 1;
				return false;
			}
		}
		return false;
	}
	
	// Return that status of the Quiz object:
	// Status = 0: everything ok
	// Status = 1: end of file reached
	// Status = 2: error has occurred
	public int getStatus()
	{
		return status;
	}
			
	// Get the next question and set the status appropriately:
	//		If status is not 0, return null, otherwise:
	//		If no lines are left before anything is read, set status
	//			to 1 and return null
	//		If the hint is read but no hint left, set status to
	//			to 2 and return null
	//		If the answer is read but no answer left, set status
	//			to 3 and return null
	//		If both the question and answer are read, return them in
	//			a new Question object.
	public Question2 getQuestion()
	{
		String q = "",a ="", h = "";
		 if(theFile.hasNextLine())
            q = theFile.nextLine();
		 else{
			 status = 1;
			 return null; 
		 }
		 if(theFile.hasNextLine())
			 h = theFile.nextLine();
		 else{
			 status = 2;
			 return null;
		 }
		 if(theFile.hasNextLine()){
			 a = theFile.nextLine();
		 }
		 else{
			 return null;
		 }
		 if(status != 0)
			 return null;
		 else
			 return new Question2(q,h,a);
	}

}