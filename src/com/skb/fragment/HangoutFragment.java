package com.skb.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.listener.FindListener;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.niuniu.skb_four.R;
import com.skb.adapter.HangoutViewPageAdapter;
import com.skb.adapter.ListAdapter;
import com.skb.entity.FoodInfo;
import com.skb.entity.FoodItem;
import com.skb.entity.HangoutItem;

public class HangoutFragment extends android.support.v4.app.Fragment {

	final int MENU_SET_MODE = 0;
	PullToRefreshListView mPullRefreshListView;
	List<FoodItem> foodList = new ArrayList<FoodItem>();
	ListAdapter listAdapter;
	View viewContent;
	
	byte[] img1,img2,img3;

	/**
	 * һ������Ķ��壨�ڴ滺�棩
	 */
	private LruCache< byte[], Bitmap> lru;
	private Context context;
	
	/**
	 * �����������
	 */
	private ViewPager mViewPager;
	private HangoutViewPageAdapter adapter;
	private List<View> mImageView = new ArrayList<View>();
	int i = 100;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
			do {
				handler.sendEmptyMessageDelayed(0, 2000);
			} while (i < 0);
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		viewContent = inflater.inflate(R.layout.hangout, container, false);
		initView();
		handler.sendEmptyMessageDelayed(0, 2000);

		/***
		 * ��������ĳ�ʼ��
		 */

		mPullRefreshListView = (PullToRefreshListView) viewContent
				.findViewById(R.id.hangout_list);
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {
					/**
					 * ����ˢ��
					 */
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase refreshView) {

						Handler handler = new Handler();
						handler.post(new Runnable() {

							@Override
							public void run() {
								Toast.makeText(getActivity(), "ˢ�³ɹ���",
										Toast.LENGTH_SHORT).show();
								DownloadItem item=new DownloadItem();
								item.execute();
								showList(foodList);
								mPullRefreshListView.onRefreshComplete();
							}
						});
					}

