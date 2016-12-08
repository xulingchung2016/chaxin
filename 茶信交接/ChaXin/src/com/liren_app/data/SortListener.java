package com.liren_app.data;

import com.newbrain.chaxin.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SortListener implements OnClickListener {

	private Button[] mBtns;
	private Context mContext;
	private ISetItemsData mSet;

	public SortListener(Button[] btns, Context context, ISetItemsData set) {
		this.mBtns = btns;
		mContext = context;
		mSet = set;
		for (int i = 0; i < mBtns.length; i++) {
			mBtns[i].setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		for (int i = 0; i < mBtns.length; i++) {
			if (mBtns[i].getId() == v.getId()) {

				mBtns[i].setTextColor(mContext.getResources().getColor(
						R.color.txt_orange));
				mSet.setData(mBtns[i]);

			} else
				mBtns[i].setTextColor(mContext.getResources().getColor(
						R.color.txt_black));
		}
	}
}
