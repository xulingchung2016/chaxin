package com.liren_app.ui;

import com.liren_app.data.AddItemAdapte;
import com.liren_app.data.ISetItemsData;
import com.liren_app.data.SortListener;
import com.newbrain.chaxin.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class AddManageActivity extends BaseActivity implements ISetItemsData {

	private ListView lst_data;
	private RelativeLayout add_new;
	private ImageButton btn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_manage);
		initUI();
		initControlListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_manage, menu);
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
		lst_data.setAdapter(AddItemAdapte.getAdapter(new String[] { "茶信", "茶信",
				"茶信", "茶信" }, new String[] { "13223233232", "13223233232",
				"13223233232", "13223233232" }, new String[] {
				"广东省深圳市龙华新区港之龙国际商务中心A座504", "广东省深圳市龙华新区港之龙国际商务中心A座504",
				"广东省深圳市龙华新区港之龙国际商务中心A座504", "广东省深圳市龙华新区港之龙国际商务中心A座504" },
				AddManageActivity.this));
		add_new = (RelativeLayout) findViewById(R.id.add_new);
		add_new.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddManageActivity.this.startActivity(new Intent().setClass(
						AddManageActivity.this, AddActivity.class));

			}
		});

		btn_back = (ImageButton) findViewById(R.id.btn_back);
	}

	@Override
	protected void initControlListener() {
		// TODO Auto-generated method stub
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddManageActivity.this.finish();
			}
		});
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
