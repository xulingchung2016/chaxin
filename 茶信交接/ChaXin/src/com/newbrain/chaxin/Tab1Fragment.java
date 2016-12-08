package com.newbrain.chaxin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.ArraySerializer;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.liren_app.ui.LiRenActivity;
import com.newbrain.adapter.DistrictListAdapter;
import com.newbrain.adapter.MyAdapter_Tab1Gridview;
import com.newbrain.chaxin.loginandregister.Login;
import com.newbrain.chaxin.message.MessageActivity;
import com.newbrain.chaxin.teacishan.TeaCiShanActivity;
import com.newbrain.chaxin.teadingzhong.TeaDingZhongActivity;
import com.newbrain.chaxin.teamall.DetailActivity;
import com.newbrain.chaxin.teamall.TeaMallActivity;
import com.newbrain.chaxin.teasearchtea.TeaSearchTeaActivity;
import com.newbrain.chaxin.teazhongchou.TeaZhongChouActivity;
import com.newbrain.customview.CityChoosePopupWindow;
import com.newbrain.jsonthread.Constant;
import com.newbrain.model.City;
import com.newbrain.model.Model_Tab1Gridview;
import com.newbrain.user.User;
import com.newbrain.utils.BundleFlag;
import com.newbrain.utils.CityUtil;
import com.newbrain.utils.Utils;
import com.newbrain.viewflow.TitleImageAdapter;
import com.newbrain.viewflow.ViewFlow;
import com.newbrain.viewflow.ViewFlowIndicator;
import com.newbrain.xutils.Xutils_HttpUtils;
import com.squareup.picasso.Picasso;

@SuppressLint("ClickableViewAccessibility")
public class Tab1Fragment extends Fragment implements AMapLocationListener,Runnable,OnClickListener{

	private Context context;
	/** viewflow的初始化 */
	@ViewInject(R.id.tab1_viewflow)
	private ViewFlow mViewFlow;
	@ViewInject(R.id.sc_root)
	private ScrollView scrollView;

	@ViewInject(R.id.tab1_viewflow_indicator)
	private ViewFlowIndicator mViewFlowIndicator;
	/** viewflow的list */
	private List<String> mListViewFlow;

	/** 主要茶板块 gridview */
	@ViewInject(R.id.tab1_gridview)
	private GridView mGridView;
	private List<Model_Tab1Gridview> mListGridView;
	private MyAdapter_Tab1Gridview mMyAdapter_Tab1Gridview;
	private LocationManagerProxy aMapLocManager = null;
	private AMapLocation aMapLocation;// 用于判断定位超时
	private Handler handler = new Handler();
	private final int CITY_CHOOSE_REQUEST_CODE = 10;
	private ArrayList<City> mCityList;
	private ArrayList<String> mCityLetterList;
	private HashMap<String, Integer> mCityMap;
	private String mCurrentDistrict;
	private List<TextView> tv_goods = new ArrayList<TextView>();
	private List<ImageView> iv_goods = new ArrayList<ImageView>();
	// 快捷搜索词
	private String[] mLetterStrs = { "&", "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };
//	private ImageView mUpDownArrow;
	private String[] mDistrictsOfCurrentCity;
	private int mChosenDistrictIndex = -1;
	private HttpUtils httpUtils;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.tab1_fragment, null);

