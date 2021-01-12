package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import com.techelevator.view.Candy;
import com.techelevator.view.Chip;
import com.techelevator.view.Drink;
import com.techelevator.view.Gum;
import com.techelevator.view.Menu;
import com.techelevator.view.Products;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_OPTION_EXIT };
	private static final String PURCHASE_MENU_INSERT_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_SELECT_ITEM = "Select Item";
	private static final String PURCHASE_MENU_FINISH = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_INSERT_MONEY, PURCHASE_MENU_SELECT_ITEM,
			PURCHASE_MENU_FINISH };

	private static List<Products> products = new ArrayList<>();
	private static List<String> printOut = new ArrayList<>();
	private Menu menu;
	BigDecimal zero = new BigDecimal("0");
	private BigDecimal money = new BigDecimal("0");
	BigDecimal quarter = new BigDecimal("0.25");
	BigDecimal dime = new BigDecimal("0.10");
	BigDecimal nickel = new BigDecimal("0.05");
	int quarterCount = 0;
	int dimeCount = 0;
	int nickelCount = 0;
	private static PrintWriter writer;


	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() { // THINK OF THIS AS THE MAIN METHOD
		money = money.setScale(2);
		Scanner in = new Scanner(System.in);
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				displayItems();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				while (true) {
					System.out.println("\nYour current balance is: $" + money);
					String purchaseMenuChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

					if (purchaseMenuChoice.equals(PURCHASE_MENU_INSERT_MONEY)) {
						insertMoneyIntoVendingMachine();
					} else if (purchaseMenuChoice.equals(PURCHASE_MENU_SELECT_ITEM)) {
						displayItems();
						chooseItem();
					} else {
						makeChange();
						break;
					}
				}
			} else {
				System.out.println("Thanks!");
				for(String print: printOut) {
					writer.print(print);
				}
				writer.flush();
				writer.close();
				System.exit(1);
			}
		}
	}

	public void makeChange() {
		String timeStamp = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(Calendar.getInstance().getTime());
		printOut.add(" >" + timeStamp + " GIVE CHANGE: $" + money);
		while (money.compareTo(zero) > 0) {

			if (money.compareTo(quarter) > -1) {
				money = money.subtract(quarter);
				quarterCount++;
			} else if (money.compareTo(dime) > -1) {
				money = money.subtract(dime);
				dimeCount++;
			} else {
				money = money.subtract(nickel);
				nickelCount++;
			}
		}
		printOut.add(" $" + money + " >");
		System.out.println("Your Change is: " + quarterCount + " quarter(s) " + dimeCount + " dime(s) " + nickelCount
				+ " nickel(s)");
	}

	public void displayItems() {
		for (Products product : products) {
			String name = product.getNameOfProduct();
			String id = product.getNameOfSlot();
			BigDecimal price = product.getPriceOfProduct();
			int quantity = product.getQuantityOfProduct();
			System.out.printf("%-22s%-22s%-22s%-22s\n", id, name, price, quantity);
		}
	}

	public void chooseItem() {
		Scanner in = new Scanner(System.in);
		String id = "";
		boolean found = false;
		id = in.nextLine();
		for (Products product : products) {
			if (id.equals(product.getNameOfSlot())) {
				if (product.soldOut()) {
					System.out.println(id + " " + product.getNameOfProduct() + " Sold Out!");
				} else if (money.compareTo(product.getPriceOfProduct()) >= 0) {
					found = true;
					String timeStamp = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a")
							.format(Calendar.getInstance().getTime());
					printOut.add(" >" + timeStamp + product.getNameOfProduct() + " " + product.getNameOfSlot() + " $"
							+ money);
					money = money.subtract(product.getPriceOfProduct());
					System.out.println(product.getNameOfProduct() + " ||" + " Price: $" + product.getPriceOfProduct()
							+ " ||" + " Balance: $" + money + " || " + product.sound());
					printOut.add(" $" + money);
					product.getSoldQuantityOfProduct();
				}
			}
		}
		if (!found) {
			System.out.print("Invalid Entry");
		}

	}

	public void insertMoneyIntoVendingMachine() {
		String timeStamp = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(Calendar.getInstance().getTime());
		System.out.println("Insert Money: $1, $2, $5, $10");
		Scanner in = new Scanner(System.in);
		String moneyInsertedStr;
		double moneyInserted;
		moneyInsertedStr = in.nextLine();
		moneyInserted = Double.parseDouble(moneyInsertedStr);
		if (moneyInserted == 10 || moneyInserted == 5 || moneyInserted == 2 || moneyInserted == 1) {
			money = money.add(new BigDecimal(moneyInsertedStr));
			printOut.add(">" + timeStamp + " FEED MONEY: $" + moneyInsertedStr + " $" + money);
	
		} else {
			System.out.println("Machine Only Accepts: $10, $5, $2, $1");
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("vendingmachine.csv");
		File log = new File("Log.txt");
		writer = new PrintWriter(log);


		try (Scanner scan = new Scanner(file)) {
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				String[] arrayOfProduct = line.split("\\|");

				String nameOfSlot = arrayOfProduct[0];
				String nameOfProduct = arrayOfProduct[1];
				BigDecimal priceOfProduct = new BigDecimal(arrayOfProduct[2]);
				String nameOfClass = arrayOfProduct[3];
				switch (nameOfClass) {
				case "Gum":
					products.add(new Gum(nameOfSlot, nameOfProduct, priceOfProduct));
					break;
				case "Candy":
					products.add(new Candy(nameOfSlot, nameOfProduct, priceOfProduct));
					break;
				case "Drink":
					products.add(new Drink(nameOfSlot, nameOfProduct, priceOfProduct));
					break;
				case "Chip":
					products.add(new Chip(nameOfSlot, nameOfProduct, priceOfProduct));
					break;
				}

			}
		} catch (Exception e) {
			System.out.println("Error: Cannot Complete Transaction At This Time!");
		}		
		

		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
