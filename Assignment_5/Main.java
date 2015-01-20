import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileWriter;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This Main class Builds the GUI and sets ToolBox Listeners
 * The program itself allows the user to draw freely from a variety of shapes by
 * simply clicking and dragging
 * @author Zach
 *
 */

public class Main {
	private JFrame mainWindow;
	private Container mainContainer;
	private JMenuBar menuBar;
	private JMenu file;
	private String fileName;
	private boolean flag;
	private CanvasPanel canvasPanel = new CanvasPanel();
	private ToolBox toolbox;
	private JMenuItem[] file_Menu = new JMenuItem[7];
	private ButtonGroup group;
	Main(){
		mainWindow = new JFrame("Card Maker");
		group = new ButtonGroup();
		JPanel s = new ColorPanel();
		buildToolBar();
		canvasPanel.setPreferredSize(new Dimension(800,500));

		mainContainer = mainWindow.getContentPane();
		mainContainer.setBackground(new Color(0xE0E0E0));
		toolbox = new ToolBox();
		
		mainContainer.add(new TopPanel(),BorderLayout.NORTH);
		mainContainer.add(s,BorderLayout.EAST);
		mainContainer.add(canvasPanel, BorderLayout.CENTER);
		mainContainer.add(toolbox,BorderLayout.WEST);
		setToolBoxListeners();
		centerFrame();
		mainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainWindow.setPreferredSize(new Dimension(900,670));
		mainWindow.pack();
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setVisible(true);
	}
	public void buildToolBar(){
		menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);

			
		file = new JMenu("File");
		file_Menu[0] = new JMenuItem("New");
		file_Menu[1] = new JMenuItem("Open");
		file_Menu[2] = new JMenuItem("Save");
		file_Menu[3] = new JMenuItem("Save As");
		file_Menu[4] = new JMenuItem("Export as PNG");
		file_Menu[5] = new JMenuItem("Print");
		file_Menu[6] = new JMenuItem("Exit");

		MenuListener m = new MenuListener();
		for(int i = 0; i < file_Menu.length; i++)
			file_Menu[i].addActionListener(m);
		
		for(JMenuItem a : file_Menu)
			file.add(a);
		
		menuBar.add(file);
		
