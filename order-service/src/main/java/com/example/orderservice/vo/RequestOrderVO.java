package com.example.orderservice.vo;

import jakarta.validation.constraints.NotNull;

public class RequestOrderVO {

	private String orderId;
	private String userId;

	@NotNull(message = "주문 상품이 누락되었습니다.")
	private String itemId;
	@NotNull(message = "주문 개수가 누락되었습니다.")
	private int itemOrderCount;
	@NotNull(message = "주문 상품 단가가 누락되었습니다.")
	private long itemUnitPrice;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getItemOrderCount() {
		return itemOrderCount;
	}

	public void setItemOrderCount(int itemOrderCount) {
		this.itemOrderCount = itemOrderCount;
	}

	public long getItemUnitPrice() {
		return itemUnitPrice;
	}

	public void setItemUnitPrice(long itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}

}
