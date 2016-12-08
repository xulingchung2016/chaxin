package com.newbrain.adapter;


import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.chaxin.R;
import com.newbrain.model.TeaCiShanListProjectBean.CiShanProjectIntro;
import com.newbrain.xutils.Xutils_BitmapUtils;



public class MyAdapter_CiShanPublicBenefitListView  extends BaseAdapter{
	
	private Context context;
	private LayoutInflater inflater;
	private BitmapUtils bitmapUtils;
	private List<CiShanProjectIntro> list;
	
	public  MyAdapter_CiShanPublicBenefitListView(Context context,List<CiShanProjectIntro> list)
	{
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(this.context);
		bitmapUtils = Xutils_BitmapUtils.getbitmapUtils_detail(context);
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
			convertView = inflater.inflate(R.layout.tea_cishan_public_benefit_list_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			convertView.setTag(viewHolder);			
		}else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		CiShanProjectIntro ciShanProjectIntro = list.get(position);
		bitmapUtils.display(viewHolder.icon, ciShanProjectIntro.getSamllImage());
		viewHolder.tv_title.setText(ciShanProjectIntro.getTitle());
		viewHolder.tv_content.setText(ciShanProjectIntro.getDigest());
		viewHolder.tv_love_nu.setText(ciShanProjectIntro.getLove_count());
		
		return convertView;
	}
	
	
	
	public class ViewHolder
	{
		
		@ViewInject(R.id.icon)
		ImageView icon;

		@ViewInject(R.id.title)
		TextView tv_title;
		
		@ViewInject(R.id.content)
		TextView tv_content;
		
		@ViewInject(R.id.love_nu)
		TextView tv_love_nu;
	}

}
