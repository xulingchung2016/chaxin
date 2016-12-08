package com.newbrain.chaxin.my.mycash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
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
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.swipeRefresh.SwipyRefreshLayout;
import com.newbrain.swipeRefresh.SwipyRefreshLayoutDirection;
import com.newbrain.swipeRefresh.SwipyRefreshLayout.OnRefreshListener;
import com.newbrain.user.User;
import com.newbrain.xutils.Xutils_HttpUtils;

/**
 * 
 * 收支明细
 * */

public class IncomeDetailActivity extends BaseActivity {

	private Context context;
	@ViewInject(R.id.swipyrefreshlayout)
	private SwipyRefreshLayout swrefresh;
	@ViewInject(R.id.order_listview)
	private ListView mLvOrder;
	private HttpUtils httpUtils;
	private List<HashMap<String,String>> datas = new ArrayList<HashMap<String,String>>();
	private int pageNo=1,pageSize=20;
	private InComeAdapter adapter;
	private String type ="";//0支出。1收入
	private String flag ="0";//0茶币1现金
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.income_detail_activity);
		ViewUtils.inject(this);
		context = this;
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		actionbarInit();
		flag = getIntent().getStringExtra("type");
		
		
		rbInit();
		getData();

	}

	private void getData() {
		CustomProgressDialog.startProgressDialog(context, "loading...");
		String userid = User.getInstance().getId();
		String token = User.getInstance().getToken();
		RequestParams params = new RequestParams();
		params.addBodyParameter("token", token);
		params.addBodyParameter("userId", userid);
		params.addBodyParameter("payType", type);
		params.addBodyParameter("relatedType", flag);
		params.addBodyParameter("pageNo", pageNo+"");
		params.addBodyParameter("pageSize", pageSize+"");
		String url =  Constant.METHOD_getPayDetail+"?token="+token+"&userId="+userid+"&payType="+type+"&relatedType="+flag+"&pageNo="+pageNo+"&pageSize="+pageSize;
		httpUtils.send(HttpMethod.POST, Constant.METHOD_getPayDetail, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				swrefresh.setRefreshing(false);
				handleNetworkError();
				pageNo--;
				CustomProgressDialog.stopProgressDialog();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				swrefresh.setRefreshing(false);
				CustomProgressDialog.stopProgressDialog();
				String result = arg0.result;
				try {
					JSONObject obj = new JSONObject(result);
					String code = obj.getString("code");
					if(code.equals("1")){
						if(pageNo == 1)datas.clear();
						datas .addAll(JSON.parseObject(obj.getJSONArray("result").toString(), new TypeReference<List<HashMap<String,String>>>(){}));
						adapter.notifyDataSetChanged();
					}else{
						if(pageNo == 1)datas.clear();
						adapter.notifyDataSetChanged();
						swrefresh.setDirection(SwipyRefreshLayoutDirection.TOP);
					} 
					}catch (Exception e) {
						swrefresh.setDirection(SwipyRefreshLayoutDirection.TOP);
					}
			}
		});
	}

	@ViewInject(R.id.income_group)
	private RadioGroup rbIncome;

	private void rbInit() {
		swrefresh.setDirection(SwipyRefreshLayoutDirection.BOTH);

		swrefresh.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh(SwipyRefreshLayoutDirection direction) {
				if(direction == SwipyRefreshLayoutDirection.TOP){
				pageNo = 1;
				}else pageNo ++;
				getData();
			}
		});
		
		adapter = new InComeAdapter();
		mLvOrder.setAdapter(adapter);
		rbIncome.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				pageNo = 1;
				switch (checkedId) {
				case R.id.my_cash_all:
					type = "";
					break;

				case R.id.my_cash_expenditure:
					type = "1";
					break;

				case R.id.my_cash_income:
					type = "0";
					break;
				default:
					break;
				}
				getData();

			}
		});

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
		actionbar_imgbtn_right.setVisibility(View.GONE);
		// actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText(getString(R.string.my_cash_income_detail));
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

	class InComeAdapter extends BaseAdapter{

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
		public View getView(int arg0, View view, ViewGroup arg2) {
			ViewHolder vh = null;
			if(view == null){
				view = LayoutInflater.from(context).inflate(R.layout.item_income, null);
				vh = new ViewHolder();
				vh.tv_acount = (TextView) view.findViewById(R.id.tv_acount);
				vh.tv_date = (TextView) view.findViewById(R.id.tv_date);
				view.setTag(vh);
			}else vh = (ViewHolder) view.getTag();
			HashMap<String,String> map = datas.get(arg0);
			String account = map.get("amount");
			String date = map.get("createDate");
			String type = map.get("payType");
			vh.tv_date.setText(date);
			if(type.equals("0"))vh.tv_acount.setText("-"+account+"元");
			else vh.tv_acount.setText(account+"元");
			
			return view;
		}
		
		class ViewHolder {
			TextView tv_acount,tv_date;
		}
		
	}
}
