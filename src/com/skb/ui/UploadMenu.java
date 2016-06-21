package com.skb.ui;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;

import com.niuniu.skb_four.R;
import com.skb.adapter.ConListAdapter;
import com.skb.adapter.FoodListAdapter;
import com.skb.adapter.MethodListAdapter;
import com.skb.entity.CondimentMsg;
import com.skb.entity.FoodInfo;
import com.skb.entity.FoodMethod;
import com.skb.entity.FoodMsg;
import com.skb.entity.PhotoModel;
import com.skb.utils.ListViewForScrollView;

public class UploadMenu extends Activity {

	private String[] sorts = { "酸", "甜", "苦", "辣", "咸" };
	private String[] times = { "1-10分钟", "11-20分钟", "21-30分钟", "30分钟以上" };
	private Button setSort, setTime, addFood, addCon, addMethod, upload;
	private EditText name;
	private ImageView img_switch;
	private Bitmap bitmap;

	FoodMsg food = new FoodMsg();
	CondimentMsg condi = new CondimentMsg();
	FoodMethod method = new FoodMethod();

	ListViewForScrollView fList, conList, methodList;
	List<FoodMsg> foodList = new ArrayList<FoodMsg>();
	List<CondimentMsg> condimentList = new ArrayList<CondimentMsg>();
	List<FoodMethod> foodMethodList = new ArrayList<FoodMethod>();

