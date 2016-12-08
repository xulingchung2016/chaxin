/**************************************************************************************
 * [Project]
 *       MyProgressDialog
 * [Package]
 *       com.lxd.widgets
 * [FileName]
 *       CustomProgressDialog.java
 * [Copyright]
 *       Copyright 2012 LXD All Rights Reserved.
 * [History]
 *       Version          Date              Author                        Record
 *--------------------------------------------------------------------------------------
 *       1.0.0           2012-4-27         lxd (rohsuton@gmail.com)        Create
 **************************************************************************************/

package com.newbrain.customview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newbrain.chaxin.R;


public class MoreDialog extends Dialog {

//	private Context mcontext;
	private static int default_width = LayoutParams.WRAP_CONTENT; // 默认宽度
	private static int default_height = LayoutParams.WRAP_CONTENT;// 默认高度
	private LinearLayout more_bg;
	private RelativeLayout home_bg;
	private RelativeLayout msg_bg;
	private RelativeLayout share_bg;

	public RelativeLayout getHome_bg() {
		return home_bg;
	}

	public void setHome_bg(RelativeLayout home_bg) {
		this.home_bg = home_bg;
	}

	public LinearLayout getMore_bg() {
		return more_bg;
	}

	public void setMore_bg(LinearLayout more_bg) {
		this.more_bg = more_bg;
	}

	public RelativeLayout getMsg_bg() {
		return msg_bg;
	}

	public void setMsg_bg(RelativeLayout msg_bg) {
		this.msg_bg = msg_bg;
	}

	public RelativeLayout getShare_bg() {
		return share_bg;
	}

	public void setShare_bg(RelativeLayout share_bg) {
		this.share_bg = share_bg;
	}
	
	public MoreDialog(Context context,int x,int y,boolean isShare){
		this(context,x,y,isShare,R.layout.moredialog, R.style.MyDialogStyle);
	}
	
	public MoreDialog(Context context,final int x,final int y,boolean isShare, int layout, int style) {
		super(context, style);
		// set content
		setContentView(layout);
//		mcontext = context;
		// set window params
		Resources resources = context.getResources();
		final DisplayMetrics dm = resources.getDisplayMetrics();
		
		// set width,height by density and gravity

		more_bg = (LinearLayout) findViewById(R.id.more_dialog_bg);
		home_bg = (RelativeLayout) findViewById(R.id.home_bg);
		msg_bg = (RelativeLayout) findViewById(R.id.msg_bg);
		msg_bg.setVisibility(View.GONE);
		share_bg = (RelativeLayout) findViewById(R.id.share_bg);
		if(!isShare){
			share_bg.setVisibility(View.GONE);
		}
		ViewTreeObserver vto = more_bg.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			boolean hasMeasured = false;
			@SuppressLint("NewApi")
			public boolean onPreDraw() {
				if (hasMeasured == false) {
					hasMeasured = true;
					Window window = getWindow();
					WindowManager.LayoutParams params = window.getAttributes();
					params.width = default_width;
					params.height = default_height;
					params.x = x - more_bg.getWidth();
					params.y = y;
//					System.out.println("x:"+x+",params.x"+params.x+",y:"+y+",widthPixels:"+dm.widthPixels+",heightPixels:"+dm.heightPixels);
					params.gravity = Gravity.TOP|Gravity.LEFT;
					params.dimAmount=0.0f;
					window.setAttributes(params);
				}
				
				more_bg.getViewTreeObserver().removeOnPreDrawListener(this);
				return true;
			}
		});
		
	}
	
	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
	}
	
}
