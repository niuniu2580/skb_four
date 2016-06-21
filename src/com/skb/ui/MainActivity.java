package com.skb.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.niuniu.skb_four.R;
import com.skb.fragment.CollectFragment;
import com.skb.fragment.HangoutFragment;
import com.skb.fragment.IndexFragment;
import com.skb.fragment.MyFragment;
import com.skb.fragment.SortFragment;

public class MainActivity extends FragmentActivity {

	RadioGroup group;
	RadioButton btn_first;
	RadioButton btn_second;
	RadioButton btn_third;
	RadioButton btn_fourth;
	RadioButton btn_fifth;
	FragmentTabHost tabHost;
	android.support.v4.view.ViewPager pager;
	List<Fragment> list = new ArrayList<Fragment>();

	public static final String SHOW_OF_FIRST_TAG = "first";
	public static final String SHOW_OF_SECOND_TAG = "second";
	public static final String SHOW_OF_THIRD_TAG = "third";
	public static final String SHOW_OF_FOURTH_TAG = "fourth";
	public static final String SHOW_OF_FIFTH_TAG = "fifth";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();

	}

	private void initView() {
		tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		group = (RadioGroup) findViewById(R.id.radio_menu);
		btn_first = (RadioButton) findViewById(R.id.radio_first);
		btn_second = (RadioButton) findViewById(R.id.radio_senond);
		btn_third = (RadioButton) findViewById(R.id.radio_third);
		btn_fourth = (RadioButton) findViewById(R.id.radio_fourth);
		btn_fifth = (RadioButton) findViewById(R.id.radio_fifth);
		pager = (android.support.v4.view.ViewPager) findViewById(R.id.Main_pager);
		tabHost.setup(MainActivity.this, getSupportFragmentManager(),
				R.id.Main_pager);

		TabSpec tab0 = tabHost.newTabSpec(SHOW_OF_FIRST_TAG).setIndicator("0");
		TabSpec tab1 = tabHost.newTabSpec(SHOW_OF_SECOND_TAG).setIndicator("1");
		TabSpec tab2 = tabHost.newTabSpec(SHOW_OF_THIRD_TAG).setIndicator("2");
		TabSpec tab3 = tabHost.newTabSpec(SHOW_OF_FOURTH_TAG).setIndicator("3");
		TabSpec tab4 = tabHost.newTabSpec(SHOW_OF_FIFTH_TAG).setIndicator("4");

		tabHost.addTab(tab0, IndexFragment.class, null);
		tabHost.addTab(tab1, CollectFragment.class, null);
		tabHost.addTab(tab2, SortFragment.class, null);
		tabHost.addTab(tab3, HangoutFragment.class, null);
		tabHost.addTab(tab4, MyFragment.class, null);

		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_first:
					tabHost.setCurrentTabByTag(SHOW_OF_FIRST_TAG);
					break;
				case R.id.radio_senond:
					tabHost.setCurrentTabByTag(SHOW_OF_SECOND_TAG);
					break;
				case R.id.radio_third:
					tabHost.setCurrentTabByTag(SHOW_OF_THIRD_TAG);
					break;
				case R.id.radio_fourth:
					tabHost.setCurrentTabByTag(SHOW_OF_FOURTH_TAG);
					break;
				case R.id.radio_fifth:
					tabHost.setCurrentTabByTag(SHOW_OF_FIFTH_TAG);
					break;

				default:
					break;
				}
			}
		});
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				int position = tabHost.getCurrentTab();
				pager.setCurrentItem(position);
			}
		});
		tabHost.setCurrentTab(0);

		IndexFragment a = new IndexFragment();
		CollectFragment b = new CollectFragment();
		SortFragment c = new SortFragment();
		HangoutFragment d = new HangoutFragment();
		MyFragment e = new MyFragment();
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		list.add(e);
		pager.setAdapter(new com.skb.adapter.MainAdapter(
				getSupportFragmentManager(), list));
		pager.setOnPageChangeListener(new ViewPager());
	}

	class ViewPager implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int index) {
			if (index == 0) {
				btn_first.setChecked(true);
			} else if (index == 1) {
				btn_second.setChecked(true);
			} else if (index == 2) {
				btn_third.setChecked(true);
			} else if (index == 3) {
				btn_fourth.setChecked(true);
			} else if (index == 4) {
				btn_fifth.setChecked(true);
			}
			tabHost.setCurrentTab(index);
		}

	}
}
