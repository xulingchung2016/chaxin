package com.newbrain.customview;

import com.newbrain.chaxin.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public class PopWindowDetail {
	
	
	private Context context;
	private PopupWindow popupWindow ;

	public PopWindowDetail(Context context) {
		super();
		// TODO Auto-generated constructor stub
		
		this.context = context;
		
		
		View viewPop = LayoutInflater.from(context).inflate(R.layout.popwindow_detail, null);
		
		popupWindow = new PopupWindow(viewPop,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		
		popupWindow.setOutsideTouchable(true);
		
		popupWindow.setAnimationStyle(R.style.pop_animation_style);
		
	}
	
	
	public void dismiss()
	{
		
		if(popupWindow.isShowing())
		{
			
			popupWindow.dismiss();
			
		}
		
		
	}
	
	public void show(View view)
	{
		
		if(!popupWindow.isShowing())
		{
			
			popupWindow.showAsDropDown(view);
			
			popupWindow.update();
		}
		
		
	}
	
	
	

}
