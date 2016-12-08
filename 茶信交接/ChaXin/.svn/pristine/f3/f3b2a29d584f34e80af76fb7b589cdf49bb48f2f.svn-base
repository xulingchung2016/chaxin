package com.newbrain.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**在scrollview 里面显示*/
public class MyViewPagerDetail extends ViewPager{

	public MyViewPagerDetail(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyViewPagerDetail(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	
	

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent arg0) 
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) 
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	

	@Override
	protected void onMeasure(int widthMeasureSpec, int   heightMeasureSpec)
	{
		// TODO Auto-generated method stub
		  int viewHeight = 0;
          View childView = getChildAt(getCurrentItem());
          
          if(childView != null)
          {
        	  
        	  childView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0,  MeasureSpec.UNSPECIFIED));
              viewHeight = childView.getMeasuredHeight();
                  heightMeasureSpec = MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY);
          }
          
          super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			
	}
}
