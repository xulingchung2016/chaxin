package com.liren_app.ui;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.my.mycash.RechargeActivity;
import com.newbrain.customview.CustomDialog;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.user.User;
import com.newbrain.utils.BitmapUtil;
import com.newbrain.utils.customRounderTransformation;
import com.newbrain.xutils.Xutils_HttpUtils;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("CutPasteId") public class MyLiRenActivity extends BaseActivity {

	private LirenInfo info;
	private ImageView img_id_zhengmian;
	private ImageView img_id_fanmain;
	private ImageView img_id_chayi;
	private Button btn_submit;
	private ImageButton btn_back;
	private EditText txt_chagyuan;
	private RelativeLayout rl_bottom,rl_top;
	private int status;
	private CheckBox ck;
	private EditText et_name,edt_age,edt_zigezheng;
	private TextView edt_sex;
	private Context context;
	private HttpUtils httpUtils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_li_ren);
		context = this;
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		status = getIntent().getIntExtra("status", 0);
		initUI();
		initControlListener();
		info = new LirenInfo();
		info.Sex = "1";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_my_li_ren, menu);
		
		return true;
	}

	@Override
	protected void initUI() {
		
		img_id_zhengmian = (ImageView) findViewById(R.id.img_id_zhengmian);
		img_id_fanmain = (ImageView) findViewById(R.id.img_id_fanmain);
		img_id_chayi = (ImageView) findViewById(R.id.img_id_chayi);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_back = (ImageButton) findViewById(R.id.btn_back);
		txt_chagyuan=(EditText) findViewById(R.id.txt_chagyuan);
		rl_bottom = (RelativeLayout) findViewById(R.id.bottom);
		rl_top = (RelativeLayout) findViewById(R.id.rl_top);
		et_name = (EditText) findViewById(R.id.edt_name);
		ck = (CheckBox) findViewById(R.id.img_mianze);
		edt_age = (EditText) findViewById(R.id.edt_age);
		edt_zigezheng = (EditText) findViewById(R.id.edt_zigezheng);
		edt_sex = (TextView) findViewById(R.id.edt_sex);//性别
		
		if(status == 2){
			rl_top.setVisibility(View.GONE);
			rl_bottom.setVisibility(View.VISIBLE);
		}
		
		
	}
	
	@Override
	protected void initControlListener() {
		// TODO Auto-generated method stub
		edt_sex.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final CustomDialog customDialog = new CustomDialog(context,true);
				customDialog.setDialogTitle("选择性别");
				customDialog.hideDialogContent();
				customDialog.hideDialogEdittext();
				customDialog.setLeftBtnText("女");
				customDialog.setRightBtnText("男");
				customDialog.setLeftBtnListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						info.Sex = "0";
						edt_sex.setText("女");
						customDialog.dismiss();
					}
				});
				
				customDialog.setRightBtnListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						info.Sex = "1";
						edt_sex.setText("男");
						customDialog.dismiss();
						
					}
				});
				
				customDialog.show();
			}
		});

		img_id_zhengmian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 1001);
			}
		});
		img_id_fanmain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 1002);
			}
		});
		img_id_chayi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 1003);
			}
		});

		btn_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				submit();

			}
		});
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
	}
	
	//提交丽人资料
	protected void submit() {
		String uid = User.getInstance().getId();
		String token = User.getInstance().getToken();
		String name = et_name.getText().toString().trim();
		String tenNo = txt_chagyuan.getText().toString().trim();
		if(TextUtils.isEmpty(name)){
			Toast.makeText(context, "请填写真实姓名", Toast.LENGTH_SHORT).show();
			return;
		}
		if(TextUtils.isEmpty(tenNo)){
			tenNo = "";
			/*Toast.makeText(context, "茶馆编号不能为空！", Toast.LENGTH_SHORT).show();
			return;*/
		}
		if(!ck.isChecked()){
			Toast.makeText(context, "请勾选免责协议", Toast.LENGTH_SHORT).show();
			return;
		}
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId", uid);
		params.addBodyParameter("token", token);
		params.addBodyParameter("sex", info.Sex);
		params.addBodyParameter("name", name);
		params.addBodyParameter("age", edt_age.getText().toString().trim());
		params.addBodyParameter("storeId",tenNo );
		params.addBodyParameter("certNo", edt_zigezheng.getText().toString());
		params.addBodyParameter("frontImage", info.frontImage);
		params.addBodyParameter("backImage", info.backImage);
		params.addBodyParameter("certImage", info.certImage);
		/*params.addBodyParameter("memo", "");
		params.addBodyParameter("image", "");
		params.addBodyParameter("photo", "");*/
		String url =Constant.METHOD_saveTeaBeauty+"?userId="+uid+"&token="+token+"&sex="
		+info.Sex+"&name="+name+"&age="+edt_age.getText().toString().trim()+"&storeId="
				+tenNo+"&certNo="+edt_zigezheng.getText().toString()
				+"&frontImage="+info.frontImage+"&backImage="+info.backImage+"&certImage="+info.certImage;
		CustomProgressDialog.startProgressDialog(context, "数据上传中...");
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				CustomProgressDialog.stopProgressDialog();
				Toast.makeText(context, "提交失败！", Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				Log.i("url", "url>>>>>:"+this.getRequestUrl());
				super.onStart();
			}
			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				CustomProgressDialog.stopProgressDialog();
				try {
					JSONObject obj = new JSONObject(arg0.result);
					String code = obj.getString("code");
					String msg = obj.getString("message");
					if(code.equals("1")){
						rl_top.setVisibility(View.GONE);
						rl_bottom.setVisibility(View.VISIBLE);
					}else{
						
						Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		});
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
		Bundle bundle = data.getExtras();
		if (bundle == null)
			return;
		Bitmap img = (Bitmap) bundle.get("data");
		if (img != null) {
			if (requestCode == 1001) {

				img_id_zhengmian.setImageBitmap(img);
				uploadFile(BitmapUtil.bitmapToBase64(img),1 );
			} else if (requestCode == 1002) {
				img_id_fanmain.setImageBitmap(img);
				uploadFile(BitmapUtil.bitmapToBase64(img),2 );
			} else if (requestCode == 1003) {

				img_id_chayi.setImageBitmap(img);
				uploadFile(BitmapUtil.bitmapToBase64(img),3 );
			}
		} else {
			if (requestCode == 1004) {
				
				txt_chagyuan.setText(data.getExtras().getString("name"));
					
			}
		}
		}
	}
	private void uploadFile(String base64Bitmap,final int type){
		CustomProgressDialog.startProgressDialog(context, "数据上传中...");
		RequestParams params = new RequestParams();
		params.addBodyParameter("imgStr", base64Bitmap);
		httpUtils.send(HttpMethod.POST, Constant.METHOD_upload_file, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(com.lidroid.xutils.exception.HttpException arg0, String arg1) {
				CustomProgressDialog.stopProgressDialog();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				CustomProgressDialog.stopProgressDialog();
				String result = arg0.result;
				Log.i("upload", "upload:<<<"+	result);
				try {
					JSONObject obj = new JSONObject(result);
					String code = obj.getString("code");
					if(code.equals("1")){
//						JSONObject jsonObjs = obj.getJSONObject("result"); 
						String url = obj.getString("image");
						if(type == 1)info.frontImage = url;
						else if(type == 2)info.backImage = url;
						else if(type == 3)info.certImage = url;
						CustomProgressDialog.stopProgressDialog();
						Toast.makeText(context, "图片已经上传", Toast.LENGTH_SHORT).show();
					}
					}catch (Exception e) {
					}
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

class LirenInfo {
	public String name;
	public String Sex;
	public String userId;
	public String age;
	public String storeId;
	public String certNo;
	public String frontImage;
	public String backImage;
	public String certImage;
	public String Memo;
	public String Token;
}