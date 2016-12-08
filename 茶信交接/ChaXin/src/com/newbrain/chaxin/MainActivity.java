package com.newbrain.chaxin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.baseactivity.BaseActivity_FA;
import com.newbrain.user.User;
import com.newbrain.utils.SharedPreferencesHelp;
import com.newbrain.xutils.Xutils_DBUtils;

public class MainActivity extends BaseActivity_FA {

	private Context context;

	@ViewInject(R.id.activity_main_radioGroup)
	private RadioGroup mRgMain;
	@ViewInject(R.id.activity_main_tab2)
	private RadioButton rb2;
	
	
	
	/**当前fragment的下标   下标相对于数组*/
	private int mCurrnetIndex ;
	
	/**当前frament的id*/
	private int mCurrentFragmentID = R.id.activity_main_tab1;
	/**存放fragment的容器*/
	private int mContainLayoutID = R.id.activity_main_contains;

	private Tab1Fragment mFraTab1;
	private Tab22Fragment mFraTab2;
	private Tab3Fragment mFraTab3;
	
	
	/**主界面fragment的数组*/
	private Fragment mFraAllTab[];
	private Xutils_DBUtils db;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		context = this;
		ViewUtils.inject(this);
		tabInit();
		db = new Xutils_DBUtils(context);
		db.openDatabase();
        SharedPreferences sp = context.getSharedPreferences("CHAXIN_CONFIG", MODE_PRIVATE);
	}


	private void tabInit()
	{
		// TODO Auto-generated method stub
		mFraTab1 = new Tab1Fragment();
		mFraTab2 = new Tab22Fragment();
		mFraTab3 = new Tab3Fragment();
		
		mFraAllTab = new Fragment[]{mFraTab1,mFraTab2,mFraTab3};

		getSupportFragmentManager().beginTransaction()
				.add(mContainLayoutID, mFraTab1)
				.add(mContainLayoutID, mFraTab2)
				.add(mContainLayoutID, mFraTab3).hide(mFraTab2).hide(mFraTab3).commit();

		mRgMain.setOnCheckedChangeListener(mOnCheckedChangeListener);
		getCache();

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if(SharedPreferencesHelp.getBoolean(context, "isGWC")){

			getSupportFragmentManager().beginTransaction()
					.show(mFraTab2).hide(mFraTab1).hide(mFraTab3).commit();
			
//			mCurrentFragmentID = checkedId;
//			mFraTab2.setList();
			rb2.setChecked(true);
		}
		super.onResume();
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

	private OnCheckedChangeListener mOnCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub

			if (checkedId == mCurrentFragmentID) {

				return;
			}
			
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			
			int temp = mCurrnetIndex;

			SharedPreferencesHelp.SavaBoolean(context, "isGWC", false);
			switch (checkedId) {
			case R.id.activity_main_tab1:
				
				mCurrnetIndex = 0;
				

				break;
			case R.id.activity_main_tab2:
				
				mCurrnetIndex = 1;

				break;
			case R.id.activity_main_tab3:
				
				
				mCurrnetIndex = 2;
				
				break;

			default:
				break;
			}
			
			
			if(!mFraAllTab[mCurrnetIndex].isAdded())
			{
				ft.add(mContainLayoutID, mFraAllTab[mCurrnetIndex]);
			}
			
			ft.show(mFraAllTab[mCurrnetIndex]).hide(mFraAllTab[temp]);
			
			ft.commit();
			
			if(1 == mCurrnetIndex)
			{
				Log.e("lijinjin", "call mFraTab2.setList()");
//				mFraTab2.setList();
			}
			
			mCurrentFragmentID = checkedId;

		}
	};

	public CharSequence getCurrentCity() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	

}
