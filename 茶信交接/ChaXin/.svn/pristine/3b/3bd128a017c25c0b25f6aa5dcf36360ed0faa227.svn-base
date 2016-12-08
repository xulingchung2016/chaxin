package com.newbrain.adapter;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.chaxin.R;
import com.newbrain.model.MyCrowdfBean.MyCrowdfInfo;



public class MyAdapter_ZhongChouCrowdfundingRecordListView  extends BaseAdapter{
	
	
	private Context context;
	private LayoutInflater inflater;
	private List<MyCrowdfInfo> list;
	
	public  MyAdapter_ZhongChouCrowdfundingRecordListView(Context context,List<MyCrowdfInfo> list)
	{
		this.context = context;
		this.list = list;
		
		inflater = LayoutInflater.from(this.context);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list != null ? list.size():10;
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
			convertView = inflater.inflate(R.layout.tea_zhongchou_crowdfunding_record_list_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			
			convertView.setTag(viewHolder);			
			
		}else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.tv_time.setText(list.get(position).getCreateDate());
		viewHolder.tv_tea_money_nu.setText(list.get(position).getGold());
		
		
		return convertView;
	}
	
	
	
	public class ViewHolder
	{
		@ViewInject(R.id.description)
		TextView tv_description;
		
		@ViewInject(R.id.time)
		TextView tv_time;
		
		@ViewInject(R.id.tea_money_nu)
		TextView tv_tea_money_nu;
	}

}
