package com.skb.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.niuniu.skb_four.R;
import com.skb.entity.FoodMethod;


public class MethodListAdapter extends BaseAdapter {

			List<FoodMethod> mList;
			LayoutInflater inflater;
			
			public MethodListAdapter(Context context,List<FoodMethod> list) {
				this.mList=list;
				inflater=LayoutInflater.from(context);
			}
			public void onMethodChanged(List<FoodMethod> list){
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
				FoodMethod method=mList.get(position);
				if(convertView==null){
					holder=new ViewHolder();
					convertView=inflater.inflate(R.layout.food_method, null);
					holder.method=(EditText) convertView.findViewById(R.id.edit_method);
					convertView.setTag(holder);
				}else{
					holder=(ViewHolder) convertView.getTag();
				}
				holder.method.setText(method.getFoodMehod());
				
				return convertView;
			}
			class ViewHolder{
				EditText method;
			}

		}