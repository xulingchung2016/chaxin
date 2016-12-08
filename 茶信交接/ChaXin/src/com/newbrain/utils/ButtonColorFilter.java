package com.newbrain.utils;

import android.graphics.ColorMatrixColorFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;

/**
 * ��view ����ɫ���й���
 * 
 * @description : �Ե���İ�ť�Ƚ�����ɫ����,ʵ�ֵ��Ч�� ���÷���:public final static void
 *              setButtonFocusChanged(View inView)
 * @package com.unitepower.mcd.util
 * @title:ButtonColorFilter.java
 * @author : email:xiangyanhui@unitepower.net
 * @date :2012-5-4 ����02:23:23
 * @version : v1.0
 */
public class ButtonColorFilter {

	private final static float[] BT_SELECTED = new float[] { 1, 0, 0, 0, 50, 0,
			1, 0, 0, 50, 0, 0, 1, 0, 50, 0, 0, 0, 1, 0 };

	/**
	 * ��ť�ָ�ԭ״����ɫ����
	 */
	private final static float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0,
			0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

	/**
	 * ��ť����ı�
	 */
	private final static OnFocusChangeListener buttonOnFocusChangeListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
				v.getBackground().setColorFilter(
						new ColorMatrixColorFilter(BT_SELECTED));
				v.setBackgroundDrawable(v.getBackground());
			} else {
				v.getBackground().setColorFilter(
						new ColorMatrixColorFilter(BT_NOT_SELECTED));
				v.setBackgroundDrawable(v.getBackground());
			}
		}
	};

	/**
	 * ��ť��������Ч��
	 */
	private final static OnTouchListener buttonOnTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {

			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				v.getBackground().setColorFilter(
						new ColorMatrixColorFilter(BT_SELECTED));
				v.setBackgroundDrawable(v.getBackground());
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				v.getBackground().setColorFilter(
						new ColorMatrixColorFilter(BT_NOT_SELECTED));
				v.setBackgroundDrawable(v.getBackground());
			} else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
				v.getBackground().setColorFilter(
						new ColorMatrixColorFilter(BT_NOT_SELECTED));
				v.setBackgroundDrawable(v.getBackground());
			}
			return false;
		}
	};

	/**
	 * ����ͼƬ��ť��ȡ����ı�״̬
	 * 
	 * @param inImageButton
	 */
	public final static void setButtonFocusChanged(View inView) {
		inView.setOnTouchListener(buttonOnTouchListener);
		inView.setOnFocusChangeListener(buttonOnFocusChangeListener);
	}
}
