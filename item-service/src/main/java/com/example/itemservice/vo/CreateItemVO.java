package com.example.itemservice.vo;

import jakarta.validation.constraints.NotNull;

public class CreateItemVO {

	private String itemId;
	
	@NotNull(message = "상품명을 입력해주세요.")
	private String itemName;
	
	private long stock;
	private long unitPrice;

	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	public long getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(long unitPrice) {
		this.unitPrice = unitPrice;
	}

}
