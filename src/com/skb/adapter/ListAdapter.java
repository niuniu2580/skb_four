package com.skb.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.niuniu.skb_four.R;
import com.skb.entity.FoodItem;
import com.skb.ui.QueryMessage;
import com.skb.utils.MyView;
import com.skb.utils.RoundImageView;

public class ListAdapter extends BaseAdapter {

	List<FoodItem> mList;
	Context context;
	ViewHolder holder;
	FoodItem foodItem;
	Intent intent = null;

	public ListAdapter(Context context, List<FoodItem> food_List) {
		mList = food_List;
		this.context = context;
	}

	public void onDataChanged(List<FoodItem> list) {
		this.mList = list;
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
	public View getView(final int position, View convertView, ViewGroup parent) {

		foodItem = mList.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.food_item, null);
			holder.title = (TextView) convertView.findViewById(R.id.item_title);
			holder.image1 = (RoundImageView) convertView.findViewById(R.id.image_first);
			holder.image2 = (RoundImageView) convertView
					.findViewById(R.id.image_second);
			holder.image3 = (RoundImageView) convertView.findViewById(R.id.image_third);
			holder.first_msg = (TextView) convertView
					.findViewById(R.id.first_msg);
			holder.second_msg = (TextView) convertView
					.findViewById(R.id.second_msg);
			holder.third_msg = (TextView) convertView
					.findViewById(R.id.third_msg);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(foodItem.getItemTitle());
		holder.image1.setImageBitmap(foodItem.getFirstItemResId());
		holder.first_msg.setText(foodItem.getFirstItemMsg());
		holder.image2.setImageBitmap(foodItem.getSecondItemResId());
		holder.second_msg.setText(foodItem.getSecondItemMsg());
		holder.image3.setImageBitmap(foodItem.getThirdItemResId());
		holder.third_msg.setText(foodItem.getThirdItemMsg());

		holder.image1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(context, QueryMessage.class);
				intent.putExtra("foodMsg", mList.get(position)
						.getFirstItemMsg());
				System.out.println(mList.get(position).getFirstItemMsg());
				context.startActivity(intent);
			}
		});
		holder.image2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(context, QueryMessage.class);
				intent.putExtra("foodMsg", mList.get(position)
						.getSecondItemMsg());
				context.startActivity(intent);
			}
		});
		holder.image3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(context, QueryMessage.class);
				intent.putExtra("foodMsg", mList.get(position)
						.getThirdItemMsg());
				context.startActivity(intent);
			}
		});

		return convertView;

	}

	class ViewHolder {
		TextView title, first_msg, second_msg, third_msg;
		RoundImageView image1, image2, image3;
	}

}
