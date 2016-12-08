package com.newbrain.chaxin.teazhongchou;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.teazhongchou.SetWithrawPasswdDialog.OnSetWithdrawPasswdListener;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.customview.DonateOrCrowdfundingDialog;
import com.newbrain.customview.DonateOrCrowdfundingDialog.OnDonateOrCrowdfundingDialogListener;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.BaseJsonDataBean;
import com.newbrain.model.Bean;
import com.newbrain.model.CrowdftotalBean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.user.User;
import com.newbrain.utils.ToastUtil;
import com.newbrain.xutils.Xutils_HttpUtils;

public class TeaZhongChouMainFragment extends Fragment implements
		OnClickListener {

	private TeaZhongChouActivity mParentsActivity;
	// private MyDirectionalViewPager viewpager;
	// private TitlePageIndicator mIndicator;
	private TextView pre_fenhong;
	private TextView total_money_tv;
	private ImageView pool_iv;
	private ImageButton left_ib;
	private ImageButton right_ib;
	private TextView my_zhongchou_tv;
	private TextView my_shouyi_tv;
	private Button crowdfunding_bt;

	private CrowdftotalBean mCrowdftotalBean;
	private boolean isSetWithdrawPasswd = false;
	private HttpUtils httputils;
	private JsonThread mThread;
	private AnimationDrawable mAnimationDrawable;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			HttpReturnData mData;
			switch (msg.what) {
			case Constant.FLAG_GET_CROWDFUNDING:
				CustomProgressDialog.stopProgressDialog();
				mData = (HttpReturnData) msg.obj;
				if (mData.isSuccess()) {
					CrowdftotalBean crowdftotalBean = (CrowdftotalBean) mData
							.getObg();
					if (crowdftotalBean.getCode().equals("1")
							&& (mCrowdftotalBean == null || !mCrowdftotalBean
									.getTotal().equals(
											crowdftotalBean.getTotal()))) {
						mCrowdftotalBean = crowdftotalBean;
						updata();
					}
				}
				break;
			case Constant.FLAG_CROWDFUNDING_INVEST:
				CustomProgressDialog.stopProgressDialog();
				mData = (HttpReturnData) msg.obj;
				if (mData.isSuccess()) {
					BaseJsonDataBean baseJsonDataBean = (BaseJsonDataBean) mData
							.getObg();
					if (baseJsonDataBean.getCode().equals("1")) {
						startGetCrowdfundThread();
						ToastUtil.Toast(mParentsActivity, "众筹成功!");
						break;
					}
				}
				ToastUtil.Toast(mParentsActivity, "众筹失败!");
				break;
				//上期利润
			case 1000:
				showCrowdfundingDialog();
				
				break;
			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		httputils=Xutils_HttpUtils.getHttpUtils(mParentsActivity);
		httputils.configCurrentHttpCacheExpiry(0);
		mParentsActivity = (TeaZhongChouActivity) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		System.out.println("TeaZhongChouMainFragment onCreateView ");
		View view = inflater.inflate(R.layout.tea_zhongchou_main_fragment,
				container, false);
		mParentsActivity.setTopBar(mParentsActivity.ZHONGCHOU_MAIN_PAGE);
		initView(view);
		setListener();
		initData();
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		// viewpager = (MyDirectionalViewPager) view.findViewById(R.id.pager);
		// mIndicator = (TitlePageIndicator) view.findViewById(R.id.indicator);
		pre_fenhong=(TextView) view.findViewById(R.id.earned_money_tv);
		total_money_tv = (TextView) view.findViewById(R.id.total_money_tv);
		pool_iv = (ImageView) view.findViewById(R.id.pool);
		left_ib = (ImageButton) view.findViewById(R.id.left);
		right_ib = (ImageButton) view.findViewById(R.id.right);
		my_zhongchou_tv = (TextView) view.findViewById(R.id.my_zhongchou);
		my_shouyi_tv = (TextView) view.findViewById(R.id.my_shouyi);
		crowdfunding_bt = (Button) view.findViewById(R.id.crowdfunding_bt);
		
	}

	private void setListener() {
		// TODO Auto-generated method stub
		left_ib.setOnClickListener(this);
		right_ib.setOnClickListener(this);
		my_zhongchou_tv.setOnClickListener(this);
		my_shouyi_tv.setOnClickListener(this);
		crowdfunding_bt.setOnClickListener(this);
	}

	private void initData() {
		// TODO Auto-generated method stub
		if (mCrowdftotalBean == null) {
			startGetCrowdfundThread();
		} else {
			total_money_tv.setText(mCrowdftotalBean.getTotal());
			pool_iv.setBackgroundResource(getAnimResId(7));
			startAnimPool();
		}
		httputils.send(HttpMethod.GET,Constant.BASE_URL+"TeaMall/interf/crowdf/lastIncome",new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject=new JSONObject(arg0.result);
					if(jsonObject.getInt("code")==1){
						pre_fenhong.setText(jsonObject.getString("total"));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	private void updata() {
		// TODO Auto-generated method stub
		total_money_tv.setText(mCrowdftotalBean.getTotal());
		pool_iv.setBackgroundResource(getAnimResId(7));
		startAnimPool();
	}

	/**
	 * 开始获取众筹总金额线程
	 */
	private void startGetCrowdfundThread() {
		CustomProgressDialog.startProgressDialog(mParentsActivity);
		List<Bean> list = new ArrayList<Bean>();
		if (mThread != null) {
			mThread.cancleReturnData();
		}
		mThread = new JsonThread(mParentsActivity, list, mHandler,
				Constant.FLAG_GET_CROWDFUNDING);
		mThread.start();
		
	}

	public void startAnimPool() {
		if (pool_iv != null) {
			System.out.println("mAnimationDrawable is null");
			mAnimationDrawable = (AnimationDrawable) pool_iv.getBackground();
		}
		if (mAnimationDrawable != null) {
			if (mAnimationDrawable.isRunning()) {
				mAnimationDrawable.stop();
			}
			mAnimationDrawable.start();
		} else {
			System.out.println("mAnimationDrawable is null ");
		}
	}

	private int getAnimResId(int percent) {
		int resID = R.anim.zhongchou_pool1;
		switch (percent) {
		case 1:
			resID = R.anim.zhongchou_pool1;
			break;
		case 2:
			resID = R.anim.zhongchou_pool2;
			break;
		case 3:
			resID = R.anim.zhongchou_pool3;
			break;
		case 4:
			resID = R.anim.zhongchou_pool4;
			break;
		case 5:
			resID = R.anim.zhongchou_pool5;
			break;
		case 6:
			resID = R.anim.zhongchou_pool6;
			break;
		case 7:
			resID = R.anim.zhongchou_pool7;
			break;
		case 8:
			resID = R.anim.zhongchou_pool8;
			break;
		case 9:
			resID = R.anim.zhongchou_pool9;
			break;
		case 10:
			resID = R.anim.zhongchou_pool10;
			break;
		}
		return resID;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.crowdfunding_bt:
			Message message = mHandler.obtainMessage(1000);
			mHandler.sendMessageDelayed(message, 100);
			
			
			// if(isSetWithdrawPasswd){
			// showCrowdfundingDialog();
			// }else{
			// showSetPasswdDialog();
			// }
			break;
		case R.id.left:
		case R.id.my_zhongchou:
			mParentsActivity.replaceFragment(
					new TeaZhongChouMyCrowdfundingFragment(), true, true, true);
			break;
		case R.id.right:
		case R.id.my_shouyi:
			
			mParentsActivity.replaceFragment(
					new TeaZhongChouMyEarningsFragment(), true, false, true);
			break;
		default:
			break;
		}
	}

	/**
	 * 显示众筹对话框
	 */
	private void showCrowdfundingDialog() {
		// TODO Auto-generated method stub
		DonateOrCrowdfundingDialog crowdfundingDialog = new DonateOrCrowdfundingDialog(
				mParentsActivity, DonateOrCrowdfundingDialog.TYPE_CROWDFUNDING);

		crowdfundingDialog
				.setOnDonateOrCrowdfundingDialogListener(new OnDonateOrCrowdfundingDialogListener() {

					@Override
					public void onCrowdfClick(String nu) {
						// TODO Auto-generated method stub
						CustomProgressDialog
								.startProgressDialog(mParentsActivity);
						List<Bean> list = new ArrayList<Bean>();
						list.add(new Bean("userId", User.getInstance().getId()));
						list.add(new Bean("gold", nu));
						new JsonThread(mParentsActivity, list, mHandler,
								Constant.FLAG_CROWDFUNDING_INVEST).start();
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

					}
				});
		crowdfundingDialog.show();
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
						isSetWithdrawPasswd = true;
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
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mThread != null) {
			mThread.cancleReturnData();
			mThread = null;
		}
	}

}
