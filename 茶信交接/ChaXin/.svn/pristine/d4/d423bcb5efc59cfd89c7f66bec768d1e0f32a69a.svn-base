package com.liren_app.ui;

import org.json.JSONException;
import org.json.JSONObject;

import com.liren_app.http.GetImgByUrl;
import com.liren_app.http.GetUrl;
import com.liren_app.http.IImageListener;
import com.liren_app.http.MessageKey;
import com.newbrain.chaxin.R;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DianDianActivity extends BaseActivity implements IImageListener {

	private JSONObject mCurrentLiren;
	private ImageButton btn_back;
	private TextView txt_title;

	private TextView txt_name;
	private TextView txt_num;
	private TextView txt_chaguan;
	private TextView txt_zhengshu_num;
	private TextView txt_zhuanye;
	private TextView txt_goutong;
	private TextView txt_zhunshi;
	private ImageView img_logo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dian_dian);
		try {
			String json = getIntent().getExtras().getString("liren");
			mCurrentLiren = new JSONObject(json);
		} catch (JSONException e) {
			mCurrentLiren = null;
		}
		initUI();
		initData();
		initControlListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_dian_dian, menu);
		return true;
	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub
		btn_back = (ImageButton) findViewById(R.id.btn_back);
		txt_title = (TextView) findViewById(R.id.txt_title);
		img_logo = (ImageView) findViewById(R.id.img_logo);
		txt_name = (TextView) findViewById(R.id.txt_name);
		txt_num = (TextView) findViewById(R.id.txt_num);
		txt_chaguan = (TextView) findViewById(R.id.txt_chaguan);
		txt_zhengshu_num = (TextView) findViewById(R.id.txt_zhengshu_num);
		txt_zhuanye = (TextView) findViewById(R.id.txt_zhuanye);
		txt_goutong = (TextView) findViewById(R.id.txt_goutong);
		txt_zhunshi = (TextView) findViewById(R.id.txt_zhunshi);
	}

	@Override
	protected void initControlListener() {
		// TODO Auto-generated method stub
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DianDianActivity.this.finish();
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
		try {
			txt_title.setText(mCurrentLiren.getString("name"));

			txt_name.setText(mCurrentLiren.getString("name"));
			txt_num.setText(mCurrentLiren.getString("no"));
			txt_chaguan.setText(mCurrentLiren.getString("storeName"));
			txt_zhengshu_num.setText(mCurrentLiren.getString("certNo"));
			txt_zhuanye.setText(mCurrentLiren.getString("professional"));
			txt_goutong.setText(mCurrentLiren.getString("communicate"));
			txt_zhunshi.setText(mCurrentLiren.getString("onTime"));
			if (1 == 1) {

				String img_url;
				try {
					img_url = mCurrentLiren.getString("image");
					img_url = GetUrl.getImgUrl(img_url);
					GetImgByUrl.getImage(MessageKey.get_liren_img,
							DianDianActivity.this, img_url);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onGetImg(int key, Bitmap img) {
		// TODO Auto-generated method stub
		if (key == MessageKey.get_liren_img) {
			if (img != null) {
				Message msg = new Message();
				msg.what = MessageKey.get_liren_img;
				msg.obj = img;
				mHandler.sendMessage(msg);
			}

		}
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {

			case MessageKey.get_liren_img:
				img_logo.setImageBitmap((Bitmap) msg.obj);
				break;

			default:
				break;
			}
		}

	};

}
