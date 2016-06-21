package com.skb.fragment;

import com.niuniu.skb_four.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MoreFragment extends Fragment {

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View viewContent=inflater.inflate(R.layout.sort_title_more, container, false);
		return viewContent;
	}
}
