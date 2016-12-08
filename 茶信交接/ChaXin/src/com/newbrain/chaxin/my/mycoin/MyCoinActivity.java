package com.newbrain.chaxin.my.mycoin;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.my.mycash.IncomeDetailActivity;
import com.newbrain.chaxin.my.mycash.RechargeActivity;
import com.newbrain.utils.SharedPreferencesHelp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyCoinActivity extends BaseActivity{
	
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycoin);
		ViewUtils.inject(this);
		context = this;
		actionbarInit();
		
		
	}
	@Override
	protected void onResume() {
		setData();
		super.onResume();
	}
	
	@ViewInject(R.id.all_actionbar_linear_left)
	private  LinearLayout actionbar_ll_left;
	@ViewInject(R.id.balace_golden_rechange)
	private  RelativeLayout rl_chongzhi;
	@ViewInject(R.id.all_actionbar_textview_back_name)
	private TextView actionbar_tv_back_name_left;
	@ViewInject(R.id.all_actionbar_name)
	private TextView actionbar_tv_name_center;
	@ViewInject(R.id.tv_jb)
	private TextView tv_jb;
	@ViewInject(R.id.tv_yb)
	private TextView tv_yb;
	@ViewInject(R.id.all_actionbar_button_right_left)
	private Button actionbar_btn_right_left;
	@ViewInject(R.id.all_actionbar_button_right)
	private ImageButton actionbar_imgbtn_right1;
	@ViewInject(R.id.balace_golden_)
	private RelativeLayout actionbar_imgbtn_right;
//	balace_golden_rechange
	
	private void actionbarInit() {
		actionbar_ll_left.setVisibility(View.VISIBLE);
		actionbar_tv_back_name_left.setVisibility(View.GONE);
		actionbar_imgbtn_right1.setVisibility(View.GONE);
		actionbar_btn_right_left.setVisibility(View.GONE);
		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText("");
		actionbar_btn_right_left.setText(getString(R.string.rule));
		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
		actionbar_imgbtn_right.setOnClickListener(clickListener_actionbar);
		rl_chongzhi.setOnClickListener(clickListener_actionbar);
	}
	
	
	private OnClickListener clickListener_actionbar = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.all_actionbar_linear_left:
				finish();
				break;
				
			case R.id.balace_golden_:
				Bundle bundle = new Bundle();
				bundle.putString("type", "0");
				openActivity(IncomeDetailActivity.class,bundle);
				break;
			case R.id.balace_golden_rechange:
				openActivity(RechargeActivity.class);
				break;
			default:
				
				break;
			}
			
			
			
			
		}
	};
	
	
	protected void setData() {
		String jb = SharedPreferencesHelp.getString(context, "gold");
		tv_jb.setText(jb);
	};
	

}
