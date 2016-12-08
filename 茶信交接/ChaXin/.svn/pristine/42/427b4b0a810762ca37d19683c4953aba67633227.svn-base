package com.newbrain.chaxin.teamall;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.adapter.Model;
import com.newbrain.adapter.MyBaseAdapter_MyShopSearchGoodsList;
import com.newbrain.adapter.MyBaseAdapter_TeaMallGoodsList;
import com.newbrain.adapter.MyBaseAdapter_TeaMallShopsList;
import com.newbrain.application.MyApplication;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.GoodsListBean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.ShopsListBean;
import com.newbrain.model.GoodsListBean.goodlist;
import com.newbrain.model.ShopsListBean.shoplist;
import com.newbrain.viewflow.PullToRefreshListView;
import com.newbrain.xutils.Xutils_HttpUtils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.AbsListView.OnScrollListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MyShopSearchGoodsActivity extends BaseActivity
{
	private List<goodlist> mGoodList;
	private MyBaseAdapter_MyShopSearchGoodsList myMyShopSearchGoodAdapter;
	
	private JsonThread mThread;
	
	@ViewInject(R.id.TeaMallShopsSearch_listview)
	private PullToRefreshListView mLvMyShopSearchGoods;
	
	@ViewInject(R.id.myshopsearch_radiogroup_choice)
	private RadioGroup mRgGoods;

	@ViewInject(R.id.myshopsearch_edittext_search)
	private EditText et_search;
		
	/**价格升序降序*/
	@ViewInject(R.id.myshopsearch_price)
	private RadioButton mRbPrice;
	
	/**价格 第一次升序降序不变   再点一次才变*/
	private int mRbPriceCount;
	
	private Context context;
	private HttpUtils httpUtils;
//	private String currentshopName;
	private String currentshopID;
	private  int currentGoodPage = 0;
	List<Bean> requestgoodlist = new ArrayList<Bean>();
	
	private Handler mHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg)
		{
			CustomProgressDialog.stopProgressDialog();
			switch (msg.what) 
			{
			case Constant.FLAG_GET_GET_SHOPS_GOODS_LIST:
			{
				HttpReturnData receiveData = (HttpReturnData) msg.obj;
				if (receiveData.isSuccess()) 
				{
					GoodsListBean  baseJsonDataBean = (GoodsListBean)receiveData.getObg();
					if (baseJsonDataBean.getCode().equals("1"))
					{
						List <goodlist> result = baseJsonDataBean.getResult();
						int iSize = result.size();
						if(currentGoodPage == 1)mGoodList.clear();
						for(int iLoop = 0; iLoop < iSize; iLoop++)
						{
							mGoodList.add(result.get(iLoop));
						}
						
						/*if(iSize > 0)
						{*/
							myMyShopSearchGoodAdapter.notifyDataSetChanged();
//						}
					}else{
						showShortToast("未搜索到相关数据！");
						mGoodList.clear();
						myMyShopSearchGoodAdapter.notifyDataSetChanged();
					}
				}else{	
					showShortToast("数据获取异常！");
				}
			}
			break;
						
			default:
				break;
			}
		}
	};
	
	public void getgoodList(List <Bean> list)
	{
		if(mThread != null)
		{
			mThread.cancleReturnData();
		}

		mThread = new JsonThread(context, list, mHandler, Constant.FLAG_GET_GET_SHOPS_GOODS_LIST);
		mThread.start();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_shop_search_activity);
		ViewUtils.inject(this);
		MyApplication.getInstance().addActivity(this);
		context = this;	
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		SharedPreferences sp = context.getSharedPreferences("CHAXIN_CONFIG", context.MODE_PRIVATE);
//		String shopName = sp.getString("shopName_for_goodsearch", "");
		String shopID = sp.getString("shopID_for_goodsearch", "");
//		currentshopName = shopName;
		currentshopID = shopID;
