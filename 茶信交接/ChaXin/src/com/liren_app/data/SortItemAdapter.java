package com.liren_app.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liren_app.ui.LiRenMainActivity;
import com.newbrain.chaxin.R;

import android.content.Context;
import android.widget.SimpleAdapter;

public class SortItemAdapter {

	public static SimpleAdapter getAdapter(String[] datas, Context context) {

		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < datas.length; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("title", datas[i]);
			listItems.add(item);
		}

		SimpleAdapter adapter = new SimpleAdapter(context, listItems,
				R.layout.sort_item_check, new String[] { "title" },
				new int[] { R.id.lst_item_title });

		return adapter;
	}

	public static SimpleAdapter getAdapter(String[] titles, String[] nums,
			Context context) {
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < titles.length; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("title", titles[i]);
			item.put("num", nums[i]);
			listItems.add(item);
		}

		SimpleAdapter adapter = new SimpleAdapter(context, listItems,
				R.layout.sort_item_num, new String[] { "title", "num" },
				new int[] { R.id.lst_item_title, R.id.lst_item_num });

		return adapter;
	}
}
