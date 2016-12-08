package com.newbrain.chaxin.loginandregister;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.NoCopySpan.Concrete;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.jsonthread.Constant;
import com.newbrain.user.User;
import com.newbrain.xutils.Xutils_BitmapUtils;
import com.newbrain.xutils.Xutils_HttpUtils;

public class RegisterActivity extends BaseActivity{
	
	
	
	@ViewInject(R.id.register_phoneNoEdit)
	private EditText etPhone;
	
	@ViewInject(R.id.register_verityEdit)
	private EditText etVerityNum;
	
	@ViewInject(R.id.register_bnt_next)
	private Button btnNext;
	
	@ViewInject(R.id.register_bnt_get_verity)
	private TextView btnGetVerity;
	
	@ViewInject(R.id.all_actionbar_name)
	private TextView actionbar_tv_name_center;
	
	@ViewInject(R.id.all_actionbar_linear_left)
	private LinearLayout actionbar_ll_left;
	
	private Context context;
	HttpUtils httpUtils;
	private boolean flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.register_phone);
		
		ViewUtils.inject(this);
		flag = getIntent().getBooleanExtra("flag", false);
		if(!flag)
		actionbar_tv_name_center.setText("注册");
		else 
			actionbar_tv_name_center.setText("重置密码");
		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
		context = this;
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);		
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
	
	private String mPhone;
	private String mVerityNum;
	
	@OnClick({R.id.register_bnt_next})
	private void onClick_RegisterNext(View v)
	{
		switch (v.getId())
		{
		case R.id.register_bnt_next:
		{
			mPhone = etPhone.getText().toString();
			mVerityNum = etVerityNum.getText().toString();
			if(TextUtils.isEmpty(mPhone)||TextUtils.isEmpty(mVerityNum))return;
			User.getInstance().setRegister_phoneNo(mPhone);
			User.getInstance().setRegister_verity(mVerityNum);
			Bundle bundle = new Bundle();
			bundle.putBoolean("flag", flag);
			openActivity(SetLoginPWDActivity.class,bundle);
			finish();
		}
			break;
			
		default:
			break;
		}
	}
	int count =60;
	@OnClick({R.id.register_bnt_get_verity})
	private void onClick(View v)
	{
		
		switch (v.getId())
		{
		case R.id.register_bnt_get_verity:
			mPhone = etPhone.getText().toString();		
			if(TextUtils.isEmpty(mPhone)){
				showShortToast("手机号不能为空！");
				return;
			}
			btnGetVerity.setEnabled(false);
			httpUtils.send(HttpMethod.GET, Constant.METHOD_VERITY_NUM+mPhone, new RequestCallBack<String>() 
			{
				@Override
				public void onFailure(HttpException arg0, String arg1)
				{
					//showShortToast("获取数据失败");
					btnGetVerity.setText("请检查网络");
					btnGetVerity.setEnabled(true);
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) 
				{
					// TODO Auto-generated method stub	
					//showShortToast(arg0.result);
					String strResult = arg0.result.toString();
					try
					{
						String code = new JSONObject(strResult).getString("code");
						if(code.equals("0"))
						{
							showShortToast("验证码发送失败！");
							btnGetVerity.setText("重新发送");
							btnGetVerity.setEnabled(true);
						}
						else
						{
							
							 new CountDownTimer(60000, 1000) {
							     public void onTick(long millisUntilFinished) {
							    	 if(count > 0)
							    	 count--;
							    	 btnGetVerity.setText(count+"s");
							     }
							     public void onFinish() {
							    	 count = 60;
							    	 btnGetVerity.setEnabled(true);
							    		btnGetVerity.setText("获取验证码");
							     }
							  }.start();

						
						}
					} 
					catch (JSONException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					LogUtils.e("--->"+arg0.result);
				}
			});

			break;

		default:
			break;
		}		
	}

}
