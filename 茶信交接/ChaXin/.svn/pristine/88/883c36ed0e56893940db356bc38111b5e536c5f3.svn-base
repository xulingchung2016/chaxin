package com.liren_app.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liren_app.ui.LiRenMainActivity;
import com.newbrain.chaxin.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MingxiItemAdapter {

	public static SimpleAdapter getAdapter(String[] datas, String[] dates,
			String[] nums, final Context context) {

		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < datas.length; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("title", datas[i]);
			item.put("date", dates[i]);
			item.put("num", nums[i]);
			listItems.add(item);
		}

		SimpleAdapter adapter = new SimpleAdapter(context, listItems,
				R.layout.mingxi_item, new String[] { "title", "date", "num" },
				new int[] { R.id.lst_item_title, R.id.lst_item_date,
						R.id.lst_item_num }) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = super.getView(position, convertView, parent);
				TextView txt = (TextView) v.findViewById(R.id.lst_item_num);
				double value = Double.parseDouble(txt.getText().toString()
						.trim());
				if (value > 0) {
					txt.setText("+" + value);
					txt.setTextColor(context.getResources().getColor(
							R.color.txt_orange));

				} else {
					txt.setText(value + "");
					txt.setTextColor(context.getResources().getColor(
							R.color.lay_green));

				}

				return v;
			}
		};

		return adapter;
	}
}
