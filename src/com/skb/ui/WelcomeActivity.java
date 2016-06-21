package com.skb.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;

import com.niuniu.skb_four.R;

public class WelcomeActivity extends Activity {

	private int TIME = 2000;
	private final int GO_HOME = 3000;
	private final int GO_GUIDE = 2000;
	private boolean isFirst = false;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case GO_GUIDE:
				goGuide();
				break;

			case GO_HOME:
				goHome();
				break;

			default:
				break;
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		init();
	}

	private void init() {
		SharedPreferences sp = getSharedPreferences("welcome", MODE_PRIVATE);
		isFirst = sp.getBoolean("isFirst", true);
		if (!isFirst) {
			handler.sendEmptyMessageDelayed(GO_HOME, TIME);
		} else {
			handler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
			Editor editor = sp.edit();
			editor.putBoolean("isFirst", false);
			editor.commit();
		}
	}

	private void goHome() {
		Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
		WelcomeActivity.this.startActivity(intent);
		WelcomeActivity.this.finish();
	}

	private void goGuide() {
		Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
		WelcomeActivity.this.startActivity(intent);
		WelcomeActivity.this.finish();
	}

}
