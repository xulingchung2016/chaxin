package com.newbrain.chaxin.teacishan;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newbrain.adapter.MyAdapter_CiShanMyDonateListView;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.customview.DonateOrCrowdfundingDialog.OnDonateOrCrowdfundingDialogListener;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.BaseJsonDataBean;
import com.newbrain.model.Bean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.TeaCiShanMyDonateBean;
import com.newbrain.user.User;
import com.newbrain.utils.ToastUtil;

public class TeaCiShanMyDonateFragment extends Fragment implements OnClickListener{
	
	private TeaCiShanActivity mParentsActivity;
	private ListView my_donate_list;
	private RelativeLayout into_donate_recored;
	private TextView donate_nu_tv;
	private TextView total_money_tv;
	
	private MyAdapter_CiShanMyDonateListView mAdapter;
	private TeaCiShanMyDonateBean mTeaCiShanListProjectBean;
	private boolean mRefreshing = false;
	private boolean mLoadmoreing = false;
	private JsonThread mThread;
	private OnDonateOrCrowdfundingDialogListener mOnDialogListener = new OnDonateOrCrowdfundingDialogListener() {
		
		@Override
		public void onCrowdfClick(String nu) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onDismiss() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onCancleClick() {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onDonateClick(String nu, String projectId) {
			// TODO Auto-generated method stub
			CustomProgressDialog.startProgressDialog(mParentsActivity);
			List<Bean> list = new ArrayList<Bean>();
			list.add(new Bean("projectId", projectId));
			list.add(new Bean("userId", User.getInstance().getId()));
			list.add(new Bean("gold", nu));
			new JsonThread(mParentsActivity, list, mHandler, Constant.FLAG_CISHAN_DONATE).start();
		}
	};
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case Constant.FLAG_GET_CISHAN_MYDONATE:
				CustomProgressDialog.stopProgressDialog();
				HttpReturnData mData = (HttpReturnData) msg.obj;
				if(mData.isSuccess()){
					TeaCiShanMyDonateBean mBean = (TeaCiShanMyDonateBean) mData.getObg();
					if(mBean.getCode().equals("1")){
						if(mTeaCiShanListProjectBean == null  ){
							mTeaCiShanListProjectBean = mBean;
							mAdapter = new MyAdapter_CiShanMyDonateListView(mParentsActivity, mTeaCiShanListProjectBean.getResult(),mOnDialogListener );
							Log.e("test",mTeaCiShanListProjectBean.getResult().get(0).getStatus());
							showRecordList();
						}else{
							mTeaCiShanListProjectBean.setCount(mBean.getCount());
							if(mRefreshing){
								mTeaCiShanListProjectBean.setPageNo("1");
								mTeaCiShanListProjectBean.getResult().clear();
								mTeaCiShanListProjectBean.getResult().addAll(mBean.getResult());
							}else if(mLoadmoreing){
								mTeaCiShanListProjectBean.setPageNo(""+Integer.valueOf(mTeaCiShanListProjectBean.getPageNo())+1);
								mTeaCiShanListProjectBean.getResult().addAll(mBean.getResult());
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
			case Constant.FLAG_CISHAN_DONATE:
				CustomProgressDialog.stopProgressDialog();
				mData = (HttpReturnData) msg.obj;
				if(mData.isSuccess()){
					BaseJsonDataBean baseJsonDataBean = (BaseJsonDataBean) mData.getObg();
					if(baseJsonDataBean.getCode().equals("1")){
						startGetCiShanListThread(true);
						ToastUtil.Toast(mParentsActivity, "捐款成功!");
						break;
					}
				}
				ToastUtil.Toast(mParentsActivity, "捐款失败!");
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
		donate_nu_tv.setText(mTeaCiShanListProjectBean.getDonate_count());
		total_money_tv.setText(mTeaCiShanListProjectBean.getGold_total());
		my_donate_list.setAdapter(mAdapter);
	}

	@Override
	
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.tea_cishan_my_donate_fragment, container, false);
		mParentsActivity.setTopBar(mParentsActivity.CISHAN_MY_DONATE_PAGE);
		initView(view);
		setlistener();
		initData();
		return view;
	}
	private void initView(View view) {
		// TODO Auto-generated method stub
		my_donate_list = (ListView) view.findViewById(R.id.my_donate_list);
		into_donate_recored = (RelativeLayout) view.findViewById(R.id.into_donate_recored);

		donate_nu_tv = (TextView) view.findViewById(R.id.donate_nu);
		total_money_tv = (TextView) view.findViewById(R.id.total_money);
		tv_emptydata = (TextView)view.findViewById(R.id.tv_emptydata);
	}

	private void setlistener() {
		// TODO Auto-generated method stub
		my_donate_list.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if(!mLoadmoreing && firstVisibleItem + visibleItemCount == totalItemCount && (mTeaCiShanListProjectBean != null && Integer.valueOf(mTeaCiShanListProjectBean.getCount()) > mTeaCiShanListProjectBean.getResult().size())){
					mLoadmoreing = false;
					if(mTeaCiShanListProjectBean!= null && mTeaCiShanListProjectBean.getResult().size() > 0){
						mLoadmoreing = true;
						startGetCiShanListThread(false);
					}
				}
			}
		});
		
		my_donate_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.e("test",mTeaCiShanListProjectBean.getResult().get(position).getProject_id() );
				mParentsActivity.replaceFragment(new TeaCiShanProjectDetailFragment(mTeaCiShanListProjectBean.getResult().get(position).getProject_id()), true, false, true);
			}
		});
	}

	protected void startGetCiShanListThread(boolean isRefresh) {
		// TODO Auto-generated method stub

		List<Bean> list = new ArrayList<Bean>();
		list.add(new Bean("userId",User.getInstance().getId()));
		mRefreshing = isRefresh;
		if(isRefresh){
			list.add(new Bean("pageNo", "1"));
			list.add(new Bean("pageSize", "10"));
		}else{
			list.add(new Bean("pageNo", String.valueOf(Integer.valueOf(mTeaCiShanListProjectBean.getPageNo())+1)));
			list.add(new Bean("pageSize", "10"));
		}
		if(mThread != null){
			mThread.cancleReturnData();
		}
		mThread = new JsonThread(mParentsActivity, list, mHandler , Constant.FLAG_GET_CISHAN_MYDONATE);
		mThread.start();
	}
	
	private void initData() {
		// TODO Auto-generated method stub
		into_donate_recored.setOnClickListener(this);
		my_donate_list.setEmptyView(tv_emptydata);
		if(mTeaCiShanListProjectBean == null){
			CustomProgressDialog.startProgressDialog(mParentsActivity);
			startGetCiShanListThread(true);
		}else{
			showRecordList();
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.into_donate_recored:
//			mParentsActivity.replaceFragment(new TeaCiShanMyDonateRecordFragment(), true, false, true);
			break;

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
