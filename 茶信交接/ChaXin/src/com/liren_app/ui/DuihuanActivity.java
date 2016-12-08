package com.liren_app.ui;

import java.util.ArrayList;
import java.util.List;

import com.liren_app.ui_opreater.HuaListener;
import com.liren_app.ui_opreater.ViewSwitcherOpreater;
import com.newbrain.chaxin.R;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class DuihuanActivity extends BaseActivity implements HuaListener {

	private ViewSwitcher vst_cha_imgs;
	private ViewSwitcherOpreater mViewSwitcherOpreater;
	private int screenNo = -1;

	private ImageButton img_duihuan;
	private ImageButton btn_back;

	private ImageView[] page_logos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duihuan);
		initUI();
		initControlListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_duihuan, menu);
		return true;
	}

	@SuppressLint("NewApi")
	@Override
	public void getNext(ViewSwitcher _switcher,  List<String> _img_ids) {
		// TODO Auto-generated method stub
		if (screenNo < _img_ids.size() - 1) {
			screenNo++;
			// 设置视图切换的动画效果
			_switcher.setInAnimation(this, R.anim.yy_slide_in_right);
			_switcher.setOutAnimation(this, R.anim.yy_slide_out_left);
			// 获取下一个视图的实例
			ImageView img = (ImageView) _switcher.getNextView();

			Picasso.with(this).load(_img_ids.get(screenNo)).into(img);

			_switcher.showNext();

			page_logos[screenNo].setBackgroundDrawable(DuihuanActivity.this
					.getResources().getDrawable(R.drawable.yy_img_show));
			if (screenNo != 0)
				page_logos[screenNo - 1]
						.setBackgroundDrawable(DuihuanActivity.this
								.getResources().getDrawable(
										R.drawable.yy_img_un_show));
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void getPrev(ViewSwitcher _switcher,  List<String> _img_ids) {
		if (screenNo > 0) {
			screenNo--;
			// 设置视图切换的动画效果
			_switcher.setInAnimation(this, R.anim.yy_slide_in_lef);
			_switcher.setOutAnimation(this, R.anim.yy_slide_out_right);
			ImageView img = (ImageView) _switcher.getNextView();

			Picasso.with(this).load(_img_ids.get(screenNo)).into(img);
			// 切换视图
			_switcher.showPrevious();

			page_logos[screenNo].setBackgroundDrawable(DuihuanActivity.this
					.getResources().getDrawable(R.drawable.yy_img_show));
			if (screenNo != 4)
				page_logos[screenNo + 1]
						.setBackgroundDrawable(DuihuanActivity.this
								.getResources().getDrawable(
										R.drawable.yy_img_un_show));
		}
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		page_logos = new ImageView[] {
				(ImageView) findViewById(R.id.img_page_1),
				(ImageView) findViewById(R.id.img_page_2),
				(ImageView) findViewById(R.id.img_page_3),
				(ImageView) findViewById(R.id.img_page_4),
				(ImageView) findViewById(R.id.img_page_5) };

		btn_back = (ImageButton) findViewById(R.id.btn_back);

		vst_cha_imgs = (ViewSwitcher) findViewById(R.id.vst_cha_imgs);
		mViewSwitcherOpreater = new ViewSwitcherOpreater(vst_cha_imgs, this,
				this);
		
		List<String> images = new ArrayList<String>();
		
		mViewSwitcherOpreater.setImgs(images);

		img_duihuan = (ImageButton) findViewById(R.id.img_duihuan);
		img_duihuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DuihuanActivity.this.startActivity(new Intent().setClass(
						DuihuanActivity.this, AddManageActivity.class));

			}
		});
	}

	@Override
	protected void initControlListener() {
		// TODO Auto-generated method stub
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DuihuanActivity.this.finish();
			}
		});
	}

	@Override
	protected void initBoardCastListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

}
