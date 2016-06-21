package com.skb.fragment;


import com.niuniu.skb_four.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CollectFragment extends android.support.v4.app.Fragment {
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View viewContent=inflater.inflate(R.layout.collection, container,false);
		
		return viewContent;
	}
}
