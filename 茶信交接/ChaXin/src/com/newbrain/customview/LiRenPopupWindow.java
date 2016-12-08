package com.newbrain.customview;

import com.newbrain.chaxin.MainActivity;
import com.newbrain.chaxin.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


public class LiRenPopupWindow extends PopupWindow {

	private ListView mDistrictGridView;

	public LiRenPopupWindow(Context context, OnClickListener clickHandler) {

		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View interalView = inflater.inflate(R.layout.pop_liren, null);

		setContentView(interalView);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.MATCH_PARENT);
		setFocusable(true);

		setTouchable(true);
		setOutsideTouchable(true);
		setBackgroundDrawable(new BitmapDrawable(context.getResources(),
				(Bitmap) null));


		mDistrictGridView = (ListView) interalView
				.findViewById(R.id.pop_list);

	}
	public ListView getDistrictGridView() {
		return mDistrictGridView;
	}
	public void setBackgroundResource(int arrowDownWhite) {
		// TODO Auto-generated method stub
		
	}
	

}
