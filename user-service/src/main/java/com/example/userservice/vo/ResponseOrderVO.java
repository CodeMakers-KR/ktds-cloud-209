package com.example.userservice.vo;

public class ResponseOrderVO {

	private String orderId;
	private String itemId;
	private int itemOrderCount;
	private long itemUnitPrice;
	private long orderAmountPrice;
	private String userId;
	private String createAt;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public long getOrderAmountPrice() {
		return orderAmountPrice;
	}

	public void setOrderAmountPrice(long orderAmountPrice) {
		this.orderAmountPrice = orderAmountPrice;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

}
