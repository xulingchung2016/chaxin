package com.newbrain.chaxin.my.order;

import java.util.concurrent.CompletionService;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.my.mycollect.chakanWuliuActivity;
import com.newbrain.chaxin.teamall.DetailActivity;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.utils.ButtonColorFilter;
import com.newbrain.utils.SharedPreferencesHelp;
import com.newbrain.xutils.Xutils_HttpUtils;

public class OrderDetailActivity extends BaseActivity 
{
	
	private Context context;
	private String order_id;
	private HttpUtils httpUtils;
	private String userId,token;
	private int pos;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_detail_activity);
		ViewUtils.inject(this);
		context = this;
		userId = SharedPreferencesHelp.getString(context, "id");
		token = SharedPreferencesHelp.getString(context, "token");
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		actionbarInit();
		order_id = getIntent().getStringExtra("ORDER_ID");
		pos = getIntent().getIntExtra("pos", 0);
		getDetails();
		
//		ButtonColorFilter.setButtonFocusChanged(btn_kf);
//		ButtonColorFilter.setButtonFocusChanged(btn_ts);
		
		
	}
	
	private void getDetails() {
		CustomProgressDialog.startProgressDialog(context, "loading");
		/*RequestParams params = new RequestParams();
		params.addBodyParameter("userId", userId);
		params.addBodyParameter("token", token);
		params.addBodyParameter("id", order_id);*/
		String url =  Constant.METHOD_order_detail+"?userId="+userId+"&token="+token+"&id="+order_id;
//		String url = Constant.METHOD_USER_GOODSDETAIL+order_id;
		httpUtils.send(HttpMethod.GET,url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				CustomProgressDialog.stopProgressDialog();
				handleNetworkError();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				CustomProgressDialog.stopProgressDialog();
//				{"message":"数据不为空","result":{"createTime":"2015-11-13 17:11:00","referrerPhone":"18513497802",
//				"paymentMode":"0","storeName":"","status":0,"list":[{"id":"2","goodsId":"2","num":1,
//				"primeCost":126.0,"price":200.0,"isEvaluate":0,"goodsImage":"/upload/tea/2.jpg","goodsName":"红茶",
//				"ordersId":"5a740a0e50ff74920151001cb82e000c"}],"ordersNo":"111317110080501","msg":"",
//				"phoneNo":"18682419618","id":"5a740a0e50ff74920151001cb82e000c","amount":200.0,"linkMan":
//					"晕乎乎","address":"寄","userId":"5a740a0e50c8435a0150cccc6e26000b"
//				,"expressNo":"","gold":0,"storeId":"","expressId":"5a740a0e50ebd7ab0150ec4f696e0007"},"code":"1"}
//				{"message":"数据不为空","result":{"total":1000.0,"primeCost":"","weight":0.0,"memo":"","teaTypeName":"绿茶","buyingNum":0.0,"storageMethod":"冷藏","brandId":"1","brandName":"福建安溪","homebred":true,"teaTypeId":"1","id":"18","time":"","goodsScore":0.0,"packaging":"袋装","isTimeLimited":false,"salesTotal":100,"evalToal":200,"serviceScore":0.0,"storeName":"","buyingPrice":0.0,"image":"","procucingArea":"福建","shelfLife":"","price":100.0,"address":"金銮时代大厦","storeId":"1","productionCertificate":"","expressScore":0.0,"goodsName":"铁观音"},"images":[{"sequence":1,"image":"http://218.244.138.142:8081/TeaMall/upload/b0c237768cb048eaa76a041ec4045b4f.jpg"},{"sequence":0,"image":"http://218.244.138.142:8081/TeaMall/upload/ce940281bda741afb108431d6bd34b56.jpg"},{"sequence":0,"image":"http://218.244.138.142:8081/TeaMall/upload/86d05dd05b9940e18d627b3ed526426a.jpg"}],"code":"1"}
				String result = arg0.result;
				try {
					JSONObject objj = new JSONObject(result);
					String code = objj.getString("code");
					String msg = objj.getString("message");
					if(code.equals("1")){
						setData(objj.getJSONObject("result"));
					}else showShortToast(msg);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	String qqNo = "";
	private String goodsId;
	protected void setData(JSONObject jsonObject) {
		
		try {
			String orderNo = jsonObject.getString("ordersNo");
			String lxr = jsonObject.getString("linkMan");
			String lxrphone = jsonObject.getString("phoneNo");
			String addresss = jsonObject.getString("address");
			String kddno = jsonObject.getString("expressNo");//快递单号
			String time = jsonObject.getString("createTime");//
			String status = jsonObject.getString("status");
			qqNo = jsonObject.getString("qqNo");
			if(TextUtils.isEmpty(qqNo))qqNo= "111111";
			tv_order_no.setText(orderNo);
			 if(status.equals("0")){
	            	tv_order_state.setText("待付款");
	            }else if(status.equals("1")){
	            	tv_order_state.setText("待收货");
	            }else if(status.equals("3")){
	            	tv_order_state.setText("待发货");
	            }else if(status.equals("2")){
	            	tv_order_state.setText("待评论");
	            }else if(status.equals("4")){
	            	tv_order_state.setText("已收货");
	            }else if(status.equals("6")){
	            	tv_order_state.setText("退款中");
	            } else if(status.equals("6")){
	            	tv_order_state.setText("已评价");
	            }else{
	            	tv_order_state.setText("交易结束");
	            }
			 String storeName = jsonObject.getString("storeName");
			tv_name.setText("收货人："+lxr);
			tv_name.setText(lxrphone);
			tv_address.setText("地址："+addresss);
			tv_kddh.setText(kddno);
			tv_time.setText(time);
			tv_shop_name.setText(storeName);
			JSONObject obj = jsonObject.getJSONArray("list").getJSONObject(pos);
			if(obj !=null){
			String goodname = obj.getString("goodsName");//
			String price = obj.getString("price");//storeName
			goodsId = obj.getString("goodsId");
			String num = obj.getString("num");
			tv_num.setText("x "+num );
			tv_goods_name.setText(goodname);
			tv_price.setText("￥"+price);
			double allMone = Integer.parseInt(num)*Double.parseDouble(price); 
			tv_all_money.setText("￥"+allMone);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/** 评价按钮 */
	@ViewInject(R.id.order_detail__comfirm)
	private Button mBtnComment;
//	@ViewInject(R.id.order_listview_item_btn_comfirm)
//	private Button btn_kf;
	/*@ViewInject(R.id.order_detail_complain)
	private Button btn_ts;*/

	@OnClick({ R.id.order_detail__comfirm,R.id.rl_center,R.id.order_listview_item_btn_comfirm,R.id.order_detail_complain })
	private void onClick_Comment(View view) {
		
		
		switch (view.getId()) {

		case R.id.order_detail__comfirm:
			
			openActivity(chakanWuliuActivity.class);

			break;
		case R.id.order_detail_complain :
			Bundle bundle = new Bundle();
			bundle.putString("id", order_id);
			openActivity(ComplaintActivity.class,bundle);
			break;
		case R.id.rl_center:
			SharedPreferences sp = context.getSharedPreferences("CHAXIN_CONFIG", context.MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putString("goodid_for_detail", goodsId);
			editor.putBoolean("isShangpu", false);
			editor.commit();
			context.startActivity(new Intent(context, DetailActivity.class));			
			break;
		case R.id.order_listview_item_btn_comfirm://
			if(!TextUtils.isEmpty(qqNo)){
			String url11 = "mqqwpa://im/chat?chat_type=wpa&uin="+qqNo+"&version=1";
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url11)));
			}else{
				showShortToast("商户未留下qq!");
			}
			break;

		}

	}

	@ViewInject(R.id.order_detail_name)
	private TextView tv_name;//收货人
	@ViewInject(R.id.order_detail_phone)
	private TextView tv_phone;//电话
	@ViewInject(R.id.order_detail_address)
	private TextView tv_address;//地址
	@ViewInject(R.id.order_detail_order_num)
	private TextView tv_order_no;//地址
	@ViewInject(R.id.order_detail_courier_num)
	private TextView tv_kddh;//地址
	@ViewInject(R.id.order_detail_time)
	private TextView tv_time;//地址
	
	@ViewInject(R.id.order_listview_item_shop_name)
	private TextView tv_shop_name;//地址
	@ViewInject(R.id.order_listview_item_goods_name)
	private TextView tv_goods_name;//地址
	@ViewInject(R.id.order_listview_item_goods_picture)
	private ImageView iv_icon;//地址
	@ViewInject(R.id.order_listview_item_goods_price)
	private TextView tv_price;//地址
	@ViewInject(R.id.order_listview_item_goods_num)
	private TextView tv_num;//地址
	
	
	@ViewInject(R.id.order_listview_item_order_state)
	private TextView tv_order_state;//地址
	@ViewInject(R.id.order_detail_money)
	private TextView tv_all_money;//地址
	
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

	private void actionbarInit() {
		// TODO Auto-generated method stub

		actionbar_ll_left.setVisibility(View.VISIBLE);
		actionbar_tv_back_name_left.setVisibility(View.GONE);
		// actionbar_tv_name_center.setVisibility(View.VISIBLE);

		actionbar_btn_right_left.setVisibility(View.GONE);
		actionbar_imgbtn_right.setVisibility(View.GONE);
		// actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText(getString(R.string.order_detail));
		actionbar_btn_right_left.setText(getString(R.string.rule));
		
		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
		actionbar_imgbtn_right.setOnClickListener(clickListener_actionbar);

	}
	
	private OnClickListener clickListener_actionbar = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			switch (v.getId()) {
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

}
