package com.skb.ui;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.niuniu.skb_four.R;

@SuppressWarnings("deprecation")
public class UserDetail extends ActivityGroup {
	
	private ImageButton img_back;
	private LinearLayout work,collect,body;	
	private int flag=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.myself);
			initView();
			showView(flag);
			img_back.setOnClickListener(listener);
			work.setOnClickListener(listener);
			collect.setOnClickListener(listener);
	}

	private void showView(int flag) {
			View v=null;
		switch (flag) {
		case 0:
			body.removeAllViews();
			v=getLocalActivityManager().startActivity("work", new Intent(UserDetail.this,Works.class)).getDecorView();
			body.addView(v);
			break;
		case 1:
			body.removeAllViews();
			v=getLocalActivityManager().startActivity("collect", new Intent(UserDetail.this,Tab_collect.class)).getDecorView();
			body.addView(v);
			break;

		default:
			break;
		}
		
	}
	private OnClickListener listener = new OnClickListener() {


		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imageButton_back:
				UserDetail.this.finish();
				break;
			case R.id.linear_works:
				flag=0;
				showView(flag);
				break;
			case R.id.linear_collect:
				flag=1;
				showView(flag);
				break;
			default:
				break;
			}
		}
	};

	private void initView() {
			work=(LinearLayout) this.findViewById(R.id.linear_works);
			collect=(LinearLayout) this.findViewById(R.id.linear_collect);
			body=(LinearLayout) this.findViewById(R.id.body);
			img_back=(ImageButton) this.findViewById(R.id.imageButton_back);
	}
	
	
	
}
