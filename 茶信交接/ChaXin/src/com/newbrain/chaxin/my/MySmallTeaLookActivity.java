package com.newbrain.chaxin.my;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;

@ContentView(R.layout.activity_my_small_tea_look)
public class MySmallTeaLookActivity extends BaseActivity implements
		OnClickListener {
	@ViewInject(R.id.webview)
	private WebView webView;
	@ViewInject(R.id.all_actionbar_linear_left)
	private LinearLayout actionbar_ll_left;

	@ViewInject(R.id.all_actionbar_name)
	private TextView actionbar_tv_name_center;

	@ViewInject(R.id.all_actionbar_button_right)
	private ImageButton actionbar_imgbtn_right;
	
	private String desc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		actionbar_tv_name_center.setText("查看茶苗");
		actionbar_ll_left.setOnClickListener(this);
		actionbar_imgbtn_right.setVisibility(View.GONE);
		desc = getIntent().getStringExtra("desc");
		
		WebSettings webSettings = webView.getSettings();  
        //设置WebView属性，能够执行Javascript脚本    
        webSettings.setJavaScriptEnabled(true);    
        //设置可以访问文件  
        webSettings.setAllowFileAccess(true);  
        
        //加载需要显示的网页    
        webView.loadUrl(desc);
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.all_actionbar_linear_left:
			finish();
			break;

		default:
			break;
		}
	}

}
