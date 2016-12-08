package com.newbrain.xutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DaoConfig;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;
import com.newbrain.chaxin.R;

public class Xutils_DBUtils implements DbUpgradeListener{

	private static DbUtils dbUtils;
	private final int BUFFER_SIZE = 400000;
	public static final String DB_NAME = "city.db"; // 保存的数据库文件名
	public static final String PACKAGE_NAME = "com.newbrain.chaxin";
	// public static final String PACKAGE_NAME = "com.xulc.test";
	public static final String DB_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ PACKAGE_NAME + "/" + "databases"; // 在手机里存放数据库的位置
	private Context context;
	public SQLiteDatabase database;
	public void openDatabase() {
		this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
	}
	public Xutils_DBUtils(Context context) {
		this.context = context;
	}

	public  DbUtils getDBUtils(Context context) {

		if (dbUtils == null) {
			DaoConfig config = new DaoConfig(context);
			config.setDbName("anerfa.db");
			config.setDbVersion(1);
			config.setDbUpgradeListener(this);
			dbUtils = DbUtils.create(config);
			

		}
		
		return dbUtils;

	}

	@Override
	public void onUpgrade(DbUtils arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	private SQLiteDatabase openDatabase(String dbfile) {
		try {
			File file = new File(dbfile);
			if (!file.exists()) {// 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库

				File filedir = new File(DB_PATH);
				if (!filedir.exists()) {
					filedir.mkdirs();
				}

				file.createNewFile();

				InputStream is = context.getResources().openRawResource(
						R.raw.china_city); // 欲导入的数据库
				FileOutputStream fos = new FileOutputStream(dbfile);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
			SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,
					null);
			return db;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
