package com.newbrain.chaxin.my.mybeauty;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.liren_app.http.GetJsonByUrl;
import com.liren_app.http.IJsonListener;
import com.liren_app.http.MessageKey;
import com.newbrain.adapter.DistrictListAdapter;
import com.newbrain.adapter.MyBaseAdapter_BeautyService;
import com.newbrain.chaxin.CityListActivity;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.Tab1Fragment;
import com.newbrain.customview.CityChoosePopupWindow;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.model.City;
import com.newbrain.model.Model_BeautyServiceGD;
import com.newbrain.user.User;
import com.newbrain.utils.BundleFlag;
import com.newbrain.utils.ButtonColorFilter;
import com.newbrain.utils.CityUtil;
import com.newbrain.utils.Utils;
import com.newbrain.xutils.Xutils_DBUtils;
import com.newbrain.xutils.Xutils_HttpUtils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MyBeautyServiceFragment extends Fragment implements IJsonListener,OnClickListener{
	
	
	private Context context;
	private RadioButton rb_now,rb_2,rb_3,rb_4;
	private TextView tv_area;
	private EditText et_price;
	private Button btn_save;
	private HttpUtils httpUtils;
	private LinearLayout ll_area;
//	private List<HashMap<String,String>> datas = new ArrayList<HashMap<String,String>>();
	List<String>values;
	private List<String> keys = new ArrayList<String>();
	private HashMap<String,Integer> location = new HashMap<String,Integer>();
	private HashMap<String,String> times = new HashMap<String,String>();
	private List<HashMap<String,String>>timeCache = new ArrayList<HashMap<String,String>>();
	private Xutils_DBUtils db;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.my_beauty_service_fragment, null);
		rb_2 = (RadioButton) view.findViewById(R.id.rb_2);
		rb_3 = (RadioButton) view.findViewById(R.id.rb_3);
		rb_4 = (RadioButton) view.findViewById(R.id.rb_4);
		rb_now = (RadioButton) view.findViewById(R.id.rb_now);
		btn_save = (Button) view.findViewById(R.id.btn_save);
		tv_area = (TextView) view.findViewById(R.id.tv_area);
		ll_area = (LinearLayout) view.findViewById(R.id.ll_area);
		et_price = (EditText) view.findViewById(R.id.et_price);
		ll_area.setOnClickListener(this);
		
		ButtonColorFilter.setButtonFocusChanged(btn_save);
		return view;
	}
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 0){
				tv_area.setText(city+": "+area);
				et_price.setText(price);
				JSONObject jobj = (JSONObject) msg.obj;
				try {
					org.json.JSONArray array =  jobj.getJSONArray("serivceTime");
					serviceTime = array.toString();
					for(int i = 0;i<array.length();i++){
//					HashMap<String,String> map = new HashMap<String, String>();
						JSONObject timeobj = array.getJSONObject(i);
						String date = timeobj.getString("date");
						String time = timeobj.getString("serviceTime");
						if("null".equals(time))time="";
						times.put(date,time );
					}
					if(TextUtils.isEmpty(city))setCurrentCity(Tab1Fragment.getCurrentCity());
					else setCurrentCity(city);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				initTimes(0);
			
		};
	};
	};
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewUtils.inject(this,view);
		context = getActivity();
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		db = new Xutils_DBUtils(context);
		db.openDatabase();
		gridviewInit();
		gridviewInitRange();
		listInitRange() ;
		if(TextUtils.isEmpty(city))setCurrentCity(city);
		else setCurrentCity(city);
	}
	
	
	@ViewInject(R.id.service_gridview)
	private GridView mGridview;
	
	private MyBaseAdapter_BeautyService mAdapter;
	
	private void gridviewInit()
	{
		
		mAdapter = new MyBaseAdapter_BeautyService(context, timeCache, R.layout.beauty_service_gridview_item);
		mGridview.setAdapter(mAdapter);
		mGridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				HashMap<String,String> tmep = timeCache.get(pos);
				String status = tmep.get("2");
				if(pos != 0&& pos !=13&& pos != 14){
					if(pos != 15){
						if(status.equals("0"))
							tmep.put("2", "1");
						else tmep.put("2", "0");
					}else {//全天
						for(int i = 1;i<13;i++){
							timeCache.get(i).put("2", "1");
						}
					}
					mAdapter.notifyDataSetChanged();
				}
				
			}
		});
		
	}

	
	
	private Date date2;
	private String token,userId;
	private void gridviewInitRange()
	{
		
		 token = User.getInstance().getToken();
		 userId = User.getInstance().getId();
		String timed = " ,09:00,10:00,11:00,12:00,13:00,14:00,15:00,16:00,17:00,18:00,19:00,20:00,21:00,22:00,全天";
		date2=new   Date();//取时间 
        Calendar   calendar   =   new   GregorianCalendar(); 
        calendar.setTime(date2);
        String time1 = new SimpleDateFormat("yyyy-MM-dd").format(date2);
        keys.add(time1);
        Log.i("time", time1);
        for(int i= 0;i<3;i++){
        calendar.add(Calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
        date2=calendar.getTime();
       //这个时间就是日期往后推一天的结果 
        String time = new SimpleDateFormat("yyyy-MM-dd").format(date2);
        
        calendar.setTime(date2); 
        keys.add(time);
        Log.i("time", time);
        }
        
		/*for(int i = 0;i<4;i++){
			Calendar c = Calendar.getInstance(); 
			String time = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+(c.get(Calendar.DAY_OF_MONTH)+i);
			keys.add(time);
		}*/
		values = Arrays.asList( timed.split(","));
		for(int i= 0;i<16;i++){
			HashMap<String,String> map = new HashMap<String, String>();
			map.put("1", values.get(i));
			map.put("2", "0");
			location.put(values.get(i), i);
			timeCache.add(map);
		}
		
		
		rb_now.setText("今天");
		rb_2.setText(keys.get(1).substring(keys.get(1).indexOf("-")+1));
		rb_3.setText(keys.get(2).substring(keys.get(2).indexOf("-")+1));
		rb_4.setText(keys.get(3).substring(keys.get(3).indexOf("-")+1));
		
		GetJsonByUrl.getMessage(MessageKey.get_liren_service_area,
				"token=" + token + "&userId=" + userId, this,1);
		
	}

	private void listInitRange() {
		rb_2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				initTimes(1);
				
			}
		});
		
		rb_4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				initTimes(3);
				
			}
		});
		rb_3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				initTimes(2);
				
			}
		});
	rb_now.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		initTimes(0);
	}
	});
	//
	btn_save.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			String price = et_price.getText().toString().trim();
			if(TextUtils.isEmpty(price)){
				Toast.makeText(context, "请填写服务价格", Toast.LENGTH_SHORT).show();
				return;
			}
			CustomProgressDialog.startProgressDialog(context, "提交数据中...");
			if(!TextUtils.isEmpty(date)){
				String serviceTimes = "";
				for(int i= 1;i<13;i++){
					if(timeCache.get(i).get("2").equals("1"))
					serviceTimes+=timeCache.get(i).get("1")+",";
					timeCache.get(i).put("2", "0");
				}
				if(!TextUtils.isEmpty(serviceTimes))
				serviceTimes = serviceTimes.substring(0,serviceTimes.length()-1 );
				times.put(date, serviceTimes);
			}
			int size = times.size();
			List<HashMap<String,String>> datas = new ArrayList<HashMap<String,String>>();
			for(int i = 0;i<size;i++){
				String date = keys.get(i);
				String Service = times.get(date);
				HashMap<String,String> map = new HashMap<String, String>();
				map.put("date", date);
				map.put("serviceTime", Service);
				datas.add(map);
			}
			String jsong = JSON.toJSONString(datas);
			RequestParams params = new RequestParams();
			params.addBodyParameter("token", token);
			params.addBodyParameter("userId", userId);
			params.addBodyParameter("city", city);
			params.addBodyParameter("area", area);
			params.addBodyParameter("price", price);
			params.addBodyParameter("serivceTime", jsong);
			/*String url = Constant.METHOD_saveTeaBeauty+"?userId="+userId+"&token="
			+token+"&city="+city+"&area="+area+"&price="+price+"&serivceTime="+jsong;*/
			httpUtils.send(HttpMethod.POST, Constant.METHOD_saveTeaBeauty2,params, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show();
					CustomProgressDialog.stopProgressDialog();
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					try {
						CustomProgressDialog.stopProgressDialog();
						JSONObject	jsonObjs = new JSONObject(arg0.result);
						String msg = jsonObjs.getString("message");
						String code = jsonObjs.getString("code");
						Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
					} catch (JSONException e) {
						Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
	});
	
	}
	
	private String city,area,serviceTime;
	private void initTimes(int pos){
		if(!TextUtils.isEmpty(date)){
			String serviceTimes = "";
			for(int i= 1;i<13;i++){
				if(timeCache.get(i).get("2").equals("1"))
				serviceTimes+=timeCache.get(i).get("1")+",";
				timeCache.get(i).put("2", "0");
			}
			if(!TextUtils.isEmpty(serviceTimes))
			serviceTimes = serviceTimes.substring(0,serviceTimes.length()-1 );
			times.put(date, serviceTimes);
		}
		date= keys.get(pos);
		String time = times.get(date);
		if(!TextUtils.isEmpty(time)){
		List <String> valueTemp = Arrays.asList( time.split(","));
		for(int i = 0;i<valueTemp.size();i++){
			int poss = location.get(valueTemp.get(i));
			timeCache.get(poss).put("2", "1");
		}
		
		}else{
			for(int i = 0;i<timeCache.size();i++){
				
				timeCache.get(i).put("2", "0");
			}
		}
		mAdapter.setList(timeCache);
		
	}
	
	private ArrayList<City> mCityList;
	private ArrayList<String> mCityLetterList;
	private HashMap<String, Integer> mCityMap;
	private String mCurrentDistrict;
	// 快捷搜索词
	private String[] mLetterStrs = { "&", "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };
//	private ImageView mUpDownArrow;
	private String[] mDistrictsOfCurrentCity;
	private int mChosenDistrictIndex = -1;
	private CityChoosePopupWindow mPopupWindow;
	private static String mCurrentCity;
private void showAreaPopupWindow() {
		
		mPopupWindow = new CityChoosePopupWindow(context,
				this,1);

//		mPopupWindow.showAsDropDown(rb_now);
//		mPopupWindow.showAtLocation(parent, gravity, x, y);
		 int[] location = new int[2];  
		 tv_area.getLocationOnScreen(location);  
		          
		 mPopupWindow.showAtLocation(tv_area, Gravity.NO_GRAVITY, location[0], location[1]-mPopupWindow.getHeight());  

		mPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {

			}
		});
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if (mCityLetterList == null || mCityList == null || mCityMap == null)
					createCityListForCityChoose();
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						
						updatePopupWindowData();
					}
				});
			}
		}).start();

	}

