package com.liren_app.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alipay.sdk.app.l;
import com.liren_app.data.JiluDataAdapte;
import com.liren_app.http.GetJsonByUrl;
import com.liren_app.http.GetUrl;
import com.liren_app.http.IJsonListener;
import com.liren_app.http.MessageKey;
import com.liren_app.ui.dia.LoadingDialog;
import com.liren_app.ui_opreater.HuaListener;
import com.liren_app.ui_opreater.ViewSwitcherOpreater;
import com.newbrain.chaxin.R;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

public class YinYuanMainActivity extends BaseActivity implements HuaListener,
		IJsonListener {

	private LoadingDialog mLoadingDialog;
	
	private static final int GET_MAIN_INFO = 1001;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case GET_MAIN_INFO:

				JSONArray list = (JSONArray) msg.obj;
				grd_jifen_list_adapter.reSetData(list);
				if(mLoadingDialog!=null && mLoadingDialog.isShowing())
					mLoadingDialog.cancel();
				break;

			default:
				break;
			}
		}

	};

	private ViewSwitcher vst_jifen_imgs;
	private ViewSwitcherOpreater mViewSwitcherOpreater;
	private int screenNo = -1;

	private GridView grd_jifen_list;
	private JiluDataAdapte grd_jifen_list_adapter;

	private LinearLayout lay_duihuanjilu;
	private LinearLayout lay_yinyuan;

	private ImageView[] page_logos;

	private long get_main_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yin_yuan_main);

		initUI();
		initData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_yin_yuan_main, menu);
		return true;
	}

	@SuppressLint("NewApi")
	@Override
	public void getNext(ViewSwitcher _switcher,  List<String>  _img_ids) {
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

			page_logos[screenNo].setBackgroundDrawable(YinYuanMainActivity.this
					.getResources().getDrawable(R.drawable.yy_img_show));
			if (screenNo != 0)
				page_logos[screenNo - 1]
						.setBackgroundDrawable(YinYuanMainActivity.this
								.getResources().getDrawable(
										R.drawable.yy_img_un_show));
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void getPrev(ViewSwitcher _switcher, List<String> _img_ids) {
		if (screenNo > 0) {
			screenNo--;
			// 设置视图切换的动画效果
			_switcher.setInAnimation(this, R.anim.yy_slide_in_lef);
			_switcher.setOutAnimation(this, R.anim.yy_slide_out_right);
			ImageView img = (ImageView) _switcher.getNextView();

			Picasso.with(this).load(_img_ids.get(screenNo)).into(img);
			// 切换视图
			_switcher.showPrevious();

			page_logos[screenNo].setBackgroundDrawable(YinYuanMainActivity.this
					.getResources().getDrawable(R.drawable.yy_img_show));
			if (screenNo != 4)
				page_logos[screenNo + 1]
						.setBackgroundDrawable(YinYuanMainActivity.this
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

		grd_jifen_list = (GridView) findViewById(R.id.grd_jifen_list);
		JSONArray jifen_list = new JSONArray();
		for (int i = 0; i < 2; i++) {
			jifen_list.put(new Object());
		}
		grd_jifen_list_adapter = new JiluDataAdapte(this, jifen_list);
		grd_jifen_list.setAdapter(grd_jifen_list_adapter);
		grd_jifen_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				YinYuanMainActivity.this.startActivity(new Intent().setClass(
						YinYuanMainActivity.this, DuihuanActivity.class));
			}
		});

		// ------------------------------------
		vst_jifen_imgs = (ViewSwitcher) findViewById(R.id.vst_jifen_imgs);
		mViewSwitcherOpreater = new ViewSwitcherOpreater(vst_jifen_imgs, this,
				this);
		List<String> images = new ArrayList<String>();
		
		mViewSwitcherOpreater.setImgs(images);

		lay_duihuanjilu = (LinearLayout) findViewById(R.id.lay_duihuanjilu);
		lay_duihuanjilu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				YinYuanMainActivity.this.startActivity(new Intent().setClass(
						YinYuanMainActivity.this, DuihuanJiLuActivity.class));
			}
		});

		lay_yinyuan = (LinearLayout) findViewById(R.id.lay_yinyuan);
		lay_yinyuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				YinYuanMainActivity.this.startActivity(new Intent().setClass(
						YinYuanMainActivity.this, MingxiActivity.class));
			}
		});
	}

	@Override
	protected void initControlListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initBoardCastListener() {
		// TODO Auto-generated method stub

	}

	private int pageNo =1;
	@Override
	protected void initData() {
		// TODO Auto-generated method stubget_yingyuan_main
		// get_main_info = GetJsonByUrl.getJsonByPost(
		// GetUrl.getUrlByKey(MessageKey.get_yingyuan_main),
		// "{\"pageNo\":\"1\",\"pageSize\":\"1000\"}", this);
		get_main_info = GetJsonByUrl.getJsonByPost(
				GetUrl.getUrlByKey(MessageKey.get_yingyuan_main,pageNo), new String[] {
						"pageNo", "pageSize" }, new String[] { "1", "1000" },
				this);
		if(mLoadingDialog==null)
			mLoadingDialog=LoadingDialog.getInstance(this);
		if(mLoadingDialog.isShowing())
			mLoadingDialog.cancel();
		mLoadingDialog.show();
	}

	@Override
	public void onGetJson(String json, int message_key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetJsonFailed(int message_key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetJson(boolean isSuccess, String json, long request_code) {
		// TODO Auto-generated method stub

		if (this.get_main_info == request_code) {
			try {
				JSONArray list = new JSONObject(json).getJSONArray("result");

				Message msg = new Message();
				msg.what = GET_MAIN_INFO;
				msg.obj = list;
				mHandler.sendMessage(msg);

			} catch (JSONException e) {

				e.printStackTrace();
			}

		}

	}
}
