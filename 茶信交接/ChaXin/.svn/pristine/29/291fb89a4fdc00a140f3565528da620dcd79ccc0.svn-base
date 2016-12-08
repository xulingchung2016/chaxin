package com.newbrain.chaxin;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.liren_app.ui.MyLiRenActivity;
import com.newbrain.chaxin.loginandregister.Login;
import com.newbrain.chaxin.message.MessageActivity;
import com.newbrain.chaxin.my.MySmallTeaActivity;
import com.newbrain.chaxin.my.mybeauty.BeautyActivity;
import com.newbrain.chaxin.my.mybeauty.BecomeBeautyActivity;
import com.newbrain.chaxin.my.mycash.MyCashActivity;
import com.newbrain.chaxin.my.mycoin.MyCoinActivity;
import com.newbrain.chaxin.my.mycollect.MyCollectActivity;
import com.newbrain.chaxin.my.order.MyChaOrderActivity;
import com.newbrain.chaxin.my.order.MyOrder;
import com.newbrain.chaxin.my.order.QueryExitGoodsActivity;
import com.newbrain.chaxin.my.set.PersonDataActivity;
import com.newbrain.chaxin.my.set.SetActivity;
import com.newbrain.chaxin.my.teafans.MyTeaFansActivity;
import com.newbrain.chaxin.my.teafans.TeaFansActivity;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.user.User;
import com.newbrain.utils.HttpUtil;
import com.newbrain.utils.SharedPreferencesHelp;
import com.newbrain.utils.customRounderTransformation;
import com.newbrain.xutils.Xutils_BitmapUtils;
import com.newbrain.xutils.Xutils_HttpUtils;
import com.squareup.picasso.Picasso;

public class Tab3Fragment extends Fragment {

