package com.newbrain.chaxin.teacishan;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.newbrain.adapter.MyAdapter_CiShanDetailViewPager;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.loginandregister.Login;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.customview.DonateOrCrowdfundingDialog;
import com.newbrain.customview.DonateOrCrowdfundingDialog.OnDonateOrCrowdfundingDialogListener;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.BaseJsonDataBean;
import com.newbrain.model.Bean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.TeaCiShanProjectDetailBean;
import com.newbrain.user.User;
import com.newbrain.utils.ToastUtil;
import com.viewpagerindicator.ImageListAdapter;
import com.viewpagerindicator.MyDirectionalViewPager;

/**
 * 茶慈善项目详情页
 * 
 * @author Administrator
 * 
 */
@SuppressLint("ValidFragment")
public class TeaCiShanProjectDetailFragment extends Fragment implements
		OnClickListener {

	private TeaCiShanActivity mParentsActivity;
	private List<String> mListViewFlow;
	private MyDirectionalViewPager mViewPager;
	private View pager1, pager2;
	private MyDirectionalViewPager mImagePager;
	private WebView webview;
	private TextView total_donate_tv;
	private TextView total_love_tv;
	private TextView title_tv;
	private TextView content_tv;
	private TextView actuator_name_tv;
	private TextView receive_actuator_name_tv;
	private Button donate_bt;
	private String mProjectId;
	private JsonThread mThread;

	private TeaCiShanProjectDetailBean mTeaCiShanProjectDetailBean;
	@SuppressLint("HandlerLeak")
	protected Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case Constant.FLAG_GET_CISHAN_PROJECT_DETAIL:
				CustomProgressDialog.stopProgressDialog();
				HttpReturnData mData = (HttpReturnData) msg.obj;
				if (mData.isSuccess()) {
					TeaCiShanProjectDetailBean mBean = (TeaCiShanProjectDetailBean) mData
							.getObg();
					if (mBean.getCode().equals("1")) {
						mTeaCiShanProjectDetailBean = mBean;
						try {
							if(mTeaCiShanProjectDetailBean.getResult().getStatus().equals("0")){
								donate_bt.setText("已结束");
								donate_bt.setBackgroundResource(R.drawable.tea_cishan_long_bt_grey);
							}else{
								donate_bt.setEnabled(true);
								donate_bt.setBackgroundResource(R.drawable.tea_cishan_long_bt_orange);
							}
						} catch (Exception e) {
							// TODO: handle exception
							
						}
						
						
						
						showRecordList();

						// 11-17 16:22:14.774: E/test(1116): params:10000005

					}
				}
				break;
			case Constant.FLAG_CISHAN_DONATE:
				CustomProgressDialog.stopProgressDialog();
				mData = (HttpReturnData) msg.obj;
				if (mData.isSuccess()) {
					BaseJsonDataBean baseJsonDataBean = (BaseJsonDataBean) mData
							.getObg();
					if (baseJsonDataBean.getCode().equals("1")) {
						ToastUtil.Toast(mParentsActivity, "捐款成功!");
						startGetTeaCiShanProjectDetailThread();
						break;
					}
				}
				ToastUtil.Toast(mParentsActivity, "捐款失败!");
				break;
			}
		}
	};

	public TeaCiShanProjectDetailFragment(String projectId) {
		mProjectId = projectId;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mParentsActivity = (TeaCiShanActivity) getActivity();
	}

	private void showRecordList() {
		// TODO Auto-generated method stub
		mListViewFlow = new ArrayList<String>();
		mListViewFlow.add(mTeaCiShanProjectDetailBean.getResult().getImages());
		// mListViewFlow.add("http://www.tianyutea.com/uploadfiles/others/image/201407/20140708104060566056.jpg");
		// mListViewFlow.add("http://www.tianyutea.com/uploadfiles/others/image/201407/20140708104060566056.jpg");
		// mListViewFlow.add("http://www.tianyutea.com/uploadfiles/others/image/201407/20140708104060566056.jpg");
		ImageListAdapter adapter = new ImageListAdapter(mParentsActivity,
				mListViewFlow);
		mImagePager.setAdapter(adapter);
		mImagePager.startAutoFlowTimer();

		WebSettings settings = webview.getSettings();
		settings.setJavaScriptEnabled(true);
		
		webview.loadUrl(mTeaCiShanProjectDetailBean.getResult().getDescription());
		// 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}
		});

		total_donate_tv.setText(mTeaCiShanProjectDetailBean.getResult()
				.getTotal_gold());
		total_love_tv.setText(mTeaCiShanProjectDetailBean.getResult()
				.getTotal_count());
		title_tv.setText(mTeaCiShanProjectDetailBean.getResult().getTitle());
		content_tv.setText(mTeaCiShanProjectDetailBean.getResult().getDigest());
		actuator_name_tv.setText(mTeaCiShanProjectDetailBean.getResult()
				.getActuator());
		receive_actuator_name_tv.setText(mTeaCiShanProjectDetailBean
				.getResult().getReceiver());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(
				R.layout.tea_cishan_project_detail_fragment, container, false);
		mParentsActivity.setTopBar(mParentsActivity.CISHAN_PROJECT_DETAIL_PAGE);
		pager1 = inflater.inflate(R.layout.tea_cishan_project_detail_page1,
				null);
		pager2 = inflater.inflate(R.layout.tea_cishan_project_detail_page2,
				null);
		initViewPager1(pager1);
		initViewPager2(pager2);
		initView(view);
		setListener();
		initData();
		return view;
	}

	private void initViewPager1(View pager1) {
		// TODO Auto-generated method stub
		mImagePager = (MyDirectionalViewPager) pager1
				.findViewById(R.id.tea_cishan_project_detail_imagepager);

		total_donate_tv = (TextView) pager1.findViewById(R.id.total_donate);
		total_love_tv = (TextView) pager1.findViewById(R.id.total_love);
		title_tv = (TextView) pager1.findViewById(R.id.content_title);
		content_tv = (TextView) pager1.findViewById(R.id.content);
		actuator_name_tv = (TextView) pager1.findViewById(R.id.actuator_name);
		receive_actuator_name_tv = (TextView) pager1
				.findViewById(R.id.receive_actuator_name);
	}

	private void initViewPager2(View pager2) {
		// TODO Auto-generated method stub
		webview = (WebView) pager2.findViewById(R.id.webview);

	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		mViewPager = (MyDirectionalViewPager) view.findViewById(R.id.viewpager);
		donate_bt = (Button) view.findViewById(R.id.donate_bt);
	}

	private void setListener() {
		// TODO Auto-generated method stub
		donate_bt.setOnClickListener(this);
		if (mTeaCiShanProjectDetailBean == null) {
			donate_bt.setEnabled(false);
		}
	}

	private void initData() {
		// TODO Auto-generated method stub
		List<View> listViews = new ArrayList<View>();
		listViews.add(pager1);
		listViews.add(pager2);
		mViewPager.setOrientation(MyDirectionalViewPager.VERTICAL);
		mViewPager.setAdapter(new MyAdapter_CiShanDetailViewPager(listViews));
		if (mTeaCiShanProjectDetailBean == null) {
			startGetTeaCiShanProjectDetailThread();
		} else {
			showRecordList();
		}
	}

	private void startGetTeaCiShanProjectDetailThread() {
		// TODO Auto-generated method stub
		CustomProgressDialog.startProgressDialog(mParentsActivity);
		List<Bean> list = new ArrayList<Bean>();
		list.add(new Bean("projectId", mProjectId));
		new JsonThread(mParentsActivity, list, mHandler,
				Constant.FLAG_GET_CISHAN_PROJECT_DETAIL).start();
	}

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
			if (mThread != null) {
				mThread.cancleReturnData();
			}
			mThread = new JsonThread(mParentsActivity, list, mHandler,
					Constant.FLAG_CISHAN_DONATE);
			mThread.start();
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.donate_bt:
			if(!User.getInstance().isLogin()){
				Intent intent=new Intent(mParentsActivity,Login.class);
				startActivity(intent);
				return;
			}
			if (mTeaCiShanProjectDetailBean != null) {
				DonateOrCrowdfundingDialog donateOrCrowdfundingDialog = new DonateOrCrowdfundingDialog(
						mParentsActivity);
				donateOrCrowdfundingDialog
						.setOnDonateOrCrowdfundingDialogListener(mOnDialogListener);
				donateOrCrowdfundingDialog
						.setProjectId(mTeaCiShanProjectDetailBean.getResult()
								.getProject_id());
				donateOrCrowdfundingDialog.show();
			}
			break;
		default:
			break;
		}
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
