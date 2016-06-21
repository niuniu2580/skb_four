package com.skb.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.niuniu.skb_four.R;
import com.skb.entity.GridItem;

public class GridAdapter extends BaseAdapter {

	List<GridItem> mList;
	Context context;
	GridItem foodItem;

	public GridAdapter(Context context, List<GridItem> food_List) {
		mList = food_List;
		this.context = context;
	}

	public void onDataChanged(List<GridItem> list) {
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
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		foodItem = mList.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.grid_view_item, null);
			holder.image1 = (ImageView) convertView
					.findViewById(R.id.imageButton);
			holder.foodMsg=(TextView) convertView.findViewById(R.id.food_item_title);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.image1.setImageBitmap(foodItem.getItemResId());
		holder.foodMsg.setText(foodItem.getFoodMsg());

		return convertView;

	}

	class ViewHolder {
		ImageView image1;
		TextView foodMsg;
	}

}
