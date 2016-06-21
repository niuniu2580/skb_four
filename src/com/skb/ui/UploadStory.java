package com.skb.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.niuniu.skb_four.R;

public class UploadStory extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_story);
		this.findViewById(R.id.imageButton_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					UploadStory.this.finish();
			}
		});
	}
}
