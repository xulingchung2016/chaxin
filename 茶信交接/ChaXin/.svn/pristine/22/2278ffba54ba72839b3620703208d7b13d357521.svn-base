package com.liren_app.ui;

import com.liren_app.data.DuihuanJiLuItemAdapter;
import com.liren_app.data.ISetItemsData;
import com.newbrain.chaxin.R;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class DuihuanJiLuActivity extends BaseActivity implements ISetItemsData {

	private ListView lst_data;
	private ImageButton btn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duihuan_ji_lu);
		initUI();
		initControlListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_duihuan_ji_lu, menu);
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
		lst_data.setAdapter(DuihuanJiLuItemAdapter.getAdapter(new int[] {
				R.drawable.yy_logo_1, R.drawable.yy_logo_2,
				R.drawable.yy_logo_3, R.drawable.yy_logo_4 }, new String[] {
				"1000", "1000", "1000", "1000" }, new String[] {
				"正宗安溪铁观音 浓香型特级NGY1600-250g 产地直供",
				"正宗安溪铁观音 浓香型特级NGY1600-250g 产地直供",
				"正宗安溪铁观音 浓香型特级NGY1600-250g 产地直供",
				"正宗安溪铁观音 浓香型特级NGY1600-250g 产地直供" }, DuihuanJiLuActivity.this));

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
