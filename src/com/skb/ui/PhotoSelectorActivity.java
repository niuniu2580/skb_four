package com.skb.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.niuniu.skb_four.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.skb.adapter.AlbumAdapter;
import com.skb.adapter.PhotoSelectorAdapter;
import com.skb.entity.AlbumModel;
import com.skb.entity.PhotoItem.onItemClickListener;
import com.skb.entity.PhotoItem.onPhotoItemCheckedListener;
import com.skb.entity.PhotoModel;
import com.skb.utils.AnimationUtil;
import com.skb.utils.CommonUtils;
import com.skb.utils.PhotoSelectorDomain;

public class PhotoSelectorActivity extends Activity implements
		onItemClickListener, onPhotoItemCheckedListener, OnItemClickListener,
		OnClickListener {

	public static final int REQUEST_PHOTO = 0;
	private static final int REQUEST_CAMERA = 1;

	public static final String RECCENT_PHOTO = "�����Ƭ";

	private GridView gvPhotos;
	private ListView lvAblum;
	private Button btnOk;
	private TextView tvAlbum, tvPreview, tvTitle;
	private PhotoSelectorDomain photoSelectorDomain;
	private PhotoSelectorAdapter photoAdapter;
	private AlbumAdapter albumAdapter;
	private RelativeLayout layoutAlbum;
	private ArrayList<PhotoModel> selected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		setContentView(R.layout.activity_photoselector);

		DisplayImageOptions defaultDisplayImageOptions = new DisplayImageOptions.Builder() //
				.considerExifParams(true) // ����ͼƬ����
				.resetViewBeforeLoading(true) // ����֮ǰ����ImageView
				.showImageOnLoading(R.drawable.ic_picture_loading) // ����ʱͼƬ����Ϊ��ɫ
				.showImageOnFail(R.drawable.ic_picture_loadfailed) // ����ʧ��ʱ��ʾ��ͼƬ
				.delayBeforeLoading(0) // ����֮ǰ���ӳ�ʱ��
				.build(); //
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.defaultDisplayImageOptions(defaultDisplayImageOptions)
				.memoryCacheExtraOptions(480, 800).threadPoolSize(5).build();
		ImageLoader.getInstance().init(config);

		photoSelectorDomain = new PhotoSelectorDomain(getApplicationContext());

		selected = new ArrayList<PhotoModel>();

		tvTitle = (TextView) findViewById(R.id.tv_title_lh);
		gvPhotos = (GridView) findViewById(R.id.gv_photos_ar);
		lvAblum = (ListView) findViewById(R.id.lv_ablum_ar);
		btnOk = (Button) findViewById(R.id.btn_right_lh);
		tvAlbum = (TextView) findViewById(R.id.tv_album_ar);
		tvPreview = (TextView) findViewById(R.id.tv_preview_ar);
		layoutAlbum = (RelativeLayout) findViewById(R.id.layout_album_ar);

		btnOk.setOnClickListener(this);
		tvAlbum.setOnClickListener(this);
		tvPreview.setOnClickListener(this);

		photoAdapter = new PhotoSelectorAdapter(getApplicationContext(),
				new ArrayList<PhotoModel>(), CommonUtils.getWidthPixels(this),
				this, this, this);
		gvPhotos.setAdapter(photoAdapter);

		albumAdapter = new AlbumAdapter(getApplicationContext(),
				new ArrayList<AlbumModel>());
		lvAblum.setAdapter(albumAdapter);
		lvAblum.setOnItemClickListener(this);

		findViewById(R.id.bv_back_lh).setOnClickListener(this); // ����

		photoSelectorDomain.getReccent(reccentListener); // ���������Ƭ
		photoSelectorDomain.updateAlbum(albumListener); // ���������Ϣ
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_right_lh)
			ok(); // ѡ����Ƭ
		else if (v.getId() == R.id.tv_album_ar)
			album();
		else if (v.getId() == R.id.tv_preview_ar)
			priview();
		else if (v.getId() == R.id.tv_camera_vc)
			catchPicture();
		else if (v.getId() == R.id.bv_back_lh)
			finish();
	}

	/** ���� */
	private void catchPicture() {
		CommonUtils.launchActivityForResult(this, new Intent(
				MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_CAMERA);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CAMERA && resultCode ==RESULT_OK ) {
			PhotoModel photoModel = new PhotoModel(CommonUtils.query(
					getApplicationContext(), data.getData()));
			selected.clear();
			selected.add(photoModel);
			ok();
		}
	}

	/** ��� */
	private void ok() {
		if (selected.isEmpty()) {
			setResult(RESULT_CANCELED);
		} else {
			Intent data =getIntent();
			Bundle bundle = new Bundle();
			bundle.putSerializable("photos", selected);
			data.putExtras(bundle);
			setResult(RESULT_OK, data);
		}
		finish();
	}

	/** Ԥ����Ƭ */
	private void priview() {
		Bundle bundle = new Bundle();
		bundle.putSerializable("photos", selected);
		CommonUtils.launchActivity(this, PhotoPreviewActivity.class, bundle);
	}

	private void album() {
		if (layoutAlbum.getVisibility() == View.GONE) {
			popAlbum();
		} else {
			hideAlbum();
		}
	}

	/** ��������б� */
	private void popAlbum() {
		layoutAlbum.setVisibility(View.VISIBLE);
		new AnimationUtil(getApplicationContext(), R.anim.translate_up_current)
				.setLinearInterpolator().startAnimation(layoutAlbum);
	}

	/** ��������б� */
	private void hideAlbum() {
		new AnimationUtil(getApplicationContext(), R.anim.translate_down)
				.setLinearInterpolator().startAnimation(layoutAlbum);
		layoutAlbum.setVisibility(View.GONE);
	}

	/** ���ѡ�е�ͼƬ */
	private void reset() {
		selected.clear();
		tvPreview.setText("Ԥ��");
		tvPreview.setEnabled(false);
	}

	@Override
	/** ����鿴��Ƭ */
	public void onItemClick(int position) {
		Bundle bundle = new Bundle();
		if (tvAlbum.getText().toString().equals(RECCENT_PHOTO))
			bundle.putInt("position", position - 1);
		else
			bundle.putInt("position", position);
		bundle.putString("album", tvAlbum.getText().toString());
		CommonUtils.launchActivity(this, PhotoPreviewActivity.class, bundle);
	}

	@Override
	/** ��Ƭѡ��״̬�ı�֮�� */
	public void onCheckedChanged(PhotoModel photoModel,
			CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			selected.add(photoModel);
			tvPreview.setEnabled(true);
		} else {
			selected.remove(photoModel);
		}
		tvPreview.setText("Ԥ��(" + selected.size() + ")"); // �޸�Ԥ������

		if (selected.isEmpty()) {
			tvPreview.setEnabled(false);
			tvPreview.setText("Ԥ��");
		}
	}

	@Override
	public void onBackPressed() {
		if (layoutAlbum.getVisibility() == View.VISIBLE) {
			hideAlbum();
		} else
			super.onBackPressed();
	}

	@Override
	/** ����б����¼� */
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		AlbumModel current = (AlbumModel) parent.getItemAtPosition(position);
		for (int i = 0; i < parent.getCount(); i++) {
			AlbumModel album = (AlbumModel) parent.getItemAtPosition(i);
			if (i == position)
				album.setCheck(true);
			else
				album.setCheck(false);
		}
		albumAdapter.notifyDataSetChanged();
		hideAlbum();
		tvAlbum.setText(current.getName());
		tvTitle.setText(current.getName());

		// ������Ƭ�б�
		if (current.getName().equals(RECCENT_PHOTO))
			photoSelectorDomain.getReccent(reccentListener);
		else
			photoSelectorDomain.getAlbum(current.getName(), reccentListener); // ��ȡѡ��������Ƭ
	}

	/** ��ȡ����ͼ����Ƭ�ص� */
	public interface OnLocalReccentListener {
		public void onPhotoLoaded(List<PhotoModel> photos);
	}

	/** ��ȡ���������Ϣ�ص� */
	public interface OnLocalAlbumListener {
		public void onAlbumLoaded(List<AlbumModel> albums);
	}

	private OnLocalAlbumListener albumListener = new OnLocalAlbumListener() {
		@Override
		public void onAlbumLoaded(List<AlbumModel> albums) {
			albumAdapter.update(albums);
		}
	};

	private OnLocalReccentListener reccentListener = new OnLocalReccentListener() {
		@Override
		public void onPhotoLoaded(List<PhotoModel> photos) {
			if (tvAlbum.getText().equals(RECCENT_PHOTO))
				photos.add(0, new PhotoModel());
			photoAdapter.update(photos);
			gvPhotos.smoothScrollToPosition(0); // ����������
			reset();
		}
	};
}
