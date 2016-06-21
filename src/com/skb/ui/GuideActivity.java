package com.skb.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.niuniu.skb_four.R;

public class GuideActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide);	
	}
		
	public void clickToApp(View view){
		Intent intent=new Intent(GuideActivity.this,MainActivity.class);
		GuideActivity.this.startActivity(intent);
		GuideActivity.this.finish();
	}


}
