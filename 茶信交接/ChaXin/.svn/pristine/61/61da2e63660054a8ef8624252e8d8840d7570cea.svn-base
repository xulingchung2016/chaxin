package com.newbrain.adapter;

import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.adapter.MyBaseAdapter_TeaMallGoodsList.MyOnClickListen;
import com.newbrain.adapter.MyBaseAdapter_TeaMallGoodsList.ViewHolder;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.teamall.DetailActivity;
import com.newbrain.chaxin.teamall.MyShopSearchGoodsActivity;
import com.newbrain.jsonthread.Constant;
import com.newbrain.model.ShopsList;
import com.newbrain.model.ShopsListBean.shoplist;
import com.newbrain.xutils.Xutils_BitmapUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyBaseAdapter_TeaMallShopsList extends BaseAdapter
{
	private Context context;
	private LayoutInflater inflater;
	private List<shoplist> list;
	
	public  MyBaseAdapter_TeaMallShopsList(Context context,List<shoplist> list)
	{
		this.context = context;
		this.list = list;
		
		inflater = LayoutInflater.from(this.context);		
	}
	
	public List<shoplist> getList()
	{
		return list;
	}

	public void setList(List<shoplist> list)
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
			convertView = inflater.inflate(R.layout.my_teamallshops_listview_item, null);
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
		
		String shopname = list.get(position).getName();
		String address = list.get(position).getAddress();
		String goodScore = list.get(position).getGoods_score();
		String serviceScore = list.get(position).getService_score();
		String expressScore = list.get(position).getExpress_score();
		String image = list.get(position).getImg();
		viewHolder.itemShopName.setText(shopname);
		viewHolder.itemShopAddress.setText(address);
		viewHolder.itemGoodsScore.setText(goodScore);
		viewHolder.itemServiceScore.setText(serviceScore);
		viewHolder.itemExpressScore.setText(expressScore);
		if(!image.isEmpty())
		{
			Xutils_BitmapUtils.getbitmapUtils_detail(context).display(viewHolder.itemShopPic, Constant.TEAMALL_SHOP_IMAGE_BASE_URL + image);
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
			Log.e("lijinjin", "onClick() start MyShopSearchGoodsActivity");
			SharedPreferences sp = context.getSharedPreferences("CHAXIN_CONFIG", context.MODE_PRIVATE);
			Editor editor = sp.edit();
			String shopName = list.get(position).getName();
			editor.putString("shopName_for_goodsearch", shopName);
			
			String shopID = list.get(position).getId();
			editor.putString("shopID_for_goodsearch", shopID);
			
			Log.e("lijinjin", "write shopName: " + shopName);
			Log.e("lijinjin", "write  shopID: " + shopID);
			
			editor.commit();
			context.startActivity(new Intent(context, MyShopSearchGoodsActivity.class));		
		}	
	}
	
	public class ViewHolder
	{		
		@ViewInject(R.id.shop_listview_item_shops_picture)
		private ImageView itemShopPic;
		
		@ViewInject(R.id.shop_listview_item_shop_name)
		private TextView itemShopName;
		
		@ViewInject(R.id.TeaMall_shop_listview_item_shop_address)
		private TextView itemShopAddress;
		
		@ViewInject(R.id.TeaMall_shop_listview_item_goods_score)
		private TextView itemGoodsScore;
		
		@ViewInject(R.id.TeaMall_shop_listview_item_service_score)
		private TextView itemServiceScore;
		
		@ViewInject(R.id.TeaMall_shop_listview_item_express_score)
		private TextView itemExpressScore;
	}
}
