package com.newbrain.chaxin.teamall;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.lang.Float;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.pay.AliPayFunction;
import com.alipay.sdk.pay.PayResult;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.liren_app.ui.DingDanActivity;
import com.newbrain.application.MyApplication;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.my.mycash.RechargeActivity;
import com.newbrain.chaxin.my.mycash.WithDrawActivity;
import com.newbrain.chaxin.my.order.MyOrder;
import com.newbrain.chaxin.my.set.ReceiveAddressManagerActivity;
import com.newbrain.customview.CustomDialog;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.BaseJsonDataBean;
import com.newbrain.model.Bean;
import com.newbrain.model.Detail_ImageList;
import com.newbrain.model.GetAddressListBean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.SimpleOrder;
import com.newbrain.model.SummitOrderListBean;
import com.newbrain.model.GetAddressListBean.GetAddressList;
import com.newbrain.user.User;
import com.newbrain.utils.SharedPreferencesHelp;
import com.newbrain.xutils.Xutils_BitmapUtils;
import com.newbrain.xutils.Xutils_HttpUtils;
import com.pay.wx.WxPayUtils;
import com.pay.yl.YLPayUtils;

public class ComfirmOrderActivity extends BaseActivity
{
	private Context context;
	private JsonThread mThread;
	
	HttpUtils httpUtils;
	String gooddetailID;
	
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
	Boolean homebred = false;
//	String price = "";
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
	
	double myCash = 0.0;
	private String subOrders="";
//	private String wxzfURl = "http://218.244.138.142:8081/TeaMall/orders/wxPayment";
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.comfirm_order_activity);
		context = this;
		MyApplication.getInstance().addActivity(this);
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		SharedPreferences sp = context.getSharedPreferences("CHAXIN_CONFIG", context.MODE_PRIVATE);
		gooddetailID = sp.getString("goodid_for_detail", "");
