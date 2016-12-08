package com.newbrain.chaxin.teadingzhong;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.newbrain.adapter.MyAdapter_TeaDingZhongDetailListView;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.customview.XListView;
import com.newbrain.customview.XListView.IXListViewListener;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.ManoSearchIntroBean;
import com.newbrain.model.ManoSearchIntroBean.ManoSearchIntro;
import com.newbrain.utils.Utils;

public class TeaDingZhongDetailActivity extends BaseActivity implements OnClickListener, OnItemClickListener{

	private View all_actionbar_linear_left;
	private TextView all_actionbar_name;
	private TextView tv_emptydata;
	private ImageButton all_actionbar_more;
	
	private XListView tea_dingzhong_detail_listview;
	private MyAdapter_TeaDingZhongDetailListView myAdapter_TeaDingZhongDetailListview;
	
//	private List<ManoSearchIntro> mManoSearchIntros;
	public static ManoSearchIntro mManoSearchIntro;
	private ManoSearchIntroBean mManoSearchIntroBean;
	private JsonThread mThread;

	private boolean mRefreshing = false;
	private boolean mLoadmoreing = false;
	private final int HIDE_HEADER = -1;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case HIDE_HEADER:
					tea_dingzhong_detail_listview.stopRefresh();
					break;
				case Constant.FLAG_GET_MANOR_SEARCH:
					CustomProgressDialog.stopProgressDialog();
					HttpReturnData reData = (HttpReturnData) msg.obj;
//					if (reData.isSuccess()) {
//						ManoSearchIntroBean manoSearchIntroBean = (ManoSearchIntroBean) reData
//								.getObg();
//						if (manoSearchIntroBean.getCode().equals("1")) {
//
//							mManoSearchIntros = manoSearchIntroBean.getResult();
//							myAdapter_TeaDingZhongDetailListview.setList(mManoSearchIntros);
//						} else {
//							System.out.println("result:"
//									+ manoSearchIntroBean.getMessage());
//						}
//					}
//					

