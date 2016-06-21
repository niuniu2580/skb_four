package com.skb.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.niuniu.skb_four.R;
import com.skb.entity.SortGridItem;
import com.skb.ui.QueryMessage;

public class SortGridAdapter extends BaseAdapter {

	Context context;
	SortGridItem item;
	private ArrayList<SortGridItem> mList = new ArrayList<SortGridItem>();
	ViewHolder holder;

	public SortGridAdapter(Context mContext, ArrayList<SortGridItem> list) {
		context = mContext;
		mList=list;
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
		item = mList.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.sort_grid_item, null);
			holder.img = (ImageView) convertView.findViewById(R.id.img_gridImg);
			holder.name = (TextView) convertView.findViewById(R.id.text_name);
			holder.steps = (TextView) convertView.findViewById(R.id.text_steps);
			holder.time = (TextView) convertView.findViewById(R.id.text_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.img.setImageBitmap(item.getImg());
		holder.img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(context, QueryMessage.class);
				intent.putExtra("foodMsg", mList.get(position).getName());
				context.startActivity(intent);
			}
		});
		holder.name.setText(item.getName());
		holder.steps.setText(item.getSteps());
		holder.time.setText(item.getTime());
		
		return convertView;
	}

	class ViewHolder {
		ImageView img;
		TextView name, steps, time;
	}

}
