package com.newbrain.chaxin.teasearchtea;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.TextView;

import com.newbrain.adapter.MyAdapter_TeaSearchTeaBeautyGridView;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.TeaHouseBeautyIntroListBean;

@SuppressLint("ValidFragment")
public class TeaSearchTeaBeautyFragment extends Fragment {

	private GridView beauty_list;
	private TextView tv_emptydata;
	private TeaSearchTeaActivity mParentsActivity;
	private TeaHouseBeautyIntroListBean mTeaHouseBeautyIntroListBean;
	private MyAdapter_TeaSearchTeaBeautyGridView mAdapter;
	private String mStoreId;
	private JsonThread mThread;
	private boolean mRefreshing = false;
	private boolean mLoadmoreing = false;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case Constant.FLAG_GET_TEAHOUSE_BEAUTY_LIST:
				CustomProgressDialog.stopProgressDialog();
				HttpReturnData mData = (HttpReturnData) msg.obj;
				if(mData.isSuccess()){
					TeaHouseBeautyIntroListBean mBean = (TeaHouseBeautyIntroListBean) mData.getObg();
					if(mBean.getCode().equals("1")){
						if(mTeaHouseBeautyIntroListBean == null  ){
							mTeaHouseBeautyIntroListBean = mBean;
							mAdapter = new MyAdapter_TeaSearchTeaBeautyGridView(mParentsActivity, mTeaHouseBeautyIntroListBean.getResult());
							beauty_list.setAdapter(mAdapter);
						}else{
							mTeaHouseBeautyIntroListBean.setNextPage(mBean.getNextPage());
							if(mRefreshing){
								mTeaHouseBeautyIntroListBean.getResult().clear();
								mTeaHouseBeautyIntroListBean.getResult().addAll(mBean.getResult());
							}else if(mLoadmoreing){
								mTeaHouseBeautyIntroListBean.getResult().addAll(mBean.getResult());
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
	
	public TeaSearchTeaBeautyFragment(String storeId){
		mStoreId = storeId;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mParentsActivity = (TeaSearchTeaActivity) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.tea_search_teabeauty_fragment,container, false);
		initView(view);
		initData();
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		beauty_list = (GridView) view.findViewById(R.id.beauty_gridview);
		tv_emptydata = (TextView) view.findViewById(R.id.tv_emptydata);
		
		beauty_list.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if(!mLoadmoreing && firstVisibleItem + visibleItemCount == totalItemCount && (mTeaHouseBeautyIntroListBean != null && !mTeaHouseBeautyIntroListBean.getNextPage().equals("0"))){
					mLoadmoreing = false;
					if(mTeaHouseBeautyIntroListBean!= null && mTeaHouseBeautyIntroListBean.getResult().size() > 0){
						mLoadmoreing = true;
						startGetTeaBeautyListThread(false);
					}
				}
			}
		});
	}

	private void initData() {
		// TODO Auto-generated method stub
		beauty_list.setEmptyView(tv_emptydata);
		if(mTeaHouseBeautyIntroListBean == null){
			startGetTeaBeautyListThread(true);
		}else{
			beauty_list.setAdapter(mAdapter);
		}
	}

	private void startGetTeaBeautyListThread(boolean isRefreshing){
		CustomProgressDialog.startProgressDialog(mParentsActivity);
		mRefreshing = isRefreshing;
		List<Bean> list = new ArrayList<Bean>();
		if(isRefreshing){
			list.add(new Bean("pageNo", "1"));
		}
		list.add(new Bean("pageSize", "10"));
		list.add(new Bean("storeId", mStoreId));
		if(mThread != null){
			mThread.cancleReturnData();
		}
		mThread = new JsonThread(mParentsActivity, list, mHandler, Constant.FLAG_GET_TEAHOUSE_BEAUTY_LIST);
		mThread.start();
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
