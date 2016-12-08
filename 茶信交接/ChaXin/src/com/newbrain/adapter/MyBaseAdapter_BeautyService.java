package com.newbrain.adapter;



import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.chaxin.R;
import com.newbrain.model.Model_BeautyServiceGD;



public class MyBaseAdapter_BeautyService  extends BaseAdapter{
	
	
	
	
	private Context context;
	private LayoutInflater inflater;
	private List<HashMap<String, String>> list;
	private int layoutID;
	
	public  MyBaseAdapter_BeautyService(Context context, List<HashMap<String, String>>  list,int layoutID)
	{
		this.context = context;
		this.list = list;
		this.layoutID = layoutID;
		inflater = LayoutInflater.from(this.context);
		
	}
	
	
	public List<HashMap<String, String>>  getList() {
		return list;
	}




	public void setList( List<HashMap<String, String>>  list) {
		this.list = list;
		
		notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
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
		
		HashMap<String,String> map = list.get(position);
		viewHolder.tv.setText(map.get("1"));
		String status = map.get("2");//0位未选
		if(status.equals("1"))
		{
			viewHolder.iv.setVisibility(View.VISIBLE);
			
		}else
		{
			
			viewHolder.iv.setVisibility(View.GONE);
		}
		
		return convertView;
	}
	
	
	
	public class ViewHolder
	{
		
		@ViewInject(R.id.beauty_service_gridview_item_tv)
		private TextView tv;
		
		@ViewInject(R.id.beauty_service_gridview_item_img)
		private ImageView iv;
	}


}
