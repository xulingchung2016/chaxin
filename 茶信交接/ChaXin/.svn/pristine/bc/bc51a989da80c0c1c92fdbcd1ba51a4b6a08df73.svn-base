package com.newbrain.jsonthread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.xutils.Xutils_HttpUtils;

/**
 * @author Administrator
 *
 */


public class HttpJsonString {	
	/**
	 * @param context 上下文
	 * @param baseUrl 基础的url
	 * @param params  url 需要带的参数
	 * @param flag    判断是请求的是哪一个接口
	 * @param httpMode  请求的方式  是post get
	 */
	public static void httpGetJsonString(Context context,RequestParams params,final Handler handler,String baseUrl,final int flag,HttpMethod httpMethod)
	{
		
		HttpUtils httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		
		System.out.println("baseUrl:"+baseUrl);
		
		httpUtils.send(httpMethod, baseUrl, params, new RequestCallBack<String>() {
			
			
			/* (non-Javadoc)
			 * @see com.lidroid.xutils.http.callback.RequestCallBack#onFailure(com.lidroid.xutils.exception.HttpException, java.lang.String)
			 *
			 *请求失败
			 *
			 */
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

				Log.e("tag","---请求失败---"+arg1);
				CustomProgressDialog.stopProgressDialog();
				Message message = handler.obtainMessage(Constant.FLAG_HTTP_ERROR, arg1);

				handler.sendMessage(message);
				
				
			}
			
			
			
			
			/* (non-Javadoc)
			 * @see com.lidroid.xutils.http.callback.RequestCallBack#onSuccess(com.lidroid.xutils.http.ResponseInfo)
			 *
			 *请求成功
			 *
			 */
			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				
				String su = arg0.result;
				
				Log.e("tag","---请求成功---"+su);
				
				Message message ;
				if(su.contains("\"code\":")){
					message = handler.obtainMessage(flag, su);
				}else{
					message = handler.obtainMessage(Constant.FLAG_HTTP_ERROR, su);
				}
				
				handler.sendMessage(message);
				
				
				
			}
		});
		
	}}
