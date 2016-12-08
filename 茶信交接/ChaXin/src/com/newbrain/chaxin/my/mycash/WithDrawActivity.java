package com.newbrain.chaxin.my.mycash;

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
import android.widget.Toast;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

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
import com.newbrain.chaxin.my.set.BankCardManagerActivity;
import com.newbrain.customview.CustomDialog;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.user.User;
import com.newbrain.xutils.Xutils_HttpUtils;

public class WithDrawActivity extends BaseActivity {

	private Context context;
	private HashMap<String,String> data;
	private HttpUtils httpUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.withdraw_activity);

		ViewUtils.inject(this);
		context = this;
		actionbarInit();
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		data = (HashMap<String, String>) getIntent().getSerializableExtra("data");
		if(data !=null){
		String bankName = data.get("bank");
		String account = data.get("cardNumber");
		tv_bank.setText(bankName+"("+account.substring(account.length() -3)+")");
		}
		
	}
	
	
	
	
	@ViewInject(R.id.withdraw_et_text)
	private EditText etText;
	@ViewInject(R.id.tv_bank)
	private TextView tv_bank;
	
	
	@OnClick({R.id.withdraw_comfirm_turn_out  ,R.id.my_cash_num})
	private void onClick_withdraw(View view)
	{
		
		switch (view.getId()) {
		case R.id.withdraw_comfirm_turn_out:
			showDialog();
			break;
			
		case R.id.my_cash_num:
//			openActivity(BankCardManagerActivity.class);
			break;

		default:
			break;
		}
		
		
	}
	
	private String str ="0";
	private void showDialog() {
		// TODO Auto-generated method stub
		
		str = etText.getText().toString();
		
		
		if(str == null || str.equals(""))
		{
			
			showShortToast("金额不能为空");
			
			return;
		}
		
		
		CustomDialog customDialog = new CustomDialog(context,true);
		
		customDialog.setDialogTitle(R.string.withdraw_please_input_password);
		customDialog.setDialogContent("提现金额为："+str+"元");
		
		customDialog.setLeftBtnListener(new MyLeftBtnClickListener(customDialog));
		
		customDialog.setRightBtnListener(new MyRightBtnClickListener(customDialog));
		
		customDialog.show();
	}

	private class MyLeftBtnClickListener implements OnClickListener
    {
    	private CustomDialog dialog;
    	
		public MyLeftBtnClickListener(CustomDialog dialog) {
			super();
			this.dialog = dialog;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			Toast.makeText(context, "点中了左边的按钮!", Toast.LENGTH_SHORT).show();
			dialog.dismiss();
		}
    }
    
    private class MyRightBtnClickListener implements OnClickListener
    {
    	private CustomDialog dialog;
    	
    	public MyRightBtnClickListener(CustomDialog dialog) {
			super();
			this.dialog = dialog;
		}

		@Override
    	public void onClick(View v) {
			String pass = dialog.getEtContent2().getText().toString().trim();
			if(TextUtils.isEmpty(pass)){
				showShortToast("提现密码不能为空!");
				return;
			}
			tiXian(pass);
    		dialog.dismiss();
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
		actionbar_tv_name_center.setText(getString(R.string.my_cash_withdraw));
		actionbar_btn_right_left.setText(getString(R.string.rule));

		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
		actionbar_imgbtn_right.setOnClickListener(clickListener_actionbar);

	}
	
	/**
	 * 提现
	 */
	public void tiXian(String password) {
		String bankid = data.get("id");
		if(TextUtils.isEmpty(bankid)){
			showShortToast("提现银行卡不存在!");
			return;
		}
		CustomProgressDialog.startProgressDialog(context, "正在提现中...");
		String token = User.getInstance().getToken();
		String uid = User.getInstance().getId();
		RequestParams params = new RequestParams();
		params.addBodyParameter("users.id", uid);
		params.addBodyParameter("bankCard.id", data.get("id"));
		params.addBodyParameter("token", token);
		params.addBodyParameter("amount", str);
		params.addBodyParameter("payPassword", password);
//		params.addBodyParameter("describe", "充茶币");
		httpUtils.send(HttpMethod.POST, Constant.METHOD_userDeposit, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				CustomProgressDialog.stopProgressDialog();
				handleNetworkError();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				CustomProgressDialog.stopProgressDialog();
				try {
					//{"ordersNo":"112023200810833","message":"提交成功。","code":"1"}
					JSONObject	jsonObjs = new JSONObject(arg0.result);
					String code = jsonObjs.getString("code");
					String msg = jsonObjs.getString("message");
					showShortToast(msg);
					if(code.equals("1")){
						finish();
					}
//					String orderno = jsonObjs.getString("ordersNo");
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
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
