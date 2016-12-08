package com.newbrain.chaxin.teazhongchou;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.newbrain.chaxin.R;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;

/**
 * 众筹池页面
 * @author ZY
 *
 */
@SuppressLint("ValidFragment")
public final class TeaZhongChouPoolFragment extends Fragment implements
		OnClickListener {

	private TeaZhongChouActivity mParentAcitvity;
	private int pool_type;
	private Button total_money_bt;
	private ImageView pool_iv;
	

	private AnimationDrawable mAnimationDrawable;
	private int money;
	private JsonThread mThread;

	public TeaZhongChouPoolFragment(String content, int money) {
		// TODO Auto-generated constructor stub
		pool_type = getType(content);
		this.money = money;
	}

	private int getType(String content) {
		// TODO Auto-generated method stub

		if (content.equals(getString(R.string.my_crowdfunding))) {
			return Constant.FLAG_FIND_TEAHOUSE_LOACTION;
		} else if (content
				.equals(getString(R.string.crowdfunding_total_nu))) {

		} else if (content.equals(getString(R.string.my_participation))) {
			mParentAcitvity
					.replaceFragment(new TeaZhongChouMyEarningsFragment(),
							true, false, true);
		}
		return 0;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("pool onCreate "+pool_type);
		mParentAcitvity = (TeaZhongChouActivity) getActivity();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("pool onCreateView "+pool_type);
		View view = inflater.inflate(R.layout.tea_zhongchou_pool_fragment,
				container, false);
		initView(view);
		setListener();
		initData();
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		total_money_bt = (Button) view.findViewById(R.id.total_money_bt);
		pool_iv = (ImageView) view.findViewById(R.id.pool);
	}

	private void setListener() {
		// TODO Auto-generated method stub
		total_money_bt.setOnClickListener(this);
	}

	private void initData() {
		// TODO Auto-generated method stub
		total_money_bt.setText(money + "万");
		pool_iv.setBackgroundResource(getAnimResId(money));
		mAnimationDrawable = (AnimationDrawable) pool_iv.getBackground();
		startAnimPool();
	}

	private void startGetDataThread(){
//		mHttpThread = new JsonThread(mParentAcitvity, list, mHandler, flag);
	}
	
	public void startAnimPool() {
		if (mAnimationDrawable == null && pool_iv != null) {
			System.out.println("mAnimationDrawable is null");
			mAnimationDrawable = (AnimationDrawable) pool_iv.getBackground();
		}
		if (mAnimationDrawable != null) {
			if (mAnimationDrawable.isRunning()) {
				mAnimationDrawable.stop();
			}
			mAnimationDrawable.start();
		}else{
			System.out.println("mAnimationDrawable is null "+pool_type);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.total_money_bt:
//			if (pool_type.equals(getString(R.string.my_crowdfunding))) {
//				mParentAcitvity.replaceFragment(
//						new TeaZhongChouMyCrowdfundingFragment(), true, false,
//						true);
//			} else if (pool_type
//					.equals(getString(R.string.crowdfunding_total_nu))) {
//
//			} else if (pool_type.equals(getString(R.string.my_participation))) {
//				mParentAcitvity
//						.replaceFragment(new TeaZhongChouMyEarningsFragment(),
//								true, false, true);
//			}
//			break;
//		}
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
}
