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
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;

public class ChangePasswordActivity extends BaseActivity {

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.change_password_activity);

		ViewUtils.inject(this);
		context = this;
		actionbarInit();

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
	@ViewInject(R.id.ll_change_loginpass)
	private LinearLayout ll_login;
	@ViewInject(R.id.ll_change_moneypsss)
	private LinearLayout ll_money;

	private void actionbarInit() {
		// TODO Auto-generated method stub

		actionbar_ll_left.setVisibility(View.VISIBLE);
		actionbar_tv_back_name_left.setVisibility(View.GONE);
		// actionbar_tv_name_center.setVisibility(View.VISIBLE);
		
		
		actionbar_btn_right_left.setVisibility(View.GONE);
//		actionbar_imgbtn_right.setVisibility(View.VISIBLE);
		actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText("修改密码");
//		actionbar_btn_right_left.setText(getString(R.string.rule));

		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
		actionbar_imgbtn_right.setOnClickListener(clickListener_actionbar);
		ll_login.setOnClickListener(clickListener_actionbar);
		ll_money.setOnClickListener(clickListener_actionbar);

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
			case R.id.ll_change_loginpass://修改登录密码
				Bundle bundle = new Bundle();
				bundle.putInt("type", 1);
				openActivity(ResetPwActivity.class,bundle);
				break;
			case R.id.ll_change_moneypsss://修改体现密码
				Bundle bundle2 = new Bundle();
				bundle2.putInt("type", 2);
				openActivity(ResetPwActivity.class,bundle2);
				break;

			default:
				break;
			}

		}
	};

}
