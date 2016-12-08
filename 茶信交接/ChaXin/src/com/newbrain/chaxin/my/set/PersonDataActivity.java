package com.newbrain.chaxin.my.set;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.http.HttpException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.user.User;
import com.newbrain.utils.BitmapUtil;
import com.newbrain.utils.SharedPreferencesHelp;
import com.newbrain.utils.customRounderTransformation;
import com.newbrain.xutils.Xutils_HttpUtils;
import com.squareup.picasso.Picasso;

public class PersonDataActivity extends BaseActivity {

	private Context context;
	private HttpUtils httpUtils;
	private String sexCode = "2";
	private String baseURL="";
	private String nickName="";
	private final int REQUEST_NAME_CODE = 101;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.person_data_activity);

		ViewUtils.inject(this);
		context = this;
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		actionbarInit();
		setBaseData();
		createFile();

	}
	
	private void setBaseData() {
		sexCode = User.getInstance().getSex();
		nickName = User.getInstance().getNickName();
		String url = User.getInstance().getImage();
		if(!TextUtils.isEmpty(url)){
		Picasso.with(context).load(url).
		centerCrop().resize(50, 50)
		.transform(new customRounderTransformation(1))
		.into(iv_user_head);
		}
		tv_nickName.setText(nickName);
		if(sexCode.equals("0"))mTvSex.setText("男");
		else if(sexCode.equals("1"))mTvSex.setText("女");
		else mTvSex.setText("");
		
	}

	@OnClick({ R.id.set_person_data, R.id.person_data_nice_name,
			R.id.person_data_sex, R.id.person_data_receive_goods_manager

	})
	private void onclick_set(View view) {

		System.out.println("-------->");

		switch (view.getId()) {
		case R.id.set_person_data:

			showDialog();

			break;

		case R.id.person_data_nice_name:
			
//			openActivity(ChangeNiceNameActivity.class);
			Intent intent = new Intent(context, ChangeNiceNameActivity.class);
			intent.putExtra("nickName", nickName);
			startActivityForResult(intent, REQUEST_NAME_CODE);

			break;

		case R.id.person_data_sex:

			setSex();

			break;

		case R.id.person_data_receive_goods_manager:

			 openActivity(ReceiveAddressManagerActivity.class);

			break;

		default:
			break;
		}

	}
	
	private void ModifyUserData(int flag){
		CustomProgressDialog.startProgressDialog(context, "正在修改...");
		RequestParams params = new RequestParams();
		String id =  User.getInstance().getId();
		String token = User.getInstance().getToken();
		Log.i("id", "idvalues:??"+id);
		if(TextUtils.isEmpty(id)){
			id = SharedPreferencesHelp.getString(context, "id");
			getCache();
		}
		if(TextUtils.isEmpty(token))token = SharedPreferencesHelp.getString(context, "token");
		params.addBodyParameter("id",id);
//		if(flag==1)
		params.addBodyParameter("image", baseURL);
//		if(flag==2)
		params.addBodyParameter("nickName", nickName);
//		if(flag==3)
		params.addBodyParameter("sex", sexCode);
		params.addBodyParameter("token", token);
		httpUtils.send(HttpMethod.POST, Constant.METHOD_POST_USERS, params,new RequestCallBack<String>() {

			@Override
			public void onFailure(
					com.lidroid.xutils.exception.HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				handleNetworkError();
				CustomProgressDialog.stopProgressDialog();
				
			}
			@Override
			public void onStart() {
				this.getRequestUrl();
				Log.i("modify", "url:<<<"+	this.getRequestUrl());
				super.onStart();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				CustomProgressDialog.stopProgressDialog();
				String result = arg0.result;
				try {
					JSONObject obj = new JSONObject(result);
					String code = obj.getString("code");
					if(code.equals("1")){
						JSONObject jsonObjs = obj.getJSONObject("result"); 
						String phoneNo = jsonObjs.getString("phoneNo");
						String id = jsonObjs.getString("id");
						String sex = jsonObjs.getString("sex");			
						String nickName = jsonObjs.getString("nickName");			
						String image = jsonObjs.getString("image");
						
						User.getInstance().setPhoneNo(phoneNo);
						User.getInstance().setId(id);
						User.getInstance().setSex(sex);
						User.getInstance().setNickName(nickName);
						User.getInstance().setImage(image);
						User.getInstance().setLogin(true);
						SharedPreferencesHelp.SavaString(context, "phoneNo", phoneNo);
						SharedPreferencesHelp.SavaString(context, "id", id);
						SharedPreferencesHelp.SavaString(context, "sex", sex);
						SharedPreferencesHelp.SavaString(context, "nickName", nickName);
						SharedPreferencesHelp.SavaString(context, "image", image);
						SharedPreferencesHelp.SavaBoolean(context, "isLogin", true);
						showShortToast("修改成功");
					}else {
						if(code.equals("2"))goOut();
						else showShortToast("修改失败");
					}
				} catch (JSONException e) {
				}
				
			}
		});
	}
	
	@ViewInject(R.id.person_data_sex_textview)
	private TextView mTvSex;
	@ViewInject(R.id.person_data_nice_name_textview)
	private TextView tv_nickName;
	
	@ViewInject(R.id.person_data_head_picture_imageview)
	private ImageView iv_user_head;
	
	

	private void setSex() {
		
		final String str[] = context.getResources().getStringArray(R.array.choice_sex_array);

		AlertDialog.Builder builder = new Builder(context);

		builder.setTitle(getString(R.string.choice_sex));
		builder.setItems(str, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
					sexCode = which+"";
					
					mTvSex.setText(str[which]);
					
					ModifyUserData(3);

			}
		});
		builder.show();

	}

	/** 判断拍照所在文件夹的创建 */
	private void createFile() {
		// TODO Auto-generated method stub

		File file = new File(Environment.getExternalStorageDirectory()
				+ "/FeiTu/" + IMAGE_FILE_NAME);

		File file_d = new File(Environment.getExternalStorageDirectory()
				+ "/FeiTu");

		if (!file_d.exists()) {

			boolean isCreate = file_d.mkdirs();

			if (isCreate) {
				LogUtils.e("feitu文件夹创建成功");
			} else {

				LogUtils.e("创建失败");
			}

		}

		if (!file.exists()) {

			try {
				file.createNewFile();

				LogUtils.e("jpg文件创建成功");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				LogUtils.e("jpg文件创建失败");
			}

		}

	}

	private String[] items = new String[] { "  选择本地图片", "  拍照" };
	/* 头像名称 */
	private static final String IMAGE_FILE_NAME = "face.png";

	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;

	/**
	 * 显示选择对话框
	 */
	private void showDialog() {

		new AlertDialog.Builder(this)
				.setTitle("设置头像")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							/*Intent intentFromGallery = new Intent();
							intentFromGallery.setType("image/*"); // 设置文件类型
							intentFromGallery
									.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(intentFromGallery,
									IMAGE_REQUEST_CODE);*/
							takePhoto();
							break;
						case 1:

							Intent intentFromCapture = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							// 判断存储卡是否可以用，可用进行存储
							if (hasSdcard()) {

								intentFromCapture.putExtra(
										MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(new File(Environment
												.getExternalStorageDirectory()
												+ "/FeiTu/" + IMAGE_FILE_NAME)));
							}

							startActivityForResult(intentFromCapture,
									CAMERA_REQUEST_CODE);
							break;
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();

	}
	/**
	 * 调用系统相册选图片
	 * @param v
	 */
	private void takePhoto(){
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
		startActivityForResult(intent,IMAGE_REQUEST_CODE);
	}
	
	
	/*
	 * public static String device_name; public static String device_address;
	 */

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 结果码不等于取消时候

		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {

			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (hasSdcard()) {
					File tempFile = new File(
							Environment.getExternalStorageDirectory()
									+ "/FeiTu/" + IMAGE_FILE_NAME);
					startPhotoZoom(Uri.fromFile(tempFile));
				} else {
					Toast.makeText(context, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG)
							.show();
				}

				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {

					getImageToView(data);

				}
				break;
			case REQUEST_NAME_CODE:
				nickName = data.getStringExtra("nickName");
				tv_nickName.setText(nickName);
				ModifyUserData(2);
				break;
				
			/*
			 * case Constant.REQUEST_CODE_REMOTE:
			 * 
			 * MainActivity.device_name =
			 * data.getStringExtra(DeviceScanActivity.DEVICE_NAME);
			 * MainActivity.device_address =
			 * data.getStringExtra(DeviceScanActivity.DEVICE_ADDRESS);
			 * 
			 * sendBroadcast(new Intent("receiver_scan_result"));
			 * 
			 * break;
			 */

			}
		}else {
			
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void getCache(){
		User.getInstance().setPhoneNo(SharedPreferencesHelp.getString(context, "phoneNo"));
		User.getInstance().setId(SharedPreferencesHelp.getString(context, "id"));
		User.getInstance().setSex(SharedPreferencesHelp.getString(context, "sex"));
		User.getInstance().setToken(SharedPreferencesHelp.getString(context, "token"));
		User.getInstance().setNickName(SharedPreferencesHelp.getString(context, "nickName"));
		User.getInstance().setImage(SharedPreferencesHelp.getString(context, "image"));
		User.getInstance().setLogin(SharedPreferencesHelp.getBoolean(context, "isLogin"));
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra(
				"output",
				Uri.fromFile(new File(Environment.getExternalStorageDirectory()
						+ "/FeiTu/" + IMAGE_FILE_NAME)));
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}
	
	private void uploadFile(String base64Bitmap){
		RequestParams params = new RequestParams();
		params.addBodyParameter("imgStr", base64Bitmap);
		httpUtils.send(HttpMethod.POST, Constant.METHOD_upload_file, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(com.lidroid.xutils.exception.HttpException arg0, String arg1) {
				handleNetworkError();
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result = arg0.result;
				Log.i("upload", "upload:<<<"+	result);
				try {
					JSONObject obj = new JSONObject(result);
					String code = obj.getString("code");
					if(code.equals("1")){
//						JSONObject jsonObjs = obj.getJSONObject("result"); 
						baseURL = obj.getString("image");
						if(!TextUtils.isEmpty(baseURL)){
							Picasso.with(context).load(baseURL).
							centerCrop().resize(50, 50)
							.transform(new customRounderTransformation(1))
							.into(iv_user_head);
							}
						ModifyUserData(1);
					}
					}catch (Exception e) {
						// TODO: handle exception
					}
			}
		});
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();

		if (extras != null) {
			 Bitmap photo = extras.getParcelable("data");
//			 baseURL = BitmapUtil.bitmapToBase64(photo);
			 
			/* Drawable drawable = new BitmapDrawable(photo);
			 iv_user_head.setImageDrawable(drawable);*/
			 uploadFile(BitmapUtil.bitmapToBase64(photo));
		}
	}

	public boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
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
		actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText(getString(R.string.set_person_data));
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
