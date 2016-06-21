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

public class MenuQueryAdapter extends BaseAdapter {

	Context context;
	private ArrayList<HashMap<String,String>> mList;
	HashMap<String,String> map;
	
	public  MenuQueryAdapter(Context context,ArrayList<HashMap<String,String>> list) {
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
		map=mList.get(position);
		if(convertView==null){
			holder=new ViewHolder();
			LayoutInflater inflater=LayoutInflater.from(context);
			convertView=inflater.inflate(R.layout.menu_elaberative, null);
			holder.name=(TextView) convertView.findViewById(R.id.food_name);
			holder.value=(TextView) convertView.findViewById(R.id.food_count);
			convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
		
		Set set=map.entrySet();
		Iterator it2=set.iterator();
		while(it2.hasNext()){
			Map.Entry<String, String> entry=(Map.Entry<String,String>) it2.next();
			String key=entry.getKey();
			String value=entry.getValue();
			holder.name.setText(key);
			holder.value.setText(value);
		}
		
		return convertView;
	}
	class ViewHolder{
		TextView name,value;
	}

}
