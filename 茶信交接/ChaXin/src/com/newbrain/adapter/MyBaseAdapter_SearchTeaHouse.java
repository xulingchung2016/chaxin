package com.newbrain.adapter;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.chaxin.R;



public class MyBaseAdapter_SearchTeaHouse  extends BaseAdapter{
	
	
	
	
	private Context context;
	private LayoutInflater inflater;
	private List<Model> list;
	private int layoutID;
	
	public  MyBaseAdapter_SearchTeaHouse(Context context,List<Model> list,int layoutID)
	{
		this.context = context;
		this.list = list;
		this.layoutID = layoutID;
		inflater = LayoutInflater.from(this.context);
		
	}
	
	
	public List<Model> getList() {
		return list;
	}




	public void setList(List<Model> list) {
		this.list = list;
		
		notifyDataSetChanged();
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
			convertView = inflater.inflate(layoutID, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			
			
			
			
			
			convertView.setTag(viewHolder);			
			
		}else
		{
			
			
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		return convertView;
	}
	
	
	
	public class ViewHolder
	{
		
		@ViewInject(R.id.search_tea_house_name)
		private TextView tv_name;
		
		

		@ViewInject(R.id.search_tea_house_address)
		private TextView tv_address;
		
		

		@ViewInject(R.id.my_beauty_appcation)
		private Button btn_name;
		
		
	}


}
