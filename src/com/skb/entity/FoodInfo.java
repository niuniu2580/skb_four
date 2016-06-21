package com.skb.entity;

import cn.bmob.v3.BmobObject;

import com.bmob.BmobProFile;

public class FoodInfo extends BmobObject{
	
		private String title;
		private String sort;
		private String time;
		private String foodMain;
		private String foodSecond;
		private String foodMethod;
		private byte[] foodImg;
		
		public String getSort() {
			return sort;
		}
		public void setSort(String sort) {
			this.sort = sort;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		
		public String getFoodMain() {
			return foodMain;
		}
		public void setFoodMain(String foodMain) {
			this.foodMain = foodMain;
		}
		public String getFoodSecond() {
			return foodSecond;
		}
		public void setFoodSecond(String foodSecond) {
			this.foodSecond = foodSecond;
		}
		public String getFoodMethod() {
			return foodMethod;
		}
		public void setFoodMethod(String foodMethod) {
			this.foodMethod = foodMethod;
		}
		public byte[] getFoodImg() {
			return foodImg;
		}
		public void setFoodImg(byte[] foodImg) {
			this.foodImg = foodImg;
		}
		
		
		
}


