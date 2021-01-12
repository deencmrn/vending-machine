package com.techelevator.view;

import java.math.BigDecimal;

public class Chip extends Products {
	public int quantity = 5;
	private int sold = 0;
	public Chip(String nameOfSlot, String nameOfProduct, BigDecimal priceOfProduct) {
		super(nameOfSlot, nameOfProduct, priceOfProduct);

	}

	@Override
	public String sound() {
		return "Crunch Crunch, Yum!";
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
