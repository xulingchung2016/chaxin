package com.newbrain.customview;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.sax.StartElementListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.my.mycash.RechargeActivity;
import com.newbrain.chaxin.teacishan.TeaCiShanProtocolActivity;
import com.newbrain.jsonthread.Constant;
import com.newbrain.user.User;
import com.newbrain.utils.StringUtil;
import com.newbrain.utils.ToastUtil;
import com.newbrain.xutils.Xutils_HttpUtils;
/**
 * 茶慈善我要捐献/众筹对话框
 * @author ZY
 *
 */
public class DonateOrCrowdfundingDialog extends Dialog {
	private Context context;
	/**捐献类型*/
	public static final int TYPE_DONATE = 0;
	/**众筹类型*/
	public static final int TYPE_CROWDFUNDING = 1;
	private static int default_width = 250; // 默认宽度
	private static int default_height = 180;// 默认高度
	private EditText nu_edit;
	private TextView tv_text;
	private TextView tv_protocol;
	private TextView tea_money_nu;
	HttpUtils httputils;
	private CheckBox checkBox;
	private Button bt_cancle;	//取消
	private Button bt_ok;		//确认
	private Button button1;    //我要充值
	
	
	private String projectId = null;
	
	
	/**回调监听*/
	private OnDonateOrCrowdfundingDialogListener mOnDonateOrCrowdfundingDialogListener;
	
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public DonateOrCrowdfundingDialog(Context context) {
		this(context,TYPE_DONATE, default_width, default_height);
	}

	public DonateOrCrowdfundingDialog(Context context,int type) {
		this(context,type, default_width, default_height);
	}
	
	public DonateOrCrowdfundingDialog(final Context context,final int type, int width, int height) {
		super(context, R.style.MyDialogStyle);
		httputils=Xutils_HttpUtils.getHttpUtils(context);
		httputils.configCurrentHttpCacheExpiry(0);
		this.context=context;
		// set content
		setContentView(R.layout.tea_cishan_donate_dialog);
		// set window params
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		// set width,height by density and gravity
		float density = getDensity(context);
		params.width = (int) (width * density);
		params.height = (int) (height * density);
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);
		nu_edit = (EditText) findViewById(R.id.nu_edit);
		tv_text = (TextView) findViewById(R.id.be);
		tv_protocol = (TextView) findViewById(R.id.protocol);
		tea_money_nu=(TextView) findViewById(R.id.tea_money_nu);
		button1=(Button) findViewById(R.id.button1);
		tv_protocol.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				switch (type) {
				case TYPE_DONATE:
					context.startActivity(new Intent(context, TeaCiShanProtocolActivity.class));
					break;
				case TYPE_CROWDFUNDING:
					break;

				default:
					break;
				}
			}
		});
		checkBox = (CheckBox) findViewById(R.id.checkBox1);
		bt_cancle = (Button) findViewById(R.id.cancle_bt);
		bt_ok = (Button) findViewById(R.id.ok_bt);
		bt_cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mOnDonateOrCrowdfundingDialogListener != null)
					mOnDonateOrCrowdfundingDialogListener.onCancleClick();
				DonateOrCrowdfundingDialog.this.dismiss();
			}
		});
		bt_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String nu = nu_edit.getText().toString();
				String be=tea_money_nu.getText().toString();
				if(TextUtils.isEmpty(nu)){
					ToastUtil.Toast(context, "茶币不能为空!");
					nu_edit.requestFocus();
					return;
				}
				try {
					
					if(Double.parseDouble(nu)>Double.parseDouble(be)){
						ToastUtil.Toast(context, "茶币不够，请充值!");
						return;
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				if(StringUtil.toInt(nu, 0) == 0){
					ToastUtil.Toast(context, "茶币不能为0!");
					nu_edit.requestFocus();
					return;
				}
				if(!checkBox.isChecked()){
					ToastUtil.Toast(context, "请勾选接受协议");
					return;
				}
				if(mOnDonateOrCrowdfundingDialogListener != null){
					switch(type){
					case TYPE_CROWDFUNDING:
						mOnDonateOrCrowdfundingDialogListener.onCrowdfClick(nu);
						break;
					case TYPE_DONATE:
						if(projectId != null){
							mOnDonateOrCrowdfundingDialogListener.onDonateClick(nu, projectId);
						}else{
							ToastUtil.Toast(context, "项目id为null!");
						}
						break;
					}
				}
				DonateOrCrowdfundingDialog.this.dismiss();
			}
		});
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context, RechargeActivity.class));
			}
		});
		setData(type);
	}
	private void setData(int type){
		switch (type) {
		case TYPE_DONATE:
			tv_text.setText(R.string.cishan_donate_dailog_text);
			tv_protocol.setText(R.string.cishan_protocol);
			bt_ok.setText(R.string.cishan_donate_dailog_ok);
			httputils.send(HttpMethod.GET,Constant.BASE_URL+"TeaMall/userAccount/detail?userId="+User.getInstance().getId()+"&token="+User.getInstance().getToken(),new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					// TODO Auto-generated method stub
					try {
						JSONObject jsonObject=new JSONObject(arg0.result);
						if(jsonObject.getString("message").equals("数据不为空")){
							JSONObject result = jsonObject.getJSONObject("result");
							tea_money_nu.setText(""+result.getInt("gold"));
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			break;
		case TYPE_CROWDFUNDING:
			httputils.send(HttpMethod.GET,Constant.BASE_URL+"TeaMall/userAccount/detail?userId="+User.getInstance().getId()+"&token="+User.getInstance().getToken(),new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					// TODO Auto-generated method stub
					try {
						JSONObject jsonObject=new JSONObject(arg0.result);
						if(jsonObject.getString("message").equals("数据不为空")){
							JSONObject result = jsonObject.getJSONObject("result");
							tea_money_nu.setText(""+result.getInt("gold"));
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			tv_text.setText(R.string.zhongchou_dailog_text);
			tv_protocol.setText(R.string.zhongchou_protocol);
			bt_ok.setText(R.string.zhongchou_dailog_ok);
			break;

		default:
			break;
		}
	}
	
	public void setOnDonateOrCrowdfundingDialogListener(OnDonateOrCrowdfundingDialogListener onHintDialogListener){
		this.mOnDonateOrCrowdfundingDialogListener = onHintDialogListener;
	}
	private float getDensity(Context context) {
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.density;
	}

	
	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
//		mContext.hidFoucsWindow();
		if(mOnDonateOrCrowdfundingDialogListener != null)
			mOnDonateOrCrowdfundingDialogListener.onDismiss();
		mOnDonateOrCrowdfundingDialogListener=null;
		super.dismiss();
	}

	public interface OnDonateOrCrowdfundingDialogListener{
		public abstract void onDonateClick(String nu,String projectId);
		public abstract void onCrowdfClick(String nu);
		public abstract void onCancleClick();
		public abstract void onDismiss();
	}
}
