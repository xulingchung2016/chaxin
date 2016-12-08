package com.newbrain.chaxin.my.mybeauty;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.crypto.spec.IvParameterSpec;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.lib.BitmapUtil;
import com.newbrain.lib.CropHandler;
import com.newbrain.lib.CropHelper;
import com.newbrain.lib.CropParams;
import com.newbrain.user.User;
import com.newbrain.utils.ButtonColorFilter;
import com.newbrain.utils.SharedPreferencesHelp;
import com.newbrain.utils.customRounderTransformation;
import com.newbrain.xutils.Xutils_HttpUtils;
import com.squareup.picasso.Picasso;

public class MyBeautyProfileFragment extends Fragment implements CropHandler,OnItemClickListener{
	
	
	private Context context;
	@ViewInject(R.id.et_name)
	EditText et_name;
	@ViewInject(R.id.my_phone)
	EditText et_phone;
	@ViewInject(R.id.my_tea)
	EditText et_chaguan;
	@ViewInject(R.id.my_intro)
	EditText et_intro;
	@ViewInject(R.id.my_beauty_profile_id)
	TextView et_no;
	@ViewInject(R.id.my_icon)
	ImageView my_icon;
	@ViewInject(R.id.btn_save)
	Button btn_save;
	private HttpUtils httpUtils;
	private List<String> urlsT = new ArrayList<String>();
	private String id;
	@ViewInject(R.id.gallery)
	private Gallery gallery;
	private HashMap<String,String> maps ;
	private ImageAdapter adapter;
	 public static final String TAG = "MyBeautyProfileFragment";
	   private  CropParams mCropParams;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.my_beauty_profile_frament, null);
		
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewUtils.inject(this,view);
		context = getActivity();
		 mCropParams = new CropParams(getActivity());
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		ButtonColorFilter.setButtonFocusChanged(btn_save);
		 createFile();
		getData();
		
		
	}
	
	private void setData() {
		adapter = new ImageAdapter(context);
		gallery.setAdapter(adapter);
		gallery.setSelection(gallery.getCount()/2);
		gallery.setOnItemClickListener(this);
		
	}

	String userid,token;
	/**
	 * 获取资料
	 */
	private void getData() {
		CustomProgressDialog.startProgressDialog(context, "loading...");
		token = User.getInstance().getToken();
		userid = User.getInstance().getId();
		
		String beautyId = SharedPreferencesHelp.getString(context,"chaxin_beautyid" );
		/*RequestParams params = new RequestParams();
		params.addBodyParameter("userId", userid);
		params.addBodyParameter("token", token);*/
//		params.addBodyParameter("beautyId", id);
//		String url = Constant.METHOD_gdetail+"?beautyId="+userid+"&token="+token;
		String url = Constant.METHOD_gdetail+"?beautyId="+beautyId;
		httpUtils.send(HttpMethod.GET,url ,new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				CustomProgressDialog.stopProgressDialog();
				setData();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
//					{"message":"查询成功","result":{"sex":1,"storeName":"","memo":"在乎","status":3,"no":"","beautyNo":"864069","certImage":"","image":"","professional":0,"backImage":"","id":"5a740a0e50f1eed90150f63875b90039","onTime":0,"phoneNo":"18686568656","price":230,"area":"","levels":0,"communicate":0,"sales":2,"age":0,"userId":"5a740a0e50c8435a0150cccc6e26000b","name":"在乎别人说","certNo":"","frontImage":"","storeId":""},"code":"1"}
					CustomProgressDialog.stopProgressDialog();
					JSONObject	jsonObjs = new JSONObject(arg0.result);
					String msg = jsonObjs.getString("message");
					String code = jsonObjs.getString("code");
//					{"message":"查询成功","result":{"sex":1,"storeName":"刚刚好","memo":"在乎多少人生路过年轻化为什么样本文学奖品","status":3,"no":"","beautyNo":"864069","certImage":"","image":"http:\/\/218.244.138.142:8081\/TeaMall\/upload\/2015\/11\/15\/738b450f0958447fae7519264091156f.png","professional":0,"backImage":"","id":"5a740a0e50f1eed90150f63875b90039","onTime":0,"phoneNo":"18686568656","price":230,"area":"","levels":0,"communicate":0,"sales":8,"age":0,"userId":"5a740a0e50c8435a0150cccc6e26000b","name":"在人说","certNo":"","frontImage":"","storeId":""},"code":"1"}
					if(code.equals("1")){
						JSONObject obj = jsonObjs.getJSONObject("result");
						if(obj.has("name"))et_name.setText(obj.getString("name"));
						if(obj.has("beautyNo"))et_no.setText(obj.getString("beautyNo"));
						if(obj.has("phoneNo"))et_phone.setText(obj.getString("phoneNo"));
						if(obj.has("storeId"))et_chaguan.setText(obj.getString("storeId"));
						if(obj.has("memo"))et_intro.setText(obj.getString("memo"));
						uploadUrl = obj.getString("image");
						if(!TextUtils.isEmpty(uploadUrl)){
							Picasso.with(context).load(uploadUrl).centerCrop().resize(60, 60).into(my_icon);
						}
						if(obj.has("photo")){
						String url = obj.getString("photo");
						if(!TextUtils.isEmpty(url)&&!url.equals("null")){
							String []urls = url.split(",");
//							urlsT = Arrays.asList(urls);
							int lenth = urls.length;
							for(int i = 0;i<lenth;i++){
								urlsT.add(urls[i]);
							}
						}
						}
					}else
					Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show();
				}
				setData();
			}
		});
		
		
	}
	private int type=1;//1为头像，2为轮播图
	@OnClick({R.id.my_icon,R.id.btn_save,R.id.btn_take_pic})
	private void onClick_Profile(View view)
	{
		
		switch (view.getId()) {
		case R.id.my_icon:
			type = 1;
			showDialog() ;
			break;
		case R.id.btn_save:
			submit();
			break;
		case R.id.btn_take_pic:
			if(urlsT.size()<5){
			type = 2;
			showDialog() ;
			}else{
				Toast.makeText(context, "最多上传5张照片！", 1).show();
			}
			break;
		default:
			break;
		}
		
		
	}
	private String uploadUrl;
	private void submit() {
		CustomProgressDialog.startProgressDialog(context, "数据上传中...");
		String name = et_name.getText().toString().trim();
		String meom = et_intro.getText().toString().trim();
		String storeName = et_chaguan.getText().toString().trim();
		if(TextUtils.isEmpty(storeName)){
			storeName = "";
			/*Toast.makeText(context, "茶馆编号不能为空！", Toast.LENGTH_SHORT).show();
			return;*/
		}
		if(urlsT == null||urlsT.size()<1){
			Toast.makeText(context, "请为丽人添加至少一张图片", Toast.LENGTH_SHORT).show();
			return;
		}
		String urlss = urlsT.toString();
		 urlss = urlss.substring(1, urlss.length() -1);
		Log.i("urls>>>>>>>>>>>>", urlss);
		String phone = et_phone.getText().toString().trim();
		RequestParams params = new RequestParams();
		params.addBodyParameter("userId", userid);
		params.addBodyParameter("token", token);
		params.addBodyParameter("name", name);
		params.addBodyParameter("memo", meom);
		params.addBodyParameter("phoneNo", phone);
		params.addBodyParameter("image", uploadUrl);
		params.addBodyParameter("storeId", storeName);
		params.addBodyParameter("photo", urlss);
//		String url = Constant.METHOD_gdetail+"?userId="+userid+"&token="+token;
		httpUtils.send(HttpMethod.POST,Constant.METHOD_gupdateTeaBeautyl,params ,new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show();
				CustomProgressDialog.stopProgressDialog();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					CustomProgressDialog.stopProgressDialog();
					JSONObject	jsonObjs = new JSONObject(arg0.result);
					String msg = jsonObjs.getString("message");
					String code = jsonObjs.getString("code");
					Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		
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
	private String[] items = new String[] {  "  拍照","  选择本地图片" };
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
		
		new AlertDialog.Builder(context)
				.setTitle("设置头像")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = null;
						mCropParams.refreshUri();
						switch (which) {
						case 0:
							 mCropParams.enable = true;
				                mCropParams.compress = false;
				                if(type == 2){
				                mCropParams.aspectX = 2;
				                mCropParams.aspectY = 1;
				                mCropParams.outputX = 480;
				                mCropParams.outputY = 320;
				                }else{
				                	mCropParams.aspectX = 1;
					                mCropParams.aspectY = 1;
					                mCropParams.outputX = 320;
					                mCropParams.outputY = 320;
				                }
				                 intent = CropHelper.buildCameraIntent(mCropParams);
				                startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
							break;
						case 1:
							 mCropParams.enable = true;
			                 mCropParams.compress = false;
			                 
			                 if(type == 2){
					                mCropParams.aspectX = 2;
					                mCropParams.aspectY = 1;
					                mCropParams.outputX = 480;
					                mCropParams.outputY = 320;
					                }else{
					                	mCropParams.aspectX = 1;
						                mCropParams.aspectY = 1;
						                mCropParams.outputX = 320;
						                mCropParams.outputY = 320;
					                }
			                 
			                
			                 intent = CropHelper.buildGalleryIntent(mCropParams);
			                startActivityForResult(intent, CropHelper.REQUEST_CROP);
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
	
	
	private void uploadFile(String base64Bitmap){
		RequestParams params = new RequestParams();
		params.addBodyParameter("imgStr", base64Bitmap);
		CustomProgressDialog.startProgressDialog(context, "上传图片中...");
		httpUtils.send(HttpMethod.POST, Constant.METHOD_upload_file, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(com.lidroid.xutils.exception.HttpException arg0, String arg1) {
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result = arg0.result;
				Log.i("upload", "upload:<<<"+	result);
				try {
					JSONObject obj = new JSONObject(result);
					String code = obj.getString("code");
					CustomProgressDialog.stopProgressDialog();
					if(code.equals("1")){
						final String baseUrl = obj.getString("image");
						if(type == 1){
//						JSONObject jsonObjs = obj.getJSONObject("result"); 
						uploadUrl = baseUrl;
						
						if(!TextUtils.isEmpty(uploadUrl)){
							
							Picasso.with(context).load(uploadUrl).
							centerCrop().resize(70, 70)
//							.transform(new customRounderTransformation(1))
							.into(my_icon);
							}
						}else{
							getActivity().runOnUiThread(new Runnable() {
						 		public void run() {
						 			urlsT.add(baseUrl);
						 			adapter.notifyDataSetChanged();
						 			gallery.setSelection(gallery.getCount()/2);
						 		}
						 	});
						}
					}
					}catch (Exception e) {
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
//			my_icon.setBackgroundDrawable(new BitmapDrawable(photo));
			 uploadFile(com.newbrain.utils.BitmapUtil.bitmapToBase64(photo));
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 结果码不等于取消时候
		 CropHelper.handleResult(this, requestCode, resultCode, data);
	        if (requestCode == 1) {
	        	
	        }
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	class ImageAdapter extends BaseAdapter{
		
		private Context context;

		public ImageAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return urlsT.size();
		}

		@Override
		public Object getItem(int arg0) {
			return urlsT.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(final int pos, View conterview, ViewGroup arg2) {
			View view = LayoutInflater.from(context).inflate(R.layout.item_write, null);
			ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
			ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
			String url = urlsT.get(pos).trim();
			
//			iv_icon.setImageResource((Integer)map.get("url"));
			if(!TextUtils.isEmpty(url)){
				Picasso.with(context).load(url).error(R.drawable.yy_cha_1).centerCrop().resize(60, 60).into(iv_icon);
			}
			iv_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Log.i("pos>>>>", "位置："+pos);
					urlsT.remove(pos);
					adapter.notifyDataSetChanged();
				}
			});
			return view;
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	 

	    @Override
	    public CropParams getCropParams() {
	        return mCropParams;
	    }

	    @Override
	    public void onPhotoCropped(Uri uri) {
	        // Original or Cropped uri
	    	Bitmap photo = null;
	        if (!mCropParams.compress)
	        	  photo =BitmapUtil.decodeUriAsBitmap(getActivity(), uri);
	        	if(photo != null)
	        	uploadFile(com.newbrain.utils.BitmapUtil.bitmapToBase64(photo));
//	            mImageView.setImageBitmap(BitmapUtil.decodeUriAsBitmap(getActivity(), uri));
	    }

	    @Override
	    public void onCompressed(Uri uri) {
	        // Compressed uri
//	        mImageView.setImageBitmap(BitmapUtil.decodeUriAsBitmap(getActivity(), uri));
	    }

	    @Override
	    public void onCancel() {
	        Toast.makeText(getActivity(), "Crop canceled!", Toast.LENGTH_LONG).show();
	    }

	    @Override
	    public void onFailed(String message) {
	        Toast.makeText(getActivity(), "Crop failed: " + message, Toast.LENGTH_LONG).show();
	    }

	    @Override
	    public void handleIntent(Intent intent, int requestCode) {
	        startActivityForResult(intent, requestCode);
	    }
	
	
	

}