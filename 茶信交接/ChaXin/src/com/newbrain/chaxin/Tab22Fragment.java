package com.newbrain.chaxin;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.adapter.MyBaseAdapter_ShopCar;
import com.newbrain.chaxin.teamall.ComfirmOrderActivity;
import com.newbrain.chaxin.teamall.DetailActivity;
import com.newbrain.chaxin.teamall.TeaMallActivity;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.BaseJsonDataBean;
import com.newbrain.model.Bean;
import com.newbrain.model.GroupCar;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.ShopCartGoodsListBean;
import com.newbrain.model.ShopCartGoodsListBean.ShopCartGoodDetail;
import com.newbrain.model.ShopCartList;
import com.newbrain.model.SimpleOrder;
import com.newbrain.swipeRefresh.SwipyRefreshLayout;
import com.newbrain.swipeRefresh.SwipyRefreshLayout.OnRefreshListener;
import com.newbrain.swipeRefresh.SwipyRefreshLayoutDirection;
import com.newbrain.user.User;
import com.newbrain.utils.ButtonColorFilter;
import com.newbrain.xutils.Xutils_BitmapUtils;
import com.newbrain.xutils.Xutils_HttpUtils;
import com.squareup.picasso.Picasso;
public class Tab22Fragment extends Fragment implements 
        OnChildClickListener, 
        OnGroupClickListener,OnScrollListener

