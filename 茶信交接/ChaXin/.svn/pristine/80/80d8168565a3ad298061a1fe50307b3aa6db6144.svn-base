package com.newbrain.viewflow;



import com.newbrain.chaxin.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ViewFlowIndicator extends LinearLayout {

	int totalCount,mCurrentIndex;
	
	public ViewFlowIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(LinearLayout.HORIZONTAL);
	}

	public void setToltal(int count) {
		removeAllViews();
		this.totalCount=count;
		for (int i = 0; i < count; i++) {
			ImageView img = new ImageView(getContext());
			img.setBackgroundResource(R.drawable.page_indicator_unfocused);
			LayoutParams ll=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			ll.setMargins(i==0?0:10, 0, 0, 0);
			img.setLayoutParams(ll);
			addView(img);
		}
		setCurrentItem(0);
	}

	public void setCurrentItem(int index) {
		mCurrentIndex=index;
		for (int i = 0; i < totalCount; i++) {
			if(index==i){
				getChildAt(index).setBackgroundResource(
						R.drawable.page_indicator_focused);
			}else{
				getChildAt(i).setBackgroundResource(
						R.drawable.page_indicator_unfocused);
			}
		}
	
	}
	
	public int getCurrentItem(){
		return mCurrentIndex;
	}
	
	public int getTotalCount(){
		return totalCount;
	}

}
