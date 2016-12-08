package com.newbrain.chaxin.teamall;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
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
import com.newbrain.model.GoodsListBean.goodlist;
import com.newbrain.model.ShopsListBean;
import com.newbrain.model.ShopsListBean.shoplist;
import com.newbrain.swipeRefresh.SwipyRefreshLayout;
import com.newbrain.swipeRefresh.SwipyRefreshLayout.OnRefreshListener;
import com.newbrain.swipeRefresh.SwipyRefreshLayoutDirection;
import com.newbrain.viewflow.PullToRefreshListView;
//import com.newbrain.viewflow.PullToRefreshListView.OnRefreshListener;

public class TeaMallActivity extends BaseActivity 
{
	private Context context;

	@ViewInject(R.id.TeaMallGoods_listview)
	private ListView mLvTeaMallGoods;
	
	@ViewInject(R.id.swipyrefreshlayout)
	private SwipyRefreshLayout swipyRefresh;
	
	@ViewInject(R.id.TeaMallShops_listview)
	private PullToRefreshListView mLvTeaMallShops;
	
	private JsonThread mThread;
	
	private MyBaseAdapter_TeaMallGoodsList myGoodAdapter;
	private MyBaseAdapter_TeaMallShopsList myShopAdapter;

	private  List<goodlist> mGoodList;
	private  List<shoplist> mShopList;
	
	private  int currentGoodPage = 0;
	private String PAGESIZE = "10";
	List<Bean> requestgoodlist = new ArrayList<Bean>();
	private  int currentShopPage = 0;
	List<Bean> requestshoplist = new ArrayList<Bean>();
	