{
	private List<GroupCar> groups = new ArrayList<GroupCar>();
	private List<List<ShopCartList>>childs = new ArrayList<List<ShopCartList>>();
	private Context context;
	HttpUtils httpUtils;
	
//	private JsonThread mThread;
	public int remove_index;
	private ExpandableListView  elistview;
	private LinearLayout ll_null;
	private MyexpandableListAdapter adapter;
	private FrameLayout indicatorGroup;
	private LayoutInflater mInflater;
	private int indicatorGroupId = -1;
	private int indicatorGroupHeight;
	private SwipyRefreshLayout swrefresh;
	private Button btn_gon;
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.tab22_fragment, null);
		elistview = (ExpandableListView) view.findViewById(R.id.elistview);
		mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		indicatorGroup = (FrameLayout) view.findViewById(R.id.topGroup);
		swrefresh = (SwipyRefreshLayout) view.findViewById(R.id.swipyrefreshlayout);
		ll_null = (LinearLayout) view.findViewById(R.id.ll_null);
		btn_gon = (Button) view.findViewById(R.id.btn_go);
		ButtonColorFilter.setButtonFocusChanged(btn_gon);
		
		return view;
	}
	@Override
	public void onAttach(Activity activity) {
		context = activity;
		super.onAttach(activity);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		ViewUtils.inject(this, view);
		context = getActivity();
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		actionbarInit();
		viewInit();		
		
	}
	
	@OnClick(R.id.btn_go)
	private void onclickNull(View v){
		startActivity(new Intent(context, TeaMallActivity.class));

	}
	@Override
	public void onResume() {
		setList();
		super.onResume();
	}
	
	@ViewInject(R.id.tab2_swipw_menu_listView)
	private SwipeMenuListView mSwipeMenuListView;
	
	
	/*private MyBaseAdapter_ShopCar mMyAdapter_ShopCar;
	private List<ShopCartGoodDetail> ShopCarData;
	private List<ShopCartGoodDetail> result;
	
	public class ContentComparator implements Comparator 
	{

		@Override
		public int compare(Object good1, Object good2)
		{
			// TODO Auto-generated method stub
			ShopCartGoodDetail gd1 = (ShopCartGoodDetail)good1;
			ShopCartGoodDetail gd2 = (ShopCartGoodDetail)good2;
			return gd1.getStoreName().compareTo(gd1.getStoreName());
		}
	
	}*/

	private void viewInit() 
	{
		
		swrefresh.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh(SwipyRefreshLayoutDirection direction) {
				setList();
				
			}
		});
		
		elistview.setOnItemLongClickListener(
				new QuickWayListener());
		
	}
	
	/*private Handler mHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case Constant.FLAG_GET_SHOPCART_LIST:
				HttpReturnData reData = (HttpReturnData) msg.obj;
				if (reData.isSuccess()) 
				{
					ShopCarData.clear();
					ShopCartGoodsListBean baseJsonDataBean = (ShopCartGoodsListBean) reData.getObg();
					if (baseJsonDataBean.getCode().equals("1"))
					{
						Log.e("lijinjin", "数据不为空");
						result = baseJsonDataBean.getResult();
						int size = result.size();
						ShopCarData.clear();
						for(int iLoop = 0; iLoop < size;)
						{
							if(ShopCarData.size() == 0)
							{
								ShopCarData.add(result.get(iLoop));
								result.remove(iLoop);
								iLoop = 0;
							}
							else
							{
								if((ShopCarData.get((ShopCarData.size() -1)).getStoreName())
										.equals((result.get(iLoop)).getStoreName()))
								{
									ShopCarData.add(result.get(iLoop));
									result.remove(iLoop);
									iLoop = 0;
								}
								else
								{
									iLoop++;
									if(1 == result.size())
									{
										ShopCarData.add(result.get(0));
										result.remove(0);
										break;
									}
									else
									{
										if(iLoop == result.size())
										{
											iLoop = 0;
											ShopCarData.add(result.get(iLoop));
											result.remove(iLoop);
											iLoop = 0;
										}
									}
								}
							}
						}
						
						mMyAdapter_ShopCar.notifyDataSetChanged();
					}
					else 
					{
						ll_null.setVisibility(View.VISIBLE);
						Log.e("lijinjin", "result:" + baseJsonDataBean.getMessage());
					}
				}
				break;
				
			case Constant.FLAG_GET_REMOVE_SHOPCART_LIST:
			{
				HttpReturnData receiveData = (HttpReturnData) msg.obj;
				if (receiveData.isSuccess()) 
				{
					BaseJsonDataBean baseJsonDataBean = (BaseJsonDataBean) receiveData.getObg();
					if (baseJsonDataBean.getCode().equals("1"))
					{
						ShopCarData.remove(remove_index);
						mMyAdapter_ShopCar.notifyDataSetChanged();
					}
					else
					{
						
					}
				}
			}
				break;
				
			default:
				break;
			}
		}
	};*/
	
	private void setList()
	{		
		/*List<Bean> list = new ArrayList<Bean>();
		if(mThread != null)
		{
			mThread.cancleReturnData();
		}
		
		mThread = new JsonThread(context, list, mHandler, Constant.);
		mThread.start();*/
		String userID = User.getInstance().getId();
		String usertoken = User.getInstance().getToken();
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId",userID );
		params.addBodyParameter("token", usertoken);
		httpUtils.send(HttpMethod.POST, Constant.METHOD_USER_CART_LIST_URL2, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				swrefresh.setRefreshing(false);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result = arg0.result;
				parseJSonresult(result);
				
				
			}
		});
	}
	
	protected void parseJSonresult(final String result) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					JSONObject obj = new JSONObject(result);
					String code = obj.getString("code");
					if(code.equals("1")){
						
					JSONArray array = 	obj.getJSONArray("result");
					int size = array.length();
					final List<GroupCar> groupst = new ArrayList<GroupCar>();
					final List<List<ShopCartList>>childst = new ArrayList<List<ShopCartList>>();
					for(int i = 0;i<size;i++){
						JSONObject objC = array.getJSONObject(i);
						String storeId = objC.getString("storeId");//商铺id
						String storeName = objC.getString("storeName");//商铺名称
						GroupCar car = new GroupCar();
						car.setStoreId(storeId);
						car.setStoreName(storeName);
						groupst.add(car);
						JSONArray arrayC = objC.getJSONArray("list");
						int sizeC = arrayC.length();
						List<ShopCartList> child = new ArrayList<ShopCartList>();
						for(int j= 0;j<sizeC;j++){
							JSONObject objD = arrayC.getJSONObject(j);
							String id =objD.getString("id");//商铺id
							String goodsId = objD.getString("goodsId");//商铺名称
							String goodsName = objD.getString("goodsName");//商铺名称
							String goodsImage = objD.getString("goodsImage");//商铺名称
							String primeCost = objD.getString("primeCost");//商铺名称
							String price = objD.getString("price");//商铺名称
							String num = objD.getString("num");//商铺名称
							ShopCartList carlist = new ShopCartList();
							carlist.setID(id);
							carlist.setGoodsId(goodsId);
							carlist.setGoodsImage(goodsImage);
							carlist.setNum(num);
							carlist.setGoodsName(goodsName);
							carlist.setPrice(price);
							carlist.setPrimeCost(primeCost);
							child.add(carlist);
							
						}
						childst.add(child);
						
					}
					getActivity().runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							swrefresh.setRefreshing(false);

							ll_null.setVisibility(View.GONE);
							groups.clear();
							childs.clear();
							groups.addAll(groupst);
							childs.addAll(childst);
							adapter = new MyexpandableListAdapter(context);
							elistview.setAdapter(adapter);
							elistview.setOnScrollListener(Tab22Fragment.this);
							elistview.setGroupIndicator(null);
							
							// 展开所有group
					        for (int i = 0, count = elistview.getCount(); i < count; i++) {
					        	elistview.expandGroup(i);
					        }
					        indicatorGroup.removeAllViews();
							mInflater.inflate(R.layout.group, indicatorGroup, true);
							indicatorGroupId = -1;
							indicatorGroupHeight = 0;
					        elistview.setOnChildClickListener(Tab22Fragment.this);
					        elistview.setOnGroupClickListener(Tab22Fragment.this);
						}
					});
					}else {
						getActivity().runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								ll_null.setVisibility(View.VISIBLE);
								swrefresh.setRefreshing(false);
							}
						});
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				
			}
		}).start();
		
	}


	public void removeShopCart(String ID)
	{		
		/*List<Bean> list = new ArrayList<Bean>();
		if(mThread != null)
		{
			mThread.cancleReturnData();
		}
		String userID = User.getInstance().getId();
		String usertoken = User.getInstance().getToken();
		list.add(new Bean("ids", ID));
		list.add(new Bean("userId", userID));
		list.add(new Bean("token", usertoken));
		mThread = new JsonThread(context, list, mHandler, Constant.FLAG_GET_REMOVE_SHOPCART_LIST);
		mThread.start();*/
		String userID = User.getInstance().getId();
		String usertoken = User.getInstance().getToken();
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId",userID );
		params.addBodyParameter("token", usertoken);
		params.addBodyParameter("ids", ID);
		httpUtils.send(HttpMethod.POST,Constant.METHOD_USER_REMOVE_SHOPCART_URL, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result = arg0.result;
				parseJSonresult2(result);
				
				
			}

			private void parseJSonresult2(String result) {
				
				try {
					JSONObject obj = new JSONObject(result);
					String code = obj.getString("code");
					String msg = obj.getString("message");
					if(code.equals("1")){
						setList();
					}
						Toast.makeText(context, msg, 1).show();
					
				} catch (JSONException e) {
				}
			}
		});
	}
	
	/*private void swipeListviewInit() 
	{
		// TODO Auto-generated method stub
		ShopCarData = new ArrayList<ShopCartGoodDetail>();	
		mMyAdapter_ShopCar = new MyBaseAdapter_ShopCar(context, ShopCarData);

		SwipeMenuCreator creator = new SwipeMenuCreator() 
		{
			@Override
			public void create(SwipeMenu menu)
			{
				// TODO Auto-generated method stub
				
				SwipeMenuItem item = new SwipeMenuItem(context);
				
				item.setBackground(new ColorDrawable(Color.RED));
				item.setTitle(getString(R.string.delete));
				item.setWidth(dp2px(80));
				item.setTitleColor(Color.WHITE);
				item.setTitleSize(dp2px(6));
				
				menu.addMenuItem(item);
			}
		};

		mSwipeMenuListView.setMenuCreator(creator);
		mSwipeMenuListView.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			@Override
			public void onMenuItemClick(int arg0, SwipeMenu arg1, int arg2) 
			{
				// TODO Auto-generated method stub
				ToastUtil.Toast(context, "点击了"+arg0);
				String ID = ShopCarData.get(arg0).getId();
				removeShopCart(ID);
				remove_index = arg0;
			}
		});

		mSwipeMenuListView.setAdapter(mMyAdapter_ShopCar);
	}*/
	
	/*private int dp2px(int dp)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
	*/
	
	@ViewInject(R.id.actionbar_homepager_style_textview_left)
	private TextView actionbar_left;

	@ViewInject(R.id.actionbar_homepager_style_textview_name_center)
	private TextView actionbar_center;

	@ViewInject(R.id.actionbar_homepager_style_button_message_right)
	private Button actionbar_right;

	private void actionbarInit() 
	{
		// TODO Auto-generated method stub
		actionbar_left.setVisibility(View.GONE);

		actionbar_center.setText("购物车");
		actionbar_center.setVisibility(View.VISIBLE);

		// actionbar_right.setText("");
		actionbar_right.setVisibility(View.GONE);

		/*actionbar_right.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(context, MessageActivity.class));
			}
		});*/
	}


