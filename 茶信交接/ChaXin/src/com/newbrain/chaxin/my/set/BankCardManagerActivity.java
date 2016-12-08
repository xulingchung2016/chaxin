package com.newbrain.chaxin.my.set;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.my.mycash.AddCardActivity;
import com.newbrain.chaxin.my.mycash.WithDrawActivity;
import com.newbrain.jsonthread.Constant;
import com.newbrain.user.User;
import com.newbrain.xutils.Xutils_HttpUtils;

public class BankCardManagerActivity  extends BaseActivity {

	private Context context;
	private BanKerAdapter adapter;
	private HttpUtils httpUtils;
	private List<HashMap<String,String>> datas = new ArrayList<HashMap<String,String>>();
	private int type =0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bank_card_manager_activity);
		ViewUtils.inject(this);
		context = this;
		actionbarInit();
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		type = getIntent().getIntExtra("type", 0);
		

	}
	@Override
	protected void onResume() {
		super.onResume();
		getData();
	};

	
	String userID,usertoken;
	private void getData() {
		userID = User.getInstance().getId();
		usertoken = User.getInstance().getToken();
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId",userID);
		params.addBodyParameter("token", usertoken);
		httpUtils.send(HttpMethod.POST, Constant.METHOD_get_banks, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				handleNetworkError();
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject	jsonObjs = new JSONObject(arg0.result);
					String code = jsonObjs.getString("code");
//					String msg = jsonObjs.getString("message");
					if(code.equals("1")){
						List<HashMap<String,String>> datass = JSON.parseObject(jsonObjs.getJSONArray("result").toString(),new TypeReference<List<HashMap<String,String>>>(){} );
						datas.clear();
						datas.addAll(datass);
						adapter.notifyDataSetChanged();
					}
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
	}



	@ViewInject(R.id.all_actionbar_linear_left)
	private LinearLayout actionbar_ll_left;

	@ViewInject(R.id.all_actionbar_textview_back_name)
	private TextView actionbar_tv_back_name_left;

	@ViewInject(R.id.all_actionbar_name)
	private TextView actionbar_tv_name_center;
	@ViewInject(R.id.bank_card_tv_add_card)
	private TextView bank_card_tv_add_card;

	@ViewInject(R.id.all_actionbar_button_right_left)
	private Button actionbar_btn_right_left;

	@ViewInject(R.id.all_actionbar_button_right)
	private ImageButton actionbar_imgbtn_right;
	@ViewInject(R.id.bank_card_listview)
	private SwipeMenuListView card_listview;
	private int pos;

	private void actionbarInit() {

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
		bank_card_tv_add_card.setOnClickListener(clickListener_actionbar);
		adapter = new BanKerAdapter();
		SwipeMenuCreator creator = new SwipeMenuCreator() 
		{
			@Override
			public void create(SwipeMenu menu)
			{
				// TODO Auto-generated method stub
				
				SwipeMenuItem item = new SwipeMenuItem(context);
				
				item.setBackground(new ColorDrawable(Color.RED));
				item.setTitle(getString(R.string.delete));
				item.setWidth(dp2px(80));
				item.setTitleColor(Color.WHITE);
				item.setTitleSize(dp2px(8));
				
				menu.addMenuItem(item);
			}
		};

		card_listview.setMenuCreator(creator);
		card_listview.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			@Override
			public void onMenuItemClick(int arg0, SwipeMenu arg1, int arg2) 
			{
				String ID = datas.get(arg0).get("id");
				removeShopCart(ID);
				pos = arg0;
			}
		});

		card_listview.setAdapter(adapter);
		card_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", datas.get(arg2));
				if(type == 0){
				openActivity(WithDrawActivity.class,bundle);
				}else {
				openActivity(AddCardActivity.class,bundle);
				}
				
			}
		});
		

	}
	
	//删除银行卡
	protected void removeShopCart(String iD) {
		userID = User.getInstance().getId();
		usertoken = User.getInstance().getToken();
		/*RequestParams params = new RequestParams();
		params.addBodyParameter("id", iD);
		params.addBodyParameter("userId",userID);
		params.addBodyParameter("token", usertoken);*/
		httpUtils.send(HttpMethod.GET, Constant.METHOD_bank_delete+"?id="+iD+"&userId="+userID+"&token="+usertoken, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				handleNetworkError();
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject	jsonObjs = new JSONObject(arg0.result);
					String code = jsonObjs.getString("code");
//					String msg = jsonObjs.getString("message");
					if(code.equals("1")){
						datas.remove(pos);
						adapter.notifyDataSetChanged();
					}
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	private int dp2px(int dp)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
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
			case R.id.bank_card_tv_add_card:
				openActivity(AddCardActivity.class);
				break;

			default:
				break;
			}

		}
	};
	
	class BanKerAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public Object getItem(int arg0) {
			return datas.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View convertView, ViewGroup arg2) {
			ViewHolder holder =null;
					if(convertView == null){
						convertView = LayoutInflater.from(context).inflate(R.layout.sort_item_num, null);
						holder = new ViewHolder();
						holder.tv_bank = (TextView) convertView.findViewById(R.id.lst_item_title);
						convertView.setTag(holder);
					}else holder = (ViewHolder) convertView.getTag();
					HashMap<String,String> data = datas.get(arg0);
					String bankName = data.get("bank");
					String account = data.get("cardNumber");
					holder.tv_bank.setText(bankName+"("+account.substring(account.length() -3)+")");
					convertView.setBackgroundColor(Color.parseColor("#ffffff"));
			return convertView;
		}
		class ViewHolder{
			TextView tv_bank;
		}
	}

}
