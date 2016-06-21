package com.skb.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.niuniu.skb_four.R;
import com.skb.adapter.SortGridAdapter;
import com.skb.entity.FoodInfo;
import com.skb.entity.SortGridItem;
import com.skb.fragment.AcidFragment;
import com.skb.fragment.MoreFragment;
import com.skb.fragment.SaltFragment;
import com.skb.fragment.SpicyFragment;
import com.skb.fragment.SweetFragment;

public class Sort_Info extends Activity {

	private ImageButton back;
	ArrayList<SortGridItem> itemList = new ArrayList<SortGridItem>();

	private PullToRefreshGridView gridView;
	private SortGridAdapter sort_adapter;

	Intent intent;
	String sort;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sort_info);

		intent = getIntent();
		sort = intent.getStringExtra("sort");

		initView();
		setTitle();

		Download load = new Download(Sort_Info.this);
		load.execute(sort);
		sort_adapter = new SortGridAdapter(Sort_Info.this, itemList);

	}

	private void initView() {
		back = (ImageButton) findViewById(R.id.imageButton_back);
		back.setOnClickListener(listener);
		gridView = (PullToRefreshGridView) findViewById(R.id.sort_grid);
		gridView.setOnRefreshListener(new OnRefreshListener2<GridView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {

				Handler handler = new Handler();
				handler.post(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(Sort_Info.this, "下拉刷新成功！",
								Toast.LENGTH_SHORT).show();

						gridView.setAdapter(sort_adapter);
						gridView.onRefreshComplete();
					}
				});

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				Toast.makeText(Sort_Info.this, "没有更多数据了！", Toast.LENGTH_SHORT)
						.show();
				gridView.onRefreshComplete();
			}
		});

	}

	public OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imageButton_back:
				Sort_Info.this.finish();
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 设置页面的title
	 */
	private void setTitle() {
		if ("酸".equals(sort)) {
			AcidFragment acid = new AcidFragment();
			FragmentTransaction trans = getFragmentManager().beginTransaction();
			trans.replace(R.id.sort_logo, acid);
			trans.addToBackStack(null);
			trans.commit();
		} else if ("甜".equals(sort)) {
			SweetFragment sweet = new SweetFragment();
			FragmentTransaction trans = getFragmentManager().beginTransaction();
			trans.replace(R.id.sort_logo, sweet);
			trans.addToBackStack(null);
			trans.commit();
		} else if ("辣".equals(sort)) {
			SpicyFragment spicy = new SpicyFragment();
			FragmentTransaction trans = getFragmentManager().beginTransaction();
			trans.replace(R.id.sort_logo, spicy);
			trans.addToBackStack(null);
			trans.commit();
		} else if ("咸".equals(sort)) {
			SaltFragment salt = new SaltFragment();
			FragmentTransaction trans = getFragmentManager().beginTransaction();
			trans.replace(R.id.sort_logo, salt);
			trans.addToBackStack(null);
			trans.commit();
		} else if ("更多".equals(sort)) {
			MoreFragment more = new MoreFragment();
			FragmentTransaction trans = getFragmentManager().beginTransaction();
			trans.replace(R.id.sort_logo, more);
			trans.addToBackStack(null);
			trans.commit();
		}
	}

	class Download extends AsyncTask<String, Void, List<SortGridItem>> {

		ArrayList<String> methodList = new ArrayList<String>();
		ProgressDialog dialog;
		Context mContext;
		String method;

		public Download(Context context) {
			this.mContext = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(Sort_Info.this);
			dialog.setMessage("正在加载......");
			dialog.setCancelable(false);
			dialog.setIndeterminate(false);
			dialog.show();
		}

		@Override
		protected List<SortGridItem> doInBackground(String... params) {

			BmobQuery<FoodInfo> query = new BmobQuery<FoodInfo>();
			query.addWhereEqualTo("sort", params[0]);
			query.findObjects(mContext, new FindListener<FoodInfo>() {

				@Override
				public void onSuccess(List<FoodInfo> list) {

					Iterator<FoodInfo> it = list.iterator();
					while (it.hasNext()) {
						SortGridItem gridItem = new SortGridItem();

						FoodInfo item = it.next();

						byte[] b = item.getFoodImg();
						Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0,
								b.length);

						String name = item.getTitle();

						method = item.getFoodMethod();
						String[] methodStr = method.split("#");
						for (int i = 0; i < methodStr.length; i++) {
							methodList.add(methodStr[i]);
						}
						String time = item.getTime();

						gridItem.setImg(bitmap);
						gridItem.setName(name);
						gridItem.setSteps(methodList.size() + "个步骤");
						gridItem.setTime(time);
						itemList.add(gridItem);
					}

					if (itemList.size() != 0) {
						dialog.dismiss();
					}
				}

				@Override
				public void onError(int arg0, String arg1) {
					Toast.makeText(mContext, "出错啦！", Toast.LENGTH_SHORT).show();
				}
			});

			return itemList;
		}

	}

}
