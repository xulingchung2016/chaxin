package com.liren_app.ui;

import com.liren_app.data.ISetItemsData;
import com.liren_app.data.MingxiItemAdapter;
import com.newbrain.chaxin.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.ListView;

public class MingxiActivity extends BaseActivity implements ISetItemsData {

	private ListView lst_data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mingxi);
		initUI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_mingxi, menu);
		return true;
	}

	@Override
	public void setData(Button btn) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		lst_data = (ListView) findViewById(R.id.lst_data);
		lst_data.setAdapter(MingxiItemAdapter.getAdapter(new String[] { "ºÈ²è",
				"¶Ò»»»ý·Ö", "Âò²èÒ¶", "¾è¿î" }, new String[] { "2015-1-1", "2015-10-1",
				"2015-11-1", "2015-12-1" }, new String[] { "-100", "100",
				"-200", "-50" }, MingxiActivity.this));
	}

	@Override
	protected void initControlListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initBoardCastListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

}
