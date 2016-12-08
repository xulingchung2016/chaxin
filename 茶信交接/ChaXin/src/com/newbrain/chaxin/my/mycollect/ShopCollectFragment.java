package com.newbrain.chaxin.my.mycollect;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.adapter.Model;
import com.newbrain.adapter.MyBaseAdapter_CollectShop;
import com.newbrain.adapter.MyBaseAdapter_MyOrder;
import com.newbrain.adapter.MyBaseAdapter_ShopCar;
import com.newbrain.chaxin.R;

public class ShopCollectFragment extends Fragment{

	
	private Context context;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.shop_collect_fragment, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		ViewUtils.inject(this,view);
		context = getActivity();
	
		
		 listviewInit();
	}
	
	
	
	@ViewInject(R.id.listview_shop_collect)
	private ListView mLv;
	
	

	private MyBaseAdapter_CollectShop myAdapter;

	private List<Model> mList;

	private void listviewInit() {

		mList = new ArrayList<Model>();

		for (int i = 0; i < 5; i++) {

			mList.add(new Model());
		}

		myAdapter = new MyBaseAdapter_CollectShop(context, mList);

		mLv.setAdapter(myAdapter);

	}
	
	
	

}