//		Log.e("lijinjin", "shopName: " + currentshopName);
		Log.e("lijinjin", "shopID: " + currentshopID);
		RadioGroupInit();	
		listviewInit();
		etSearchInit();
		currentGoodPage = 1;
		getGoodData("");
	}
	private int select_type = 0;
	private final String PAGESIZE = "10";
	private void getGoodData(String name){
		CustomProgressDialog.startProgressDialog(context, "loading...");
		requestgoodlist.clear();
		name = et_search.getText().toString().trim();
//		currentGoodPage++;
		if(!TextUtils.isEmpty(name)){
			currentGoodPage = 1;
			requestgoodlist.add(new Bean("name", name));
			requestgoodlist.add(new Bean("pageNo", currentGoodPage+""));
			requestgoodlist.add(new Bean("pageSize", PAGESIZE));
			requestgoodlist.add(new Bean("storeId", currentshopID));
		}else{
		String pageNo = "" + currentGoodPage;
		requestgoodlist.add(new Bean("pageNo", pageNo));
		requestgoodlist.add(new Bean("pageSize", PAGESIZE));
		requestgoodlist.add(new Bean("storeId", currentshopID));
		if(!TextUtils.isEmpty(name))requestgoodlist.add(new Bean("goodsName", name));
		if(select_type == 0)requestgoodlist.add(new Bean("allSort", "0"));//综合
		else if(select_type == 1)requestgoodlist.add(new Bean("stockSort", "0"));//销量
		else if(select_type == 2)requestgoodlist.add(new Bean("priceSort", mRbPriceCount+""));//价格
		else if(select_type == 3)requestgoodlist.add(new Bean("evaSort", "0"));//评论
		}
		getgoodList(requestgoodlist);
	};
		
	/**分类*/
	@ViewInject(R.id.myshopsearch_linear_tea_sort)
	private LinearLayout mLlSort;
	
	@ViewInject(R.id.myshopsearch_expandablelistview)
	private ExpandableListView mExpanListview;
	
	@OnClick({R.id.myshopsearch_mall_back})
	private void  onClick_myShop(View view)
	{	
		switch (view.getId())
		{
		case R.id.myshopsearch_mall_back:
			finish();

			break;

		default:
			break;
		}
	}

	private void etSearchInit()
	{
		et_search.setOnKeyListener(new OnKeyListener()
		{
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) 
			{
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_ENTER) 
				{
					LogUtils.e("-------->按下");
					String name = et_search.getText().toString().trim();
					if(!TextUtils.isEmpty(name)){
						/*SharedPreferences sp = context.getSharedPreferences("CHAXIN_CONFIG", context.MODE_PRIVATE);
						Editor edit = sp.edit();
						edit.putString("shopName_for_goodsearch", name);
						edit.putString("shopID_for_goodsearch", "");
						edit.commit();
						c
						openActivity(MyShopSearchGoodsActivity.class);*/
						currentGoodPage = 1;
						getGoodData(name);
					}
					return true;
				}
				return false;
			}
		});
		
		et_search.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				if(arg0.toString().equals("")){
					currentGoodPage = 1;
					getGoodData("");
				}
					
					
				
			}
		});
	}

	private void RadioGroupInit()
	{
		// TODO Auto-generated method stub
		mRgGoods.setOnCheckedChangeListener(onCheckedChangeListener);
		//mRgShop.setOnCheckedChangeListener(onCheckedChangeListener);
		
		mRbPrice.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				if(mRbPriceCount == 0)
				{
					mRbPriceCount =1;
				}else mRbPriceCount = 0;
				
				LogUtils.w("---------->前"+mRbPrice.isSelected());
				
				mRbPrice.setSelected(!mRbPrice.isSelected());
				getGoodData("");
				LogUtils.w("---------->后"+mRbPrice.isSelected());
			}
		});
	}

	private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener()
	{
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) 
		{
			currentGoodPage = 1;
			// TODO Auto-generated method stub
			switch (checkedId) 
			{
			//综合
			case R.id.myshopsearch_comprehensive:
				select_type = 0;
				getGoodData("");
				break;
			case R.id.myshopsearch_sales_volume:
				select_type = 1;
				getGoodData("");
				break;
			case R.id.myshopsearch_price:
				select_type = 2;
				break;				
			//新品
			case R.id.myshopsearch_comment:
				select_type = 3;
				getGoodData("");
				break;
			default:
				break;
			}
		}
	};
	
	
	private void listviewInit() 
	{
		//final PullToRefreshListView mLvTeaMallGoods = (PullToRefreshListView)findViewById(R.id.TeaMallGoods_listview);
		mGoodList = new ArrayList<goodlist>();

		myMyShopSearchGoodAdapter = new MyBaseAdapter_MyShopSearchGoodsList(context, mGoodList);

		mLvMyShopSearchGoods.setAdapter(myMyShopSearchGoodAdapter);
				
		mLvMyShopSearchGoods.setOnScrollListener(new OnScrollListener()
		{
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) 
			{
				// TODO Auto-generated method stub				
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{
				// TODO Auto-generated method stub
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
				{  
					// 判断是否滚动到底部  
					if (view.getLastVisiblePosition() == view.getCount() - 1)
					{
						//加载更多功能的代码 						
						currentGoodPage++;
						getGoodData("");
					}					
				}				
			}});
	}
}
