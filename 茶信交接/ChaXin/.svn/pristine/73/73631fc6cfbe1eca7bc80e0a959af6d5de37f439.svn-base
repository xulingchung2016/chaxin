package com.newbrain.chaxin.my.mybeauty;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.adapter.Model;
import com.newbrain.adapter.MyBaseAdapter_ChangeCard;
import com.newbrain.adapter.MyBaseAdapter_SearchTeaHouse;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;

public class SearchTeaHouseActivity  extends BaseActivity {

	private Context context;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.search_tea_house_activity);

		ViewUtils.inject(this);
		context = this;
		
		listviewInit() ;
		
	}
	
	
	@OnClick({R.id.tea_mall_back })
	private void onClick_withdraw(View view)
	{
		
		switch (view.getId()) {
		case R.id.tea_mall_back:
			
			
			finish();
			
			break;
			
		
		default:
			break;
		}
		
		
	}
	
	
	@ViewInject(R.id.search_tea_house_listview)
	private ListView mListview;
	
	private MyBaseAdapter_SearchTeaHouse myAdapter;

	private List<Model> mList;

	private void listviewInit() {

		mList = new ArrayList<Model>();

		for (int i = 0; i < 5; i++) {

			mList.add(new Model());
		}

		myAdapter = new MyBaseAdapter_SearchTeaHouse(context, mList,R.layout.search_tea_house_listview_item);

		mListview.setAdapter(myAdapter);

	}

}
