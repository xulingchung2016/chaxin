package com.newbrain.chaxin.teasearchtea;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.TeaHouseDetailInfoBean;
import com.newbrain.model.TeaHouseDetailInfoBean.TeaHouseDetailInfo;
/**
 * 茶馆详情页
 * @author ZY
 *
 */
@SuppressLint("ValidFragment")
public class TeaSearchTeaDetailMainFragment extends Fragment {

	private TeaSearchTeaActivity mParentsActivity;
	private String mTeaHouseId;
	
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private RadioGroup mRadioGroup;
	private RadioButton mTea_Bueaty_rbt;
	private RadioButton mTea_detail_rbt;
	private TeaSearchTeaBeautyFragment mTeaBeautyFragment;
	private TeaSearchTeaDetailFragment mTeaDetailFragment;
	
	/**茶丽人列表*/
	private final int PAGE_TYPE_BUEATY = 0;
	/**茶馆详情*/
	private final int PAGE_TYPE_DETAIL = 1;
	private int current_type = -1;
	private TeaHouseDetailInfo mTeaHouseDetailInfo;
	private JsonThread mThread;
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case Constant.FLAG_GET_TEAHOUSE_DETAIL:
				CustomProgressDialog.stopProgressDialog();
				HttpReturnData mData = (HttpReturnData) msg.obj;
				if(mData.isSuccess()){
					TeaHouseDetailInfoBean teaHouseDetailInfoBean = (TeaHouseDetailInfoBean) mData.getObg();
					if(teaHouseDetailInfoBean.getCode().equals("1")){
						mTeaHouseDetailInfo = teaHouseDetailInfoBean.getResult();
						mTea_Bueaty_rbt.setChecked(true);
					}
				}
				break;
			}
		}
	};
	
	public TeaSearchTeaDetailMainFragment(String teaHouseId){
		mTeaHouseId = teaHouseId;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mParentsActivity = (TeaSearchTeaActivity)getActivity();
		fragmentManager = getChildFragmentManager();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		mParentsActivity.setTopBar(mParentsActivity.SEARCHTEA_DETAIL_PAGE);
		View view = inflater.inflate(R.layout.tea_search_teadetailmain_fragment,container, false);
		initView(view);
		setListener();
		initData();
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		mRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);
		mTea_Bueaty_rbt = (RadioButton) view.findViewById(R.id.tea_beauty);
		mTea_detail_rbt = (RadioButton) view.findViewById(R.id.tea_detail);
	}

	private void setListener() {
		// TODO Auto-generated method stub
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int id) {
				// TODO Auto-generated method stub
				switch(id){
				case R.id.tea_beauty:
					if(mTeaBeautyFragment == null){
						mTeaBeautyFragment = new TeaSearchTeaBeautyFragment(mTeaHouseId);
					}
					if(current_type != PAGE_TYPE_BUEATY){
						if(mTeaDetailFragment == null){
							replaceFragment(mTeaBeautyFragment, false, false, false);
						}else{
							replaceFragment(mTeaBeautyFragment, true, true, false);
						}
					}
					current_type = PAGE_TYPE_BUEATY;
					break;
				case R.id.tea_detail:
					
					if(mTeaDetailFragment == null){
						mTeaDetailFragment = new TeaSearchTeaDetailFragment(mTeaHouseDetailInfo);
					}
					if(current_type != PAGE_TYPE_DETAIL){
						if(mTeaBeautyFragment == null){
							replaceFragment(mTeaDetailFragment, false, false, false);
						}else{
							replaceFragment(mTeaDetailFragment, true, false, false);
						}
					}
					current_type = PAGE_TYPE_DETAIL;
					break;
				}
			}
		});
	}

	private void initData() {
		// TODO Auto-generated method stub
		List<Bean> list = new ArrayList<Bean>();
		list.add(new Bean("id", mTeaHouseId));
		CustomProgressDialog.startProgressDialog(mParentsActivity);

		if(mThread != null){
			mThread.cancleReturnData();
		}
		mThread = new JsonThread(mParentsActivity, list, mHandler , Constant.FLAG_GET_TEAHOUSE_DETAIL);
		mThread.start();
		
	}

	/**
	 * 更换Fragment
	 * 
	 * @param fragment
	 * @param isAnim
	 *            是否开启动画
	 * @param isLeftIn
	 *            是否左边进入
	 * @param isBack
	 *            是否加入回退栈
	 */
	public void replaceFragment(Fragment fragment, boolean isAnim,
			boolean isLeftIn, boolean isBack) {
		fragmentTransaction = fragmentManager.beginTransaction();
		if (isAnim) {
			if (isLeftIn) {
				fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,
						R.anim.slide_out_right, R.anim.slide_in_right,
						R.anim.slide_out_left);
			} else {
				fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,
						R.anim.slide_out_left, R.anim.slide_in_left,
						R.anim.slide_out_right);
			}
		}
		fragmentTransaction.replace(R.id.tea_search_fragment, fragment);
		if (isBack) {
			fragmentTransaction.addToBackStack(null);
		}
		fragmentTransaction.commit();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mThread != null){
			mThread.cancleReturnData();
			mThread = null;
		}
	}
	
}
