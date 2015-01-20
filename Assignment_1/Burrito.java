import java.text.NumberFormat;

/**
 * Class: CS-0401
 * Burrito.java
 * Purpose: Class allows me to create Burrito objects from information
 * provided by the user
 *
 * @author Zach Ward
 * @version 1.0
 */

public class Burrito {
	private int qt_1;
	private int qt_2;
	private int qt_3;
	
	static NumberFormat fmt = NumberFormat.getCurrencyInstance();
	private double totalCost;
	private double cost = 1;
	private double subTotal_1 = 0;
	private double subTotal_2 = 0;
	private double subTotal_3 = 0;
	private boolean cardHolder;
	
	
	Burrito(boolean cardHolder, double cost, int qt_1, int qt_2, int qt_3,double subTotal_1, double subTotal_2, double subTotal_3, double totalCost){
		this.cardHolder = cardHolder;
		this.qt_1 = qt_1;
		this.qt_2 = qt_2;
		this.qt_3 = qt_3;
		this.subTotal_1 = subTotal_1;
		this.subTotal_2 = subTotal_2;
		this.subTotal_3 = subTotal_3;
		this.totalCost = totalCost;
	}
	public double getTotalCost(){
		return totalCost;
	}
	private double getCost(){
		return cost;
	}
	private int getQt_1(){
		return qt_1;
	}
	private int getQt_2(){
		return qt_2;
	}
	private int getQt_3(){
		return qt_3;
	}
	private double getSubTotal_1() {
		return subTotal_1;
	}
	private double getSubTotal_2() {
		return subTotal_2;
	}
	private double getSubTotal_3() {
		return subTotal_3;
	}
	
	//Returns Formatted Text
	public void getFinalOutput(){
		if(cardHolder)
	 		System.out.println("\n\t\t-$.10 off normal Burrito Prices");
	 	System.out.printf("\n\t%-4s %15s%2s%20s\n", getQt_1(),"Burrito(s) for: ",fmt.format(getCost()),fmt.format(getSubTotal_1()));
	 	if(qt_2 > 0)
	 		System.out.printf("\t%-4s %15s%2s%20s\n", getQt_2(),"Burrito(s) for: ",fmt.format(getCost()-.1),fmt.format(getSubTotal_2()));
	 	if(qt_3 > 0)
	 		System.out.printf("\t%-4s %15s%2s%20s\n", getQt_3(),"Burrito(s) for: ",fmt.format(getCost()-.2),fmt.format(getSubTotal_3()));
	 	System.out.println("\t------------------------------------------------");
	 	System.out.printf("\t%-3s %37s\n","Sub Cost",fmt.format(getTotalCost()));
	
	}
	
}
