package com.newbrain.chaxin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.adapter.MyBaseAdapter_ShopCar;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.BaseJsonDataBean;
import com.newbrain.model.Bean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.ShopCartGoodsListBean;
import com.newbrain.model.ShopCartGoodsListBean.ShopCartGoodDetail;
import com.newbrain.user.User;
import com.newbrain.utils.ToastUtil;
import com.newbrain.xutils.Xutils_HttpUtils;

public class Tab2Fragment extends Fragment
{
	private Context context;
	HttpUtils httpUtils;
	
	private JsonThread mThread;
	public int remove_index;
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub		
		View view = inflater.inflate(R.layout.tab2_fragment, null);
		Log.e("lijinjin", "-------------- onCreateView");
		return view;
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
	
	
	@ViewInject(R.id.tab2_swipw_menu_listView)
	private SwipeMenuListView mSwipeMenuListView;
	
	
	private MyBaseAdapter_ShopCar mMyAdapter_ShopCar;
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
	
	}

	private void viewInit() 
	{
		// TODO Auto-generated method stub
		swipeListviewInit();	
	}
	
	private Handler mHandler = new Handler() 
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
					ShopCartGoodsListBean baseJsonDataBean = (ShopCartGoodsListBean) reData.getObg();
					if (baseJsonDataBean.getCode().equals("1"))
					{
						Log.e("lijinjin", "数据不为空");
						ShopCarData.clear();
						result = baseJsonDataBean.getResult();
						int size = result.size();
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
	};
	
	public void setList()
	{		
		List<Bean> list = new ArrayList<Bean>();
		if(mThread != null)
		{
			mThread.cancleReturnData();
		}
		
		mThread = new JsonThread(context, list, mHandler, Constant.FLAG_GET_SHOPCART_LIST);
		mThread.start();
	}
	
	public void removeShopCart(String ID)
	{		
		List<Bean> list = new ArrayList<Bean>();
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
		mThread.start();
	}
	
	private void swipeListviewInit() 
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
	}
	
	private int dp2px(int dp)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
	
	
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
}
