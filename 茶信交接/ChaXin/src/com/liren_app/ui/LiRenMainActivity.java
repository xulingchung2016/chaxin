package com.liren_app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.liren_app.ui.dia.LoadingDialog;
import com.liren_app.data.CurrentLocation;
import com.liren_app.data.LiRenDataAdapte;
import com.liren_app.http.GetJsonByUrl;
import com.liren_app.http.IJsonListener;
import com.liren_app.http.MessageKey;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.Tab1Fragment;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.customview.LiRenPopupWindow;
import com.newbrain.jsonthread.Constant;
import com.newbrain.swipeRefresh.SwipyRefreshLayout;
import com.newbrain.swipeRefresh.SwipyRefreshLayoutDirection;
import com.newbrain.swipeRefresh.SwipyRefreshLayout.OnRefreshListener;
import com.newbrain.utils.CityUtil;
import com.newbrain.xutils.Xutils_HttpUtils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

 public class LiRenMainActivity extends BaseActivity implements
		IJsonListener,OnScrollListener, OnClickListener {

	private LoadingDialog mLoadingDialog;
	private GridView grd_liren_list,grd_liren_list2;
	private LiRenDataAdapte grd_liren_list_adapter,grd_liren_list_adapter2;
	private ImageButton btn_seleter;
	private LinearLayout lay_sort;
	private RadioGroup ll_top;
	private RelativeLayout lay_title;
	private RadioButton btn_quancheng;
	private RadioButton btn_zonghe;
	private RadioButton btn_dengji;
	private RadioButton btn_xingbie;
	private ImageButton btn_back;
	private ImageButton btn_search;
	private EditText et_search;
	private ImageButton btn_cancle;
	// -------------------------------
	private boolean isSort = false;
	private String city = "深圳";
	// -------------------------------
	private String paixu_key = "";
	private String[] zonghe_paixu = new String[] { "综合排序", "好评优先", "距离优先",
			"成交量优先" };
	private String[] zonghe_paixu_value = new String[] { "0", "1", "2", "3" };
	private String[] dengji_paixu = new String[] { "翡翠", "钻石", "黄金", "白银" };
	private String[] dengji_paixu_value = new String[] { "3", "2", "1", "0" };
	private String[] xingbie_paixu = new String[] { "男", "女","未知" };
	private String[] xingbie_paixu_value = new String[] { "0", "1","2" };
	private String[] citys ;
	private LiRenPopupWindow popwindow;
	private RelativeLayout lay_grv;
	private String value = "";
	private HttpUtils httpUtils;
	private SwipyRefreshLayout swipyRefresh1,swipyRefresh2;
	//SearchName
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		
			switch (msg.what) {
			case MessageKey.get_liren_info_simp:
				swipyRefresh1.setRefreshing(false);
				try {
					JSONArray array = (JSONArray) msg.obj;
					if(pageNo ==1)grd_liren_list_adapter.clearData();
					grd_liren_list_adapter.ReSetData(initJsonToLIst(array));
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				break;
			case MessageKey.get_liren_paixu:
				swipyRefresh2.setRefreshing(false);
				try {
					JSONArray array1 = (JSONArray) msg.obj;
					if(pageNo ==1)grd_liren_list_adapter2.clearData();
					grd_liren_list_adapter2.ReSetData(initJsonToLIst(array1));
				} catch (JSONException e) {
				}
				break;

			case MessageKey.sort_key_changed_finish:
				int pos= (Integer) msg.obj;
				if (paixu_key.equals("zonghe_paixu")) {
					value = "&sort=" + zonghe_paixu_value[pos];
					if ((Integer) msg.obj == 2) {
						value += "&lng="
								+ CurrentLocation
										.getlocation(LiRenMainActivity.this)[1]
								+ "&lat="
								+ CurrentLocation
										.getlocation(LiRenMainActivity.this)[0];
					}
					btn_zonghe.setText(zonghe_paixu[pos]);
					/*btn_zonghe.setTextColor(LiRenMainActivity.this
							.getResources().getColor(R.color.txt_black));*/
				} else if (paixu_key.equals("dengji_paixu")) {

					value = "&levels=" + dengji_paixu_value[pos];
					btn_dengji.setText(dengji_paixu[pos]);

				} else if (paixu_key.equals("xingbie_paixu")) {

					value = "&sex=" + xingbie_paixu_value[pos];
					btn_xingbie.setText(xingbie_paixu[pos]);
				} else {
					if (citys != null) {
						
						value = "&city="+city+"&area="+citys[pos]+"&sort=0";
						if(citys[pos].equals("全城"))value = "&city="+city+"&area="+"&sort=0";;
						btn_quancheng.setText(citys[pos]);
					}
				}

				/*GetJsonByUrl.getMessage(MessageKey.get_liren_paixu, value,
						LiRenMainActivity.this,pageNo);*/
				getPaixu();
				if (mLoadingDialog == null)
					mLoadingDialog = LoadingDialog
							.getInstance(LiRenMainActivity.this);
				else {
					if (mLoadingDialog.isShowing())
						mLoadingDialog.cancel();
					mLoadingDialog.show();
				}
				break;
			default:
				break;
			}
		}

	};

	private List<HashMap<String, String>> initJsonToLIst(JSONArray array1)
			throws JSONException {
//		int size1 = array1.length();
		List<HashMap<String, String>> datas1 = new ArrayList<HashMap<String, String>>();
		datas1 = JSON.parseObject(array1.toString(), new TypeReference<List<HashMap<String,String>>>(){});
	/*	for (int i = 0; i < size1; i++) {
			JSONObject obj = array1.getJSONObject(i);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", obj.getString("name"));
			map.put("sales", obj.getString("sales"));
			map.put("price", obj.getString("price"));
			map.put("levels", obj.getString("levels"));
			map.put("image", obj.getString("image"));
			map.put("id", obj.getString("id"));
			map.put("userId", obj.getString("userId"));
			datas1.add(map);
		}*/
		return datas1;
	}
	
