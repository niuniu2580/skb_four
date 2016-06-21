package com.skb.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainAdapter extends FragmentPagerAdapter{
	
	List<Fragment> mList=new ArrayList<Fragment>();
	public MainAdapter(FragmentManager fm,List<Fragment> list) {
		super(fm);
		this.mList=list;
	}

	@Override
	public Fragment getItem(int arg0) {
		
		return mList.get(arg0);
	}

	@Override
	public int getCount() {
		
		return mList.size();
	}
	
}
