package com.newbrain.chaxin.my.set;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;

public class ChangeNiceNameActivity extends BaseActivity {

	private Context context;
	private String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.change_nice_name_activity);

		ViewUtils.inject(this);
		context = this;
		name = getIntent().getStringExtra("nickName");
		mEtName.setText(name);
		actionbarInit();

	}

	@ViewInject( R.id.change_nice_name_edit_name)
	private EditText mEtName;

	@OnClick({ R.id.change_nice_name_img_clean_name,
		R.id.change_nice_name_btn_save_name  })
	private void onClickRa(View view) {

		switch (view.getId()) {
		case R.id.change_nice_name_img_clean_name:

			mEtName.setText("");

			break;
		case R.id.change_nice_name_btn_save_name:
			String name =  mEtName.getText().toString().trim();
			if(TextUtils.isEmpty(name))return;
			Intent intent = new Intent();
			intent.putExtra("nickName",name);
			setResult(102, intent);
			finish();
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
//		actionbar_imgbtn_right.setVisibility(View.VISIBLE);
		 actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText(getString(R.string.set));
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