					/**
					 * ����ˢ��
					 */
					@Override
					public void onPullUpToRefresh(PullToRefreshBase refreshView) {

						Handler handler = new Handler();
						handler.post(new Runnable() {

							@Override
							public void run() {
								Toast.makeText(getActivity(), "û�и�����������",
										Toast.LENGTH_SHORT).show();
								mPullRefreshListView.onRefreshComplete();
							}
						});

					}
				});
		/**
		 * �ڴ滺��ĳ�ʼ��
		 */
		open(getActivity());

		Bmob.initialize(getActivity(), "8774785dac79711f4f8a914efce9a4bf");
		return viewContent;
	}
	
	private void open(Context context){
		this.context=context;
		ActivityManager manager=(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		int memSize=manager.getMemoryClass();
		int maxSize=memSize/8 * 1024 *1024;
		lru=new LruCache<byte[], Bitmap>(maxSize);
	}
	
	private void addBitmapToCache(byte[] bytes,Bitmap bitmap){
		
		if(getBitmapFromCache(bytes)==null){
			lru.put(bytes, bitmap);
		}
		
	}
	private Bitmap getBitmapFromCache(byte[] bytes){
		
		return lru.get(bytes);
		
	}

	private void initView() {
		/*
		 * ViewPager������
		 */
		mViewPager = (ViewPager) viewContent.findViewById(R.id.ViewPager);
		LayoutInflater viewPager = LayoutInflater.from(getActivity());

		mImageView.add(viewPager.inflate(R.layout.ad1, null));
		mImageView.add(viewPager.inflate(R.layout.ad2, null));
		mImageView.add(viewPager.inflate(R.layout.ad3, null));
		adapter = new HangoutViewPageAdapter(getActivity(), mImageView);
		mViewPager.setAdapter(adapter);
	}

	/*
	 * �˷��������������ˢ�ºͼ��ظ���Ľӿ� ������ý���仯�ļ�ⷽ�� ������adapter
	 */
	/*
	 * �˷��������������ˢ�ºͼ��ظ���Ľӿ� ������ý���仯�ļ�ⷽ�� ������adapter
	 */

	public void showList(List<FoodItem> foodItem) {
		if (listAdapter == null) {
			listAdapter = new ListAdapter(getActivity(), foodList);
			mPullRefreshListView.setAdapter(listAdapter);
		} else {
			listAdapter.onDataChanged(foodItem);
		}

		// ��������ˢ�µ���
		ILoadingLayout slayout = mPullRefreshListView.getLoadingLayoutProxy(
				true, false);
		slayout.setPullLabel("��������ˢ��");
		slayout.setRefreshingLabel("��������������....");
		slayout.setReleaseLabel("�ſ�����ˢ��");
		// ���������������
		ILoadingLayout layout = mPullRefreshListView.getLoadingLayoutProxy(
				false, true);
		layout.setPullLabel("���ظ���");
		layout.setRefreshingLabel("��������������....");
		layout.setReleaseLabel("�ſ�����ˢ��");
	}

	/**
	 * ���������ϵĶ���������ȡ������ת����ͼƬ ������Ĵ�С�ı�
	 * 
	 * @param bytes
	 * @return
	 */
	public Bitmap getPicFromByte(byte[] bytes, int mReqWidth, int mReqHeight) {

		Bitmap bitmap = null;
		if (bytes != null) {
			bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		}

		BitmapFactory.Options option = new BitmapFactory.Options();

		option.inJustDecodeBounds = true;

		if (bytes != null) {
			bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
					option);
		}

		option.inSampleSize = caculateBitmap(option, mReqWidth, mReqHeight);

		option.inJustDecodeBounds = false;

		bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, option);
		return bitmap;
	}

	/***
	 * ���¼����������
	 * 
	 * @param option
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */

	private int caculateBitmap(BitmapFactory.Options option, int reqWidth,
			int reqHeight) {

		int width = option.outWidth;
		int height = option.outHeight;
		int sampleSize = 1;
		if (width > reqWidth || height > reqHeight) {
			if (width > height) {
				sampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				sampleSize = Math.round((float) width / (float) reqWidth);
			}
		}

		return sampleSize;
	}

	class DownloadItem extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			foodList = new ArrayList<FoodItem>();

			BmobQuery<HangoutItem> query = new BmobQuery<HangoutItem>();
			query.include("recipe1,recipe2,recipe3");
			Boolean isCache=query.hasCachedResult(getActivity(), HangoutItem.class);
			if(isCache){
				query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
			}
			query.setMaxCacheAge(TimeUnit.DAYS.toMillis(10));
			query.findObjects(getActivity(), new FindListener<HangoutItem>() {

				@Override
				public void onSuccess(List<HangoutItem> args) {

					for (int i = 0; i < args.size(); i++) {

						FoodItem item = new FoodItem();
						String title = args.get(i).getTitle();

						FoodInfo recipe1 = args.get(i).getRecipe1();
						String title1 = recipe1.getTitle();
						img1 = recipe1.getFoodImg();
						Bitmap b1=getPicFromByte(img1, 500, 300);
						if(getBitmapFromCache(img1)==null){
							addBitmapToCache(img1,b1);
						}else{
							System.out.println("ͼƬ�Ѵ���");
							b1=getBitmapFromCache(img1);
						}

						FoodInfo recipe2 = args.get(i).getRecipe2();
						String title2 = recipe2.getTitle();
						img2 = recipe2.getFoodImg();
						Bitmap b2=getPicFromByte(img2, 500, 300);
						if(getBitmapFromCache(img2)==null){
							addBitmapToCache(img2,b2);
						}else{
							System.out.println("ͼƬ�Ѵ���");
							Toast.makeText(getActivity(), "ͼƬ�Ѿ����ڣ�", Toast.LENGTH_SHORT).show();
							b2=getBitmapFromCache(img2);
						}

						FoodInfo recipe3 = args.get(i).getRecipe3();
						String title3 = recipe3.getTitle();
						img3 = recipe3.getFoodImg();
						Bitmap b3=getPicFromByte(img3, 500, 300);
						if(getBitmapFromCache(img3)==null){
							addBitmapToCache(img3,b3);
						}else{
							System.out.println("ͼƬ�Ѵ���");
							b3=getBitmapFromCache(img3);
						}
							
						item.setItemTitle(title);
						item.setFirstItemResId(b1);
						item.setFirstItemMsg(title1);
						item.setSecondItemResId(b2);
						item.setSecondItemMsg(title2);
						item.setThirdItemResId(b3);
						item.setThirdItemMsg(title3);

						foodList.add(item);
						
						showList(foodList);
					}
				}

				@Override
				public void onError(int arg0, String arg1) {
						Toast.makeText(getActivity(), "��������", Toast.LENGTH_SHORT).show();					
				}
			});
			return null;
		}
	}
}