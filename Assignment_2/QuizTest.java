// CS 0401 Fall 2014
// Driver program to test Quiz class.  Look at this code
// carefully and look at the output.  Your output should
// match this.

import java.io.*; // needed for IOException

public class QuizTest
{
	public static void main(String[] args) throws IOException
	{
		Quiz Q;
		Q = new Quiz("QUIZ.txt");
		System.out.println("Testing questions.txt file");
		testQuiz(Q);
		System.out.println();
		System.out.println("Testing bogus.txt file");
		Q = new Quiz("BAD_QUIZ.txt");
		testQuiz(Q);	
	}
	
	public static void testQuiz(Quiz theQuiz)
	{
		Question quest;
		int status;
		System.out.println(theQuiz.hasAQuestion());
		if (theQuiz.hasAQuestion())
			System.out.println("Questions are available");
		
		status = theQuiz.getStatus();
		if (status == 0)
			System.out.println("Quiz is ok");
		quest = theQuiz.getQuestion();
		String Q = quest.getQ();
		String A = quest.getA();
		System.out.println("Here is the first question: " + Q);
		System.out.println("Here is the first answer: " + A);
		System.out.println("Entire object as a string: ");
		System.out.println(quest.toString());
		
		int num = 2;
		while (theQuiz.hasAQuestion())
		{
			quest = theQuiz.getQuestion();
			if (quest != null)
			{
				System.out.println("Question " + num + ": " + quest.getQ());
				System.out.println("Answer " + num + ": " + quest.getA());
				System.out.println();
				num++;
			}
		}
		status = theQuiz.getStatus();
		if (status == 1)
			System.out.println("End of file reached");
		else if (status == 2)
			System.out.println("Error in question file");
	}
}