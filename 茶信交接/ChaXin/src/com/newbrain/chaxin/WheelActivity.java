package com.newbrain.chaxin;

import java.util.ArrayList;
import java.util.Calendar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.customview.ScrollerNumberPicker;
import com.newbrain.customview.ScrollerNumberPicker.OnSelectListener;
import com.newbrain.xutils.Xutils_DBUtils;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WheelActivity extends BaseActivity implements OnClickListener {

	@ViewInject(R.id.rl_wheel)
	// 昵称
	private RelativeLayout rl_wheel;
	@ViewInject(R.id.ll_3)
	// 个性签名
	private LinearLayout ll_3;
	@ViewInject(R.id.ll_2)
	// 个性签名
	private LinearLayout ll_2;
	@ViewInject(R.id.ww_1)
	private ScrollerNumberPicker ww_1;
	@ViewInject(R.id.ww_2)
	private ScrollerNumberPicker ww_2;
	@ViewInject(R.id.ww_3)
	private ScrollerNumberPicker ww_3;
	@ViewInject(R.id.ww_5)
	private ScrollerNumberPicker ww_5;
	@ViewInject(R.id.ww_4)
	private ScrollerNumberPicker ww_4;

	@ViewInject(R.id.tv_quxiao)
	private TextView tv_quxiao;
	@ViewInject(R.id.tv_queren)
	private TextView tv_queren;
	private ArrayList<String> data_sg;
	private ArrayList<String> data_bank;
	private ArrayList<String> data_tz;
	private ArrayList<String> data_point = new ArrayList<String>();
	private ArrayList<String> data_year = new ArrayList<String>(); // 年龄及城市却换
	private ArrayList<String> data_year_code = new ArrayList<String>();
	private ArrayList<String> data_month = new ArrayList<String>();
	private ArrayList<String> data_month_code = new ArrayList<String>();
	private ArrayList<String> data_day = new ArrayList<String>();
	private ArrayList<String> data_day_code = new ArrayList<String>();
	private int data_type = 0;
	private String param = "";
	private String year = "2015年", month = "10月";
	private int p_pos = 0, c_pos = 1;
	private Xutils_DBUtils db;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_wheel_layout);
		ViewUtils.inject(this);
		context = this;
		db  = new Xutils_DBUtils(context);
		db.openDatabase();
		data_type = getIntent().getIntExtra("type", 0);
		tv_queren.setOnClickListener(this);
		tv_quxiao.setOnClickListener(this);
		if (data_type == 4) {
			getProviceData();
			if (data_year_code != null && data_year_code.size() > 0)
				getCityData(data_year.get(0), data_year_code.get(0));
			if (data_month_code != null && data_month_code.size() > 0)
				getAreasData(data_month_code.get(0));
			ll_2.setVisibility(View.GONE);
			ll_3.setVisibility(View.VISIBLE);
			ww_1.setOnSelectListener(new OnSelectListener() {

				@Override
				public void selecting(int id, String text) {

				}

				@Override
				public void endSelect(int id, String text) {
					if (data_year_code != null && data_year_code.size() > 0) {
						String proviceid = data_year_code.get(id);
						String name = data_year.get(id);
						getCityData(name, proviceid);
						ww_2.setData(data_month);
						ww_2.setDefault(0);
						if (data_month_code != null
								&& data_month_code.size() > 0) {
							String cityid = data_month_code.get(0);
							getAreasData(cityid);
							ww_3.setData(data_day);
							ww_3.setDefault(0);
						}
					}
				}
			});
			ww_2.setOnSelectListener(new OnSelectListener() {

				@Override
				public void selecting(int id, String text) {

				}

				@Override
				public void endSelect(int id, String text) {
					if (data_month_code != null && data_month_code.size() > 0) {
						String cityid = data_month_code.get(id);
						getAreasData(cityid);
						ww_3.setData(data_day);
						ww_3.setDefault(0);
					}
				}
			});

			/*
			 * ww_3.setOnSelectListener(new OnSelectListener() {
			 * 
			 * @Override public void selecting(int id, String text) {
			 * 
			 * }
			 * 
			 * @Override public void endSelect(int id, String text) {
			 * 
			 * } });
			 */

			if (data_year != null && data_year.size() > 0) {
				ww_1.setData(data_year);
				ww_1.setDefault(0);
			}
			if (data_month != null && data_month.size() > 0) {
				ww_2.setData(data_month);
				ww_2.setDefault(0);
			}
			if (data_day != null && data_day.size() > 0) {
				ww_3.setData(data_day);
				ww_3.setDefault(0);
			}
		} else if (data_type == 5) {
			String time = getIntent().getStringExtra("time");
			year = time.substring(0, time.indexOf("年") + 1);
			month = time.substring(time.indexOf("年") + 1);
			data_year = new ArrayList<String>();
			for (int i = 0; i < 90; i++) {
				String data = i + 2014 + "年";
				if (year.equals(data))
					p_pos = i;
				data_year.add(data);
			}
			
			data_month = new ArrayList<String>();
			for (int i = 0; i < 12; i++) {
				String data = i + 1 + "月";
				if (i < 9)
					data = 0 + data;
				if (month.equals(data))
					c_pos = i;
				data_month.add(data);
			}
			ShowWheel(data_type);

		} else if(data_type == 6){//银行
			data_bank = new ArrayList<String>();
			data_bank.add("中国银行");
			data_bank.add("中国工商银行 ");
			data_bank.add("中国农业银行");
			data_bank.add("中国建设银行");
			data_bank.add("中国邮政储蓄");
			data_bank.add("交通银行 ");
			data_bank.add("招商银行");
			data_bank.add("中信实业银行");
			data_bank.add("上海浦东发展银行");
			data_bank.add("民生银行");
			data_bank.add("光大银行");
			data_bank.add("广东发展银行");
			data_bank.add("兴业银行");
			data_bank.add("华夏银行");
			data_bank.add("上海银行");
			data_bank.add("北京银行");
			data_bank.add("深圳发展银行");
			data_bank.add("深圳市商业银行");
			data_bank.add("天津市商业银行");
			ShowWheel(data_type);
		}else {
			initWheel();
			param = getIntent().getStringExtra("param");
			ShowWheel(data_type);
		}

	}

	// 获取省数据
	private void getProviceData() {
		String sql = "select * from provinces";
		Cursor cursor = db.database.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("province"));
			String proid = cursor
					.getString(cursor.getColumnIndex("provinceid"));
			data_year.add(name.substring(0, name.length() - 1));
			data_year_code.add(proid);

		}
		cursor.close();
	}

	// 获取市区数据
	private void getCityData(String pname, String provinceid) {
		data_month.clear();
		data_month_code.clear();
		String sql = "select * from cities where provinceid = '" + provinceid
				+ "'";
		Cursor cursor = db.database.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("city"));
			String proid = cursor.getString(cursor.getColumnIndex("cityid"));
			data_month.add(name.length() > 4 ? name.substring(0,
					name.length() - 1) : name);
			data_month_code.add(proid);

		}
		cursor.close();
		if (data_month.size() == 0) {
			if (!TextUtils.isEmpty(pname) && pname.length() >= 2)
				data_month.add(pname.substring(0, 2));
			else
				data_month.add("全部");
			data_month_code
					.add(provinceid.substring(0, provinceid.length() - 2));
		}
	}

	// 获取区数据
	private void getAreasData(String cityid) {
		data_day.clear();
		data_day_code.clear();
		String sql = "select * from areas where cityid = '" + cityid + "'";
		Cursor cursor = db.database.rawQuery(sql, null);
		data_day.add("全部");
		data_day_code.add("0");
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("area"));
			String proid = cursor.getString(cursor.getColumnIndex("areaid"));
			data_day.add(name.length() > 4 ? name.substring(0,
					name.length() - 1) : name);
			data_day_code.add(proid);
		}
		cursor.close();
	}

	private void initWheel() {
		data_point.add(".0");
		data_point.add(".5");
		if (data_type == 1) {
			data_sg = new ArrayList<String>();
			for (int i = 0; i < 110; i++) {
				String data = i + 100 + "";
				data_sg.add(data);
			}
		} else if (data_type == 2) {
			data_tz = new ArrayList<String>();
			for (int i = 0; i < 50; i++) {
				String data = i + 40 + "";
				data_tz.add(data);
			}
		} else {
			data_year = new ArrayList<String>();
			for (int i = 0; i < 100; i++) {
				String data = i + 2015 + "年";
				data_year.add(data);
			}
			data_month = new ArrayList<String>();
			for (int i = 0; i < 12; i++) {
				String data = i + 1 + "月";
				if (i < 9)
					data = 0 + data;
				data_month.add(data);
			}
			data_day = new ArrayList<String>();
			for (int i = 0; i < 31; i++) {
				String data = i + 1 + "日";
				if (i < 9)
					data = 0 + data;
				data_day.add(data);
			}
		}
	}

	private void ShowWheel(int type) {
		if (type == 1) {// 身高
			ll_2.setVisibility(View.VISIBLE);
			ll_3.setVisibility(View.GONE);
			ww_4.setData(data_sg);
			ww_4.setDefault(70);
			ww_5.setData(data_point);
			ww_5.setDefault(1);
		} else if (type == 2) {// 体重
			ll_2.setVisibility(View.VISIBLE);
			ll_3.setVisibility(View.GONE);
			ww_4.setData(data_tz);
			ww_4.setDefault(20);
			ww_5.setData(data_point);
			ww_5.setDefault(1);
		} else if (type == 5) {
			ll_2.setVisibility(View.VISIBLE);
			ll_3.setVisibility(View.GONE);
			ww_4.setData(data_year);
			ww_4.setDefault(p_pos);

			ww_5.setData(data_month);
			ww_5.setDefault(c_pos);
		} else if(type == 6){//银行
			ll_2.setVisibility(View.VISIBLE);
			ll_3.setVisibility(View.GONE);
			ww_5.setVisibility(View.GONE);
			ww_4.setData(data_bank);
			ww_4.setDefault(2);
		}else {
			ll_2.setVisibility(View.GONE);
			ll_3.setVisibility(View.VISIBLE);
			ww_1.setData(data_year);
			ww_1.setDefault(0);
			ww_2.setData(data_month);
			ww_2.setDefault(0);
			ww_3.setData(data_day);
			ww_3.setDefault(0);

		}
	}

	private String cityCode = "", cityName = "";

	public void onClick(View arg0) {
		int id = arg0.getId();
		switch (id) {
		case R.id.tv_quxiao:
			finish();
			if (data_type == 5) {

				overridePendingTransition(R.anim.push_bottom_in,
						R.anim.push_bottom_out);
			} else
				overridePendingTransition(R.anim.push_bottom_in,
						R.anim.push_bottom_out);
			break;
		case R.id.tv_queren:
			String content = "";
			if (data_type == 4) {
				int index = ww_3.getSelected();
				if (index == 0) {
					int city_index = ww_2.getSelected();

					if (data_month_code.size() > 0) {

						cityCode = data_month_code.get(city_index).substring(0,
								4);
						Log.i("citycode", "+++++" + cityCode);
					}
				}

				else if (data_day_code.size() > 0)
					cityCode = data_day_code.get(index);
				cityName =ww_1.getSelectedText()+" "+ ww_2.getSelectedText() + " "
						+ ww_3.getSelectedText();
				Intent inten = new Intent();
				inten.putExtra("cityName", cityName);
				inten.putExtra("cityCode", cityCode);
				setResult(101, inten);
				finish();
				overridePendingTransition(R.anim.push_bottom_in,
						R.anim.push_bottom_out);
			} else if (data_type == 5) {
				Intent inten = new Intent();

				inten.putExtra("year", ww_4.getSelectedText().replace("年", ""));
				inten.putExtra("month", ww_5.getSelectedText().replace("月", ""));
				setResult(101, inten);
				finish();
				overridePendingTransition(R.anim.push_bottom_in,
						R.anim.push_bottom_out);
			}else if(data_type == 6){
				String content1 = ww_4.getSelectedText();
				Intent inten = new Intent();
				inten.putExtra("data", content1);
				setResult(101, inten);
				finish();
				overridePendingTransition(R.anim.push_bottom_in,
						R.anim.push_bottom_out);
			}

			if (data_type != 3) {

				content = ww_4.getSelectedText() + ww_5.getSelectedText();
				if (data_type == 1) {
				} else {
				}
			} else {
				
				content = ww_1.getSelectedText() + ww_2.getSelectedText()
						+ ww_3.getSelectedText();
				content = content.replace("年", "-").replace("月", "-")
						.replace("日", "");
				Intent inten = new Intent();
				inten.putExtra("data", content);
				setResult(101, inten);
				finish();
				overridePendingTransition(R.anim.push_bottom_in,
						R.anim.push_bottom_out);

			}

			break;

		default:
			break;
		}

	}

	

}
