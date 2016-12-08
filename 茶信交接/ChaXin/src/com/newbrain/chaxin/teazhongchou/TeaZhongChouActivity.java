package com.newbrain.chaxin.teazhongchou;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.newbrain.baseactivity.BaseActivity_FA;
import com.newbrain.chaxin.R;
import com.newbrain.customview.MoreDialog;

public class TeaZhongChouActivity extends BaseActivity_FA implements OnClickListener{

	private TextView title_tv;
	private TextView rule_bt;
	private ImageView more;
	private ImageView back;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	/**茶众筹主页面*/
	public final int ZHONGCHOU_MAIN_PAGE = 0;
	/**茶众筹我的众筹页面*/
	public final int ZHONGCHOU_MY_CROWDFUNDING_PAGE = 1;
	/**茶众筹我的收益页面*/
	public final int ZHONGCHOU_MY_EARNINGS_PAGE = 2;
	/**茶众筹提现页面*/
	public final int ZHONGCHOU_SET_WITHDRAW_DEPOSIT_PAGE = 3;
	
	private RelativeLayout top_bar;
	
	@SuppressLint("CommitTransaction")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teazhongchou);
		int type = getIntent().getIntExtra("type",ZHONGCHOU_MAIN_PAGE);
		fragmentManager = getSupportFragmentManager();
		initView();
		setListener();
		initFragment(type);
	}
	
	/**初始化进入众筹模块第一个界面*/
	private void initFragment(int type) {
		// TODO Auto-generated method stub
		switch (type) {
		case ZHONGCHOU_MAIN_PAGE:
			replaceFragment(new TeaZhongChouMainFragment(), false, false, false);
			break;
		case ZHONGCHOU_MY_CROWDFUNDING_PAGE:
			break;
		case ZHONGCHOU_MY_EARNINGS_PAGE:
			replaceFragment(new TeaZhongChouMyEarningsFragment(), false, false, false);
			break;
		case ZHONGCHOU_SET_WITHDRAW_DEPOSIT_PAGE:
			replaceFragment(new TeaZhongChouSetWithdrawDepositFragment(), false, false, false);
			break;

		default:
			replaceFragment(new TeaZhongChouMainFragment(), false, false, false);
			break;
		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		top_bar = (RelativeLayout) findViewById(R.id.zhongchou_top_bar);
		title_tv = (TextView) top_bar.findViewById(R.id.tea_zhongchou_title);
		rule_bt = (Button) top_bar.findViewById(R.id.tea_zhongchou_rule_bt);
		more = (ImageView) top_bar.findViewById(R.id.tea_zhongchou_more);
		back=(ImageView) top_bar.findViewById(R.id.tea_zhongchou_back);
		
	}
	private void setListener() {
		// TODO Auto-generated method stub
		top_bar.findViewById(R.id.tea_zhongchou_back).setOnClickListener(this);
		rule_bt.setOnClickListener(this);
		more.setOnClickListener(this);
	}
	/**
	 * 设置顶部栏类容
	 * @param page_type 页面类型
	 */
	public void setTopBar(int page_type){
		rule_bt.setVisibility(View.INVISIBLE);
		more.setVisibility(View.INVISIBLE);
		switch(page_type){
		case ZHONGCHOU_MAIN_PAGE:
			title_tv.setText(getString(R.string.tea_zhongchou));
			rule_bt.setVisibility(View.VISIBLE);
			
			LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			rule_bt.setLayoutParams(params);
			
			params=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.CENTER_VERTICAL);
			
			back.setLayoutParams(params);
			more.setVisibility(View.GONE);
			
			break;
		case ZHONGCHOU_MY_CROWDFUNDING_PAGE:
			title_tv.setText(getString(R.string.my_crowdfunding));
//			more.setVisibility(View.VISIBLE);
			break;
		case ZHONGCHOU_MY_EARNINGS_PAGE:
			title_tv.setText(getString(R.string.my_earnings));
			break;
		case ZHONGCHOU_SET_WITHDRAW_DEPOSIT_PAGE:
			title_tv.setText(getString(R.string.withdraw_deposit));
//			more.setVisibility(View.VISIBLE);
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
		fragmentTransaction.replace(R.id.zhongchou_fragment, fragment);
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
		case R.id.tea_zhongchou_back:
			removeFragerment(true);
			break;
		case R.id.tea_zhongchou_rule_bt:
			
			break;
		case R.id.tea_zhongchou_more:
			showMoreDialog(v, false);
			break;
		default:
			break;
		}
	}

}
