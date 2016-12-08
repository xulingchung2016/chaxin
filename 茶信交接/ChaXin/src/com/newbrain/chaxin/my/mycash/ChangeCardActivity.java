package com.newbrain.chaxin.my.mycash;

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
import com.newbrain.adapter.MyBaseAdapter_Fans;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;

public class ChangeCardActivity extends BaseActivity {

	private Context context;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.i_want_thanks_activity);

		ViewUtils.inject(this);
		context = this;
		actionbarInit();
		
		
		listviewInit() ;
	}
	
	
	@ViewInject(R.id.change_card_listview)
	private ListView mListview;
	
	private MyBaseAdapter_ChangeCard myAdapter;

	private List<Model> mList;

	private void listviewInit() {

		mList = new ArrayList<Model>();

		for (int i = 0; i < 5; i++) {

			mList.add(new Model());
		}

		myAdapter = new MyBaseAdapter_ChangeCard(context, mList,R.layout.change_card_listview_item);

		mListview.setAdapter(myAdapter);

	}
	
	
	
	@OnClick({ R.id.change_card_add_card })
	private void onClick_cash(View v) {

		switch (v.getId()) {
		case R.id.change_card_add_card:
			
			openActivity(AddCardActivity.class);
			
			
			break;
		

		default:
			break;
		}

	}
	

	@ViewInject(R.id.all_actionbar_linear_left)
	private LinearLayout actionbar_ll_left;

	@ViewInject(R.id.all_actionbar_textview_back_name)
	private TextView actionbar_tv_back_name_left;

	@ViewInject(R.id.all_actionbar_name)
	private TextView actionbar_tv_name_center;

	@ViewInject(R.id.all_actionbar_button_right_left)
	private Button actionbar_btn_right_left;

	@ViewInject(R.id.all_actionbar_button_right)
	private ImageButton actionbar_imgbtn_right;

	private void actionbarInit() {
		// TODO Auto-generated method stub

		actionbar_ll_left.setVisibility(View.VISIBLE);
		actionbar_tv_back_name_left.setVisibility(View.GONE);
		// actionbar_tv_name_center.setVisibility(View.VISIBLE);
		
		
		actionbar_btn_right_left.setVisibility(View.GONE);
		actionbar_imgbtn_right.setVisibility(View.VISIBLE);
//		actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText(getString(R.string.my_cash_withdraw));
		actionbar_btn_right_left.setText(getString(R.string.rule));

		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
		actionbar_imgbtn_right.setOnClickListener(clickListener_actionbar);

	}

	private OnClickListener clickListener_actionbar = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			switch (v.getId()) {
			case R.id.all_actionbar_linear_left:

				finish();

				break;

			case R.id.all_actionbar_button_right:

				break;

			default:
				break;
			}

		}
	};

}
