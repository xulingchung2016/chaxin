package com.newbrain.adapter;


import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.adapter.MyBaseAdapter_TeaMallGoodsList.MyOnClickListen;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.my.order.ComplaintActivity;
import com.newbrain.chaxin.my.order.MyOrder;
import com.newbrain.chaxin.my.order.OrderDetailActivity;
import com.newbrain.customview.CustomDialog;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.model.MyOrderListBean.MyOrderList;
import com.newbrain.user.User;
import com.newbrain.xutils.Xutils_BitmapUtils;
import com.newbrain.xutils.Xutils_HttpUtils;

public class MyBaseAdapter_MyOrder  extends BaseAdapter
{
	private MyOrder context;
	private LayoutInflater inflater;
	private List<MyOrderList> list;
	private int pos;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
		};
	};
	public  MyBaseAdapter_MyOrder(MyOrder context,List<MyOrderList> list)
	{
		this.context = context;
		this.list = list;
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		inflater = LayoutInflater.from(this.context);		
	}
	
	public List<MyOrderList> getList() 
	{
		return list;
	}

	public void setList(List<MyOrderList> list)
	{
		this.list = list;
		notifyDataSetChanged();
	}


	@Override
	public int getCount() 
	{
		return list.size();
	}

	@Override
	public Object getItem(int position)
	{
		return list.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.my_order_listview_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			convertView.setTag(viewHolder);			
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		String status = list.get(position).getStatus();
//		String name = 	list.get(position).getChildOrderBean().get(0).getGoodsName();
//		Log.i("tag", "tstsss>>>>>>>>>>>>>>>>>"+name);
		MyOnClickListen myOnClickListen = new MyOnClickListen(position,status);
		convertView.setOnClickListener(myOnClickListen);
		final String orderid = list.get(position).getOrdersNo();
		viewHolder.tv_goods_price.setText(list.get(position).getAmount());
		viewHolder.tv_goods_detail_name.setText(list.get(position).getGoodsName());
		viewHolder.tv_shop_name.setText(list.get(position).getStoreName());
		viewHolder.btn_goods_num.setText(list.get(position).getNum());
		String image = list.get(position).getGoodsImage();
		viewHolder.tv_delete_order_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				pos = position;
				delete(orderid);
			}
		});
		if(!TextUtils.isEmpty(image))
		{
			Xutils_BitmapUtils.getbitmapUtils_detail(context).display(viewHolder.tv_goods_detail_picture, Constant.TEAMALL_IMAGE_BASE_URL + image);
		}
		
		if(status.equals("0"))
		{
			viewHolder.tv_order_status.setText("待付款");
			viewHolder.btn_action_order.setText("付款");
		}
		else if(status.equals("1"))
		{
			viewHolder.tv_order_status.setText("待收货");
			viewHolder.btn_action_order.setText("确认收货");
		}
		else if(status.equals("3"))
		{
			viewHolder.tv_order_status.setText("已经付款");
			viewHolder.btn_action_order.setText("确认收货");
		}
		else if(status.equals("4"))
		{
			viewHolder.tv_order_status.setText("交易成功");
			viewHolder.btn_action_order.setText("评论");
		}
		else 
		{
			viewHolder.tv_order_status.setText("交易成功");
			viewHolder.btn_action_order.setText("评论");
		}
		
		viewHolder.btn_action_order.setOnClickListener(myOnClickListen);
			
		return convertView;
	}
	
	/**
	 * 删除订单
	 */
	protected void delete(final String orderid) {
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
				deleteOrders(orderid);
				
				customDialog.dismiss();
				
			}
		});
		
		customDialog.show();
		
	}
	private HttpUtils httpUtils;

	protected void deleteOrders(String orderno) {
		CustomProgressDialog.startProgressDialog(context, "正在删除，请稍后...");
		String userid = User.getInstance().getId();
		String token = User.getInstance().getToken();
		String url = Constant.METHOD_delete_orders+"?userId="+userid+"&token="+token+"&orderId="+orderno;
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
						list .remove(pos);
						notifyDataSetChanged();
					}else{
						Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
					}
					}catch (Exception e) {
					}
				
			}
		});
		
	}


	public class MyOnClickListen implements OnClickListener
	{		
		private int position;
		private String status;
	
		public MyOnClickListen()
		{
			super();
			// TODO Auto-generated constructor stub
		}


		public MyOnClickListen(int position,String status) 
		{
			super();
			this.position = position;
			this.status = status;
		}

		@Override
		public void onClick(View v) 
		{
			switch (v.getId()) 
			{
			case R.id.btn_action_orders:
				if(status.equals("0")){
					context.pay("茶叶", "茶信商城",list.get(position).getAmount() , list.get(position).getOrdersNo(), handler,1);
					
				}
//					context.startActivity(new Intent(context, OrderDetailActivity.class).putExtra("ORDER_ID", list.get(position).getId()));
				else
				context.startActivity(new Intent(context, ComplaintActivity.class));		
				break;

			default:
				if(status.equals("0"))
				context.startActivity(new Intent(context, OrderDetailActivity.class).putExtra("ORDER_ID", list.get(position).getId()));
				break;
			}
		}
	}
	
	
	public class ViewHolder
	{
		@ViewInject(R.id.TeaMall_listview_order_title_shop_name)
		private TextView tv_shop_name;
		
		@ViewInject(R.id.TeaMall_listview_order_title_order_status)
		private TextView tv_order_status;
		
		@ViewInject(R.id.gooddetail_image_listview_order_title_order_deleteimg)
		private ImageView tv_delete_order_image;
		
		@ViewInject(R.id.order_listview_item_order_detail_picture)
		private ImageView tv_goods_detail_picture;
		
		@ViewInject(R.id.TeaMall_listview_item_order_detail_name)
		private TextView tv_goods_detail_name;
		
		@ViewInject(R.id.TeaMall_listview_item_order_detail_price)
		private TextView tv_goods_price;
		
		@ViewInject(R.id.TeaMall_listview_item_order_detail_num)
		private TextView btn_goods_num;
		
		@ViewInject(R.id.TeaMall_listview_item_goods_sellCount)
		private TextView btn_goods_count;
		
		@ViewInject(R.id.btn_action_orders)
		private Button btn_action_order;
	}

}
