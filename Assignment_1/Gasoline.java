import java.text.NumberFormat;


/**
 * Class: CS-0401
 * Gasoline.java
 * Purpose: Class allows me to create Gasoline objects from information
 * provided by the user
 *
 * @author Zach Ward
 * @version 1.0
 */

public class  Gasoline{
		static NumberFormat fmt = NumberFormat.getCurrencyInstance();
	
		private String type;
		boolean cardHolder;
		private double gallons;
		private double discount;
		private double fuelCost;
		private double totalCost;
		private double subTotal;
		
		Gasoline(boolean cardHolder,String type, double fuelCost, double gallons, double subTotal, double discount,double totalCost ){
			this.cardHolder = cardHolder;
			this.type = type;
			this.fuelCost = fuelCost;
			this.gallons = gallons;
			this.subTotal = subTotal;
			this.discount = discount;
			this.totalCost = totalCost;
		}
		public double getTotalCost(){
			return this.totalCost;
		}
		private double getGallons(){
			return this.gallons;
		}
		 double getFuelCost(){
			return  this.fuelCost;
		}
		public String getType(){
			return this.type;
		}
		private double getSubTotal(){
			return this.subTotal;
		}
		private double getDiscount(){
			return this.discount;
		}
		public void getFinalOutput(){
			if(cardHolder)
		 		System.out.println("\t\tGas Advantage Discounts applied");
			System.out.printf("\t%s%39s\n","Gallons",String.format("%.2f", getGallons()));
			System.out.println("\t------------------------------------------------");
			System.out.printf("\t%s%31s\n","Cost Per Gallon",fmt.format(getFuelCost()));
			System.out.printf("\t%s%37s\n","Sub Total" ,fmt.format(getSubTotal()));
			System.out.println("\t------------------------------------------------");
			
			if(cardHolder){
				System.out.printf("\t%s%38s\n","Discount",fmt.format(getDiscount()));
				System.out.println("\t------------------------------------------------");
			}
				System.out.printf("\t%s%38s\n","Sub Cost",fmt.format(getTotalCost()));
		
		}
	}