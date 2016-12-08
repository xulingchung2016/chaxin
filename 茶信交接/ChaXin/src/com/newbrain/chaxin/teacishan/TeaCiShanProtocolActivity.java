package com.newbrain.chaxin.teacishan;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.TeaCiShanProtocolBean;

public class TeaCiShanProtocolActivity extends BaseActivity{

	private String projectId;
	private ImageView back;
	private TextView mProtocol_tv;
	private JsonThread mThread;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case Constant.FLAG_GET_CISHAN_PROTOCOL:
				CustomProgressDialog.stopProgressDialog();
				HttpReturnData mData = (HttpReturnData) msg.obj;
				if(mData.isSuccess()){
					TeaCiShanProtocolBean teaCiShanProtocolBean = (TeaCiShanProtocolBean) mData.getObg();
					if(teaCiShanProtocolBean.getCode().equals("1")){
						mProtocol_tv.setText(teaCiShanProtocolBean.getAgreement());
					}
				}
				break;
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacishan_protocol);
		projectId = getIntent().getStringExtra("projectId");
		mProtocol_tv = (TextView) findViewById(R.id.protocol);
		back = (ImageView) findViewById(R.id.tea_cishan_protocol_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				TeaCiShanProtocolActivity.this.finish();
			}
		});
		startGetProtocolThread();
	}
	
	private void startGetProtocolThread(){
		CustomProgressDialog.startProgressDialog(this);
		List<Bean> list = new ArrayList<Bean>();
		list.add(new Bean("projectId", projectId));
		if(mThread != null){
			mThread.cancleReturnData();
		}
		mThread = new JsonThread(this, list, mHandler, Constant.FLAG_GET_CISHAN_PROTOCOL);
		mThread.start();
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
