package com.newbrain.adapter;

import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.adapter.MyBaseAdapter_TeaMallGoodsList.MyOnClickListen;
import com.newbrain.adapter.MyBaseAdapter_TeaMallGoodsList.ViewHolder;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.teamall.DetailActivity;
import com.newbrain.jsonthread.Constant;
import com.newbrain.model.GoodsListBean.goodlist;
import com.newbrain.xutils.Xutils_BitmapUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyBaseAdapter_MyShopSearchGoodsList extends BaseAdapter 
{
	private Context context;
	private LayoutInflater inflater;
	private List<goodlist> list;
	
	public  MyBaseAdapter_MyShopSearchGoodsList(Context context,List<goodlist> list)
	{
		this.context = context;
		this.list = list;
		
		inflater = LayoutInflater.from(this.context);
		
	}
	
	public List<goodlist> getList()
	{
		return list;
	}

	public void setList(List<goodlist> list)
	{
		this.list = list;
		
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) 
	{
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.my_teamallmyshopssearchgood_listview_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			convertView.setTag(viewHolder);			
		}
		else
		{		
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		MyOnClickListen myOnClickListen = new MyOnClickListen(position);
		convertView.setOnClickListener(myOnClickListen);
		
		String goodname = list.get(position).getGoodsName();
		String goodprice = list.get(position).getPrice();
		String punlin = list.get(position).getEvalToal();
		goodprice = "￥" + goodprice;
		String salesTotal = list.get(position).getSalesTotal();
		String salesTotalValue = "销量：" + salesTotal;
		String image = list.get(position).getImage(); 
		viewHolder.itemGoodsName.setText(goodname);
		viewHolder.itemGoodsprice.setText(goodprice);
		viewHolder.itemGoodsSellCount.setText(salesTotalValue);
		viewHolder.itemGoodspinlun.setText("评论： "+punlin);
		if(!TextUtils.isEmpty(image))
		{
			Xutils_BitmapUtils.getbitmapUtils_detail(context).display(viewHolder.itemGoodsPic, image);
		}
		
		return convertView;
	}
	
	public class MyOnClickListen implements OnClickListener
	{
		private int position;
		
		public MyOnClickListen()
		{
			super();
			// TODO Auto-generated constructor stub
		}


		public MyOnClickListen(int position)
		{
			super();
			this.position = position;
		}

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			SharedPreferences sp = context.getSharedPreferences("CHAXIN_CONFIG", context.MODE_PRIVATE);
			Editor editor = sp.edit();
			String goodID = list.get(position).getId();
			editor.putString("goodid_for_detail", goodID);
			editor.putBoolean("isShangpu", true);
			editor.commit();
			context.startActivity(new Intent(context, DetailActivity.class));	
		}	
	}
	
	public class ViewHolder
	{		
		@ViewInject(R.id.searchgoods_listview_item_goods_picture)
		private ImageView itemGoodsPic;
		
		@ViewInject(R.id.searchgoods_listview_item_goods_name)
		private TextView itemGoodsName;
		
		@ViewInject(R.id.TeaMall_searchgoods_listview_item_goods_price)
		private TextView itemGoodsprice;
		
		@ViewInject(R.id.TeaMall_searchgoods_listview_item_goods_sellCount)
		private TextView itemGoodsSellCount;
		@ViewInject(R.id.TeaMall_searchgoods_listview_item_goods_pl)
		private TextView itemGoodspinlun;
	}

}
