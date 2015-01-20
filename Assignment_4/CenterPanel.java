import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


public class CenterPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JPanel topPanel,midPanel,btmPanel;
	private CardPanel backdrop;
	private JLabel lbl_Word,lbl_Guesses;
	private JButton btn_Enter;
	private JTextField user_Input;
	private JComboBox<String> choice;
	static Color gray = new Color(0xE0E0E0);
	
	/**
	 * Constructors sets the three panels
	 */
	public CenterPanel(){
		topPanel = new JPanel();
		midPanel = new JPanel();
		btmPanel = new JPanel();
		setLayout(new BorderLayout());
		backdrop = new CardPanel(300,400, false, 0);
		add(backdrop);
		init_top();
		init_mid();
		init_bottom();
	}
	/**
	 * Initializes the TopPanel with fonts and borders
	 */
	private void init_top() {
		topPanel.setLayout(new GridLayout(0,2));
		//topPanel.setBorder(new EmptyBorder(50, 10, 50, 10));
		topPanel.setOpaque(false);
		JLabel lbl_currentWord = new JLabel("Current Word");		
		lbl_currentWord.setFont(new Font("SansSerif", Font.PLAIN,30));
		lbl_currentWord.setBorder(BorderFactory.createEmptyBorder(50, 50, 10, 50));
		topPanel.add(lbl_currentWord);
		topPanel.add(lbl_Word = new JLabel());
		lbl_Word.setBorder(BorderFactory.createEmptyBorder(50, 50, 10, 50));
		lbl_Word.setFont(new Font("SansSerif", Font.PLAIN,26));
		backdrop.add(topPanel,BorderLayout.NORTH);
	}
	/**
	 * Initializes the middle panel with fonts and borders
	 */
	private void init_mid() {
		midPanel = new JPanel();
		midPanel.setLayout(new GridLayout(0,2));
		midPanel.setOpaque(false);
		JLabel guessLabel = new JLabel("Wrong Guesses");
		guessLabel.setFont(new Font("SansSerif", Font.PLAIN,30));
		guessLabel.setBorder(BorderFactory.createEmptyBorder(150, 50, 10, 50));
		midPanel.add(guessLabel);
		lbl_Guesses = new JLabel("");
		lbl_Guesses.setFont(new Font("SansSerif", Font.PLAIN,26));
		lbl_Guesses.setBorder(BorderFactory.createEmptyBorder(150, 50, 10, 50));
		midPanel.add(lbl_Guesses);
		backdrop.add(midPanel,BorderLayout.CENTER);
		
	}
	/**
	 * Initializes the bottom panel
	 */
	private void init_bottom() {
		btmPanel = new JPanel();
		btmPanel.setLayout(new GridLayout(0,3));
		btmPanel.add(user_Input = new JTextField());
		user_Input.setDocument(new JTextFieldLimit(1));
		
		user_Input.getDocument().addDocumentListener(new MyTextListener());
		btmPanel.add(btn_Enter = new JButton("Guess"));
		String[] c = {"Letter","Word"};
		btmPanel.add(choice = new JComboBox<String>(c));
		choice.addActionListener(new ComboListener());
		btn_Enter.setEnabled(false);
		btmPanel.setBorder(BorderFactory.createEmptyBorder(25, 50, 10, 25));
		btmPanel.setBackground(gray);
		add(btmPanel,BorderLayout.SOUTH);		
	}
	/**
	 * @return btn_Enter the entry button
	 */
	public JButton getEnterButton(){
		return btn_Enter;
	}
	/**
	 * @return lbl_Guesses label for guesses so far
	 */
	public JLabel getGuessLabel(){
		return lbl_Guesses;
	}
	/**
	 * clears the input textfield
	 */
	public void clearUserField(){
		user_Input.setText("");
	}
	/**
	 * clears the user guess JLabel
	 */
	public void clearGuessField(){
		lbl_Guesses.setText("");
	}
	/**
	 * @return user_Input.getText() this returns the current user_Input text
	 */
	public String getLetter(){
		return user_Input.getText();
	}
	/**
	 * updates the current mystery label
	 * @param a the updated String
	 */
	public void updateWord(String a){
		lbl_Word.setText(a);
	}
	/**
	 * This choice resets the combobox
	 */
	public void resetCombo(){
		choice.setSelectedIndex(0);
	}
	/**
	 * This DocumentListener
	 * toggles the enter button on or off based
	 * on whether or not the field is blank 
	 * @author Zach
	 *
	 */
	private class MyTextListener implements DocumentListener{
		public void changedUpdate(DocumentEvent e) {
			toggle();
		}
		public void removeUpdate(DocumentEvent e) {
			toggle();
		}
		public void insertUpdate(DocumentEvent e) {
			toggle();	
		}
		public void toggle() {
			if (user_Input.getText().equals(""))
		    	 btn_Enter.setEnabled(false);
		    else
		    	 btn_Enter.setEnabled(true);
	    }
	}
	/**
	 * This class lets us adjust the toggle the char limit
	 * in the user_Input field
	 * 
	 * @author Zach
	 */
	private class ComboListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JComboBox<String> state = (JComboBox<String>) e.getSource();
			if(state.getSelectedItem().toString().equals("word"))
				user_Input.setDocument(new JTextFieldLimit(1));
			else
				user_Input.setDocument(new JTextFieldLimit(100)); //Arb Limits
			
			user_Input.getDocument().addDocumentListener(new MyTextListener());
		}
	}
	// Way to limit input in Textfield based on letter vs word 
	// doc used at https://docs.oracle.com/javase/6/docs/api/javax/swing/JTextField.html
	// Forces correct input lengths
	/**
	 * This allows us to limit the amount of characters in a TextField
	 * by adding to PlainDocument
	 * @author Zach
	 */
	private class JTextFieldLimit extends PlainDocument {
		  private int charLimit;
		  JTextFieldLimit(int l) {
		   charLimit = l;
		   }
		  public void insertString( int offset, String  field, AttributeSet attr ) throws BadLocationException {
		    if (field == null) 
		    	return;
		    if ((getLength() + field.length()) <= charLimit) 
		      super.insertString(offset, field, attr);
		  }
	}
	
}
