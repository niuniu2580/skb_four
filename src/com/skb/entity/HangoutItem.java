package com.skb.entity;

import cn.bmob.v3.BmobObject;

public class HangoutItem extends BmobObject {
	
		private String title;
		
		private FoodInfo recipe1;
		private FoodInfo recipe2;
		private FoodInfo recipe3;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public FoodInfo getRecipe1() {
			return recipe1;
		}
		public void setRecipe1(FoodInfo recipe1) {
			this.recipe1 = recipe1;
		}
		public FoodInfo getRecipe2() {
			return recipe2;
		}
		public void setRecipe2(FoodInfo recipe2) {
			this.recipe2 = recipe2;
		}
		public FoodInfo getRecipe3() {
			return recipe3;
		}
		public void setRecipe3(FoodInfo recipe3) {
			this.recipe3 = recipe3;
		}
		
	
	
}
