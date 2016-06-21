package com.skb.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niuniu.skb_four.R;

public class SpicyFragment extends Fragment {

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View viewContent=inflater.inflate(R.layout.sort_title_la, container, false);
		return viewContent;
	}
}
