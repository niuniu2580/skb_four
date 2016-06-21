package com.skb.entity;

import android.graphics.Bitmap;

public class GridItem {

	private Bitmap ItemResId;
	private String foodMsg;
	
	
	public GridItem() {

	}
	public Bitmap getItemResId() {
		return ItemResId;
	}

	public void setItemResId(Bitmap itemResId) {
		ItemResId = itemResId;
	}
	public String getFoodMsg() {
		return foodMsg;
	}
	public void setFoodMsg(String foodMsg) {
		this.foodMsg = foodMsg;
	}
	
	
}
