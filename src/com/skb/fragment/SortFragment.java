package com.skb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.niuniu.skb_four.R;
import com.skb.ui.Sort_Info;

public class SortFragment extends android.support.v4.app.Fragment {

	private LinearLayout suan, tian, la, xian, more;

	private ImageButton acid = null;
	private Button button_acid = null;
	private ImageButton spicy = null;
	private Button button_spicy = null;
	private ImageButton salty = null;
	private Button button_salty = null;
	private ImageButton sweet = null;
	private Button button_sweet = null;
	private ImageButton btn_more = null;
	private Button button_more = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View viewContent = inflater.inflate(R.layout.sort, container, false);
		suan = (LinearLayout) viewContent.findViewById(R.id.suan);
		tian = (LinearLayout) viewContent.findViewById(R.id.tian);
		la = (LinearLayout) viewContent.findViewById(R.id.la);
		xian = (LinearLayout) viewContent.findViewById(R.id.xian);
		more = (LinearLayout) viewContent.findViewById(R.id.more);

		sweet = (ImageButton) viewContent.findViewById(R.id.imageView_sweet);
		button_sweet = (Button) viewContent.findViewById(R.id.button_sweet);
		acid = (ImageButton) viewContent.findViewById(R.id.imageView_acid);
		button_acid = (Button) viewContent.findViewById(R.id.button_acid);
		salty = (ImageButton) viewContent.findViewById(R.id.imageView_salt);
		button_salty = (Button) viewContent.findViewById(R.id.button_salty);
		spicy = (ImageButton) viewContent.findViewById(R.id.imageView_spicy);
		button_spicy = (Button) viewContent.findViewById(R.id.button_spicy);
		btn_more = (ImageButton) viewContent.findViewById(R.id.imageView_more);
		button_more = (Button) viewContent.findViewById(R.id.button_more);

		acid.setOnClickListener(listener);
		button_acid.setOnClickListener(listener);
		spicy.setOnClickListener(listener);
		button_spicy.setOnClickListener(listener);
		salty.setOnClickListener(listener);
		button_salty.setOnClickListener(listener);
		sweet.setOnClickListener(listener);
		button_sweet.setOnClickListener(listener);
		btn_more.setOnClickListener(listener);
		button_more.setOnClickListener(listener);

		suan.setOnClickListener(listener);
		tian.setOnClickListener(listener);
		la.setOnClickListener(listener);
		xian.setOnClickListener(listener);
		more.setOnClickListener(listener);

		return viewContent;
	}

	public OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = null;

			switch (v.getId()) {
			case R.id.suan:
				intent = new Intent(getActivity(), Sort_Info.class);
				intent.putExtra("sort", "Ëá");
				startActivity(intent);
				break;

			case R.id.imageView_acid:
				intent = new Intent(getActivity(), Sort_Info.class);
				intent.putExtra("sort", "Ëá");
				startActivity(intent);
				break;
			case R.id.button_acid:
				intent = new Intent(getActivity(), Sort_Info.class);
				intent.putExtra("sort", "Ëá");
				startActivity(intent);
				break;

			case R.id.tian:
				intent = new Intent(getActivity(), Sort_Info.class);
				intent.putExtra("sort", "Ìð");
				startActivity(intent);
				break;

			case R.id.imageView_sweet:
				intent = new Intent(getActivity(), Sort_Info.class);
				intent.putExtra("sort", "Ìð");
				startActivity(intent);
				break;
			case R.id.button_sweet:
				intent = new Intent(getActivity(), Sort_Info.class);
				intent.putExtra("sort", "Ìð");
				startActivity(intent);
				break;
			case R.id.la:
				intent = new Intent(getActivity(), Sort_Info.class);
				intent.putExtra("sort", "À±");
				startActivity(intent);
				break;

			case R.id.imageView_spicy:
				intent = new Intent(getActivity(), Sort_Info.class);
				intent.putExtra("sort", "À±");
				startActivity(intent);
				break;
			case R.id.button_spicy:
				intent = new Intent(getActivity(), Sort_Info.class);
				intent.putExtra("sort", "À±");
				startActivity(intent);
				break;
			case R.id.xian:
				intent = new Intent(getActivity(), Sort_Info.class);
				intent.putExtra("sort", "ÏÌ");
				startActivity(intent);
				break;

			case R.id.imageView_salt:
				intent = new Intent(getActivity(), Sort_Info.class);
				intent.putExtra("sort", "ÏÌ");
				startActivity(intent);
				break;
			case R.id.button_salty:
				intent = new Intent(getActivity(), Sort_Info.class);
				intent.putExtra("sort", "ÏÌ");
				startActivity(intent);
				break;
			case R.id.more:
				intent = new Intent(getActivity(), Sort_Info.class);
				intent.putExtra("sort", "¸ü¶à");
				startActivity(intent);
				break;

			case R.id.imageView_more:
				intent = new Intent(getActivity(), Sort_Info.class);
				intent.putExtra("sort", "¸ü¶à");
				startActivity(intent);
				break;
			case R.id.button_more:
				intent = new Intent(getActivity(), Sort_Info.class);
				intent.putExtra("sort", "¸ü¶à");
				startActivity(intent);
				break;

			default:
				break;
			}
		};
	};
}