private void setCurrentCity(String mCurrentCity) {
	if (mCurrentCity == null||mCurrentCity.equals("定位中..."))
		mCurrentCity = "深圳市";
	this.mCurrentCity = mCurrentCity.replace("市", "");
}
public static String getCurrentCity() {
	return mCurrentCity;
}

private void createCityListForCityChoose() {
	try {
		String content = Utils.getAssetsFie(context, "city.json");
		dealWithJson(content);
	} catch (IOException e) {
		Log.e("aaa", "city init failed", e);
	}
}


private void dealWithJson(String content) {

	try {
		JSONObject json = new JSONObject(content);
		String status = json.getString("status");
		if ("200".equals(status)) {
			JSONObject result = json.getJSONObject("result");
//			int cityVersion = result.optInt("version");
			JSONObject data = result.getJSONObject("city");
			HashMap<String, Integer> tempCityHashMap = new HashMap<String, Integer>();
			ArrayList<String> temp_city_letter_list = new ArrayList<String>();
			ArrayList<City> tempCityList = new ArrayList<City>();
			for (int m = 0; m < mLetterStrs.length; m++) {
				String key = mLetterStrs[m];
				JSONArray array = data.optJSONArray(key);
				if (array == null) {
					continue;
				}
				temp_city_letter_list.add(key);
				for (int i = 0; i < array.length(); i++) {
					JSONObject json_city = array.getJSONObject(i);
					City model_item = new City();
					model_item.name = json_city.optString("name");
					model_item.code = json_city.optString("cityCode");
					if (i == 0) {
						model_item.letter = key;
						tempCityHashMap.put(key, tempCityList.size());
					}
					tempCityList.add(model_item);
				}
			}

			if (mCityList == null || mCityList.size() <= 0) {
				mCityList = tempCityList;
				mCityMap = tempCityHashMap;
				mCityLetterList = temp_city_letter_list;
			}
		} else {
			if (mCityList == null || mCityList.size() <= 0) {
				showErrorDialog(getString(R.string.city_get_error));
			}

		}
	} catch (JSONException e) {
		e.printStackTrace();
	}

}
private void showErrorDialog(String string) {

	Dialog dialog = new Dialog(context);

	dialog.setOnCancelListener(new OnCancelListener() {

		@Override
		public void onCancel(DialogInterface dialog) {
			dialog.dismiss();

		}
	});

	dialog.show();

}
private HashMap<String, String[]> mDistrictsOfcityMap = new HashMap<String, String[]>();;
private List<HashMap<String, String>> cityList = new ArrayList<HashMap<String,String>>();
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

	CityUtil cityUtil = new CityUtil(context, city);
	List<String> lists = cityUtil.getItsDistricts();
	String[] districtsOfThisCity = lists.toArray(new String[lists.size()]);
	mDistrictsOfcityMap.put(city, districtsOfThisCity);

	return districtsOfThisCity;
}
private DistrictListAdapter adapter;
	private void updatePopupWindowData() {
		cityList.clear();
//		mDistrictsOfCurrentCity = getDistrictsBasedonCityName(getCurrentCity());
		String sql = "select * from areas where cityid = (select cityid from cities where city like '%"+mCurrentCity+"%')";
		Cursor cursor = db.database.rawQuery(sql, null);
		List<String> temp = new ArrayList<String>();
		if(!TextUtils.isEmpty(area)){
			temp = Arrays.asList(area.split(","));
		}
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("area"));
			
			HashMap<String,String> map = new HashMap<String, String>();
			map.put("name", name);
			if(temp.contains(name))map.put("status", "1");
			else map.put("status", "0");
			cityList.add(map);
		}
		cursor.close();
		adapter = new DistrictListAdapter(context, cityList,
				1);
		mPopupWindow.getDistrictGridView().setAdapter(
				adapter);

		mPopupWindow.getDistrictGridView().setOnItemClickListener(
				mGridViewItemListener);

		mPopupWindow.getCurrentCityTextView().setText(getCurrentCity());

