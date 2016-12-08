package com.newbrain.chaxin.teasearchtea;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.newbrain.chaxin.R;
import com.newbrain.jsonthread.Constant;

public class TeaSearchTeaSearchFrament extends Fragment{
	
	private TeaSearchTeaActivity mParentsActivity;
	private ListView listview;
	private SharedPreferences sp;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mParentsActivity = (TeaSearchTeaActivity)getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tea_searchtea_search_frament, null);
		mParentsActivity.setTopBar(mParentsActivity.SEARCHTEA_SEARCH_PAGE);
		initView(view);
//		setListener();
//		initData();
		return view;
	}
	private void initView(View view) {
		listview=(ListView) view.findViewById(R.id.search_listview);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Message message = mParentsActivity.mHandler.obtainMessage();
				message.what=Constant.SEARCH_BACK;
				message.arg1=position;
				mParentsActivity.mHandler.sendMessage(message);
				mParentsActivity.removeFragerment(true);
			}
		});
	}
	
	public void setData(String[] data){
		listview.setAdapter(new ArrayAdapter(mParentsActivity, android.R.layout.simple_list_item_1, data));
	}
}