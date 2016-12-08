package com.newbrain.chaxin.teazhongchou;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.BaseJsonDataBean;
import com.newbrain.model.Bean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.utils.ToastUtil;

/**
 * 茶众筹提现密码设置界面
 * 
 * @author ZY
 * 
 */
public class TeaZhongChouSetWithdrawDepositFragment extends Fragment implements
		OnClickListener {

	private TeaZhongChouActivity mParentsActivity;
	private EditText user_id_edit;
	private EditText userphone_edit;
	private EditText userpassword_edit;
	private EditText userpassword_affirm_edit;
	private Button ok_bt;
	private JsonThread mThread;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case Constant.FLAG_SET_PWD:
				CustomProgressDialog.stopProgressDialog();
				HttpReturnData mData = (HttpReturnData) msg.obj;
				if (mData.isSuccess()) {
					BaseJsonDataBean data = (BaseJsonDataBean) mData.getObg();
					if (data.getCode().equals("1")) {
						ToastUtil.Toast(mParentsActivity,getString(R.string.pwd_set_suc));
						mParentsActivity.removeFragerment(true);
						break;
					}
				}
				ToastUtil.Toast(mParentsActivity,getString(R.string.pwd_set_fail));
				break;
			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mParentsActivity = (TeaZhongChouActivity) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(
				R.layout.tea_zhongchou_set_withdraw_deposit_fragment,
				container, false);
		mParentsActivity
				.setTopBar(mParentsActivity.ZHONGCHOU_SET_WITHDRAW_DEPOSIT_PAGE);
		initView(view);
		setListener();
		initData();
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		user_id_edit = (EditText) view.findViewById(R.id.user_id_edit);
		userphone_edit = (EditText) view.findViewById(R.id.userphone_edit);
		userpassword_edit = (EditText) view
				.findViewById(R.id.userpassword_edit);
		userpassword_affirm_edit = (EditText) view
				.findViewById(R.id.userpassword_affirm_edit);
		ok_bt = (Button) view.findViewById(R.id.set_withdraw_password_ok);
	}

	private void setListener() {
		// TODO Auto-generated method stub
		ok_bt.setOnClickListener(this);
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	private void startSetPwdThread() {
		CustomProgressDialog.startProgressDialog(mParentsActivity, "");
		List<Bean> list = new ArrayList<Bean>();
		list.add(new Bean("userId", "10000001"));
		list.add(new Bean("phoneNo", userphone_edit.getText().toString()));
		list.add(new Bean("presentPassword", userpassword_edit.getText().toString()));
		list.add(new Bean("identity", user_id_edit.getText().toString()));
		if(mThread != null){
			mThread.cancleReturnData();
		}
		mThread = new JsonThread(mParentsActivity, list, mHandler, Constant.FLAG_SET_PWD);
		mThread.start();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.set_withdraw_password_ok:
			if (TextUtils.isEmpty(user_id_edit.getText().toString())) {
				ToastUtil.Toast(mParentsActivity, getString(R.string.id_null));
				user_id_edit.requestFocus();
				break;
			}
			if (TextUtils.isEmpty(userphone_edit.getText().toString())) {
				ToastUtil.Toast(mParentsActivity,
						getString(R.string.phone_null));
				userphone_edit.requestFocus();
				break;
			}
			if (TextUtils.isEmpty(userpassword_edit.getText().toString())) {
				ToastUtil.Toast(mParentsActivity, getString(R.string.pwd_null));
				userpassword_edit.requestFocus();
				break;
			}
			if (TextUtils
					.isEmpty(userpassword_affirm_edit.getText().toString())) {
				ToastUtil.Toast(mParentsActivity,
						getString(R.string.pwd_affirm_null));
				userpassword_affirm_edit.requestFocus();
				break;
			}
			if ((userpassword_edit.getText().toString())
					.equals(userpassword_affirm_edit.getText().toString())) {
				startSetPwdThread();
			} else {
				ToastUtil.Toast(mParentsActivity,
						getString(R.string.pwd_not_same));
				userpassword_edit.requestFocus();
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mThread != null){
			mThread.cancleReturnData();
			mThread = null;
		}
	}
	
}
