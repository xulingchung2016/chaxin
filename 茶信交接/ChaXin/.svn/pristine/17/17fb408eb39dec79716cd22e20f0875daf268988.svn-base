package com.newbrain.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.chaxin.R;
import com.newbrain.model.ManoSearchIntroBean.ManoSearchIntro;
import com.lidroid.xutils.BitmapUtils;
import com.newbrain.xutils.Xutils_BitmapUtils;

public class MyAdapter_TeaDingZhongDetailListView extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<ManoSearchIntro> list;
	private BitmapUtils bitmapUtils;
	public MyAdapter_TeaDingZhongDetailListView(Context context,
			List<ManoSearchIntro> list) {
		this.context = context;
		this.list = list;

		inflater = LayoutInflater.from(this.context);
		bitmapUtils = Xutils_BitmapUtils.getbitmapUtils_detail(context);
	}

	public void setList(List<ManoSearchIntro> list){
		if(this.list.size() > 0) this.list.clear();
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

		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.tea_dingzhong_detail_listview_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);

			convertView.setTag(viewHolder);

		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}

		ManoSearchIntro info = list.get(position);
		
		bitmapUtils.display(viewHolder.imageView, info.getSmallImage());
		viewHolder.tv_name.setText(info.getManor_name());

		return convertView;
	}

	public class ViewHolder {

		@ViewInject(R.id.tea_dingzhong_detail_listview_textview)
		TextView tv_name;

		@ViewInject(R.id.tea_dingzhong_detail_listview_imageview)
		ImageView imageView;
	}

}
