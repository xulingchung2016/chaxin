package com.newbrain.chaxin.my.order;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.swipeRefresh.SwipyRefreshLayoutDirection;
import com.newbrain.user.User;
import com.newbrain.utils.ButtonColorFilter;
import com.newbrain.xutils.Xutils_HttpUtils;

public class ReturnGoodsActivity extends BaseActivity {


	private Context context;
	private String orderid,price;
	private HttpUtils httpUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.return_activity);
		orderid = getIntent().getStringExtra("id");
		price = getIntent().getStringExtra("price");
		ViewUtils.inject(this);
		context = this;
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		actionbarInit();
		
		
		
		
	}

	

	@ViewInject(R.id.all_actionbar_linear_left)
	private LinearLayout actionbar_ll_left;
	@ViewInject(R.id.et_price)
	private EditText et_price;
	@ViewInject(R.id.et_desc)
	private EditText et_desc;
	@ViewInject(R.id.btn_submit)
	private Button btn_submit;

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
		et_price.setText(price);
		actionbar_ll_left.setVisibility(View.VISIBLE);
		actionbar_tv_back_name_left.setVisibility(View.GONE);
		// actionbar_tv_name_center.setVisibility(View.VISIBLE);
		
		
		actionbar_btn_right_left.setVisibility(View.GONE);
		actionbar_imgbtn_right.setVisibility(View.GONE);
//		actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText("申请退款");
		actionbar_btn_right_left.setText(getString(R.string.rule));

		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
		btn_submit.setOnClickListener(clickListener_actionbar);

	}

	private OnClickListener clickListener_actionbar = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			switch (v.getId()) {
			case R.id.all_actionbar_linear_left:

				finish();

				break;

			case R.id.btn_submit:
				submit();
				break;

			default:
				break;
			}

		}
	};

	protected void submit() {
		ButtonColorFilter.setButtonFocusChanged(btn_submit);
		String prices = et_price.getText().toString().trim();
		if(TextUtils.isEmpty(prices)){
			showShortToast("请输入退款金额");
			return;
		}
		if(Double.parseDouble(prices)>Double.parseDouble(price)){
			showShortToast("退款金额不能大于订单总金额");
			return;
		}
		String desc = et_desc.getText().toString().trim();
		if(TextUtils.isEmpty(desc))desc = "暂无说明";
		CustomProgressDialog.startProgressDialog(context, "loading...");
		String userid = User.getInstance().getId();
		String token = User.getInstance().getToken();
		RequestParams params = new RequestParams();
		params.addBodyParameter("token", token);
		params.addBodyParameter("userId", userid);
		params.addBodyParameter("orderId", orderid);
		params.addBodyParameter("returnDesc", desc);
		params.addBodyParameter("reAmount", prices);
//		String url =  Constant.METHOD_beauty_order+"?token="+token+"&u"
		httpUtils.send(HttpMethod.POST, Constant.METHOD_returnedGood, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				handleNetworkError();
				CustomProgressDialog.stopProgressDialog();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				CustomProgressDialog.stopProgressDialog();
				String result = arg0.result;
//				{"message":"查询成功","result":[{"beautyProfessional":null,"paymentMode":"1","beautyCertNo":"","beautyPhoneNo":"18617167404","status":0,"beautyImage":"http://218.244.138.142:8081/TeaMall/upload/2015/11/13/86cc467e873b47d2bc8330e6999d7942.png","beautyHighOpinion":null,"ordersNo":"111316211417998","msg":"","beautyLevel":null,"beautyName":"上杭NB","id":"5a740a0e50ff74920150ffef26b2000b","amount":150.0,"beautyId":"5a740a0e50ff74920150ffba8e1d0001","bookTime":"13:00","bookDate":"2015-11-13","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-13 16:21:14","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"1","beautyCertNo":"","beautyPhoneNo":"18686568656","status":0,"beautyImage":null,"beautyHighOpinion":null,"ordersNo":"1113155256-8412","msg":"","beautyLevel":null,"beautyName":"在乎别人说","id":"5a740a0e50ff74920150ffd53be10009","amount":222.0,"beautyId":"5a740a0e50f1eed90150f63875b90039","bookTime":"16:00","bookDate":"2015-11-13","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-13 15:52:56","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"0","beautyCertNo":"48051778","beautyPhoneNo":"18711214442","status":0,"beautyImage":"http://218.244.138.142:8081/TeaMall/upload/2015/11/11/2a8b4897107b49bbbec073887691bce1.png","beautyHighOpinion":null,"ordersNo":"1113152816-2054","msg":"","beautyLevel":null,"beautyName":"南国丽园美容","id":"5a740a0e50ff74920150ffbea7bd0007","amount":120.0,"beautyId":"5a740a0e50ece5d00150ef96af460011","bookTime":"13:00","bookDate":"2015-11-13","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-13 15:28:16","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"0","beautyCertNo":"","beautyPhoneNo":"18686568656","status":0,"beautyImage":null,"beautyHighOpinion":null,"ordersNo":"1112120527-1000","msg":"123","beautyLevel":null,"beautyName":"在乎别人说","id":"5a740a0e50f9cd6c0150f9de9e7c0005","amount":222.0,"beautyId":"5a740a0e50f1eed90150f63875b90039","bookTime":"16:00","bookDate":"2015-11-15","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-12 12:05:27","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"0","beautyCertNo":"48051778","beautyPhoneNo":"18711214442","status":0,"beautyImage":"http://218.244.138.142:8081/TeaMall/upload/2015/11/11/2a8b4897107b49bbbec073887691bce1.png","beautyHighOpinion":null,"ordersNo":"1111194553-2123","msg":"123","beautyLevel":null,"beautyName":"南国丽园美容","id":"5a740a0e50f1eed90150f65dcd2c003c","amount":120.0,"beautyId":"5a740a0e50ece5d00150ef96af460011","bookTime":"12:00","bookDate":"2015-11-12","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-11 19:45:53","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"}],"code":"1"}
					try {
						JSONObject obj = new JSONObject(result);
						String code = obj.getString("code");
						String msg = obj.getString("message");
						if(code.equals("1")){
							showShortToast(msg);
							setResult(102);
							finish();
						}else{
							showShortToast(msg);
						}
					} catch (JSONException e) {
					}
			}
		});
		
		
	}



}
