package com.viewpagerindicator;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.chaxin.R;
import com.newbrain.xutils.Xutils_BitmapUtils;

public class ImageListAdapter extends PagerAdapter implements IconPagerAdapter{

	private Context mContext;
	private LayoutInflater mInflater;
	private List<String> imageUrlList;
	private BitmapUtils bitmapUtils;
	private View[] mViews;
	
	public ImageListAdapter(Context context, List<String> imageUrlList) {
		mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.imageUrlList = imageUrlList;
		mInflater = LayoutInflater.from(mContext);
		
		bitmapUtils = Xutils_BitmapUtils.getbitmapUtils_detail(context);

		mViews = new View[imageUrlList.size()];
	}

	@Override
	public int getCount() {
		return imageUrlList.size();
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		((MyDirectionalViewPager)container).removeView(mViews[position]);
		mViews[position] = null;
	}

	@Override
	public View instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if(mViews[position] == null){
			viewHolder = new ViewHolder();
			View view = mInflater.inflate(R.layout.viewflow_item, null);
			ViewUtils.inject(viewHolder, view);
			view.setTag(viewHolder);
			mViews[position] = view;
		}else{
			viewHolder = (ViewHolder) mViews[position].getTag();
			
		}
		bitmapUtils.display(viewHolder.iv_viewflow, imageUrlList.get(position%imageUrlList.size()));
		
		((MyDirectionalViewPager)container).addView(mViews[position],0);
		return mViews[position];
	}

	@Override
	public int getIconResId(int index) {
		// TODO Auto-generated method stub
		return R.drawable.image_page_indicator_bg;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}
	
	
	public class ViewHolder
	{
		
		@ViewInject(R.id.viewflow_item_image)
		private ImageView iv_viewflow;
		
		
	}


}
