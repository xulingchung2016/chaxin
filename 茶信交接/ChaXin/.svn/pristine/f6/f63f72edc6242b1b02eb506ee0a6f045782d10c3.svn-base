package com.newbrain.chaxin.my.set;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.WheelActivity;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.AddAddressListBean;
import com.newbrain.model.Bean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.AddAddressListBean.AddressList;
import com.newbrain.user.User;
import com.newbrain.xutils.Xutils_HttpUtils;

public class ModifyAddressActivity  extends BaseActivity
{
	private Context context;
	private JsonThread mThread;
	
	
	private String strPersonName = "";
	private String strPhoneNumber = "";
	private String strZoneNumber = "";
	private String strZoneAddress = "";
	private String strDetailAddress = "";
	private String isDefaultAddress = "false";
	private String strID = "";
	HttpUtils httpUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		setContentView(R.layout.modify_address_activity);
		ViewUtils.inject(this);
		btn_save.setOnClickListener(clickListener_actionbar);
		actionbarInit();
		
		Bundle bundle = this.getIntent().getExtras();  
		strPersonName = bundle.getString("linkMan");
		strPhoneNumber = bundle.getString("phoneNo");
		strZoneNumber = bundle.getString("zipCode");
		strZoneAddress = bundle.getString("area");
		strDetailAddress = bundle.getString("address");
		isDefaultAddress = bundle.getString("isDefault");
		strID  = bundle.getString("id");
		person_name.setText(strPersonName);
		phone_number.setText(strPhoneNumber);
		zone_code.setText(strZoneNumber);
		zone_address.setText(strZoneAddress);
		detail_address.setText(strDetailAddress);
		//isDefaultAddress = check_default_address.isChecked();
		if(strDetailAddress.equals("true"))
		{
			check_default_address.setChecked(true);
		}
		else
		{
			check_default_address.setChecked(false);
		}
		
