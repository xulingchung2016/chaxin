package com.newbrain.chaxin.my.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alipay.sdk.pay.AliPayFunction;
import com.alipay.sdk.pay.PayResult;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.liren_app.ui.LiRenActivity;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.my.mycash.RechargeActivity;
import com.newbrain.customview.CustomDialog;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.swipeRefresh.SwipyRefreshLayout;
import com.newbrain.swipeRefresh.SwipyRefreshLayoutDirection;
import com.newbrain.swipeRefresh.SwipyRefreshLayout.OnRefreshListener;
import com.newbrain.user.User;
import com.newbrain.xutils.Xutils_HttpUtils;
import com.squareup.picasso.Picasso;

public class MyChaOrderActivity extends BaseActivity{
	private Context context;
	@ViewInject(R.id.swipyrefreshlayout)
	private SwipyRefreshLayout swrefresh;
	@ViewInject(R.id.order_listview)
	private ListView mLvOrder;
	private HttpUtils httpUtils;
	private List<HashMap<String,String>> datas = new ArrayList<HashMap<String,String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_my_cha_order);
		context = this;
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		ViewUtils.inject(this);
		actionbarInit();
		getData();
		listviewInit() ;
		
	}
	private BeautyAdapter adapter;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
		if(msg.what== AliPayFunction.SDK_PAY_FLAG)
		{
			PayResult payResult = new PayResult((String) msg.obj);
			
			// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
//			String resultInfo = payResult.getResult();
			
			String resultStatus = payResult.getResultStatus();

			// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
			if (TextUtils.equals(resultStatus, "9000")) 
			{
				showShortToast("支付成功");
				Log.e("lijinjin", "支付成功");
//				okService();
				getData();
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
		}
		};
	};
	
	private void listviewInit() 
	{
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
		
		adapter = new BeautyAdapter();
		mLvOrder.setAdapter(adapter);
		mLvOrder.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) 
			{
				/*Intent intent = new Intent(context, LiRenActivity.class);
				intent.putExtra("data", datas.get(position));
				startActivity(intent);*/
			}
		});
	}
	protected void okService() {
		String userid = User.getInstance().getId();
		String token = User.getInstance().getToken();
		String url = Constant.METHOD_completeService+"?userId="+userid+"&token="+token+"&orderId="+CompeleteId;
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				CustomProgressDialog.stopProgressDialog();
				handleNetworkError();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				CustomProgressDialog.stopProgressDialog();
				try {
					JSONObject obj = new JSONObject(arg0.result);
					String code = obj.getString("code");
					String msg = obj.getString("message");
					if(code.equals("1")){
						getData();
					}else{
						showShortToast(msg);
					}
					}catch (Exception e) {
					}
				
			}
		});
		
	}
	private int pageNo = 1;
	
	private final int pageSize = 20;
	
	private void getData() {
		CustomProgressDialog.startProgressDialog(context, "loading...");
		String userid = User.getInstance().getId();
		String token = User.getInstance().getToken();
		RequestParams params = new RequestParams();
		params.addBodyParameter("token", token);
		params.addBodyParameter("userId", userid);
		params.addBodyParameter("pageNo", pageNo+"");
		params.addBodyParameter("pageSize", pageSize+"");
//		String url =  Constant.METHOD_beauty_order+"?token="+token+"&u"
		httpUtils.send(HttpMethod.POST, Constant.METHOD_beauty_order, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				swrefresh.setRefreshing(false);
				handleNetworkError();
				pageNo--;
				CustomProgressDialog.stopProgressDialog();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				swrefresh.setRefreshing(false);
				CustomProgressDialog.stopProgressDialog();
				String result = arg0.result;
//				{"message":"查询成功","result":[{"beautyProfessional":null,"paymentMode":"1","beautyCertNo":"","beautyPhoneNo":"18617167404","status":0,"beautyImage":"http://218.244.138.142:8081/TeaMall/upload/2015/11/13/86cc467e873b47d2bc8330e6999d7942.png","beautyHighOpinion":null,"ordersNo":"111316211417998","msg":"","beautyLevel":null,"beautyName":"上杭NB","id":"5a740a0e50ff74920150ffef26b2000b","amount":150.0,"beautyId":"5a740a0e50ff74920150ffba8e1d0001","bookTime":"13:00","bookDate":"2015-11-13","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-13 16:21:14","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"1","beautyCertNo":"","beautyPhoneNo":"18686568656","status":0,"beautyImage":null,"beautyHighOpinion":null,"ordersNo":"1113155256-8412","msg":"","beautyLevel":null,"beautyName":"在乎别人说","id":"5a740a0e50ff74920150ffd53be10009","amount":222.0,"beautyId":"5a740a0e50f1eed90150f63875b90039","bookTime":"16:00","bookDate":"2015-11-13","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-13 15:52:56","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"0","beautyCertNo":"48051778","beautyPhoneNo":"18711214442","status":0,"beautyImage":"http://218.244.138.142:8081/TeaMall/upload/2015/11/11/2a8b4897107b49bbbec073887691bce1.png","beautyHighOpinion":null,"ordersNo":"1113152816-2054","msg":"","beautyLevel":null,"beautyName":"南国丽园美容","id":"5a740a0e50ff74920150ffbea7bd0007","amount":120.0,"beautyId":"5a740a0e50ece5d00150ef96af460011","bookTime":"13:00","bookDate":"2015-11-13","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-13 15:28:16","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"0","beautyCertNo":"","beautyPhoneNo":"18686568656","status":0,"beautyImage":null,"beautyHighOpinion":null,"ordersNo":"1112120527-1000","msg":"123","beautyLevel":null,"beautyName":"在乎别人说","id":"5a740a0e50f9cd6c0150f9de9e7c0005","amount":222.0,"beautyId":"5a740a0e50f1eed90150f63875b90039","bookTime":"16:00","bookDate":"2015-11-15","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-12 12:05:27","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"0","beautyCertNo":"48051778","beautyPhoneNo":"18711214442","status":0,"beautyImage":"http://218.244.138.142:8081/TeaMall/upload/2015/11/11/2a8b4897107b49bbbec073887691bce1.png","beautyHighOpinion":null,"ordersNo":"1111194553-2123","msg":"123","beautyLevel":null,"beautyName":"南国丽园美容","id":"5a740a0e50f1eed90150f65dcd2c003c","amount":120.0,"beautyId":"5a740a0e50ece5d00150ef96af460011","bookTime":"12:00","bookDate":"2015-11-12","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-11 19:45:53","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"}],"code":"1"}
				try {
					JSONObject obj = new JSONObject(result);
					String code = obj.getString("code");
					if(code.equals("1")){
						if(pageNo == 1)datas.clear();
						datas .addAll(JSON.parseObject(obj.getJSONArray("result").toString(), new TypeReference<List<HashMap<String,String>>>(){}));
						adapter.notifyDataSetChanged();
					}else swrefresh.setDirection(SwipyRefreshLayoutDirection.TOP);
					}catch (Exception e) {
						swrefresh.setDirection(SwipyRefreshLayoutDirection.TOP);
					}
			}
		});
		
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
		actionbar_tv_name_center.setText("我的茶丽人");
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
	private String CompeleteId;
	class BeautyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public Object getItem(int arg0) {
			return datas.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(final int pos, View view, ViewGroup arg2) {
			ViewHolder holder = null;
			if(view == null){
				view = LayoutInflater.from(context).inflate(R.layout.item_beauty_order, null);
				holder = new ViewHolder();
				holder.tv_fwtime = (TextView) view.findViewById(R.id.tv_fwtime);
				holder.tv_time = (TextView) view.findViewById(R.id.tv_time);
				holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
				holder.tv_price = (TextView) view.findViewById(R.id.tv_price);
				holder.tv_no = (TextView) view.findViewById(R.id.tv_no);
				holder.btn_delete = (Button) view.findViewById(R.id.btn_cancle);
				holder.btn_phone = (Button) view.findViewById(R.id.btn_phone);
				holder.btn_pay = (Button) view.findViewById(R.id.btn_pay);
				holder.iv_icon = (ImageView) view.findViewById(R.id.iv_picture);
				view.setTag(holder);
				
			}else {
				holder = (ViewHolder) view.getTag();
			}
//			{"message":"查询成功","result":[{"beautyProfessional":null,"paymentMode":"1",
//			"beautyCertNo":"","beautyPhoneNo":"18617167404","status":0,
//			"beautyImage":"http://218.244.138.142:8081/TeaMall/upload/2015/11/13/86cc467e873b47d2bc8330e6999d7942.png",
//			"beautyHighOpinion":null,"ordersNo":"111316211417998","msg":"","beautyLevel":null,
//			"beautyName":"上杭NB","id":"5a740a0e50ff74920150ffef26b2000b",
//			"amount":150.0,"beautyId":"5a740a0e50ff74920150ffba8e1d0001",
//			"bookTime":"13:00","bookDate":"2015-11-13","userId":"5a740a0e50c8435a0150cccc6e26000b",
//			"beautyAddress":"66777888","gold":0,"createDate":"2015-11-13 16:21:14","expressId":
//				"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"1",
//					"beautyCertNo":"","beautyPhoneNo":"18686568656","status":0,"beautyImage":null,
//					"beautyHighOpinion":null,"ordersNo":"1113155256-8412","msg":"","beautyLevel":null,"beautyName":"在乎别人说","id":"5a740a0e50ff74920150ffd53be10009","amount":222.0,"beautyId":"5a740a0e50f1eed90150f63875b90039","bookTime":"16:00","bookDate":"2015-11-13","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-13 15:52:56","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"0","beautyCertNo":"48051778","beautyPhoneNo":"18711214442","status":0,"beautyImage":"http://218.244.138.142:8081/TeaMall/upload/2015/11/11/2a8b4897107b49bbbec073887691bce1.png","beautyHighOpinion":null,"ordersNo":"1113152816-2054","msg":"","beautyLevel":null,"beautyName":"南国丽园美容","id":"5a740a0e50ff74920150ffbea7bd0007","amount":120.0,"beautyId":"5a740a0e50ece5d00150ef96af460011","bookTime":"13:00","bookDate":"2015-11-13","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-13 15:28:16","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"0","beautyCertNo":"","beautyPhoneNo":"18686568656","status":0,"beautyImage":null,"beautyHighOpinion":null,"ordersNo":"1112120527-1000","msg":"123","beautyLevel":null,"beautyName":"在乎别人说","id":"5a740a0e50f9cd6c0150f9de9e7c0005","amount":222.0,"beautyId":"5a740a0e50f1eed90150f63875b90039","bookTime":"16:00","bookDate":"2015-11-15","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-12 12:05:27","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"0","beautyCertNo":"48051778","beautyPhoneNo":"18711214442","status":0,"beautyImage":"http://218.244.138.142:8081/TeaMall/upload/2015/11/11/2a8b4897107b49bbbec073887691bce1.png","beautyHighOpinion":null,"ordersNo":"1111194553-2123","msg":"123","beautyLevel":null,"beautyName":"南国丽园美容","id":"5a740a0e50f1eed90150f65dcd2c003c","amount":120.0,"beautyId":"5a740a0e50ece5d00150ef96af460011","bookTime":"12:00","bookDate":"2015-11-12","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-11 19:45:53","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"}],"code":"1"}
			final HashMap<String,String> map = datas.get(pos);
			String name = map.get("beautyName");
			final String price = map.get("amount");
			String time = map.get("createDate");
			String fwtime = map.get("bookDate")+" "+map.get("bookTime");
			String no = map.get("beautyAddress");
			final String status = map.get("status");
			final String  id = map.get("id");
			String url = map.get("beautyImage");
			final String orderno = map.get("ordersNo");
			final String phone = map.get("beautyPhoneNo");
			holder.tv_fwtime.setText("服务时间："+fwtime);
			holder.tv_name.setText(name);
			holder.tv_no.setText(no);
			holder.tv_time.setText(time);
			holder.tv_price.setText("￥"+price);
			if(!TextUtils.isEmpty(url)){
				Picasso.with(context).load(url).centerCrop().resize(120, 120).into(holder.iv_icon);
			}else Picasso.with(context).load(R.drawable.cha).centerCrop().resize(120, 120).into(holder.iv_icon);
			String statusValue = "立即付款";
			if(status.equals("0")){
				statusValue = "立即付款";
			}else if(status.equals("1")){
				statusValue = "立即评价";
			}else if(status.equals("2")){
				statusValue = "完成服务";
			}else if(status.equals("3")){
				statusValue = "交易完成";
			}
			holder.btn_pay.setText(statusValue);
			holder.btn_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					delete(orderno,pos);
				}
			});
			
			holder.btn_pay.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					CompeleteId = id;
					if(status.equals("0"))
					pay("服务", "暂无", price, orderno, handler,2);
					else if(status.equals("2")){
						okService();
					}else if(status.equals("1")){
						Intent intent = new Intent(context, MyCommentActivity.class);
						intent.putExtra("data", map);
						intent.putExtra("flag", 1);
						startActivityForResult(intent, 101);
					}
				}
			});
			
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
		/**
		 * 订单评价
		 */
		protected void comment() {
			CustomProgressDialog.startProgressDialog(context, "正在提交您的评价...");
			String userid = User.getInstance().getId();
			String token = User.getInstance().getToken();
			RequestParams params = new RequestParams();
			params.addBodyParameter("token", token);
			params.addBodyParameter("userId", userid);
			params.addBodyParameter("pageNo", pageNo+"");
			params.addBodyParameter("pageSize", pageSize+"");
//			String url = Constant.METHOD_hideTeaBeautyOrder+"?userId="+userid+"&token="+token+"&ordersNo="+orderno;
			httpUtils.send(HttpMethod.POST,Constant.METHOD_saveEvaluation ,params, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					CustomProgressDialog.stopProgressDialog();
					handleNetworkError();
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					CustomProgressDialog.stopProgressDialog();
					try {
						JSONObject obj = new JSONObject(arg0.result);
						String code = obj.getString("code");
						String msg = obj.getString("message");
						if(code.equals("1")){
						getData();
						}else{
							showShortToast(msg);
						}
						}catch (Exception e) {
						}
					
				}
			});
		}

		/**
		 * 删除茶丽人订单
		 */
		protected void delete(String orderno,final int pos) {
			CustomProgressDialog.startProgressDialog(context, "正在删除，请稍后...");
			String userid = User.getInstance().getId();
			String token = User.getInstance().getToken();
			String url = Constant.METHOD_hideTeaBeautyOrder+"?userId="+userid+"&token="+token+"&ordersNo="+orderno;
			httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					CustomProgressDialog.stopProgressDialog();
					handleNetworkError();
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					CustomProgressDialog.stopProgressDialog();
					try {
						JSONObject obj = new JSONObject(arg0.result);
						String code = obj.getString("code");
						String msg = obj.getString("message");
						if(code.equals("1")){
							datas .remove(pos);
							adapter.notifyDataSetChanged();
						}else{
							showShortToast(msg);
						}
						}catch (Exception e) {
						}
					
				}
			});
			
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
		Button btn_pay;
		
		
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 102){
			getData();
		}
	}

}
