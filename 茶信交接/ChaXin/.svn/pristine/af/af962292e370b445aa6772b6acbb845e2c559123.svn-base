package com.newbrain.chaxin.teasearchtea;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.lidroid.xutils.ViewUtils;
import com.newbrain.baseactivity.BaseActivity_FA;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.FindTeaHouseIntroBean;
import com.newbrain.model.FindTeaHouseIntroBean.TeaHouseIntro;
import com.newbrain.model.HttpReturnData;

public class TeaSearchTeaActivity extends BaseActivity_FA implements
		OnClickListener {

	public final int SEARCHTEA_MAIN_PAGE = 0;
	public final int SEARCHTEA_SEARCH_PAGE = 1;
	public final int SEARCHTEA_DETAIL_PAGE = 2;

	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;

	/** 返回 */
	private ImageView iv_back;
	private TextView tv_title;
	/** 进入搜索页面 */
	private ImageView iv_search;

	/** 消息 */
	private ImageView iv_message;

	private View layout_search;
	private ImageView layout_iv_search;
	private EditText layout_et_search;
	private TextView tv_cancel;

	/** 按名字查找茶馆 */
	private JsonThread mThread;

	private TeaSearchTeaMainFrament mTeaSearchTeaMainFrament;
	private TeaSearchTeaSearchFrament mTSearchTeaSearchFrament;
	private TeaSearchTeaDetailMainFragment mTeaSearchTeaDetailMainFragment;

	/* 搜索返回的茶馆列表 */
	private List<TeaHouseIntro> mTeaHouseIntros;

	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constant.FLAG_SEARCH_TEAHOUSE_NAME:

				CustomProgressDialog.stopProgressDialog();
				HttpReturnData reData = (HttpReturnData) msg.obj;
				if (reData.isSuccess()) {
					FindTeaHouseIntroBean findTeaHouseIntroBean = (FindTeaHouseIntroBean) reData
							.getObg();
					if (findTeaHouseIntroBean.getCode().equals("1")) {
						mTeaHouseIntros = findTeaHouseIntroBean.getResult();
						List<String> names = new ArrayList<String>();
						for (int i = 0; i < mTeaHouseIntros.size(); i++) {
							TeaHouseIntro intro = mTeaHouseIntros.get(i);
							names.add(mTeaHouseIntros.get(i).getName());
							Log.e("test",
									intro.getName() + "  "
											+ intro.getDistance());

						}
						mTSearchTeaSearchFrament.setData(names
								.toArray(new String[] {}));
					} else {
						System.out.println("result:"
								+ findTeaHouseIntroBean.getMessage());
					}
				}
				break;
			case Constant.SEARCH_BACK:
				List<TeaHouseIntro> intros = new ArrayList<TeaHouseIntro>();
				intros.add(mTeaHouseIntros.get(msg.arg1));
				Message message = mTeaSearchTeaMainFrament.mHandler
						.obtainMessage(Constant.SEARCH_BACK, mTeaHouseIntros);
				message.arg1 = msg.arg1;
				mTeaSearchTeaMainFrament.mHandler.sendMessage(message);
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tea_search_tea);
		ViewUtils.inject(this);

		fragmentManager = getSupportFragmentManager();
		initView();
		mTeaSearchTeaMainFrament = new TeaSearchTeaMainFrament();
		mTSearchTeaSearchFrament = new TeaSearchTeaSearchFrament();
		replaceFragment(mTeaSearchTeaMainFrament, false, false, false);
	}

	private void initView() {
		iv_back = (ImageView) findViewById(R.id.tea_teasearch_top_back);
		tv_title = (TextView) findViewById(R.id.tea_teasearch_top_title);
		iv_search = (ImageView) findViewById(R.id.tea_teasearch_top_search);
		iv_message = (ImageView) findViewById(R.id.tea_teasearch_top_message);

		layout_search = (View) findViewById(R.id.tea_teasearch_top_layout_search);
		layout_iv_search = (ImageView) findViewById(R.id.tea_teasearch_top_layout_iv_search);
		layout_et_search = (EditText) findViewById(R.id.tea_teasearch_top_layout_et_search);
		tv_cancel = (TextView) findViewById(R.id.tea_teasearch_top_cancel);
		iv_back.setOnClickListener(this);
		iv_search.setOnClickListener(this);
		iv_message.setOnClickListener(this);
		layout_et_search.setOnClickListener(this);
		layout_iv_search.setOnClickListener(this);
		tv_cancel.setOnClickListener(this);
		layout_et_search
				.setOnEditorActionListener(new OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						// TODO Auto-generated method stub
						if (actionId == EditorInfo.IME_ACTION_SEARCH) {
							if (!TextUtils.isEmpty(layout_et_search.getText().toString())) {
								// mTeaSearchTeaMainFrament.startSearchTeaHouseName(layout_et_search.getText().toString());
								List<Bean> list = new ArrayList<Bean>();
								list.add(new Bean("name", layout_et_search.getText().toString()));
								list.add(new Bean("lng", String
										.valueOf(mTeaSearchTeaMainFrament.mPosition.longitude)));
								list.add(new Bean("lat", String
										.valueOf(mTeaSearchTeaMainFrament.mPosition.latitude)));
								mThread = new JsonThread(TeaSearchTeaActivity.this, list, mHandler,
										Constant.FLAG_SEARCH_TEAHOUSE_NAME);
								mThread.start();
							}
							return true;
						}
						return false;

					}
				});

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tea_teasearch_top_back:// 返回
			removeFragerment(true);
			break;
		case R.id.tea_teasearch_top_search:// 搜索
			replaceFragment(mTSearchTeaSearchFrament, true, false, true);
			break;
		case R.id.tea_teasearch_top_message:// 消息
			showMoreDialog(view, false);
			break;
		case R.id.tea_teasearch_top_layout_iv_search:
			if (!TextUtils.isEmpty(layout_et_search.getText().toString())) {
				// mTeaSearchTeaMainFrament.startSearchTeaHouseName(layout_et_search.getText().toString());
				List<Bean> list = new ArrayList<Bean>();
				list.add(new Bean("name", layout_et_search.getText().toString()));
				list.add(new Bean("lng", String
						.valueOf(mTeaSearchTeaMainFrament.mPosition.longitude)));
				list.add(new Bean("lat", String
						.valueOf(mTeaSearchTeaMainFrament.mPosition.latitude)));
				mThread = new JsonThread(this, list, mHandler,
						Constant.FLAG_SEARCH_TEAHOUSE_NAME);
				mThread.start();
			}
			break;
		case R.id.tea_teasearch_top_cancel:
			removeFragerment(true);
			break;
		}
	}

	/**
	 * 设置顶部栏类容
	 * 
	 * @param page_type
	 *            页面类型
	 */
	public void setTopBar(int page_type) {
		switch (page_type) {
		case SEARCHTEA_MAIN_PAGE:
			tv_title.setText(getString(R.string.tea_search_tea));
			setTopViewVisible(View.VISIBLE, View.VISIBLE, View.VISIBLE,
					View.VISIBLE, View.GONE, View.GONE);
			break;

		case SEARCHTEA_SEARCH_PAGE:
			setTopViewVisible(View.GONE, View.GONE, View.GONE, View.GONE,
					View.VISIBLE, View.VISIBLE);
			break;

		case SEARCHTEA_DETAIL_PAGE:
			setTopViewVisible(View.VISIBLE, View.VISIBLE, View.GONE,
					View.VISIBLE, View.GONE, View.GONE);
			break;

		}
	}

	private void setTopViewVisible(int back_status, int title_status,
			int search_status, int message_status, int layout_search_status,
			int cancel_status) {
		iv_back.setVisibility(back_status);
		tv_title.setVisibility(title_status);
		iv_search.setVisibility(search_status);
		iv_message.setVisibility(View.INVISIBLE);

		layout_search.setVisibility(layout_search_status);
		tv_cancel.setVisibility(cancel_status);
	}

	/**
	 * 更换Fragment
	 * 
	 * @param fragment
	 * @param isAnim
	 *            是否开启动画
	 * @param isLeftIn
	 *            是否左边进入
	 * @param isBack
	 *            是否加入回退栈
	 */
	public void replaceFragment(Fragment fragment, boolean isAnim,
			boolean isLeftIn, boolean isBack) {
		fragmentTransaction = fragmentManager.beginTransaction();
		if (isAnim) {
			if (isLeftIn) {
				fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,
						R.anim.slide_out_right, R.anim.slide_in_right,
						R.anim.slide_out_left);
			} else {
				fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,
						R.anim.slide_out_left, R.anim.slide_in_left,
						R.anim.slide_out_right);
			}
		}

		fragmentTransaction.replace(R.id.chaguan_fragment, fragment);
		if (isBack) {
			fragmentTransaction.addToBackStack(null);
		}
		fragmentTransaction.commit();
	}

	/**
	 * 移除当前Fragment
	 * 
	 * @param isAnim
	 *            是否开启动画
	 */
	public void removeFragerment(boolean isAnim) {
		if (!isAnim) {
			fragmentTransaction.setCustomAnimations(0, 0, 0, 0);
		}
		if (fragmentManager.getBackStackEntryCount() > 0) {
			fragmentManager.popBackStack();
		} else {
			finish();
		}
	}
}
