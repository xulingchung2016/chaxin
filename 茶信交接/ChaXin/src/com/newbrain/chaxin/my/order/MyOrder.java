package com.newbrain.chaxin.my.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alipay.sdk.pay.AliPayFunction;
import com.alipay.sdk.pay.PayResult;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.Tab3Fragment;
import com.newbrain.customview.CustomDialog;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.SummitOrderListBean;
import com.newbrain.swipeRefresh.SwipyRefreshLayout;
import com.newbrain.swipeRefresh.SwipyRefreshLayout.OnRefreshListener;
import com.newbrain.swipeRefresh.SwipyRefreshLayoutDirection;
import com.newbrain.user.User;
import com.newbrain.utils.SharedPreferencesHelp;
import com.newbrain.xutils.Xutils_HttpUtils;
import com.squareup.picasso.Picasso;

@SuppressLint("HandlerLeak")
public class MyOrder extends BaseActivity implements 
OnChildClickListener, 
OnGroupClickListener,OnScrollListener
{
	private Context context;
	private JsonThread mThread;
	private HttpUtils httpUtils ;
	private List<HashMap<String,String>> groups = new ArrayList<HashMap<String,String>>();
	private List<List<HashMap<String,String>>>childs = new ArrayList<List<HashMap<String,String>>>();
	private FrameLayout indicatorGroup;
	private LayoutInflater mInflater;
	private int indicatorGroupId = -1;
	private int indicatorGroupHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_order_activity);
		ViewUtils.inject(this);
		context = this;
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		actionbarInit();
		rgInit();
		listviewInit();
		dataInit();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) 
			{
			case AliPayFunction.SDK_PAY_FLAG:
			{
				PayResult payResult = new PayResult((String) msg.obj);
				
				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
//				String resultInfo = payResult.getResult();
				
				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) 
				{
					showShortToast("支付成功");
					Log.e("lijinjin", "支付成功");
					pageNum =1;
					getOrderList();
				} 
				else 
				{
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000"))
					{
						showShortToast("有可能支付失败");
						Log.e("lijinjin", "有可能支付失败");
					}
					else 
					{
						showShortToast("支付失败");
						Log.e("lijinjin", "支付失败");
					}
				}
				break;
			}
			case AliPayFunction.SDK_CHECK_FLAG: 
			{
				break;
			}

			default:
				break;
			}
		};
	};

	
	@ViewInject(R.id.order_all)
	private RadioButton mRbAll;
	@ViewInject(R.id.order_wait_comment)
	private RadioButton mRbComment;
	@ViewInject(R.id.order_wait_pay)
	private RadioButton mRbPay;
	@ViewInject(R.id.order_wait_received)
	private RadioButton mRbReceived;
	@ViewInject(R.id.order_wait_fh)
	private RadioButton mFH;
	@ViewInject(R.id.swipyrefreshlayout)
	private SwipyRefreshLayout swrefresh;;
	
	String userID,usertoken,temp;
	int  pageNum = 1;
	String statu = "";
	final int pageSize = 15;
	private void getOrderList()
	{
		
		CustomProgressDialog.startProgressDialog(context, "loading...");
		userID = User.getInstance().getId();
		usertoken = User.getInstance().getToken();
		temp = userID + "&token=" + usertoken + "&status=" + statu + "&pageNo=" + pageNum + "&pageSize=" + pageSize;
		httpUtils.send(HttpMethod.POST, Constant.METHOD_USER_GET_ORDER_LIST_URL + temp,new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				CustomProgressDialog.stopProgressDialog();
				swrefresh.setRefreshing(false);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result = arg0.result;
				CustomProgressDialog.stopProgressDialog();
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
					final List<HashMap<String,String>> groupst = new ArrayList<HashMap<String,String>>();
					final List<List<HashMap<String,String>>>childst = new ArrayList<List<HashMap<String,String>>>();
					for(int i = 0;i<size;i++){
						JSONObject objC = array.getJSONObject(i);
						
						HashMap<String,String> car = new HashMap<String,String>();
						car = JSON.parseObject(objC.toString(),new TypeReference<HashMap<String,String>>(){});
						/*String storeId = objC.getString("storeId");//商铺id
						String storeName = objC.getString("storeName");//商铺名称
						car.setStoreId(storeId);
						car.setStoreName(storeName);*/
						groupst.add(car);
						JSONArray arrayC = objC.getJSONArray("list");
//						int sizeC = arrayC.length();
						List<HashMap<String,String>> child = new ArrayList<HashMap<String,String>>();
						child = JSON.parseObject(arrayC.toString(), new TypeReference<List<HashMap<String,String>>>(){});
						/*for(int j= 0;j<sizeC;j++){
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
							
						}*/
						childst.add(child);
						
					}
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							CustomProgressDialog.stopProgressDialog();
							swrefresh.setRefreshing(false);
							if(pageNum == 1){
							groups.clear();
							childs.clear();
							}
							groups.addAll(groupst);
							childs.addAll(childst);
							adapter = new MyBaseAdapter(context);
							elistview.setAdapter(adapter);
							elistview.setOnScrollListener(MyOrder.this);
							elistview.setGroupIndicator(null);
							
							// 展开所有group
					        for (int i = 0, count = elistview.getCount(); i < count; i++) {
					        	elistview.expandGroup(i);
					        }
					        indicatorGroup.removeAllViews();
							mInflater.inflate(R.layout.group_order, indicatorGroup, true);
							indicatorGroupId = -1;
							indicatorGroupHeight = 0;
					        elistview.setOnChildClickListener(MyOrder.this);
					        elistview.setOnGroupClickListener(MyOrder.this);
						}
					});
					}else {
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								CustomProgressDialog.stopProgressDialog();
								if(pageNum == 1){
								groups.clear();
								childs.clear();
								}
								if(adapter !=null)
								adapter.notifyDataSetChanged();
								swrefresh.setRefreshing(false);
								swrefresh.setDirection(SwipyRefreshLayoutDirection.TOP);
							}
						});
					}
				} catch (JSONException e) {
					CustomProgressDialog.stopProgressDialog();
					e.printStackTrace();
				}
				
				
			}
		}).start();
		
	}

	private void dataInit() 
	{
		int type = getIntent().getIntExtra(Tab3Fragment.WAIT_TYPE, Tab3Fragment.WAIT_ALL);
		if(type !=4){
			mRg.setVisibility(View.GONE);
		}

		switch (type)
		{
		case Tab3Fragment.WAIT_ALL:
			Log.e("lijinjin", "WAIT_ALL");
			statu = "";
			pageNum = 1;
			getOrderList();
			mRbAll.performClick();
			
			break;

		case Tab3Fragment.WAIT_COMMENT:	
			Log.e("lijinjin", "WAIT_COMMENT");
			mRbComment.performClick();
			
			break;

		case Tab3Fragment.WAIT_PAY:		
			Log.e("lijinjin", "WAIT_PAY");
			mRbPay.performClick();
			break;

		case Tab3Fragment.WAIT_RECEIVE:	
			Log.e("lijinjin", "WAIT_RECEIVE");
			mRbReceived.performClick();
			break;
		case Tab3Fragment.WAIT_fh:	
			Log.e("lijinjin", "fh");
			mFH.performClick();
			break;

		default:
			break;
		}
	}

	@ViewInject(R.id.order_group)
	private RadioGroup mRg;

	private void rgInit() 
	{
		mRg.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				// TODO Auto-generated method stub
				switch (checkedId)
				{
				case R.id.order_all:
					statu = "";
					pageNum = 1;
					getOrderList();
					break;

				case R.id.order_wait_pay:
					statu = "0";
					pageNum = 1;
					getOrderList();
					break;

				case R.id.order_wait_received:
					statu = "1";
					pageNum = 1;
					getOrderList();
					break;

				case R.id.order_wait_comment:
					statu = "2";
					pageNum = 1;
					getOrderList();
					break;
				case R.id.order_wait_fh:
					statu = "3";
					pageNum = 1;
					getOrderList();
					break;

				default:
					break;
				}
			}
		});
	}

	@ViewInject(R.id.order_listview)
	private ExpandableListView elistview;

	private MyBaseAdapter adapter;
	private void listviewInit() 
	{
		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		indicatorGroup = (FrameLayout) findViewById(R.id.topGroup);
		swrefresh.setDirection(SwipyRefreshLayoutDirection.BOTH);

		swrefresh.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh(SwipyRefreshLayoutDirection direction) {
				if(direction == SwipyRefreshLayoutDirection.TOP){
				pageNum = 1;
				}else pageNum ++;
				getOrderList();
			}
		});
		
