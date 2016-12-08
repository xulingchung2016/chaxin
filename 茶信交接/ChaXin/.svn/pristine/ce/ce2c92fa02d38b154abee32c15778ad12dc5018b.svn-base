package com.liren_app.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.AliPayFunction;
import com.alipay.sdk.pay.PayResult;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.my.mycash.RechargeActivity;
import com.newbrain.chaxin.my.mycash.WithDrawActivity;
import com.newbrain.chaxin.my.set.ReceiveAddressManagerActivity;
import com.newbrain.customview.CustomDialog;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.SummitOrderListBean;
import com.newbrain.user.User;
import com.newbrain.utils.ButtonColorFilter;
import com.newbrain.utils.SharedPreferencesHelp;
import com.newbrain.xutils.Xutils_HttpUtils;
import com.pay.wx.WxPayUtils;
import com.pay.yl.YLPayUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DingDanActivity extends BaseActivity {

	private JSONObject mCurrentLiren;

	private LinearLayout lay_xiadan;

	private ImageButton lay_zhifubao;
	private ImageButton lay_yinlian;
	private ImageButton lay_bank;
	private ImageButton lay_weixin;

	private ImageButton img_seleted_yinlian;
	private ImageButton img_seleted_zhifubao;
	private ImageButton img_seleted_bank;
	private ImageButton img_seleted_weixin;
	/*private ImageButton btn_xianjing_boolean;
	private boolean btn_xianjing_boolean_state = true;
	private ImageButton btn_chabi_boolean;
	private boolean btn_chabi_boolean_state = true;*/

	private ImageButton btn_back;
	private TextView txt_liren_name;
	private TextView txt_memo;
	private TextView tv_price;
	private TextView tv_time;
	private TextView tv_zprice;
	private TextView xia_dan_ren;
	private TextView tv_address;
	private TextView tv_tip;
	private TextView tv_cash;//现金余额
	private EditText et_msg;
	private ImageButton btn_save;
	private String date ,time;
	private HttpUtils httputils;
	private Context context;
	private int payType = 0;//0为支付宝，1位银联支付，2位现金支付,3为微信支付

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ding_dan);
		context = this;
		try {
			String json = getIntent().getExtras().getString("liren");

			mCurrentLiren = new JSONObject(json);
		} catch (JSONException e) {
			mCurrentLiren = null;
		}
		date = getIntent().getStringExtra("date");
		time = getIntent().getStringExtra("time");
		httputils = Xutils_HttpUtils.getHttpUtils(this);
		initUI();
		initData();
		initControlListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_ding_dan, menu);
		return true;
	}
	
	private String NameTemp,address;
	@Override
	protected void onResume() {
		NameTemp = SharedPreferencesHelp.getString(context, "name");
		address = SharedPreferencesHelp.getString(context, "address");
		expressId = SharedPreferencesHelp.getString(context, "expressId");
		if(!TextUtils.isEmpty(NameTemp)){
			tv_address.setText(address);
			xia_dan_ren.setText(NameTemp);
			tv_tip.setVisibility(View.GONE);
		}
		String cash = SharedPreferencesHelp.getString(context, "cash");
		if(TextUtils.isEmpty(cash))cash="0";
		myCash = Double.parseDouble(cash);
		tv_cash.setText("￥"+myCash);
		
		super.onResume();
	}

	@Override
	protected void initUI() {
		
		lay_xiadan = (LinearLayout) findViewById(R.id.lay_xiadan);
		img_seleted_yinlian = (ImageButton) findViewById(R.id.img_seleted_yinlian);
		img_seleted_zhifubao = (ImageButton) findViewById(R.id.img_seleted_zhifubao);
		img_seleted_bank = (ImageButton) findViewById(R.id.img_seleted_bank);
		img_seleted_weixin = (ImageButton) findViewById(R.id.img_seleted_weixin);

		btn_back = (ImageButton) findViewById(R.id.btn_back);
		txt_liren_name = (TextView) findViewById(R.id.txt_liren_name);
		txt_memo = (TextView) findViewById(R.id.txt_memo);
		tv_price = (TextView) findViewById(R.id.txt_num);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_zprice = (TextView) findViewById(R.id.price);
		xia_dan_ren = (TextView) findViewById(R.id.xia_dan_ren);
		tv_address = (TextView) findViewById(R.id.tv_address);
		tv_tip = (TextView) findViewById(R.id.tv_tip);
		tv_cash = (TextView) findViewById(R.id.tv_cash);
		et_msg = (EditText) findViewById(R.id.et_liuyan);
		
		
		btn_save = (ImageButton) findViewById(R.id.btn_save);
		ButtonColorFilter.setButtonFocusChanged(btn_save);

//		btn_xianjing_boolean = (ImageButton) findViewById(R.id.btn_xianjing_boolean);
//		btn_chabi_boolean = (ImageButton) findViewById(R.id.btn_chabi_boolean);
		
	
		
		
		
	}
	private double myCash,totalPrice;

	@Override
	protected void initControlListener() {
		lay_xiadan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity((new Intent(context, ReceiveAddressManagerActivity.class)).putExtra("from", "order"));
			}
		});

		lay_zhifubao = (ImageButton) findViewById(R.id.img_seleted_zhifubao);
		lay_yinlian = (ImageButton) findViewById(R.id.img_seleted_yinlian);//现金支付
		lay_bank = (ImageButton) findViewById(R.id.img_seleted_bank);//银联支付
		lay_weixin = (ImageButton) findViewById(R.id.img_seleted_weixin);//微信支付

		lay_zhifubao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				payType = 0;
				img_seleted_zhifubao.setBackgroundResource(R.drawable.check_box_checked_square);
				img_seleted_yinlian.setBackgroundResource(R.drawable.check_box_normal_square);
				img_seleted_bank.setBackgroundResource(R.drawable.check_box_normal_square);
				img_seleted_weixin.setBackgroundResource(R.drawable.check_box_normal_square);
