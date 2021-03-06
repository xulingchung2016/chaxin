package com.liren_app.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.liren_app.http.GetJsonByUrl;
import com.liren_app.http.GetUrl;
import com.liren_app.http.IJsonListener;
import com.liren_app.http.MessageKey;
import com.liren_app.ui.dia.LoadingDialog;
import com.liren_app.ui_opreater.HuaListener;
import com.liren_app.ui_opreater.ViewSwitcherOpreater;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.loginandregister.Login;
import com.newbrain.chaxin.teamall.CommentActivity;
import com.newbrain.jsonthread.Constant;
import com.newbrain.user.User;
import com.newbrain.utils.SharedPreferencesHelp;
import com.newbrain.utils.Utils;
import com.newbrain.xutils.Xutils_BitmapUtils;
import com.newbrain.xutils.Xutils_HttpUtils;
import com.squareup.picasso.Picasso;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class LiRenActivity extends BaseActivity implements HuaListener,
		IJsonListener {

	private LoadingDialog mLoadingDialog;

	private String id;
	private String userId;

	private JSONObject mCrrentLiRen;

	private LinearLayout lay_select_time;
	private RelativeLayout rl_pinlun;
	private RelativeLayout rl_collect;
	private ViewSwitcher vst_liren_imgs;
	private ViewSwitcherOpreater mViewSwitcherOpreater;
	private int screenNo = -1;
	private ImageButton img_selete_her;
	private LinearLayout lay_time,ll_bottom;
	private TextView txt_yuyue_info;
	private ImageView img_logo;

//	private Map<TextView, String> btn_times;
//	private List<TextView> btn_times_list;
	private ImageButton img_see_liren_info;
	private TimeAdapter adapter;

	private TextView txt_memo;
	private TextView txt_price;
	private TextView txt_name;
	private TextView txt_num;
	private TextView txt_pinlun;
	private TextView txt_chengjiao;
//	private TextView txt_storeName;
	private ImageView iv_levels;
	private HttpUtils httpUtils;
	private LinearLayout ll_tip_bottom;

	private ImageView[] page_logos;
	
	private RadioButton rb_now,rb_2,rb_3,rb_4;
	private TextView tv_area;
	private GridView gl_time;
	private Button btn_back;
//	private List<HashMap<String,String>> times = new ArrayList<HashMap<String,String>>();
	private HashMap<String,String> times = new HashMap<String,String>();
//	private List<HashMap<String,String>> timesCache = new ArrayList<HashMap<String,String>>();
	private final static int[] dengji_resources = new int[] {
		
		R.drawable.yy_baiyin, R.drawable.yy_gold, R.drawable.yy_diamonds,R.drawable.yy_emerald };
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case MessageKey.get_liren_info:

				JSONObject liren = (JSONObject) msg.obj;

				setLirenData(liren);


					String img_url;
					try {
						img_url = liren.getString("image");
						img_url = GetUrl.getImgUrl(img_url);
						
					} catch (JSONException e) {
						e.printStackTrace();
					}


				break;

			case MessageKey.get_liren_service_area:
				setService();
				break;

			case MessageKey.get_liren_img:

				img_logo.setImageBitmap((Bitmap) msg.obj);
				break;

			default:
				break;
			}
		}
	};
	/**
	 * 获取详情
	 */
	private void getDetail(){//f8cca9d951b89d880151bac0a5650006 42ff79d26a03d5d6315285ca74a6827a
		userID = User.getInstance().getId();
		usertoken = User.getInstance().getToken();
		String url = Constant.METHOD_gdetail;
		RequestParams params = new RequestParams();
		params.addBodyParameter("beautyId", id);
		httpUtils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("result", arg1);
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
//					{"message":"查询成功","result":{"sex":1,"storeName":"","memo":"","status":2,"no":"","beautyNo":"528305","certImage":"null","image":"","professional":0,"backImage":"null","id":"f8cca9d951bfa7a40151c6f99ef50011","onTime":0,"phoneNo":"","price":0,"area":"","levels":0,"communicate":0,"sales":0,"age":0,"userId":"f8cca9d951b89d880151bd3342a1000b","name":"用英语","certNo":"","frontImage":"null","storeId":""},"code":"1"}
					JSONObject	jsonObjs = new JSONObject(arg0.result);
					String code = jsonObjs.getString("code");
					if(code.equals("1")){
						JSONObject obj = jsonObjs.getJSONObject("result");
						if(obj.has("photo")){
						String photo = obj.getString("photo");
						List<String> ress = new ArrayList<String>();
						if(TextUtils.isEmpty(photo)){
						ress.add(Constant.TEAMALL_IMAGE_BASE_URL+"test.jpg");
						}else{
							ress = Arrays.asList(photo.split(","));
						}
						mViewSwitcherOpreater.setImgs(ress);
						for(int i =0;i<ress.size();i++){
							if(i<5)
							page_logos[i].setVisibility(View.VISIBLE);
						}
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private String userID,usertoken;
	private void collects(){
		if(User.getInstance().isLogin()){
			userID = User.getInstance().getId();
			if(userId.equals(userID)){
				Toast.makeText(LiRenActivity.this, "不能收藏自己的丽人！", 1).show();
				return;
			}
			usertoken = User.getInstance().getToken();
			String url = Constant.METHOD_USER_GOOD_Collect_URL;
//			String urs = Constant.METHOD_USER_GOOD_Collect_URL+"?userId="+userId+"&beauty.id="+id+"&token="+usertoken+"&type=1";
			RequestParams params = new RequestParams();
			params.addBodyParameter("userId", userID);
			params.addBodyParameter("beauty.id", id);
			params.addBodyParameter("token", usertoken);
			params.addBodyParameter("type", "1");
			httpUtils.send(HttpMethod.POST, url, params,new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Toast.makeText(LiRenActivity.this, "网络不给力！", 1).show();
					
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					try {
						if(isStop)return;
						JSONObject	jsonObjs = new JSONObject(arg0.result);
						String code = jsonObjs.getString("code");
						String msg = jsonObjs.getString("message");
						Toast.makeText(LiRenActivity.this, msg, Toast.LENGTH_SHORT).show();
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				}
			});
			}else{
				Toast.makeText(LiRenActivity.this, "你尚未登录,请先登录！", 1).show();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				openActivity(Login.class);
				startActivity(new Intent(this, Login.class));
			}
	}

	protected void setService() {
		
	}

	private void setLirenData(JSONObject obj) {
//{"beautyProfessional":null,"paymentMode":"0","beautyCertNo":"188441777","beautyPhoneNo":"18617041963","status":"0","beautyImage":"http:\/\/218.244.138.142:8081\/TeaMall\/upload\/2015\/11\/21\/dfe7032e941d47e6a12a649420fb3bfd.png","beautyHighOpinion":null,"ordersNo":"112419190410387","msg":"","beautyLevel":"0","beautyName":"李小丽","id":"5a740a0e51385d3201513937ec210016","amount":"180","beautyId":"5a740a0e5127fa6e0151283820ca0004","bookTime":"10:00","bookDate":"2015-11-24","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":"0","createDate":"2015-11-24 19:19:04","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"}
		mCrrentLiRen = obj;

		try {
			txt_memo.setText(obj.getString("memo"));
			txt_price.setText("￥" + obj.getString("price"));
			txt_name.setText(obj.getString("name"));
			txt_num.setText("编号:" + obj.getString("beautyNo"));
			txt_chengjiao.setText("成交" + obj.getString("sales"));
//			txt_storeName.setText(obj.getString("storeName"));
			
			
			String levels = obj.getString("levels");
			if(TextUtils.isEmpty(levels))levels = "0";
			iv_levels.setBackgroundResource(dengji_resources[Integer.parseInt(levels)]);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	private HashMap<String,String> data = new HashMap<String,String>();
	private Context context;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*id = getIntent().getExtras().getString("id");
		userId = getIntent().getExtras().getString("userId");*/
//		f8cca9d951bfa7a40151c22c68fd0000 f8cca9d951bfa7a40151c4bf37d4000a
		data = (HashMap<String, String>) getIntent().getSerializableExtra("data");
		if(data != null){
		id = data.get("id");
		userId = data.get("userId");
		}
		setContentView(R.layout.activity_li_ren);
		context = this;
		httpUtils = Xutils_HttpUtils.getHttpUtils(this);
		initUI();
		getDetail();
		initData();
		initControlListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_li_ren, menu);
		return true;
	}
	private ImageView iv_1,iv_2,iv_3,iv_4,iv_5;
	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		img_logo = (ImageView) findViewById(R.id.img_logo);
		txt_memo = (TextView) findViewById(R.id.txt_memo);
		txt_price = (TextView) findViewById(R.id.txt_price);
		txt_name = (TextView) findViewById(R.id.txt_name);
		txt_num = (TextView) findViewById(R.id.txt_num);
		txt_pinlun = (TextView) findViewById(R.id.tv_pinlun);
		tv_area = (TextView) findViewById(R.id.tv_area);
		btn_back= (Button) findViewById(R.id.btn_back);
		txt_chengjiao = (TextView) findViewById(R.id.txt_chengjiao);
//		txt_storeName = (TextView) findViewById(R.id.txt_storeName);
		iv_levels = (ImageView) findViewById(R.id.iv_levels);
		iv_1  = (ImageView) findViewById(R.id.img_page_1);
		iv_2  = (ImageView) findViewById(R.id.img_page_2);
		iv_3  = (ImageView) findViewById(R.id.img_page_3);
		iv_4  = (ImageView) findViewById(R.id.img_page_4);
		iv_5  = (ImageView) findViewById(R.id.img_page_5);
		// ---------------------------------------
		page_logos = new ImageView[] {
				iv_1,
				iv_2,
				iv_3,
				iv_4,
				iv_5};

		lay_select_time = (LinearLayout) findViewById(R.id.lay_select_time);
		vst_liren_imgs = (ViewSwitcher) findViewById(R.id.vst_liren_imgs);
		mViewSwitcherOpreater = new ViewSwitcherOpreater(vst_liren_imgs, this,
				this);
		/*mViewSwitcherOpreater.setImgs(new int[] { R.drawable.yy_liren001,
				R.drawable.yy_liren002, R.drawable.yy_liren003,
				R.drawable.yy_liren004, R.drawable.yy_liren005 });*/
		
		img_see_liren_info = (ImageButton) findViewById(R.id.img_see_liren_info);
		img_selete_her = (ImageButton) findViewById(R.id.img_selete_her);
		lay_time = (LinearLayout) findViewById(R.id.lay_time);
		ll_bottom= (LinearLayout) findViewById(R.id.ll_bottom);
		ll_tip_bottom = (LinearLayout) findViewById(R.id.ll_bottom_tip);
//		btn_times = new HashMap<TextView, String>();
//		btn_times_list = new ArrayList<TextView>();
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		//__________________________________________________
		rb_2 = (RadioButton) findViewById(R.id.rb_2);
		rb_3 = (RadioButton) findViewById(R.id.rb_3);
		rb_4 = (RadioButton) findViewById(R.id.rb_4);
		rb_now = (RadioButton) findViewById(R.id.rb_now);
		gl_time = (GridView) findViewById(R.id.gd_time);
		rl_pinlun = (RelativeLayout) findViewById(R.id.lay_pinlun);
		rl_collect = (RelativeLayout) findViewById(R.id.rl_collect);
		rl_pinlun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(User.getInstance().isLogin())
				{
					SharedPreferencesHelp.SavaString(context, "lirenID", id);
					Intent inte = new Intent(context, CommentActivity.class);
					inte.putExtra("type", 1);
					startActivity(inte);
				}else{
//					showShortToast("还没有登录，请登录");
					Toast.makeText(LiRenActivity.this, "还没有登录，请登录！", 500).show();
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Intent inte = new Intent(context, Login.class);
					inte.putExtra("type", 1);
					startActivity(inte);
				}
				
			}
		});

		txt_yuyue_info = (TextView) findViewById(R.id.txt_yuyue_info);
	}

	/*private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String[] _info = btn_times.get((TextView) v).split("/");
			String info = "";
			info = _info[1];

			if (info.contains("不可预约")) {
				Toast.makeText(LiRenActivity.this, "此时段不可预约！", 500).show();
			} else {
				txt_yuyue_info.setText(info.split("_")[0]);
				lay_select_time.setVisibility(View.GONE);
			}
		}
	};*/

	@Override
	protected void initControlListener() {
		// TODO Auto-generated method stub
		lay_select_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lay_select_time.setVisibility(View.GONE);
			}
		});
		rl_collect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				collects();
				
			}
		});
		img_see_liren_info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent inte = new Intent();
				inte.putExtra("liren", mCrrentLiRen.toString());
				inte.setClass(LiRenActivity.this, DianDianActivity.class);
				LiRenActivity.this.startActivity(inte);
			}
		});
		img_selete_her.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(userId.equals(User.getInstance().getId())){
					Toast.makeText(LiRenActivity.this, "不能预订自己！", 1).show();
					return;
				}
				if(TextUtils.isEmpty(time)){
					Toast.makeText(LiRenActivity.this, "请选择预约时间！", 500).show();
				}else{
					if(User.getInstance().isLogin()){
				Intent inte = new Intent();
				inte.putExtra("liren", mCrrentLiRen.toString());
				inte.putExtra("date", date);
				inte.putExtra("time", time);
				inte.setClass(LiRenActivity.this, DingDanActivity.class);
				LiRenActivity.this.startActivity(inte);
					}else{
						Toast.makeText(LiRenActivity.this, "尚未登录，请登录！", 500).show();
						startActivity(new Intent(context, Login.class));
					}
				}
			}
		});
		
		lay_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				initTimes(0);
				lay_select_time.setVisibility(View.VISIBLE);
			}
		});
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
	
	}
	
	private void initTimes(int pos){
		if(!TextUtils.isEmpty(date)){
			for(int i =0;i<12;i++){
				timeCache.get(i).put("2", "0");
			}
		}
		date= keys.get(pos);
		String time = times.get(date);
		if(!TextUtils.isEmpty(time)&&!"null".equals(time)){
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
		
		adapter.notifyDataSetChanged();
	}
	private String date,time;

	@Override
	protected void initBoardCastListener() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	public void getNext(ViewSwitcher _switcher, List<String> _img_ids) {
		int size =  _img_ids.size();
		if(size>5)size = 5;
		if (screenNo < size- 1) {
			screenNo++;
			// 设置视图切换的动画效果
			_switcher.setInAnimation(this, R.anim.yy_slide_in_right);
			_switcher.setOutAnimation(this, R.anim.yy_slide_out_left);
			// 获取下一个视图的实例
			ImageView img = (ImageView) _switcher.getNextView();
			img.setScaleType(ScaleType.FIT_XY);
			Picasso.with(this).load(_img_ids.get(screenNo).trim()).error(R.drawable.yy_liren002).resize(Utils.getwidth(context), Utils.dip2px(context, 200)).into(img);

			_switcher.showNext();

			page_logos[screenNo].setBackgroundDrawable(LiRenActivity.this
					.getResources().getDrawable(R.drawable.yy_img_show));
			if (screenNo != 0)
				page_logos[screenNo - 1]
						.setBackgroundDrawable(LiRenActivity.this
								.getResources().getDrawable(
										R.drawable.yy_img_un_show));

		}
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	public void getPrev(ViewSwitcher _switcher, List<String> _img_ids) {
		if (screenNo > 0) {
			screenNo--;
			// 设置视图切换的动画效果
			_switcher.setInAnimation(this, R.anim.yy_slide_in_lef);
			_switcher.setOutAnimation(this, R.anim.yy_slide_out_right);
			ImageView img = (ImageView) _switcher.getNextView();
			img.setScaleType(ScaleType.FIT_XY);
			Picasso.with(this).load(_img_ids.get(screenNo).trim()).error(R.drawable.yy_liren002).resize(Utils.getwidth(context), Utils.dip2px(context, 200)).into(img);
			// 切换视图
			_switcher.showPrevious();

			page_logos[screenNo].setBackgroundDrawable(LiRenActivity.this
					.getResources().getDrawable(R.drawable.yy_img_show));
			if (screenNo != 4)
				page_logos[screenNo + 1]
						.setBackgroundDrawable(LiRenActivity.this
								.getResources().getDrawable(
										R.drawable.yy_img_un_show));
		}
	}
	List<String>values;
	private int pageNo = 1;
	private List<String> keys = new ArrayList<String>();
	private HashMap<String,Integer> location = new HashMap<String,Integer>();
	private List<HashMap<String,String>>timeCache = new ArrayList<HashMap<String,String>>();
	private Date date2;
	@Override
	protected void initData() {
		String token = SharedPreferencesHelp.getString(this, "token");
		setDetail();
		String timed = "09:00,10:00,11:00,12:00,13:00,14:00,15:00,16:00,17:00,18:00,19:00,20:00";
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
		values = Arrays.asList( timed.split(","));
		for(int i= 0;i<12;i++){
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
		adapter = new TimeAdapter();
		gl_time.setAdapter(adapter);
		gl_time.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gl_time.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				HashMap<String,String> map = timeCache.get(arg2);
				String status = map.get("2");
				if(status.equals("0")){
					Toast.makeText(LiRenActivity.this, "此时段不可预约！", 500).show();
				}else{
					time = map.get("1");
					txt_yuyue_info.setText(date+" "+time);
					lay_select_time.setVisibility(View.GONE);
				}
				
			}
		});
		/*GetJsonByUrl.getMessage(MessageKey.get_liren_info, "id=" + id
				+ "&userId=" + uid+"&token="+token, this,pageNo);*/
		GetJsonByUrl.getMessage(MessageKey.get_liren_service_area,
				"token=" + token + "&userId=" + userId, this,pageNo);

		if (mLoadingDialog == null)
			mLoadingDialog = LoadingDialog.getInstance(this);
		mLoadingDialog.show();
	}
	
	private void setDetail(){
		if(data != null){
		setLirenData(new JSONObject(data));
		String img_url;
			img_url = data.get("image");
//			img_url = GetUrl.getImgUrl(img_url);
			if(!TextUtils.isEmpty(img_url)){
				Xutils_BitmapUtils.getbitmapUtils_detail(this).display(img_logo, img_url);
			}
		}

	}

	@Override
	public void onGetJson(String json, int message_key) {
		// {"msessage":"查询成功。","price":222.0,"area":"朝阳,宣武","address":null,"userId":"5a740a0e50c8435a0150cccc6e26000b","":[{"serviceTime":"09:00,10:00,11:00,12:00,13:00,14:00,15:00,16:00,17:00,18:00,19:00,20:00","date":"2015-11-14"},{"serviceTime":"09:00,10:00,11:00,12:00,13:00,14:00,15:00,16:00,17:00,18:00,19:00,20:00","date":"2015-11-13"},{"serviceTime":"09:00,10:00,11:00,12:00,13:00,15:00,16:00,18:00,19:00,20:00","date":"2015-11-12"},{"serviceTime":"09:00,10:00,11:00,12:00,13:00,14:00,15:00,16:00,17:00,18:00,19:00,20:00","date":"2015-11-15"}],"province":null,"code":"1","city":"常熟"}
		try {
			final JSONObject jobj = new JSONObject(json);
			String code = jobj.getString("code");
			if(code.equals("1")){
				final String area = jobj.getString("area");
				final String city = jobj.getString("city");
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						tv_area.setText(city+" "+area);
						try {
						/*String time;
							time = jobj.getJSONArray("serivceTime").toString();
						if(!TextUtils.isEmpty(time)){
							times = JSON.parseObject(time, new TypeReference<List<HashMap<String,String>>>(){});
							
						}*/
						org.json.JSONArray array =  jobj.getJSONArray("serivceTime");
						for(int i = 0;i<array.length();i++){
//							HashMap<String,String> map = new HashMap<String, String>();
							JSONObject timeobj = array.getJSONObject(i);
							String date = timeobj.getString("date");
							String time = timeobj.getString("serviceTime");
							if("null".equals(time))time="";
							times.put(date,time );
							
							 
						}
						
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
				
			}
			/*String code = jobj.getString("code");
			Message msg = new Message();
			msg.what = message_key;
			if(code.equals("1")){
			msg.obj = jobj.getJSONObject("result");;
			}else msg.obj = new JSONObject();
			mHandler.sendMessage(msg);*/

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (mLoadingDialog != null) {
			if (mLoadingDialog.isShowing())
				mLoadingDialog.cancel();
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
	class TimeAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return timeCache.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return timeCache.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View view, ViewGroup arg2) {
			view = LayoutInflater.from(context).inflate(R.layout.gl_item, null);
			TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
//			TextView tv_status = (TextView) view.findViewById(R.id.tv_time);
			HashMap<String,String> map = timeCache.get(arg0);
			tv_time.setText(map.get("1"));
			String status = map.get("2");
			if(status.equals("0")){
				tv_time.setBackgroundResource(R.drawable.yy_sharp_write);
				tv_time.setTextColor(Color.BLACK);
			}
			else {
				tv_time.setTextColor(Color.WHITE);
				tv_time.setBackgroundResource(R.drawable.yy_sharp_org);
			}
			
			return view;
		}
		
	}
	private boolean isStop;
	@Override
	protected void onStop() {
		isStop= true;
		super.onStop();
	}

}
