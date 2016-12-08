package com.newbrain.customview;

import com.newbrain.chaxin.MainActivity;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.my.mybeauty.BeautyActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class CityChoosePopupWindow extends PopupWindow {

	private GridView mDistrictGridView;
	private LinearLayout mChangeCitylayout;
	private TextView mCurrentCityTextView;
	private Button btn_cancle,btn_save;

	public CityChoosePopupWindow(Context context, OnClickListener clickHandler,int flag) {

		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View interalView = inflater.inflate(R.layout.popup_city_choose, null);

		setContentView(interalView);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setFocusable(true);

		setTouchable(true);
		setOutsideTouchable(true);
		setBackgroundDrawable(new BitmapDrawable(context.getResources(),
				(Bitmap) null));

		mChangeCitylayout = (LinearLayout) interalView
				.findViewById(R.id.change_city_linearlayout);

		setCurrentCityTextView((TextView) interalView
				.findViewById(R.id.current_city));

		mDistrictGridView = (GridView) interalView
				.findViewById(R.id.district_gridview);

		mChangeCitylayout.setOnClickListener(clickHandler);
		RelativeLayout rl_top = (RelativeLayout) interalView.findViewById(R.id.rl_top);
		if(flag == 0){
			rl_top.setVisibility(View.GONE);
		getCurrentCityTextView().setText(
				((MainActivity) context).getCurrentCity());
		}else {
			btn_save = (Button) interalView.findViewById(R.id.btn_ok);
			btn_cancle = (Button) interalView.findViewById(R.id.btn_cancle);
			btn_cancle.setOnClickListener(clickHandler);
			btn_save.setOnClickListener(clickHandler);
			rl_top.setVisibility(View.VISIBLE);
			getCurrentCityTextView().setText(
					((BeautyActivity) context).getCurrentCity());
		}

	}

	public Button getBtn_cancle() {
		return btn_cancle;
	}

	public void setBtn_cancle(Button btn_cancle) {
		this.btn_cancle = btn_cancle;
	}

	public Button getBtn_save() {
		return btn_save;
	}

	public void setBtn_save(Button btn_save) {
		this.btn_save = btn_save;
	}

	public GridView getDistrictGridView() {
		return mDistrictGridView;
	}

	public void setmDistrictGridView(GridView mDistrictGridView) {
		this.mDistrictGridView = mDistrictGridView;
	}

	public TextView getCurrentCityTextView() {
		return mCurrentCityTextView;
	}

	private void setCurrentCityTextView(TextView mCurrentCityTextView) {
		this.mCurrentCityTextView = mCurrentCityTextView;
	}

}
