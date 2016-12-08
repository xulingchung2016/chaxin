package com.newbrain.chaxin.teacishan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newbrain.baseactivity.BaseActivity_FA;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.loginandregister.Login;
import com.newbrain.user.User;

public class TeaCiShanActivity extends BaseActivity_FA implements OnClickListener{

	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	public final int CISHAN_MAIN_PAGE = 0;
	public final int CISHAN_MY_DONATE_PAGE = 1;
	public final int CISHAN_MY_DONATE_RECORD_PAGE = 2;
	public final int CISHAN_PROJECT_DETAIL_PAGE = 3;
	private boolean mRefreshing = false;
	private boolean mLoadmoreing = false;
	private RelativeLayout top_bar;
	@SuppressLint("CommitTransaction")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacishan);
		fragmentManager = getSupportFragmentManager();
		initView();
		replaceFragment(new TeaCiShanMainFragment(), false, false, false);
	}
	private void initView() {
		// TODO Auto-generated method stub
		top_bar = (RelativeLayout) findViewById(R.id.cishan_top_bar);
		top_bar.findViewById(R.id.tea_cishan_back).setOnClickListener(this);
		top_bar.findViewById(R.id.tea_cishan_right_bt).setOnClickListener(this);
		top_bar.findViewById(R.id.tea_cishan_more).setOnClickListener(this);
	}
	/**
	 * 设置顶部栏类容
	 * @param page_type 页面类型
	 */
	public void setTopBar(int page_type){
		TextView title_tv = (TextView) top_bar.findViewById(R.id.tea_cishan_title);
		TextView right_bt = (Button) top_bar.findViewById(R.id.tea_cishan_right_bt);
		right_bt.setVisibility(View.INVISIBLE);
		ImageView more = (ImageView) top_bar.findViewById(R.id.tea_cishan_more);
		more.setVisibility(View.INVISIBLE);
		switch(page_type){
		case CISHAN_MAIN_PAGE:
			title_tv.setText(getString(R.string.tea_charity));
			right_bt.setText(getString(R.string.my_donate));
			right_bt.setVisibility(View.VISIBLE);
			break;
		case CISHAN_MY_DONATE_PAGE:
			title_tv.setText(getString(R.string.my_donate));
			more.setVisibility(View.INVISIBLE);
			break;
		case CISHAN_MY_DONATE_RECORD_PAGE:
			title_tv.setText(getString(R.string.my_donate_record));
			right_bt.setText(getString(R.string.share));
			right_bt.setVisibility(View.VISIBLE);
			break;
		case CISHAN_PROJECT_DETAIL_PAGE:
			title_tv.setText(getString(R.string.project_detail));
			right_bt.setText(getString(R.string.share));
			right_bt.setVisibility(View.INVISIBLE);
			break;
		}
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
		fragmentTransaction.replace(R.id.cishan_fragment, fragment);
		if (isBack) {
			fragmentTransaction.addToBackStack(null);
		}
		fragmentTransaction.commit();
	}

	/**
	 * 移除当前Fragment
	 * @param isAnim 是否开启动画
	 */
	public void removeFragerment(boolean isAnim){
		if(!isAnim){
			fragmentTransaction.setCustomAnimations(0,0,0,0);
		}
		if(fragmentManager.getBackStackEntryCount() > 0){
			fragmentManager.popBackStack();
		}else{
			finish();
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.tea_cishan_back:
			removeFragerment(true);
			break;
		case R.id.tea_cishan_more:
			showMoreDialog(v, false);
			break;
		case R.id.tea_cishan_right_bt:
			String text = ((Button)v).getText().toString();
			if(text.equals(getString(R.string.my_donate))){
				if(!User.getInstance().isLogin()){
					Intent intent=new Intent(TeaCiShanActivity.this,Login.class);
					startActivity(intent);
					return;
				}
				
				replaceFragment(new TeaCiShanMyDonateFragment(), true, false, true);
				
				
				
			}else if(text.equals(getString(R.string.share))){
				
			}
			break;
		}
	}

}
