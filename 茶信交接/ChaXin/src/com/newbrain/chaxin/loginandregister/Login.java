package com.newbrain.chaxin.loginandregister;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.GetAddressListBean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.GetAddressListBean.GetAddressList;
import com.newbrain.user.User;
import com.newbrain.utils.ButtonColorFilter;
import com.newbrain.utils.SharedPreferencesHelp;
import com.newbrain.xutils.Xutils_HttpUtils;

public class Login extends BaseActivity
{
	@ViewInject(R.id.login_phoneNoEdit)
	private EditText login_phoneNo;
	private String str_loginPhoneNo;
	
	@ViewInject(R.id.login_pwdEdit)
	private EditText login_pwd;
	private String str_loginPwd;
	
	@ViewInject(R.id.all_actionbar_name)
	private TextView actionbar_tv_name_center;
	
	@ViewInject(R.id.all_actionbar_linear_left)
	private LinearLayout actionbar_ll_left;
	
	private Context context;
	HttpUtils httpUtils;
	private JsonThread mThread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		ViewUtils.inject(this);
		context = this;
		actionbar_tv_name_center.setText("登录");
		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		login_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
	}
	
	public void getReceiveAddress()
	{		
		List<Bean> list = new ArrayList<Bean>();
		if(mThread != null)
		{
			mThread.cancleReturnData();
		}

		mThread = new JsonThread(context, list, mHandler, Constant.FLAG_GET_GET_RECEIVE_ADDRESS);
		mThread.start();
	}
	
	private Handler mHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what) 
			{
			case Constant.FLAG_GET_GET_RECEIVE_ADDRESS:
			{
				HttpReturnData receiveData = (HttpReturnData) msg.obj;
				if (receiveData.isSuccess()) 
				{
					GetAddressListBean baseJsonDataBean = (GetAddressListBean) receiveData.getObg();
					if (baseJsonDataBean.getCode().equals("1"))
					{
						List<GetAddressList> result = baseJsonDataBean.getResult();
						int iSize = result.size();
						for(int iLoop = 0; iLoop < iSize; iLoop++)
						{
							String str_address = "";
							String str_area = "";
							String str_ID = "";
							boolean b_isDefault = false;
							String str_Name = "";
							String str_Phone = "";
							String str_UserID = "";
							String str_ZoneCode = "";
							str_address = result.get(iLoop).getAddress();
							str_area = result.get(iLoop).getArea();
							str_ID = result.get(iLoop).getId();
							b_isDefault = result.get(iLoop).getIsDefault();
							str_Name = result.get(iLoop).getLinkMan();
							str_Phone = result.get(iLoop).getPhoneNo();
							str_UserID = result.get(iLoop).getUserId();
							str_ZoneCode = result.get(iLoop).getZipCode();
							
							if(b_isDefault)
							{
								String NameTemp = "收件人： " + str_Name + "    " + str_Phone;
								String address = str_address;
								
								User.getInstance().setreceive_Namephone(NameTemp);
								User.getInstance().setreceive_DetailAddress(address);
								
								break;
							}
							
							Log.e("lijinjin", "str_address: " + str_address);
							Log.e("lijinjin", "str_area: " + str_area);
							Log.e("lijinjin", "str_ID: " + str_ID);
							Log.e("lijinjin", "b_isDefault: " + b_isDefault);
							Log.e("lijinjin", "str_Name: " + str_Name);
							Log.e("lijinjin", "str_Phone: " + str_Phone);
							Log.e("lijinjin", "str_ZoneCode: " + str_ZoneCode);
						}
						
					}
				}
			}
			break;
			default:
				break;
			}
		}
	};
	
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
	
	@OnClick({R.id.fast_register})
	private void onClick_Register(View v)
	{
		openActivity(RegisterActivity.class);
	}
	/**
	 * 忘记密码
	 * @param v
	 */
	@OnClick({R.id.forget_pwd})
	private void onClick_reSetpwd(View v)
	{
		Bundle bundle = new Bundle();
		bundle.putBoolean("flag", true);
		openActivity(RegisterActivity.class,bundle);
	}
	
	
	@OnClick({R.id.login_bnt_ok})
	private void onClick(View v)
	{		
		switch (v.getId())
		{
		case R.id.login_bnt_ok:
		{
			//openActivity(RegisterActivity.class);
			if(User.getInstance().isLogin())
			{
				showShortToast("已经登录了");
				return;
			}
			
			str_loginPhoneNo = login_phoneNo.getText().toString();
			str_loginPwd = login_pwd.getText().toString();
			if(TextUtils.isEmpty(str_loginPhoneNo)){
				showShortToast("账号不能为空!");
				
				return;
			}
			if(TextUtils.isEmpty(str_loginPwd)){
				showShortToast("密码不能为空!");
				
				return;
			}
			CustomProgressDialog.startProgressDialog(context, "登录中...");
			String requstStr = "phoneNo=" + str_loginPhoneNo + "&password=" + str_loginPwd;
			LogUtils.e("--->"+ requstStr);
			httpUtils.send(HttpMethod.GET, Constant.METHOD_USER_LOGIN+requstStr, new RequestCallBack<String>()
			{
				@Override
				public void onFailure(HttpException arg0, String arg1)
				{
					CustomProgressDialog.stopProgressDialog();
					// TODO Auto-generated method stub
					showShortToast("登录失败");
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0)
				{
					CustomProgressDialog.stopProgressDialog();
					// TODO Auto-generated method stub
					//showShortToast(arg0.result);
					
					LogUtils.e("--->"+arg0.result);
					//解析出来并保存
					try 
					{
						String strResult = arg0.result.toString();
						String code = new JSONObject(strResult).getString("code");
						String msg = new JSONObject(strResult).getString("message");
						LogUtils.e("--->" + code);
						if(code.equals("1"))
						{
							showShortToast("登录成功");
							JSONObject jsonObjs = new JSONObject(strResult).getJSONObject("result"); 
							String phoneNo = jsonObjs.getString("phoneNo");
							String id = jsonObjs.getString("id");
							String sex = jsonObjs.getString("sex");			
							String token = jsonObjs.getString("token");					
							String nickName = jsonObjs.getString("nickName");			
							String image = jsonObjs.getString("image");
							
							User.getInstance().setPhoneNo(phoneNo);
							User.getInstance().setId(id);
							User.getInstance().setSex(sex);
							User.getInstance().setToken(token);
							User.getInstance().setNickName(nickName);
							User.getInstance().setImage(image);
							User.getInstance().setLogin(true);
							SharedPreferencesHelp.SavaString(context, "phoneNo", phoneNo);
							SharedPreferencesHelp.SavaString(context, "id", id);
							SharedPreferencesHelp.SavaString(context, "sex", sex);
							SharedPreferencesHelp.SavaString(context, "token", token);
							SharedPreferencesHelp.SavaString(context, "nickName", nickName);
							SharedPreferencesHelp.SavaString(context, "image", image);
							SharedPreferencesHelp.SavaBoolean(context, "isLogin", true);
							Log.e("lijinjin", "phoneNo: " + phoneNo);
							Log.e("lijinjin", "id: " + id);
							Log.e("lijinjin", "sex: " + sex);
							Log.e("lijinjin", "token: " + token);
							Log.e("lijinjin", "nickName: " + nickName);
							Log.e("sex", "image: " + image);
							Log.e("sex", "token: " + token);
							
							getReceiveAddress();
							
							finish();
						}else {
							showShortToast(msg);
						}
					} 
					catch (JSONException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			});
			break;
		}
		default:
			break;
		}
	}

}
