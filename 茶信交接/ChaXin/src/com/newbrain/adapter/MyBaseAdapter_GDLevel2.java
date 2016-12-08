package com.newbrain.adapter;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.chaxin.R;
import com.newbrain.model.Model_TeaMallGridview;



public class MyBaseAdapter_GDLevel2  extends BaseAdapter{
	
	
	private Context context;
	private LayoutInflater inflater;
	private List<Model_TeaMallGridview> list;
	
	public  MyBaseAdapter_GDLevel2(Context context,List<Model_TeaMallGridview> list)
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
			convertView = inflater.inflate(R.layout.tea_mall_gridview_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			
			
			
			
			
			convertView.setTag(viewHolder);			
			
		}else
		{
			
			
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		
		viewHolder.checkBox.setOnCheckedChangeListener(new MyOnChecked(position));
		
		viewHolder.checkBox.setText(list.get(position).getName());
		
		
		return convertView;
	}
	
	
	
	public class ViewHolder
	{
		
		@ViewInject(R.id.gridview_item_checkbox)
		private CheckBox checkBox;
		
		
	}
	
	
	public class MyOnChecked implements OnCheckedChangeListener
	{
		
		private int position;
		

		public MyOnChecked( int position) {
			super();
			// TODO Auto-generated constructor stub
			
			
			this.position = position;
			
			
			
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			
			
			list.get(position).setIscheck(isChecked);
			
			
			
		}
		
		
		
		
		
	}
	
	
	

}
