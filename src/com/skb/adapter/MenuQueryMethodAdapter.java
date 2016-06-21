package com.skb.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.niuniu.skb_four.R;

public class MenuQueryMethodAdapter extends BaseAdapter {

	Context context;
	private ArrayList<String> mList;
	
	
	public  MenuQueryMethodAdapter(Context context,ArrayList<String> list) {
			this.context=context;
			this.mList=list;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		String item=mList.get(position);
		if(convertView==null){
			holder=new ViewHolder();
			LayoutInflater inflater=LayoutInflater.from(context);
			convertView=inflater.inflate(R.layout.method_elaberative, null);
			holder.method_item=(TextView) convertView.findViewById(R.id.food_method_item);
			convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
		holder.method_item.setText(item);
		
		
		return convertView;
	}
	class ViewHolder{
		TextView method_item;
	}

}