//		getGoodDetail(gooddetailID);
		ViewUtils.inject(this);
		actionbarInit();
		getGoods();
		
		NameTemp = SharedPreferencesHelp.getString(context, "name");
		address = SharedPreferencesHelp.getString(context, "address");
		expressId = SharedPreferencesHelp.getString(context, "expressId");

		if(TextUtils.isEmpty(NameTemp)){
			tv_add.setVisibility(View.VISIBLE);
			receice_Namephone.setVisibility(View.GONE);
			receice_DetailAddress.setVisibility(View.GONE);
		}else{
			tv_add.setVisibility(View.GONE);
		receice_Namephone.setText(NameTemp);
		receice_DetailAddress.setText(address);
		receice_Namephone.setVisibility(View.VISIBLE);
		receice_DetailAddress.setVisibility(View.VISIBLE);
		}
	}
	
	private List<SimpleOrder> orders = new ArrayList<SimpleOrder>();
			
			
	private void getGoods() {
		orders = (List<SimpleOrder>) getIntent().getSerializableExtra("datas");
		storeId = getIntent().getStringExtra("stroreId");
		totalprice = getIntent().getDoubleExtra("prices", 0.0);
		should_pay.setText("￥" + totalprice);
		if(orders != null){
			subOrders = JSON.toJSONString(orders, true);
			int size = orders.size();
			for(int i= 0;i<size;i++){
				SimpleOrder order = orders.get(i);
				RelativeLayout view = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.item_orders, null);
				
				ImageView icon = (ImageView) view.findViewById(R.id.goods_listview_item_imageview);
				TextView tv_tea = (TextView) view.findViewById(R.id.goods_listview_item_tea_name);
				TextView tv_price = (TextView) view.findViewById(R.id.goods_listview_item_tea_price);
				TextView tv_num = (TextView) view.findViewById(R.id.confirm_order_num);
				String url = order.getIcon();
				if(!TextUtils.isEmpty(url))Xutils_BitmapUtils.getbitmapUtils_detail(context).display(icon,  url);;
				tv_tea.setText(order.getName());
				tv_price.setText("￥"+order.getPrice());
				tv_num.setText("x  "+order.getNum());
				ll_goods.addView(view);
			}
		}
		
	}

	@ViewInject(R.id.all_actionbar_linear_left)
	private LinearLayout actionbar_ll_left;
	@ViewInject(R.id.tv_add)
	private TextView tv_add;

	@ViewInject(R.id.all_actionbar_textview_back_name)
	private TextView actionbar_tv_back_name_left;

	@ViewInject(R.id.all_actionbar_name)
	private TextView actionbar_tv_name_center;

	@ViewInject(R.id.all_actionbar_button_right_left)
	private Button actionbar_btn_right_left;

	@ViewInject(R.id.all_actionbar_button_right)
	private ImageButton actionbar_imgbtn_right;
	
	@ViewInject(R.id.detail_right_shop_car)
	private Button commit_order;
	
	@ViewInject(R.id.comfirm_order_receice_Namephone)
	private TextView receice_Namephone;
	
	@ViewInject(R.id.comfirm_order_receice_DetailAddress)
	private TextView receice_DetailAddress;
	
	@ViewInject(R.id.goods_detail_shop_name)
	private TextView goodsShopName;
	
	@ViewInject(R.id.goods_listview_item_tea_name)
	private TextView goodsTeaName;
	
	/*@ViewInject(R.id.goods_listview_item_imageview)
	private ImageView goodsTeaImage;*/
	
	@ViewInject(R.id.goods_listview_item_tea_price)
	private TextView goodsTeaprice;
	
	/*@ViewInject(R.id.confirm_order_reduce)
	private Button num_reduce;
	
	@ViewInject(R.id.confirm_order_plus)
	private Button num_plus;
	
	@ViewInject(R.id.confirm_order_num)
	private TextView goodsTeanum;*/
	
	@ViewInject(R.id.ll_goods)
	private LinearLayout ll_goods;
	
	@ViewInject(R.id.tv_cash)//现金
	private TextView tv_cash;
	
	@ViewInject(R.id.comfirm_order_tv_should_pay)
	private TextView should_pay;
	
	@ViewInject(R.id.comfirm_order_receice_address)
	private LinearLayout AddressListPageImage;
	@ViewInject(R.id.check_box)
	private CheckBox ck_xj;
	@ViewInject(R.id.check_box_ylzf)
	private CheckBox ck_ylzf;
	@ViewInject(R.id.comfirm_order_choice_edit)
	private EditText et_msg;
	@ViewInject(R.id.check_box_zfb)
	private CheckBox ck_zfb;
	@ViewInject(R.id.check_box_wxzf)
	private CheckBox ck_wxzf;
	private int zfType = 0;//0为支付宝，1位银联支付，2位现金支付,3为微信支付
	

	private void actionbarInit() 
	{
		// TODO Auto-generated method stub
		actionbar_ll_left.setVisibility(View.VISIBLE);
		actionbar_tv_back_name_left.setVisibility(View.GONE);
		// actionbar_tv_name_center.setVisibility(View.VISIBLE);
		actionbar_btn_right_left.setVisibility(View.GONE);
		actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText(getString(R.string.comfirm_order_comfirm));
		actionbar_btn_right_left.setText(getString(R.string.rule));

		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
		actionbar_imgbtn_right.setOnClickListener(clickListener_actionbar);
		commit_order.setOnClickListener(clickListener_commit_order);
	/*	num_reduce.setOnClickListener(clickListener_num);
		num_plus.setOnClickListener(clickListener_num);*/
		AddressListPageImage.setOnClickListener(clickListener_num);
		ck_xj.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(myCash>=totalprice){
					ck_xj.setChecked(true);
					ck_ylzf.setChecked(false);
					ck_zfb.setChecked(false);
					ck_wxzf.setChecked(false);
					zfType = 2;
					}
					else{
						ck_xj.setChecked(false);
						final CustomDialog customDialog = new CustomDialog(context,true);
						customDialog.setDialogTitle("现金不足");
						customDialog.hideDialogContent();
						customDialog.hideDialogEdittext();
						customDialog.setLeftBtnText("取消");
						customDialog.setRightBtnText("马上充值");
						customDialog.setLeftBtnListener(new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								
								customDialog.dismiss();
							}
						});
						
						customDialog.setRightBtnListener(new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								Intent intent = new Intent(context, RechargeActivity.class);
								intent.putExtra("type", 3);
								startActivity(intent);
								customDialog.dismiss();
								
							}
						});
						
						customDialog.show();
					}
					
				
				
				
			}
		});
		
		
		ck_ylzf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ck_ylzf.setChecked(true);
				ck_xj.setChecked(false);
				ck_zfb.setChecked(false);
				ck_wxzf.setChecked(false);
				zfType = 1;
				
			}
		});
		ck_zfb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
		
				ck_zfb.setChecked(true);
				ck_ylzf.setChecked(false);
				ck_xj.setChecked(false);
				ck_wxzf.setChecked(false);
				zfType = 0;
		
			}
		});
		
		/**
		 * 微信支付
		 */
		ck_wxzf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ck_wxzf.setChecked(true);
				ck_zfb.setChecked(false);
				ck_ylzf.setChecked(false);
				ck_xj.setChecked(false);
				zfType = 3;
				
			}
		});
	}
	
	
	
	private void request_commitOrder()
	{
		if(TextUtils.isEmpty(expressId)){
			showShortToast("请先填写收货地址！");
			return;
		}
		CustomProgressDialog.startProgressDialog(context, "loading...");
		List<Bean> list = new ArrayList<Bean>();
		if(mThread != null)
		{
			mThread.cancleReturnData();
		}
		
		String userID = SharedPreferencesHelp.getString(context, "id");
		String userToken =SharedPreferencesHelp.getString(context, "token");
		
		Log.e("lijinjin", "userID: " + userID + " userToken: " + userToken);
		list.add(new Bean("users.id", userID)); //用户id
		list.add(new Bean("token", userToken)); //密钥
		list.add(new Bean("store.id", storeId)); //密钥
		
//		list.add(new Bean("goods.id", gooddetailID)); //商品id
//		list.add(new Bean("price", price)); //原价
//		list.add(new Bean("primeCost", primeCost)); //现价
//		String num = goodsTeanum.getText().toString();
//		list.add(new Bean("num", num)); //数量
		list.add(new Bean("subOrders", subOrders)); //商品订单
		
		list.add(new Bean("referrerPhone", "18513497802")); //推荐人手机号码
		
		String NameTemp = User.getInstance().getreceive_Namephone();
		String address = User.getInstance().getreceive_DetailAddress();
		
		list.add(new Bean("msg", et_msg.getText().toString().trim())); //说明
		list.add(new Bean("paymentMode", zfType+"")); //支付方式，0：支付宝，1：银联支付，2：现金支付
//		Log.e("lijinjin", "111 num : " + num);
		
//		price = "1.1";
//		float f_price = Float.parseFloat(price);
//		Log.e("lijinjin", "f_price: " + f_price);
//		int iTemp = Integer.parseInt(num);
//		float totalprice = f_price * iTemp;
		Log.e("lijinjin", "totalprice: " + totalprice);
		
		String Temp = ""+ totalprice;
		Log.e("lijinjin", "totalprice Temp: " + Temp);
//		Temp = "0.01";
		list.add(new Bean("amount",Temp)); //支付金额
		list.add(new Bean("gold", "0")); //金币
		list.add(new Bean("express.id", expressId)); //快递地址
		list.add(new Bean("payPassword", password)); //现金支付密码
		list.add(new Bean("stauts", "0")); //订单状态，0：待付款，1：待收货，2：待评价，3：已付款，4：已收货，5：已评价
		list.add(new Bean("idisTimeLimited", isTimeLimited));
		
		mThread = new JsonThread(this, list, mHandler, Constant.FLAG_SUBMMIT_GOODS_ORDER);
		mThread.setType(zfType);
		mThread.start();
	}
	
	public String getOutTradeNo() 
	{
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}
	
	private Handler mHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg)
		{
			
			switch (msg.what) 
			{
			case AliPayFunction.SDK_PAY_FLAG:
			{
				CustomProgressDialog.stopProgressDialog();
				PayResult payResult = new PayResult((String) msg.obj);
				
				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
//				String resultInfo = payResult.getResult();
				
				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) 
				{
					showShortToast("支付成功");
					Log.e("lijinjin", "支付成功");
					finish();
				} 
				else 
				{
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000"))
					{
						showShortToast("有可能支付失败");
						Log.e("lijinjin", "有可能支付失败");
					}
					else 
					{
						showShortToast("支付失败");
						Log.e("lijinjin", "支付失败");
					}
				}
				break;
			}
			case Constant.FLAG_SUBMMIT_GOODS_ORDER:
			{
				Log.e("lijinjin", "提交订单结果");
				if(zfType == 3){
//					{"message":"提交成功","result":{"sign":"7738F2AF62D0A700913339E794DA608C","appId":"wx46c0f861a02329c2","cash":10000.0,"nonceStr":"n64lrjjmu1JRLDUB","mchId":"1298031201","ordersNo":"1228203916-1635","prepayId":"wx20151228203923f87ed437150692495774"},"code":"1"}
					String result = msg.obj.toString();
					try {
						JSONObject obj = new JSONObject(result);
						if(null != obj){
						if(obj.getInt("code")== 1){
							
							WxPayUtils.pay(context, obj.getJSONObject("result").toString(),"1");
							
						}else {
							CustomProgressDialog.stopProgressDialog();
							Toast.makeText(context, "支付失败", 1).show();
						}
						}
						return;
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else if(zfType == 1){//银联支付
					String result = msg.obj.toString();
					try {
						CustomProgressDialog.stopProgressDialog();
						JSONObject obj = new JSONObject(result);
						if(null != obj){
							
						if(obj.getInt("code")== 1){
							YLPayUtils.doStartUnionPayPlugin(ComfirmOrderActivity.this, obj.getJSONObject("result").getString("tn"));
							
						}else {
							CustomProgressDialog.stopProgressDialog();
							Toast.makeText(context, "支付失败", 1).show();
						}
						}
						return;
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				}
				HttpReturnData reData = (HttpReturnData) msg.obj;
				Log.e("lijinjin", "reData: " + reData);
				
				if (reData.isSuccess()) 
				{
					SummitOrderListBean baseJsonDataBean = (SummitOrderListBean) reData.getObg();
					if (baseJsonDataBean.getCode().equals("1"))
					{
						showShortToast("订单提交成功");
						Log.e("lijinjin", "goodID: " + baseJsonDataBean.getResult().getGoodsId());
						if(zfType ==0 ){
						String orderNo = baseJsonDataBean.getResult().getOrdersNo();
						String goodName = baseJsonDataBean.getResult().getGoodsName();
						String orderDetail = "茶信商城";
						String amount = totalprice+"";
						
						//转去支付当前提交的订单
						pay(goodName, orderDetail, amount, orderNo, mHandler,1);
						}else if(zfType == 1){//调用银联支付
							
							YLPayUtils.doStartUnionPayPlugin(ComfirmOrderActivity.this, "");
						}else if(zfType == 3){//微信支付结果
//							String result = baseJsonDataBean.getResult().toString();
//							WxPayUtils.pay(context, "");
						}else{
							CustomProgressDialog.stopProgressDialog();
							double yuer = myCash - totalprice;
							tv_cash.setText("￥"+yuer);
							SharedPreferencesHelp.SavaString(context, "cash",yuer+"" );
							finish();
						}
					}
					else 
					{
						if(baseJsonDataBean.getCode().equals("0")){
							Toast.makeText(context, "账号登录已过期，请重新登录", 1).show();
							
							}
						CustomProgressDialog.stopProgressDialog();
						showShortToast("订单提交失败");
						System.out.println("result:" + baseJsonDataBean.getMessage());
					}
				}
			}
				break;
			case AliPayFunction.SDK_CHECK_FLAG: 
			{
				CustomProgressDialog.stopProgressDialog();
				break;
			}

			default:
				break;
			}
		};
	};
	
	private double totalprice;
	private OnClickListener clickListener_num = new OnClickListener() 
	{
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
//			String num = goodsTeanum.getText().toString();
			switch (v.getId()) 
			{
			/*case R.id.confirm_order_reduce:
			{
				int iTemp = Integer.parseInt(num);
				Log.e("lijinjin", "1 iTemp: " + iTemp);
				 totalprice = f_price * iTemp;
				if(iTemp > 1)
				{
					iTemp--;
					goodsTeanum.setText(""+iTemp);
				}
				should_pay.setText("￥" + totalprice);
				
				break;
			}
			
			case R.id.confirm_order_plus:
			{
				int iTemp = Integer.parseInt(num);
				iTemp++;
				Log.e("lijinjin", "2 iTemp: " + iTemp);
				totalprice = f_price * iTemp;
				goodsTeanum.setText(""+iTemp);
				should_pay.setText("￥" + totalprice);
				
				break;
			}*/
			case R.id.comfirm_order_receice_address:
			{
				startActivity((new Intent(context, ReceiveAddressManagerActivity.class)).putExtra("from", "order"));
				break;
			}
			
			default:
				break;
			}

		}
	};
	
	private OnClickListener clickListener_commit_order = new OnClickListener() 
	{
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			switch (v.getId()) 
			{
			case R.id.detail_right_shop_car:
			{
				showLongToast("提交订单");
				//提交订单
				if(zfType == 2){//现金支付
					showDialog();
				}else 
				request_commitOrder();
//				send();

				break;
			}
			
			default:
				break;
			}

		}
	};
	
