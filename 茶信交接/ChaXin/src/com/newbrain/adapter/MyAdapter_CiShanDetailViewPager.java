package com.newbrain.adapter;


import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.viewpagerindicator.MyDirectionalViewPager;



public class MyAdapter_CiShanDetailViewPager  extends PagerAdapter{
	
	
	private List<View> list;
	
	public  MyAdapter_CiShanDetailViewPager(List<View> list)
	{
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		((MyDirectionalViewPager)container).removeView(list.get(position));
	}

	@Override
	public View instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		((MyDirectionalViewPager)container).addView(list.get(position),0);
		return list.get(position);
	}

//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return list.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		
//		
//		ViewHolder viewHolder;
//		
//		if(convertView == null)
//		{
//			convertView = inflater.inflate(R.layout.tea_cishan_my_donate_list_item, null);
//			viewHolder = new ViewHolder();
//			ViewUtils.inject(viewHolder, convertView);
//			
//			convertView.setTag(viewHolder);			
//			
//		}else
//		{
//			viewHolder = (ViewHolder) convertView.getTag();
//		}
//		
////		viewHolder.icon.setImageResource(list.get(position));
////		viewHolder.tv_title.setText(list.get(position));
//		viewHolder.bt_again_donate.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				new DonateOrCrowdfundingDialog(context).show();
//			}
//		});
//		
//		
//		return convertView;
//	}
//	
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

}
