package com.liren_app.ui;

import com.liren_app.data.ChaguanItemAdapte;
import com.liren_app.data.ISetItemsData;
import com.newbrain.chaxin.R;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class ChaguanActivity extends BaseActivity implements ISetItemsData {

	private ListView lst_chaguan;
	private ImageButton btn_back;
	private TextView txt_cancel;

	private String[] names = new String[] { "茶馆1", "茶馆2", "茶馆3", "茶馆4" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chaguan);
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
		txt_cancel = (TextView) findViewById(R.id.txt_cancel);
		lst_chaguan = (ListView) findViewById(R.id.lst_chaguan);
		lst_chaguan.setAdapter(ChaguanItemAdapte.getAdapter(names,
				new String[] { "13223233232", "13223233232", "13223233232",
						"13223233232" }, new String[] { "广东省深圳市", "广东省深圳市",
						"广东省深圳市", "广东省深圳市" }, ChaguanActivity.this));
		btn_back = (ImageButton) findViewById(R.id.btn_back);
	}

	@Override
	protected void initControlListener() {
		// TODO Auto-generated method stub
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("name", "");
				setResult(1004, intent);
				finish();
			}
		});

		lst_chaguan.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent intent = new Intent();
				intent.putExtra("name", names[arg2]);
				setResult(1004, intent);
				finish();
			}
		});

		txt_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("name", "");
				setResult(1004, intent);
				finish();
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