//	private void send(){
//		httpUtils.send(HttpMethod.GET, wxzfURl, new RequestCallBack<String>() {
//
//			@Override
//			public void onFailure(HttpException arg0, String arg1) {
//				Log.i("wxzf", "ereror:>>"+arg1+arg0.toString());
//			}
//
//			@Override
//			public void onSuccess(ResponseInfo<String> arg0) {
//				Log.i("wxzf", "result:>>"+arg0.result);
//			}
//		});
//	}
	
	private String password;
	private void showDialog() {
		
		final CustomDialog customDialog = new CustomDialog(context,true);
		
		customDialog.setDialogTitle("请输入支付密码");
		
		customDialog.setDialogContent("初始密码为登录密码");
		customDialog.setLeftBtnText("确认");
		customDialog.setRightBtnText("取消");
		customDialog.setLeftBtnListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String str = ((EditText)customDialog.findViewById(R.id.dialog_content_et2)).getText().toString();
				
				
				if(str == null || str.equals(""))
				{
					Toast.makeText(context, "请输入账号密码", Toast.LENGTH_SHORT).show();
					return;
				}else{
					password = str;
					request_commitOrder();
					customDialog.dismiss();
				}
				
			}
		});
		
		customDialog.setRightBtnListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				customDialog.dismiss();
				
			}
		});
		
		customDialog.show();
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
	String expressId= "",NameTemp,address;
	@Override
	protected void onResume() 
	{
		String cash = SharedPreferencesHelp.getString(context, "cash");
		if(TextUtils.isEmpty(cash))cash="0";
		myCash = Double.parseDouble(cash);
		tv_cash.setText("￥"+myCash);
		super.onResume();
		NameTemp = SharedPreferencesHelp.getString(context, "name");
		address = SharedPreferencesHelp.getString(context, "address");
		expressId = SharedPreferencesHelp.getString(context, "expressId");
		if(TextUtils.isEmpty(NameTemp)){
			tv_add.setVisibility(View.VISIBLE);
			receice_Namephone.setVisibility(View.GONE);
			receice_DetailAddress.setVisibility(View.GONE);
		}else{
			tv_add.setVisibility(View.GONE);
		receice_Namephone.setText(NameTemp);
		receice_DetailAddress.setText(address);
		receice_Namephone.setVisibility(View.VISIBLE);
		receice_DetailAddress.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
        if (data == null) {
            return;
        }

        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            msg = "支付成功！";
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("支付结果通知");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        // builder.setCustomTitle();
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
