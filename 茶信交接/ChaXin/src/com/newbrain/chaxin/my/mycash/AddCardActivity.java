package com.newbrain.chaxin.my.mycash;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.WheelActivity;
import com.newbrain.jsonthread.Constant;
import com.newbrain.user.User;
import com.newbrain.utils.ButtonColorFilter;
import com.newbrain.xutils.Xutils_HttpUtils;

public class AddCardActivity extends BaseActivity {

	private Context context;
	private HashMap<String,String> data;
	private String id;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.i_want_thanks_activity);
		ViewUtils.inject(this);
		context = this;
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		actionbarInit();
		data = (HashMap<String, String>) getIntent().getSerializableExtra("data");
		if(data!= null){
			id = data.get("id");
			ed_date.setText(data.get("validDate"));
			ed_name.setText(data.get("bank"));
			ed_no.setText(data.get("cardNumber"));
			ed_people.setText(data.get("holder"));
			ed_phone.setText(data.get("phoneNo"));
		}
		
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
	@ViewInject(R.id.et_bank_people)
	private EditText ed_people;
	@ViewInject(R.id.et_bank_no)
	private EditText ed_no;
	@ViewInject(R.id.et_bank_phone)
	private EditText ed_phone;
	@ViewInject(R.id.et_bank_name)
	private TextView ed_name;
	@ViewInject(R.id.et_bank_date)
	private TextView ed_date;
	@ViewInject(R.id.btn_save)
	private Button btn_save;

	private void actionbarInit() {

		actionbar_ll_left.setVisibility(View.VISIBLE);
		actionbar_tv_back_name_left.setVisibility(View.GONE);
		// actionbar_tv_name_center.setVisibility(View.VISIBLE);
		actionbar_btn_right_left.setVisibility(View.GONE);
//		actionbar_imgbtn_right.setVisibility(View.VISIBLE);
		actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText(getString(R.string.my_cash_add_card));
		actionbar_btn_right_left.setText(getString(R.string.rule));

		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
		btn_save.setOnClickListener(clickListener_actionbar);
		ed_date.setOnClickListener(clickListener_actionbar);
		ed_name.setOnClickListener(clickListener_actionbar);
		ButtonColorFilter.setButtonFocusChanged(btn_save);
		ed_no.addTextChangedListener(new MyWatcher());

	}

	private OnClickListener clickListener_actionbar = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent inten = null;
			switch (v.getId()) {
			case R.id.all_actionbar_linear_left:

				finish();

				break;

			case R.id.btn_save:
				save();
				break;
			case R.id.et_bank_date:
				inten = new Intent(context, WheelActivity.class);
				inten.putExtra("type", 3);
				startActivityForResult(inten, 101);
				overridePendingTransition(R.anim.push_bottom_in,
						R.anim.push_bottom_out);
				break;
			case R.id.et_bank_name:
				inten = new Intent(context, WheelActivity.class);
				inten.putExtra("type", 6);
				startActivityForResult(inten, 102);
				overridePendingTransition(R.anim.push_bottom_in,
						R.anim.push_bottom_out);
				break;

			default:
				break;
			}

		}
	};

	/**
	 * 添加银行卡
	 */
	String userID,usertoken;
	private HttpUtils httpUtils;
	protected void save() {
		String phoneNo = ed_phone.getText().toString().trim();
		String holder = ed_people.getText().toString().trim();
		String cardNumber = ed_no.getText().toString().trim();
		String bank = ed_name.getText().toString().trim();
		String validDate = ed_date.getText().toString().trim();
		if(TextUtils.isEmpty(phoneNo)||TextUtils.isEmpty(holder)
				||TextUtils.isEmpty(cardNumber)||TextUtils.isEmpty(bank)
				||TextUtils.isEmpty(validDate)){
			showShortToast("请完善资料！");
			return;
		}
		
		if(phoneNo.length()!=11){
			showShortToast("手机号码不正确！");
			return;
		}
		userID = User.getInstance().getId();
		usertoken = User.getInstance().getToken();
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId",userID);
		params.addBodyParameter("token", usertoken);
		params.addBodyParameter("phoneNo", phoneNo);
		params.addBodyParameter("holder", holder);
		params.addBodyParameter("cardNumber", cardNumber);
		params.addBodyParameter("bank", bank);
		params.addBodyParameter("validDate", validDate);
		String url =  Constant.METHOD_save_bank;
		if(!TextUtils.isEmpty(id)){
			params.addBodyParameter("id", id);
			url = Constant.METHOD_bankCardUpdate;
		}
		
		httpUtils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				handleNetworkError();
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					JSONObject	jsonObjs = new JSONObject(arg0.result);
					String code = jsonObjs.getString("code");
					String msg = jsonObjs.getString("message");
					showShortToast(msg);
					if(code.equals("1")){
						finish();
					}
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==101){
			if(resultCode == 101){
			String date = data.getStringExtra("data");
			ed_date.setText(date);
			}
		}else if(requestCode == 102){
			if(resultCode == 101){
				String date = data.getStringExtra("data");
				ed_name.setText(date);
				}
		}
	}
	
	class MyWatcher implements TextWatcher {
		int beforeTextLength = 0;
		int onTextLength = 0;
		boolean isChanged = false;

		int location = 0;// 记录光标的位置
		private char[] tempChar;
		private StringBuffer buffer = new StringBuffer();
		int konggeNumberB = 0;

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			onTextLength = s.length();
			buffer.append(s.toString());
			if (onTextLength == beforeTextLength || onTextLength <= 3
					|| isChanged) {
				isChanged = false;
				return;
			}
			isChanged = true;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			beforeTextLength = s.length();
			if (buffer.length() > 0) {
				buffer.delete(0, buffer.length());
			}
			konggeNumberB = 0;
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == ' ') {
					konggeNumberB++;
				}
			}
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if (isChanged) {
				location = ed_no.getSelectionEnd();
				int index = 0;
				while (index < buffer.length()) {
					if (buffer.charAt(index) == ' ') {
						buffer.deleteCharAt(index);
					} else {
						index++;
					}
				}

				index = 0;
				int konggeNumberC = 0;
				while (index < buffer.length()) {
					if ((index == 4 || index == 9 || index == 14 || index == 19)) {
						buffer.insert(index, ' ');
						konggeNumberC++;
					}
					index++;
				}

				if (konggeNumberC > konggeNumberB) {
					location += (konggeNumberC - konggeNumberB);
				}

				tempChar = new char[buffer.length()];
				buffer.getChars(0, buffer.length(), tempChar, 0);
				String str = buffer.toString();
				if (location > str.length()) {
					location = str.length();
				} else if (location < 0) {
					location = 0;
				}

				ed_no.setText(str);
				Editable etable = ed_no.getText();
				Selection.setSelection(etable, location);
				isChanged = false;
			}
		}
	}

}
