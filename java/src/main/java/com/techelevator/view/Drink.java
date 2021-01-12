package com.techelevator.view;

import java.math.BigDecimal;

public class Drink extends Products {
	public int quantity = 5;
	private int sold = 0;

	public Drink(String nameOfSlot, String nameOfProduct, BigDecimal priceOfProduct) {
		super(nameOfSlot, nameOfProduct, priceOfProduct);

	}

	@Override
	public String sound() {
		return "Glug Glug, Yum!";
	}

	@Override
	public int getQuantityOfProduct() {

		return quantity-sold;
	}


	@Override
	public int getSoldQuantityOfProduct() {
		return sold++;
	}
}
