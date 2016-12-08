package com.newbrain.chaxin.teazhongchou;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.newbrain.chaxin.R;

public class SetWithrawPasswdDialog extends Dialog {

//	private Context mcontext;
	private static int default_width = 250; // 默认宽度
	private static int default_height = 110;// 默认高度
	private TextView dialog_ok_bt, dialog_cancle_bt;
	private TextView dialog_message;
	private OnSetWithdrawPasswdListener mOnSetWithdrawPasswdListener;

	public SetWithrawPasswdDialog(Context context){
		this(context, default_width, default_height, R.layout.tea_zhongchou_setwithrawpasswd_dialog, R.style.MyDialogStyle);
	}
	
	public SetWithrawPasswdDialog(Context context, int layout, int style) {
		this(context, default_width, default_height, layout, style);
	}

	public SetWithrawPasswdDialog(Context context, int width, int height, int layout, int style) {
		super(context, style);
		// set content
		setContentView(layout);
//		mcontext = context;
		dialog_ok_bt = (TextView) findViewById(R.id.dialog_right);
		dialog_cancle_bt = (TextView) findViewById(R.id.dialog_left);
		dialog_message = (TextView) findViewById(R.id.dialog_msg);
		// set window params
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		// set width,height by density and gravity
		float density = getDensity(context);
		params.width = (int) (width * density);
		params.height = (int) (height * density);
		params.gravity = Gravity.CENTER;
//		params.dimAmount=0.0f;
		window.setAttributes(params);
		dialog_ok_bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mOnSetWithdrawPasswdListener != null){
					mOnSetWithdrawPasswdListener.onRightBtListener();
				}
				SetWithrawPasswdDialog.this.dismiss();
			}
		});
		dialog_cancle_bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mOnSetWithdrawPasswdListener != null){
					mOnSetWithdrawPasswdListener.onLeftBtListener();
				}
				SetWithrawPasswdDialog.this.dismiss();
			}
		});
	}
	
	public void setOnSetWithdrawPasswdListener(OnSetWithdrawPasswdListener onSetWithdrawPasswdListener){
		mOnSetWithdrawPasswdListener = onSetWithdrawPasswdListener;
	}
	
	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
		if(mOnSetWithdrawPasswdListener != null){
			mOnSetWithdrawPasswdListener.onDismiss();
		}
	}

	private float getDensity(Context context) {
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.density;
	}

	public void setMessage(String message) {
		dialog_message.setText(message);
	}
	public void setMessage(int res) {
		dialog_message.setText(res);
	}
	
	interface OnSetWithdrawPasswdListener{
		public void onRightBtListener();
		public void onLeftBtListener();
		public void onDismiss();
	}
}