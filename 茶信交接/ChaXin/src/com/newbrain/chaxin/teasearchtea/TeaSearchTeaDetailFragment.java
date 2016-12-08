package com.newbrain.chaxin.teasearchtea;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.newbrain.chaxin.R;
import com.newbrain.model.TeaHouseDetailInfoBean.TeaHouseDetailInfo;

@SuppressLint("ValidFragment")
public class TeaSearchTeaDetailFragment extends Fragment {

	private WebView detail_img;
	private TeaHouseDetailInfo mTeaHouseDetailInfo;
	private String appCacheDir;
	
	public TeaSearchTeaDetailFragment(TeaHouseDetailInfo teaHouseDetailInfo){
		mTeaHouseDetailInfo = teaHouseDetailInfo;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.tea_search_teadetail_fragment,container, false);
		appCacheDir = getActivity().getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
		initView(view);
		initData();
		return view;
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("SetJavaScriptEnabled")
	private void initView(View view) {
		// TODO Auto-generated method stub
		detail_img = (WebView) view.findViewById(R.id.tea_detail_img);
		WebSettings webSettings = detail_img.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setDomStorageEnabled(true);
		// ����Ӧ�ó��򻺴�
		webSettings.setAppCacheEnabled(true);
		webSettings.setAppCachePath(appCacheDir);
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
		webSettings.setAppCacheMaxSize(1024 * 1024 * 10);// ���û����С���������10M
		// ���õ���λ
		webSettings.setGeolocationEnabled(true);
		// ���ö�λ�����ݿ�·��
		// webSettings.setGeolocationDatabasePath(databaseDir);
		webSettings.setAllowFileAccess(true);
		
		//在应用内部显示网页
		detail_img.setWebViewClient(new WebViewClient(){
	           @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            // TODO Auto-generated method stub
	               //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
	             view.loadUrl(url);
	            return true;
	        }
	       });
	}

	private void initData() {
		// TODO Auto-generated method stub
		if(mTeaHouseDetailInfo != null){
			detail_img.loadUrl(mTeaHouseDetailInfo.getDetail_url());
//			Xutils_BitmapUtils.getbitmapUtils_detail(getActivity()).display(detail_img, mTeaHouseDetailInfo.getImg());
		}
	}

}