					if(reData.isSuccess()){
						ManoSearchIntroBean manoSearchIntroBean = (ManoSearchIntroBean) reData
							.getObg();
						if(manoSearchIntroBean.getCode().equals("1")){
							if(mManoSearchIntroBean == null  ){
								mManoSearchIntroBean = manoSearchIntroBean;
								myAdapter_TeaDingZhongDetailListview = new MyAdapter_TeaDingZhongDetailListView(TeaDingZhongDetailActivity.this, mManoSearchIntroBean.getResult());
								tea_dingzhong_detail_listview.setAdapter(myAdapter_TeaDingZhongDetailListview);
							}else{
								mManoSearchIntroBean.setCount(manoSearchIntroBean.getCount());
//								mManoSearchIntroBean.setPageSize(manoSearchIntroBean.getPageSize());
								if(mRefreshing){
									mManoSearchIntroBean.setPageNo("1");
									mManoSearchIntroBean.getResult().clear();
									mManoSearchIntroBean.getResult().addAll(manoSearchIntroBean.getResult());
								}else if(mLoadmoreing){
									mManoSearchIntroBean.setPageNo(""+Integer.valueOf(mManoSearchIntroBean.getPageNo())+1);
									mManoSearchIntroBean.getResult().addAll(manoSearchIntroBean.getResult());
								}
								myAdapter_TeaDingZhongDetailListview.notifyDataSetChanged();
							}
							onLoad(true);
						}else if(mRefreshing){
							tv_emptydata.setText(R.string.empty_data);
						}
					}else{
						tv_emptydata.setText(R.string.get_data_fail);
					}
					mRefreshing = false;
					mLoadmoreing = false;
					break;
			}
		}

	};
	
	/**
	 * 重新加载
	 * @param flag 数据更新是否成功
	 */
	private void onLoad(boolean flag) {
		if(mManoSearchIntroBean != null && Integer.valueOf(mManoSearchIntroBean.getCount()) == mManoSearchIntroBean.getResult().size())//数据是否全部加载完成
			tea_dingzhong_detail_listview.setPullLoadEnable(false);
		else
			tea_dingzhong_detail_listview.setPullLoadEnable(true);
		if(flag){
			if(mRefreshing){
				String time = Utils.getDateString();
				tea_dingzhong_detail_listview.setRefreshTime(time);
				tea_dingzhong_detail_listview.updateSuccess();
				startTimer();
			}
			if(mLoadmoreing)
				tea_dingzhong_detail_listview.stopLoadMore();
		}else{
			if(mRefreshing){
				tea_dingzhong_detail_listview.updateFail();
				startTimer();
			}
			if(mLoadmoreing){
				tea_dingzhong_detail_listview.loadmoreFail();
			}
		}
	}
	private Timer timer;
	private void startTimer() {
		// TODO Auto-generated method stub
		if(timer == null)
			timer = new Timer(true);
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(HIDE_HEADER);
			}
		}, 1000L);
	}

	private void stopTimer(){
		if(timer != null){
			timer.cancel();
			timer = null;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tea_dingzhong_detail);
		
		all_actionbar_linear_left = (View)findViewById(R.id.all_actionbar_linear_left);
		all_actionbar_linear_left.setOnClickListener(this);
		all_actionbar_name = (TextView)findViewById(R.id.all_actionbar_name);
		all_actionbar_name.setText(getString(R.string.tea_grow_tea));
		all_actionbar_more = (ImageButton) findViewById(R.id.all_actionbar_button_right);
		all_actionbar_more.setOnClickListener(this);
		all_actionbar_more.setVisibility(View.GONE);
		
		tv_emptydata = (TextView)findViewById(R.id.tv_emptydata);
		
		tea_dingzhong_detail_listview = (XListView)findViewById(R.id.tea_dingzhong_detail_listview);
		tea_dingzhong_detail_listview.setEmptyView(tv_emptydata);
		tea_dingzhong_detail_listview.setOnItemClickListener(this);

		tea_dingzhong_detail_listview.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if(!mLoadmoreing && firstVisibleItem + visibleItemCount == totalItemCount && (mManoSearchIntroBean != null && Integer.valueOf(mManoSearchIntroBean.getCount()) > mManoSearchIntroBean.getResult().size())){
					tea_dingzhong_detail_listview.startLoadMore();
					Log.e("initView", "onScroll");
				}
			}
		});
		tea_dingzhong_detail_listview.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				mRefreshing = false;
				tea_dingzhong_detail_listview.stopLoadMore();
				if(mManoSearchIntroBean!= null && mManoSearchIntroBean.getResult().size() > 0){
					mRefreshing = true;
					startGetManorSearch(true);
				}
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				mLoadmoreing = false;
				tea_dingzhong_detail_listview.stopRefresh();
				if(mManoSearchIntroBean!= null && mManoSearchIntroBean.getResult().size() > 0){
					mLoadmoreing = true;
					startGetManorSearch(false);
				}
			}
		});
		CustomProgressDialog.startProgressDialog(this);
		startGetManorSearch(true);
	}
	
	/** 根据订种茶类型搜索茶庄园列表 */
	private void startGetManorSearch(boolean isRefresh) {
		if(TeaDingZhongActivity.mTeaTypeIntro != null){
			List<Bean> list = new ArrayList<Bean>();
			list.add(new Bean("teaType", TeaDingZhongActivity.mTeaTypeIntro.getEname()));
			list.add(new Bean("name", ""));

			mRefreshing = isRefresh;
			if(isRefresh){
				list.add(new Bean("pageNo", "1"));
				list.add(new Bean("pageSize", "10"));
			}else{
				list.add(new Bean("pageNo", String.valueOf(Integer.valueOf(mManoSearchIntroBean.getPageNo())+1)));
				list.add(new Bean("pageSize", "10"));
			}
			startSearchThread(list, Constant.FLAG_GET_MANOR_SEARCH);
		}
	}

	private void startSearchThread(List<Bean> params, int flag) {

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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long position) {
		mManoSearchIntro = mManoSearchIntroBean.getResult().get((int)position);
		startActivity(new Intent(this, TeaDingZhongOrderActivity.class));
		overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
	}
	
	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopTimer();
		if(mThread != null){
			mThread.cancleReturnData();
			mThread = null;
		}
	}

}