	private Context context;
	private HttpUtils httpUtils;
	private int status = -1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.tab3_fragment, null);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		ViewUtils.inject(this, view);
		context = getActivity();
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		actionbarInit();
		

	}
	//ode－－1:未申请  2 正在审核 3  审核通过  4 审核未通过
	private void checkIsLiren() {
		if(User.getInstance().isLogin()){
			CustomProgressDialog.startProgressDialog(context, "loading...");
			String userid = User.getInstance().getId();
			String token = User.getInstance().getToken();
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId",userid);
		params.addBodyParameter("token",token);
		httpUtils.send(HttpMethod.POST, Constant.METHOD_isTeaBeauty, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
//				Toast.makeText(context, "异常！", 1).show();
				CustomProgressDialog.stopProgressDialog();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					CustomProgressDialog.stopProgressDialog();
					JSONObject	jsonObjs = new JSONObject(arg0.result);
					String msg = jsonObjs.getString("message");
					String code = jsonObjs.getString("code");
					
					status = Integer.parseInt(code);
					if(code.equals("0")){
					Toast.makeText(context, "账号登录已过期，请重新登录", 1).show();
					
					}
					if(status== 3){
						String beautyId = jsonObjs.getString("beautyId");
						startActivity(new Intent(context, BeautyActivity.class));
						SharedPreferencesHelp.SavaString(context, "chaxin_beautyid",beautyId );
					}
						else if(status == 1||status == 4||status == 2){
							Intent intent = new Intent(context, MyLiRenActivity.class);
							intent.putExtra("status", status);
							startActivity(intent);
						} 
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		}
	}

	@OnClick({ R.id.my_fans, R.id.my_coin, R.id.my_cash, R.id.my_beauty,
			R.id.my_wait_for_pay, R.id.my_wait_for_comment,R.id.my_wait_for_fh,
			R.id.my_wait_for_query_goods, R.id.my_wait_for_receive,
			R.id.my_all_order, R.id.my_small_tea, R.id.my_collect, R.id.my_set,R.id.my_user,R.id.my_all_clrorder
		
	})
	private void onClick_tab3(View view)
	{
		switch (view.getId()) 
		{
		case R.id.my_fans:
			
			startActivity(new Intent(context, MyTeaFansActivity.class));

			break;
		case R.id.my_coin:
			if(User.getInstance().isLogin()){
				startActivity(new Intent(context, MyCoinActivity.class));
			}else
			startActivity(new Intent(context, Login.class));
			

			break;
		case R.id.my_all_clrorder:
			startActivity(new Intent(context, MyChaOrderActivity.class));

		break;
		case R.id.my_cash:
			if(User.getInstance().isLogin()){
				startActivity(new Intent(context, MyCashActivity.class));
			}else
			startActivity(new Intent(context, Login.class));
			
			

			break;
		case R.id.my_beauty:
			//ode－－1:未申请  2 正在审核 3  审核通过  4 审核未通过
//			startActivity(new Intent(context, BecomeBeautyActivity.class));
			checkIsLiren();
			

			break;
		case R.id.my_wait_for_pay:
			
			startActivity((new Intent(context, MyOrder.class)).putExtra(WAIT_TYPE, WAIT_PAY));

			break;
		case R.id.my_wait_for_comment:
			
			
			startActivity((new Intent(context, MyOrder.class)).putExtra(WAIT_TYPE, WAIT_COMMENT));
			

			break;
		case R.id.my_wait_for_fh:
			startActivity((new Intent(context, MyOrder.class)).putExtra(WAIT_TYPE, WAIT_fh));
			
			break;
		//查退货
		case R.id.my_wait_for_query_goods:
			
			startActivity(new Intent(context, QueryExitGoodsActivity.class));

			break;

		case R.id.my_wait_for_receive:
			
			startActivity((new Intent(context, MyOrder.class)).putExtra(WAIT_TYPE, WAIT_RECEIVE));
			

			break;

		case R.id.my_all_order:
			
			startActivity((new Intent(context, MyOrder.class)).putExtra(WAIT_TYPE, WAIT_ALL));

			break;

		case R.id.my_small_tea:
			
			startActivity(new Intent(context, MySmallTeaActivity.class));

			break;

		case R.id.my_collect:
			
			startActivity((new Intent(context, MyCollectActivity.class)).putExtra(WAIT_TYPE, WAIT_ALL));
			
			break;
			
		//设置
		case R.id.my_set:
			
			
			LogUtils.e("------------->");
			startActivity(new Intent(context, SetActivity.class));
			

			break;
		case R.id.my_user:
		{
			if(User.getInstance().isLogin()){
				startActivity(new Intent(context, PersonDataActivity.class));
			}else
			startActivity(new Intent(context, Login.class));
		}
		
		break;

		default:
			break;
		}

	}
	
	
	public static final String WAIT_TYPE = "WAIT_TYPE";
	public static final int WAIT_PAY = 1;
	
	public static final int WAIT_RECEIVE = 2;
	public static final int WAIT_COMMENT = 3;
	public static final int WAIT_fh = 0;
	
	public static final int WAIT_ALL = 4;
	

	@ViewInject(R.id.actionbar_homepager_style_textview_left)
	private TextView actionbar_left;

	@ViewInject(R.id.actionbar_homepager_style_textview_name_center)
	private TextView actionbar_center;

	@ViewInject(R.id.actionbar_homepager_style_button_message_right)
	private Button actionbar_right;
	@ViewInject(R.id.rl_top)
	private RelativeLayout rl_top;
	
	@ViewInject(R.id.my_user_xianjin)
	private TextView tv_xj;
	@ViewInject(R.id.my_user_chabi)
	private TextView tv_cb;
	@ViewInject(R.id.my_user_num)
	private TextView tv_dj;
	@ViewInject(R.id.ll_bz)
	private LinearLayout ll_bz;
	@ViewInject(R.id.my_user)
	private ImageView my_user_head;
	

	private void actionbarInit() {

		actionbar_left.setText(getString(R.string.sign_in));
		Drawable draw = getResources().getDrawable(R.drawable.my_qiandao);
		draw.setBounds(0, 0, draw.getMinimumWidth(), draw.getMinimumHeight());
		actionbar_left.setCompoundDrawables(null,
				draw, null, null);
		actionbar_left.setVisibility(View.VISIBLE);
		actionbar_left.setTextSize(10);
		rl_top.setBackgroundResource(0);
		actionbar_center.setText("");
		actionbar_center.setVisibility(View.GONE);

		// actionbar_right.setText("");
		actionbar_right.setVisibility(View.GONE);
		
		

		actionbar_left.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				signIn();//签到

			}
		});

	}
	
	/**
	 * 签到
	 */
	String userID,usertoken;
	protected void signIn() {
		if(User.getInstance().isLogin()){
			userID = User.getInstance().getId();
			usertoken = User.getInstance().getToken();
			String url = Constant.METHOD_USER_GOOD_sign_in;
			RequestParams params = new RequestParams();
			params.addBodyParameter("userId", userID);
			params.addBodyParameter("token", usertoken);
			httpUtils.send(HttpMethod.POST, url,params, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					try {
						JSONObject	jsonObjs = new JSONObject(arg0.result);
//						String code = jsonObjs.getString("code");
						String msg = jsonObjs.getString("message");
						Toast.makeText(context, msg, 1).show();
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				}
			});
			}else{
				Toast.makeText(context, "还没有登录，请登录", 1).show();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				startActivity(new Intent(context, Login.class));
			}
		
	}

	@Override
	public void onResume() {
		intData();
		
		super.onResume();
	}
	/**
	 * 获取账号详细信息
	 */
	private void getData(String userid,String token) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId", userid);
		params.addBodyParameter("token", token);
		httpUtils.send(HttpMethod.POST, Constant.METHOD_GET_ACCOUNT, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}


			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				String result = arg0.result;
				try {
					JSONObject obj = new JSONObject(result);
					String code = obj.getString("code");
//					String msg = obj.getString("message");
					if(code.equals("1")){
						JSONObject objc = obj.getJSONObject("result");
						String cash = objc.getString("cash");
						String gold = objc.getString("gold");
						SharedPreferencesHelp.SavaString(context, "cash",cash);
						SharedPreferencesHelp.SavaString(context, "gold",gold);
						tv_cb.setText("茶币："+gold);
						tv_xj.setText("现金："+cash);
					}else if(code .equals("2")){
						User.getInstance().setLogin(false);
						Toast.makeText(context, "账号已过期,请重新登录。", 1).show();
					}
				} catch (JSONException e) {
				}
				
			}
		});
		
	}

	private void intData() {
		
		boolean isLogin = User.getInstance().isLogin();
		if(isLogin){
		String nickName = User.getInstance().getNickName();
		String icon = User.getInstance().getImage();
		if(!TextUtils.isEmpty(icon)){
				Picasso.with(context).load(icon).
				centerCrop().resize(100, 100)
				.transform(new customRounderTransformation(1))
				.into(my_user_head);
		}else my_user_head.setImageResource(R.drawable.my_user);

		
		
		ll_bz.setVisibility(View.VISIBLE);
		tv_dj.setText(nickName);
		getData(User.getInstance().getId(), User.getInstance().getToken());
		}else{
			my_user_head.setImageResource(R.drawable.my_user);
			ll_bz.setVisibility(View.INVISIBLE);
			tv_dj.setText("点击头像登录");
		}
		
	}
}
