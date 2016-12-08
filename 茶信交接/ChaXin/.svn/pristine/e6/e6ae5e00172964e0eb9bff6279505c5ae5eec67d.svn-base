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
import android.widget.Button;
import android.widget.AbsListView.OnScrollListener;
import android.widget.TextView;

import com.newbrain.adapter.MyAdapter_ZhongChouEarningsRecordListView;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.teazhongchou.SetWithrawPasswdDialog.OnSetWithdrawPasswdListener;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.customview.XListView;
import com.newbrain.customview.XListView.IXListViewListener;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.MyIncomeBean;
import com.newbrain.user.User;
import com.newbrain.utils.Utils;

/**
 * 茶众筹我的收益界面
 * 
 * @author ZY
 * 
 */
public class TeaZhongChouMyEarningsFragment extends Fragment implements
		OnClickListener {

	private TeaZhongChouActivity mParentsActivity;
	private TextView total_earnings_nu_tv;
//	private TextView current_earnings_nu_tv;
	private XListView earnings_record_list;
	private Button withraw_bt;

	private MyIncomeBean mMyINcomeBean;
	private boolean mRefreshing = false;
	private boolean mLoadmoreing = false;
	private final int HIDE_HEADER = -1;
	private MyAdapter_ZhongChouEarningsRecordListView mAdapter;
	private JsonThread mThread;
	private Timer timer;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case HIDE_HEADER:
				earnings_record_list.stopRefresh();
				break;
			case Constant.FLAG_GET_MY_INCOME:
				HttpReturnData mData = (HttpReturnData) msg.obj;
				if (mData.isSuccess()) {
					MyIncomeBean myCrowdfBean = (MyIncomeBean) mData.getObg();
					if (myCrowdfBean.getCode().equals("1")) {
						updataView(myCrowdfBean);
						if (mMyINcomeBean == null) {
							mMyINcomeBean = myCrowdfBean;
							mAdapter = new MyAdapter_ZhongChouEarningsRecordListView(
									mParentsActivity, mMyINcomeBean.getResult());
							showRecordList();
						} else {
							mMyINcomeBean.setCount(myCrowdfBean.getCount());
							mMyINcomeBean.setPageNo(myCrowdfBean.getPageNo());
							mMyINcomeBean.setPageSize(myCrowdfBean
									.getPageSize());
							if (mRefreshing) {
								mMyINcomeBean.getResult().clear();
								mMyINcomeBean.getResult().addAll(
										myCrowdfBean.getResult());
							} else if (mLoadmoreing) {
								mMyINcomeBean.getResult().addAll(
										myCrowdfBean.getResult());
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mParentsActivity = (TeaZhongChouActivity) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(
				R.layout.tea_zhongchou_my_earnings_fragment, container, false);
		mParentsActivity.setTopBar(mParentsActivity.ZHONGCHOU_MY_EARNINGS_PAGE);
		initView(view);
		setListener();
		initData();
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		earnings_record_list = (XListView) view
				.findViewById(R.id.earnings_record_list);
		total_earnings_nu_tv = (TextView) view
				.findViewById(R.id.total_earnings_nu);
		/*current_earnings_nu_tv = (TextView) view
				.findViewById(R.id.current_earnings_nu);*/
		withraw_bt = (Button) view.findViewById(R.id.withraw_bt);
		tv_emptydata = (TextView)view.findViewById(R.id.tv_emptydata);
		
	}

	private void setListener() {
		// TODO Auto-generated method stub

		earnings_record_list.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub

			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if (!mLoadmoreing
						&& firstVisibleItem + visibleItemCount == totalItemCount
						&& (mMyINcomeBean != null && Integer
								.valueOf(mMyINcomeBean.getCount()) > mMyINcomeBean
								.getResult().size())) {
					earnings_record_list.startLoadMore();
					Log.e("initView", "onScroll");
				}
			}
		});
		earnings_record_list.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				mRefreshing = false;
				earnings_record_list.stopLoadMore();
				if (mMyINcomeBean != null
						&& mMyINcomeBean.getResult().size() > 0) {
					mRefreshing = true;
					startGetMyCrowdfundingThread(true);
				}
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				mLoadmoreing = false;
				earnings_record_list.stopRefresh();
				if (mMyINcomeBean != null
						&& mMyINcomeBean.getResult().size() > 0) {
					mLoadmoreing = true;
					startGetMyCrowdfundingThread(false);
				}
			}
		});

		withraw_bt.setOnClickListener(this);
	}

	private void initData() {
		// TODO Auto-generated method stub
		earnings_record_list.setEmptyView(tv_emptydata);
		if (mMyINcomeBean != null) {
			updataView(mMyINcomeBean);
		}
		if (mAdapter == null) {
			CustomProgressDialog.startProgressDialog(mParentsActivity);
			startGetMyCrowdfundingThread(true);
		} else {
			showRecordList();
			onLoad(true);
		}
	}

	/**
	 * 显示收益记录数据
	 */
	private void showRecordList() {
		// TODO Auto-generated method stub
		CustomProgressDialog.stopProgressDialog();
		earnings_record_list.setAdapter(mAdapter);
	}

	/**
	 * 更新界面信息
	 * 
	 * @param myIncomeBean
	 */
	private void updataView(MyIncomeBean myIncomeBean) {
		// TODO Auto-generated method stub
		total_earnings_nu_tv.setText(myIncomeBean.getTotal());
//		current_earnings_nu_tv.setText(myIncomeBean.getCurrent());
		
	}
	/**
	 * 开始获取收益信息线程
	 * 
	 * @param isRefresh
	 */
	private void startGetMyCrowdfundingThread(boolean isRefresh) {
		List<Bean> list = new ArrayList<Bean>();
		list.add(new Bean("userId",User.getInstance().getId()));
		mRefreshing = isRefresh;
		if (isRefresh) {
			list.add(new Bean("pageNo", "1"));
			list.add(new Bean("pageSize", "10"));
		} else {
			list.add(new Bean("pageNo", String.valueOf(Integer
					.valueOf(mMyINcomeBean.getPageNo()) + 1)));
			list.add(new Bean("pageSize", "10"));
		}
		if(mThread != null){
			mThread.cancleReturnData();
		}
		mThread = new JsonThread(mParentsActivity, list, mHandler,
				Constant.FLAG_GET_MY_INCOME);
		mThread.start();
	}

	/**
	 * 重新加载
	 * 
	 * @param flag
	 *            数据更新是否成功
	 */
	private void onLoad(boolean flag) {
		if (mMyINcomeBean != null
				&& Integer.valueOf(mMyINcomeBean.getCount()) == mMyINcomeBean
						.getResult().size())// 数据是否全部加载完成
			earnings_record_list.setPullLoadEnable(false);
		else
			earnings_record_list.setPullLoadEnable(true);
		if (flag) {
			if (mRefreshing) {
				String time = Utils.getDateString();
				earnings_record_list.setRefreshTime(time);
				earnings_record_list.updateSuccess();
				startTimer();
			}
			if (mLoadmoreing)
				earnings_record_list.stopLoadMore();
		} else {
			if (mRefreshing) {
				earnings_record_list.updateFail();
				startTimer();
			}
			if (mLoadmoreing) {
				earnings_record_list.loadmoreFail();
			}
		}
	}

	private void startTimer() {
		// TODO Auto-generated method stub
		if (timer == null)
			timer = new Timer(true);
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(HIDE_HEADER);
			}
		}, 1000L);
	}

	private void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	/**
	 * 显示设置提现密码对话框
	 */
	private void showSetPasswdDialog() {
		// TODO Auto-generated method stub
		SetWithrawPasswdDialog setWithrawPasswdDialog = new SetWithrawPasswdDialog(
				mParentsActivity);
		setWithrawPasswdDialog
				.setOnSetWithdrawPasswdListener(new OnSetWithdrawPasswdListener() {
					@Override
					public void onRightBtListener() {
						// TODO Auto-generated method stub
						mParentsActivity.replaceFragment(
								new TeaZhongChouSetWithdrawDepositFragment(),
								true, false, true);
						// isSetWithdrawPasswd = true;
					}

					@Override
					public void onLeftBtListener() {
						// TODO Auto-generated method stub

					}

					@Override
					public void onDismiss() {
						// TODO Auto-generated method stub

					}
				});
		setWithrawPasswdDialog.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.withraw_bt:
			showSetPasswdDialog();
			break;
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
