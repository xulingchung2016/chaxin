package com.newbrain.chaxin.my;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.adapter.Model;
import com.newbrain.adapter.MyBaseAdapter_CollectBeauty;
import com.newbrain.adapter.MyBaseAdapter_MySmallTea;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.teadingzhong.TeaDingZhongDetailActivity;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.customview.XListView;
import com.newbrain.customview.XListView.IXListViewListener;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.MySmallTeaOrderListBean;
import com.newbrain.model.MySmallTeaOrderListBean.MySmallTeaOrder;
import com.newbrain.user.User;
import com.newbrain.utils.ToastUtil;
import com.newbrain.utils.Utils;

public class MySmallTeaActivity extends BaseActivity {

	private Context context;

	private List<MySmallTeaOrder> mySmallTeaOrders;
	private MySmallTeaOrderListBean mySmallTeaOrderListBean;
	private JsonThread mThread;
	private boolean mRefreshing = false;
	private boolean mLoadmoreing = false;
	private Timer timer;
	private final int HIDE_HEADER = -1;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HIDE_HEADER:
				mLv.stopRefresh();
				break;
			case Constant.FLAG_GET_GET_MY_SMALL_TEA_ORDER:
				CustomProgressDialog.stopProgressDialog();
				HttpReturnData reData1 = (HttpReturnData) msg.obj;
				if (reData1.isSuccess()) {

					mySmallTeaOrderListBean = (MySmallTeaOrderListBean) reData1
							.getObg();
					if (mySmallTeaOrderListBean.getCode().equals("1")) {

						mySmallTeaOrders = mySmallTeaOrderListBean.getResult();

						myAdapter = new MyBaseAdapter_MySmallTea(context,
								mySmallTeaOrders);

						mLv.setAdapter(myAdapter);
						
						onLoad(true);
					}
					mRefreshing = false;
					mLoadmoreing = false;
				}
				
				break;
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_small_tea_activity);
		ViewUtils.inject(this);
		context = this;
		actionbarInit();
		setListener();
		startGetMySmallTeaOrder(true);
	}

	@ViewInject(R.id.small_tea_listview)
	private XListView mLv;

	private MyBaseAdapter_MySmallTea myAdapter;

	@ViewInject(R.id.all_actionbar_linear_left)
	private LinearLayout actionbar_ll_left;

	@ViewInject(R.id.all_actionbar_textview_back_name)
	private TextView actionbar_tv_back_name_left;

	@ViewInject(R.id.all_actionbar_name)
	private TextView actionbar_tv_name_center;

	@ViewInject(R.id.all_actionbar_button_right_left)
	private Button actionbar_btn_right_left;

	@ViewInject(R.id.all_actionbar_button_right)
	private ImageButton actionbar_imgbtn_right;

	private void actionbarInit() {
		// TODO Auto-generated method stub

		actionbar_ll_left.setVisibility(View.VISIBLE);
		actionbar_tv_back_name_left.setVisibility(View.GONE);
		// actionbar_tv_name_center.setVisibility(View.VISIBLE);

		actionbar_btn_right_left.setVisibility(View.GONE);
		actionbar_imgbtn_right.setVisibility(View.GONE);
		// actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText(getString(R.string.my_small_tea));
		actionbar_btn_right_left.setText(getString(R.string.rule));

		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
		actionbar_imgbtn_right.setOnClickListener(clickListener_actionbar);

	}

	private OnClickListener clickListener_actionbar = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			switch (v.getId()) {
			case R.id.all_actionbar_linear_left:

				finish();

				break;

			case R.id.all_actionbar_button_right:

				break;
			default:
				break;
			}

		}
	};

	private void startGetMySmallTeaOrder(boolean isRefresh) {
		CustomProgressDialog.startProgressDialog(this);
		List<Bean> list = new ArrayList<Bean>();
		list.add(new Bean("userId", User.getInstance().getId()));
		if(isRefresh){
			list.add(new Bean("pageNo", "1"));
		}else{
			list.add(new Bean("pageNo",String.valueOf(Integer.valueOf(mySmallTeaOrderListBean.getPageNo())+1)));
		}
		list.add(new Bean("pageSize", "1000"));
		if(mThread != null){
			mThread.cancleReturnData();
		}
		mThread=new JsonThread(this, list, mHandler,
				Constant.FLAG_GET_GET_MY_SMALL_TEA_ORDER);
		mThread.start();
	}

	private void setListener() {
		mLv.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
//				Log.e("test",""+ mLoadmoreing  + "     "+ (firstVisibleItem + visibleItemCount == totalItemCount)+"      "+  (mySmallTeaOrderListBean != null)+"    "+(Integer.valueOf(mySmallTeaOrderListBean.getCount()) > mySmallTeaOrderListBean.getResult().size()));
				Log.e("test","mLoadmoreing   "+!mLoadmoreing);
				Log.e("test","totalItemCount   "+(firstVisibleItem + visibleItemCount == totalItemCount));
				
				if(mySmallTeaOrderListBean!=null){
					Log.e("test","mySmallTeaOrderListBean   "+(Integer
							.valueOf(mySmallTeaOrderListBean.getCount()) > mySmallTeaOrderListBean
							.getResult().size()));
				}
				if (!mLoadmoreing
						&& firstVisibleItem + visibleItemCount == totalItemCount
						&& (mySmallTeaOrderListBean != null && Integer
								.valueOf(mySmallTeaOrderListBean.getCount()) > mySmallTeaOrderListBean
								.getResult().size())) {
					Log.e("test","上啦加载");
					mLv.startLoadMore();
					Log.e("initView", "onScroll");
				}
			}
		});
		mLv.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				mRefreshing = false;
				mLv.stopLoadMore();
				if (mySmallTeaOrderListBean != null
						&& mySmallTeaOrderListBean.getResult().size() > 0) {
					mRefreshing = true;
					startGetMySmallTeaOrder(true);
				}
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				Log.e("test", "上啦加载");
				mLoadmoreing = false;
				mLv.stopRefresh();
				if (mySmallTeaOrderListBean != null
						&& mySmallTeaOrderListBean.getResult().size() > 0) {
					mLoadmoreing = true;
					startGetMySmallTeaOrder(false);
				}
			}
		});
	}
	
	/**
	 * 重新加载
	 * @param flag 数据更新是否成功
	 */
	private void onLoad(boolean flag) {
		if(mySmallTeaOrderListBean != null && Integer.valueOf(mySmallTeaOrderListBean.getCount()) == mySmallTeaOrderListBean.getResult().size())//数据是否全部加载完成
			mLv.setPullLoadEnable(false);
		else
			mLv.setPullLoadEnable(true);
		if(flag){
			if(mRefreshing){
				String time = Utils.getDateString();
				mLv.setRefreshTime(time);
				mLv.updateSuccess();
				startTimer();
			}
			if(mLoadmoreing)
				mLv.stopLoadMore();
		}else{
			if(mRefreshing){
				mLv.updateFail();
				startTimer();
			}
			if(mLoadmoreing){
				mLv.loadmoreFail();
			}
		}
	}
	
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
	public void onDestroy() {
		// TODO Auto-generated method stub
		stopTimer();
		super.onDestroy();
	}
}
