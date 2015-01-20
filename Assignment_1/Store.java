import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Class: CS-0401
 * Store.java
 * Purpose: Provide a fake customer exchange program
 *
 * @author Zach Ward
 * @version 1.0
 */

public class Store {
	static double regularFuel = 3.75;
	static double plusFuel = 4.00;
	static boolean cardHolder;
	static boolean newCardHolder;
	static double cardBalance;
	static Scanner sc;
	
	static ArrayList<Gasoline> gOrders = new ArrayList<Gasoline>();
	static ArrayList<Propane> pOrders = new ArrayList<Propane>();
	static ArrayList<Burrito> bOrders = new ArrayList<Burrito>();
	//Handles
	static NumberFormat fmt = NumberFormat.getCurrencyInstance();
	public static void main(String[] args) {
		sc = new Scanner(System.in);
		printHeader(0);
		String userInput;
		double numberInput = 0;
		boolean checkOut = false;
		boolean exitProgram = false;
		while(!exitProgram){
			//Resets values to initial settings
			checkOut = false;
			gOrders.clear();
			pOrders.clear();
			bOrders.clear();
			
			//Check  
			System.out.print("\nIs there a customer in line (Yes/No):\t");
			userInput = getInput("Yes","No");
			if(userInput.equalsIgnoreCase("No")){
				System.out.println("Thank you for shopping with us");
				System.exit(0);
			}
			System.out.print("Are you a card member (Yes/No):\t");
			userInput = getInput("Yes", "No");
			//Check name against records
			readMemberFile(userInput);	
			int ct = 0;
			while(!checkOut){
				printHeader(1);
				while(numberInput == 0){
				System.out.print("Please choose an option from the menu above (1-4): ");
				numberInput = getInput(4);
				sc.nextLine();
				}		
				if(numberInput == 1)
					runGas();
				if(numberInput == 2)
					runProp();
				if(numberInput == 3)
					runBur();
				if(numberInput == 4)
					if(ct > 0){
						runCheckOut();
						checkOut = true;
					}	
					else{
						System.out.print("You havn't placed any orders would you "
								+"like to exit the line? (Yes/No): ");
						userInput = getInput("yes", "no");
						if(userInput.equalsIgnoreCase("Yes"))
							break;
						else
							System.out.println("\n\t\tReturning to Menu...");
					}
				numberInput = 0;
				ct++;
			}
		}
		//End Program
		System.exit(0);
	}
	/**
	 * This method creates a Gasoline object based on the user's order preference.
	 * It allows to order in gallons or cash.
	 * 
	 * A subtotal is then calculated among other information useful for the final bill. 
	 * If the user is a card holder, discounts are applied.The method then prints a 
	 * mini receipt to the user before adding the new Gasoline object to an arrayList 
	 * of Gasoline objects called gOrders for future use. The method then returns control
	 * to the main method.
	 * 
	 * Empty Orders cancel the creation of the object and displays a window to the user
	 */
	private static void runGas(){
		printHeader(2);
		
		String userInput;
		String fuelType;
		double fuelCost;	
		double amount = 0;
		double gasSubTotal = 0;
		double gasTotal = 0;
		double discount = 0;
		
		//Gets orders preference
		System.out.print("Please choose order type, Gallons or Cash (Gallons/Cash): ");
		userInput = getInput("Gallons", "Cash");
		
		System.out.print("Plus Fuel ($" + (String.format("%.02f", plusFuel)) + "/gal) or Regular Fuel ($" 
				+ regularFuel + "/gal) (Plus / Regular): ");
		fuelType = getInput("Regular","Plus");
		//Determines the type of fuel the user would like
		if(fuelType.equalsIgnoreCase("Plus"))
			fuelCost = plusFuel;
		else
			fuelCost = regularFuel;
		//ORDER BY GALLONS
		
		if(userInput.equalsIgnoreCase("Gallons")){
			System.out.print("Enter the amount of gallons you want to buy (0 to Cancel): ");
			amount = getInput(-1);
			//Handles Empty Orders
			if(amount == 0)
				JOptionPane.showMessageDialog(null, "\t\tEmpty Order\nCanceling Request","Alert", 0);
			else{
				if(cardHolder){
					//CardHolder Discoutns for a Gas Order
					gasSubTotal = amount * fuelCost;
					int tier = (int) ((cardBalance) / 50);
					discount = (amount * (fuelCost + ((int)tier/10.0)) - gasSubTotal);
					gasTotal = gasSubTotal - discount;
				}
				else{
					//Not Card Holder
					gasSubTotal = amount * fuelCost;
					gasTotal = gasSubTotal;
				}
			}
		}
		else{
			//Collects the amount of cash the user wants to spend
			System.out.print("How much money do you want to spend on gas: $");
			gasSubTotal = getInput(-1);
			if(gasSubTotal == 0)
				JOptionPane.showMessageDialog(null, "\t\tEmpty Order\nCanceling Request","Alert", 0);
			else{
				amount = gasSubTotal / fuelCost;
				if(cardHolder){
					//CardHolder Discounts calculated for cash orders
					int tier = (int) ((cardBalance) / 50);
					discount = (amount * (fuelCost + ((int)tier/10.0)) - gasSubTotal);
					gasTotal = gasSubTotal - discount;
				}
				else{
					//Not CardHolder cash order
					gasSubTotal = amount * fuelCost;
					gasTotal = gasSubTotal;
				}
			}
		}
		//Display mini receipt
		System.out.println("\n\t-------Estimated Cost("+fuelType+" Fuel)---------");
		System.out.printf("\n\tGallons  %30s\n", String.format("%.2f", amount));
		System.out.println("\t-------------------------------------------");
		System.out.printf("\tCost Per Gallon%24s\n",fmt.format(fuelCost));
		System.out.printf("\tSub Total%30s\n", fmt.format(gasSubTotal));
		System.out.println("\t-------------------------------------------");
		
		if(cardHolder){
			//Only displays discount if the user is a cardHolder
			System.out.printf("\tDiscount %30s\n", fmt.format(discount));
			System.out.println("\t-------------------------------------------");
		}
		System.out.printf("\tTotal Cost%29s\n\n", fmt.format(gasTotal));
		gOrders.add(new Gasoline(cardHolder,fuelType,fuelCost,amount,gasSubTotal,discount, gasTotal));
	}
	/**
	 * This method creates a Propane object based on the user's order preference.
	 * Two subTotals are created for each type of tank. The method then prints a 
	 * mini receipt to the user before adding the new Propane object to an arrayList 
	 * of Propane objects called pOrders for future use. The method then returns control
	 * to the main method.
	 * 
	 * Empty Orders cancel the creation of the object and displays a window to the user
	 */
	private static void runProp(){
		printHeader(3);
		double numExchange = -1, numBought = -1;
		
		System.out.print("Number of tanks you want to exchange:\t");
		numExchange = getInput(-1);
		
		System.out.print("Number of tanks you want to purchase:\t");
		numBought = getInput(-1);
		if(!(numExchange == 0 && numBought == 0)){
			double exSubTotal, boughtSubTotal,combinedSubTotal, discount = 0, totalCost;	
			exSubTotal = numExchange * 20;
			boughtSubTotal = numBought * 35;
			combinedSubTotal = exSubTotal + boughtSubTotal;
			if(cardHolder)
				discount = combinedSubTotal * .10;
			totalCost = combinedSubTotal - discount;	
			
			System.out.println("\n\t-------------Estimated Cost----------------");
			if(numExchange > 0)
				System.out.printf("\n\tExchanged Tanks%10s%15s\n", numExchange, fmt.format(exSubTotal));
			if(numBought > 0)
				System.out.printf("\tPurchased Tanks%10s%15s\n", numBought, fmt.format(boughtSubTotal));
			System.out.printf("\tSub Total %30s\n",fmt.format(combinedSubTotal));
			System.out.println("\t-----------------------------------------------");
			if(cardHolder){
				System.out.printf("\tDiscounts %30s\n", fmt.format(discount));
				System.out.println("\t-----------------------------------------------");
			}
			System.out.printf("\tTotal Cost %29s\n\n",fmt.format(totalCost));
			pOrders.add(new Propane(cardHolder, numExchange, numBought, exSubTotal, boughtSubTotal, discount, totalCost));
		}	
		else
			JOptionPane.showMessageDialog(null, "\t\tEmpty Order\nCanceling Request","Alert", 0);
	}
	/**
	 * This method creates a Burrito object based on the user's order preference.
	 * Three subTotals are created for each cost tier in order for future
	 * output to the user. Card discounts are applied to the costs if the
	 * user is a card member. The method then prints a mini receipt to the user
	 * before adding the new burrito object to an arrayList of Burrito objects called
	 * bOrders for future use. The method then returns control to the main method.
	 * 
	 * Empty Orders cancel the creation of the object and displays a window to the user
	 */
	private static void runBur(){
		printHeader(4);
		int quantity,qt_1 = 0,qt_2 = 0,qt_3 = 0;
		double totalCost = 0,cost = 1,subTotal_1 = 0,subTotal_2 = 0, subTotal_3 = 0;
		System.out.print("\nEnter how many burritos you wish to purchase: ");
		quantity = (int) Math.round(getInput(-1));
		if(quantity <= 0)
			JOptionPane.showMessageDialog(null, "\t\tEmpty Order\nCanceling Request","Alert", 0);
		else{
		
			if(cardHolder)
				cost = .9;
			else
				cost = 1;
			
			int tier = (int) (quantity / 5); 
		 	int remain = (int) (quantity - (5 * tier));
		 	
		 	if(quantity <= 5){
		 		qt_1 = quantity;
		 		subTotal_1 = quantity * cost;
		 		totalCost += subTotal_1;
		 	}
		 	if(quantity > 5 && quantity <= 10){
		 		qt_1 = 5;
		 		qt_2 = remain;
		 		subTotal_1 = (5* cost);
		 		if(remain == 10)
		 			subTotal_2 = 5 *(cost -.1);
		 		else
		 			subTotal_2 = remain *(cost -.1);
		 		totalCost += (subTotal_1 + subTotal_2);
		 	}
		 	if(quantity > 10){
		 		qt_1 =5;
		 		qt_2 =5;
		 		qt_3 = (quantity - 10);
		 		subTotal_1 = (5* cost);
		 		subTotal_2 = 5 *(cost -.1);
		 		subTotal_3 = (quantity - 10) * (cost -.2);
		 		totalCost += (subTotal_1 + subTotal_2 + subTotal_3);		
		 	}	
		 	
		 	System.out.println("\n\t---------------Estimated Costs ---------------");
		 	if(cardHolder)
		 		System.out.println("\t\t-$.10 off normal Burrito Prices");
		 	System.out.printf("\n\t%-4s %15s%2s%20s\n", qt_1,"Burrito(s) for: ",fmt.format(cost),fmt.format(subTotal_1));
		 	if(qt_2 > 0)
		 		System.out.printf("\t%-4s %15s%2s%20s\n", qt_2,"Burrito(s) for: ",fmt.format(cost-.1),fmt.format(subTotal_2));
		 	if(qt_3 > 0)
		 		System.out.printf("\t%-4s %15s%2s%20s\n", qt_3,"Burrito(s) for: ",fmt.format(cost-.2),fmt.format(subTotal_3));
		 	System.out.println("\t-----------------------------------------------");
		 	System.out.printf("\t%-3s %35s\n\n","Total Cost",fmt.format(totalCost));
		 	
		 	bOrders.add(new Burrito(cardHolder,cost, qt_1, qt_2, qt_3, subTotal_1, subTotal_2, subTotal_3, totalCost));
		}
	}
	/**
	 * This method displays the final bill by printing out all of the objects in
	 * gOrders, pOrders, and bOrders. This allows the customer to avoid overwriting
	 * previous orders and place more than one order in each subject area. The output
	 * is done through System.out.printf to ensure the answers are well formatted when
	 * displayed to the user.
	 */
	private static void runCheckOut(){
		int numGOrders = gOrders.size();
		int numPOrders = pOrders.size();
		int numBOrders = bOrders.size();
		
		double gasTotal = 0;
		double propaneTotal = 0;
		double burritoTotal = 0;
		System.out.println("\t------------------------------------------------");
		System.out.println("\t\t\tOrder Numbers");
		System.out.println("\t------------------------------------------------");
		System.out.printf("\t%s%24s\n","Total Gas Orders     ",numGOrders);
		System.out.printf("\t%s%24s\n","Total Propane Order  ",numPOrders);
		System.out.printf("\t%s%24s\n","Total Burrito Orders ",numBOrders);
		System.out.println("\t------------------------------------------------");
		if(numGOrders != 0)
			for(int i = 0; i < gOrders.size(); i++){
				System.out.println("\t\t   Gas Order("+gOrders.get(i).getType()+"): "+(i+1));
				gOrders.get(i).getFinalOutput();
				gasTotal += gOrders.get(i).getTotalCost();
			}
		if(numPOrders != 0)
			for(int i = 0; i < pOrders.size(); i++){
				System.out.println("\t\t\tPropane Order: " + (i+1));
				pOrders.get(i).getFinalOutput();
				propaneTotal += pOrders.get(i).getTotalCost();
			}
		if(numBOrders != 0)
			for(int i = 0; i < bOrders.size(); i++){
				System.out.print("\t\t\tBurrito Order: " + (i+1));
				bOrders.get(i).getFinalOutput();
				burritoTotal += bOrders.get(i).getTotalCost();
			}
		
		System.out.println("\t------------------------------------------------");
		System.out.println("\t\t\t    Totals");
		System.out.println("\t------------------------------------------------");
		if(numGOrders != 0)
			System.out.printf("\t%s%29s\n", "Gas Total Cost   ",fmt.format(gasTotal));
		if(numPOrders != 0)
			System.out.printf("\t%s%28s\n","Propane Total Cost",fmt.format(propaneTotal));
		if(numBOrders != 0)
			System.out.printf("\t%s%28s\n","Burrito Total Cost",fmt.format(burritoTotal));
		
		System.out.println("\t------------------------------------------------");
		double preTaxCost = gasTotal + propaneTotal + burritoTotal;
		System.out.printf("\t%s%28s\n","Pre Tax Total Cost",fmt.format(preTaxCost));
		System.out.println("\t------------------------------------------------");
		
		double taxed,preTotalTax,finalCost;
		if(preTaxCost >= 50){
			double discount = preTaxCost * .10;
			System.out.printf("\t%s%34s\n","10% discount",fmt.format(discount));
			preTotalTax = (preTaxCost - discount);
			taxed = preTotalTax * .07;
			finalCost = (preTotalTax + taxed);
		}
		else{
			taxed = preTaxCost * .07;
			finalCost = taxed + preTaxCost;
		}
		System.out.printf("\t%s%37s\n","Total Tax",fmt.format(taxed));
		System.out.println("\t------------------------------------------------");
		System.out.printf("\t%s%36s\n", "Final Cost",fmt.format(finalCost));
		
	}
	/**
	 * This is an overloaded method designed to gather all user input that requires
	 * picking between two string responses. The method will not return unless 
	 * the methods's input variable is equal to either option1 or option2
	 * @param option1	Valid response used to match up against input
	 * @param option2	Valid response used to match up against input
	 * @return input	This will be equal to either option1 or option2
	 */
	private static String getInput(String option1, String option2){
		//Makes Sure the user chooses a valid choice
		String input = sc.nextLine();
		if(input.equals(""))
			input = sc.nextLine();
		while(!(input.equalsIgnoreCase(option1) || input.equalsIgnoreCase(option2))){
			System.out.print("Enter a valid response  (" + option1 + "/" 
					+ option2 +"): "); 
			input = sc.nextLine();
		}
		return input;
	}
	/**
	 * This is an overloaded method designed to gather all numerical input.
	 * It forces the user to enter a positive number and will prevent the user
	 * from entering values that will crash the program such as strings.
	 * 
	 * @param limit		Used to set the limit on acceptable user input.
	 * 					If set to -1 there is no limit placed on the user's
	 * 					Input as long as its positive
	 * @return input	positive variable collected from user 
	 * 					that is less that or equal to the limit reference
	 * 					variable
	 */
	private static double getInput(int limit){
	    int ct = 0; 
		double input = 0;
	    do{
	    	//Tells the user to enter a positive number
	    	if(ct > 0){
	    		System.out.print("Please enter a valid positive number: ");
	    		sc.nextLine();
	    	}
	    	//Tells the user that this input does not handle letters
	    	while (!(sc.hasNextDouble())) {
		        System.out.print("Please enter only numbers: ");
		        sc.nextLine();
	    	}
	    	input = sc.nextDouble();
	    	//Makes sure user entry is in the valid range
	    	//If limit = -1 it means their is no limit
	    	if(limit != -1)
	    		while((input > limit && (input >= 0)) && ct >= 0){
	    			System.out.print("Invalid Range, Please Re-enter: ");
	    			sc.nextLine();
	    			try{
	    			input = sc.nextDouble();
	    			}catch(Exception e){
	    				System.out.print("\tThat was not a number, ");
	    			}
	    			ct = 0;
	    		}
	    	ct++;
	    }while(input < 0);
	    return input;
	}
	/**
	 * This method searches the CARD_HOLDERS.txt to check
	 * if the User's inputed name matches any of those found
	 * in the text file. If the name matches the global cardBalance 
	 * variable is updated to match the balance listed in the text
	 * file. It will then set the global boolean cardHolder to true
	 * 
	 * If the search fails it asks the user if they would like
	 * to become a cardUser at which point they can either accept
	 * or deny. Denying will return control back to the main method.
	 * 
	 * If they accept their name and balance is collected and added to
	 * the CARD_HOLDERS.txt file
	 * 
	 *
	 * @param userInput User's name, used to match
	 * 					up against current card holder list
	 */
	private static void readMemberFile(String userInput){

		String userName, searchName;
		if(userInput.equalsIgnoreCase("Yes")){
			System.out.print("Please enter your full name: ");
			userName = sc.nextLine();
			searchName = userName.toUpperCase();
			try{
				BufferedReader br = new BufferedReader(new FileReader("CARD_HOLDERS.txt"));
				String textLine;
				while((textLine = br.readLine()) != null){
					int indexfound = textLine.indexOf(searchName);
					if (indexfound > -1) {
						String[] temp = textLine.split(" ");
						cardHolder = true;
						cardBalance = Double.parseDouble(temp[temp.length - 1]);
						System.out.println("\n\t\tWelcome back " + userName + "!");
						System.out.println("\n\tCurrent Balance: "  + fmt.format(cardBalance));
						System.out.println("\tCard Status: Active\n");
						
						int tier = (int)(cardBalance/50);
						plusFuel = plusFuel - ((int)tier/10.0);
						regularFuel = regularFuel - ((int)tier/10.0);
						
					}
				}br.close();	
			}catch(Exception e){
				System.out.println("There was an error searching our records");	
			}
			if(!cardHolder){
				System.out.print("\nSorry " + userName 
						+", you did not appear on our list of card holders\n");
				System.out.print("Would you like to become a card member (Yes/No): ");
				userInput = getInput("Yes", "No");
				if(userInput.equalsIgnoreCase("Yes")){
					System.out.print("How much money would you like to put on your new card: $");
					cardBalance = getInput(1875); //Limit Placed so negative is not possible
					newCardHolder = true;
					try {
						FileWriter fileWritter = new FileWriter("CARD_HOLDERS.txt",true);
		    	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		    	        bufferWritter.write("\n"+searchName +" "+ cardBalance);
		    	        bufferWritter.close();
		    	        
		    	        System.out.println("\n\t\tWelcome " + userName + "!");
						System.out.println("\n\tCurrent Balance: "  + fmt.format(cardBalance));
						System.out.println("\tCard Status: New\n");
						int tier = (int)(cardBalance/50);
						plusFuel = plusFuel - ((int)tier/10.0);
						regularFuel = regularFuel - ((int)tier/10.0);
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				else
					System.out.println("\n\t\t\tOpening Main Menu . . .\n");
			}
		}
		
	}
	/**
	 * Prints the headers and menus for the duration of the
	 * program based on what integer into the reference variable i
	 * 
	 * This method also handles whether or not to display
	 * the card advantage user menu depending on if the
	 * boolean variable is true or not
	 * 
	 * @param  i 	the argument that determines what headers are displayed
	 */
	private static void printHeader(int i) {
		if(i == 0){
			System.out.println("_____________________------Welcome To----_________________________");
			System.out.println("|||   ___   __   ____     ___   __   ____     ___   __   ____ 	|||");
			System.out.println("|||  / __) / _\\ / ___)   / __) / _\\ / ___)   / __) / _\\ / ___)	|||");
			System.out.println("||| ( (_ \\/    \\\\___ \\  ( (_ \\/    \\\\___ \\  ( (_ \\/    \\\\___ \\	|||");
			System.out.println("|||  \\___/\\_/\\_/(____/   \\___/\\_/\\_/(____/   \\___/\\_/\\_/(____/	|||");
			System.out.println("_____________________--------------------_________________________");
		}
		if(i == 1){
			System.out.println("   -------------------_________________________------------------");
			System.out.println("|||         ___  ___      _        ___  ___                       |||");
			System.out.println("|||         |  \\/  |     (_)       |  \\/  |                       |||");
			System.out.println("|||         | .  . | __ _ _ _ __   | .  . | ___ _ __  _   _       |||");
			System.out.println("|||         | |\\/| |/ _` | | '_ \\  | |\\/| |/ _ \\ '_ \\| | | |      |||");
			System.out.println("|||         | |  | | (_| | | | | | | |  | |  __/ | | | |_| |      |||");
			System.out.println("|||         \\_|  |_/\\__,_|_|_| |_| \\_|  |_/\\___|_| |_|\\__,_|      |||");
			System.out.println("   -------------------_________________________------------------");
			if(cardHolder){
				System.out.println("|||                  CARD HOLDER DISCOUNTS                        |||");
				System.out.println("|||      10¢ off every gallon for every $50.00 on card            |||");
				System.out.println("|||      10% discount on all propanr purchases                    |||");
				System.out.println("|||      $0.10/ea discount on burritos                            |||");
				System.out.println("   -------------------_________________________------------------");
			}
			System.out.println("||| 1. Buy Gasoline: $"+(String.format("%.02f", regularFuel)) +"/gal. reg., $"
				+ (String.format("%.02f", plusFuel))+"/gal. plus             |||");
			System.out.println("||| 2. Buy Propane : $20.00/20lb with tank, $35.00 without tank   |||");
			System.out.println("||| 3. Buy Burritos: $1.00/each [with volume discounts]           |||");
			System.out.println("||| 4. Check Out                                                  |||");
			System.out.println("|||      Note: Will only read first number entered                |||");
			System.out.println("--------------------_________________________--------------------");
		}
		if(i == 2){
			System.out.println("\n\n   -------------------_________________________------------------");
			System.out.println("|||           _____                 _ _                           |||");
			System.out.println("|||          |  __ \\               | (_)                          |||");
			System.out.println("|||          | |  \\/ __ _ ___  ___ | |_ _ __   ___                |||");
			System.out.println("|||          | | __ / _` / __|/ _ \\| | | '_ \\ / _ \\               |||");
			System.out.println("|||          | |_\\ \\ (_| \\__ \\ (_) | | | | | |  __/               |||");
			System.out.println("|||           \\____/\\__,_|___/\\___/|_|_|_| |_|\\___|               |||");
			System.out.println("   -------------------_________________________------------------");
		}
		if(i == 3){
			System.out.println("\n\n   -------------------_________________________------------------");
			System.out.println("|||             ___                                               |||");
			System.out.println("|||            / _ \\_ __ ___  _ __   __ _ _ __   ___              |||");
			System.out.println("|||           / /_)/ '__/ _ \\| '_ \\ / _` | '_ \\ / _ \\             |||");
			System.out.println("|||          / ___/| | | (_) | |_) | (_| | | | |  __/             |||");
			System.out.println("|||          \\/    |_|  \\___/| .__/ \\__,_|_| |_|\\___|             |||");
			System.out.println("|||                        |_|                                    |||");
			System.out.println("   -------------------_________________________------------------\n");
		}
		if(i == 4){
			System.out.println("\n\n   -------------------_________________________------------------");
			System.out.println("|||            ______                 _ _                         |||");
			System.out.println("|||            | ___ \\               (_) |                        |||");
			System.out.println("|||            | |_/ /_   _ _ __ _ __ _| |_ ___                   |||");
			System.out.println("|||            | ___ \\ | | | '__| '__| | __/ _ \\                  |||");
			System.out.println("|||            | |_/ / |_| | |  | |  | | || (_) |                 |||");
			System.out.println("|||            \\____/ \\__,_|_|  |_|  |_|\\__\\___/                  |||");
			System.out.println("   -------------------_________________________------------------");
		}
		
	}

}
