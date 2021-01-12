package com.techelevator.view;

import java.math.BigDecimal;

public class Candy extends Products {
	public int quantity = 5;
	private int sold = 0;

	public Candy(String nameOfSlot, String nameOfProduct, BigDecimal priceOfProduct) {
		super(nameOfSlot, nameOfProduct, priceOfProduct);

	}

	@Override
	public String sound() {
		return "Munch Munch, Yum!";
	}

	@Override
	public int getQuantityOfProduct() {
		return quantity - sold;
	}

	@Override
	public int getSoldQuantityOfProduct() {
		return sold++;
	}

}
