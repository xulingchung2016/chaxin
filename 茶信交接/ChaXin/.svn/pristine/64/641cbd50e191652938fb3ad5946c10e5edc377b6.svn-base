package com.newbrain.chaxin.teamall;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.application.MyApplication;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.loginandregister.Login;
import com.newbrain.chaxin.message.MessageActivity;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.customview.MoreDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.model.Detail_ImageList;
import com.newbrain.model.SimpleOrder;
import com.newbrain.user.User;
import com.newbrain.utils.SharedPreferencesHelp;
import com.newbrain.xutils.Xutils_BitmapUtils;
import com.newbrain.xutils.Xutils_HttpUtils;

@SuppressLint("SetJavaScriptEnabled") public class DetailActivity extends BaseActivity 
{
	private Context context;
	HttpUtils httpUtils;
	private LayoutInflater mInflater;
	private List<Detail_ImageList> m_GoodsImageList;
	
	String total = "";
	String storesName = "";
	String primeCost = "";
	String memo = "";
	String teaTypeName = "";
	String storageMethod = "";
	String brandName = "";
	String brandId = "";
	String procucingArea = "";
	String id = "";
	String teaTypeId = "";
	String homebred = "1";
	String price = "";
	String packaging = "";
	String salesTotal = "";
	String storeId = "";
	String evalToal = "";
	String goodsName = "";
	String productionCertificate = "";
	String shelfLife = "";
	String goodweight = "";
	String goodImage = "";
	String buyingPrice = "";
	String isTimeLimited = "";
	String timeSales = "";
	String time = "";
	String buyingNum = "";
	String storeAddress = "";
	String goodsScore = "";
	String serviceScore = "";
	String expressScore = "";
	
	String gooddetailID = "";
	String userID = "";
	String usertoken = "";
	
	@ViewInject(R.id.detail_viewflow)
	ImageView goodImageview; //商品主图
	
	@ViewInject(R.id.detail_tea_name)
	TextView detailTealName; //商品主图
	
	@ViewInject(R.id.detail_tea_address)
	TextView detailTealAddress; //商品所在地
	
	@ViewInject(R.id.detail_comment)
	TextView detailTealComment; //商品评论数
	@ViewInject(R.id.rl_comment)
	RelativeLayout rl_comment; //商品评论数
	
	@ViewInject(R.id.goods_listview_item_tea_price)
	TextView detailTeaPrice; //商品单价
	
	@ViewInject(R.id.shop_listview_item_shop_name)
	TextView detailTeaShopName; //商品销量
	
	@ViewInject(R.id.shop_listview_item_shop_address)
	TextView detailTeaShopAddress; //商品地址
	
	@ViewInject(R.id.detail_tea_sale_volume)
	TextView detailTeavolume; //商品销量
	
	@ViewInject(R.id.tea_mall_shop_serviceScore)
	TextView detailShopserviceScore; //服务评分
	
	@ViewInject(R.id.tea_mall_shop_expressScore)
	TextView detailShopexpressScore; //物流务评分
	
	@ViewInject(R.id.tea_mall_shop_goodsScore)
	TextView detailShopgoodsScore; //商品评分
	
	@ViewInject(R.id.detail_viewpager_sort_name)
	TextView BrandName; //品牌
	
	@ViewInject(R.id.detail_viewpager_sort)
	TextView CategoryName; //类别
	
	@ViewInject(R.id.detail_viewpager_in_where)
	TextView ProductArea; //产地
	
	@ViewInject(R.id.detail_viewpager_our_country_or_other_country)
	TextView Import; //是否进口
	
	@ViewInject(R.id.detail_viewpager_shelf_life)
	TextView goodShelfLife; //保质期
	
	@ViewInject(R.id.detail_viewpager_packing)
	TextView packing; //包装
	
	@ViewInject(R.id.detail_viewpager_net_weigth)
	TextView goodWeight; //净重
	
	@ViewInject(R.id.detail_viewpager_storage_method)
	TextView goodstorageMethod; //存储方式
	
	@ViewInject(R.id.detail_viewpager_shengchanxukez)
	TextView License; //生产许可证
	
