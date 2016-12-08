package com.newbrain.chaxin.my.set;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;

public class AccountManagerActivity extends BaseActivity {

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.account_manager_activity);

		ViewUtils.inject(this);
		context = this;
		actionbarInit();

	}
	
	
	@OnClick({ R.id.set_person_data, R.id.set_account_manager,
		 R.id.set_clear_cache,
		R.id.set_about_chaxin

})
private void onclick_set(View view) {

	System.out.println("-------->");
	
	switch (view.getId()) {
	case R.id.set_person_data:
		Bundle bundle = new Bundle();
		bundle.putInt("type",1 );
		openActivity(BankCardManagerActivity.class,bundle);

		break;

	case R.id.set_account_manager:
		
		openActivity(ChangePasswordActivity.class);

		break;

	
	case R.id.set_clear_cache:

//		openActivity(ResetPwActivity.class);
		
		
		
		break;

	case R.id.set_about_chaxin:
		openActivity(ResetPwOneActivity.class);
		
//		openActivity(pClass);
		

		break;

	default:
		break;
	}

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
		actionbar_imgbtn_right.setVisibility(View.VISIBLE);
//		actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText(getString(R.string.set_account_manager));
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
