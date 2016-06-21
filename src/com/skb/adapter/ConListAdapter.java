package com.skb.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.niuniu.skb_four.R;
import com.skb.entity.CondimentMsg;

public class ConListAdapter extends BaseAdapter {

			List<CondimentMsg> mList;
			LayoutInflater inflater;
			
			public ConListAdapter(Context context,List<CondimentMsg> list) {
				this.mList=list;
				inflater=LayoutInflater.from(context);
			}
			public void onConDataChanged(List<CondimentMsg> list){
				this.mList=list;
				this.notifyDataSetChanged();
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
				ViewHolder holder;
				CondimentMsg food=mList.get(position);
				if(convertView==null){
					holder=new ViewHolder();
					convertView=inflater.inflate(R.layout.food_msg, null);
					holder.name=(EditText) convertView.findViewById(R.id.food_name);
					holder.much=(EditText) convertView.findViewById(R.id.food_much);
					convertView.setTag(holder);
				}else{
					holder=(ViewHolder) convertView.getTag();
				}
				holder.name.setText(food.getCondiName());
				holder.much.setText(food.getCondiMuch());
				
				return convertView;
			}
			class ViewHolder{
				EditText name,much;
			}

		}