package com.skb.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.niuniu.skb_four.R;
import com.skb.adapter.MenuQueryAdapter;
import com.skb.adapter.MenuQueryMethodAdapter;
import com.skb.entity.FoodInfo;
import com.skb.utils.ListViewForScrollView;

public class QueryMessage extends Activity {

	TextView food_title;
	ImageView food_image;
	ImageButton backToHangout;
	String foodMsg;
	MenuQueryAdapter mainAdapter;
	MenuQueryAdapter secondAdapter;
	MenuQueryMethodAdapter methodAdapter;
	
	ListViewForScrollView mainFoodList;
	ListViewForScrollView secondFoodList;
	ListViewForScrollView foodMethodList;

	ArrayList<HashMap<String, String>> mainList = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> secondList = new ArrayList<HashMap<String, String>>();
	ArrayList<String> methodList=new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_item);
		showView();
		
		Intent intent = getIntent();
		foodMsg = intent.getStringExtra("foodMsg");
		
		mainAdapter=new MenuQueryAdapter(QueryMessage.this, mainList);
		secondAdapter=new MenuQueryAdapter(QueryMessage.this, secondList);
		methodAdapter=new MenuQueryMethodAdapter(QueryMessage.this, methodList);
		
		DownloadTask down=new DownloadTask(QueryMessage.this);
		down.execute(foodMsg);
	
	}

	public void showView() {
		food_title = (TextView) findViewById(R.id.textView_title);
		food_image = (ImageView) findViewById(R.id.grid_food_image);
		
		mainFoodList = (ListViewForScrollView) findViewById(R.id.mainFood);
		secondFoodList = (ListViewForScrollView) findViewById(R.id.secondFood);
		foodMethodList=(ListViewForScrollView) findViewById(R.id.menu_method);
		
		backToHangout = (ImageButton) findViewById(R.id.imageButton_back);
		backToHangout.setOnClickListener(listener);
	}

	class DownloadTask extends AsyncTask<String, Void, List<FoodInfo>> {

		Bitmap bitmap;
		String title;
		String mainFood;
		String secondFood;
		String method;
		
		
		List<String> mainFoodName = new ArrayList<String>();
		List<String> mainFoodCount = new ArrayList<String>();

		List<String> secondFoodName = new ArrayList<String>();
		List<String> secondFoodCount = new ArrayList<String>();

		ProgressDialog dialog;
		Context mContext;

		public DownloadTask(Context context) {
			this.mContext = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(mContext);
			dialog.setMessage("正在加载......");
			dialog.setCancelable(false);
			dialog.setIndeterminate(false);
			dialog.show();
		}

		@Override
		protected List<FoodInfo> doInBackground(String... params) {

			final List<FoodInfo> menu = new ArrayList<FoodInfo>();
			BmobQuery<FoodInfo> query = new BmobQuery<FoodInfo>();
			query.addWhereEqualTo("title", params[0]);
			query.findObjects(QueryMessage.this, new FindListener<FoodInfo>() {

				@Override
				public void onSuccess(List<FoodInfo> foodInfo) {
					Iterator<FoodInfo> it = foodInfo.iterator();
					while (it.hasNext()) {
						FoodInfo info = it.next();
						byte[] b = info.getFoodImg();
						bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
						title = info.getTitle();
						/**
						 * 对主料和辅料的list的设置
						 */
						mainFood = info.getFoodMain();
						Matcher matcherCount = Pattern.compile("\\，(.*?)\\。")
								.matcher(mainFood);
						while (matcherCount.find()) {
							mainFoodCount.add(matcherCount.group(1));
						}
						Matcher matcherName = Pattern.compile("\\。(.*?)\\，")
								.matcher(mainFood);
						while (matcherName.find()) {
							mainFoodName.add(matcherName.group(1));
						}

						for (int i = 0; i < mainFoodName.size(); i++) {
							HashMap<String, String> map = new HashMap<String, String>();
							if(i>=0 && i<mainFoodName.size()){
								map.put(mainFoodName.get(i), mainFoodCount.get(i));
								mainList.add(map);
							}
						}
						mainFoodList.setAdapter(mainAdapter);
						
						secondFood = info.getFoodSecond();
						Matcher matcherSecCount = Pattern
								.compile("\\，(.*?)\\。").matcher(secondFood);
						while (matcherSecCount.find()) {
							secondFoodCount.add(matcherSecCount.group(1));
						}
						Matcher matcherSecName = Pattern.compile("\\。(.*?)\\，")
								.matcher(secondFood);
						while (matcherSecName.find()) {
							secondFoodName.add(matcherSecName.group(1));
						}

						for (int i = 0; i < secondFoodCount.size(); i++) {
							HashMap<String, String> secMap = new HashMap<String, String>();
							if(i>=0 && i<secondFoodCount.size()){
								secMap.put(secondFoodName.get(i),
										secondFoodCount.get(i));
							}
							secondList.add(secMap);
						}
						secondFoodList.setAdapter(secondAdapter);

						/***
						 * 对制作方法进行处理
						 */
						method = info.getFoodMethod();
						String[] methodStr=method.split("#");
						for(int i=0; i < methodStr.length;i++){
							methodList.add(methodStr[i]);
						}
						foodMethodList.setAdapter(methodAdapter);
						
						
						food_title.setText(title);
						food_image.setImageBitmap(bitmap);
						menu.add(info);
						if (food_title != null) {
							dialog.dismiss();
						}
					}
				}

				@Override
				public void onError(int arg0, String arg1) {
					Toast.makeText(QueryMessage.this, "出错啦", Toast.LENGTH_SHORT)
							.show();
				}
			});

			return menu;
		}
	}

	public OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imageButton_back:
				QueryMessage.this.finish();
				break;

			default:
				break;
			}
		}
	};
}