//		actionbar_left.setText(getCurrentCity());
	}
	
	private OnItemClickListener mGridViewItemListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			HashMap<String,String> map = cityList.get(arg2);
			String status  = map.get("status");
			if(status.equals("1"))
			map.put("status", "0");
			else map.put("status", "1");
			adapter.notifyDataSetChanged();
			
		}
	};
	
	private String date,time,price;

	@Override
	public void onGetJson(String json, int message_key) {
		try {
			final JSONObject jobj = new JSONObject(json);
			String code = jobj.getString("code");
			if(code.equals("1")){
				 area = jobj.getString("area");
				 city = jobj.getString("city");
				 price = jobj.getString("price");
				Message msgMessage = handler.obtainMessage();
				msgMessage.what = 0;
				msgMessage.obj =jobj;
				handler.sendMessage(msgMessage);
				
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		
	}

	@Override
	public void onGetJsonFailed(int message_key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetJson(boolean isSuccess, String json, long request_code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.change_city_linearlayout:
			gotoCityListActivity();
			break;
		case R.id.actionbar_homepager_style_textview_left:
			showAreaPopupWindow();
			break;
		case R.id.ll_area:
			showAreaPopupWindow();
			break;
		case R.id.btn_ok:
			city = mCurrentCity;
			String areaTemp = "";
			for(int i= 0;i<cityList.size();i++){
				String name = cityList.get(i).get("name");
				if(cityList.get(i).get("status").equals("1"))areaTemp+=name+",";
			}
			area = areaTemp.substring(0, areaTemp.length() -1);
			tv_area.setText(city+": "+area);
			mPopupWindow.dismiss();
			break;
		case R.id.btn_cancle:
			mPopupWindow.dismiss();
			break;
				

		default:
			break;
		}
		
	}
	private final int CITY_CHOOSE_REQUEST_CODE = 10;
	private void gotoCityListActivity() {

		Intent intent = new Intent(context, CityListActivity.class);
		intent.putExtra(BundleFlag.CITY_LIST, mCityList);
		intent.putStringArrayListExtra(BundleFlag.CITY_LETTERS, mCityLetterList);
		intent.putExtra(BundleFlag.CITY_MAP, mCityMap);
		startActivityForResult(intent, CITY_CHOOSE_REQUEST_CODE);

	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (CITY_CHOOSE_REQUEST_CODE == requestCode
				&& resultCode == BundleFlag.SUCCESS) {
			City city = (City) data.getSerializableExtra(BundleFlag.CITY_MODEL);
			if (city != null) {
				setCity(city);
			}
			
			mCurrentDistrict = "";
			
			return;
		}
		

		super.onActivityResult(requestCode, resultCode, data);
	}
	private void setCity(City city) {
		mChosenDistrictIndex = -1;
		setCurrentCity(city.name.toString());
		updatePopupWindowData();
	}
	
	
	
	
	

}
