package com.newbrain.chaxin.teazhongchou;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.newbrain.adapter.MyAdapter_ZhongChouCrowdfundingRecordListView;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.customview.XListView;
import com.newbrain.customview.XListView.IXListViewListener;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.CrowdftotalBean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.MyCrowdfBean;
import com.newbrain.user.User;
import com.newbrain.utils.Utils;
/**
 * 茶众筹我的众筹界面
 * @author ZY
 *
 */
public class TeaZhongChouMyCrowdfundingFragment extends Fragment implements OnClickListener{

	private TeaZhongChouActivity mParentsActivity;
	private XListView crowdfunding_record_list;
	private TextView total_money_tv;
	private boolean mRefreshing = false;
	private boolean mLoadmoreing = false;
	private final int HIDE_HEADER = -1;
	private MyCrowdfBean mMyCrowdfBean;
	private MyAdapter_ZhongChouCrowdfundingRecordListView mAdapter;
	private Timer timer;
	private JsonThread mThread;
	private JsonThread mThread2;
	
	
	private CrowdftotalBean mCrowdftotalBean;
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case HIDE_HEADER:
				crowdfunding_record_list.stopRefresh();
				break;
			case Constant.FLAG_GET_MY_CROWDFUNDING:
				HttpReturnData mData = (HttpReturnData) msg.obj;
				if(mData.isSuccess()){
					MyCrowdfBean myCrowdfBean = (MyCrowdfBean) mData.getObg();
					if(myCrowdfBean.getCode().equals("1")){
						if(mMyCrowdfBean == null  ){
							mMyCrowdfBean = myCrowdfBean;
							total_money_tv.setText(""+mMyCrowdfBean.getTotal());
							mAdapter = new MyAdapter_ZhongChouCrowdfundingRecordListView(mParentsActivity, mMyCrowdfBean.getResult());
							showRecordList();
						}else{
							total_money_tv.setText(""+mMyCrowdfBean.getTotal());
							mMyCrowdfBean.setCount(myCrowdfBean.getCount());
							mMyCrowdfBean.setPageNo(myCrowdfBean.getPageNo());
							mMyCrowdfBean.setPageSize(myCrowdfBean.getPageSize());
							if(mRefreshing){
								mMyCrowdfBean.getResult().clear();
								mMyCrowdfBean.getResult().addAll(myCrowdfBean.getResult());
							}else if(mLoadmoreing){
								mMyCrowdfBean.getResult().addAll(myCrowdfBean.getResult());
							}
							mAdapter.notifyDataSetChanged();
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
	private TextView tv_emptydata;

	/**
	 * 显示众筹记录数据
	 */
	private void showRecordList() {
		// TODO Auto-generated method stub
		CustomProgressDialog.stopProgressDialog();
		crowdfunding_record_list.setAdapter(mAdapter);
	}
	
	@Override
	public void onCreate( Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mParentsActivity = (TeaZhongChouActivity) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.tea_zhongchou_my_crowdfunding_fragment, container, false);
		mParentsActivity.setTopBar(mParentsActivity.ZHONGCHOU_MY_CROWDFUNDING_PAGE);
		initView(view);
		setListener();
		initData();
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		crowdfunding_record_list = (XListView) view.findViewById(R.id.crowdfunding_record_list);
		tv_emptydata = (TextView)view.findViewById(R.id.tv_emptydata);
		total_money_tv=(TextView) view.findViewById(R.id.total_money_tv);
	}

	private void setListener() {
		// TODO Auto-generated method stub
		crowdfunding_record_list.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if(!mLoadmoreing && firstVisibleItem + visibleItemCount == totalItemCount && (mMyCrowdfBean != null && Integer.valueOf(mMyCrowdfBean.getCount()) > mMyCrowdfBean.getResult().size())){
					crowdfunding_record_list.startLoadMore();
					Log.e("initView", "onScroll");
				}
			}
		});
		crowdfunding_record_list.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				mRefreshing = false;
				crowdfunding_record_list.stopLoadMore();
				if(mMyCrowdfBean!= null && mMyCrowdfBean.getResult().size() > 0){
					mRefreshing = true;
					startGetMyCrowdfundingThread(true);
				}
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				mLoadmoreing = false;
				crowdfunding_record_list.stopRefresh();
				if(mMyCrowdfBean!= null && mMyCrowdfBean.getResult().size() > 0){
					mLoadmoreing = true;
					startGetMyCrowdfundingThread(false);
				}
			}
		});
	}

	private void initData() {
		// TODO Auto-generated method stub
		crowdfunding_record_list.setEmptyView(tv_emptydata);
		if(mAdapter == null){
			CustomProgressDialog.startProgressDialog(mParentsActivity);
			startGetMyCrowdfundingThread(true);
		}else{
			showRecordList();
			onLoad(true);
		}
		
		
//		crowdfunding_record_list.setAdapter(new MyAdapter_ZhongChouCrowdfundingRecordListView(mParentsActivity, null));
	}

	/***
	 * 开始获取众筹总金额线程
	 */
	private void startGetCrowdfundThread(){
		CustomProgressDialog.startProgressDialog(mParentsActivity);
		List<Bean> list = new ArrayList<Bean>(); 

		if(mThread2 != null){
			mThread.cancleReturnData();
		}
		mThread2 = new JsonThread(mParentsActivity, list, mHandler , Constant.FLAG_GET_CROWDFUNDING);
		mThread2.start();
	}
	
	
	private void startGetMyCrowdfundingThread(boolean isRefresh){
		List<Bean> list = new ArrayList<Bean>();
		list.add(new Bean("userId", User.getInstance().getId()));
		mRefreshing = isRefresh;
		if(isRefresh){
			list.add(new Bean("pageNo", "1"));
			list.add(new Bean("pageSize", "10"));
		}else{
			list.add(new Bean("pageNo", String.valueOf(Integer.valueOf(mMyCrowdfBean.getPageNo())+1)));
			list.add(new Bean("pageSize", "10"));
		}

		if(mThread != null){
			mThread.cancleReturnData();
		}
		mThread = new JsonThread(mParentsActivity, list, mHandler, Constant.FLAG_GET_MY_CROWDFUNDING);
		mThread.start();
	}
	
	/**
	 * 重新加载
	 * @param flag 数据更新是否成功
	 */
	private void onLoad(boolean flag) {
		if(mMyCrowdfBean != null && Integer.valueOf(mMyCrowdfBean.getCount()) == mMyCrowdfBean.getResult().size())//数据是否全部加载完成
			crowdfunding_record_list.setPullLoadEnable(false);
		else
			crowdfunding_record_list.setPullLoadEnable(true);
		if(flag){
			if(mRefreshing){
				String time = Utils.getDateString();
				crowdfunding_record_list.setRefreshTime(time);
				crowdfunding_record_list.updateSuccess();
				startTimer();
			}
			if(mLoadmoreing)
				crowdfunding_record_list.stopLoadMore();
		}else{
			if(mRefreshing){
				crowdfunding_record_list.updateFail();
				startTimer();
			}
			if(mLoadmoreing){
				crowdfunding_record_list.loadmoreFail();
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		default:
			break;
		}
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
