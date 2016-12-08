package com.newbrain.chaxin.my.set;

import java.io.File;
import java.math.BigDecimal;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.user.User;
import com.newbrain.utils.SharedPreferencesHelp;

public class SetActivity extends BaseActivity {

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_activity);

		ViewUtils.inject(this);
		context = this;
		actionbarInit();
		try {
			String size = getCacheSize(getCacheDir());
			tv_cache_size.setText(size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		checkMessageRemidInit();
	}
	
	public static long getFolderSize(File file) throws Exception {  
		        long size = 0;  
		        try {  
		           File[] fileList = file.listFiles();  
	          for (int i = 0; i < fileList.length; i++) {  
		               // 如果下面还有文件  
		                if (fileList[i].isDirectory()) {  
		                  size = size + getFolderSize(fileList[i]);  
		              } else {  
		                   size = size + fileList[i].length();  
		                }  
		            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
		        }  
	        return size;  
		   }  

	
	
	
	private void checkMessageRemidInit() {
		// TODO Auto-generated method stub
		
		
		mTbCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
				if(isChecked)
				{
					
					
					
				}else
				{
					
					
					
					
				}
				
				
			}
		});
		
		
	}



	@ViewInject(R.id.set_person_data)
	private LinearLayout mllset_person_data;
	@ViewInject(R.id.set_cache_size)
	private TextView tv_cache_size;
	
	

	@ViewInject(R.id.set_account_manager)
	private LinearLayout mllset_account_manager;
	
	

	@ViewInject(R.id.set_message_remind)
	private LinearLayout mllset_message_remind;
	
	

	@ViewInject(R.id.set_clear_cache)
	private LinearLayout mllset_clear_cache;
	

	@ViewInject(R.id.set_about_chaxin)
	private LinearLayout mllset_about_chaxin;
	
	
	
	@ViewInject(R.id.set_check_message_remind)
	private ToggleButton mTbCheck;
	
	
	
	

	@OnClick({ R.id.set_person_data, R.id.set_account_manager,
			R.id.set_message_remind, R.id.set_clear_cache,
			R.id.set_about_chaxin,R.id.btn_exit,

	})
	private void onclick_set(View view) {

		System.out.println("-------->");
		
		switch (view.getId()) {
		case R.id.set_person_data:
			
			openActivity(PersonDataActivity.class);

			break;

		case R.id.set_account_manager:
			
			openActivity(AccountManagerActivity.class);

			break;

		case R.id.set_message_remind:
			
			

			break;
		case R.id.set_clear_cache:

			
			cleanInternalCache(context);
			
			
			
			break;

		case R.id.set_about_chaxin://关于茶信
			openActivity(about.class);
			

			break;
		case R.id.btn_exit:
			goOuts();
		break;

		default:
			break;
		}

	}
	
	
	private void goOuts() {
		User.getInstance().setPhoneNo("");
		User.getInstance().setId("");
		User.getInstance().setSex("2");
		User.getInstance().setNickName("");
		User.getInstance().setImage("");
		User.getInstance().setToken("");
		User.getInstance().setLogin(false);
		SharedPreferencesHelp.SavaString(context, "phoneNo", "");
		SharedPreferencesHelp.SavaString(context, "id", "");
		SharedPreferencesHelp.SavaString(context, "sex", "2");
		SharedPreferencesHelp.SavaString(context, "nickName", "");
		SharedPreferencesHelp.SavaString(context, "image", "");
		SharedPreferencesHelp.SavaString(context, "token", "");
		SharedPreferencesHelp.SavaBoolean(context, "isLogin", false);
		SharedPreferencesHelp.SavaString(context, "cash","0.0");
		SharedPreferencesHelp.SavaString(context, "gold","0");
		showShortToast("已退出登录");
		finish();
		
	}



	/** * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context */
    public  void cleanInternalCache(Context context) {
        deleteFilesByDirectory(getCacheDir());
        tv_cache_size.setText("0M");
        showShortToast(R.string.cleanCache_success);
    }

    /** * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context */
    public  void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
     * context
     */
    public  void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /** * 按名字清除本应用数据库 * * @param context * @param dbName */
    public  void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /** * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context */
    public  void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
     * context
     */
    public  void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /** * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * * @param filePath */
    public  void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /** * 清除本应用所有的数据 * * @param context * @param filepath */
    public  void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    /** * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory */
    private  void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
            	if(item.isFile())
                item.delete();
            	if(item.isDirectory())
                deleteFilesByDirectory(item);
            }
        }
    }
    
    /**
	 * 格式化单位
	 * 
	 * @param size
	 * @return
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			return size + "M";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "KB";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "MB";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
				+ "TB";
	}
	
	
	public static String getCacheSize(File file) throws Exception {
		return getFormatSize(getFolderSize(file));
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

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText(getString(R.string.set));
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
