package com.newbrain.chaxin.teadingzhong;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.my.set.ReceiveAddressManagerActivity;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.BaseJsonDataBean;
import com.newbrain.model.Bean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.user.User;
import com.newbrain.utils.SharedPreferencesHelp;
import com.newbrain.xutils.Xutils_BitmapUtils;

public class TeaDingZhongOrderFormActivity extends BaseActivity implements
		OnClickListener {

	private View all_actionbar_linear_left;
	private TextView all_actionbar_name;
	private ImageButton all_actionbar_button_right;
	/** 订单联系地址 */
	@ViewInject(R.id.tea_dingzhong_order_form_contact)
	private View layout_contact;

	@ViewInject(R.id.tea_dingzhong_order_form_contact_way)
	private TextView tv_contact_way;

	@ViewInject(R.id.tea_dingzhong_order_form_contact_address)
	private TextView tv_contact_address;
	@ViewInject(R.id.tv_add)
	private TextView tv_add;
	/** 联系QQ */
	@ViewInject(R.id.tea_dingzhong_order_form_qq)
	private EditText et_contact_qq;
	/** 标题 */
	@ViewInject(R.id.tea_dingzhong_order_form_title)
	private EditText ev_title;
	/** 图片 */
	@ViewInject(R.id.tea_dingzhong_order_form_icon)
	private ImageView iv_icon;

	/** 品种 */
	@ViewInject(R.id.tea_dingzhong_order_form_variety)
	private TextView tv_variety;

	/** 茶币 */
	@ViewInject(R.id.tea_dingzhong_order_form_chabi_num)
	private TextView tv_gold;

	/** 减 */
	@ViewInject(R.id.iv_tea_dingzhong_order_form_reduce)
	private ImageView iv_reduce;

	/** 数量 */
	@ViewInject(R.id.tv_tea_dingzhong_order_form_amount)
	private TextView tv_variety_count;
	/** 加 */
	@ViewInject(R.id.iv_tea_dingzhong_order_form_plus)
	private ImageView iv_plus;

	/** 留言 */
	@ViewInject(R.id.et_tea_dingzhong_order_form_message)
	private EditText et_message;

	/** 应付茶币 */
	@ViewInject(R.id.tea_dingzhong_order_form_pay_chabi)
	private TextView tv_pay_gold;

	/** 提交订单 */
	@ViewInject(R.id.btn_submit_orders)
	private Button btn_submit_orders;
	private JsonThread mThread;
	String expressId= "",NameTemp,address;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constant.FLAG_GET_MANOR_BOOKING:
				CustomProgressDialog.stopProgressDialog();
				HttpReturnData reData = (HttpReturnData) msg.obj;
				if (reData.isSuccess()) {
					BaseJsonDataBean baseJsonDataBean = (BaseJsonDataBean) reData
							.getObg();
					if (baseJsonDataBean.getCode().equals("1")) {
						showShortToast("订单提交成功");
					} else {
						showShortToast("订单提交失败");
						System.out.println("result:"
								+ baseJsonDataBean.getMessage());
					}
				}
				break;
			}
		}

	};
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tea_dingzhong_order_form);
		context = this;
		ViewUtils.inject(this);

		all_actionbar_linear_left = (View) findViewById(R.id.all_actionbar_linear_left);
		all_actionbar_linear_left.setOnClickListener(this);
		all_actionbar_name = (TextView) findViewById(R.id.all_actionbar_name);
		all_actionbar_name.setText(getString(R.string.order_form));
		all_actionbar_button_right = (ImageButton) findViewById(R.id.all_actionbar_button_right);
		all_actionbar_button_right.setVisibility(View.GONE);
		if (TeaDingZhongOrderActivity.getInstance().mImages != null
				&& TeaDingZhongOrderActivity.getInstance().mImages.size() > 0) {
			BitmapUtils bitmapUtils = Xutils_BitmapUtils
					.getbitmapUtils_detail(this);
			bitmapUtils.display(iv_icon,
					TeaDingZhongOrderActivity.getInstance().mImages.get(0));
		}

		tv_variety
				.setText(TeaDingZhongOrderActivity.getInstance().mManoVarietyIntro
						.getVariety_name());
		tv_gold.setText(TeaDingZhongOrderActivity.getInstance().mManoDetailIntro
				.getPrice());
		tv_variety_count.setText(String.valueOf(TeaDingZhongOrderActivity
				.getInstance().mManoVarietyCount));

		double pay_gold = Double.parseDouble(TeaDingZhongOrderActivity
				.getInstance().mManoDetailIntro.getPrice())
				* TeaDingZhongOrderActivity.getInstance().mManoVarietyCount;
		tv_pay_gold.setText(String.valueOf(pay_gold));
		layout_contact.setOnClickListener(this);
		iv_reduce.setOnClickListener(this);
		iv_plus.setOnClickListener(this);
		btn_submit_orders.setOnClickListener(this);
		
		
	}
	@Override
	protected void onResume() {
		NameTemp = SharedPreferencesHelp.getString(context, "name");
		address = SharedPreferencesHelp.getString(context, "address");
		expressId = SharedPreferencesHelp.getString(context, "expressId");

		if(TextUtils.isEmpty(NameTemp)){
			tv_add.setVisibility(View.VISIBLE);
			tv_contact_way.setVisibility(View.GONE);
			tv_contact_address.setVisibility(View.GONE);
		}else{
			tv_add.setVisibility(View.GONE);
			tv_contact_way.setText(NameTemp);
			tv_contact_address.setText(address);
			tv_contact_way.setVisibility(View.VISIBLE);
			tv_contact_address.setVisibility(View.VISIBLE);
		}
		super.onResume();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.all_actionbar_linear_left:
			onBackPressed();
			break;
		case R.id.tea_dingzhong_order_form_contact:// 订单联系地址
			startActivity((new Intent(this, ReceiveAddressManagerActivity.class)).putExtra("from", "order"));
			break;
		case R.id.iv_tea_dingzhong_order_form_reduce:// 减
			if (TeaDingZhongOrderActivity.getInstance().mManoVarietyCount > 1) {
				TeaDingZhongOrderActivity.getInstance().mManoVarietyCount--;
				double pay_gold = Double.parseDouble(TeaDingZhongOrderActivity
						.getInstance().mManoDetailIntro.getPrice())
						* TeaDingZhongOrderActivity.getInstance().mManoVarietyCount;
				tv_pay_gold.setText(String.valueOf(pay_gold));

				tv_variety_count
						.setText(String.valueOf(TeaDingZhongOrderActivity
								.getInstance().mManoVarietyCount));
			}
			break;
		case R.id.iv_tea_dingzhong_order_form_plus:// 加
			TeaDingZhongOrderActivity.getInstance().mManoVarietyCount++;
			double pay_gold = Double.parseDouble(TeaDingZhongOrderActivity
					.getInstance().mManoDetailIntro.getPrice())
					* TeaDingZhongOrderActivity.getInstance().mManoVarietyCount;
			tv_pay_gold.setText(String.valueOf(pay_gold));
			tv_variety_count.setText(String.valueOf(TeaDingZhongOrderActivity
					.getInstance().mManoVarietyCount));
			break;
		case R.id.btn_submit_orders:// 提交订单
			startOrderForm();
			break;
		}
	}

	/** 提交订单 */
	private void startOrderForm() {
		if(TeaDingZhongActivity.mTeaTypeIntro != null){
			if(TextUtils.isEmpty(expressId)){
				showShortToast("请添加地址");
				return;
			}
			String name = SharedPreferencesHelp.getString(context, "name1");
			String phone = SharedPreferencesHelp.getString(context, "phone");
			List<Bean> list = new ArrayList<Bean>();
			list.add(new Bean("userId", User.getInstance().getId()));
			Log.e("test","ID  "+User.getInstance().getId());
			list.add(new Bean("userName", name));
			list.add(new Bean("phone", phone));
			list.add(new Bean("address", tv_contact_address.getText().toString()));
			list.add(new Bean("qq", et_contact_qq.getText().toString()));
			list.add(new Bean("manorId", TeaDingZhongOrderActivity.getInstance().mManoDetailIntro.getManor_id()));
			list.add(new Bean("varietyId", TeaDingZhongOrderActivity.getInstance().mManoVarietyIntro.getVariety_id()));
			list.add(new Bean("msg", et_message.getText().toString()));
			list.add(new Bean("totalPrice",String.valueOf(Integer.parseInt(TeaDingZhongOrderActivity.getInstance().mManoDetailIntro.getPrice()) 
					* TeaDingZhongOrderActivity.getInstance().mManoVarietyCount)));
			list.add(new Bean("price", TeaDingZhongOrderActivity.getInstance().mManoDetailIntro.getPrice()));
			Log.e("test","定种茶苗数量  "+String.valueOf(TeaDingZhongOrderActivity.getInstance().mManoVarietyCount));
			list.add(new Bean("orderCount", String.valueOf(TeaDingZhongOrderActivity.getInstance().mManoVarietyCount)));
			startSearchThread(list, Constant.FLAG_GET_MANOR_BOOKING);
		}
	}

	private void startSearchThread(List<Bean> params, int flag) {
		CustomProgressDialog.startProgressDialog(this);

		if (mThread != null) {
			mThread.cancleReturnData();
		}
		mThread = new JsonThread(this, params, mHandler, flag);
		mThread.start();
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mThread != null) {
			mThread.cancleReturnData();
			mThread = null;
		}
	}

}