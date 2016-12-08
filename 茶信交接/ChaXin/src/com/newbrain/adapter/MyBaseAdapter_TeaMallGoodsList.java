package com.newbrain.adapter;

import java.util.List;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.adapter.MyBaseAdapter_MyOrder.MyOnClickListen;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.my.order.ComplaintActivity;
import com.newbrain.chaxin.teamall.DetailActivity;
import com.newbrain.jsonthread.Constant;
import com.newbrain.model.GoodsListBean.goodlist;
import com.newbrain.xutils.Xutils_BitmapUtils;
import com.squareup.picasso.Picasso;

public class MyBaseAdapter_TeaMallGoodsList  extends BaseAdapter
{
	private Context context;
	private LayoutInflater inflater;
	private List<goodlist> list;
	
	public  MyBaseAdapter_TeaMallGoodsList(Context context,List<goodlist> list)
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
	public View getView(int position, View view, ViewGroup parent) 
	{
		// TODO Auto-generated method stub
		Log.e("lijinjin","position:" + position + " GoodID:" + list.get(position).getId() + " GoodName: " + list.get(position).getGoodsName());
		Log.e("lijinjin","position:" + position + " price" + list.get(position).getPrice() + " salesTotal: " + list.get(position).getSalesTotal());
		Log.e("lijinjin","position:" + position + " procucingArea" + list.get(position).getProcucingArea() + " image: " + list.get(position).getImage());
		ViewHolder viewHolder;
		if(view == null)
		{
			view = inflater.inflate(R.layout.my_teamallgoods_listview_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, view);
			view.setTag(viewHolder);
			
		}
		else
		{		
			viewHolder = (ViewHolder) view.getTag();
		}
		
		goodlist data = list.get(position);
		MyOnClickListen myOnClickListen = new MyOnClickListen(position);
		view.setOnClickListener(myOnClickListen);
		String goodname = data.getGoodsName();
		String goodprice = data.getPrice();
		goodprice = "￥" + goodprice;
		String salesTotal =data.getSalesTotal();
		String salesTotalValue = "";
		if(TextUtils.isEmpty(salesTotal))
		salesTotalValue = "销量：0" ;
		else salesTotalValue = "销量：" +salesTotal;
//		String salesPlace = list.get(position).getProcucingArea();
		String salesPlace =data.getEvalToal();
		String image = data.getImage();
		if(TextUtils.isEmpty(image))image = Constant.TEAMALL_IMAGE_BASE_URL+"tea/111.jpg";
		viewHolder.itemGoodsName.setText(goodname);
		viewHolder.itemGoodsprice.setText(goodprice);
		viewHolder.itemGoodsSellCount.setText(salesTotalValue);
		if(!TextUtils.isEmpty(salesPlace))
			
		viewHolder.itemGoodsSellPlace.setText("评论："+salesPlace);
		else viewHolder.itemGoodsSellPlace.setText("评论：0");
		if(!TextUtils.isEmpty(image))
		{
			Picasso.with(context).load(image).error(R.drawable.shape_imageview_kk).centerCrop().resize(60, 60).into(viewHolder.itemGoodsPic);
//			viewHolder.itemGoodsPic.setBackgroundResource(R.drawable.yy_cha_1);
//			Xutils_BitmapUtils.getbitmapUtils_detail(context).display(viewHolder.itemGoodsPic, image);
		}else viewHolder.itemGoodsPic.setBackgroundResource(R.drawable.shape_imageview_kk);
		
		return view;
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
			
			Log.e("lijinjin", "order_listview_item_goods_picture position: " + position);
			SharedPreferences sp = context.getSharedPreferences("CHAXIN_CONFIG", context.MODE_PRIVATE);
			Editor editor = sp.edit();
			String goodID = list.get(position).getId();
			editor.putString("goodid_for_detail", goodID);
			editor.putBoolean("isShangpu", false);
			editor.commit();
			context.startActivity(new Intent(context, DetailActivity.class));			
		}	
	}
	
	public class ViewHolder
	{		
		@ViewInject(R.id.order_listview_item_goods_picture)
		private ImageView itemGoodsPic;
		
		@ViewInject(R.id.order_listview_item_goods_name)
		private TextView itemGoodsName;
		
		@ViewInject(R.id.TeaMall_listview_item_goods_price)
		private TextView itemGoodsprice;
		
		@ViewInject(R.id.TeaMall_listview_item_goods_sellCount)
		private TextView itemGoodsSellCount;
		
		@ViewInject(R.id.TeaMall_listview_item_goods_sellPlace)
		private TextView itemGoodsSellPlace;
	}
}