	@ViewInject(R.id.goods_listview_item_tea_name)
	TextView tv_teaname; //查名
	@ViewInject(R.id.tv_prices)
	TextView tv_prices; //现价
	@ViewInject(R.id.shop_car_num)
	TextView tv_num; //购买数量
	@ViewInject(R.id.shop_car_plus)
	Button btn_plus; //生产许可证
	@ViewInject(R.id.shop_car_reduce)
	Button btn_reduce; //生产许可证
	@ViewInject(R.id.btn_yes)
	Button btn_yes; //确定
	@ViewInject(R.id.btn_cancle)
	Button btn_cancle; //确定
	@ViewInject(R.id.goods_listview_item_imageview)
	ImageView iv_icon; //生产许可证
	@ViewInject(R.id.shop_listview_item_imageview)
	ImageView iv_shop_icon; //生产许
	@ViewInject(R.id.rl_bottom)
	RelativeLayout rl_botoom; //底部弹窗
	
	@ViewInject(R.id.btn_more)
	Button btn_more; //生产许可证
	@ViewInject(R.id.wb)
	WebView wb_b; //生产许可证
	private String qqNo="123456";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_activity);
		MyApplication.getInstance().addActivity(this);
		ViewUtils.inject(this);
		context = this;
		m_GoodsImageList = new ArrayList<Detail_ImageList>();

		mInflater = LayoutInflater.from(context);
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);

		SharedPreferences sp = context.getSharedPreferences("CHAXIN_CONFIG", context.MODE_PRIVATE);
		gooddetailID = sp.getString("goodid_for_detail", "");
		
		Log.e("lijinjin", "detail ID: " + gooddetailID);
		viewPagerInit();
		
		Log.e("lijinjin", "get detail URL: " + Constant.METHOD_USER_GOODSDETAIL+gooddetailID);
		
		httpUtils.send(HttpMethod.GET, Constant.METHOD_USER_GOODSDETAIL+gooddetailID, new RequestCallBack<String>() 
		{
			@Override
			public void onFailure(HttpException arg0, String arg1) 
			{
				// TODO Auto-generated method stub
		
				//showShortToast("获取数据失败");
				Log.e("lijinjin", "获取数据失败");
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) 
			{
				// TODO Auto-generated method stub
			
				//showShortToast(arg0.result);
				Log.e("lijinjin", "获取数据成功");
				String strResult = arg0.result.toString();
				Log.e("lijinjin", "获取数据成功 strResult： " + strResult);
			
				
				JSONObject jsonObjs;
//				{"total":11,"weight":0,"primeCost":"","memo":"强222","teaTypeName":"黄山毛峰","buyingNum":0,"storageMethod":"","brandName":"","brandId":"","id":"5a740a0e511e7bed01511f4eac540001","teaTypeId":"3","homebred":"","time":"","goodsScore":0,"packaging":"","isTimeLimited":0,"salesTotal":"","storeImge":"http:\/\/218.244.138.142:8081\/TeaMall\/upload\/c370ff42fe154511920e13bb7bb93f4c.jpg","evalToal":"","serviceScore":0,"storeName":"上杭八马茶1","buyingPrice":0,"image":"http:\/\/218.244.138.142:8081\/TeaMall\/upload\/7db9af6ff07b4160b2b839312f1e51c9.jpg","procucingArea":"","shelfLife":"","price":10,"address":"","productionCertificate":"","storeId":"5a740a0e50ebd7ab0150ec025add0000","goodsName":"茉莉花茶1","expressScore":0}
				try 
				{
					
					JSONObject json = new JSONObject(strResult);
					String code = json.getString("code");
					if(code.equals("-1"))return;
					jsonObjs = json.getJSONObject("result");
					if(jsonObjs.has("qqNo")){
						qqNo = jsonObjs.getString("qqNo");
					}
					if(jsonObjs.has("total"))
					{
						total = jsonObjs.getString("total");
					}
					
					if(jsonObjs.has("storeName"))
					{
						storesName = jsonObjs.getString("storeName");
						detailTeaShopName.setText(storesName);
					}
					
					if(jsonObjs.has("storeName"))
					{
						storeAddress = jsonObjs.getString("address");
						detailTeaShopAddress.setText("地址：" + storeAddress);
					}
					
					if(jsonObjs.has("goodsScore"))
					{
						goodsScore = jsonObjs.getString("goodsScore");
						detailShopgoodsScore.setText(goodsScore);
					}
					
					if(jsonObjs.has("serviceScore"))
					{
						serviceScore = jsonObjs.getString("serviceScore");
						detailShopserviceScore.setText(serviceScore);
					}
					
					if(jsonObjs.has("expressScore"))
					{
						expressScore = jsonObjs.getString("expressScore");
						detailShopexpressScore.setText(expressScore);
					}
					
					
					if(jsonObjs.has("primeCost"))
					{
						primeCost = jsonObjs.getString("primeCost");
					}
					
					if(jsonObjs.has("memo"))
					{
						memo = jsonObjs.getString("memo");
					}
					
					if(jsonObjs.has("teaTypeName"))
					{
						teaTypeName = jsonObjs.getString("teaTypeName");
					}
					
					if(jsonObjs.has("storageMethod"))
					{
						storageMethod = jsonObjs.getString("storageMethod");
					}
					
					if(jsonObjs.has("brandName"))
					{
						brandName = jsonObjs.getString("brandName");
					}
					
					if(jsonObjs.has("brandId"))
					{
						brandId = jsonObjs.getString("brandId");
					}
					
					if(jsonObjs.has("procucingArea"))
					{
						procucingArea = jsonObjs.getString("procucingArea");
						detailTealAddress.setText(procucingArea);
					}
					
					if(jsonObjs.has("id"))
					{
						id = jsonObjs.getString("id");
					}
					
					if(jsonObjs.has("teaTypeId"))
					{
						teaTypeId = jsonObjs.getString("teaTypeId");
					}
					
					if(jsonObjs.has("homebred"))
					{
						homebred = jsonObjs.getString("homebred");
					}
					
					if(jsonObjs.has("price"))
					{
						price = jsonObjs.getString("price");
						
						detailTeaPrice.setText("￥" + price);
						tv_prices.setText("￥"+price);
					}
					
					if(jsonObjs.has("packaging"))
					{
						packaging = jsonObjs.getString("packaging");
					}
					
					if(jsonObjs.has("salesTotal"))
					{
						salesTotal = jsonObjs.getString("salesTotal");
						if(TextUtils.isEmpty(salesTotal))detailTeavolume.setText("销量：0" );
						else detailTeavolume.setText("销量：" + salesTotal);
					}
					
					if(jsonObjs.has("storeId"))
					{
						storeId = jsonObjs.getString("storeId");
					}
					
					if(jsonObjs.has("evalToal"))
					{
						evalToal = jsonObjs.getString("evalToal");
						if(TextUtils.isEmpty(evalToal))detailTealComment.setText("累计评价(0)");
						else detailTealComment.setText("累计评价(" + evalToal + ")");
					}
					
					if(jsonObjs.has("goodsName"))
					{
						goodsName = jsonObjs.getString("goodsName");
						detailTealName.setText(goodsName);
						tv_teaname.setText(goodsName);
					}
					
					if(jsonObjs.has("productionCertificate"))
					{
						productionCertificate = jsonObjs.getString("productionCertificate");
					}
					
					if(jsonObjs.has("image"))
					{
						goodImage = jsonObjs.getString("image");
						Xutils_BitmapUtils.getbitmapUtils_detail(context).display(goodImageview, goodImage);
						Xutils_BitmapUtils.getbitmapUtils_detail(context).display(iv_icon,  goodImage);
					}
					if(jsonObjs.has("storeImge"))
					{
						String image = jsonObjs.getString("storeImge");
						Xutils_BitmapUtils.getbitmapUtils_detail(context).display(iv_shop_icon, image);
					}
					
					
					if(jsonObjs.has("buyingPrice"))
					{
						buyingPrice = jsonObjs.getString("buyingPrice");
					}
					
					if(jsonObjs.has("isTimeLimited"))
					{
						isTimeLimited = jsonObjs.getString("isTimeLimited");
					}
					
					if(jsonObjs.has("timeSales"))
					{
						timeSales = jsonObjs.getString("timeSales");
					}
					
					if(jsonObjs.has("time"))
					{
						time = jsonObjs.getString("time");
					}
					
					if(jsonObjs.has("buyingNum"))
					{
						buyingNum = jsonObjs.getString("buyingNum");
					}
					
					
					JSONArray jsonObjArray = new JSONObject(strResult).getJSONArray("images");
					Detail_ImageList ImageTemp = null;
					for(int i = 0; i < jsonObjArray.length() ; i++)
					{
						JSONObject jsonObj = ((JSONObject)jsonObjArray.opt(i));
						String sequence = jsonObj.getString("sequence"); 
		                String image = jsonObj.getString("image");
		                Log.e("lijinjin", "sequence: " + sequence + " image: " + image);
		                
		                ImageTemp = new Detail_ImageList();
		                ImageTemp.setSequence(sequence);
		                ImageTemp.setImage(image);
		                m_GoodsImageList.add(ImageTemp);
					}
					
					if(jsonObjArray.length() > 0)
					{
						Comparator comp = new Comparator()
						{
							public int compare(Object o1, Object o2)
							{
								Detail_ImageList a = (Detail_ImageList)o1;
								Detail_ImageList b = (Detail_ImageList)o2;
								int aSequence = Integer.parseInt(a.getSequence());
								int bSequence = Integer.parseInt(b.getSequence());
								if (aSequence < bSequence)
								{
									return -1;
								}
								else if (aSequence == bSequence)
								{
					                 return 0;
								}
					             else if (aSequence > bSequence)
					             {
					            	 return 1;
					             }
								
								return 0;
							}						
						};
						Collections.sort(m_GoodsImageList, comp);
						for(int iLoop = 0; iLoop < m_GoodsImageList.size(); iLoop++)
						{
							Log.e("lijinjin", "iLoop: " + iLoop);
							ImageView img = new ImageView(context);
							detail_pic.addView(img);
							String image = m_GoodsImageList.get(iLoop).getImage();
							Log.e("lijinjin", "image: " + image);
							Xutils_BitmapUtils.getbitmapUtils_detail(context).display(img,  image);
						}
					}
					
					if(null != memo)
					{
						TextView text = new TextView(context);
						text.setText(memo);
						detail_pic.addView(text);
					}
					
					BrandName.setText(brandName);
					CategoryName.setText(teaTypeName);
					ProductArea.setText(procucingArea);
				/*	if(homebred)
					{
						Import.setText("进口");
					}
					else
					{
						Import.setText("国产");
					}
					*/
					goodShelfLife.setText(shelfLife);
					packing.setText(packaging);
					goodWeight.setText(goodweight);
					goodstorageMethod.setText(storageMethod);
					License.setText(productionCertificate);

				} 
				catch (JSONException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				
				LogUtils.e("--->"+arg0.result);
			}
		});
		loadHtml();
		initBottomData();
		//showShortToast(gooddetailID);
	}
	
	private void initBottomData() {
		btn_cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			rl_botoom.setVisibility(View.GONE);	
			}
		});
		
		
		tv_num.setText(nums+"");
		btn_plus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				nums++;
				tv_num.setText(nums+"");
			}
		});
		
		btn_reduce.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(nums>1)nums--;
				else nums= 1;
				tv_num.setText(nums+"");
			}
		});
		btn_yes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			if(type ==1 ){//添加到购物车
				userID = User.getInstance().getId();
				usertoken = User.getInstance().getToken();
				//showShortToast("onClickAddShopCar");
				CustomProgressDialog.startProgressDialog(context, "正在处理数据...");
				RequestParams params;
				params = new RequestParams();
				params.addBodyParameter("userId", userID);
				params.addBodyParameter("goodsId", gooddetailID);
				params.addBodyParameter("goodsName", goodsName);
				params.addBodyParameter("goodsImage", goodImage);
				params.addBodyParameter("primeCost", primeCost);
				params.addBodyParameter("price", price);
				params.addBodyParameter("storeId", storeId);
				params.addBodyParameter("storeName", storesName);
				params.addBodyParameter("num", nums+"");
				params.addBodyParameter("token", usertoken);
				Log.e("lijinjin", "Use token: " + usertoken + "gooddetailID: " + gooddetailID);
				
				addShopCardRequest(httpUtils, params, false);
			}else{
				Log.e("lijinjin", "onClickBuyNow call pay");
				Bundle bundle = new Bundle();
				SimpleOrder order = new SimpleOrder();
				order.setGoodsId(gooddetailID);
				order.setIcon(goodImage);
				order.setName(goodsName);
				order.setNum(nums+"");
				if(TextUtils.isEmpty(price))price = "0.0";
				order.setPrice(price);
				order.setPrimeCost(primeCost);
				List<SimpleOrder> data = new ArrayList<SimpleOrder>();
				data.add(order);
				bundle.putString("stroreId", storeId);
				bundle.putDouble("prices", (nums*Double.parseDouble(price)));
				bundle.putSerializable("datas",(Serializable) data);
				openActivity(ComfirmOrderActivity.class,bundle);
				rl_botoom.setVisibility(View.GONE);
			}
				
			}
		});
		
	}
	private int nums =1;

	private void loadHtml() {
		WebSettings settings = wb_b.getSettings();
		settings.setJavaScriptEnabled(true);
		wb_b.loadUrl(Constant.BASE_URL+"TeaMall/html/goodsIntroduce.jsp?goodsId="+gooddetailID);
		  //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
		wb_b.setWebViewClient(new WebViewClient(){
	           @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            // TODO Auto-generated method stub
	               //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
	             view.loadUrl(url);
	            return true;
	        }
	       });

	}

	@OnClick({R.id.rl_comment,R.id.btn_back})
	private void onClickComment(View v){
		int id = v.getId();
		switch (id) {
		case R.id.rl_comment:
			if(User.getInstance().isLogin())
			{
				Bundle bundle = new Bundle();
				bundle.putInt("type", 0);
			openActivity(CommentActivity.class,bundle);
			}else{
				showShortToast("还没有登录，请登录");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				openActivity(Login.class);
			}
			break;
			
		case R.id.btn_back:
			finish();
			break;

		default:
			break;
		}
	}
	
	private void addShopCardRequest(HttpUtils httpUtils, RequestParams params, final boolean clearGoodListFlag)
	{
		httpUtils.send(HttpMethod.POST, Constant.METHOD_USER_ADD_CART_URL,params, new RequestCallBack<String>()	
		{
			@Override
			public void onFailure(HttpException arg0, String arg1) 
			{
				// TODO Auto-generated method stub
				showShortToast("添加失败！");
				CustomProgressDialog.stopProgressDialog();
				LogUtils.e("--->"+arg0.getMessage());	
			}
			
			@Override
			public void onSuccess(ResponseInfo<String> arg0)
			{
				// TODO Auto-generated method stub
				//showShortToast("获取数据成功");
				String result = arg0.result;
				try {
					String code = new JSONObject(result).getString("code");
					CustomProgressDialog.stopProgressDialog();
					if(code.equals("1")){
					rl_botoom.setVisibility(View.GONE);
					
					showShortToast("已经添加到购物车！");
					}else showShortToast("未成功添加！");
					LogUtils.e("--->"+arg0.result);
				} catch (JSONException e) {
					e.printStackTrace();
				}				
			}			
		});
	}
	
	@OnClick(R.id.detail_right_shop_car)
	private void onClickAddShopCar(View v)
	{
		if(User.getInstance().isLogin())
		{
			rl_botoom.setVisibility(View.VISIBLE);
			type = 1;
			
		}
		else
		{
			showShortToast("还没有登录，请登录");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			openActivity(Login.class);
		}
		
	}
	/**
	 * 跳转到轻轻咨询
	 * @param v
	 */
	@OnClick(R.id.detail_zixun)
	private void onClickZiXun(View v){
		if(TextUtils.isEmpty(qqNo))qqNo = "123456";
		String url11 = "mqqwpa://im/chat?chat_type=wpa&uin="+qqNo+"&version=1";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url11)));
	}
	
	/**
	 * 跳转到购物车
	 * @param v
	 */
	@OnClick(R.id.btn_gwc)
	private void onClickGWC(View v){
		
		SharedPreferencesHelp.SavaBoolean(context, "isGWC", true);
		MyApplication.getInstance().exit();
		
	}
	
	/**
	 * 收藏
	 * @param v
	 */
	@OnClick(R.id.detail_collect)
	private void onClickCollects(View v){
		if(User.getInstance().isLogin()){
		userID = User.getInstance().getId();
		usertoken = User.getInstance().getToken();
		String url = Constant.METHOD_USER_GOOD_Collect_URL;
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId", userID);
		params.addBodyParameter("goods.id", gooddetailID);
		params.addBodyParameter("beauty.id", "");
		params.addBodyParameter("store.id", "");
		params.addBodyParameter("token", usertoken);
		params.addBodyParameter("type", "0");
		/*params.addBodyParameter("userId", "402880ed4ef78c65014ef792c30b0002");
		params.addBodyParameter("goods.id", "19");
		params.addBodyParameter("beauty.id", "");
		params.addBodyParameter("store.id", "");
		params.addBodyParameter("token", "c6e281203d77862ae205a63788de99ea");
		params.addBodyParameter("type", "0");*/
		
//		http://218.244.138.142:8081/TeaMall/collection/saveCollection?goods.id=19&userId=402880ed4ef78c65014ef792c30b0002&token=c6e281203d77862ae205a63788de99ea&type=0
		httpUtils.send(HttpMethod.POST, url,params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				handleNetworkError();
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject	jsonObjs = new JSONObject(arg0.result);
//					String code = jsonObjs.getString("code");
					
					String msg = jsonObjs.getString("message");
					showShortToast(msg);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		});
		}else{
			showShortToast("还没有登录，请登录");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			openActivity(Login.class);
		}
	}
	
	/**
	 * 更多
	 * @param v
	 */
	@OnClick(R.id.btn_more)
	private void onClickMore(View v){
		MyApplication.getInstance().exit();
		/*final MoreDialog dialog =showMoreDialog(btn_more, true);
		dialog.getHome_bg().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				MyApplication.getInstance().exit();

			}
		});
		
		dialog.getMsg_bg().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				openActivity(MessageActivity.class);
				dialog.dismiss();
			}
		});
		
		dialog.getShare_bg().setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View arg0) {
//				recommandToYourFriend(goodImage, "商品详情");
				dialog.dismiss();
			}
		});*/
		
		}
	
	/**
	 * 所有商品
	 * @param v
	 */
	@OnClick(R.id.btn_all_goods)
	private void onClickAllGoods(View v){
		SharedPreferences sp = context.getSharedPreferences("CHAXIN_CONFIG", context.MODE_PRIVATE);
		boolean issp = sp.getBoolean("isShangpu", false);//如果是茶铺列表调整过来的则直接finish，不用再跳装过去了
		if(issp)finish();
		else{
		Editor edit = sp.edit();
		
//		edit.putString("shopName_for_goodsearch", name);
		edit.putString("shopID_for_goodsearch", storeId);
		edit.commit();
		
		openActivity(MyShopSearchGoodsActivity.class);
		}
	}
	/**
	 * 首页
	 * @param v
	 *//*
	@OnClick(R.id.home_bg)
	private void onClickHome(View v){
		finish();
	}
	*//**
	 * 消息
	 * @param v
	 *//*
	@OnClick(R.id.msg_bg)
	private void onClickMsg(View v){
		
		openActivity(MessageActivity.class);
		
	}
	*//**
	 * 分享
	 * @param v
	 *//*
	@OnClick(R.id.share_bg)
	private void onClickShare(View v){
		recommandToYourFriend(Constant.TEAMALL_IMAGE_BASE_URL + goodImage, "商品详情");
		
	}*/
	private int type;//0为立即购买，1为购物车
	@OnClick(R.id.detail_right_now_buy)
	private void onClickBuyNow(View v)
	{
		if(User.getInstance().isLogin())
		{
		//	showShortToast("onClickBuyNow");
			rl_botoom.setVisibility(View.VISIBLE);
			type = 0;
			
		}
		else
		{
			showShortToast("还没有登录，请登录");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			openActivity(Login.class);
		}
		
	}

	@ViewInject(R.id.detail_comment_pic)
	private LinearLayout detail_pic; 
	
	@ViewInject(R.id.detail_comment_text)
	private LinearLayout detail_text;
	
	private List<View> mListVp;

	@ViewInject(R.id.detail_rg_detail)
	private RadioGroup mRgPtDetail;


	@SuppressWarnings("deprecation")
	private void viewPagerInit() 
	{
		mRgPtDetail.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) 
			{
				switch (checkedId) 
				{
				case R.id.detail_picture_text_detail:
					Log.e("lijinjin", "detail_picture_text_detail");
					detail_pic.setVisibility(View.VISIBLE);
					detail_text.setVisibility(View.GONE);

					break;

				case R.id.detail_picture_text_specification_parameter:
					Log.e("lijinjin", "detail_picture_text_specification_parameter");
					detail_pic.setVisibility(View.GONE);
					detail_text.setVisibility(View.VISIBLE);
	
					break;

				default:
					break;
				}

			}
		});

	}
}
