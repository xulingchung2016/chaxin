package com.newbrain.chaxin.my.mycash;

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
import com.newbrain.chaxin.my.set.BankCardManagerActivity;
import com.newbrain.utils.SharedPreferencesHelp;

public class MyCashActivity extends BaseActivity {

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.my_cash_activity);

		ViewUtils.inject(this);
		context = this;
		actionbarInit();
		
	}
	@Override
	protected void onResume() {
		setData();
		super.onResume();
	}

	@OnClick({ R.id.my_cash_num, R.id.my_cash_recharge, R.id.my_cash_withdraw })
	private void onClick_cash(View v) {

		switch (v.getId()) {
		case R.id.my_cash_num:
			Bundle bundles = new Bundle();
			bundles.putString("type", "1");
			openActivity(IncomeDetailActivity.class,bundles);
			break;
		case R.id.my_cash_recharge:
			Bundle bundle = new Bundle();
			bundle.putInt("type", 3);
			openActivity(RechargeActivity.class,bundle);
			break;

		case R.id.my_cash_withdraw:
			openActivity(BankCardManagerActivity.class);
			
			break;

		default:
			break;
		}

	}

	@ViewInject(R.id.all_actionbar_linear_left)
	private LinearLayout actionbar_ll_left;

	@ViewInject(R.id.all_actionbar_textview_back_name)
	private TextView actionbar_tv_back_name_left;
	@ViewInject(R.id.my_cash_num_balance)
	private TextView tv_cash;

	@ViewInject(R.id.all_actionbar_name)
	private TextView actionbar_tv_name_center;

	@ViewInject(R.id.all_actionbar_button_right_left)
	private Button actionbar_btn_right_left;

	@ViewInject(R.id.all_actionbar_button_right)
	private ImageButton actionbar_imgbtn_right;

	private void actionbarInit() {

		actionbar_ll_left.setVisibility(View.VISIBLE);
		actionbar_tv_back_name_left.setVisibility(View.GONE);
		// actionbar_tv_name_center.setVisibility(View.VISIBLE);

		actionbar_btn_right_left.setVisibility(View.GONE);
		actionbar_imgbtn_right.setVisibility(View.VISIBLE);
		// actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText(getString(R.string.my_cash_));
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
	
	protected void setData() {
		String cash = SharedPreferencesHelp.getString(context, "cash");
		tv_cash.setText(cash);
	};

}
