package com.newbrain.chaxin.my.mycollect;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;

@SuppressLint("SetJavaScriptEnabled") 
public class chakanWuliuActivity extends BaseActivity {
	private WebView wb_b;
	private ImageView btn_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_web_view);
		btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
		wb_b = (WebView) findViewById(R.id.wb);
		WebSettings settings = wb_b.getSettings();
		settings.setJavaScriptEnabled(true);
		//自适应屏幕
		wb_b.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		wb_b.getSettings().setLoadWithOverviewMode(true);
		wb_b.loadUrl("http://www.kuaidi100.com/");
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

}
