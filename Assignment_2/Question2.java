// CS 0401 Fall 2014
// Question class.  This class will store a (Question, Hint, Answer).

public class Question2
{
	private String Q;  // Value of the question 
	private String H;  // Value of the Hint
	private String A;  // Value of the answer
	
	// Create a Question
	public Question2(String quest, String hint, String ans)
	{
		Q = new String(quest);
		H = new String(hint);
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
	// Returns the Hint of the Question
	public String getH(){
		return H;
	}
	// Overrides toString to display information about a quiz object 
	public String toString()
	{
		StringBuilder S = new StringBuilder();
		S.append("Question: " + Q + "\n");
		S.append("Hint: " + H + "\n");
		S.append("Answer: " + A + "\n");
		return S.toString();
	}
}