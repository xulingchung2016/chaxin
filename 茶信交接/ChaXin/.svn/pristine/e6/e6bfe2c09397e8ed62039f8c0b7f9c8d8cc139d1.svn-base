package com.newbrain.chaxin.my.mybeauty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomDialog;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.swipeRefresh.SwipyRefreshLayout;
import com.newbrain.swipeRefresh.SwipyRefreshLayoutDirection;
import com.newbrain.swipeRefresh.SwipyRefreshLayout.OnRefreshListener;
import com.newbrain.user.User;
import com.newbrain.xutils.Xutils_HttpUtils;
import com.squareup.picasso.Picasso;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyBeautyYuyueFragment extends Fragment {


	
	private Context context;
	private HttpUtils httpUtils;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.beauty_collect_fragment, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		ViewUtils.inject(this,view);
		context = getActivity();
		
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		listviewInit();
	}
		@ViewInject(R.id.beauty_listview)
		private ListView mLv;
		@ViewInject(R.id.swipyrefreshlayout)
		private SwipyRefreshLayout swrefresh;
		private BeautyAdapter myAdapter;

		private List<HashMap<String, String>> mList;
		private int pos;
		private void listviewInit() {

			mList = new ArrayList<HashMap<String, String>>();
			swrefresh.setDirection(SwipyRefreshLayoutDirection.BOTH);

			swrefresh.setOnRefreshListener(new OnRefreshListener() {
				
				@Override
				public void onRefresh(SwipyRefreshLayoutDirection direction) {
					if(direction == SwipyRefreshLayoutDirection.TOP){
					pageNo = 1;
					}else pageNo ++;
					getData();
				}
			});
			myAdapter = new BeautyAdapter();
		/*	SwipeMenuCreator creator = new SwipeMenuCreator() 
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
					item.setTitleSize(dp2px(8));
					
					menu.addMenuItem(item);
				}
			};*/

//			mLv.setMenuCreator(creator);
			/*mLv.setOnMenuItemClickListener(new OnMenuItemClickListener()
			{
				@Override
				public void onMenuItemClick(int arg0, SwipeMenu arg1, int arg2) 
				{
					String ID = mList.get(arg0).get("id");
					removeShopCart(ID);
					pos = arg0;
				}
			});*/
			mLv.setAdapter(myAdapter);
			
			getData();

		}
	
		/**
		 * 删除
		 * @param iD
		 */
		/*protected void removeShopCart(String iD) {
			// TODO Auto-generated method stub
			
		}

		private int dp2px(int dp)
		{
			return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
					getResources().getDisplayMetrics());
		}*/
		private int pageNo =1,pageSize = 20;
		private void getData() {

			CustomProgressDialog.startProgressDialog(context, "loading...");
			String userid = User.getInstance().getId();
			String token = User.getInstance().getToken();
			/*RequestParams params = new RequestParams();
			params.addBodyParameter("token", token);
			params.addBodyParameter("userId", userid);
			params.addBodyParameter("type", "0");
			params.addBodyParameter("pageNo", pageNo+"");
			params.addBodyParameter("pageSize", pageSize+"");*/
			String url =  Constant.METHOD_getBeautyMyOrders+"?userId="+userid+"&token="+token+"&type=1&pageNo="+pageNo+"&pageSize="+pageSize;
			httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					swrefresh.setRefreshing(false);
					CustomProgressDialog.stopProgressDialog();
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					swrefresh.setRefreshing(false);
					CustomProgressDialog.stopProgressDialog();
					String result = arg0.result;
//					{"message":"查询成功","result":[{"paymentMode":"1","status":0,"ordersNo":"1113155256-8412","guestNickName":"特殊","msg":"","guestImage":"","id":"5a740a0e50ff74920150ffd53be10009","amount":222.0,"guestSex":1,"bookTime":"16:00","bookDate":"2015-11-13","guestPhoneNo":"18682419618","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-13 15:52:56","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"paymentMode":"0","status":0,"ordersNo":"1112120527-1000","guestNickName":"特殊","msg":"123","guestImage":"","id":"5a740a0e50f9cd6c0150f9de9e7c0005","amount":222.0,"guestSex":1,"bookTime":"16:00","bookDate":"2015-11-15","guestPhoneNo":"18682419618","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-12 12:05:27","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"}],"code":"1"}
					try {
						JSONObject obj = new JSONObject(result);
						String code = obj.getString("code");
						if(code.equals("1")){
							if(pageNo == 1)mList.clear();
							mList .addAll(JSON.parseObject(obj.getJSONArray("result").toString(), new TypeReference<List<HashMap<String,String>>>(){}));
							
						}else swrefresh.setDirection(SwipyRefreshLayoutDirection.TOP);
						myAdapter.notifyDataSetChanged();
						}catch (Exception e) {
						}
				}
			});
			
		
			
		}
		
		class BeautyAdapter extends BaseAdapter{

			@Override
			public int getCount() {
				return mList.size();
			}

			@Override
			public Object getItem(int arg0) {
				return mList.get(arg0);
			}

			@Override
			public long getItemId(int arg0) {
				return arg0;
			}

			@Override
			public View getView(int arg0, View view, ViewGroup arg2) {
				ViewHolder holder = null;
				if(view == null){
					view = LayoutInflater.from(context).inflate(R.layout.item_beauty_my_order, null);
					holder = new ViewHolder();
					holder.tv_fwtime = (TextView) view.findViewById(R.id.tv_fwtime);
					holder.tv_time = (TextView) view.findViewById(R.id.tv_time);
					holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
					holder.tv_price = (TextView) view.findViewById(R.id.tv_price);
					holder.tv_no = (TextView) view.findViewById(R.id.tv_no);
					holder.btn_delete = (Button) view.findViewById(R.id.btn_cancle);
					holder.btn_phone = (Button) view.findViewById(R.id.btn_phone);
					holder.btn_status = (Button) view.findViewById(R.id.btn_status);
					holder.iv_icon = (ImageView) view.findViewById(R.id.iv_picture);
					view.setTag(holder);
					
				}else {
					holder = (ViewHolder) view.getTag();
				}
//				{"message":"查询成功","result":[{"beautyProfessional":null,"paymentMode":"1",
//				"beautyCertNo":"","beautyPhoneNo":"18617167404","status":0,
//				"beautyImage":"http://218.244.138.142:8081/TeaMall/upload/2015/11/13/86cc467e873b47d2bc8330e6999d7942.png",
//				"beautyHighOpinion":null,"ordersNo":"111316211417998","msg":"","beautyLevel":null,
//				"beautyName":"上杭NB","id":"5a740a0e50ff74920150ffef26b2000b",
//				"amount":150.0,"beautyId":"5a740a0e50ff74920150ffba8e1d0001",
//				"bookTime":"13:00","bookDate":"2015-11-13","userId":"5a740a0e50c8435a0150cccc6e26000b",
//				"beautyAddress":"66777888","gold":0,"createDate":"2015-11-13 16:21:14","expressId":
//					"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"1",
//						"beautyCertNo":"","beautyPhoneNo":"18686568656","status":0,"beautyImage":null,
//						"beautyHighOpinion":null,"ordersNo":"1113155256-8412","msg":"","beautyLevel":null,"beautyName":"在乎别人说","id":"5a740a0e50ff74920150ffd53be10009","amount":222.0,"beautyId":"5a740a0e50f1eed90150f63875b90039","bookTime":"16:00","bookDate":"2015-11-13","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-13 15:52:56","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"0","beautyCertNo":"48051778","beautyPhoneNo":"18711214442","status":0,"beautyImage":"http://218.244.138.142:8081/TeaMall/upload/2015/11/11/2a8b4897107b49bbbec073887691bce1.png","beautyHighOpinion":null,"ordersNo":"1113152816-2054","msg":"","beautyLevel":null,"beautyName":"南国丽园美容","id":"5a740a0e50ff74920150ffbea7bd0007","amount":120.0,"beautyId":"5a740a0e50ece5d00150ef96af460011","bookTime":"13:00","bookDate":"2015-11-13","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-13 15:28:16","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"0","beautyCertNo":"","beautyPhoneNo":"18686568656","status":0,"beautyImage":null,"beautyHighOpinion":null,"ordersNo":"1112120527-1000","msg":"123","beautyLevel":null,"beautyName":"在乎别人说","id":"5a740a0e50f9cd6c0150f9de9e7c0005","amount":222.0,"beautyId":"5a740a0e50f1eed90150f63875b90039","bookTime":"16:00","bookDate":"2015-11-15","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-12 12:05:27","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"0","beautyCertNo":"48051778","beautyPhoneNo":"18711214442","status":0,"beautyImage":"http://218.244.138.142:8081/TeaMall/upload/2015/11/11/2a8b4897107b49bbbec073887691bce1.png","beautyHighOpinion":null,"ordersNo":"1111194553-2123","msg":"123","beautyLevel":null,"beautyName":"南国丽园美容","id":"5a740a0e50f1eed90150f65dcd2c003c","amount":120.0,"beautyId":"5a740a0e50ece5d00150ef96af460011","bookTime":"12:00","bookDate":"2015-11-12","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-11 19:45:53","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"}],"code":"1"}
//				paymentMode=1, status=0, ordersNo=1113155256-8412, guestNickName=特殊, msg=, guestImage=, id=5a740a0e50ff74920150ffd53be10009, amount=222, guestSex=1, bookTime=16:00, bookDate=2015-11-13, guestPhoneNo=18682419618, userId=5a740a0e50c8435a0150cccc6e26000b, beautyAddress=66777888, gold=0, createDate=2015-11-13 15:52:56, expressId=5a740a0e50ebd7ab0150ec50d56f0008}
				HashMap<String,String> map = mList.get(arg0);
				String name = map.get("guestNickName");
				final String price = map.get("amount");
				String time = map.get("createDate");
				String fwtime = map.get("bookDate")+" "+map.get("bookTime");
				String no = map.get("beautyAddress");
				String url = map.get("guestImage");
				String status = map.get("status");
//				final String orderno = map.get("ordersNo");
				final String phone = map.get("guestPhoneNo");
				holder.tv_fwtime.setText("服务时间："+fwtime);
				holder.tv_name.setText(name);
				holder.tv_no.setText("地址："+no);
				holder.tv_time.setText(time);
				holder.tv_price.setText("￥"+price);
				if(!TextUtils.isEmpty(url)){
					Picasso.with(context).load(url).centerCrop().resize(120, 120).into(holder.iv_icon);
				}
				if(status.equals("0")){ //订单状态，0：待付款，1：待评价，2：已付款，3：已评价

					holder.btn_status.setText("待付款");
				}else if("1".equals(status)){
					holder.btn_status.setText("待评价");
				}else if("2".equals(status)){
					holder.btn_status.setText("已付款");
				}else if("3".equals(status)){
					holder.btn_status.setText("已评价");
				}
				holder.btn_delete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						
						
					}
				});
				
				/*holder.btn_pay.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
//						pay("服务", "暂无", price, orderno, handler);
					}
				});*/
				
				holder.btn_phone.setOnClickListener(new OnClickListener() {
		
				@Override
				public void onClick(View arg0) {
					final CustomDialog customDialog = new CustomDialog(context,true);
					customDialog.setDialogTitle("要拨打"+phone);
					customDialog.hideDialogContent();
					customDialog.hideDialogEdittext();
					customDialog.setLeftBtnText("取消");
					customDialog.setRightBtnText("确定");
					customDialog.setLeftBtnListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							
							customDialog.dismiss();
						}
					});
					
					customDialog.setRightBtnListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
			                //通知activtity处理传入的call服务
							startActivity(intent);
							customDialog.dismiss();
							
						}
					});
					
					customDialog.show();
					

				}
			});
				
				return view;
			}
			
			class ViewHolder {
			ImageView iv_icon;
			TextView tv_name;
			TextView tv_time;
			TextView tv_price;
			TextView tv_fwtime;
			TextView tv_no;
			Button btn_delete;
			Button btn_phone;
			Button btn_status;
			
			
			}
		}
}
