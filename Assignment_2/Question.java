// CS 0401 Fall 2014
// Question class.  This class will store a (Question, Answer) pair.
// See more details below.  You must use this class exactly as written
// here with no changes.

public class Question
{
	private String Q;  // Value of the question 
	private String A;  // Value of the answer
	
	// Create a Question
	public Question(String quest, String ans)
	{
		Q = new String(quest);
		A = new String(ans);
	}
	
	
	// Return the question part of the Question
	public String getQ()
	{
		return Q;
	}
	
	// Return the answer part of the Question
	public String getA()
	{
		return A;
	}
	
	public String toString()
	{
		StringBuilder S = new StringBuilder();
		S.append("Question: " + Q + "\n");
		S.append("Answer: " + A + "\n");
		return S.toString();
	}
}