package com.newbrain.adapter;


import java.util.HashMap;
import java.util.List;

import com.newbrain.chaxin.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DistrictListAdapter extends BaseAdapter {

	private Context mContext;
	private  String[] mDistricts;
	private int mIndex;
	private int flag;
	private List<HashMap<String,String>> datas ;

	public DistrictListAdapter(Context context, String[] districts, int index) {
		this.mContext = context;
		this.mDistricts = districts;
		this.mIndex = index;
	}

	public DistrictListAdapter(Context context, List<HashMap<String,String>> datas,int flag) {
		this.mContext = context;
		this.datas = datas;
		this.flag = flag;
	}
	
	@Override
	public int getCount() {
		return flag == 1?datas.size():mDistricts.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView;

		itemView = new View(mContext);

		itemView = inflater.inflate(R.layout.item_district, null);

		TextView contentTextView = (TextView) itemView
				.findViewById(R.id.district_name);
		if(flag == 0){
		contentTextView.setText(mDistricts[position]);

		if (position == mIndex) {

			contentTextView.setTextColor(mContext.getResources().getColor(
					R.color.blue_cloud_scheme));

		}
		}else{//多选
			HashMap<String,String>map = datas.get(position);
			String name = map.get("name");
			String status = map.get("status");
			contentTextView.setText(name);
			if(status.equals("0")){
				itemView.setBackgroundResource(R.drawable.dropdown_item_normal);
				contentTextView.setTextColor(mContext.getResources().getColor(
					R.color.city_normal));
			}else {
				contentTextView.setTextColor(mContext.getResources().getColor(
						R.color.blue_cloud_scheme));
				itemView.setBackgroundResource(R.drawable.dropdown_item_chosen);
			}
		}

		return itemView;
	}
}
