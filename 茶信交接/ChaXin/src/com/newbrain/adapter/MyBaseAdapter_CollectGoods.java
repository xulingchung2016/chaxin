package com.newbrain.adapter;



import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.chaxin.R;
import com.squareup.picasso.Picasso;



public class MyBaseAdapter_CollectGoods  extends BaseAdapter{
	
	
	private Context context;
	private LayoutInflater inflater;
	private List<HashMap<String, String>> list;
	
	public  MyBaseAdapter_CollectGoods(Context context,List<HashMap<String, String>> list)
	{
		this.context = context;
		this.list = list;
		
		inflater = LayoutInflater.from(this.context);
		
	}
	
	
	public List<HashMap<String, String>> getList() {
		return list;
	}




	public void setList(List<HashMap<String, String>> list) {
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
			convertView = inflater.inflate(R.layout.goods_collect_fragment_listview_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			
			
			
			
			
			convertView.setTag(viewHolder);			
			
		}else
		{
			
			
			viewHolder = (ViewHolder) convertView.getTag();
		}
//		{id=5a740a0e50f1eed90150f44f5e110012, goodsId=3, price=100, name=铁观音, userId=5a740a0e50c8435a0150cccc6e26000b, image=, salesTotal=100, type=0}
		HashMap<String,String> map = list.get(position);
		String name = map.get("name");
		String icon = map.get("image");
		
		String price = map.get("price");
		String xl = map.get("salesTotal");
		viewHolder.tv_name.setText(name);
		viewHolder.tv_price.setText("￥"+price);
		viewHolder.tv_xl.setText("销量："+xl);
		if(!TextUtils.isEmpty(icon)){
			Picasso.with(context).load(icon).centerCrop().resize(60, 60).into(viewHolder.iv_img);
		}
		return convertView;
	}
	
	
	
	public class ViewHolder
	{
		
		@ViewInject(R.id.goods_collect_name)
		private TextView tv_name;
		
		
		@ViewInject(R.id.goods_collect_price)
		private TextView tv_price;
		
		@ViewInject(R.id.goods_collect_imageview)
		private ImageView iv_img;
		@ViewInject(R.id.goods_xl)
		private TextView tv_xl;
		
		
	}

}