private void showAreaPopupWindow() {
		
		popwindow = new LiRenPopupWindow(this,
				this);

		popwindow.showAsDropDown(ll_top);
		popwindow.setBackgroundResource(R.drawable.arrow_down_white);
//		Drawable draw1 = getResources().getDrawable(R.drawable.arrow_up_white);
/*		draw1.setBounds(0, 0, draw1.getMinimumWidth(), draw1.getMinimumHeight());
		actionbar_left.setCompoundDrawables(null, null, draw1, null);
*/
		popwindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				
			}
		});
		
		popwindow.getDistrictGridView().setAdapter(adapter);
		popwindow.getDistrictGridView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				pageNo = 1;
				popwindow.dismiss();
				Message msg = new Message();
				msg.what = MessageKey.sort_key_changed_finish;
				msg.obj = Integer.parseInt(arg2 + "");
				mHandler.sendMessage(msg);
			}
		});
	
	}


	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yy_activity_main);
		httpUtils = Xutils_HttpUtils.getHttpUtils(this);
		initUI();
		initData();
		initControlListener();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void initUI() {
		try {
			grd_liren_list = (GridView) this.findViewById(R.id.grd_liren_list);
			grd_liren_list2 = (GridView) this.findViewById(R.id.grd_liren_list2);
			grd_liren_list.setOnScrollListener(this);
			grd_liren_list2.setOnScrollListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		grd_liren_list_adapter = new LiRenDataAdapte(this,
				new ArrayList<HashMap<String, String>>());
		grd_liren_list_adapter2 = new LiRenDataAdapte(this,
				new ArrayList<HashMap<String, String>>());
		grd_liren_list.setAdapter(grd_liren_list_adapter);
		grd_liren_list2.setAdapter(grd_liren_list_adapter2);
		// -----------------------------------------
		btn_seleter = (ImageButton) findViewById(R.id.btn_seleter);
		lay_sort = (LinearLayout) findViewById(R.id.lay_sort);
		ll_top = (RadioGroup) findViewById(R.id.ll_top);
		lay_grv = (RelativeLayout) findViewById(R.id.lay_grv);
		lay_sort.setVisibility(View.INVISIBLE);
		lay_title = (RelativeLayout) findViewById(R.id.lay_title);

		// -----------------------------------------
		btn_quancheng = (RadioButton) findViewById(R.id.btn_quancheng);
		btn_zonghe = (RadioButton) findViewById(R.id.btn_zonghe);
		btn_dengji = (RadioButton) findViewById(R.id.btn_dengji);
		btn_xingbie = (RadioButton) findViewById(R.id.btn_xingbie);
		btn_quancheng.setOnClickListener(this);
		btn_zonghe.setOnClickListener(this);
		btn_dengji.setOnClickListener(this);
		btn_xingbie.setOnClickListener(this);
		// -----------------------------------------
		// -----------------------------------------
//		lay_next_tuijian = (RelativeLayout) findViewById(R.id.lay_next_tuijian);
		// -----------------------------------------
		btn_back = (ImageButton) findViewById(R.id.btn_back);

		btn_search = (ImageButton) findViewById(R.id.btn_search);
		btn_cancle = (ImageButton) findViewById(R.id.btn_more);
		et_search = (EditText) findViewById(R.id.et_search);
		btn_cancle.setOnClickListener(this);
		
		swipyRefresh1 = (SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout1);
		swipyRefresh1.setDirection(SwipyRefreshLayoutDirection.TOP);

		swipyRefresh1.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh(SwipyRefreshLayoutDirection direction) {
				pageNo = 1;
				GetJsonByUrl.getMessage(MessageKey.get_liren_info_simp, LiRenMainActivity.this, pageNo);
				
			}

		
		});
		swipyRefresh2 = (SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout2);
		swipyRefresh2.setDirection(SwipyRefreshLayoutDirection.TOP);

		swipyRefresh2.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh(SwipyRefreshLayoutDirection direction) {
				pageNo = 1;
				/*GetJsonByUrl.getMessage(MessageKey.get_liren_paixu, value,
						LiRenMainActivity.this,pageNo);*/
				getPaixu();
			}

		
		});
	}
	
	private HashMap<String, String[]> mDistrictsOfcityMap = new HashMap<String, String[]>();;
	/**
	 * 根据城市名字，得到该城市下对应的所有行政区的字符串数组
	 * 
	 * @param city
	 * @return
	 */
	private String[] getDistrictsBasedonCityName(String city) {

		if (mDistrictsOfcityMap.containsKey(city)) {
			return mDistrictsOfcityMap.get(city);
		}

		CityUtil cityUtil = new CityUtil(this, city);
		List<String> lists = cityUtil.getItsDistricts();
		String[] districtsOfThisCity = lists.toArray(new String[lists.size()]);
		mDistrictsOfcityMap.put(city, districtsOfThisCity);

		return districtsOfThisCity;
	}
	
	private void getPaixu(){
		
		 String url =Constant.BASE_URL+"TeaMall/teaBeauty/teaBeautyList?pageNo="+pageNo+"&pageSize=30"
					+ value;
//				String url = "http://218.244.138.142:8081/TeaMall/teaBeauty/teaBeautyList?pageNo=1&pageSize=30&city=深圳&area=&sort=0";
				httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						CustomProgressDialog.stopProgressDialog();
						if(isSort)	swipyRefresh2.setRefreshing(false);
						else swipyRefresh1.setRefreshing(false);
						
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String result = arg0.result;
						CustomProgressDialog.stopProgressDialog();
						if(isSort)	swipyRefresh2.setRefreshing(false);
						else swipyRefresh1.setRefreshing(false);
						try {
							JSONObject jobj = new JSONObject(result);
							try {
								JSONArray jsonArr =null;
								String code = jobj.getString("code");
								if(code.equals("0")){
									jsonArr = new JSONArray();
								}else{
								jsonArr = jobj.getJSONArray("result");
								}
								Message msg = new Message();
								msg.what = MessageKey.get_liren_paixu;
								msg.obj = jsonArr;
								mHandler.sendMessage(msg);

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
								mLoadingDialog.cancel();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
	}

	@Override
	protected void initControlListener() {
		// TODO Auto-generated method stub
		btn_seleter.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				pageNo=1;
				if (!isSort) {
					isSort = true;
					btn_seleter.setBackground(LiRenMainActivity.this
							.getResources().getDrawable(
									R.drawable.yy_selecter_sort));
					lay_sort.setVisibility(View.VISIBLE);
					lay_grv.setVisibility(View.GONE);
					lay_title.setBackgroundColor(LiRenMainActivity.this
							.getResources().getColor(R.color.main_basic_color));
					value = "&city="+city+"&area="+"&sort=0";;
					/*GetJsonByUrl.getMessage(MessageKey.get_liren_paixu, value,
							LiRenMainActivity.this,pageNo);*/
					getPaixu();
					

				} else {
					isSort = false;
					btn_seleter
							.setBackground(LiRenMainActivity.this
									.getResources().getDrawable(
											R.drawable.yy_selecter));
					lay_sort.setVisibility(View.GONE);
					lay_grv.setVisibility(View.VISIBLE);
					GetJsonByUrl.getMessage(MessageKey.get_liren_info_simp, LiRenMainActivity.this, pageNo);

				}
			}
		});


		// -----------------------------------------
		grd_liren_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent inte = new Intent();
				try {
					/*inte.putExtra("id", grd_liren_list_adapter.datas.get(arg2)
							.get("id"));
					inte.putExtra("userId",
							grd_liren_list_adapter.datas.get(arg2)
									.get("userId"));*/
					inte.putExtra("data", grd_liren_list_adapter.datas.get(arg2));

				} catch (Exception e) {
					/*inte.putExtra("id", "");
					inte.putExtra("userId", "");*/
				}
				inte.setClass(LiRenMainActivity.this, LiRenActivity.class);
				LiRenMainActivity.this.startActivity(inte);
			}
		});
		grd_liren_list2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent inte = new Intent();
				try {
					inte.putExtra("id", grd_liren_list_adapter.datas.get(arg2)
							.get("id"));
					inte.putExtra("userId",
							grd_liren_list_adapter.datas.get(arg2)
									.get("userId"));

				} catch (Exception e) {
					inte.putExtra("id", "");
					inte.putExtra("userId", "");
				}
				inte.setClass(LiRenMainActivity.this, LiRenActivity.class);
				LiRenMainActivity.this.startActivity(inte);
			}
		});


		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LiRenMainActivity.this.finish();
			}
		});

		btn_cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				btn_seleter.setVisibility(View.VISIBLE);
				btn_cancle.setVisibility(View.GONE);
				et_search.setVisibility(View.GONE);
				et_search.setText("");
				pageNo = 1;
				if(isSort){
					value = "&city="+city+"&area=&sort=0";
					/*GetJsonByUrl.getMessage(MessageKey.get_liren_paixu, value,
							LiRenMainActivity.this,pageNo);*/
					getPaixu();
				}else{
					GetJsonByUrl.getMessage(MessageKey.get_liren_info_simp,LiRenMainActivity. this, pageNo);
				}
				
			}
		});
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(btn_cancle.getVisibility()== View.GONE){
					btn_seleter.setVisibility(View.GONE);
					btn_cancle.setVisibility(View.VISIBLE);
					et_search.setVisibility(View.VISIBLE);
					
				}else{
					String key = et_search.getText().toString().trim();
					if(!TextUtils.isEmpty(key)){
						pageNo = 1;
						CustomProgressDialog.startProgressDialog(LiRenMainActivity.this, "数据查询中...");
						if(isSort){
					String values = value+"&SearchName="+key;
					search(MessageKey.get_liren_paixu, values);
						}else{
							String values = value+"&SearchName="+key;
							search(MessageKey.get_liren_info_simp, values);
						}
					}else{
						Toast.makeText(LiRenMainActivity.this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();
					}
				}
				
			}
		});
		
	}
	private void search(final int what,String values){
		 String url =Constant.BASE_URL+"TeaMall/teaBeauty/teaBeautyList?pageNo="+pageNo+"&pageSize=30"
					+ values;
//				String url = "http://218.244.138.142:8081/TeaMall/teaBeauty/teaBeautyList?pageNo=1&pageSize=30&city=深圳&area=&sort=0";
				httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						CustomProgressDialog.stopProgressDialog();
						if(isSort)	swipyRefresh2.setRefreshing(false);
						else swipyRefresh1.setRefreshing(false);
						
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String result = arg0.result;
						CustomProgressDialog.stopProgressDialog();
						if(isSort)	swipyRefresh2.setRefreshing(false);
						else swipyRefresh1.setRefreshing(false);
						try {
							JSONObject jobj = new JSONObject(result);
							try {
								JSONArray jsonArr =null;
								String code = jobj.getString("code");
								if(code.equals("0")){
									jsonArr = new JSONArray();
								}else{
								jsonArr = jobj.getJSONArray("result");
								}
								Message msg = new Message();
								msg.what = what;
								msg.obj = jsonArr;
								mHandler.sendMessage(msg);

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
								mLoadingDialog.cancel();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
	}

	@Override
	protected void initBoardCastListener() {
		// TODO Auto-generated method stub

	}


	private int pageNo = 1;

	@SuppressWarnings("unused")
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		city = Tab1Fragment.getCurrentCity();
		if(TextUtils.isEmpty(city))city="深圳";
		else city = city.replace("市", "");
		citys = getDistrictsBasedonCityName(city);
		pageNo = 1;
		GetJsonByUrl.getMessage(MessageKey.get_liren_info_simp, this, pageNo);
		if (mLoadingDialog == null)
			mLoadingDialog = LoadingDialog.getInstance(this);
		mLoadingDialog.show();
	}

	@Override
	public void onGetJson(String json, int message_key) {
		CustomProgressDialog.stopProgressDialog();
		if(isSort)	swipyRefresh2.setRefreshing(false);
		else swipyRefresh1.setRefreshing(false);
		try {
			JSONArray jsonArr =null;
			JSONObject jobj = new JSONObject(json);
			String code = jobj.getString("code");
			if(code.equals("0")){
				jsonArr = new JSONArray();
			}else{
			jsonArr = jobj.getJSONArray("result");
			}
			Message msg = new Message();
			msg.what = message_key;
			msg.obj = jsonArr;
			mHandler.sendMessage(msg);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
			mLoadingDialog.cancel();
		}
	}

	@Override
	public void onGetJsonFailed(int message_key) {
		// TODO Auto-generated method stubCustomProgressDialog
		CustomProgressDialog.stopProgressDialog();
		if(isSort)	swipyRefresh2.setRefreshing(false);
		else swipyRefresh1.setRefreshing(false);
		if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
			mLoadingDialog.cancel();
		}
	}



	@Override
	public void onGetJson(boolean isSuccess, String json, long request_code) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
				loadMoreData();

			}

		}
	}

	/**
	 * 加载更多数据
	 */
	private void loadMoreData() {
		pageNo++;
		if(!isSort){
		GetJsonByUrl.getMessage(MessageKey.get_liren_info_simp, this, pageNo);
		}else {
			GetJsonByUrl.getMessage(MessageKey.get_liren_paixu, value,
					LiRenMainActivity.this,pageNo);
		}
	}
	 ArrayAdapter<String> adapter;
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.btn_quancheng:
			paixu_key = "quyu_paixu";
			btn_quancheng.setTextColor(Color.parseColor("#fc9c76"));
			
			btn_zonghe.setTextColor(Color.parseColor("#000000"));
			btn_dengji.setTextColor(Color.parseColor("#000000"));
			btn_xingbie.setTextColor(Color.parseColor("#000000"));
			adapter = new ArrayAdapter<String>(this,R.layout.sort_item_num,R.id.lst_item_title,citys);  
			showAreaPopupWindow();
			break;
		case R.id.btn_zonghe:
			paixu_key = "zonghe_paixu";
			btn_zonghe.setTextColor(Color.parseColor("#fc9c76"));
			
			btn_quancheng.setTextColor(Color.parseColor("#000000"));
			btn_dengji.setTextColor(Color.parseColor("#000000"));
			btn_xingbie.setTextColor(Color.parseColor("#000000"));
			adapter = new ArrayAdapter<String>(this,R.layout.sort_item_num,R.id.lst_item_title,zonghe_paixu);  
			showAreaPopupWindow();

			break;
		case R.id.btn_dengji:
			paixu_key = "dengji_paixu";
			btn_dengji.setTextColor(Color.parseColor("#fc9c76"));
			
			btn_quancheng.setTextColor(Color.parseColor("#000000"));
			btn_zonghe.setTextColor(Color.parseColor("#000000"));
			btn_xingbie.setTextColor(Color.parseColor("#000000"));
			adapter = new ArrayAdapter<String>(this,R.layout.sort_item_num,R.id.lst_item_title,dengji_paixu);  

			showAreaPopupWindow();

			break;
		case R.id.btn_xingbie:
			paixu_key = "xingbie_paixu";
			btn_xingbie.setTextColor(Color.parseColor("#fc9c76"));
			
			btn_quancheng.setTextColor(Color.parseColor("#000000"));
			btn_zonghe.setTextColor(Color.parseColor("#000000"));
			btn_dengji.setTextColor(Color.parseColor("#000000"));
			adapter = new ArrayAdapter<String>(this,R.layout.sort_item_num,R.id.lst_item_title,xingbie_paixu);  
			showAreaPopupWindow();

			break;

		default:
			break;
		}

	}
	
	
}
