package com.newbrain.viewflow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class PullToRefreshListView extends ListView implements OnScrollListener 
{
	private final static int RELEASE_To_REFRESH = 0;// 下拉过程的状态值  
	private final static int PULL_To_REFRESH = 1; // 从下拉返回到不刷新的状态值  
	private final static int REFRESHING = 2;// 正在刷新的状态值  
	private final static int DONE = 3;
	private final static int LOADING = 4;
	
	private int state;
	private boolean isRefreshable;
	private OnRefreshListener refreshListener;

	public PullToRefreshListView(Context context, AttributeSet attrs) 
	{
		super(context,attrs);
		// TODO Auto-generated constructor stub
		
		init(context);
	}
	
	private void init(Context context)
	{
		// 设置滚动监听事件  
		setOnScrollListener(this);
		
		// 一开始的状态就是下拉刷新完的状态，所以为DONE
		state = DONE;
		
		// 是否正在刷新  
		isRefreshable = false;
	}

	@Override
	public void onScroll(AbsListView arg0, int firstVisibleItem, int visibleItemCount, int totalItemCount) 
	{
		// TODO Auto-generated method stub
		if (firstVisibleItem == 0)
		{
			isRefreshable = true;
		}
		else
		{
			isRefreshable = false;
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) 
	{
		// TODO Auto-generated method stub
		
	}
	
	
	@Override  
	public boolean onTouchEvent(MotionEvent ev)
	{
		if (isRefreshable)
		{
			switch (ev.getAction())
			{
			case MotionEvent.ACTION_DOWN:

				break;
			case MotionEvent.ACTION_UP:

				break;
				case MotionEvent.ACTION_MOVE:
					
					break;					
				default:
					break;					
			}
			
		}
		return super.onTouchEvent(ev);
	}
	
	public void setonRefreshListener(OnRefreshListener refreshListener) 
	{
		this.refreshListener = refreshListener;		
	}

	public interface OnRefreshListener 
	{  
		public void onRefresh();		
	}
}
