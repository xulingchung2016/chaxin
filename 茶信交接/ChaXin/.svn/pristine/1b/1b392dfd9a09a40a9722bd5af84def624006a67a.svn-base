package com.newbrain.chaxin.my.mycollect;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.baseactivity.BaseActivity_FA;
import com.newbrain.chaxin.R;

public class MyCollectActivity extends BaseActivity_FA {

	private Context context;

	

	@ViewInject(R.id.car_friend_radiogroup)
	private RadioGroup mRadioGroup;

	private Fragment mFragment_array[];

	private GoodsCollectFragment mGoodsCollectFragment;

	private BeautyCollectFragment mBeautyCollectFragment;

//	private ShopCollectFragment mShopCollectFragment;

	private int mCurrentFramentIndex;

	private int mIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.car_friends_activity);

		context = this;
		ViewUtils.inject(this);
		
		actionbarInit();

		fragmentInit();

		mRadioGroup.setOnCheckedChangeListener(mCheckedChangeListener);
	}

	private void fragmentInit() {
		// TODO Auto-generated method stub

		mBeautyCollectFragment = new BeautyCollectFragment();

		mGoodsCollectFragment = new GoodsCollectFragment();
		
//		mShopCollectFragment = new ShopCollectFragment();

		mFragment_array = new Fragment[] { mGoodsCollectFragment,
				mBeautyCollectFragment};

		getSupportFragmentManager().beginTransaction()
				.add(R.id.car_friend_contains, mGoodsCollectFragment)
				.add(R.id.car_friend_contains, mBeautyCollectFragment)
				.hide(mBeautyCollectFragment).show(mGoodsCollectFragment)
				.commit();

	}

	
	private OnCheckedChangeListener mCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub

			switch (checkedId) {
			case R.id.collect_goods:
				
				mIndex = 0;
				
				break;
			case R.id.collect_beauty:

				mIndex = 1;
				
				break;
			case R.id.collect_shop:

				mIndex = 2;
				
				break;

			default:
				break;
			}

			
			if (mIndex != mCurrentFramentIndex) {

				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();

				ft.hide(mFragment_array[mCurrentFramentIndex]);

				if (!mFragment_array[mIndex].isAdded()) {

					ft.add(R.id.car_friend_contains, mFragment_array[mIndex]);

				}

				ft.show(mFragment_array[mIndex]);

				ft.commit();

				mCurrentFramentIndex = mIndex;

			}

		}
	};
	
	
	@ViewInject(R.id.all_actionbar_linear_left)
	private LinearLayout actionbar_ll_left;

	@ViewInject(R.id.all_actionbar_textview_back_name)
	private TextView actionbar_tv_back_name_left;

	@ViewInject(R.id.all_actionbar_name)
	private TextView actionbar_tv_name_center;

	@ViewInject(R.id.all_actionbar_button_right_left)
	private Button actionbar_btn_right_left;

	@ViewInject(R.id.all_actionbar_button_right)
	private ImageButton actionbar_imgbtn_right;

	private void actionbarInit() {
		// TODO Auto-generated method stub

		actionbar_ll_left.setVisibility(View.VISIBLE);
		actionbar_tv_back_name_left.setVisibility(View.GONE);
		// actionbar_tv_name_center.setVisibility(View.VISIBLE);
		
		
		actionbar_btn_right_left.setVisibility(View.GONE);
//		actionbar_imgbtn_right.setVisibility(View.VISIBLE);
		actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText(getString(R.string.my_collect));
		actionbar_btn_right_left.setText(getString(R.string.rule));

		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
		actionbar_imgbtn_right.setOnClickListener(clickListener_actionbar);

	}

	private OnClickListener clickListener_actionbar = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			switch (v.getId()) {
			case R.id.all_actionbar_linear_left:

				finish();

				break;

			case R.id.all_actionbar_button_right:

				break;

			default:
				break;
			}

		}
	};

	
	
	

}
