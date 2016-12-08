package com.newbrain.chaxin.my.order;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.user.User;
import com.newbrain.utils.ButtonColorFilter;
import com.newbrain.xutils.Xutils_HttpUtils;
import com.squareup.picasso.Picasso;

public class MyCommentActivity extends BaseActivity {

	private Context context;
	private String orderId = "0";
	private HttpUtils httpUtils;
	private HashMap<String,String> map;
	private int flag;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.my_comment_activity);

		ViewUtils.inject(this);
		context = this;
		actionbarInit();
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		flag = getIntent().getIntExtra("flag", 0);
		map = (HashMap<String, String>) getIntent().getSerializableExtra("data");
		if(flag == 0){
		orderId = getIntent().getStringExtra("ordersNo");
		if(map !=null){
			String image = map.get("goodsImage");
			String name = map.get("goodsName");
			String price = map.get("price"); 
			if(!TextUtils.isEmpty(image)){
				Picasso.with(context).load(image).centerCrop().resize(60, 60).into(iv_icon);
			}
			tv_name.setText(name);
			tv_price.setText("￥"+price);
			}
		}else {
//			linear_beauty.setVisibility(View.VISIBLE);
			String name = map.get("beautyName");
			final String price = map.get("amount");
			String url = map.get("beautyImage");
			if(!TextUtils.isEmpty(url)){
				Picasso.with(context).load(url).centerCrop().resize(60, 60).into(iv_icon);
			}
			tv_name.setText(name);
			tv_price.setText("￥"+price);
		}
		
		choiceInit();
	}
	@Override
	protected void onResume() {
		super.onResume();
	}

	/** 选择好评 中评 差评 */	
	@ViewInject(R.id.my_comment_bad)
	private TextView mTvbad;
	
	@ViewInject(R.id.my_comment_center)
	private TextView  mTvcenter;
	
	@ViewInject(R.id.linear_beauty)
	private  LinearLayout linear_beauty;
	@ViewInject(R.id.my_comment_good)
	private  TextView mTvgood;
	@ViewInject(R.id.goods_collect_name)
	private  TextView tv_name;
	@ViewInject(R.id.goods_collect_price)
	private  TextView tv_price;
	@ViewInject(R.id.beauty_collect_imageview)
	private  ImageView iv_icon;
	@ViewInject(R.id.comment_edit)
	private  EditText et_comment;
	@ViewInject(R.id.comment_profession_ratingbar)
	private  RatingBar rb_profession;
	
	@ViewInject(R.id.comment_contact_ratingbar)
	private  RatingBar rb_contact;
	
	@ViewInject(R.id.comment_on_the_time_ratingbar)
	private  RatingBar rb_time;
	
	
	private TextView mtv[] ;
	
	private int mCurrentChoice;
	
	private int mTemp;
	
	
	private void choiceInit()
	{
		
		mtv = new TextView[]{mTvgood,mTvcenter,mTvbad};
		
		mtv[0].setSelected(true);
		
	}
	
	

	@OnClick({ R.id.my_comment_bad, R.id.my_comment_center,
			R.id.my_comment_good })
	private void onClick_myComment(View v) {
		
		

		switch (v.getId()) {
		case R.id.my_comment_bad:
			
			
			mTemp = 2;
			

			break;

		case R.id.my_comment_center:
			
			mTemp = 1;
			
			break;

		case R.id.my_comment_good:
			
			mTemp = 0;

			break;

		default:
			break;
			
			
			
		}
		
		if(mCurrentChoice == mTemp)
		{
			
			return;
		}
		
		
		mtv[mTemp].setSelected(true);
		
		mtv[mCurrentChoice].setSelected(false);
		
		mCurrentChoice = mTemp;

	}
	
	
	
	
	
	

	@ViewInject(R.id.all_actionbar_linear_left)
	private LinearLayout actionbar_ll_left;


	@ViewInject(R.id.all_actionbar_name)
	private TextView actionbar_tv_name_center;

	@ViewInject(R.id.all_actionbar_button_right_left)
	private Button actionbar_btn_right_left;

	@ViewInject(R.id.all_actionbar_button_right)
	private ImageButton actionbar_imgbtn_right;
	@ViewInject(R.id.comment_btn_warp)
	private Button btn_pub;

	private void actionbarInit() {
		// TODO Auto-generated method stub

		actionbar_ll_left.setVisibility(View.VISIBLE);
		// actionbar_tv_name_center.setVisibility(View.VISIBLE);

		actionbar_btn_right_left.setVisibility(View.GONE);
//		actionbar_imgbtn_right.setVisibility(View.VISIBLE);
		 actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_name_center.setText(getString(R.string.comment));
		actionbar_btn_right_left.setText(getString(R.string.rule));

		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
		actionbar_imgbtn_right.setOnClickListener(clickListener_actionbar);
		btn_pub.setOnClickListener(clickListener_actionbar);
		ButtonColorFilter.setButtonFocusChanged(btn_pub);

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
			case R.id.comment_btn_warp:
				if(flag == 0)
				publish();
				else publishLr();
				break;

			default:
				break;
			}

		}
	};

	protected void publish() {
		String content = et_comment.getText().toString().trim();
		CustomProgressDialog.startProgressDialog(context, "正在发布，请稍后...");
		String goodId = map.get("goodsId");
		String ordersId = map.get("id");//子订单id
		String userid = User.getInstance().getId();
		String token = User.getInstance().getToken();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user.id", userid);
		params.addBodyParameter("token", token);
		params.addBodyParameter("goodsId", goodId);
		params.addBodyParameter("highOpinion", mTemp+"");
		params.addBodyParameter("content", content);
		params.addBodyParameter("subOrderId", ordersId);
		params.addBodyParameter("orderId", orderId);
		/*String url = Constant.METHOD_saveGoodsEvaluation+"?user.id="+userid+"&token="
		+token+"&goodsId="+goodId+"&highOpinion="+mTemp+"&content="+content+"&subOrderId="+ordersId+"&orderId="+orderId;*/
		httpUtils.send(HttpMethod.POST, Constant.METHOD_saveGoodsEvaluation,params, new RequestCallBack<String>() {

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
						Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
						setResult(102);
						finish();
					}else{
						Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
					}
					}catch (Exception e) {
						
					}
				
			}
		});
	}
	
	/**
	 * 茶丽人评论
	 * 请求参数：
字段	类型	备注	必填项
beautyId	 String	丽人id	是
highOpinion	 String	好评，0:好评，1中评，2差评	 是
content	String	评价内容	否
profession	String	专业	否
communicate	String	沟通	否
onTime	String	准时	否
user.id	String	会员id	是
token	String	密钥	是

	 */
	protected void publishLr() {
		String content = et_comment.getText().toString().trim();
		CustomProgressDialog.startProgressDialog(context, "正在发布，请稍后...");
		String goodId = map.get("beautyId");
		String ordersId = map.get("id");//子订单id
		String userid = User.getInstance().getId();
		String token = User.getInstance().getToken();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user.id", userid);
		params.addBodyParameter("token", token);
		params.addBodyParameter("beautyId", goodId);
		params.addBodyParameter("highOpinion", mTemp+"");
		params.addBodyParameter("content", content);
		params.addBodyParameter("orderId", ordersId);
		/*params.addBodyParameter("profession", rb_profession.getStepSize()+"");
		params.addBodyParameter("communicate", rb_contact.getStepSize()+"");
		params.addBodyParameter("onTime", rb_time.getStepSize()+"");*/
		String url = Constant.METHOD_saveEvaluation+"?user.id="+userid+"&token="
		+token+"&beautyId="+goodId+"&highOpinion="+mTemp+"&content="+content;
//		"&profession="+rb_profession.getStepSize()+"&communicate="+ rb_contact.getStepSize()+"&onTime="+ rb_time.getStepSize();
		httpUtils.send(HttpMethod.POST, Constant.METHOD_saveEvaluation,params, new RequestCallBack<String>() {

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
						Toast.makeText(context, "评价成功！", Toast.LENGTH_SHORT).show();
						setResult(102);
						finish();
					}else{
						Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
					}
					}catch (Exception e) {
						
					}
				
			}
		});
	}

}
