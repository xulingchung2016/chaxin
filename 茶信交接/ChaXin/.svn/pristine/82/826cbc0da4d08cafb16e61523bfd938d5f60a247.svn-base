package com.newbrain.chaxin.teacishan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.newbrain.adapter.MyAdapter_CiShanPublicBenefitListView;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.customview.XListView;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.TeaCiShanListProjectBean;
import com.newbrain.utils.ToastUtil;
import com.newbrain.xutils.Xutils_HttpUtils;
import com.viewpagerindicator.IconPageIndicator;
import com.viewpagerindicator.ImageListAdapter;
import com.viewpagerindicator.MyDirectionalViewPager;

public class TeaCiShanMainFragment extends Fragment{
	
	private TeaCiShanActivity mParentsActivity;
	/**图片url的list*/
	private List<String> mListViewFlow;
	private MyDirectionalViewPager image_list;
	private IconPageIndicator mIconPageIndicator;
	private LinearLayout love_nu_layout;
	private ListView public_benefit_list;
	private MyAdapter_CiShanPublicBenefitListView mAdapter;
	private TeaCiShanListProjectBean mTeaCiShanListProjectBean;
	private boolean mRefreshing = false;
	private boolean mLoadmoreing = false;
	private TextView tv_emptydata;
	private JsonThread mThread;
	private HttpUtils httpUtils;
	
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case Constant.FLAG_GET_CISHAN_LISTPROJECT:
				CustomProgressDialog.stopProgressDialog();
				HttpReturnData mData = (HttpReturnData) msg.obj;
				if(mData.isSuccess()){
					TeaCiShanListProjectBean mBean = (TeaCiShanListProjectBean) mData.getObg();
					if(mBean.getCode().equals("1")){
						if(mTeaCiShanListProjectBean == null  ){
							mTeaCiShanListProjectBean = mBean;
							
							mAdapter = new MyAdapter_CiShanPublicBenefitListView(mParentsActivity, mTeaCiShanListProjectBean.getResult());
							showRecordList();
						}else{
							mTeaCiShanListProjectBean.setCount(mBean.getCount());
							if(mRefreshing){
								mTeaCiShanListProjectBean.setPageNo("1");
								mTeaCiShanListProjectBean.getResult().clear();
								mTeaCiShanListProjectBean.getResult().addAll(mBean.getResult());
							}else if(mLoadmoreing){
								mTeaCiShanListProjectBean.setPageNo(""+Integer.valueOf(mTeaCiShanListProjectBean.getPageNo())+1);
								mTeaCiShanListProjectBean.getResult().addAll(mBean.getResult());
							}
							mAdapter.notifyDataSetChanged();
						}
					}else if(mRefreshing){
						tv_emptydata.setText(R.string.empty_data);
					}
				}else{
					tv_emptydata.setText(R.string.get_data_fail);
				}
				mRefreshing = false;
				mLoadmoreing = false;
				break;
			}
		}

	};
	private ImageListAdapter adapter;

	@Override
	public void onCreate( Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mParentsActivity = (TeaCiShanActivity) getActivity();
		httpUtils=Xutils_HttpUtils.getHttpUtils(mParentsActivity);
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.tea_cishan_main_fragment, container, false);
		mParentsActivity.setTopBar(mParentsActivity.CISHAN_MAIN_PAGE);
		initView(view);
		setListener();
		initData();
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		image_list = (MyDirectionalViewPager) view.findViewById(R.id.tea_cishan_main_image_viewlist);
		mIconPageIndicator = (IconPageIndicator) view.findViewById(R.id.tea_cishan_main_viewflow_indicator);
		love_nu_layout = (LinearLayout) view.findViewById(R.id.love_nu_layout_bg);
		public_benefit_list = (ListView) view.findViewById(R.id.public_benefit_list);
		tv_emptydata = (TextView)view.findViewById(R.id.tv_emptydata);
	}

	private void setListener() {
		// TODO Auto-generated method stub
		public_benefit_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				mParentsActivity.replaceFragment(new TeaCiShanProjectDetailFragment(mTeaCiShanListProjectBean.getResult().get(arg2).getProject_id()), true, false, true);
			}
		});
	}
	private void initData() {
		// TODO Auto-generated method stub
		mListViewFlow = new ArrayList<String>();
		mListViewFlow.add("http://www.tianyutea.com/uploadfiles/others/image/201407/20140708104060566056.jpg");
		mListViewFlow.add("http://www.tianyutea.com/uploadfiles/others/image/201407/20140708104060566056.jpg");
		mListViewFlow.add("http://www.tianyutea.com/uploadfiles/others/image/201407/20140708104060566056.jpg");
		mListViewFlow.add("http://www.tianyutea.com/uploadfiles/others/image/201407/20140708104060566056.jpg");
		adapter = new ImageListAdapter(mParentsActivity, mListViewFlow);
		image_list.setAdapter(adapter);
		final List<Map<String,String>> datas= new ArrayList<Map<String,String>>();
		httpUtils.send(HttpMethod.GET,Constant.BASE_URL+"TeaMall/interf/charity/topImageShow",new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("test","茶慈善首页轮播图出错");
			}
			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				  try {
					JSONObject jsonObject=new JSONObject(arg0.result);
					if(jsonObject.getInt("code")==1){
						JSONArray array=jsonObject.getJSONArray("result");
						datas.clear();
						Map<String,String> map;
						mListViewFlow.clear();
						for (int i = 0; i < array.length(); i++) {
							map=new HashMap<String, String>();
							JSONObject json=(JSONObject) array.get(i);
							map.put("project_id", json.getString("project_id"));
							map.put("title", json.getString("title"));
							map.put("image", json.getString("image"));
							mListViewFlow.add(json.getString("image"));
							datas.add(map);
						}
//						if(mListViewFlow == null||mListViewFlow.size() == 0)return;
						adapter=new ImageListAdapter(mParentsActivity, mListViewFlow);
						image_list.setAdapter(adapter);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		mIconPageIndicator.setViewPager(image_list);
		image_list.startAutoFlowTimer();
		public_benefit_list.setEmptyView(tv_emptydata);
		if(mTeaCiShanListProjectBean == null){
			setLoveNuView("0");
			CustomProgressDialog.startProgressDialog(mParentsActivity);
			startGetCiShanListThread(true);
		}else{
			showRecordList();
		}
		httpUtils.send(HttpMethod.GET, Constant.BASE_URL+"TeaMall/interf/charity/donateCount", new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				ToastUtil.Toast(mParentsActivity, "网络出错");
			}
			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject=new JSONObject(arg0.result);
					if(jsonObject.getInt("code")==1){
						love_nu_layout.removeAllViews();
						setLoveNuView(""+jsonObject.getInt("count"));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	private void showRecordList() {
		// TODO Auto-generated method stub
//		love_nu_layout.removeAllViews();
		String love_nu = mTeaCiShanListProjectBean.getLove_total();
//		setLoveNuView(love_nu);
		public_benefit_list.setAdapter(mAdapter);
	}
	private void setLoveNuView(String love_nu){
		love_nu = TextUtils.isEmpty(love_nu)?"0":love_nu;
		
		for(int i = 0;i < love_nu.length();i++){
			View view = LayoutInflater.from(mParentsActivity).inflate(R.layout.tea_cishan_love_nu, null);
			((TextView)view.findViewById(R.id.nu)).setText(love_nu.substring(i, i+1));
			love_nu_layout.addView(view);
		}
	}
	private void startGetCiShanListThread(boolean isRefresh){
		List<Bean> list = new ArrayList<Bean>();
		mRefreshing = isRefresh;
		if(isRefresh){
			list.add(new Bean("pageNo", "1"));
			list.add(new Bean("pageSize", "10"));
		}else{
			list.add(new Bean("pageNo", String.valueOf(Integer.valueOf(mTeaCiShanListProjectBean.getPageNo())+1)));
			list.add(new Bean("pageSize", "10"));
		}
		if(mThread != null){
			mThread.cancleReturnData();
		}
		mThread = new JsonThread(mParentsActivity, list, mHandler , Constant.FLAG_GET_CISHAN_LISTPROJECT);
		mThread.start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mThread != null){
			mThread.cancleReturnData();
			mThread = null;
		}
	}
	
}
