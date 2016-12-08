package com.newbrain.chaxin.teadingzhong;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.adapter.MyAdapter_SelectVarietiesDialogGridview;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.my.mycash.RechargeActivity;
import com.newbrain.jsonthread.Constant;
import com.newbrain.user.User;
import com.newbrain.utils.SharedPreferencesHelp;
import com.newbrain.xutils.Xutils_BitmapUtils;
import com.newbrain.xutils.Xutils_HttpUtils;

public class SelectVarietiesDialog extends BaseActivity implements
		OnClickListener, OnItemClickListener {

	/** 取消 */
	@ViewInject(R.id.iv_cancel_round)
	private ImageView iv_cancel_round;

	/** 图片 */
	@ViewInject(R.id.iv_dialog_select_varieties_icon)
	private ImageView iv_icon;

	/** 标题 */
	@ViewInject(R.id.tv_dialog_select_varieties_title)
	private TextView tv_memo;

	/** 茶币数 */
	@ViewInject(R.id.tv_dialog_select_varieties_chabi_num)
	private TextView tv_gold;

	/** 品种 */
	@ViewInject(R.id.gv_dialog_select_varieties)
	private GridView gv_varieties;

	/** 减 */
	@ViewInject(R.id.iv_dialog_select_varieties_reduce)
	private ImageView iv_reduce;

	@ViewInject(R.id.tv_dialog_select_varieties_amount)
	private TextView tv_amount;

	/** 加 */
	@ViewInject(R.id.iv_dialog_select_varieties_plus)
	private ImageView iv_plus;

	/** 金币充值 */
	@ViewInject(R.id.btn_dialog_select_varieties_gold_deposits)
	private Button btn_gold_deposits;

	/** 订单 */
	@ViewInject(R.id.btn_order)
	private Button btn_order;
	/** 是否选择了品种 */
	private  boolean varieties = false;
	private HttpUtils httputils;
	
	private MyAdapter_SelectVarietiesDialogGridview myAdapter_SelectVarietiesDialogGridview;

	private int gold_total ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_select_varieties);
		
		ViewUtils.inject(this);
		btn_order.setSelected(false);
		httputils=Xutils_HttpUtils.getHttpUtils(this);
		// mList = getResources().getStringArray(R.array.varieties);
		myAdapter_SelectVarietiesDialogGridview = new MyAdapter_SelectVarietiesDialogGridview(
				this,
				TeaDingZhongOrderActivity.getInstance().mManoVarietyIntros);
		gv_varieties.setAdapter(myAdapter_SelectVarietiesDialogGridview);
		gv_varieties.setOnItemClickListener(this);

		tv_amount.setText(String.valueOf(TeaDingZhongOrderActivity
				.getInstance().mManoVarietyCount));
		if (TeaDingZhongOrderActivity.getInstance().mImages != null
				&& TeaDingZhongOrderActivity.getInstance().mImages.size() > 0) {
			BitmapUtils bitmapUtils = Xutils_BitmapUtils
					.getbitmapUtils_detail(this);
			bitmapUtils.display(iv_icon, TeaDingZhongOrderActivity
					.getInstance().mManoDetailIntro.getSamllIimage());
		}
		tv_memo.setText(Html.fromHtml(TeaDingZhongOrderActivity.getInstance().mManoDetailIntro
				.getManor_name()));
		tv_gold.setText(TeaDingZhongOrderActivity.getInstance().mManoDetailIntro
				.getPrice());
		btn_gold_deposits.setVisibility(View.INVISIBLE);
		btn_gold_deposits.setOnClickListener(this);
		iv_cancel_round.setOnClickListener(this);
		iv_reduce.setOnClickListener(this);
		iv_plus.setOnClickListener(this);
		btn_order.setOnClickListener(this);
		getGold();
	}

	@Override
	protected void onResume() {
		super.onResume();
		myAdapter_SelectVarietiesDialogGridview
				.setIndex(TeaDingZhongOrderActivity.getInstance().select_variety_index);
		if(TeaDingZhongOrderActivity.getInstance().select_variety_index!=-1){
			varieties=true;
			String gold = SharedPreferencesHelp.getString(this, "gold");
			if(TextUtils.isEmpty(gold))gold = "0";
			gold_total = Integer.parseInt(gold);
			checkGold();
			
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_cancel_round:
			finish();
			break;
		case R.id.iv_dialog_select_varieties_reduce:// 减
			if (TeaDingZhongOrderActivity.getInstance().mManoVarietyCount > 1) {
				TeaDingZhongOrderActivity.getInstance().mManoVarietyCount--;
				tv_amount.setText(String.valueOf(TeaDingZhongOrderActivity
						.getInstance().mManoVarietyCount));
			}
			checkGold();
			break;
		case R.id.iv_dialog_select_varieties_plus:// 加
			TeaDingZhongOrderActivity.getInstance().mManoVarietyCount++;
			tv_amount.setText(String.valueOf(TeaDingZhongOrderActivity
					.getInstance().mManoVarietyCount));
			checkGold();
			break;
		case R.id.btn_dialog_select_varieties_gold_deposits:// 金币充值
			showShortToast(R.string.gold_deposits);
			openActivity(RechargeActivity.class);
			break;
		case R.id.btn_order:// 我要订种
			if (TeaDingZhongOrderActivity.getInstance().select_variety_index == -1) {
				showShortToast(R.string.toast_select_varieties);
				return;
			}
			// btn_gold_deposits.setVisibility(View.GONE);
			startActivity(new Intent(this, TeaDingZhongOrderFormActivity.class));
			finish();

			break;
		}
	}
	
	private void getGold(){
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
						gold_total=result.getInt("gold");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	private void checkGold() {
		double pay_gold = Double.parseDouble(TeaDingZhongOrderActivity
				.getInstance().mManoDetailIntro.getPrice())
				* TeaDingZhongOrderActivity.getInstance().mManoVarietyCount;
		if (varieties) {
			if (gold_total < pay_gold) {
				btn_gold_deposits.setVisibility(View.VISIBLE);
				btn_order.setSelected(false);
				btn_order.setClickable(false);
			} else {
				btn_gold_deposits.setVisibility(View.INVISIBLE);
				btn_order.setSelected(true);
				btn_order.setClickable(true);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long position) {
		if (TeaDingZhongOrderActivity.getInstance().select_variety_index == (int) position)
			return;
		TeaDingZhongOrderActivity.getInstance().mManoVarietyIntro = TeaDingZhongOrderActivity
				.getInstance().mManoVarietyIntros.get((int) position);
		TeaDingZhongOrderActivity.getInstance().select_variety_index = (int) position;
		TeaDingZhongOrderActivity.getInstance().mManoVarietyCount = 1;
		tv_amount.setText(String.valueOf(TeaDingZhongOrderActivity
				.getInstance().mManoVarietyCount));
		myAdapter_SelectVarietiesDialogGridview
				.setIndex(TeaDingZhongOrderActivity.getInstance().select_variety_index);
		varieties = true;
		checkGold();
	}
}
