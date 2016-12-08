package com.newbrain.chaxin.teadingzhong;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.newbrain.adapter.MyAdapter_TeaDingZhongGridview;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.ManoTeaTypeIntroBean;
import com.newbrain.model.ManoTeaTypeIntroBean.TeaTypeIntro;

public class TeaDingZhongActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {

	private View all_actionbar_linear_left;
	private ImageButton all_actionbar_more;
	private TextView all_actionbar_name;

	private GridView tea_dingzhong_gridview;
	private MyAdapter_TeaDingZhongGridview myAdapter_TeaDingZhongGridview;

	private List<TeaTypeIntro> mTeaTypeIntros;
	
	public static TeaTypeIntro mTeaTypeIntro;
	private JsonThread mThread;
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case Constant.FLAG_GET_MANOR_TEATYPE:
					CustomProgressDialog.stopProgressDialog();
					HttpReturnData reData = (HttpReturnData) msg.obj;
					if (reData.isSuccess()) {
						ManoTeaTypeIntroBean manoTeaTypIntroBean = (ManoTeaTypeIntroBean) reData
								.getObg();
						if (manoTeaTypIntroBean.getCode().equals("1")) {
							tv_emptydata.setVisibility(View.INVISIBLE);
							mTeaTypeIntros = manoTeaTypIntroBean.getResult();
							myAdapter_TeaDingZhongGridview.setList(mTeaTypeIntros);
						}else{
							tv_emptydata.setText(R.string.empty_data);
						}
					}else{
						tv_emptydata.setText(R.string.get_data_fail);
					}
					break;
			}
		}

	};

	private TextView tv_emptydata;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tea_dingzhong);

		all_actionbar_linear_left = (View) findViewById(R.id.all_actionbar_linear_left);
		all_actionbar_linear_left.setOnClickListener(this);
		all_actionbar_name = (TextView) findViewById(R.id.all_actionbar_name);
		all_actionbar_name.setText(getString(R.string.tea_grow_tea));
		all_actionbar_more = (ImageButton) findViewById(R.id.all_actionbar_button_right);
		all_actionbar_more.setOnClickListener(this);
		all_actionbar_more.setVisibility(View.GONE);
		mTeaTypeIntros = new ArrayList<ManoTeaTypeIntroBean.TeaTypeIntro>();
		myAdapter_TeaDingZhongGridview = new MyAdapter_TeaDingZhongGridview(
				TeaDingZhongActivity.this, mTeaTypeIntros);
		tea_dingzhong_gridview = (GridView) findViewById(R.id.tea_dingzhong_gridview);
		tea_dingzhong_gridview.setOnItemClickListener(this);
		tea_dingzhong_gridview.setAdapter(myAdapter_TeaDingZhongGridview);
		tv_emptydata = (TextView)findViewById(R.id.tv_emptydata);
		tea_dingzhong_gridview.setEmptyView(tv_emptydata);
		startGetManorTeaType();
	}

	/** 搜索订种茶类型 */
	private void startGetManorTeaType() {
		List<Bean> list = new ArrayList<Bean>();
		startSearchThread(list, Constant.FLAG_GET_MANOR_TEATYPE);
	}

	private void startSearchThread(List<Bean> params, int flag) {
		CustomProgressDialog.startProgressDialog(this);

		if(mThread != null){
			mThread.cancleReturnData();
		}
		mThread = new JsonThread(this, params, mHandler, flag);
		mThread.start();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.all_actionbar_linear_left:
			onBackPressed();
			break;
		case R.id.all_actionbar_button_right:
			showMoreDialog(view, false);
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long position) {
		mTeaTypeIntro = mTeaTypeIntros.get((int)position);
		Intent intent=new Intent(this, TeaDingZhongDetailActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mThread != null){
			mThread.cancleReturnData();
			mThread = null;
		}
	}
}