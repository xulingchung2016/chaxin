package com.newbrain.chaxin.wxapi;



import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.user.User;
import com.newbrain.utils.SharedPreferencesHelp;
import com.newbrain.xutils.Xutils_HttpUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler,OnClickListener{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
    private TextView tv_result;
    private ImageButton btn_back;
    private String orderNo="",type="";
    private HttpUtils httpUtils;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        httpUtils = Xutils_HttpUtils.getHttpUtils(this);
        tv_result = (TextView) findViewById(R.id.tv_result);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
    	api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
    	orderNo = SharedPreferencesHelp.getString(this, "wexin_orderno");
    	type = SharedPreferencesHelp.getString(this, "wexin_type");
    	btn_back.setOnClickListener(this);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			String msg ="";
			int code = resp.errCode;
			switch (code) {
			case 0:
				msg = "支付成功";
				CustomProgressDialog.startProgressDialog(this, "正在获取支付结果...");
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						getResult();
					}
				}, 3000);
				break;
			case -1:
				msg = "支付失败";
				tv_result.setText("订单号："+orderNo+"\n支付结果："+msg);
				break;
			case -2:
				msg = "取消支付";
				tv_result.setText("订单号："+orderNo+"\n支付结果："+msg);
				break;

			default:
				break;
			}
			
			/*AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.app_tip);
			builder.setMessage(getString(R.string.pay_result_callback_msg, msg));
			builder.show();*/
		}
	}
	/**
	 * 获取支付结果
	 */
	private void getResult() {
		CustomProgressDialog.startProgressDialog(this, "正在获取支付结果...");
		String token = User.getInstance().getToken();
		String uid = User.getInstance().getId();
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId", uid);
		params.addBodyParameter("token", token);
		params.addBodyParameter("type", type);
		params.addBodyParameter("orderNo", orderNo);
		httpUtils.send(HttpMethod.POST, Constant.METHOD_getOrderState, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				tv_result.setText("订单号："+orderNo+"\n支付结果：查询失败");
				CustomProgressDialog.stopProgressDialog();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				CustomProgressDialog.stopProgressDialog();
				try {
					JSONObject obj = new JSONObject(arg0.result);
					String status = obj.getString("message");
					tv_result.setText("订单号："+orderNo+"\n支付结果："+status);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		finish();
		
	}
}