//				img_seleted_yinlian.setVisibility(View.INVISIBLE);
//				img_seleted_zhifubao.setVisibility(View.VISIBLE);

			}
		});
		
		lay_bank.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				payType = 1;
				img_seleted_zhifubao.setBackgroundResource(R.drawable.check_box_normal_square);
				img_seleted_yinlian.setBackgroundResource(R.drawable.check_box_normal_square);
				img_seleted_bank.setBackgroundResource(R.drawable.check_box_checked_square);
				img_seleted_weixin.setBackgroundResource(R.drawable.check_box_normal_square);
//				img_seleted_yinlian.setVisibility(View.INVISIBLE);
//				img_seleted_zhifubao.setVisibility(View.VISIBLE);

			}
		});
		lay_weixin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				payType = 3;
				img_seleted_zhifubao.setBackgroundResource(R.drawable.check_box_normal_square);
				img_seleted_yinlian.setBackgroundResource(R.drawable.check_box_normal_square);
				img_seleted_bank.setBackgroundResource(R.drawable.check_box_normal_square);
				img_seleted_weixin.setBackgroundResource(R.drawable.check_box_checked_square);
//				img_seleted_yinlian.setVisibility(View.INVISIBLE);
//				img_seleted_zhifubao.setVisibility(View.VISIBLE);

			}
		});
		
		lay_yinlian.setOnClickListener(new OnClickListener() {//现金

			@Override
			public void onClick(View v) {
				if(myCash>=totalPrice){
				payType = 2;
				
				img_seleted_zhifubao.setBackgroundResource(R.drawable.check_box_normal_square);
				img_seleted_yinlian.setBackgroundResource(R.drawable.check_box_checked_square);
				img_seleted_bank.setBackgroundResource(R.drawable.check_box_normal_square);
				img_seleted_weixin.setBackgroundResource(R.drawable.check_box_normal_square);
				}else{
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
//				img_seleted_yinlian.setVisibility(View.VISIBLE);
//				img_seleted_zhifubao.setVisibility(View.INVISIBLE);
			}
		});

		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DingDanActivity.this.finish();
			}
		});
		/*btn_xianjing_boolean.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (btn_xianjing_boolean_state) {
					btn_xianjing_boolean_state = false;
					btn_xianjing_boolean
							.setBackgroundResource(R.drawable.yy_off);
				} else {
					btn_xianjing_boolean_state = true;
					btn_xianjing_boolean
							.setBackgroundResource(R.drawable.yy_open);
				}
			}
		});
		btn_chabi_boolean.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (btn_chabi_boolean_state) {
					btn_chabi_boolean_state = false;
					btn_chabi_boolean.setBackgroundResource(R.drawable.yy_off);
				} else {
					btn_chabi_boolean_state = true;
					btn_chabi_boolean.setBackgroundResource(R.drawable.yy_open);
				}
			}
		});*/
		/**
		 * 提交订单
		 */
		btn_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(TextUtils.isEmpty(expressId)){
					Toast.makeText(DingDanActivity.this, "请选择地址", Toast.LENGTH_SHORT).show();
				return;	
				}
				if(payType == 2){
					showDialog();
					return;
				}
				submitOrder();
				
			}
		});
		
	}
	
	private void showDialog() {
		
		final CustomDialog customDialog = new CustomDialog(context,true);
		
		customDialog.setDialogTitle("请输入支付密码");
		
		customDialog.setDialogContent("初始密码为登录密码");
		customDialog.setLeftBtnText("确认");
		customDialog.setRightBtnText("取消");
		customDialog.setLeftBtnListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EditText et = ((EditText)customDialog.findViewById(R.id.dialog_content_et2));
//						et.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
				String str = et.getText().toString().trim();
				
				if(str == null || str.equals(""))
				{
					Toast.makeText(DingDanActivity.this, "请输入账号密码", Toast.LENGTH_SHORT).show();
					return;
				}else{
					password = str;
					submitOrder();
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

	private String expressId ="";
	private String userID,usertoken,password="";
	protected void submitOrder() {
		if(TextUtils.isEmpty(expressId)){
			showShortToast("请先填写收货地址！");
			return;
		}
		CustomProgressDialog.startProgressDialog(context, "loading...");
		userID = User.getInstance().getId();
		usertoken = User.getInstance().getToken();
		 userID = SharedPreferencesHelp.getString(context, "id");
		 usertoken =SharedPreferencesHelp.getString(context, "token");
		String url = Constant.METHOD_submit_order;
		RequestParams params = new RequestParams();
		params.addBodyParameter("users.id", userID);
		params.addBodyParameter("express.id",expressId );//地址ID
		try {
		params.addBodyParameter("teaBeauty.id", mCurrentLiren.getString("id"));
		params.addBodyParameter("token", usertoken);
		params.addBodyParameter("msg", et_msg.getText().toString().trim());
		params.addBodyParameter("paymentMode", payType+"");
		params.addBodyParameter("amount", totalPrice+"");
		params.addBodyParameter("gold", "0");
		if(!TextUtils.isEmpty(date)){
		params.addBodyParameter("bookDate", date);
		params.addBodyParameter("bookTime",time);
		params.addBodyParameter("payPassword",password);
		}
		} catch (JSONException e1) {
			params.addBodyParameter("teaBeauty.id","1");
		}
		
		httputils.send(HttpMethod.POST, url, params,new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(DingDanActivity.this, "网络不给力！", Toast.LENGTH_SHORT).show();
				CustomProgressDialog.stopProgressDialog();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					
					JSONObject	jsonObjs = new JSONObject(arg0.result);
					String code = jsonObjs.getString("code");
					String msg = jsonObjs.getString("message");
					Toast.makeText(DingDanActivity.this, msg, 1).show();
					
					if(code.equals("1")){
						if(payType == 0){
							String orderNo = jsonObjs.getString("ordersNo");
							String goodName ="茶丽人";
							String orderDetail = "茶信商城";
							String amount = totalPrice+"";
							
							//转去支付当前提交的订单
							pay(goodName, orderDetail, amount, orderNo, mHandler,2);
						}else if(payType == 1){//银联支付
							YLPayUtils.doStartUnionPayPlugin(DingDanActivity.this, "");
						}else if(payType == 3){//微信支付
							String result = jsonObjs.toString();
							WxPayUtils.pay(context, result,"2");
						}else {
							CustomProgressDialog.stopProgressDialog();
					finish();
						}
					}else{
						CustomProgressDialog.stopProgressDialog();
					}
					
				} catch (JSONException e) {
					CustomProgressDialog.stopProgressDialog();
				}
				
			}
		});
		
	}
	
	private Handler mHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg)
		{
			CustomProgressDialog.stopProgressDialog();
			switch (msg.what) 
			{
			case AliPayFunction.SDK_PAY_FLAG:
			{
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
			
			case AliPayFunction.SDK_CHECK_FLAG: 
			{
				break;
			}

			default:
				break;
			}
		}

		
	};
	
	private void showShortToast(String string) {
		Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
		
	};

	@Override
	protected void initBoardCastListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initData() {
		try {
			if(mCurrentLiren !=null){
				String price = mCurrentLiren.getString("price");
				if(TextUtils.isEmpty(price))price = "0.0";
				totalPrice = Double.parseDouble(price);
			txt_liren_name.setText(mCurrentLiren.getString("name"));
			txt_memo.setText(mCurrentLiren.getString("memo"));
			tv_price.setText("￥"+price);
			tv_zprice.setText("￥"+price);
			tv_time.setText(date+" "+time);
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param subject 商品名称
	 * @param body 商品详情
	 * @param price 订单价格
	 * @param OutTradeNo 订单号码
	 * @param mHandler 用于对支付返回的结果进行处理  com.alipay.sdk.pay.AliPayFunction的private Handler mHandler = new Handler()处理
	 * 订单类型 1位商品支付2位丽人支付。3为充值支付
	 */
	public void pay(String subject, String body, String price, String OutTradeNo,final Handler mHandler,int type) 
	{
		Log.e("lijinjin", "subject: " + subject + " body: " + body + " price: " + price + " OutTradeNo: " + OutTradeNo);
		if(TextUtils.isEmpty(subject))subject = "茶";
		if(TextUtils.isEmpty(body))body = "暂无";
		if(TextUtils.isEmpty(price))price = "0.01";
		subject = "茶";
		body = "暂无";
//		price = "0.01";
		// 订单
		String orderInfo = AliPayFunction.getOrderInfo(subject, body, price, OutTradeNo,type);

		// 对订单做RSA 签名
		String sign = AliPayFunction.sign(orderInfo);
		try 
		{
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} 
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + AliPayFunction.getSignType();

		Runnable payRunnable = new Runnable()
		{
			@Override
			public void run()
			{
				// 构造PayTask 对象
				PayTask alipay = new PayTask(DingDanActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = AliPayFunction.SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
		
}
