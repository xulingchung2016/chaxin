package com.newbrain.chaxin.teacishan;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.newbrain.adapter.MyAdapter_CiShanMyDonateRecordListView;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.TeaCiShanMyDonateDetailListBean;
import com.newbrain.user.User;

public class TeaCiShanMyDonateRecordFragment extends Fragment implements OnClickListener{
	
	private TeaCiShanActivity mParentsActivity;
	private ListView my_donate_record_list;

	private MyAdapter_CiShanMyDonateRecordListView mAdapter;
	private TeaCiShanMyDonateDetailListBean mTeaCiShanMyDonateDetailListBean;
	private boolean mRefreshing = false;
	private boolean mLoadmoreing = false;
	private JsonThread mThread;
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case Constant.FLAG_GET_CISHAN_MYDONATE_LIST:
				CustomProgressDialog.stopProgressDialog();
				HttpReturnData mData = (HttpReturnData) msg.obj;
				if(mData.isSuccess()){
					TeaCiShanMyDonateDetailListBean mBean = (TeaCiShanMyDonateDetailListBean) mData.getObg();
					if(mBean.getCode().equals("1")){
						if(mTeaCiShanMyDonateDetailListBean == null  ){
							mTeaCiShanMyDonateDetailListBean = mBean;
							mAdapter = new MyAdapter_CiShanMyDonateRecordListView(mParentsActivity, mTeaCiShanMyDonateDetailListBean.getResult());
							showRecordList();
						}else{
							mTeaCiShanMyDonateDetailListBean.setCount(mBean.getCount());
							if(mRefreshing){
								mTeaCiShanMyDonateDetailListBean.setPageNo("1");
								mTeaCiShanMyDonateDetailListBean.getResult().clear();
								mTeaCiShanMyDonateDetailListBean.getResult().addAll(mBean.getResult());
							}else if(mLoadmoreing){
								mTeaCiShanMyDonateDetailListBean.setPageNo(""+Integer.valueOf(mTeaCiShanMyDonateDetailListBean.getPageNo())+1);
								mTeaCiShanMyDonateDetailListBean.getResult().addAll(mBean.getResult());
							}
							mAdapter.notifyDataSetChanged();
						}
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
	@Override
	public void onCreate( Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mParentsActivity = (TeaCiShanActivity) getActivity();
	}

	protected void showRecordList() {
		// TODO Auto-generated method stub
		my_donate_record_list.setAdapter(mAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.tea_cishan_my_donate_record_fragment, container, false);
		mParentsActivity.setTopBar(mParentsActivity.CISHAN_MY_DONATE_RECORD_PAGE);
		initView(view);
		setListener();
		initData();
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		my_donate_record_list = (ListView) view.findViewById(R.id.my_donate_record_list);
		tv_emptydata = (TextView)view.findViewById(R.id.tv_emptydata);
	}

	private void setListener() {
		// TODO Auto-generated method stub
		my_donate_record_list.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if(!mLoadmoreing && firstVisibleItem + visibleItemCount == totalItemCount && (mTeaCiShanMyDonateDetailListBean != null && Integer.valueOf(mTeaCiShanMyDonateDetailListBean.getCount()) > mTeaCiShanMyDonateDetailListBean.getResult().size())){
					mLoadmoreing = false;
					if(mTeaCiShanMyDonateDetailListBean!= null && mTeaCiShanMyDonateDetailListBean.getResult().size() > 0){
						mLoadmoreing = true;
						startGetCiShanMyDonateListThread(false);
					}
				}
			}
		});
	}

	private void initData() {
		// TODO Auto-generated method stub
		my_donate_record_list.setEmptyView(tv_emptydata);
		if(mTeaCiShanMyDonateDetailListBean == null){
			CustomProgressDialog.startProgressDialog(mParentsActivity);
			startGetCiShanMyDonateListThread(true);
		}else{
			showRecordList();
		}
	}

	protected void startGetCiShanMyDonateListThread(boolean isRefresh) {
		// TODO Auto-generated method stub

		List<Bean> list = new ArrayList<Bean>();
		list.add(new Bean("userId",User.getInstance().getId()));
		mRefreshing = isRefresh;
		if(isRefresh){
			list.add(new Bean("pageNo", "1"));
			list.add(new Bean("pageSize", "10"));
		}else{
			list.add(new Bean("pageNo", String.valueOf(Integer.valueOf(mTeaCiShanMyDonateDetailListBean.getPageNo())+1)));
			list.add(new Bean("pageSize", "10"));
		}
		if(mThread != null){
			mThread.cancleReturnData();
		}
		mThread = new JsonThread(mParentsActivity, list, mHandler , Constant.FLAG_GET_CISHAN_MYDONATE_LIST);
		mThread.start();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		default:
			break;
		}
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
