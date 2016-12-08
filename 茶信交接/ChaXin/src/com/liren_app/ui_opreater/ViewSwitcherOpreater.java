package com.liren_app.ui_opreater;

import java.util.List;

import com.newbrain.chaxin.R;

import android.app.Activity;
import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;
import android.widget.ViewSwitcher.ViewFactory;

public class ViewSwitcherOpreater {

	private Context mContext;
	private ViewSwitcher mSwicher;
//	private int[] mImgs;
	private List<String> mImgs;
	private HuaListener mlistener;

	public ViewSwitcherOpreater(ViewSwitcher _Swicher, HuaListener listener,
			Context _Context) {
		this.mSwicher = _Swicher;
		this.mContext = _Context;
		mlistener = listener;
	}

	public void setImgs( List<String>  ress) {
		mImgs = ress;
		MyOnGestureListener myOnGestureListener = new MyOnGestureListener(
				mlistener, mSwicher, mImgs);
		GestureDetector myDesDet = new GestureDetector(myOnGestureListener);
		ViewFactory viewFactory = new myViewFactory(mContext, myDesDet);

		mSwicher.setFactory(viewFactory);
		mlistener.getNext(mSwicher, mImgs);
	}

	class myViewFactory implements ViewFactory {
		private GestureDetector desDet;
		private Context mContext;

		public myViewFactory(Context _mContext, GestureDetector _desDet) {
			mContext = _mContext;
			desDet = _desDet;
		}

		@Override
		public View makeView() {

			ImageView img = (ImageView) ((Activity) mContext)
					.getLayoutInflater().inflate(R.layout.img_lay, null);

			img.setOnTouchListener(new MyOnTouchListener(desDet));
			return img;
		}
	}

	class MyOnTouchListener implements OnTouchListener {
		private GestureDetector desDet;

		public MyOnTouchListener(GestureDetector _desDet) {
			desDet = _desDet;
		}

		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			return desDet.onTouchEvent(arg1);
		}
	}
}
