package com.newbrain.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.chaxin.R;
import com.newbrain.model.ManoTeaTypeIntroBean.TeaTypeIntro;

public class MyAdapter_TeaDingZhongGridview extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<TeaTypeIntro> list;

	public MyAdapter_TeaDingZhongGridview(Context context,
			List<TeaTypeIntro> list) {
		this.context = context;
		this.list = list;

		inflater = LayoutInflater.from(this.context);

	}

	public void setList(List<TeaTypeIntro> list){
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

		ViewHolder viewHolder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.tea_dingzhong_gridview_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);

			convertView.setTag(viewHolder);

		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_name.setText(list.get(position).getCname());

		return convertView;
	}

	public class ViewHolder {

		@ViewInject(R.id.tea_dingzhong_gridview_textview)
		TextView tv_name;

	}

}
