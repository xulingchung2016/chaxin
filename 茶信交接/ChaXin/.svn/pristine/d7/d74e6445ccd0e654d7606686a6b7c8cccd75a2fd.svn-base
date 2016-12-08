package com.pay.wx;

import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.utils.SharedPreferencesHelp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WxPayUtils {
	private static IWXAPI api;
	
	public static void pay(Context context,String content,String type){
		CustomProgressDialog.stopProgressDialog();
		if(!TextUtils.isEmpty(content)){
		if(api == null)
		api = WXAPIFactory.createWXAPI(context, Constant.APP_ID);
		 try{
//			 {"sign":"fff7d391c1ff63e3299e97629ec31c93","timestamp":"1452341897","noncestr":"IsHlkvqBJtmYA8196Hs5Al8y4LJpX8rj","partnerid":"1298031201","prepayid":"wx201601092018162ee193aa540405905943","package":"Sign=WXPay","cash":595.97,"appid":"wx46c0f861a02329c2","ordersNo":"010920181532221"}
//			 "sign":"7738F2AF62D0A700913339E794DA608C","appId":"wx46c0f861a02329c2","cash":10000.0,"nonceStr":"n64lrjjmu1JRLDUB","mchId":"1298031201","ordersNo":"1228203916-1635","prepayId":"wx20151228203923f87ed437150692495774"}
//			 {"sign":"a1dd0c38819bf2610f9040b011d5c241","timestamp":"1451403352","noncestr":"Iv1TcAk53NyvdNa7EBUo712LT8S9W04o","partnerid":"1298031201","prepayid":"wx201512292335526e83e0c44c0134462308","package":"Sign=WXPay","cash":10000,"appid":"wx46c0f861a02329c2","ordersNo":"122923355212624"}
				if (content != null && content.length() > 0) {
					Log.e("get server pay params:",content);
		        	JSONObject json = new JSONObject(content); 
					if(null != json && !json.has("retcode") ){
						PayReq req = new PayReq();
						//req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
						req.appId			= json.getString("appid");
						req.partnerId		= json.getString("partnerid");
						req.prepayId		= json.getString("prepayid");
						req.nonceStr		= json.getString("noncestr");
						req.timeStamp		= json.getString("timestamp");
//						 long seconds = System.currentTimeMillis() / 1000;

//						req.timeStamp		= seconds+"";
//						req.packageValue	= "Sign=WXPay";
						req.packageValue = json.getString("package");
						req.sign			= json.getString("sign");
						req.extData			= "app data"; // optional
						String orderNo = json.getString("ordersNo");
						SharedPreferencesHelp.SavaString(context, "wexin_orderno", orderNo);
						SharedPreferencesHelp.SavaString(context, "wexin_type", type);
						Toast.makeText(context, "正常调起支付", Toast.LENGTH_SHORT).show();
						// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
						api.registerApp(Constant.APP_ID);  
						api.sendReq(req);
					}else{
			        	Log.d("PAY_GET", "返回错误"+json.getString("retmsg"));
			        	Toast.makeText(context, "返回错误"+json.getString("retmsg"), Toast.LENGTH_SHORT).show();
					}
				}else{
		        	Log.d("PAY_GET", "服务器请求错误");
		        	Toast.makeText(context, "服务器请求错误", Toast.LENGTH_SHORT).show();
		        }
			 
			
	        }catch(Exception e){
	        	Log.e("PAY_GET", "异常："+e.getMessage());
	        	Toast.makeText(context, "异常："+e.getMessage(), Toast.LENGTH_SHORT).show();
	        }
	}
	}
	/*<xml>
	   <appid>wx2421b1c4370ec43b</appid>
	   <attach>支付测试</attach>
	   <body>JSAPI支付测试</body>
	   <mch_id>10000100</mch_id>
	   <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>
	   <notify_url>http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php</notify_url>
	   <openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid>
	   <out_trade_no>1415659990</out_trade_no>
	   <spbill_create_ip>14.23.150.211</spbill_create_ip>
	   <total_fee>1</total_fee>
	   <trade_type>JSAPI</trade_type>
	   <sign>0CB01533B8C1EF103065174F50BCA001</sign>
	</xml> 
*/

		

}
