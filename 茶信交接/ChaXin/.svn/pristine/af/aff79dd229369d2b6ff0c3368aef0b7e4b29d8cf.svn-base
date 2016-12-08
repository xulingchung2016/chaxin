package com.newbrain.chaxin.my.set;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.adapter.MyBaseAdapter_AddressList;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.AddAddressListBean;
import com.newbrain.model.Bean;
import com.newbrain.model.GetAddressListBean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.GetAddressListBean.GetAddressList;
import com.newbrain.user.User;

public class ReceiveAddressManagerActivity extends BaseActivity 
{
	private Context context;
	private JsonThread mThread;
	
	private List<GetAddressList> addressList;
	
	@ViewInject(R.id.receive_address_listview)
	private ListView lvAddressList;
	
	private MyBaseAdapter_AddressList myAddressListAdapter;
	
	private String addressfrom = "";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receive_address_manager_activity);
		ViewUtils.inject(this);
		addressList = new ArrayList<GetAddressList>(); 
		context = this;
		myAddressListAdapter = new MyBaseAdapter_AddressList(context, addressList, mHandler);
		lvAddressList.setAdapter(myAddressListAdapter);
		
		actionbarInit();
		addressfrom = getIntent().getStringExtra("from");
		Log.e("lijinjin", "addressfrom: " + addressfrom);
		myAddressListAdapter.set_requestaddressfrom(addressfrom);
	}
	@Override
	protected void onResume() {
		getReceiveAddress();
		super.onResume();
	}
	
	@OnClick({R.id.receive_address_add_})
	private void onClickRa(View view)
	{
		switch (view.getId()) 
		{
		case R.id.receive_address_add_:	
			openActivity(EditAddressActivity.class);	
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

				break;

			default:
				break;
			}
		}
	};
	
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
						addressList.clear();
						List<GetAddressList> result = baseJsonDataBean.getResult();
						int iSize = result.size();
						for(int iLoop = 0; iLoop < iSize; iLoop++)
						{
							addressList.add(result.get(iLoop));
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
							
							Log.e("lijinjin", "str_address: " + str_address);
							Log.e("lijinjin", "str_area: " + str_area);
							Log.e("lijinjin", "str_ID: " + str_ID);
							Log.e("lijinjin", "b_isDefault: " + b_isDefault);
							Log.e("lijinjin", "str_Name: " + str_Name);
							Log.e("lijinjin", "str_Phone: " + str_Phone);
							Log.e("lijinjin", "str_ZoneCode: " + str_ZoneCode);
						}
						
						if(addressList.size() > 0)
						{
							myAddressListAdapter.notifyDataSetChanged();
						}
					}else {
						addressList.clear();
						myAddressListAdapter.notifyDataSetChanged();
					}
				}
			}
				break;
			case 1122:
			{
				Log.e("lijinjin", "1122");
				finish();
				break;
			}
			default:
				break;
			
			}
		}
	};
	
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
}
