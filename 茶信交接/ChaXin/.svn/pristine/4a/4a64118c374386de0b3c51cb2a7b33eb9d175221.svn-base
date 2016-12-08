package com.newbrain.chaxin.my.teafans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.adapter.Model;
import com.newbrain.adapter.MyBaseAdapter_CollectBeauty;
import com.newbrain.adapter.MyBaseAdapter_Fans;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;

public class TeaFansActivity extends BaseActivity {

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.tea_fans_activity);

		ViewUtils.inject(this);
		context = this;
		actionbarInit();

		dataInit();
		
		listviewInit();
	}

	@ViewInject(R.id.tea_fans_num)
	private TextView mTvFansNum;


	@ViewInject(R.id.tea_fans_begin_time)
	private Button mBtnBeginTime;

	@ViewInject(R.id.tea_fans_finish_time)
	private Button mBtnFinishTime;

	@ViewInject(R.id.tea_fans_search)
	private Button mBtnSearch;

	private void dataInit() {
		// TODO Auto-generated method stub

	}
	
	
	private final int TIME_BEGIN = 0;
	
	private final int TIME_FINISH = 1;

	@OnClick({ R.id.tea_fans_begin_time, R.id.tea_fans_finish_time,
			R.id.tea_fans_search, R.id.tea_fans_linear})
	private void onClick_Fans(View view) {

		switch (view.getId()) {
		case R.id.tea_fans_begin_time:
			
			
			showTime(TIME_BEGIN);
			
			

			break;

		case R.id.tea_fans_finish_time:
			
			showTime(TIME_FINISH);

			break;

		case R.id.tea_fans_search:

			break;
			
		case R.id.tea_fans_linear:
			
			
			openActivity(TeaFansDetailActivity.class);

			break;
			

		default:
			break;
		}

	}
	
	
	
	@ViewInject(R.id.tea_fans_listview)
	private ListView mListview;
	
	private MyBaseAdapter_Fans myAdapter;

	private List<Model> mList;

	private void listviewInit() {

		mList = new ArrayList<Model>();

		for (int i = 0; i < 5; i++) {

			mList.add(new Model());
		}

		myAdapter = new MyBaseAdapter_Fans(context, mList);

		mListview.setAdapter(myAdapter);

	}
	
	

	
	public void showTime(final int index)
	{
		
		Calendar calendar = Calendar.getInstance();
		
		int year = calendar.get(Calendar.YEAR);
		int monthOfYear = calendar.get(Calendar.MONTH);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		
		DatePickerDialog datePickerDialog = new DatePickerDialog(context, DatePickerDialog.THEME_DEVICE_DEFAULT_LIGHT,new OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				
				
				if(index == TIME_BEGIN)
				{
					
					
					mBtnBeginTime.setText(year+"."+monthOfYear);
					
				}else
				{
					
					mBtnFinishTime.setText(year+"."+monthOfYear);
					
				}
				
			}
		}, year, monthOfYear,dayOfMonth);
		
		
		
		Window window = datePickerDialog.getWindow();
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(R.style.animation);
		
		datePickerDialog.show();
		
		
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
		// actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText(getString(R.string.my_fans));
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
