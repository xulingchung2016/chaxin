package com.newbrain.chaxin.my.mybeauty;

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
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.baseactivity.BaseActivity_FA;
import com.newbrain.chaxin.R;

public class BeautyActivity  extends BaseActivity_FA {

	private Context context;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.beauty_activity);

		ViewUtils.inject(this);
		context = this;
		actionbarInit();
		
		fragmentInit();
		 rbInit();
	}

	
	@ViewInject(R.id.my_beauty_group)
	private RadioGroup rbIncome;
	
	private int mCurrentFragment;
	
	private MyBeautyProfileFragment mProfileFragment;
	private MyBeautyServiceFragment mServiceFragment;
	private MyBeautyYuyueFragment mYuyueFragment;//我的预约
	
	
	private Fragment mFragmentArray[];
	

	private void rbInit() {

		rbIncome.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				
				int temp = 0;
				
				switch (checkedId) {
				case R.id.my_beauty_service:
					
					temp = 0;
					
					break;

				case R.id.my_beauty_profile:
					
					temp = 1;

					break;
				case R.id.my_beauty_my:
					temp = 2;
					
					break;

				
				default:
					break;
				}
				
				
				if(temp != mCurrentFragment)
				{
					
					
					
					FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
					
					transaction.hide(mFragmentArray[mCurrentFragment]);
					
					if(!mFragmentArray[temp].isAdded())
					{
						
						
						transaction.add(R.id.container,mFragmentArray[temp]);
						
					}
					
					
					transaction.show(mFragmentArray[temp]);
					
					
					transaction.commit();
					
					mCurrentFragment = temp;
					
				}
				

			}
		});

	}
	
	
	private void fragmentInit()
	{
		mProfileFragment = new MyBeautyProfileFragment();
		
		mServiceFragment = new MyBeautyServiceFragment();
		mYuyueFragment = new MyBeautyYuyueFragment();
		
		mFragmentArray = new Fragment[]{mServiceFragment,mProfileFragment,mYuyueFragment};
		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		
		transaction.add(R.id.container,mProfileFragment).add(R.id.container,mServiceFragment).add(R.id.container,mYuyueFragment).hide(mProfileFragment).hide(mYuyueFragment);
		
		transaction.commit();
	}
	
	

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
		actionbar_tv_name_center.setText(getString(R.string.my_beauty_));
		actionbar_btn_right_left.setText(getString(R.string.rule));

		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
		actionbar_imgbtn_right.setOnClickListener(clickListener_actionbar);

	}

	private OnClickListener clickListener_actionbar = new OnClickListener() {

		@Override
		public void onClick(View v) {
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


	public CharSequence getCurrentCity() {
		// TODO Auto-generated method stub
		return null;
	}

}
