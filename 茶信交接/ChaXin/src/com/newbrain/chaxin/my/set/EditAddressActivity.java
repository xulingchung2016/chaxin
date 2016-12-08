package com.newbrain.chaxin.my.set;

import java.util.ArrayList;
import java.util.List;

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

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.WheelActivity;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.AddAddressListBean;
import com.newbrain.model.AddAddressListBean.AddressList;
import com.newbrain.model.BaseJsonDataBean;
import com.newbrain.model.Bean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.user.User;

public class EditAddressActivity extends BaseActivity
{
	private Context context;
	private JsonThread mThread;
	
	
	private String strPersonName = "";
	private String strPhoneNumber = "";
	private String strZoneNumber = "";
	private String strZoneAddress = "";
	private String strDetailAddress = "";
	private String isDefaultAddress = "false";

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;

		setContentView(R.layout.edit_address_activity);

		ViewUtils.inject(this);
		btn_save.setOnClickListener(clickListener_actionbar);
		actionbarInit();
		ll_address.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, WheelActivity.class);
				intent.putExtra("type", 4);
				startActivityForResult(intent, 102);
				
			}
		});
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
					String msgs = baseJsonDataBean.getMessage();
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
						showShortToast(msgs);
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
		
		list.add(new Bean("id", ""));
		list.add(new Bean("linkMan", strPersonName));
		list.add(new Bean("phoneNo", strPhoneNumber));
		list.add(new Bean("userId", userID));
		list.add(new Bean("token", usertoken));
		list.add(new Bean("area", strZoneAddress));
		list.add(new Bean("address", strDetailAddress));
		list.add(new Bean("zipCode", strZoneNumber));
		if(check_default_address.isChecked())
		{
			list.add(new Bean("isDefault", "true"));
		}
		else
		{
			list.add(new Bean("isDefault", "false"));
		}
		
		mThread = new JsonThread(context, list, mHandler, Constant.FLAG_GET_ADD_RECEIVE_ADDRESS);
		mThread.start();
		
	

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 101){
			zone_address.setText(data.getStringExtra("cityName"));
		}
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
	private ImageButton actionbar_imgbtn_right;

	private void actionbarInit() 
	{
		// TODO Auto-generated method stub
		actionbar_ll_left.setVisibility(View.VISIBLE);
		actionbar_tv_back_name_left.setVisibility(View.GONE);		
		
		actionbar_btn_right_left.setVisibility(View.GONE);
		actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText(getString(R.string.set));
		actionbar_btn_right_left.setText(getString(R.string.rule));

		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
//		actionbar_imgbtn_right.setOnClickListener(clickListener_actionbar);
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
					showShortToast("请填写完数据！");
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
