package com.newbrain.customview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.newbrain.chaxin.R;

public class CustomDialog extends Dialog {
	private Context mContext;
	private View view;
	private TextView tvTitle, tvContent;// ����,����
	private ImageView ivContent;
	private EditText etContent,etContent2;
	
	private Button btnLeft, btnRight;// ȡ��,ȷ����ť
	private StateListDrawable stateListDrawable;
	private boolean isPass;//是否是密码输入

	/**
	 * Dialog ������
	 * 
	 * @param context
	 *            ������
	 * @param idNormal
	 * @param idPressed
	 * @param idUnavailable
	 */
	public CustomDialog(Context context,boolean isPass) {
		this(context, R.style.MyDialogStyle);
		this.isPass = isPass;
		mContext = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		view = LayoutInflater.from(mContext).inflate(
				R.layout.withdraw_dialog, null);
		setCanceledOnTouchOutside(false);// Ĭ�ϵ����Χ����ȡ��
		initView(view);
	}

	@SuppressLint("InflateParams")
	public CustomDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		mContext = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		view = LayoutInflater.from(mContext).inflate(
				R.layout.withdraw_dialog, null);
		setCanceledOnTouchOutside(false);// Ĭ�ϵ����Χ����ȡ��
		initView(view);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// btnLeft.setBackgroundResource(R.drawable.dialog_left_btn_bg_selector);
		setContentView(view);
	}


	public EditText getEtContent2() {
		return etContent2;
	}

	public void setEtContent2(EditText etContent2) {
		this.etContent2 = etContent2;
	}

	public void setDialogTitle(String str) {
		tvTitle.setText(str);
	}

	public void setDialogTitle(int strId) {
		tvTitle.setText(strId);
	}

	public void setDialogContent(int conetId) {
		tvContent.setVisibility(View.VISIBLE);
		tvContent.setText(conetId);
	}

	public void setDialogContent(String content) {
		tvContent.setVisibility(View.VISIBLE);
		tvContent.setText(content);
	}

	public void setLeftBtnText(String content) {
		btnLeft.setText(content);
	}

	public void setLeftBtnText(int contentId) {
		btnLeft.setText(contentId);
	}

	public void setRightBtnText(String content) {
		btnRight.setText(content);
	}

	public void setRightBtnText(int contentId) {
		btnRight.setText(contentId);
	}

	// ��:���������С

	/**
	 * ���öԻ�����������С
	 * 
	 * @param titleSizeId
	 */
	@SuppressLint("ShowToast")
	public void setDialogTitleSize(int titleSizeId) {
		// ��������ߴ������px,�����dp����sp,��Ҫ����density
		float mSize = mContext.getResources()
				.getDimensionPixelSize(titleSizeId);
		tvTitle.setTextSize(mSize);
	}

	/**
	 * ���öԻ������������С
	 * 
	 * @param titleSizeId
	 */
	public void setDialogContentSize(int contentSizeId) {
		// ��������ߴ������px,�����dp����sp,��Ҫ����density
		float mSize = mContext.getResources().getDimensionPixelSize(
				contentSizeId);
		tvContent.setTextSize(mSize);
	}

	/**
	 * ����������ť�������С
	 * 
	 * @param titleSizeId
	 */
	public void setDialogBtnSize(int contentSizeId) {
		// ��������ߴ������px,�����dp����sp,��Ҫ����density
		float mSize = mContext.getResources().getDimensionPixelSize(
				contentSizeId);
		btnLeft.setTextSize(mSize);
		btnRight.setTextSize(mSize);
	}

	/**
	 * ��:���ضԻ�������
	 */
	public void hideDialogContent() {
		tvContent.setVisibility(View.GONE);
	}
	
	public void hideDialogEdittext() {
		etContent2.setVisibility(View.GONE);
		etContent.setVisibility(View.GONE);
	}

	// ��:������ɫ

	/**
	 * ���öԻ���ı��ⱳ��ɫ
	 */
	public void setDialogTitleBacColor(int colorId) {
		int mColor = mContext.getResources().getColor(colorId);
		GradientDrawable bgShape = (GradientDrawable) tvTitle.getBackground();
		bgShape.setColor(mColor);
	}

	/**
	 * �������ݵı���ɫ
	 */
	public void setDialogContentBacColor(int colorId) {
		int mColor = mContext.getResources().getColor(colorId);
		tvContent.setBackgroundColor(mColor);
	}

	/**
	 * ����������ť��������ɫ
	 * 
	 * @param colorLeft
	 *            ��߰�ť��������ɫ
	 * @param ColorRight
	 *            �ұ߰�ť��������ɫ
	 */
	public void setDialogBtnTextColor(int colorLeft, int ColorRight) {
		int mColorLeft = mContext.getResources().getColor(colorLeft);
		int mColorRight = mContext.getResources().getColor(ColorRight);
		btnLeft.setTextColor(mColorLeft);
		btnRight.setTextColor(mColorRight);
	}

	// ��:���õ���¼�

	/**
	 * �Ի�����ఴť�ĵ���¼�
	 * 
	 * @param listener
	 */
	public void setLeftBtnListener(android.view.View.OnClickListener listener) {
		this.dismiss();
		btnLeft.setOnClickListener(listener);
	}

	/**
	 * �Ի����Ҳఴť�ĵ���¼�
	 * 
	 * @param listener
	 */
	public void setRightBtnListener(android.view.View.OnClickListener listener) {
		this.dismiss();
		btnRight.setOnClickListener(listener);
	}

	/**
	 * @param context
	 *            ������
	 * @param idNormal
	 *            ����״̬��ͼƬ��ԴID
	 * @param idPressed
	 *            ����״̬��ͼƬ��ԴID
	 * @param idUnavailable
	 *            ������״̬��ͼƬ��ԴID
	 */
	public void setLeftBtnSelector(int colorIdNormal,
			int colorIdPressed, int colorIdUnavailable) {

		// ����ͼƬ
		/*GradientDrawable drawableNormal = (GradientDrawable) mContext
				.getResources().getDrawable(R.drawable.dialog_left_btn_bg);
		drawableNormal
				.setColor(mContext.getResources().getColor(colorIdNormal));
		drawableNormal.invalidateSelf();
		// ����ͼƬ
		GradientDrawable drawablePre = (GradientDrawable) mContext
				.getResources().getDrawable(
						R.drawable.dialog_left_btn_bg_pressed);
		drawablePre.setColor(mContext.getResources().getColor(colorIdPressed));
		drawablePre.invalidateSelf();
		// ʧЧͼƬ
		GradientDrawable drawableUnavailable = (GradientDrawable) mContext
				.getResources().getDrawable(
						R.drawable.dialog_left_btn_bg_unavailable);
		drawableUnavailable.setColor(mContext.getResources().getColor(
				colorIdUnavailable));
		stateListDrawable=new StateListDrawable();
		// ���ɵ��״̬
		stateListDrawable.addState(
				new int[] { -android.R.attr.state_enabled },
				mContext.getResources().getDrawable(
						R.drawable.dialog_left_btn_bg));
		// ���״̬
		stateListDrawable.addState(
				new int[] { android.R.attr.state_pressed },
				mContext.getResources().getDrawable(
						R.drawable.dialog_left_btn_bg_pressed));
		// ��ͨ״̬
		stateListDrawable.addState(new int[] { -android.R.attr.state_focused,
				-android.R.attr.state_pressed }, mContext.getResources()
				.getDrawable(R.drawable.dialog_left_btn_bg));

		btnLeft.setBackgroundDrawable(stateListDrawable);*/

		// btnLeft.setEnabled(false);
		/*
		 * //����ͼƬ GradientDrawable drawableNormal=(GradientDrawable)
		 * context.getResources().getDrawable(R.drawable.dialog_left_btn_bg);
		 * drawableNormal
		 * .setColor(context.getResources().getColor(R.color.red)); //����ͼƬ
		 * GradientDrawable drawablePre=(GradientDrawable)
		 * context.getResources()
		 * .getDrawable(R.drawable.dialog_left_btn_bg_pressed);
		 * drawablePre.setColor
		 * (context.getResources().getColor(R.color.moccasin)); //ʧЧͼƬ
		 * GradientDrawable drawableUnavailable=(GradientDrawable)
		 * context.getResources
		 * ().getDrawable(R.drawable.dialog_left_btn_bg_unavailable);
		 * drawableUnavailable
		 * .setColor(context.getResources().getColor(R.color.black));
		 */

	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		tvTitle = (TextView) view.findViewById(R.id.dialog_title);
		initDefaultColor();
		tvContent = (TextView) view.findViewById(R.id.dialog_content_tv);
		etContent = (EditText) view.findViewById(R.id.dialog_content_et);
		etContent2 = (EditText) view.findViewById(R.id.dialog_content_et2);
		if(isPass)etContent.setVisibility(View.GONE);
		else etContent2.setVisibility(View.GONE);
		
		ivContent = (ImageView) view.findViewById(R.id.dialog_content_img);
		btnLeft = (Button) view.findViewById(R.id.dialog_btn_cancel);
		btnRight = (Button) view.findViewById(R.id.dialog_btn_confirm);
	}

	public EditText getEtContent() {
		return etContent;
	}

	public void setEtContent(EditText etContent) {
		this.etContent = etContent;
	}

	@SuppressLint("ResourceAsColor")
	private void initDefaultColor() {
//		setDialogTitleBacColor(R.color.m29c741);
	}

}
