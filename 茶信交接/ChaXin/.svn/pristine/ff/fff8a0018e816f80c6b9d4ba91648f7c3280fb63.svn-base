package com.newbrain.chaxin.my.set;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.jsonthread.Constant;
import com.newbrain.user.User;
import com.newbrain.xutils.Xutils_HttpUtils;

public class ResetPwActivity extends BaseActivity {

	private Context context;
	private int type;//1为修改原密码2为修改体现密码
	private HttpUtils httpUtils;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.reset_pw_activity);
		ViewUtils.inject(this);
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		
		context = this;
		type = getIntent().getIntExtra("type", 1);
		actionbarInit();

	}


	@ViewInject(R.id.all_actionbar_linear_left)
	private LinearLayout actionbar_ll_left;

	@ViewInject(R.id.all_actionbar_textview_back_name)
	private TextView actionbar_tv_back_name_left;

	@ViewInject(R.id.all_actionbar_name)
	private TextView actionbar_tv_name_center;
	@ViewInject(R.id.et_pass_1)
	private EditText et_pass_1;
	@ViewInject(R.id.et_pass_2)
	private  EditText et_pass_2;
	@ViewInject(R.id.et_pass_3)
	private  EditText et_pass_3;;

	@ViewInject(R.id.btn_ok)
	private Button btn_ok;
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

		actionbar_tv_back_name_left.setText("");
		if(type == 1)
		actionbar_tv_name_center.setText(getString(R.string.reset_login_pw));
		else 
			actionbar_tv_name_center.setText(getString(R.string.reset_trade_pw));
		actionbar_btn_right_left.setText(getString(R.string.rule));

		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
		actionbar_imgbtn_right.setOnClickListener(clickListener_actionbar);
		btn_ok.setOnClickListener(clickListener_actionbar);
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
			case R.id.btn_ok:
				submit();
				break;

			default:
				break;
			}

		}
	};
	
	protected void submit() {
		String pass1 = et_pass_1.getText().toString().trim();
		String pass2 = et_pass_2.getText().toString().trim();
		String pass3 = et_pass_3.getText().toString().trim();
		if(TextUtils.isEmpty(pass1)||TextUtils.isEmpty(pass2)||TextUtils.isEmpty(pass3)){
			showShortToast("密码输入不能为空！");
			return;
		}
		if(!pass2.equals(pass3)){
			showShortToast("两次密码输入不一致！");
			return;
		}
		if(type == 2){//修改体现密码
			if(User.getInstance().isLogin()){
		String token = User.getInstance().getToken();
		String id= User.getInstance().getId();
		RequestParams params = new RequestParams();
		params.addBodyParameter("token", token);
		params.addBodyParameter("userId", id);
		params.addBodyParameter("oldPassword", pass1);
		params.addBodyParameter("password", pass2);
		httpUtils.send(HttpMethod.POST, Constant.METHOD_modifyPayPassword, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				handleNetworkError();
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject	jsonObjs = new JSONObject(arg0.result);
					String code = jsonObjs.getString("code");
					String msg = jsonObjs.getString("message");
					if(code.equals("1")){
						finish();
					}
					showShortToast(msg);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		}
		}else{
			showShortToast("尚未登录");
		}
	}

}
