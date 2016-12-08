package com.newbrain.chaxin.loginandregister;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.jsonthread.Constant;
import com.newbrain.user.User;
import com.newbrain.xutils.Xutils_HttpUtils;

public class SetLoginPWDActivity  extends BaseActivity
{
	private Context context;
	HttpUtils httpUtils;
	
	private String mPhone;
	private String mVerityNum;
	private String mPwd;
	
	@ViewInject(R.id.setpwd_Edit)
	private EditText password;
	
	@ViewInject(R.id.setpwd_comfortEdit)
	private EditText comfortpwd;
	
	@ViewInject(R.id.setpwd_bnt_ok)
	private Button setpwd_ok;
	
	@ViewInject(R.id.all_actionbar_name)
	private TextView actionbar_tv_name_center;
	
	@ViewInject(R.id.all_actionbar_linear_left)
	private LinearLayout actionbar_ll_left;
	private boolean flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setloginpwd_layout);
		ViewUtils.inject(this);
		actionbar_tv_name_center.setText("设置密码");
		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
		context = this;
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		flag = getIntent().getBooleanExtra("flag", false);
		mPhone = User.getInstance().getRegister_phoneNo();
		mVerityNum = User.getInstance().getRegister_verity();
	}
	
	private OnClickListener clickListener_actionbar = new OnClickListener() 
	{
		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			switch (v.getId()) 
			{
			case R.id.all_actionbar_linear_left:
				finish();

				break;

			default:
				break;
			}

		}
	};
	
	@OnClick({R.id.setpwd_bnt_ok})
	private void onClick_SetPwd(View v)
	{
		String pwd = password.getText().toString();
		String cmfdpwd = comfortpwd.getText().toString();
		if((!pwd.isEmpty()) && (pwd.equals(cmfdpwd)))
		{
			if(!flag){
			mPwd = pwd;
			RequestParams params = new RequestParams();
			params.addBodyParameter("phoneNo", mPhone);
			params.addBodyParameter("password", mPwd);
			params.addBodyParameter("verifyCode", mVerityNum);
	
			httpUtils.send(HttpMethod.POST, Constant.METHOD_REGISTER,params, new RequestCallBack<String>() 
			{
				@Override
				public void onFailure(HttpException arg0, String arg1) 
				{
					// TODO Auto-generated method stub	
					//showShortToast("获取数据失败");
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0)
				{
					// TODO Auto-generated method stub
					
					String strResult = arg0.result.toString();
					try
					{
						String code = new JSONObject(strResult).getString("code");
						String msg =  new JSONObject(strResult).getString("message");
						if(code.equals("2")) //注册成功
						{
							showShortToast("注册成功,马上登录！");
							finish();
						}
						else if(code.equals("1")) //户已注册
						{
							showShortToast(msg);
						}
						else //注册失败
						{
							showShortToast(msg);
						}
					} 
					catch (JSONException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//showShortToast(arg0.result);
					
					LogUtils.e("--->"+arg0.result);
				}
			});
			}else{//重置密码
				mPwd = pwd;
				RequestParams params = new RequestParams();
				params.addBodyParameter("phoneNo", mPhone);
				params.addBodyParameter("password", mPwd);
				params.addBodyParameter("verifyCode", mVerityNum);
				httpUtils.send(HttpMethod.POST, Constant.METHOD_FORGET_PED, params, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						showShortToast("密码重置失败！");
						
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						showShortToast("密码重置成功！");
						finish();
						
					}
				});
		
			}
		}
		else
		{
			password.setText("");
			password.invalidate();
			
			comfortpwd.setText("");
			password.invalidate();
			showLongToast("两次密码不相同");
		}
	}
}
