package com.newbrain.adapter;


import java.util.List;

import android.content.Context;
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
import com.newbrain.model.TeaHouseBeautyIntroListBean.TeaBeautyIntro;
import com.newbrain.utils.StringUtil;
import com.newbrain.xutils.Xutils_BitmapUtils;



public class MyAdapter_TeaSearchTeaBeautyGridView  extends BaseAdapter{
	
	
	private Context context;
	private LayoutInflater inflater;
	private List<TeaBeautyIntro> list;
	private BitmapUtils bitmapUtils;
	
	public  MyAdapter_TeaSearchTeaBeautyGridView(Context context,List<TeaBeautyIntro> list)
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
			convertView = inflater.inflate(R.layout.tea_search_teabeauty_gridview_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			
			convertView.setTag(viewHolder);			
			
		}else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		TeaBeautyIntro teaBeautyIntro = list.get(position);
		bitmapUtils.display(viewHolder.beauty_icon, teaBeautyIntro.getImage());
		viewHolder.tv_beauty_nu.setText(teaBeautyIntro.getNo());
		viewHolder.iv_beauty_level.setImageResource(getLevelImgResId(teaBeautyIntro.getLevels()));
		viewHolder.tv_beauty_price.setText(teaBeautyIntro.getNo());
		viewHolder.bt_deal_nu.setText(String.format(context.getString(R.string.deal_nu), teaBeautyIntro.getSales()));
		
		return convertView;
	}
	
	
	
	private int getLevelImgResId(String levels) {
		// TODO Auto-generated method stub
		int resId = R.drawable.baiyin;
		switch(StringUtil.toInt(levels, -1)){
		case 0:
			resId = R.drawable.emerald;
			break;
		case 1:
			resId = R.drawable.diamonds;
			break;
		case 2:
			resId = R.drawable.gold;
			break;
		case 3:
			resId = R.drawable.baiyin;
			break;
		}
		return resId;
	}



	public class ViewHolder
	{
		
		@ViewInject(R.id.beauty_icon)
		ImageView beauty_icon;

		@ViewInject(R.id.beauty_nu)
		TextView tv_beauty_nu;
		
		@ViewInject(R.id.beauty_level)
		ImageView iv_beauty_level;
		
		@ViewInject(R.id.beauty_price)
		TextView tv_beauty_price;

		@ViewInject(R.id.deal_nu)
		TextView bt_deal_nu;
		
	}

}
