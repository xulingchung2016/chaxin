package com.newbrain.chaxin.my.mycash;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.pay.AliPayFunction;
import com.alipay.sdk.pay.PayResult;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.user.User;
import com.newbrain.utils.SharedPreferencesHelp;
import com.newbrain.xutils.Xutils_HttpUtils;
import com.pay.wx.WxPayUtils;
import com.pay.yl.YLPayUtils;

public class RechargeActivity extends BaseActivity {

	private Context context;
	private HttpUtils httpUtils;
	private int type = 2;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.recharge_activity);

		ViewUtils.inject(this);
		context = this;
		type = getIntent().getIntExtra("type", 2);
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		actionbarInit();
		
		
		
	}
	
	
	@ViewInject(R.id.recharge_zhifubao)
	private ImageView mIvZhifubao;
	
	@ViewInject(R.id.recharge_yinlian)
	private ImageView mIvYinlian;
	@ViewInject(R.id.recharge_weixin)
	private ImageView mWeixin;
	@ViewInject(R.id.et_bank_people)
	private EditText et_je;//金额
	
	private int mCurrentIndex =0;
	
	
	@OnClick({R.id.recharge_linear_zhifubao,R.id.recharge_linear_yinlian,R.id.recharge_linear_weixin,R.id.recharge_go_pay})
	private void onClick_Charge(View v)
	{
		
		switch (v.getId()) {
		case R.id.recharge_linear_zhifubao:
			
			mIvYinlian.setVisibility(View.GONE);
			mWeixin.setVisibility(View.GONE);
			mIvZhifubao.setVisibility(View.VISIBLE);
			
			mCurrentIndex = 0;
			
			break;
		case R.id.recharge_linear_yinlian:
			
			mIvYinlian.setVisibility(View.VISIBLE);
			mIvZhifubao.setVisibility(View.GONE);
			mWeixin.setVisibility(View.GONE);
			mCurrentIndex = 1;
			
			break;
		case R.id.recharge_linear_weixin:
			
			mIvYinlian.setVisibility(View.GONE);
			mIvZhifubao.setVisibility(View.GONE);
			mWeixin.setVisibility(View.VISIBLE);
			mCurrentIndex = 3;
			
			break;
			
		case R.id.recharge_go_pay:
			
//			pay(subject, body, price, OutTradeNo, mHandler);
			
			chongZhi();
			
			
			
			break;
		default:
			break;
		}
		
		
		
		
	}
	
	
	
	
	
	private String account = "0";
	private void chongZhi() {
		account = et_je.getText().toString();
		if(TextUtils.isEmpty(account)){
			showShortToast("请输入要充值金额！");
			return;
		
		}
		CustomProgressDialog.startProgressDialog(context, "正在充值...");
		String token = User.getInstance().getToken();
		String uid = User.getInstance().getId();
		RequestParams params = new RequestParams();
		params.addBodyParameter("users.id", uid);
		params.addBodyParameter("type", type+"");
		params.addBodyParameter("token", token);
		params.addBodyParameter("paymentMode", mCurrentIndex+"");
		params.addBodyParameter("amount", account);
//		params.addBodyParameter("describe", "充茶币");
		String url = Constant.METHOD_userRecharge+"?users.id='"+uid+"'&type='"+type+"'&token='"+token+"'&amount='"+account+"'";
		
		httpUtils.send(HttpMethod.POST, Constant.METHOD_userRecharge, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				CustomProgressDialog.stopProgressDialog();
				handleNetworkError();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				CustomProgressDialog.stopProgressDialog();
				try {
					//{"ordersNo":"112023200810833","message":"提交成功。","code":"1"}
					JSONObject	jsonObjs = new JSONObject(arg0.result);
					String code = jsonObjs.getString("code");
//					String msg = jsonObjs.getString("message");
					if(mCurrentIndex == 0){
						String orderno = jsonObjs.getString("ordersNo");
					pay("茶币充值", "", account, orderno, handler,3);
					}
					else if(mCurrentIndex == 1) {//银联支付
						YLPayUtils.doStartUnionPayPlugin(RechargeActivity.this, "");
						
					}else if(mCurrentIndex == 3){//微信支付
						String result = jsonObjs.toString();
						WxPayUtils.pay(context, result,"3");
					}
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
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
					if(type == 2){
						String oldGold = SharedPreferencesHelp.getString(context, "gold");
						int sum = Integer.parseInt(oldGold)+Integer.parseInt(account);
						SharedPreferencesHelp.SavaString(context, "gold",sum+"");
						
					}
					else{
						String oldGold = SharedPreferencesHelp.getString(context, "cash");
						double sum = Double.parseDouble(oldGold)+Integer.parseInt(account);
						SharedPreferencesHelp.SavaString(context, "cash",sum+"");
					}
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
		};
	};
	};

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
		actionbar_imgbtn_right.setVisibility(View.VISIBLE);
//		actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText(getString(R.string.my_cash_recharge));
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

}