	private Handler mHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg)
		{
			CustomProgressDialog.stopProgressDialog();
			swipyRefresh.setRefreshing(false);
			switch (msg.what) 
			{
			case Constant.FLAG_GET_GET_GOODS_LIST:
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
						
						if(iSize > 0)
						{
							myGoodAdapter.notifyDataSetChanged();
						}
					}else {
						if(currentGoodPage == 1){mGoodList.clear();
						myGoodAdapter.notifyDataSetChanged();}
					}
				}
			}
			break;
			
			case Constant.FLAG_GET_GET_SHOPS_LIST:
			{
				HttpReturnData receiveData = (HttpReturnData) msg.obj;
				if (receiveData.isSuccess()) 
				{
					ShopsListBean  baseJsonDataBean = (ShopsListBean)receiveData.getObg();
					if (baseJsonDataBean.getCode().equals("1"))
					{
						List <shoplist> result = baseJsonDataBean.getResult();
						int iSize = result.size();
						for(int iLoop = 0; iLoop < iSize; iLoop++)
						{
							mShopList.add(result.get(iLoop));
						}
						
						if(iSize > 0)
						{
							myShopAdapter.notifyDataSetChanged();
						}
					}
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
		CustomProgressDialog.startProgressDialog(context, "loading...");
		mThread = new JsonThread(context, list, mHandler, Constant.FLAG_GET_GET_GOODS_LIST);
		mThread.start();
		
	}
	
	public void getshopList(List <Bean> list)
	{
		if(mThread != null)
		{
			mThread.cancleReturnData();
		}

		CustomProgressDialog.startProgressDialog(context, "loading...");
		mThread = new JsonThread(context, list, mHandler, Constant.FLAG_GET_GET_SHOPS_LIST);
		mThread.start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.teamall_activity);
		MyApplication.getInstance().addActivity(this);
		ViewUtils.inject(this);
		context = this;
		actionbarInit();
		RadioGroupInit();
//		GDLevel2Init();
		
		listviewInit();
		currentGoodPage = 1;
		getSelectData("");
		
	}
	private int select_type = 0;//0是综合，1是，销量，2，价格，3是评论；
	private void getSelectData(String name){
		requestgoodlist.clear();
		name = et_Search.getText().toString().trim();
//		currentGoodPage++;
		if(!TextUtils.isEmpty(name)){
			currentGoodPage = 1;
			requestgoodlist.add(new Bean("goodsName", name));
			requestgoodlist.add(new Bean("pageNo", currentGoodPage+""));
			requestgoodlist.add(new Bean("pageSize", "5"));
		}else{
		String pageNo = "" + currentGoodPage;
		requestgoodlist.add(new Bean("pageNo", pageNo));
		requestgoodlist.add(new Bean("pageSize", PAGESIZE));
		if(!TextUtils.isEmpty(name))requestgoodlist.add(new Bean("goodsName", name));
		if(select_type == 0)requestgoodlist.add(new Bean("allSort", "0"));//综合
		else if(select_type == 1)requestgoodlist.add(new Bean("stockSort", "0"));//销量
		else if(select_type == 2)requestgoodlist.add(new Bean("priceSort", mRbPriceCount+""));//价格
		else if(select_type == 3)requestgoodlist.add(new Bean("evaSort", "0"));//评论
		}
		getgoodList(requestgoodlist);
	}

	@ViewInject(R.id.tea_mall_radiogroup_choice)//商品
	private RadioGroup mRgGoods;

	@ViewInject(R.id.tea_mall_radiogroup_choice_level_2)
	private RadioGroup mRgChoiceLevel2;
	
	@ViewInject(R.id.relative_level_2)
	private RelativeLayout mRlLevle2;
	
	@ViewInject(R.id.tea_mall_radiogroup_choice_shop)//茶铺
	private RadioGroup mRgShop;
	
	
	
	/**价格升序降序*/
	@ViewInject(R.id.teamall_price)
	private RadioButton mRbPrice;
	
	/**价格 第一次升序降序不变   再点一次才变*/
	private int mRbPriceCount;
	
	private void RadioGroupInit() 
	{
		// TODO Auto-generated method stub

		mRgGoods.setOnCheckedChangeListener(onCheckedChangeListener);
//		mRgChoiceLevel2.setOnCheckedChangeListener(onCheckedChangeListenerLevel2);
		mRbPrice.setOnClickListener(new OnClickListener() 
		{		
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				currentGoodPage =1;
				if(mRbPriceCount == 0)
				{
					mRbPriceCount=1;
				}else mRbPriceCount = 0;
				
				LogUtils.w("---------->前"+mRbPrice.isSelected());
				
				mRbPrice.setSelected(!mRbPrice.isSelected());
				getSelectData("");
				LogUtils.w("---------->后"+mRbPrice.isSelected());
				
				
			}
		});
		mRgShop.setOnCheckedChangeListener(onCheckedChangeListener);
		
		
		
	}

	private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() 
	{
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId)
		{
			
			// TODO Auto-generated method stub
			/*if(checkedId == R.id.teamall_comprehensive||checkedId == R.id.teamall_comprehensive_shop)
			{
				if(mRlLevle2.getVisibility() != View.VISIBLE)
				{					
					mRlLevle2.setVisibility(View.VISIBLE);	
				}								
			}
			else
			{				
				if(mRlLevle2.getVisibility() != View.GONE)
				{				
					mRlLevle2.setVisibility(View.GONE);			
				}				
			}
			
			if(checkedId != R.id.teamall_price)
			{
				mRbPriceCount = 0;	
			}*/
			currentGoodPage = 1;

			switch (checkedId) 
			{
			//综合
			case R.id.teamall_comprehensive:
				select_type = 0;
				
				getSelectData("");
				
				break;

			case R.id.teamall_sales_volume:
				select_type = 1;
				getSelectData("");
				
				break;
			case R.id.teamall_price:
				select_type = 2;
				
				break;

			case R.id.teamall_comment:
				select_type = 3;
				getSelectData("");
				break;				
			//综合
			case R.id.teamall_comprehensive_shop:
				select_type = 0;
				getShopSelectData();
				break;

			case R.id.teamall_sales_volume_shop:
				select_type = 1;
				getShopSelectData();
				break;

			case R.id.teamall_comment_shop:
				select_type = 2;
				getShopSelectData();
				break;
			
			default:
				break;
			}

		}
	};
	
	
	
	/*@ViewInject(R.id.tea_mall_2_linear)
	private LinearLayout teaMallLinearGridviewContainer;
	
	@ViewInject(R.id.tea_mall_2_gridview)
	private GridView mGdLevel;
	
	private List<Model_TeaMallGridview> mListGDLevel2;
	
	private MyBaseAdapter_GDLevel2 mMyAdapterLevel2;
	
	@ViewInject(R.id.tea_mall_2_gridview_button)
	private Button mBtnConfirmLevel2;
	
	
	private void GDLevel2Init()
	{
		mListGDLevel2 = new ArrayList<Model_TeaMallGridview>();
		falseDataInit();
		
		mMyAdapterLevel2 = new MyBaseAdapter_GDLevel2(context, mListGDLevel2);
		
		mGdLevel.setAdapter(mMyAdapterLevel2);
		
		mBtnConfirmLevel2.setOnClickListener(new OnClickListener() 
		{			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub				
				teaMallLinearGridviewContainer.setVisibility(View.GONE);				
			}
		});	
	}

	private void falseDataInit() 
	{
		// TODO Auto-generated method stub
		
		for (int i = 0; i < 6; i++) 
		{		
			mListGDLevel2.add(new Model_TeaMallGridview(true,"茉莉花茶"+i));
		}
	}

	private OnCheckedChangeListener onCheckedChangeListenerLevel2 = new OnCheckedChangeListener()
	{
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId)
		{
			// TODO Auto-generated method stub
			teaMallLinearGridviewContainer.setVisibility(View.VISIBLE);

			switch (checkedId) 
			{		
			//茶类  
			case R.id.teamall_sort:
				break;
			//品牌
			case R.id.teamall_sort_name:
				break;
			//产地
			case R.id.teamall_in_where:
				break;
			//价格范围
			case R.id.teamall_price_range:
				break;

			default:
				break;
			}
		}
	};*/

	@ViewInject(R.id.tea_mall_back)
	private ImageView mIvBack;

	private void actionbarInit() 
	{
		// TODO Auto-generated method stub
		mIvBack.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}
		});

		spinnerInit();
		etSearchInit();
	}

	@ViewInject(R.id.tea_mall_edittext_search)
	private EditText et_Search;

	private void etSearchInit()
	{
		et_Search.setOnKeyListener(new OnKeyListener()
		{
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) 
			{
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_ENTER) 
				{
					LogUtils.e("-------->按下");
					String name = et_Search.getText().toString().trim();
					if(!TextUtils.isEmpty(name)){
						/*SharedPreferences sp = context.getSharedPreferences("CHAXIN_CONFIG", context.MODE_PRIVATE);
						Editor edit = sp.edit();
						edit.putString("shopName_for_goodsearch", name);
						edit.putString("shopID_for_goodsearch", "");
						edit.commit();
						
						openActivity(MyShopSearchGoodsActivity.class);*/
						getSelectData(name);
					}
					return true;
				}
				return false;
			}
		});
		
		et_Search.addTextChangedListener(new TextWatcher() {
			
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
					getSelectData("");
				}
					
					
				
			}
		});
	}

	@ViewInject(R.id.tea_mall_spinner)
	private Spinner mSpinner;

	private String str[] = { "商品", "店铺" };

	private void spinnerInit() 
	{
		ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(context,
				R.layout.drop_down_item, str);

		myAdapter.setDropDownViewResource(R.layout.drop_down_item);

		mSpinner.setAdapter(myAdapter);

		mSpinner.setSelection(0, true);

		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() 
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id)
			{
				// TODO Auto-generated method stub
				switch (position)
				{
				case 0:
					mRgGoods.setVisibility(View.VISIBLE);
					mRgShop.setVisibility(View.GONE);
					mLvTeaMallShops.setVisibility(View.GONE);
					mLvTeaMallGoods.setVisibility(View.VISIBLE);
					
					/*requestgoodlist.clear();
					currentGoodPage++;
					String pageNo = "" + currentGoodPage;
					requestgoodlist.add(new Bean("pageNo", pageNo));
					requestgoodlist.add(new Bean("pageSize", PAGESIZE));
					
					getgoodList(requestgoodlist);*/
					currentGoodPage = 1;
					select_type = 0;
					getSelectData("");

					break;
				case 1:
					mRgGoods.setVisibility(View.GONE);
					mRgShop.setVisibility(View.VISIBLE);
					mLvTeaMallShops.setVisibility(View.VISIBLE);
					mLvTeaMallGoods.setVisibility(View.GONE);
							
					currentShopPage =1;
					select_type = 0;
					getShopSelectData();

					break;

				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// TODO Auto-generated method stub

			}
		});
	}
	
	/**
	 * 获取商铺列表数据
	 */
	private void getShopSelectData(){
		requestshoplist.clear();
		String shoppageNo = "" + currentShopPage;
		requestshoplist.add(new Bean("sort=", select_type+""));
		requestshoplist.add(new Bean("&pageNo=", shoppageNo));
		requestshoplist.add(new Bean("&pageSize=", PAGESIZE));
		
		getshopList(requestshoplist);
	}
	
	private void listviewInit() 
	{
		//final PullToRefreshListView mLvTeaMallGoods = (PullToRefreshListView)findViewById(R.id.TeaMallGoods_listview);
		mShopList = new ArrayList<shoplist>();
		mGoodList = new ArrayList<goodlist>();

		myGoodAdapter = new MyBaseAdapter_TeaMallGoodsList(context, mGoodList);

		mLvTeaMallGoods.setAdapter(myGoodAdapter);
		
		myShopAdapter = new MyBaseAdapter_TeaMallShopsList(context, mShopList);
		mLvTeaMallShops.setAdapter(myShopAdapter);
		swipyRefresh.setDirection(SwipyRefreshLayoutDirection.TOP);

		swipyRefresh.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh(SwipyRefreshLayoutDirection direction) {
				currentGoodPage =1;
				getSelectData(et_Search.getText().toString().trim());
			}

		
		});
		/*mLvTeaMallGoods.setonRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				
				currentGoodPage =1;
				getSelectData(et_Search.getText().toString().trim());
			}
		});*/
		
		mLvTeaMallGoods.setOnScrollListener(new OnScrollListener()
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
						/*requestgoodlist.clear();
						String pageNo = "" + currentGoodPage;
						requestgoodlist.add(new Bean("pageNo", pageNo));
						requestgoodlist.add(new Bean("pageSize", PAGESIZE));
						
						getgoodList(requestgoodlist);*/
						getSelectData("");
					}					
				}				
			}});
				
	
		mLvTeaMallShops.setOnScrollListener(new OnScrollListener()
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
						currentShopPage++;
						/*requestshoplist.clear();
						String shoppageNo = "" + currentShopPage;
						requestshoplist.add(new Bean("sort=", ShopSort));
						requestshoplist.add(new Bean("&pageNo=", shoppageNo));
						requestshoplist.add(new Bean("&pageSize=", PAGESIZE));
						getshopList(requestshoplist);*/
						getShopSelectData();
					}					
				}				
			}});
	}
	
}