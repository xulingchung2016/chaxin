package com.newbrain.adapter;


import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.chaxin.R;
import com.newbrain.model.Model_Tab1Gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class MyAdapter_Tab1Gridview  extends BaseAdapter{
	
	
	private Context context;
	private LayoutInflater inflater;
	private List<Model_Tab1Gridview> list;
	
	public  MyAdapter_Tab1Gridview(Context context,List<Model_Tab1Gridview> list)
	{
		this.context = context;
		this.list = list;
		
		inflater = LayoutInflater.from(this.context);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
		ViewHolder viewHolder;
		
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.tab1_gridview_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			
			
			
			
			
			convertView.setTag(viewHolder);			
			
		}else
		{
			
			
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		viewHolder.imageView.setImageResource(list.get(position).getPicture());
		viewHolder.tv_name.setText(list.get(position).getName());
		
		
		return convertView;
	}
	
	
	
	public class ViewHolder
	{
		
		@ViewInject(R.id.tab1_gridview_textview)
		TextView tv_name;
		
		@ViewInject(R.id.tab1_gridview_imageview)
		ImageView imageView;
	}

}
