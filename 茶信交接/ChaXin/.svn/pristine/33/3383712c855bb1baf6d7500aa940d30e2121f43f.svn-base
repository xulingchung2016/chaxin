package com.newbrain.adapter;


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



public class MyBaseAdapter_FansDetail  extends BaseAdapter{
	
	
	private Context context;
	private LayoutInflater inflater;
	private List<Model> list;
	
	public  MyBaseAdapter_FansDetail(Context context,List<Model> list)
	{
		this.context = context;
		this.list = list;
		
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
			convertView = inflater.inflate(R.layout.tea_fans_detail_listview_item, null);
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
		
		@ViewInject(R.id.tea_fans_is_verity)
		private TextView tv_is_verity;
		
		@ViewInject(R.id.tea_fans_detail_name)
		private TextView tv_idetail_name;
		
		@ViewInject(R.id.tea_fans_qualified)
		private TextView tv_qualified;
		
		
		@ViewInject(R.id.tea_fans_not_phone_verity)
		private TextView tv_phone_verity;
		
		@ViewInject(R.id.tea_fans_imageview)
		private ImageView img_img;
		
		@ViewInject(R.id.tea_fans_detail_level)
		private TextView tv_level;
		
		@ViewInject(R.id.tea_fans_tip)
		private TextView btn_tip;
		
		
	}

}
