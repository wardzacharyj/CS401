import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class ShapeFileHandler {
	/**
	 * Builds String to be written to file
	 * @param shapes
	 * @return sb	completed Builder for file
	 */
	public static StringBuilder saveNewCanvas(ArrayList<MyShape> shapes){
		StringBuilder sb = new StringBuilder();
		for(MyShape s: shapes)
			sb.append(s.saveData() + "\n");
		return sb;
		
	}
	/**
	 * Reads the coordinates back into shape array
	 * @param fileName
	 * @return
	 */
	public static ArrayList<MyShape> openCanvas(String fileName){
		ArrayList<MyShape> shapes = new ArrayList<MyShape>();
		
		try {
			Scanner sc = new Scanner(new File(fileName));
			sc.useDelimiter(":");
			while(sc.hasNextLine()){
				MyShape shape = null;
				Color color = null;
				String line = sc.nextLine().trim();
				String[] pieces = line.split(":"); //Designate the delimeter
				if(pieces[0].contains("G")){
					//MyTextBox(int startX, int startY, int h, String s,Color c)
					color = new Color(Integer.parseInt(pieces[5]),Integer.parseInt(pieces[6]),Integer.parseInt(pieces[7]));
					shape = new MyTextBox(Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]), Integer.parseInt(pieces[3]),pieces[4],color);
				}
				else{
					try{
						color = new Color(Integer.parseInt(pieces[5]),Integer.parseInt(pieces[6]),Integer.parseInt(pieces[7]));
					}catch(ArrayIndexOutOfBoundsException e){
						
					}
				}
				//Builds new Array depending on Split line
				
				if(pieces[0].contains("R"))
					shape = new MyRectangle(Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]), Integer.parseInt(pieces[3]), Integer.parseInt(pieces[4]),color);
				if(pieces[0].contains("O"))
					shape = new MyOval(Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]), Integer.parseInt(pieces[3]), Integer.parseInt(pieces[4]),color);
				if(pieces[0].contains("T"))
					shape = new MyTree(Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]), Integer.parseInt(pieces[3]), Integer.parseInt(pieces[4]),color);
				if(pieces[0].contains("S"))
					shape = new MySnowflake(Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]), Integer.parseInt(pieces[3]), Integer.parseInt(pieces[4]));
				if(pieces[0].contains("H"))
					shape = new MyCabin(Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]), Integer.parseInt(pieces[3]), Integer.parseInt(pieces[4]),color);
				if(pieces[0].contains("C"))
					shape = new MyCloud(Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]), Integer.parseInt(pieces[3]), Integer.parseInt(pieces[4]),color);
				if(pieces[0].contains("M"))
					shape = new MyMountain(Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]), Integer.parseInt(pieces[3]), Integer.parseInt(pieces[4]));
				
				
				shapes.add(shape); //Add to Array
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return shapes;
	}
}
