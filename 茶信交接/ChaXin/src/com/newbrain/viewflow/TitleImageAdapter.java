package com.newbrain.viewflow;

import java.util.List;
import java.util.zip.Inflater;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.chaxin.R;
import com.newbrain.xutils.Xutils_BitmapUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class TitleImageAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private List<String> imageUrlList;
	private BitmapUtils bitmapUtils;
	

	public TitleImageAdapter(Context context, List<String> imageUrlList) {
		mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.imageUrlList = imageUrlList;
		mInflater = LayoutInflater.from(mContext);
		
		bitmapUtils = Xutils_BitmapUtils.getbitmapUtils_detail(context);
		
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		
		if(convertView == null)
		{
			viewHolder = new ViewHolder();
			
			convertView = mInflater.inflate(R.layout.viewflow_item, null);
			ViewUtils.inject(viewHolder, convertView);
			
			convertView.setTag(viewHolder);
			
			
		}else
		{
			viewHolder = (ViewHolder) convertView.getTag();
			
		}
		
		
		bitmapUtils.display(viewHolder.iv_viewflow, imageUrlList.get(position%imageUrlList.size()));
		
		return convertView;
	}
	
	
	public class ViewHolder
	{
		
		@ViewInject(R.id.viewflow_item_image)
		private ImageView iv_viewflow;
		
		
	}
	
	

}