		mainWindow.setJMenuBar(menuBar);
	}
	
	public void centerFrame(){
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) (dimension.getWidth() - mainWindow.getWidth() / 2);
	    int y = (int) (dimension.getHeight() - mainWindow.getHeight() / 2);
	    mainWindow.setLocation(x, y);
	    
	}
	public void setToolBoxListeners(){
		
		group.add(toolbox.getMoverButton());
		group.add(toolbox.getRecButton());
		group.add(toolbox.getOvalButton());
		group.add(toolbox.getTreeButton());
		group.add(toolbox.getSnowflakeButton());
		group.add(toolbox.getCabinButton());
		group.add(toolbox.getCloudButton());
		group.add(toolbox.getMountainButton());
		group.add(toolbox.getTextBoxButton());
		
		MyItemListener l = new MyItemListener();
		
		
		toolbox.getMoverButton().addItemListener(l);
		toolbox.getRecButton().addItemListener(l);
		toolbox.getOvalButton().addItemListener(l);
		toolbox.getTreeButton().addItemListener(l);
		toolbox.getSnowflakeButton().addItemListener(l);
		toolbox.getCabinButton().addItemListener(l);
		toolbox.getCloudButton().addItemListener(l);
		toolbox.getMountainButton().addItemListener(l);
		toolbox.getTextBoxButton().addItemListener(l);
		
		ClearListener c = new ClearListener();
		ColorPanel.getClearButton().addActionListener(c);
	}
	public static void main(String[] args){
		new Main();
	}
	//Reminds User to Save before Exit/Open etc
	public void saveReminder(){
		int i = JOptionPane.showConfirmDialog(mainWindow, "Would you like to save your current project?");
		if(JOptionPane.YES_OPTION == i){
			flag = true;
			if(fileName == null){
				JFileChooser chooser = new JFileChooser();
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
			    int retrival = chooser.showSaveDialog(null);
		    
			    if (retrival == JFileChooser.APPROVE_OPTION) {
			    	try {
			            FileWriter fw = new FileWriter(chooser.getSelectedFile()+".txt");
			            fw.write(canvasPanel.save().toString());
			            fw.close();
			    	} catch (Exception ex) {
			    		ex.printStackTrace();
		        	}
		    	}
			}
		}
		if(JOptionPane.NO_OPTION == i)
			flag = true;
		else
			flag = false;
			
	}
	//Listener for File Menu
	class MenuListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			JMenuItem m = (JMenuItem)e.getSource();
			if(m == file_Menu[0]){ //New
				if(canvasPanel.isBlank())
					saveReminder();	
				if(flag)
					canvasPanel.clear();
			}
			if(m == file_Menu[1]){ //Open File
				if(canvasPanel.isBlank()) // If not blank prompt to save
					saveReminder();		

					JFileChooser fChoose =new JFileChooser();
					fChoose.setDialogTitle("Please Choose a Word Text File");
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
					fChoose.setFileFilter(filter);
					File workingDirectory = new File(System.getProperty("user.dir"));
					fChoose.setCurrentDirectory(workingDirectory); //Set Current Dir to default
					fChoose.validate();
					int returnVal = fChoose.showOpenDialog(null);
					if(returnVal == JFileChooser.APPROVE_OPTION){ 
						fileName = fChoose.getSelectedFile().getName(); 
						canvasPanel.load(fileName);
					}
			}
			if(m == file_Menu[2]){ // Save 
				if(fileName != null){ //Checks if fileName already known
					try{
						FileWriter fw = new FileWriter(fileName);
			            fw.write(canvasPanel.save().toString()); //calls save method in canvas and returns formatted String
			            fw.close();
		        	} catch (Exception ex) {
			            ex.printStackTrace();
			        }
				}
				else{
					JFileChooser chooser = new JFileChooser();
					File workingDirectory = new File(System.getProperty("user.dir")); // default dir
					chooser.setCurrentDirectory(workingDirectory);
				    int retrival = chooser.showSaveDialog(null);
			    
				    if (retrival == JFileChooser.APPROVE_OPTION) {
				    	try {
				            FileWriter fw = new FileWriter(chooser.getSelectedFile()+".txt");
				            fileName = chooser.getSelectedFile()+".txt";
				            fw.write(canvasPanel.save().toString());
				            fw.close();
				    	} catch (Exception ex) {
				    		ex.printStackTrace();
			        	}
			    	}
				}
			}
			if(m == file_Menu[3]){ // Save As	
				JFileChooser chooser = new JFileChooser();
				File workingDirectory = new File(System.getProperty("user.dir")); //defualt dir
				chooser.setCurrentDirectory(workingDirectory);
			    int retrival = chooser.showSaveDialog(null);
		    
			    if (retrival == JFileChooser.APPROVE_OPTION) {		//Lets User Specify save location
			    	try {
			            FileWriter fw = new FileWriter(chooser.getSelectedFile()+".txt");
			            fileName = chooser.getSelectedFile()+".txt";		
			            fw.write(canvasPanel.save().toString());
			            fw.close();
			    	} catch (Exception ex) {
			    		ex.printStackTrace();
		        	}
		    	}
			}
			if(m == file_Menu[4]){ // Export As PNG
				canvasPanel.saveAsPNG();		//calls Save as Png method in canvasPanel
			}
			if(m == file_Menu[5]){ //Print
				 PrinterJob pj = PrinterJob.getPrinterJob();
				  pj.setJobName("Assignment 5");	//Arb Title
				  pj.setPrintable (new Printable() {    
				    public int print(Graphics g, PageFormat p, int pageNum){
				      if (pageNum > 0){		
				    	  return Printable.NO_SUCH_PAGE;  //Non Existant
				      }
				      Graphics2D g2 = (Graphics2D) g;
				      g2.translate(p.getImageableX(), p.getImageableY()); //Hanfdle Translation
				      canvasPanel.paint(g2); 	//paints panel
				      return Printable.PAGE_EXISTS;  
				    }
				  });
				  
				  if (pj.printDialog() == false)
					  return;
				  try {
				        pj.print();
				  } catch (PrinterException ex) {}
			}
			if(m == file_Menu[6]){ //Exit
				saveReminder();
				if(flag)
					System.exit(0);
			}
		}
	}
	//handles Tool selection
	class MyItemListener implements ItemListener{
		public void itemStateChanged(ItemEvent e) {
			JToggleButton c = (JToggleButton) e.getSource();
			canvasPanel.updatePop(false);
			if(c == toolbox.getMoverButton()){
				 CanvasPanel.changeTool(ToolBox.MOVER);	
				 canvasPanel.updatePop(true);
			}
			if(c == toolbox.getRecButton())
				 CanvasPanel.changeTool(ToolBox.RECTANGLE);	 
			 if(c == toolbox.getOvalButton())
				 CanvasPanel.changeTool(ToolBox.OVAL);		 
			 if(c == toolbox.getTreeButton())
				 CanvasPanel.changeTool(ToolBox.TREE);
			 if(c == toolbox.getSnowflakeButton())
				 CanvasPanel.changeTool(ToolBox.SNOWFLAKE);
			 if(c == toolbox.getCabinButton())
				 CanvasPanel.changeTool(ToolBox.CABIN);
			 if(c == toolbox.getCloudButton())
				 CanvasPanel.changeTool(ToolBox.CLOUD);
			 if(c == toolbox.getMountainButton())
				 CanvasPanel.changeTool(ToolBox.MOUNTAIN);
			 if(c == toolbox.getTextBoxButton())
				 CanvasPanel.changeTool(ToolBox.TEXTBOX);	 
		}
	}
	class ClearListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if(b == ColorPanel.getClearButton())
				canvasPanel.clear();
		}
	}
}