/*	@Override
	public View getPinnedHeader() {

        View headerView = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.group, null);
        headerView.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        return headerView;

	}


	@Override
	public void updatePinnedHeader( View headerView,int firstVisibleGroupPos) {

        GroupCar firstVisibleGroup =  (GroupCar) adapter.getGroup(firstVisibleGroupPos);
        TextView textView = (TextView) headerView.findViewById(R.id.shop_car_item_shop_name);
        final CheckBox ck = (CheckBox) headerView.findViewById(R.id.shop_car_item_check_all);
        ck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				 ck.setChecked(arg1);
			}
		});
        textView.setText(firstVisibleGroup.getStoreName());
        ck.setChecked(firstVisibleGroup.isCheck());
		
	}*/


	@Override
	public boolean onGroupClick(ExpandableListView arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2,
			int arg3, long arg4) {
		SharedPreferences sp = context.getSharedPreferences("CHAXIN_CONFIG", context.MODE_PRIVATE);
		Editor editor = sp.edit();
		String goodID = childs.get(arg2).get(arg3).getGoodsId();
		editor.putString("goodid_for_detail", goodID);
		editor.putBoolean("isShangpu", false);
		editor.commit();
		context.startActivity(new Intent(context, DetailActivity.class));	
		return true;
	}
	
	class MyexpandableListAdapter extends BaseExpandableListAdapter {
		private Context context;
		 private LayoutInflater inflater;
		 public MyexpandableListAdapter(Context context){
			 this.context = context;
			 this.inflater = LayoutInflater.from(context);
			 
		 }

		@Override
		public Object getChild(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return childs.get(arg0).get(arg1);
		}

		@Override
		public long getChildId(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return arg1;
		}

		@Override
		public View getChildView(int arg0, int arg1, boolean arg2, View convertView,
				ViewGroup arg4) {

            ChildHolder childHolder = null;
            if (convertView == null) {
                childHolder = new ChildHolder();
                convertView = inflater.inflate(R.layout.shop_car_listview_item, null);

                ViewUtils.inject(childHolder, convertView);

                convertView.setTag(childHolder);
            } else {
                childHolder = (ChildHolder) convertView.getTag();
            }
            convertView.setTag(R.id.btn_js, arg0);
    		convertView.setTag(R.id.rl_js, arg1);
            List<ShopCartList> details = childs.get(arg0);
    		final ShopCartList detail = details.get(arg1);
    		 String nusms = detail.getNum();
    				if(TextUtils.isEmpty(nusms))nusms = "0";
    		final	int no = Integer.parseInt(nusms);
//    		childHolder.tvShopName.setText(detail.getStoreName());
    		childHolder.tvGoodsName.setText(detail.getGoodsName());
    		childHolder.tvGoodsPrice.setText(detail.getPrice());
    		childHolder.tvGoodsNum.setText(detail.getNum());
    		GroupCar car = groups.get(arg0);
    		final String storeId = car.getStoreId(); 
    		String image = detail.getGoodsImage();
    		final boolean ischeck = detail.isCheck();
    		if(ischeck)childHolder.checkBoxGoods.setBackgroundResource(R.drawable.check_checked);
    		else childHolder.checkBoxGoods.setBackgroundResource(R.drawable.check_normal);
    		childHolder.checkBoxGoods.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if(ischeck)detail.setCheck(false);
					else detail.setCheck(true);
					adapter.notifyDataSetChanged();
				}
			});
    		
    		/*if(ischeck)childHolder.checkBoxGoods.setChecked(true);
    		else childHolder.checkBoxGoods.setChecked(false);
    		childHolder.checkBoxGoods.setSelected(ischeck);
    		childHolder.checkBoxGoods.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					// TODO Auto-generated method stub
					detail.setCheck(arg1);
					adapter.notifyDataSetChanged();
				}
			});*/
    		
    		
    		
    		int size = details.size();
    		if(arg1 == size -1) {
    			childHolder.rl_js.setVisibility(View.VISIBLE);
    			Double sum = 0.0;
    			int nums=0;
    			final List<SimpleOrder> data = new ArrayList<SimpleOrder>();
    			for(int i = 0;i<size;i++){
    				String num = details.get(i).getNum();
    				String price = details.get(i).getPrice();
    				String gooddetailID = details.get(i).getGoodsId();
    				String goodImage = details.get(i).getGoodsImage();
    				String primeCost = details.get(i).getPrimeCost();
    				String goodsName = details.get(i).getGoodsName();
    				if(!TextUtils.isEmpty(goodImage)){
    					Picasso.with(context).load(goodImage).centerCrop().resize(120, 120).into(childHolder.goods_img);
    				}else Picasso.with(context).load(R.drawable.cha).centerCrop().resize(120, 120).into(childHolder.goods_img);
    				
    				boolean isCheck = details.get(i).isCheck();
    				if(isCheck){
    				sum += Integer.parseInt(num)*Double.parseDouble(price);
    				nums+= Integer.parseInt(num);
    				SimpleOrder order = new SimpleOrder();
    				order.setGoodsId(gooddetailID);
    				order.setIcon(goodImage);
    				order.setName(goodsName);
    				order.setNum(nums+"");
    				if(TextUtils.isEmpty(price))price = "0.0";
    				order.setPrice(price);
    				order.setPrimeCost(primeCost);
    				data.add(order);
    				}
    				
    			}
    			final Double tempsum = sum;
    			childHolder.tv_prices.setText("￥"+sum);
    			childHolder.btn_js.setText("结算（"+nums+")");
    			childHolder.btn_js.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {//结算
						/*
						Bundle bundle = new Bundle();
						bundle.putDouble("prices", tempsum);
						bundle.putSerializable("datas",(Serializable) data);*/
//						getActivity().startActivity(new Intent(getActivity(), ComfirmOrderActivity.class), bundle);;
						if(data.size()>0){
						Intent intent = new Intent(getActivity(), ComfirmOrderActivity.class);
						intent.putExtra("prices", tempsum);
						intent.putExtra("stroreId", storeId);
						intent.putExtra("datas",(Serializable) data);
						startActivity(intent);
						}
					}
				});
    			
    			
    			
    		}
    		else childHolder.rl_js.setVisibility(View.GONE);
    		
    		childHolder.btnGoodsNumReduce.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					int notemp = no;
					if(notemp>0)notemp-=1;
					detail.setNum(notemp+"");
					notifyDataSetChanged();
				}
			});
			
		childHolder.btnGoodsNumPlus.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					int notemp = no;
					if(notemp>=0)notemp+=1;
					detail.setNum(notemp+"");
					notifyDataSetChanged();
				}
			});
            return convertView;

		}

		@Override
		public int getChildrenCount(int arg0) {
			// TODO Auto-generated method stub
			return childs.get(arg0).size();
		}

		@Override
		public Object getGroup(int arg0) {
			// TODO Auto-generated method stub
			return groups.get(arg0);
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return groups.size();
		}

		@Override
		public long getGroupId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getGroupView(final int arg0, boolean arg1, View convertView,
				ViewGroup arg3) {

//            GroupHolder groupHolder = null;
			View v = null;
            if (convertView == null) {
                v = inflater.inflate(R.layout.group, null);
                convertView = v;
                
            } else {
            	v = convertView;
            }
            convertView.setTag(R.id.btn_js, arg0);
    		convertView.setTag(R.id.rl_js, -1);
            TextView textView = (TextView) v.findViewById(R.id.shop_car_item_shop_name);
            CheckBox ck_g = (CheckBox) v.findViewById(R.id.shop_car_item_check_all);
           final GroupCar car =  groups.get(arg0);
           String storeName = car.getStoreName(); 
           if(TextUtils.isEmpty(storeName))storeName ="茶铺";
          textView.setText(storeName);
           boolean ischeck = car.isCheck();
          ck_g.setSelected(ischeck);
           ck_g.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton cp, boolean arg1) {
					car.setCheck(arg1);
					List<ShopCartList> temps =  childs.get(arg0);
					for(int i= 0;i<temps.size();i++){
						ShopCartList temp = temps.get(i);
						temp.setCheck(arg1);
					}
					
				adapter.notifyDataSetChanged();
				
			}
		});
            return convertView;

		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return true;
		}
		

	    /*class GroupHolder {
	    	@ViewInject(R.id.shop_car_item_shop_name)
	        TextView textView;
	    	@ViewInject(R.id.shop_car_item_check_all)
	        CheckBox ck_g;
	    }
*/
	    public class ChildHolder 
		{	
			/**店铺名称   第二个与第一相同需隐藏*/
			@ViewInject(R.id.tab2_money_check_goods)//金额合计
			private TextView tv_prices;

			@ViewInject(R.id.btn_js)
			private Button btn_js;
			@ViewInject(R.id.rl_js)
			private RelativeLayout rl_js;
			@ViewInject(R.id.shop_car_item_check)
			private ImageView checkBoxGoods;
			
			@ViewInject(R.id.shop_car_item_img)
			private ImageView goods_img;

			@ViewInject(R.id.shop_car_item_tea_name)
			private TextView tvGoodsName;

			@ViewInject(R.id.shop_car_item_tea_price)
			private TextView tvGoodsPrice;

			@ViewInject(R.id.shop_car_reduce)
			private Button btnGoodsNumReduce;

			@ViewInject(R.id.shop_car_num)
			private TextView tvGoodsNum;

			@ViewInject(R.id.shop_car_plus)
			private Button btnGoodsNumPlus;

		}

		
	}

	@Override
	public void onScroll(AbsListView view, int arg1, int arg2, int arg3) {

		final ExpandableListView listView = (ExpandableListView) view;
		/**
		 * calculate point (0,0)
		 */
		int npos = view.pointToPosition(0, 0);//
		if (npos == AdapterView.INVALID_POSITION)// 濡傛灉绗竴涓綅缃€兼棤鏁?
			return;

		long pos = listView.getExpandableListPosition(npos);
		int childPos = ExpandableListView.getPackedPositionChild(pos);// 鑾峰彇绗竴琛宑hild鐨刬d
		int groupPos = ExpandableListView.getPackedPositionGroup(pos);// 鑾峰彇绗竴琛実roup鐨刬d
		if (childPos == AdapterView.INVALID_POSITION) {// 绗竴琛屼笉鏄樉绀篶hild,灏辨槸group,姝ゆ椂娌″繀瑕佹樉绀烘寚绀哄櫒
			View groupView = listView.getChildAt(npos
					- listView.getFirstVisiblePosition());// 绗竴琛岀殑view
			indicatorGroupHeight = groupView.getHeight();// 鑾峰彇group鐨勯珮搴?
			indicatorGroup.setVisibility(View.GONE);// 闅愯棌鎸囩ず鍣?
		} else {

			indicatorGroup.setVisibility(View.VISIBLE);// 婊氬姩鍒扮涓€琛屾槸child锛屽氨鏄剧ず鎸囩ず鍣?
		}
		// get an error data, so return now
		if (indicatorGroupHeight == 0) {
			return;
		}
		// update the data of indicator group view
		if (groupPos != indicatorGroupId) {
			// 濡傛灉鎸囩ず鍣ㄦ樉绀虹殑涓嶆槸褰撳墠group
			 if (adapter != null)
				adapter.getGroupView(groupPos,
						listView.isGroupExpanded(groupPos),
						indicatorGroup.getChildAt(0), null);// 灏嗘寚绀哄櫒鏇存柊涓哄綋鍓峠roup

			indicatorGroupId = groupPos;
			// Log.e(TAG, PRE + "bind to new group,group position = " +
			// groupPos);
			// mAdapter.hideGroup(indicatorGroupId); // we set this group view
			// to be hided
			// 涓烘鎸囩ず鍣ㄥ鍔犵偣鍑讳簨浠?
			indicatorGroup.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					listView.collapseGroup(indicatorGroupId);
				}
			});
		}
		if (indicatorGroupId == -1) // 濡傛灉姝ゆ椂grop鐨刬d鏃犳晥锛屽垯杩斿洖
			return;

		/**
		 * calculate point (0,indicatorGroupHeight) 涓嬮潰鏄舰鎴愬線涓婃帹鍑虹殑鏁堟灉
		 */
		int showHeight = indicatorGroupHeight;
		int nEndPos = listView.pointToPosition(0, indicatorGroupHeight);// 绗簩涓猧tem鐨勪綅缃?
		if (nEndPos == AdapterView.INVALID_POSITION)// 濡傛灉鏃犳晥鐩存帴杩斿洖
			return;
		long pos2 = listView.getExpandableListPosition(nEndPos);
		int groupPos2 = ExpandableListView.getPackedPositionGroup(pos2);// 鑾峰彇绗簩涓猤roup鐨刬d
		if (groupPos2 != indicatorGroupId) {// 濡傛灉涓嶇瓑浜庢寚绀哄櫒褰撳墠鐨刧roup
			View viewNext = listView.getChildAt(nEndPos
					- listView.getFirstVisiblePosition());
			showHeight = viewNext.getTop();
		}

		// update group position
		MarginLayoutParams layoutParams = (MarginLayoutParams) indicatorGroup
				.getLayoutParams();
		layoutParams.topMargin = -(indicatorGroupHeight - showHeight);
		indicatorGroup.setLayoutParams(layoutParams);
	
	}


	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	private int groupPos,childPos;
	private class QuickWayListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
			groupPos = (Integer) view.getTag(R.id.btn_js); // 参数值是在setTag时使用的对应资源id号
			childPos = (Integer) view.getTag(R.id.rl_js);
			if(childPos != -1){
				String id = childs.get(groupPos).get(childPos).getID();
				showDialog(id);
			}
			return false;
		}
		
	}
	
	private String[] items = new String[] { "要删除该订单？","  删除","  取消" };
	/**
	 * 显示选择对话框
	 */
	private void showDialog(final String ID) {
		/*Dialog dialog = new Dialog(context, R.style.DialogTheme);
		dialog.setTitle("要删除该订单？");
		dialog.set*/
		new AlertDialog.Builder(getActivity(),R.style.DialogTheme)
				.setItems(items, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						if(arg1 == 1){
						removeShopCart(ID);
						}
						dialog.dismiss();
					}
				})
				.show();

	}
}
