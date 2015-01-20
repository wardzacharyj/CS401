import java.text.NumberFormat;

/**
 * Class: CS-0401
 * Gasoline.java
 * Purpose: Class allows me to create Propane objects from information
 * provided by the user
 *
 * @author Zach Ward
 * @version 1.0
 */

public class Propane {
	
	private boolean cardHolder;
	private double numExchange;
	private double numNoExchange;
	private double subTotal;
	private double totalCost;
	
	private double discount;
	private double exCost;
	private double noExCost;
	static NumberFormat fmt = NumberFormat.getCurrencyInstance();
	
	Propane(boolean cardHolder,double numExchange, double numNoExchange,double exSubTotal, double boughtSubTotal, double discount, double totalCost){
		this.cardHolder = cardHolder;
		this.numExchange = numExchange;
		this.numNoExchange = numNoExchange;
		this.exCost = exSubTotal;
		this.noExCost = boughtSubTotal;
		this.discount = discount;
		this.totalCost = totalCost;
	}
	public double getNumExchange(){
		return numExchange;
	}
	public double getNumNoExchange(){
		return numNoExchange;		
	}
	public double getExCost(){
		return exCost;
	}
	public double getNoExCost(){
		return noExCost;
	}
	public double getTotalCost(){
		return totalCost;
	}
	public double getSubTotal() {
		return subTotal;
	}
	public boolean getCardHolder(){
		return cardHolder;
	}
	public double getDiscount(){
		return discount;
	}
	public void getFinalOutput(){
		if(getNumExchange() > 0)
			System.out.printf("\n\t%s%10s%21s\n","Exchanged Tanks" ,getNumExchange(), fmt.format(getExCost()));
		if(getNumNoExchange() > 0)
			System.out.printf("\t%s%10s%21s\n","Purchased Tanks" ,getNumNoExchange(), fmt.format(getNoExCost()));
		System.out.printf("\t%s%37s\n","Sub Total" ,fmt.format(getExCost() + getNoExCost()));
		System.out.println("\t-----------------------------------------------");
		if(cardHolder){
			System.out.printf("\t%s%37s\n","Discounts" ,fmt.format(discount));
			System.out.println("\t-----------------------------------------------");
		}
		System.out.printf("\t%s%38s\n\n","Sub Cost" ,fmt.format(totalCost));
	
	}
	
	
	
}
