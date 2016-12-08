package com.liren_app.ui.dia;

import com.newbrain.chaxin.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class LoadingDialog extends Dialog {

	@Override
	public void show() {
		super.show();
		Animation operatingAnim = AnimationUtils.loadAnimation(mContext,
				R.anim.yy_turn_around_anim);
		img_loading.startAnimation(operatingAnim);
	}

	private Context mContext;
	private ImageView img_loading;

	private LoadingDialog(Context context) {
		super(context);
	}

	public static LoadingDialog mDia;

	public static LoadingDialog getInstance(Context _context) {
		if (mDia != null && mDia.isShowing())
			mDia.cancel();
		mDia = new LoadingDialog(_context);
		mDia.mContext = _context;
		mDia.initDia();
		return mDia;
	}

	private void initDia() {

		img_loading = (ImageView) View.inflate(mContext,
				R.layout.dialog_loading, null);
		this.setContentView(img_loading);
		ColorDrawable colorDrawable = new ColorDrawable(Color.argb(0, 0, 0, 0));
		this.getWindow().setBackgroundDrawable(colorDrawable);
		this.setCancelable(true);
	}
}
