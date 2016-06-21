package com.skb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.niuniu.skb_four.R;
import com.skb.ui.UploadMenu;
import com.skb.ui.UploadStory;
import com.skb.ui.UserDetail;

public class MyFragment extends android.support.v4.app.Fragment {

	private LinearLayout user;
	private LinearLayout menu,story;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View viewContent = inflater.inflate(R.layout.my, container, false);
		user = (LinearLayout) viewContent.findViewById(R.id.linear_user);
		menu=(LinearLayout)viewContent. findViewById(R.id.linear_uploadMenu);
		story=(LinearLayout) viewContent.findViewById(R.id.linear_uploadStory);
		
		
		user.setOnClickListener(listener);

		menu.setOnClickListener(listener);
		story.setOnClickListener(listener);
		return viewContent;
	}

	private OnClickListener listener = new OnClickListener() {

		Intent intent = null;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.linear_user:
				intent = new Intent(getActivity(), UserDetail.class);
				startActivity(intent);
				break;
			case R.id.linear_uploadMenu:
				intent=new Intent(getActivity(),UploadMenu.class);
				startActivity(intent);
			break;
			case R.id.linear_uploadStory:
				intent=new Intent(getActivity(),UploadStory.class);
				startActivity(intent);
			break;
			default:
				break;
			}
		}
	};
}