		ll_address.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, WheelActivity.class);
				intent.putExtra("type", 4);
				startActivityForResult(intent, 102);
				
			}
		});

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 101){
			zone_address.setText(data.getStringExtra("cityName"));
		}
	}
	
	private Handler mHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case Constant.FLAG_GET_ADD_RECEIVE_ADDRESS:
			{
				HttpReturnData receiveData = (HttpReturnData) msg.obj;
				if (receiveData.isSuccess()) 
				{
					AddAddressListBean baseJsonDataBean = (AddAddressListBean) receiveData.getObg();
					if (baseJsonDataBean.getCode().equals("1"))
					{
						AddressList result = baseJsonDataBean.getResult();
						String str_address = result.getAddress();
						String str_area = result.getArea();
						String str_ID = result.getId();
						boolean str_DefaultAddress  = result.getIsDefault();
						String str_Name  = result.getLinkMan();
						String str_PhoneNo  = result.getPhoneNo();
						String str_UserID  = result.getUserId();
						String str_ZipCode  = result.getZipCode();
						Log.e("lijinjin", "str_address: " + str_address + " str_area: " + str_area);
						Log.e("lijinjin", "str_ID: " + str_ID + " str_DefaultAddress: " + str_DefaultAddress);
						
						Log.e("lijinjin", "str_address: " + str_address + " str_area: " + str_area);
						Log.e("lijinjin", "str_ID: " + str_ID + " str_DefaultAddress: " + str_DefaultAddress);
						
						Log.e("lijinjin", "str_Name: " + str_Name + " str_PhoneNo: " + str_PhoneNo);
						Log.e("lijinjin", "str_UserID: " + str_UserID + " str_ZipCode: " + str_ZipCode);
						
						finish();
					}
					else
					{
						
					}
				}
				break;
			}
				
			default:
			{
				break;
			}
			
			}
		}
	};
	
	public void addReceiveAddress()
	{		
		List<Bean> list = new ArrayList<Bean>();
		if(mThread != null)
		{
			mThread.cancleReturnData();
		}
		String userID = User.getInstance().getId();
		String usertoken = User.getInstance().getToken();
		
		list.add(new Bean("id", strID));
		list.add(new Bean("linkMan", strPersonName));
		list.add(new Bean("phoneNo", strPhoneNumber));
		list.add(new Bean("userId", userID));
		list.add(new Bean("token", usertoken));
		list.add(new Bean("area", strZoneAddress));
		list.add(new Bean("address", strDetailAddress));
		list.add(new Bean("zipCode", strZoneNumber));
		
		if(check_default_address.isChecked())
		{
			Log.e("lijinjin", "isDefault: true");
			list.add(new Bean("isDefault", "true"));
		}
		else
		{
			Log.e("lijinjin", "isDefault: false");
			list.add(new Bean("isDefault", "false"));
		}
		
		
		mThread = new JsonThread(context, list, mHandler, Constant.FLAG_GET_ADD_RECEIVE_ADDRESS);
		mThread.start();
	}

	@ViewInject(R.id.address_manager_person)
	private EditText person_name;
	
	@ViewInject(R.id.address_manager_phone)
	private EditText phone_number;
	
	@ViewInject(R.id.address_manager_zip_code)
	private EditText zone_code;
	
	@ViewInject(R.id.address_manager_address)
	private TextView zone_address;
	
	@ViewInject(R.id.address_manager_detail_address)
	private EditText detail_address;
	
	@ViewInject(R.id.address_manager_detail_default_address)
	private CheckBox check_default_address;
	
	@ViewInject(R.id.address_manager_detail_btn_save)
	private Button btn_save;

	@ViewInject(R.id.all_actionbar_linear_left)
	private LinearLayout actionbar_ll_left;
	@ViewInject(R.id.ll_address)
	private LinearLayout ll_address;

	@ViewInject(R.id.all_actionbar_textview_back_name)
	private TextView actionbar_tv_back_name_left;

	@ViewInject(R.id.all_actionbar_name)
	private TextView actionbar_tv_name_center;

	@ViewInject(R.id.all_actionbar_button_right_left)
	private Button actionbar_btn_right_left;

	@ViewInject(R.id.all_actionbar_button_right)
	private TextView actionbar_imgbtn_right;

	private void actionbarInit() 
	{
		// TODO Auto-generated method stub
		actionbar_ll_left.setVisibility(View.VISIBLE);
		actionbar_tv_back_name_left.setVisibility(View.GONE);		
		
		actionbar_btn_right_left.setVisibility(View.GONE);
		actionbar_imgbtn_right.setVisibility(View.VISIBLE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText(getString(R.string.set));
		actionbar_btn_right_left.setText(getString(R.string.rule));

		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
		actionbar_imgbtn_right.setOnClickListener(clickListener_actionbar);
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
			case R.id.all_actionbar_button_right:
				String userId = User.getInstance().getId();
				String token = User.getInstance().getToken();
				String requstStr = strID + "&userId=" + userId + "&token=" + token;
				LogUtils.e("--->"+ requstStr);
				httpUtils.send(HttpMethod.GET, Constant.METHOD_USER_DELETE_ADDRESS_URL+requstStr, new RequestCallBack<String>()
				{
					@Override
					public void onFailure(HttpException arg0, String arg1)
					{
						// TODO Auto-generated method stub
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0)
					{
						// TODO Auto-generated method stub
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
								finish();
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
			case R.id.address_manager_detail_btn_save:
				strPersonName = person_name.getText().toString();
				strPhoneNumber = phone_number.getText().toString();
				strZoneNumber = zone_code.getText().toString();
				strZoneAddress = zone_address.getText().toString();
				strDetailAddress = detail_address.getText().toString();
				//isDefaultAddress = check_default_address.isChecked();
				if(check_default_address.isChecked())
				{
					isDefaultAddress = "true";
				}
				else
				{
					isDefaultAddress = "false";
				}
				
				if(strPersonName.isEmpty() || strPhoneNumber.isEmpty() ||
						strZoneNumber.isEmpty() || strZoneAddress.isEmpty() ||
						strDetailAddress.isEmpty())
				{
					
				}
				else
				{
					addReceiveAddress();
				}
					
				break;

			default:
				break;
			}

		}
	};
}