	FoodListAdapter foodAdapter;
	ConListAdapter conAdapter;
	MethodListAdapter methodAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_menu);
		initView();
		
		Bmob.initialize(this, "8774785dac79711f4f8a914efce9a4bf");
		BmobInstallation.getCurrentInstallation(this).save();
	}
	


	private void initView() {
		setSort = (Button) findViewById(R.id.sort);
		setTime = (Button) findViewById(R.id.Time);
		addFood = (Button) findViewById(R.id.addFood);
		addCon = (Button) findViewById(R.id.addCondiment);
		addMethod = (Button) findViewById(R.id.addMethod);
		upload = (Button) findViewById(R.id.upload);
		name = (EditText) findViewById(R.id.name);
		img_switch = (ImageView) findViewById(R.id.imageSwitch);

		showList(foodList);
		setSort.setOnClickListener(listener);
		setTime.setOnClickListener(listener);
		addFood.setOnClickListener(listener);
		addCon.setOnClickListener(listener);
		addMethod.setOnClickListener(listener);
		upload.setOnClickListener(listener);
		img_switch.setOnClickListener(listener);
	}
	

	private void showList(List<FoodMsg> foods) {

		if (foodAdapter == null) {
			fList = (ListViewForScrollView) findViewById(R.id.food_List);
			foodAdapter = new FoodListAdapter(this, foods);
			fList.setAdapter(foodAdapter);
		} else {
			foodAdapter.onDataChanged(foods);
		}
	}

	private void showConList(List<CondimentMsg> foods) {
		if (conAdapter == null) {
			conList = (ListViewForScrollView) findViewById(R.id.condiment_List);
			conAdapter = new ConListAdapter(this, foods);
			conList.setAdapter(conAdapter);
		} else {
			conAdapter.onConDataChanged(foods);
		}
	}

	private void showMethodList(List<FoodMethod> foods) {
		if (methodAdapter == null) {
			methodList = (ListViewForScrollView) findViewById(R.id.method_List);
			methodAdapter = new MethodListAdapter(this, foods);
			methodList.setAdapter(methodAdapter);
		} else {
			methodAdapter.onMethodChanged(foods);
		}
	}

	OnClickListener listener = new OnClickListener() {
		Intent intent;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.sort:
				AlertDialog.Builder sort = new AlertDialog.Builder(
						UploadMenu.this);
				sort.setTitle("选择一个类别");

				sort.setItems(sorts, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						setSort.setText(sorts[which]);
					}
				});
				sort.create().show();
				break;
			case R.id.Time:
				AlertDialog.Builder time = new AlertDialog.Builder(
						UploadMenu.this);
				time.setTitle("选择时间");
				time.setItems(times, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						setTime.setText(times[which]);
					}
				});
				time.create().show();
				break;
			case R.id.addFood:
				intent = new Intent(UploadMenu.this, AddFood.class);
				startActivityForResult(intent, 1000);
				break;

			case R.id.addCondiment:
				intent = new Intent(UploadMenu.this, AddCondiment.class);
				startActivityForResult(intent, 2000);
				break;

			case R.id.addMethod:
				intent = new Intent(UploadMenu.this, AddMethod.class);
				startActivityForResult(intent, 3000);
				break;

			case R.id.upload:
				uploadMenu();
				Toast.makeText(UploadMenu.this, "发表成功！", Toast.LENGTH_SHORT)
						.show();
				UploadMenu.this.finish();
				break;
			case R.id.imageButton_back:
				UploadMenu.this.finish();
				break;
			case R.id.imageSwitch:
				intent = new Intent(UploadMenu.this,
						PhotoSelectorActivity.class);
				startActivityForResult(intent, 4000);
				break;

			default:
				break;
			}
		}
	};

	private void uploadMenu() {
		FoodInfo menu = new FoodInfo();

		menu.setTitle(name.getText().toString().trim());
		menu.setSort(setSort.getText().toString().trim());
		menu.setTime(setTime.getText().toString().trim());

		Iterator<FoodMsg> foodLists = foodList.iterator();
		String matrial1 = null;
		StringBuffer matrialFood = new StringBuffer();
		while (foodLists.hasNext()) {
			FoodMsg food = foodLists.next();
			String name = food.getFoodName();
			String much = food.getFoodMuch();
			matrial1 = "。" + name + "，" + much ;
			matrialFood.append(matrial1);
		}
		menu.setFoodMain(matrialFood.toString());
		
		Iterator<CondimentMsg> condimentLists = condimentList.iterator();
		String matrial2 = null;
		StringBuffer matrialCondiment = new StringBuffer();
		while (condimentLists.hasNext()) {
			CondimentMsg condiment = condimentLists.next();
			String name = condiment.getCondiName();
			String much = condiment.getCondiMuch();
			matrial2 = "。" + name + "，" + much;
			matrialCondiment.append(matrial2);
		}
		menu.setFoodSecond(matrialCondiment.toString());
		
		Iterator<FoodMethod> menuLists = foodMethodList.iterator();
		String matrial3 = null;
		StringBuffer matrialMethod = new StringBuffer();
		while (menuLists.hasNext()) {
			FoodMethod method = menuLists.next();
			String foodMethod = method.getFoodMehod();
			matrial3 = foodMethod + "#";
			matrialMethod.append(matrial3);
		}
		menu.setFoodMethod(matrialMethod.toString());
		
		menu.setFoodImg(bitmap2Stream(bitmap));
		menu.save(UploadMenu.this);
	}

	
	/**
	 * 将本地的图片转换成字节流
	 * 
	 * @param resId
	 * @return
	 */

	public byte[] bitmap2Stream(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 bitmap.compress(Bitmap.CompressFormat.JPEG, 85, baos);
		return baos.toByteArray();
	}
	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 1000 && resultCode == 1001) {
			String name = intent.getStringExtra("name");
			String much = intent.getStringExtra("much");
			FoodMsg food = new FoodMsg();
			food.setFoodName(name);
			food.setFoodMuch(much);
			foodList.add(food);

			showList(foodList);

		}
		if (requestCode == 2000 && resultCode == 2001) {
			String name = intent.getStringExtra("name");
			String much = intent.getStringExtra("much");

			CondimentMsg condi = new CondimentMsg();
			condi.setCondiName(name);
			condi.setCondiMuch(much);
			condimentList.add(condi);

			showConList(condimentList);

		}

		if (requestCode == 3000 && resultCode == 3001) {
			String method = intent.getStringExtra("method");
			FoodMethod fMethod = new FoodMethod();
			fMethod.setFoodMehod(method);
			foodMethodList.add(fMethod);
			showMethodList(foodMethodList);
		}

		if (requestCode == 4000 && resultCode == RESULT_OK) {
			ArrayList<PhotoModel> list = new ArrayList<PhotoModel>();
			Bundle bundle = intent.getExtras();
			list = (ArrayList<PhotoModel>) bundle.getSerializable("photos");
			Iterator<PhotoModel> it = list.iterator();
			while (it.hasNext()) {
				PhotoModel model = it.next();
				bitmap = BitmapFactory.decodeFile(model
						.getOriginalPath());
				img_switch.setImageBitmap(bitmap);
			}

		}
	}

}
