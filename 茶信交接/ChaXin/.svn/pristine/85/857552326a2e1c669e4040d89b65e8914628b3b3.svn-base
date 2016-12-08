package com.newbrain.baseactivity;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.AliPayFunction;
import com.alipay.sdk.pay.PayResult;
import com.alipay.sdk.pay.SignUtils;
import com.newbrain.application.MyApplication;
import com.newbrain.chaxin.loginandregister.Login;
import com.newbrain.customview.MoreDialog;
import com.newbrain.user.User;
import com.newbrain.utils.LogUtilss;
import com.newbrain.utils.SharedPreferencesHelp;


public class BaseActivity extends Activity {

	private static final String TAG = "BaseActivity";
	

	/******************************** 【Activity LifeCycle For Debug】 *******************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LogUtilss.d(TAG, this.getClass().getSimpleName()
				+ " onCreate() invoked!!");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		initBaseValue();

	}
	

	
	

	@Override
	protected void onStart() {
		LogUtilss.d(TAG, this.getClass().getSimpleName() + " onStart() invoked!!");
		super.onStart();
	}

	@Override
	protected void onRestart() {
		LogUtilss.d(TAG, this.getClass().getSimpleName()
				+ " onRestart() invoked!!");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		LogUtilss.d(TAG, this.getClass().getSimpleName()
				+ " onResume() invoked!!");
		super.onResume();
	}

	@Override
	protected void onPause() {
		LogUtilss.d(TAG, this.getClass().getSimpleName() + " onPause() invoked!!");
		super.onPause();
	}

	@Override
	protected void onStop() {
		LogUtilss.d(TAG, this.getClass().getSimpleName() + " onStop() invoked!!");
		super.onStop();
	}

	@Override
	public void onDestroy() {
		LogUtilss.d(TAG, this.getClass().getSimpleName()
				+ " onDestroy() invoked!!");
		super.onDestroy();

	}

	/*public void recommandToYourFriend(String url, String shareTitle) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, shareTitle + "   " + url);

		Intent itn = Intent.createChooser(intent, "分享");
		startActivity(itn);
	}*/

	/******************************** 【Activity LifeCycle For Debug】 *******************************************/

	protected void showShortToast(int pResId) {
		showShortToast(getString(pResId));
	}

	protected void showLongToast(String pMsg) {
		Toast.makeText(this, pMsg, Toast.LENGTH_LONG).show();
	}
	
	protected void showLongToast(int pResId) {
		
		showLongToast(getString(pResId));
	}

	protected void showShortToast(String pMsg) {
		Toast.makeText(this, pMsg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		// overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	};

	@SuppressLint("NewApi")
	@Override
	public void startActivity(Intent intent, Bundle options) {
		super.startActivity(intent, options);
	};

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
	}

	protected void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}
	
	protected void openActivity(Class<?> pClass) {
		
		Intent intent = new Intent(this, pClass);
		startActivity(intent);
		
	}

	/**
	 * 显示更多弹窗
	 * @param view 点击的控件
	 * @param isShare 是否显示分享
	 */
	public MoreDialog showMoreDialog(View view,boolean isShare){
		int[] lt = new int[2];
		view.getLocationInWindow(lt);
		
		MoreDialog dialog = new MoreDialog(this, lt[0]+view.getWidth(), lt[1],isShare);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		
		return dialog;
	}
	

	
	/**
	 * 通过反射来设置对话框是否要关闭，在表单校验时很管用， 因为在用户填写出错时点确定时默认Dialog会消失， 所以达不到校验的效果
	 * 而mShowing字段就是用来控制是否要消失的，而它在Dialog中是私有变量， 所有只有通过反射去解决此问题
	 * 
	 * @param pDialog
	 * @param pIsClose
	 */
	public void setAlertDialogIsClose(DialogInterface pDialog, Boolean pIsClose) {
		try {
			Field field = pDialog.getClass().getSuperclass()
					.getDeclaredField("mShowing");
			field.setAccessible(true);
			field.set(pDialog, pIsClose);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void hideKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	protected void handleOutmemoryError() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(BaseActivity.this, "内存空间不足！", Toast.LENGTH_SHORT)
						.show();
				// finish();
			}
		});
	}
	private void initBaseValue(){
		String id =  User.getInstance().getId();
		Log.i("id", "idvalues:??"+id);
		if(TextUtils.isEmpty(id)){
			
			getCache();
		}
	}

	private void getCache(){
		User.getInstance().setPhoneNo(SharedPreferencesHelp.getString(this, "phoneNo"));
		User.getInstance().setId(SharedPreferencesHelp.getString(this, "id"));
		User.getInstance().setSex(SharedPreferencesHelp.getString(this, "sex"));
		User.getInstance().setToken(SharedPreferencesHelp.getString(this, "token"));
		User.getInstance().setNickName(SharedPreferencesHelp.getString(this, "nickName"));
		User.getInstance().setImage(SharedPreferencesHelp.getString(this, "image"));
		User.getInstance().setLogin(SharedPreferencesHelp.getBoolean(this, "isLogin"));
	}
	private int network_err_count = 0;

	protected void handleNetworkError() {
		network_err_count++;
		if (network_err_count < 3) {
			Toast.makeText(BaseActivity.this, "网速好像不怎么给力啊！", Toast.LENGTH_SHORT)
					.show();
		} else if (network_err_count < 5) {
			Toast.makeText(BaseActivity.this, "网速真的不给力！", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(BaseActivity.this, "唉，今天的网络怎么这么差劲！",
					Toast.LENGTH_SHORT).show();
		}
	}

	protected void handleMalformError() {
		Toast.makeText(BaseActivity.this, "数据格式错误！", Toast.LENGTH_SHORT).show();
	}

	protected void handleFatalError() {
		Toast.makeText(BaseActivity.this, "发生了一点意外，程序终止！", Toast.LENGTH_SHORT)
				.show();
		finish();
	}

	public void finish() {
		super.finish();
		// overridePendingTransition(R.anim.push_right_in,
		// R.anim.push_right_out);
	}

	public void defaultFinish() {
		super.finish();
	}

	/**
	 * 验证密码长度
	 * 
	 * @return
	 */
	public boolean isCheckPwdSize(String pwd) {
		if (pwd.length() >= 6 && pwd.length() <= 20) {
			return true;
		}
		return false;
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
//		subject = "茶";
//		body = "暂无";
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
				PayTask alipay = new PayTask(BaseActivity.this);
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
	
	public void goOut() {
		User.getInstance().setPhoneNo("");
		User.getInstance().setId("");
		User.getInstance().setSex("2");
		User.getInstance().setNickName("");
		User.getInstance().setImage("");
		User.getInstance().setLogin(false);
		SharedPreferencesHelp.SavaString(this, "phoneNo", "");
		SharedPreferencesHelp.SavaString(this, "id", "");
		SharedPreferencesHelp.SavaString(this, "sex", "2");
		SharedPreferencesHelp.SavaString(this, "nickName", "");
		SharedPreferencesHelp.SavaString(this, "image", "");
		SharedPreferencesHelp.SavaBoolean(this, "isLogin", false);
		SharedPreferencesHelp.SavaString(this, "cash","0.0");
		SharedPreferencesHelp.SavaString(this, "gold","0");
		finish();
		showShortToast("账号已过期,请重新登录。");
		openActivity(Login.class);
		
		
	}
}
