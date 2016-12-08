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
import com.newbrain.model.TeaCiShanMyDonateDetailListBean.CiShanMyDonateDetail;



public class MyAdapter_CiShanMyDonateRecordListView  extends BaseAdapter{
	
	
	private Context context;
	private LayoutInflater inflater;
	private List<CiShanMyDonateDetail> list;
	
	public  MyAdapter_CiShanMyDonateRecordListView(Context context,List<CiShanMyDonateDetail> list)
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
			convertView = inflater.inflate(R.layout.tea_cishan_my_donate_record_list_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			
			convertView.setTag(viewHolder);			
			
		}else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		CiShanMyDonateDetail ciShanMyDonateDetail = list.get(position);
		viewHolder.tv_title.setText(ciShanMyDonateDetail.getTitle());
		viewHolder.tv_time.setText(ciShanMyDonateDetail.getCreate_time());
		viewHolder.tv_donate_nu.setText(ciShanMyDonateDetail.getGold()+"茶币");
		
		
		return convertView;
	}
	
	
	
	public class ViewHolder
	{

		@ViewInject(R.id.title)
		TextView tv_title;
		
		
		@ViewInject(R.id.time)
		TextView tv_time;

		@ViewInject(R.id.donate_nu)
		TextView tv_donate_nu;
		
		@ViewInject(R.id.donate_status)
		TextView tv_donate_status;
	}

}
