package com.liren_app.ui_opreater;

import java.util.Date;
import java.util.List;

import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.ViewSwitcher;

public class MyOnGestureListener implements OnGestureListener {
	private HuaListener listener;
	private static Date lastTime = null;
	private ViewSwitcher mSwitcher;
	private  List<String> mImgs;

	public MyOnGestureListener(HuaListener a, ViewSwitcher _Switcher,  List<String>  imgs) {
		listener = a;
		mSwitcher = _Switcher;
		mImgs = imgs;
	}

	@Override
	// 第三个参数是：滑动手势在水平方向上的位移量
	// 第四个参数是：滑动手势在垂直方向上的位移量
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// 获取当前时间
		Date curDate = new Date(System.currentTimeMillis());
		float shijiancha = 1000;
		if (lastTime != null) {
			shijiancha = curDate.getTime() - lastTime.getTime();
		}
		// 对比两次滑动手势之间的时间间隔。
		// 由于对屏幕的一次滑动可能会被GestureDetector识别到好几次手势，所以需要过滤掉时间间隔太短的手势。
		if (shijiancha > 500) {
			if (distanceX > 10) {
				listener.getNext(mSwitcher, mImgs);
			}
			if (distanceX < -10) {
				listener.getPrev(mSwitcher, mImgs);
			}
		}
		lastTime = curDate;
		return true;
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return true;
	}
}