//		myAdapter = new MyBaseAdapter_MyOrder(this, orderdata);
//		mLvOrder.setAdapter(myAdapter);
		/*mLvOrder.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) 
			{
				Bundle bundle = new Bundle();
				bundle.putString("ORDER_ID", orderdata.get(position).getId());
				openActivity(OrderDetailActivity.class,bundle);	
			}
		});*/
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

	private void actionbarInit()
	{
		// TODO Auto-generated method stub
		actionbar_ll_left.setVisibility(View.VISIBLE);
		actionbar_tv_back_name_left.setVisibility(View.GONE);
		// actionbar_tv_name_center.setVisibility(View.VISIBLE);

		actionbar_btn_right_left.setVisibility(View.GONE);
//		actionbar_imgbtn_right.setVisibility(View.VISIBLE);
		 actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText(getString(R.string.my_order));
		actionbar_btn_right_left.setText(getString(R.string.rule));

		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
		actionbar_imgbtn_right.setOnClickListener(clickListener_actionbar);
	}

	private OnClickListener clickListener_actionbar = new OnClickListener() 
	{
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			switch (v.getId())
			{
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
	
	class MyBaseAdapter extends BaseExpandableListAdapter{
		private int position;
		private Context context;
		 private LayoutInflater inflater;
		 public MyBaseAdapter(Context context){
			 this.context = context;
			 this.inflater = LayoutInflater.from(context);
			 
		 }

		@Override
		public Object getChild(int arg0, int arg1) {
			return childs.get(arg0).get(arg1);
		}

		@Override
		public long getChildId(int arg0, int arg1) {
			return arg1;
		}

		@Override
		public View getChildView(int arg0, int arg1, boolean arg2, View convertView,
				ViewGroup arg4) {
			 ChildHolder childHolder = null;
	            if (convertView == null) {
	                childHolder = new ChildHolder();
	                convertView = inflater.inflate(R.layout.shop_order_listview_item, null);

	                ViewUtils.inject(childHolder, convertView);

	                convertView.setTag(childHolder);
	            } else {
	                childHolder = (ChildHolder) convertView.getTag();
	            }
	            final HashMap<String,String> map = childs.get(arg0).get(arg1);
	            final HashMap<String,String> map1 = groups.get(arg0);
	            final String status = map1.get("status");
	            String name = map.get("goodsName");
	            final String price = map.get("price");
	            final String amount = map1.get("amount");
	            String num = map.get("num");
	            String url = map.get("goodsImage");
	            childHolder.tvGoodsName.setText(name);
	            childHolder.tvGoodsNum.setText("x "+num);
	            childHolder.tvGoodsPrice.setText("￥"+price);
	            if(status.equals("1")||status.equals("3")){
	            	childHolder.tv_tk.setVisibility(View.VISIBLE);
	            	childHolder.tv_tk.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							Intent intent = new Intent(context, ReturnGoodsActivity.class);
//							intent.putExtra("data", map);
							intent.putExtra("id", map1.get("id"));
							intent.putExtra("price", amount);
							startActivityForResult(intent, 101);
						}
					});
	            }else childHolder.tv_tk.setVisibility(View.INVISIBLE);
	            if(status.equals("2")){
	            	childHolder.tv_pj.setVisibility(View.VISIBLE);
	            	childHolder.tv_pj.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							Intent intent = new Intent(context, MyCommentActivity.class);
							intent.putExtra("data", map);
							intent.putExtra("ordersNo", map1.get("id"));
							startActivityForResult(intent, 101);
						}
					});
	            }
	            else childHolder.tv_pj.setVisibility(View.INVISIBLE);
	          
	            if(!TextUtils.isEmpty(url)){
	            	Picasso.with(context).load(url).centerCrop().resize(60, 60).into(childHolder.goods_img);
	            }
	            
			return convertView;
		}

		@Override
		public int getChildrenCount(int arg0) {
			return childs.get(arg0).size();
		}

		@Override
		public Object getGroup(int arg0) {
			return groups.get(arg0);
		}

		@Override
		public int getGroupCount() {
			return groups.size();
		}

		@Override
		public long getGroupId(int arg0) {
			return arg0;
		}

		@Override
		public View getGroupView(final int pos, boolean arg1, View convertView,
				ViewGroup arg3) {
			View v = null;
            if (convertView == null) {
                v = inflater.inflate(R.layout.group_order, null);
//                v.getLayoutParams().height = dp2px(30);
                convertView = v;
                
            } else {
            	v = convertView;
            }
//            {referrerPhone=18513497802, paymentMode=0, storeName=, status=0, silver=, list=[{"id":"5a740a0e50ff74920151001cb84d000d","goodsId":"2","num":1,"primeCost":126,"price":200,"isEvaluate":0,"goodsImage":"/upload/tea/2.jpg","goodsName":"红茶","ordersId":"5a740a0e50ff74920151001cb82e000c"}], ordersNo=111317110080501, msg=, id=5a740a0e50ff74920151001cb82e000c, amount=200, userId=5a740a0e50c8435a0150cccc6e26000b, expressNo=, gold=0, createDate=2015-11-13 17:11:00, isSilver=0, storeId=, expressId=5a740a0e50ebd7ab0150ec4f696e0007}
            TextView tv_storeName = (TextView) v.findViewById(R.id.tv_store_name);
            TextView tv_time = (TextView) v.findViewById(R.id.tv_time);
            TextView tv_pay = (TextView) v.findViewById(R.id.btn_pay);
            final HashMap<String,String> map = groups.get(pos);
            String storeName = map.get("storeName");
            final String ordersNo = map.get("id");
            String paymentMode = map.get("paymentMode");//支付方式
            final String amount = map.get("amount");
            String time = map.get("createDate");
            final String orderno = map.get("ordersNo");
            final String status = map.get("status");
            if(!TextUtils.isEmpty(storeName))
            tv_storeName.setText(storeName);
            if(!TextUtils.isEmpty(time))
            tv_time.setText(time);
            if(status.equals("0")){
            	tv_pay.setText("付款");
            }else if(status.equals("1")){
            	tv_pay.setText("确认收货");
            }else if(status.equals("3")){
            	tv_pay.setText("待发货");
            }else if(status.equals("2")){
            	tv_pay.setText("待评价");
            }else if(status.equals("4")) {
            	tv_pay.setText("已收货");
            }else if(status.equals("6")){
            	tv_pay.setText("退款中");
            }else if(status.equals("6")){
            	tv_pay.setText("已评价");
            }else{
            	tv_pay.setText("交易结束");
            }
            tv_storeName.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					position = pos;
					delete(ordersNo);
					
				}
			});
            tv_pay.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if(status.equals("0")){
						pay("茶", "茶信商城", amount, orderno, handler,1);
					}else if(status.equals("1")){//确认收货
						comform(ordersNo);
						
					}
					/*else if(status.equals("2")){
						Intent intent = new Intent(context, MyCommentActivity.class);
						intent.putExtra("data", map);
						intent.putExtra("ordersNo", map.get("id"));
						startActivityForResult(intent, 101);
					}*/
					
				}
			});
			return convertView;
		}
		
		protected void comform(String ordersNo) {
			CustomProgressDialog.startProgressDialog(context, "处理中，请稍后...");
			String userid = User.getInstance().getId();
			String token = User.getInstance().getToken();
			String url = Constant.METHOD_confirmOrder+"?userId="+userid+"&token="+token+"&orderId="+ordersNo;
			httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					CustomProgressDialog.stopProgressDialog();
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					CustomProgressDialog.stopProgressDialog();
					try {
						JSONObject obj = new JSONObject(arg0.result);
						String code = obj.getString("code");
						String msg = obj.getString("message");
						if(code.equals("1")){
//							list .remove(pos);
							/*groups.remove(position);
							childs.remove(childs.get(position));*/
//							notifyDataSetChanged();
							pageNum = 1;
							getOrderList();
						}else{
							Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
						}
						}catch (Exception e) {
						}
					
				}
			});
			
		}

		protected void delete(final String ordersNo) {
			final CustomDialog customDialog = new CustomDialog(context,true);
			customDialog.setDialogTitle("要删除该订单吗？");
			customDialog.setDialogContent("删除的订单将无法找回");
			customDialog.hideDialogEdittext();
			customDialog.setLeftBtnText("取消");
			customDialog.setRightBtnText("删除");
			customDialog.setLeftBtnListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					
					customDialog.dismiss();
				}
			});
			
			customDialog.setRightBtnListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					deleteOrders(ordersNo);
					
					customDialog.dismiss();
					
				}
			});
			
			customDialog.show();
			
		}
		/*private int dp2px(int dp)
		{
			return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
					getResources().getDisplayMetrics());
		}*/
		protected void deleteOrders(String ordersNo) {

			CustomProgressDialog.startProgressDialog(context, "正在删除，请稍后...");
			String userid = User.getInstance().getId();
			String token = User.getInstance().getToken();
			String url = Constant.METHOD_delete_orders+"?userId="+userid+"&token="+token+"&orderId="+ordersNo;
			httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					CustomProgressDialog.stopProgressDialog();
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					CustomProgressDialog.stopProgressDialog();
					try {
						JSONObject obj = new JSONObject(arg0.result);
						String code = obj.getString("code");
						String msg = obj.getString("message");
						if(code.equals("1")){
//							list .remove(pos);
							/*groups.remove(position);
							childs.remove(childs.get(position));*/
//							notifyDataSetChanged();
							pageNum = 1;
							getOrderList();
						}else{
							Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
						}
						}catch (Exception e) {
						}
					
				}
			});
			
		
			
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			return true;
		}
		
		public class ChildHolder 
		{	
			@ViewInject(R.id.iv_icon)
			private ImageView goods_img;
			@ViewInject(R.id.tv_goodname)
			private TextView tvGoodsName;
			@ViewInject(R.id.tv_price)
			private TextView tvGoodsPrice;
			@ViewInject(R.id.tv_num)
			private TextView tvGoodsNum;
			@ViewInject(R.id.tv_pj)
			private TextView tv_pj;
			@ViewInject(R.id.tv_tk)//退款
			private TextView tv_tk;

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

	@Override
	public boolean onGroupClick(ExpandableListView arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2,
			int arg3, long arg4) {
		Bundle bundle = new Bundle();
		bundle.putString("ORDER_ID", childs.get(arg2).get(arg3).get("ordersId"));
		bundle.putInt("pos", arg3);
		openActivity(OrderDetailActivity.class,bundle);	
		return false;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 102){
			getOrderList();
		}
	}

}
