package com.skb.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class HangoutViewPageAdapter extends PagerAdapter {

	private List<View> mViews;
	private Context context;

	public HangoutViewPageAdapter(Context context, List<View> views) {
		mViews = views;
		this.context = context;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
			position %=mViews.size();
			if(position<0){
				position=mViews.size()+position;
			}
		View view=mViews.get(position);
		ViewParent vp=view.getParent();
		if(vp!=null){
			ViewGroup parent=(ViewGroup) vp;
			parent.removeView(view);
		}
		container.addView(view);
		return view;
	}

	@Override
	public int getCount() {

		return mViews.size() * 100;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {

		return arg0 ==  arg1;
	}

}
