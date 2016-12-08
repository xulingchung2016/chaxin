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

public class DuihuanJiLuItemAdapter {

	public static SimpleAdapter getAdapter(int[] logos, String[] prices,
			String[] discs, Context context) {

		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < logos.length; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("logo", logos[i]);
			item.put("price", prices[i]);
			item.put("disc", discs[i]);
			listItems.add(item);
		}

		SimpleAdapter adapter = new SimpleAdapter(context, listItems,
				R.layout.duihuan_jilu_item, new String[] { "logo", "price",
						"disc" },
				new int[] { R.id.logo, R.id.price, R.id.disc });

		return adapter;
	}
}
