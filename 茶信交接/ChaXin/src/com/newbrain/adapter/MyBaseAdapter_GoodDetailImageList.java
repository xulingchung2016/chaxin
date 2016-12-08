package com.newbrain.adapter;

import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.adapter.MyBaseAdapter_TeaMallGoodsList.MyOnClickListen;
import com.newbrain.adapter.MyBaseAdapter_TeaMallGoodsList.ViewHolder;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.teamall.DetailActivity;
import com.newbrain.jsonthread.Constant;
import com.newbrain.model.Detail_ImageList;
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

public class MyBaseAdapter_GoodDetailImageList  extends BaseAdapter
{
	private Context context;
	private LayoutInflater inflater;
	private List<Detail_ImageList> list;
	
	public  MyBaseAdapter_GoodDetailImageList(Context context,List<Detail_ImageList> list)
	{
		this.context = context;
		this.list = list;
		
		inflater = LayoutInflater.from(this.context);
		
	}
	
	public List<Detail_ImageList> getList()
	{
		return list;
	}

	public void setList(List<Detail_ImageList> list)
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
			convertView = inflater.inflate(R.layout.gooddetail_image_list, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			convertView.setTag(viewHolder);			
		}
		else
		{		
			viewHolder = (ViewHolder) convertView.getTag();
			MyOnClickListen myOnClickListen = new MyOnClickListen(position);
			convertView.setOnClickListener(myOnClickListen);
		}

		String image = list.get(position).getImage();

		if(!image.isEmpty())
		{
			Log.e("lijinjin", "not empty");
			Xutils_BitmapUtils.getbitmapUtils_detail(context).display(viewHolder.itemGoodsPic, Constant.TEAMALL_IMAGE_BASE_URL + image);
		}
		else
		{
			Log.e("lijinjin", "empty");
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
			
		}	
	}
	
	public class ViewHolder
	{		
		@ViewInject(R.id.gooddetail_image_listview_picture)
		private ImageView itemGoodsPic;
	}
}
