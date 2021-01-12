package com.techelevator.view;

import java.math.BigDecimal;

public abstract class Products {

	private String nameOfSlot;
	public String nameOfProduct;
	private BigDecimal priceOfProduct;

	public Products(String nameOfSlot, String nameOfProduct, BigDecimal priceOfProduct) {
		this.nameOfSlot = nameOfSlot;
		this.nameOfProduct = nameOfProduct;
		this.priceOfProduct = priceOfProduct;
	}

	public String getNameOfSlot() {
		return nameOfSlot;
	}

	public String getNameOfProduct() {
		return nameOfProduct;
	}

	public BigDecimal getPriceOfProduct() {
		return priceOfProduct;
	}

	public abstract String sound();
	public abstract int getSoldQuantityOfProduct();

	public abstract int getQuantityOfProduct();

	public boolean soldOut() {

		return getQuantityOfProduct() == 0;
	}
}
