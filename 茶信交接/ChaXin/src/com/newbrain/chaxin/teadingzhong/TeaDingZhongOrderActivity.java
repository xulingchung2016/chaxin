package com.newbrain.chaxin.teadingzhong;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.ManoDetailIntroBean;
import com.newbrain.model.ManoDetailIntroBean.ManoDetailIntro;
import com.newbrain.model.ManoVarietyIntroBean;
import com.newbrain.model.ManoVarietyIntroBean.ManoVarietyIntro;
import com.viewpagerindicator.IconPageIndicator;
import com.viewpagerindicator.ImageListAdapter;
import com.viewpagerindicator.MyDirectionalViewPager;

public class TeaDingZhongOrderActivity extends BaseActivity implements
		OnClickListener {

	/** 返回 */
	private ImageView iv_back;
	/** 消息 */
	private ImageView iv_message;

	/** viewflow的初始化 */

	private MyDirectionalViewPager mMyDirectionalViewPager;
	private IconPageIndicator mIconPageIndicator;

	/** 选择品种 */
	private View mSelectVarieties;

	private TextView mVariety;

	/** 收藏 */
	@ViewInject(R.id.tv_shoucang)
	private TextView tv_shoucang;

	/** 咨询 */
	@ViewInject(R.id.tv_consultation)
	private TextView tv_consultation;

	/** 我要订种 */
	@ViewInject(R.id.btn_order)
	private Button btn_order;

	/** viewpager的初始化 */
	@ViewInject(R.id.directional_viewpager)
	private MyDirectionalViewPager mViewpager;

	private ImageButton all_actionbar_more;
	private View view1, view2;
	private TextView tv_memo;
	private TextView tv_gold;
	private TextView tv_statistics;
	private TextView tv_notice;
	private WebView webView;
	private ArrayList<View> pages = new ArrayList<View>();
	public ManoDetailIntro mManoDetailIntro;

	public List<ManoVarietyIntro> mManoVarietyIntros;
	public ManoVarietyIntro mManoVarietyIntro;
	public int select_variety_index = -1;
	public int mManoVarietyCount = 1;
	public ArrayList<String> mImages;
	private JsonThread mThread;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constant.FLAG_GET_MANOR_DETAIL:
				CustomProgressDialog.stopProgressDialog();
				HttpReturnData reData1 = (HttpReturnData) msg.obj;
				if (reData1.isSuccess()) {
					ManoDetailIntroBean manoDetailIntroBean = (ManoDetailIntroBean) reData1
							.getObg();
					if (manoDetailIntroBean.getCode().equals("1")) {
						mManoDetailIntro = manoDetailIntroBean.getResult();
						tv_memo.setText(mManoDetailIntro.getManor_name());
						tv_gold.setText(mManoDetailIntro.getPrice());
						// tv_statistics.setText(mManoDetailIntro.getStatistics());
						tv_notice.setText(mManoDetailIntro.getNotice());

						if (mImages == null) {
							mImages = new ArrayList<String>();
						}
						mImages.clear();
						// for(int i=0; i<mManoDetailIntro.getImages().size();
						// i++){
						// mImages.add(mManoDetailIntro.getImages().get(i).getImg());
						// }
						mImages.add(mManoDetailIntro.getImage());
						ImageListAdapter adapter = new ImageListAdapter(
								TeaDingZhongOrderActivity.this, mImages);
						mMyDirectionalViewPager.setAdapter(adapter);
						mIconPageIndicator
								.setViewPager(mMyDirectionalViewPager);
						mMyDirectionalViewPager
								.setOrientation(MyDirectionalViewPager.HORIZONTAL);
						mMyDirectionalViewPager.startAutoFlowTimer();
						webView.loadUrl(mManoDetailIntro.getManorIntro());

					} else {
						System.out.println("result:"
								+ manoDetailIntroBean.getMessage());
					}
				}
				break;
			case Constant.FLAG_GET_MANOR_VARIETY:
				CustomProgressDialog.stopProgressDialog();
				HttpReturnData reData2 = (HttpReturnData) msg.obj;
				if (reData2.isSuccess()) {
					ManoVarietyIntroBean manoDetailIntroBean = (ManoVarietyIntroBean) reData2
							.getObg();
					if (manoDetailIntroBean.getCode().equals("1")) {
						mManoVarietyIntros = manoDetailIntroBean.getResult();
					} else {
						System.out.println("result:"
								+ manoDetailIntroBean.getMessage());
					}
				}
				break;
			}
		}

	};

	private static TeaDingZhongOrderActivity mInstance;

	public static TeaDingZhongOrderActivity getInstance() {
		if (mInstance == null) {
			mInstance = new TeaDingZhongOrderActivity();
		}
		return mInstance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tea_dingzhong_order);
		mInstance = this;

		ViewUtils.inject(this);
		view1 = LayoutInflater.from(this).inflate(
				R.layout.view_tea_dingzhong_order_1, null);
		view2 = LayoutInflater.from(this).inflate(
				R.layout.view_tea_dingzhong_order_2, null);

		tv_memo = (TextView) view1.findViewById(R.id.tea_dingzhong_order_title);
		tv_gold = (TextView) view1
				.findViewById(R.id.tea_dingzhong_order_chabi_num);
		tv_statistics = (TextView) view1
				.findViewById(R.id.tea_dingzhong_order_chabi_msg);
		tv_notice = (TextView) view1
				.findViewById(R.id.tea_dingzhong_order_attention);
		webView = (WebView) view1.findViewById(R.id.webview);
		pages.add(view1);
		pages.add(view2);
		initView();
		startGetManorDetail();
		startGetManorVariety();
	}

	private void initView() {
		viewPagerInit();
		viewFlowInit();
		mSelectVarieties = (View) view1
				.findViewById(R.id.tea_dingzhong_order_select_varieties);
		mVariety = (TextView) view1
				.findViewById(R.id.tea_dingzhong_order_variety);
		iv_back = (ImageView) view1.findViewById(R.id.tea_dingzhong_order_back);
		iv_message = (ImageView) view1
				.findViewById(R.id.tea_dingzhong_order_message);
		iv_message.setVisibility(View.GONE);
		WebSettings settings = webView.getSettings();
		//设置WebView属性，能够执行Javascript脚本    
		settings.setJavaScriptEnabled(true);    
        //设置可以访问文件  
		settings.setAllowFileAccess(true);  
         //设置支持缩放  
		settings.setBuiltInZoomControls(true);  
		
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		iv_back.setOnClickListener(this);
		iv_message.setOnClickListener(this);
		mSelectVarieties.setOnClickListener(this);
		tv_shoucang.setOnClickListener(this);
		tv_consultation.setOnClickListener(this);
		btn_order.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mManoVarietyIntro != null) {
			mVariety.setText(mManoVarietyIntro.getVariety_name());
		}
	}

	private void viewPagerInit() {
		mViewpager.setOrientation(MyDirectionalViewPager.VERTICAL);
		mViewpager.setAdapter(new AllPagesAdapter(pages));
	}

	/** viewflowinit初始化 */
	private void viewFlowInit() {
		// TODO Auto-generated method stub

		mIconPageIndicator = (IconPageIndicator) view1
				.findViewById(R.id.tea_dingzhong_order_viewflow_indicator);
		mMyDirectionalViewPager = (MyDirectionalViewPager) view1
				.findViewById(R.id.tea_dingzhong_order_viewflow);
	}

	/** 根据茶庄园ID获取详情 */
	private void startGetManorDetail() {
		if (TeaDingZhongDetailActivity.mManoSearchIntro != null) {
			List<Bean> list = new ArrayList<Bean>();
			list.add(new Bean("manorId",
					TeaDingZhongDetailActivity.mManoSearchIntro.getManor_id()));
			startSearchThread(list, Constant.FLAG_GET_MANOR_DETAIL);
		}
	}

	/** 根据茶庄园ID获取品种列表 */
	private void startGetManorVariety() {
		if (TeaDingZhongDetailActivity.mManoSearchIntro != null) {
			List<Bean> list = new ArrayList<Bean>();
			list.add(new Bean("manorId",
					TeaDingZhongDetailActivity.mManoSearchIntro.getManor_id()));
			// startSearchThread(list, Constant.FLAG_GET_MANOR_VARIETY);
			new JsonThread(this, list, mHandler,
					Constant.FLAG_GET_MANOR_VARIETY).start();
		}
	}

	private void startSearchThread(List<Bean> params, int flag) {
		CustomProgressDialog.startProgressDialog(this);

		if (mThread != null) {
			mThread.cancleReturnData();
		}
		mThread = new JsonThread(this, params, mHandler, flag);
		mThread.start();
	}

	@Override
	public void onClick(View view) {
		iv_back.setOnClickListener(this);
		iv_message.setOnClickListener(this);
		mSelectVarieties.setOnClickListener(this);
		tv_shoucang.setOnClickListener(this);
		tv_consultation.setOnClickListener(this);
		btn_order.setOnClickListener(this);

		switch (view.getId()) {
		case R.id.tea_dingzhong_order_back:// 返回
			onBackPressed();
			break;
		case R.id.tea_dingzhong_order_message:// 消息
			showMoreDialog(view, true);
			break;
		case R.id.tea_dingzhong_order_select_varieties:// 选择品种
			if (mManoVarietyIntros != null) {
				startActivity(new Intent(this, SelectVarietiesDialog.class));
			}
			break;
		case R.id.tv_shoucang:// 收藏
			break;
		case R.id.tv_consultation:// 咨询
			break;
		case R.id.btn_order:// 我要订种
			if (mManoVarietyIntros == null)
				return;
			startActivity(new Intent(this, SelectVarietiesDialog.class));
			
			break;
		}
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mThread != null) {
			mThread.cancleReturnData();
			mThread = null;
		}
		mInstance = null;
	}

}
