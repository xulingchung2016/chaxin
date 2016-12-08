package com.liren_app.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newbrain.chaxin.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AddItemAdapte {

	public static SimpleAdapter getAdapter(String[] names, String[] tels,
			String[] adds, final Context context) {

		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < names.length; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("txt_name", names[i]);
			item.put("txt_tel", tels[i]);
			item.put("txt_add", adds[i]);
			listItems.add(item);
		}

		SimpleAdapter adapter = new SimpleAdapter(context, listItems,
				R.layout.address_item, new String[] { "txt_name", "txt_tel",
						"txt_add" }, new int[] { R.id.txt_name, R.id.txt_tel,
						R.id.txt_add }) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = super.getView(position, convertView, parent);
				TextView txt = (TextView) v.findViewById(R.id.txt_moren);

				if (position == 0) {
					txt.setVisibility(View.VISIBLE);
				}
				return v;
			}
		};

		return adapter;
	}
}