		return view;
	}
	
	
	/**
	 * 获取首页轮播图
	 */
	private void getLunBoPices(){
		mListViewFlow = new ArrayList<String>();
		mListViewFlow.clear();
		String url = Constant.METHOD_getTeaMallIamges;
		RequestParams params = new RequestParams();
		httpUtils.send(HttpMethod.POST,url,params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.i("tag", arg1);
				mListViewFlow
				.add("http://www.tianyutea.com/uploadfiles/others/image/201407/20140708104060566056.jpg");
		mListViewFlow
				.add("http://www.tianyutea.com/uploadfiles/others/image/201407/20140708104060566056.jpg");
		mListViewFlow
				.add("http://www.tianyutea.com/uploadfiles/others/image/201407/20140708104060566056.jpg");
		viewFlowInit();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
//				{"message":"数据不为空","result":[{"total":1000.0,"serviceScore":0.0,"storeName":"青藤茶馆","primeCost":"","memo":"口感润滑，茶叶清香","teaTypeName":"绿茶","image":"","storageMethod":"冷藏","brandName":"福建安溪","brandId":"","procucingArea":"福建","id":"1","teaTypeId":"1","homebred":false,"price":100.0,"goodsScore":0.0,"address":"金銮时代大厦","packaging":"袋装","salesTotal":"","storeId":"1","expressScore":0.0,"evalToal":"","goodsName":"铁观音12"},{"total":1000.0,"serviceScore":0.0,"storeName":"青藤茶馆","primeCost":"","memo":"<p>商品名称：大树乔木茶 普洱茶 生茶 古树茶 七子饼茶商品编号：90005382店铺：<a href=\"http://dadesheng.tealg.com/\" class=\"or\" style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial; color: rgb(183, 29, 29); text-decoration: none;\">大德盛普洱茶官方旗舰店</a>上架时间：2014/12/17 0:00:00商品毛重：357g商品产地：云南南糯山包装：纸皮工艺：传统工艺&nbsp;</p><p><img src=\"../upload/4866a86ebb2040c2a2911043f888f3a4.jpg\" alt=\"4866a86ebb2040c2a2911043f888f3a4.jpg\" /><br /></p>","teaTypeName":"","image":"","storageMethod":"","brandName":"","brandId":"","procucingArea":"福建","id":"5a740a0e5101d76d01510399c8ee0000","teaTypeId":"1","homebred":"","price":700.0,"goodsScore":0.0,"address":"金銮时代大厦","packaging":"","salesTotal":"","storeId":"1","expressScore":0.0,"evalToal":"","goodsName":"大树乔木茶 普洱茶 生茶 古树茶 七子饼茶"},{"total":100.0,"serviceScore":0.0,"storeName":"青藤茶馆","primeCost":"","memo":"<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial; color: rgb(51, 51, 51); font-size: 12px;\"><tbody style=\"margin: 0px; padding: 0px; list-style: none;\"><tr style=\"margin: 0px; padding: 0px; list-style: none;\"><td height=\"25\" style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial;\">商品名称：大树乔木茶 普洱茶 生茶 古树茶 七子饼茶</td><td style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial;\">商品编号：90005382</td><td style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial;\">店铺：<a href=\"http://dadesheng.tealg.com/\" class=\"or\" style=\"margin: 0px; padding: 0px; list-style: none; color: rgb(183, 29, 29); text-decoration: none;\">大德盛普洱茶官方旗舰店</a></td></tr><tr style=\"margin: 0px; padding: 0px; list-style: none;\"><td height=\"25\" style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial;\">上架时间：2014/12/17 0:00:00</td><td style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial;\">商品毛重：357g</td><td style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial;\">商品产地：云南南糯山</td></tr><tr style=\"margin: 0px; padding: 0px; list-style: none;\"><td height=\"25\" style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial;\">包装：纸皮</td><td style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial;\">工艺：传统工艺</td></tr></tbody></table>","teaTypeName":"","image":"","storageMethod":"","brandName":"","brandId":"","procucingArea":"福建","id":"5a740a0e5101d76d0151039be1c50002","teaTypeId":"1","homebred":true,"price":700.0,"goodsScore":0.0,"address":"金銮时代大厦","packaging":"","salesTotal":"","storeId":"1","expressScore":0.0,"evalToal":"","goodsName":"大树乔木茶 普洱茶 生茶 古树茶 七子饼茶"}],"code":"1"}
				String result = arg0.result;
			
				mListViewFlow.clear();
				try {
					JSONObject obj = new JSONObject(result);
					String code = obj.getString("code");
					if(code.equals("1")){
						String images = obj.getString("images");
						String [] datas = images.split(",");
						
						mListViewFlow.addAll(Arrays.asList(datas));
						viewFlowInit();
						/*JSONArray array = obj.getJSONArray("result");
						for(int i = 0;i<array.length();i++){
							JSONObject objC = array.getJSONObject(i);
							String url = objC.getString("images");
							String msg = objC.getString("goodsName");
							String id = objC.getString("id");
							tv_goods.get(i).setTag(id);
							tv_goods.get(i).setText(msg);
							if(!TextUtils.isEmpty(url)){
								Picasso.with(context).load(url).error(R.drawable.yy_cha_1).centerCrop().resize(80, 60).into(iv_goods.get(i));
							}
						}*/
					}
						
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		context = getActivity();
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		ViewUtils.inject(this, view);
		startLocation();
		actionbarInit();
		gridviewInit();
		gridviewInitMoreHappy();
		initTui();
		getGoods();
		getChaliren();
		getLunBoPices();
		

	}
	
	private void initTui() {
		tv_goods.clear();
		tv_goods.add(tv_goods_1);
		tv_goods.add(tv_goods_2);
		tv_goods.add(tv_goods_3);
		tv_goods.add(tv_liren_1);
		tv_goods.add(tv_liren_2);
		tv_goods.add(tv_liren_3);
		iv_goods.add(iv_goods_1);
		iv_goods.add(iv_goods_2);
		iv_goods.add(iv_goods_3);
		iv_goods.add(iv_liren_1);
		iv_goods.add(iv_liren_2);
		iv_goods.add(iv_liren_3);
		
		
	}

	private void getChaliren() {
		String url = Constant.METHOD_teaBeautyList+"?pageNo=1&pageSize=3&recommand=1";
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
//				{"message":"数据不为空","result":[{"sex":"1","memo":"1111112","beautyNo":"555210","no":"","image":"http://218.244.138.142:8081/TeaMall/upload/2015/11/05/b7eb37d996754b309bb746e50a1cf55a.png","certImage":"","backImage":"","phoneNo":"1386888889","id":"5a740a0e50a1d1cf0150a1d9c4ed0001","area":"","price":"120.0","levels":"","sales":"","name":"sdasdasdasd","userId":"402880ed4ef78c65014ef792c30b0002","age":"","certNo":"12345678","storeId":"","frontImage":""},{"sex":"0","memo":"优质泡茶服务","beautyNo":"333943","no":"","image":"http://218.244.138.142:8081/TeaMall/upload/2015/11/11/2a8b4897107b49bbbec073887691bce1.png","certImage":"","backImage":"","phoneNo":"18711214442","id":"5a740a0e50ece5d00150ef96af460011","area":"","price":"120.0","levels":"","sales":"1","name":"南国丽园美容","userId":"5a740a0e50ece5d00150ef91112d000b","age":"","certNo":"48051778","storeId":"","frontImage":""},{"sex":"1","memo":"在乎多少人生路过年轻化为什么样本文学奖品","beautyNo":"864069","no":"","image":"http://218.244.138.142:8081/TeaMall/upload/2015/11/15/738b450f0958447fae7519264091156f.png","certImage":"","backImage":"","phoneNo":"18686568656","id":"5a740a0e50f1eed90150f63875b90039","area":"","price":"230.0","levels":"","sales":"2","name":"在人说","userId":"5a740a0e50c8435a0150cccc6e26000b","age":"","certNo":"","storeId":"","frontImage":""}],"code":"1"}
				String result = arg0.result;
				try {
					JSONObject obj = new JSONObject(result);
					String code = obj.getString("code");
					if(code.equals("1")){
						JSONArray array = obj.getJSONArray("result");
						for(int i = 0;i<array.length();i++){
							JSONObject objC = array.getJSONObject(i);
							HashMap<String, String> data = JSON.parseObject(objC.toString(),new TypeReference<HashMap<String, String>>(){});
							String url = objC.getString("image");
							String msg = objC.getString("name");
//							String id = objC.getString("id");
							tv_goods.get(i+3).setText(msg);
							tv_goods.get(i+3).setTag(data);
							if(!TextUtils.isEmpty(url)){
								Picasso.with(context).load(url).error(R.drawable.yy_cha_1).centerCrop().resize(80, 60).into(iv_goods.get(i+3));
							}
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	private void getGoods() {
		String url = Constant.METHOD_USER_GOODSLIST+"?pageNo=1&pageSize=3&recommand=1&isAdvise=1";
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
//				{"message":"数据不为空","result":[{"total":1000.0,"serviceScore":0.0,"storeName":"青藤茶馆","primeCost":"","memo":"口感润滑，茶叶清香","teaTypeName":"绿茶","image":"","storageMethod":"冷藏","brandName":"福建安溪","brandId":"","procucingArea":"福建","id":"1","teaTypeId":"1","homebred":false,"price":100.0,"goodsScore":0.0,"address":"金銮时代大厦","packaging":"袋装","salesTotal":"","storeId":"1","expressScore":0.0,"evalToal":"","goodsName":"铁观音12"},{"total":1000.0,"serviceScore":0.0,"storeName":"青藤茶馆","primeCost":"","memo":"<p>商品名称：大树乔木茶 普洱茶 生茶 古树茶 七子饼茶商品编号：90005382店铺：<a href=\"http://dadesheng.tealg.com/\" class=\"or\" style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial; color: rgb(183, 29, 29); text-decoration: none;\">大德盛普洱茶官方旗舰店</a>上架时间：2014/12/17 0:00:00商品毛重：357g商品产地：云南南糯山包装：纸皮工艺：传统工艺&nbsp;</p><p><img src=\"../upload/4866a86ebb2040c2a2911043f888f3a4.jpg\" alt=\"4866a86ebb2040c2a2911043f888f3a4.jpg\" /><br /></p>","teaTypeName":"","image":"","storageMethod":"","brandName":"","brandId":"","procucingArea":"福建","id":"5a740a0e5101d76d01510399c8ee0000","teaTypeId":"1","homebred":"","price":700.0,"goodsScore":0.0,"address":"金銮时代大厦","packaging":"","salesTotal":"","storeId":"1","expressScore":0.0,"evalToal":"","goodsName":"大树乔木茶 普洱茶 生茶 古树茶 七子饼茶"},{"total":100.0,"serviceScore":0.0,"storeName":"青藤茶馆","primeCost":"","memo":"<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial; color: rgb(51, 51, 51); font-size: 12px;\"><tbody style=\"margin: 0px; padding: 0px; list-style: none;\"><tr style=\"margin: 0px; padding: 0px; list-style: none;\"><td height=\"25\" style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial;\">商品名称：大树乔木茶 普洱茶 生茶 古树茶 七子饼茶</td><td style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial;\">商品编号：90005382</td><td style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial;\">店铺：<a href=\"http://dadesheng.tealg.com/\" class=\"or\" style=\"margin: 0px; padding: 0px; list-style: none; color: rgb(183, 29, 29); text-decoration: none;\">大德盛普洱茶官方旗舰店</a></td></tr><tr style=\"margin: 0px; padding: 0px; list-style: none;\"><td height=\"25\" style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial;\">上架时间：2014/12/17 0:00:00</td><td style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial;\">商品毛重：357g</td><td style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial;\">商品产地：云南南糯山</td></tr><tr style=\"margin: 0px; padding: 0px; list-style: none;\"><td height=\"25\" style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial;\">包装：纸皮</td><td style=\"margin: 0px; padding: 0px; list-style: none; font-family: 'Microsoft Yahei', Arial;\">工艺：传统工艺</td></tr></tbody></table>","teaTypeName":"","image":"","storageMethod":"","brandName":"","brandId":"","procucingArea":"福建","id":"5a740a0e5101d76d0151039be1c50002","teaTypeId":"1","homebred":true,"price":700.0,"goodsScore":0.0,"address":"金銮时代大厦","packaging":"","salesTotal":"","storeId":"1","expressScore":0.0,"evalToal":"","goodsName":"大树乔木茶 普洱茶 生茶 古树茶 七子饼茶"}],"code":"1"}
				String result = arg0.result;
				try {
					JSONObject obj = new JSONObject(result);
					String code = obj.getString("code");
					if(code.equals("1")){
						JSONArray array = obj.getJSONArray("result");
						for(int i = 0;i<array.length();i++){
							JSONObject objC = array.getJSONObject(i);
							String url = objC.getString("image");
							String msg = objC.getString("goodsName");
							String id = objC.getString("id");
							tv_goods.get(i).setTag(id);
							tv_goods.get(i).setText(msg);
							if(!TextUtils.isEmpty(url)){
								Picasso.with(context).load(url).error(R.drawable.yy_cha_1).centerCrop().resize(80, 60).into(iv_goods.get(i));
							}
						}
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	//定位当前城市
	private void startLocation() {
		
//		mCurrentCityDistrictTextview = (TextView) findViewById(R.id.current_city_district_textview);
//		mUpDownArrow = (ImageView) findViewById(R.id.up_down_arrow);

		aMapLocManager = LocationManagerProxy.getInstance(context);
		/*
		 * mAMapLocManager.setGpsEnable(false);//
		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
		 * API定位采用GPS和网络混合定位方式
		 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
		 */
		aMapLocManager.requestLocationData(
				LocationProviderProxy.AMapNetwork, 2000, 10, this);
		handler.postDelayed(this, 12000);// 设置超过12秒还没有定位到就停止定位
	}

	@ViewInject(R.id.actionbar_homepager_style_textview_left)
	private TextView actionbar_left;
	@ViewInject(R.id.rl_top)
	private RelativeLayout rl_top;
	@ViewInject(R.id.ll_lr)
	private LinearLayout ll_lr;
	
	@ViewInject(R.id.actionbar_homepager_style_textview_name_center)
	private TextView actionbar_center;
	
	@ViewInject(R.id.homepager_limite_time_shop_textview1)
	private TextView tv_goods_1;
	@ViewInject(R.id.homepager_limite_time_shop_textview2)
	private TextView tv_goods_2;
	@ViewInject(R.id.homepager_limite_time_shop_textview3)
	private TextView tv_goods_3;
	@ViewInject(R.id.homepager_limite_time_shop_imageview1)
	private ImageView iv_goods_1;
	@ViewInject(R.id.homepager_limite_time_shop_imageview2)
	private ImageView iv_goods_2;
	@ViewInject(R.id.homepager_limite_time_shop_imageview3)
	private ImageView iv_goods_3;
	
	@ViewInject(R.id.beautiful_men_recommed_textview1)
	private TextView tv_liren_1;
	@ViewInject(R.id.beautiful_men_recommed_textview2)
	private TextView tv_liren_2;
	@ViewInject(R.id.beautiful_men_recommed_textview3)
	private TextView tv_liren_3;
	@ViewInject(R.id.beautiful_men_recommed_imageview1)
	private ImageView iv_liren_1;
	@ViewInject(R.id.beautiful_men_recommed_imageview2)
	private ImageView iv_liren_2;
	@ViewInject(R.id.beautiful_men_recommed_imageview3)
	private ImageView iv_liren_3;
	
	@ViewInject(R.id.actionbar_homepager_style_button_message_right)
	private Button actionbar_right;

	private void actionbarInit() {
		// TODO Auto-generated method stub

		actionbar_left.setText("定位中...");
		actionbar_left.setVisibility(View.VISIBLE);
		actionbar_left.setOnClickListener(this);
		actionbar_center.setText("");
		actionbar_center.setVisibility(View.VISIBLE);
		
			
		/*actionbar_right.setVisibility(View.VISIBLE);
		actionbar_right.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//消息
				startActivity(new Intent(context, MessageActivity.class));
				
			}
		});*/

	}

	/** 限时抢购 */

	@OnClick({ R.id.homepager_limite_time_shop_linear1,
			R.id.homepager_limite_time_shop_linear2,
			R.id.homepager_limite_time_shop_linear3, })
	private void onClick_Homepager(View v) {
		String goodID = ""; 
		switch (v.getId()) {
		case R.id.homepager_limite_time_shop_linear1:
			goodID = (String) tv_goods.get(0).getTag();
			break;
		case R.id.homepager_limite_time_shop_linear2:
			goodID = (String) tv_goods.get(1).getTag();
			break;
		case R.id.homepager_limite_time_shop_linear3:
			goodID = (String) tv_goods.get(2).getTag();
			break;

		default:
			break;
		}
		SharedPreferences sp = context.getSharedPreferences("CHAXIN_CONFIG", context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("goodid_for_detail", goodID);
		editor.putBoolean("isShangpu", false);
		editor.commit();
		startActivity(new Intent(context, DetailActivity.class));

	}
	
	/** 茶丽人推荐 */

	@SuppressWarnings("unchecked")
	@OnClick({ R.id.beautiful_men_recommed_linear1,
			R.id.beautiful_men_recommed_linear2,
			R.id.beautiful_men_recommed_linear3, })
	private void onClick_Homepager2(View v) {
		Intent intent = new Intent(context, LiRenActivity.class);
		switch (v.getId()) {
		case R.id.beautiful_men_recommed_linear1:
			intent.putExtra("data", (HashMap<String, String>)tv_goods.get(3).getTag());
			break;
		case R.id.beautiful_men_recommed_linear2:
			intent.putExtra("data", (HashMap<String, String>)tv_goods.get(4).getTag());
			break;
		case R.id.beautiful_men_recommed_linear3:
			intent.putExtra("data", (HashMap<String, String>)tv_goods.get(5).getTag());
			break;

		default:
			break;
		}
		
		startActivity(intent);
		

	}
	@OnClick({R.id.ll_lr,R.id.ll_good})
	private void onclick_detial(View v){
		switch (v.getId()) {
		case R.id.ll_lr:
			Tab1Fragment.this.getActivity().startActivity(
					(new Intent(getActivity(),
							com.liren_app.ui.LiRenMainActivity.class)));
			break;
		case R.id.ll_good:
			startActivity(new Intent(context, TeaMallActivity.class));
			break;

		default:
			break;
		}
	}

	/** viewflowinit初始化 */
	private void viewFlowInit() {
		TitleImageAdapter adapter = new TitleImageAdapter(context,
				mListViewFlow);
		mViewFlowIndicator.setToltal(mListViewFlow.size());

		mViewFlow.setmSideBuffer(mListViewFlow.size());
		mViewFlow.setIndicator(mViewFlowIndicator);
		mViewFlow.setAdapter(adapter);
		mViewFlow.setTimeSpan(3000);
		// mViewFlow.setSelection(mListViewFlow.size()*3000);
		mViewFlow.startAutoFlowTimer();
		
		mViewFlow.setOnTouchListener(new OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        if (event.getAction() == MotionEvent.ACTION_UP) {
		            scrollView.requestDisallowInterceptTouchEvent(false);
		        } else {
		            scrollView.requestDisallowInterceptTouchEvent(true);
		        }
		        return false;
		    }
		});

	}

	/** 初始化gridview */
	private void gridviewInit() {

		mListGridView = new ArrayList<Model_Tab1Gridview>();

		listGridviewDataInit();

		mMyAdapter_Tab1Gridview = new MyAdapter_Tab1Gridview(context,
				mListGridView);

		mGridView.setAdapter(mMyAdapter_Tab1Gridview);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// TODO Auto-generated method stub

				switch (position) {
				/** 茶商城 */
				case 0:

					startActivity(new Intent(context, TeaMallActivity.class));

					break;

				/** 茶众筹 */
				case 1:
					if(User.getInstance().isLogin()){
						startActivity(new Intent(getActivity(),
								TeaZhongChouActivity.class));
					}else{
						startActivity(new Intent(getActivity(), Login.class));
					}
					
					// startActivity(new Intent(context, DetailActivity.class));


					break;

					/** 定种茶 */
				case 2:
					startActivity(new Intent(getActivity(),
							TeaDingZhongActivity.class));
					
					
					break;

				
					/** 茶慈善 */
				case 3:
					startActivity(new Intent(getActivity(),
							TeaCiShanActivity.class));
					
					break;

				
					/** 找茶馆 */
				case 4:
					startActivity(new Intent(getActivity(),
							TeaSearchTeaActivity.class));
					break;

				
					/** 茶丽人 */
				case 5:
					Tab1Fragment.this.getActivity().startActivity(
							(new Intent(getActivity(),
									com.liren_app.ui.LiRenMainActivity.class)));
					

					break;

					/** 银元区 */
				case 6:
					Tab1Fragment.this
					.getActivity()
					.startActivity(
							(new Intent(
									getActivity(),
									com.liren_app.ui.YinYuanMainActivity.class)));
					break;

				}

			}
		});

	}
	private CityChoosePopupWindow mPopupWindow;
	private static String mCurrentCity;
	private void showAreaPopupWindow() {
		
		mPopupWindow = new CityChoosePopupWindow(context,
				this,0);

		mPopupWindow.showAsDropDown(rl_top);
//		mUpDownArrow.setBackgroundResource(R.drawable.arrow_down_white);
		Drawable draw1 = getResources().getDrawable(R.drawable.arrow_up_white);
		draw1.setBounds(0, 0, draw1.getMinimumWidth(), draw1.getMinimumHeight());
		actionbar_left.setCompoundDrawables(null, null, draw1, null);

		mPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				Drawable draw2 = getResources().getDrawable(R.drawable.arrow_down_white);
				draw2.setBounds(0, 0, draw2.getMinimumWidth(), draw2.getMinimumHeight());
				actionbar_left.setCompoundDrawables(null, null,draw2, null);

//				mUpDownArrow.setBackgroundResource(R.drawable.arrow_up_white);
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
						if(actionbar_left.getText().toString().trim().equals("定位中...")){
							startLocation();
							return;
						}
						updatePopupWindowData();
					}
				});
			}
		}).start();

	}

	private void updatePopupWindowData() {

		mDistrictsOfCurrentCity = getDistrictsBasedonCityName(getCurrentCity());

		mPopupWindow.getDistrictGridView().setAdapter(
				new DistrictListAdapter(context, mDistrictsOfCurrentCity,
						mChosenDistrictIndex));

		mPopupWindow.getDistrictGridView().setOnItemClickListener(
				mGridViewItemListener);

		mPopupWindow.getCurrentCityTextView().setText(getCurrentCity());

//		actionbar_left.setText(getCurrentCity());
	}
	
	private OnItemClickListener mGridViewItemListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			mChosenDistrictIndex = arg2;
			mCurrentDistrict = mDistrictsOfCurrentCity[arg2];
			actionbar_left.setText(getCurrentCity()
					+" "+mCurrentDistrict);
			
			mPopupWindow.dismiss();
		}
	};
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

		CityUtil cityUtil = new CityUtil(context, city);
		List<String> lists = cityUtil.getItsDistricts();
		String[] districtsOfThisCity = lists.toArray(new String[lists.size()]);
		mDistrictsOfcityMap.put(city, districtsOfThisCity);

		return districtsOfThisCity;
	}
	
	private void setCurrentCity(String mCurrentCity) {
		if (mCurrentCity == null||mCurrentCity.equals("定位中..."))
			mCurrentCity = "深圳市";
		mCurrentCity = mCurrentCity.replace("市", "");
		actionbar_left.setText(mCurrentCity);
		this.mCurrentCity = mCurrentCity;
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
//				int cityVersion = result.optInt("version");
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
				// TODO Auto-generated method stub

			}
		});

		dialog.show();

	}

	private void listGridviewDataInit() {
		// TODO Auto-generated method stub

		Model_Tab1Gridview gridview1 = new Model_Tab1Gridview(
				R.drawable.tea_shop, getString(R.string.tea_shop));
		Model_Tab1Gridview gridview2 = new Model_Tab1Gridview(
				R.drawable.tea_zhongchou, getString(R.string.tea_zhongchou));
		Model_Tab1Gridview gridview3 = new Model_Tab1Gridview(
				R.drawable.tea_search_tea, getString(R.string.tea_search_tea));
		Model_Tab1Gridview gridview4 = new Model_Tab1Gridview(
				R.drawable.tea_liren, getString(R.string.tea_beautiful_men));
		Model_Tab1Gridview gridview5 = new Model_Tab1Gridview(
				R.drawable.tea_dingzhong, getString(R.string.tea_grow_tea));
		//银元
		/*Model_Tab1Gridview gridview6 = new Model_Tab1Gridview(
				R.drawable.tea_yinyuanqu, getString(R.string.tea_coin_space));*/
		Model_Tab1Gridview gridview7 = new Model_Tab1Gridview(
				R.drawable.tea_cishan, getString(R.string.tea_charity));

		mListGridView.add(gridview1);
		mListGridView.add(gridview2);
		mListGridView.add(gridview5);
		mListGridView.add(gridview7);
		mListGridView.add(gridview3);
		mListGridView.add(gridview4);

//		mListGridView.add(gridview6);

	}

	@ViewInject(R.id.tab1_gridview_more_happy_wait_you)
	private GridView mGridViewMoreHappy;

	// private List<E>

	private void gridviewInitMoreHappy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		if (aMapLocation == null) {
			
			stopLocation();// 销毁掉定位
//			startLocation();
		}
		
	}
	
	/**
	 * 销毁定位
	 */
	private void stopLocation() {
		if (aMapLocManager != null) {
			aMapLocManager.removeUpdates(this);
			aMapLocManager.destroy();
		}
		aMapLocManager = null;
	}


	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(AMapLocation location) {
		if (location != null) {
			this.aMapLocation = location;// 判断超时机制
			Double geoLat = location.getLatitude();
			Double geoLng = location.getLongitude();
			String cityCode = "";
			String desc = "";
			Bundle locBundle = location.getExtras();
			if (locBundle != null) {
				cityCode = locBundle.getString("citycode");
				desc = locBundle.getString("desc");
			}
			/*String str = ("定位成功:(" + geoLng + "," + geoLat + ")"
					+ "\n精    度    :" + location.getAccuracy() + "米"
					+ "\n定位方式:" + location.getProvider() + "\n定位时间:"
					+ AMapUtil.convertToTime(location.getTime()) + "\n城市编码:"
					+ cityCode + "\n位置描述:" + desc + "\n省:"
					+ location.getProvince() + "\n市:" + location.getCity()
					+ "\n区(县):" + location.getDistrict() + "\n区域编码:" + location
					.getAdCode());*/
			String str = "";
			String city = location.getCity();
			String district = location.getDistrict();
			str = city+" "+district;
			setCurrentCity(city);
			actionbar_left.setText(str);
			stopLocation();
		}
		
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

		default:
			break;
		}
		
	}
	
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
