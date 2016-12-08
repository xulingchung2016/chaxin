package com.newbrain.chaxin.my.set;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.jsonthread.Constant;
import com.newbrain.xutils.Xutils_HttpUtils;

/**
 * 关于茶信
 * @author zyh
 *
 */
public class about extends BaseActivity {
	private HttpUtils httpUtils;
	private Context context;
	@ViewInject(R.id.wb)
	WebView wb_b; //生产许可证
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		context = this;
		ViewUtils.inject(this);
		actionbarInit() ;
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
	
		httpUtils.send(HttpMethod.GET, Constant.METHOD_about, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				handleNetworkError();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result = arg0.result;
					try {
						JSONObject obj = new JSONObject(result);
						String code = obj.getString("code");
						if(code.equals("1")){
							String content = obj.getString("content");
							WebSettings settings = wb_b.getSettings();
							 /*settings.setUseWideViewPort(true); 
						        settings.setLoadWithOverviewMode(true); */

						        settings.setDefaultTextEncodingName("utf-8");
							wb_b.loadData(content,
							     "text/html; charset=UTF-8", null);
							

//							WebSettings settings = wb_b.getSettings();
//							settings.setJavaScriptEnabled(true);
							/*wb_b.loadUrl(content);
							  //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
							wb_b.setWebViewClient(new WebViewClient(){
						           @Override
						        public boolean shouldOverrideUrlLoading(WebView view, String url) {
						            // TODO Auto-generated method stub
						               //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
						             view.loadUrl(url);
						            return true;
						        }
						       });*/
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
			}
		});
	}
	
	
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
		actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText("关于茶信");

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
