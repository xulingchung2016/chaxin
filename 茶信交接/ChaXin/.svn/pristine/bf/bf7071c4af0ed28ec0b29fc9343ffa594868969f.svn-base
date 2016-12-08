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
import com.newbrain.model.ManoVarietyIntroBean.ManoVarietyIntro;

public class MyAdapter_SelectVarietiesDialogGridview extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<ManoVarietyIntro> list;

	public MyAdapter_SelectVarietiesDialogGridview(Context context,
			List<ManoVarietyIntro> list) {
		this.context = context;
		this.list = list;

		inflater = LayoutInflater.from(this.context);

	}

	private int mIndex = 0;
	public void setIndex(int index){
		mIndex = index;
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.select_varieties_dialog_gridview_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);

			convertView.setTag(viewHolder);

		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}

		if(mIndex == position){
			viewHolder.tv_name.setSelected(true);
			viewHolder.iv_icon.setVisibility(View.VISIBLE);
		}else{
			viewHolder.tv_name.setSelected(false);
			viewHolder.iv_icon.setVisibility(View.GONE);
		}
		viewHolder.tv_name.setText(list.get(position).getVariety_name());

		return convertView;
	}

	public class ViewHolder {

		@ViewInject(R.id.select_varieties_dialog_gridview_textview)
		TextView tv_name;
		@ViewInject(R.id.select_varieties_dialog_gridview_imageview)
		ImageView iv_icon;

	}

}
