package com.newbrain.chaxin.my.mycollect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.liren_app.ui.LiRenActivity;
import com.newbrain.adapter.Model;
import com.newbrain.adapter.MyBaseAdapter_CollectBeauty;
import com.newbrain.adapter.MyBaseAdapter_CollectGoods;
import com.newbrain.adapter.MyBaseAdapter_MyOrder;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.swipeRefresh.SwipyRefreshLayout;
import com.newbrain.swipeRefresh.SwipyRefreshLayoutDirection;
import com.newbrain.swipeRefresh.SwipyRefreshLayout.OnRefreshListener;
import com.newbrain.user.User;
import com.newbrain.xutils.Xutils_HttpUtils;

public class BeautyCollectFragment extends Fragment implements OnItemLongClickListener,OnItemClickListener{

	
	private Context context;
	private HttpUtils httpUtils;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.beauty_collect_fragment, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		
		ViewUtils.inject(this,view);
		context = getActivity();
		
		httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		listviewInit();
	}
		@ViewInject(R.id.beauty_listview)
		private ListView mLv;
		@ViewInject(R.id.swipyrefreshlayout)
		private SwipyRefreshLayout swrefresh;
		private MyBaseAdapter_CollectBeauty myAdapter;

		private List<HashMap<String, String>> mList;
		private int pos;
		private void listviewInit() {

			mList = new ArrayList<HashMap<String, String>>();
			swrefresh.setDirection(SwipyRefreshLayoutDirection.BOTH);
			mLv.setOnItemLongClickListener(this);
			mLv.setOnItemClickListener(this);

			swrefresh.setOnRefreshListener(new OnRefreshListener() {
				
				@Override
				public void onRefresh(SwipyRefreshLayoutDirection direction) {
					if(direction == SwipyRefreshLayoutDirection.TOP){
					pageNo = 1;
					}else pageNo ++;
					getData();
				}
			});
			myAdapter = new MyBaseAdapter_CollectBeauty(context, mList);
			/*SwipeMenuCreator creator = new SwipeMenuCreator() 
			{
				@Override
				public void create(SwipeMenu menu)
				{
					// TODO Auto-generated method stub
					
					SwipeMenuItem item = new SwipeMenuItem(context);
					
					item.setBackground(new ColorDrawable(Color.RED));
					item.setTitle(getString(R.string.delete));
					item.setWidth(dp2px(80));
					item.setTitleColor(Color.WHITE);
					item.setTitleSize(dp2px(8));
					
					menu.addMenuItem(item);
				}
			};

			mLv.setMenuCreator(creator);
			mLv.setOnMenuItemClickListener(new OnMenuItemClickListener()
			{
				@Override
				public void onMenuItemClick(int arg0, SwipeMenu arg1, int arg2) 
				{
					String ID = mList.get(arg0).get("id");
					removeShopCart(ID);
					pos = arg0;
				}
			});*/
			mLv.setAdapter(myAdapter);
			
			getData();

		}
	
		/**
		 * 删除
		 * @param iD
		 */
		protected void removeShopCart(String iD) {
			CustomProgressDialog.startProgressDialog(context, "正在删除...");
			String userid = User.getInstance().getId();
			String token = User.getInstance().getToken();
			
			String url =  Constant.METHOD_deleteCollection+"?userId="+userid+"&token="+token+"&id="+iD;
			httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					CustomProgressDialog.stopProgressDialog();
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					CustomProgressDialog.stopProgressDialog();
					String result = arg0.result;
//					{"message":"查询成功","result":[{"beautyProfessional":null,"paymentMode":"1","beautyCertNo":"","beautyPhoneNo":"18617167404","status":0,"beautyImage":"http://218.244.138.142:8081/TeaMall/upload/2015/11/13/86cc467e873b47d2bc8330e6999d7942.png","beautyHighOpinion":null,"ordersNo":"111316211417998","msg":"","beautyLevel":null,"beautyName":"上杭NB","id":"5a740a0e50ff74920150ffef26b2000b","amount":150.0,"beautyId":"5a740a0e50ff74920150ffba8e1d0001","bookTime":"13:00","bookDate":"2015-11-13","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-13 16:21:14","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"1","beautyCertNo":"","beautyPhoneNo":"18686568656","status":0,"beautyImage":null,"beautyHighOpinion":null,"ordersNo":"1113155256-8412","msg":"","beautyLevel":null,"beautyName":"在乎别人说","id":"5a740a0e50ff74920150ffd53be10009","amount":222.0,"beautyId":"5a740a0e50f1eed90150f63875b90039","bookTime":"16:00","bookDate":"2015-11-13","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-13 15:52:56","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"0","beautyCertNo":"48051778","beautyPhoneNo":"18711214442","status":0,"beautyImage":"http://218.244.138.142:8081/TeaMall/upload/2015/11/11/2a8b4897107b49bbbec073887691bce1.png","beautyHighOpinion":null,"ordersNo":"1113152816-2054","msg":"","beautyLevel":null,"beautyName":"南国丽园美容","id":"5a740a0e50ff74920150ffbea7bd0007","amount":120.0,"beautyId":"5a740a0e50ece5d00150ef96af460011","bookTime":"13:00","bookDate":"2015-11-13","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-13 15:28:16","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"0","beautyCertNo":"","beautyPhoneNo":"18686568656","status":0,"beautyImage":null,"beautyHighOpinion":null,"ordersNo":"1112120527-1000","msg":"123","beautyLevel":null,"beautyName":"在乎别人说","id":"5a740a0e50f9cd6c0150f9de9e7c0005","amount":222.0,"beautyId":"5a740a0e50f1eed90150f63875b90039","bookTime":"16:00","bookDate":"2015-11-15","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-12 12:05:27","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"0","beautyCertNo":"48051778","beautyPhoneNo":"18711214442","status":0,"beautyImage":"http://218.244.138.142:8081/TeaMall/upload/2015/11/11/2a8b4897107b49bbbec073887691bce1.png","beautyHighOpinion":null,"ordersNo":"1111194553-2123","msg":"123","beautyLevel":null,"beautyName":"南国丽园美容","id":"5a740a0e50f1eed90150f65dcd2c003c","amount":120.0,"beautyId":"5a740a0e50ece5d00150ef96af460011","bookTime":"12:00","bookDate":"2015-11-12","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-11 19:45:53","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"}],"code":"1"}
					try {
						JSONObject obj = new JSONObject(result);
						String code = obj.getString("code");
						String msg = obj.getString("message");
						if(code.equals("1")){
							mList.remove(pos);
							myAdapter.notifyDataSetChanged();
						}else {
							Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
						}
						}catch (Exception e) {
						}
				}
			});
			
		}

		private int dp2px(int dp)
		{
			return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
					getResources().getDisplayMetrics());
		}
		private int pageNo =1,pageSize = 20;
		private void getData() {

			CustomProgressDialog.startProgressDialog(context, "loading...");
			String userid = User.getInstance().getId();
			String token = User.getInstance().getToken();
			/*RequestParams params = new RequestParams();
			params.addBodyParameter("token", token);
			params.addBodyParameter("userId", userid);
			params.addBodyParameter("type", "0");
			params.addBodyParameter("pageNo", pageNo+"");
			params.addBodyParameter("pageSize", pageSize+"");*/
			String url =  Constant.METHOD_getCollections+"?userId="+userid+"&token="+token+"&type=1&pageNo="+pageNo+"&pageSize="+pageSize;
			httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					swrefresh.setRefreshing(false);
					CustomProgressDialog.stopProgressDialog();
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					swrefresh.setRefreshing(false);
					CustomProgressDialog.stopProgressDialog();
					String result = arg0.result;
//					{"message":"查询成功","result":[{"beautyProfessional":null,"paymentMode":"1","beautyCertNo":"","beautyPhoneNo":"18617167404","status":0,"beautyImage":"http://218.244.138.142:8081/TeaMall/upload/2015/11/13/86cc467e873b47d2bc8330e6999d7942.png","beautyHighOpinion":null,"ordersNo":"111316211417998","msg":"","beautyLevel":null,"beautyName":"上杭NB","id":"5a740a0e50ff74920150ffef26b2000b","amount":150.0,"beautyId":"5a740a0e50ff74920150ffba8e1d0001","bookTime":"13:00","bookDate":"2015-11-13","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-13 16:21:14","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"1","beautyCertNo":"","beautyPhoneNo":"18686568656","status":0,"beautyImage":null,"beautyHighOpinion":null,"ordersNo":"1113155256-8412","msg":"","beautyLevel":null,"beautyName":"在乎别人说","id":"5a740a0e50ff74920150ffd53be10009","amount":222.0,"beautyId":"5a740a0e50f1eed90150f63875b90039","bookTime":"16:00","bookDate":"2015-11-13","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-13 15:52:56","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"0","beautyCertNo":"48051778","beautyPhoneNo":"18711214442","status":0,"beautyImage":"http://218.244.138.142:8081/TeaMall/upload/2015/11/11/2a8b4897107b49bbbec073887691bce1.png","beautyHighOpinion":null,"ordersNo":"1113152816-2054","msg":"","beautyLevel":null,"beautyName":"南国丽园美容","id":"5a740a0e50ff74920150ffbea7bd0007","amount":120.0,"beautyId":"5a740a0e50ece5d00150ef96af460011","bookTime":"13:00","bookDate":"2015-11-13","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-13 15:28:16","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"0","beautyCertNo":"","beautyPhoneNo":"18686568656","status":0,"beautyImage":null,"beautyHighOpinion":null,"ordersNo":"1112120527-1000","msg":"123","beautyLevel":null,"beautyName":"在乎别人说","id":"5a740a0e50f9cd6c0150f9de9e7c0005","amount":222.0,"beautyId":"5a740a0e50f1eed90150f63875b90039","bookTime":"16:00","bookDate":"2015-11-15","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-12 12:05:27","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"},{"beautyProfessional":null,"paymentMode":"0","beautyCertNo":"48051778","beautyPhoneNo":"18711214442","status":0,"beautyImage":"http://218.244.138.142:8081/TeaMall/upload/2015/11/11/2a8b4897107b49bbbec073887691bce1.png","beautyHighOpinion":null,"ordersNo":"1111194553-2123","msg":"123","beautyLevel":null,"beautyName":"南国丽园美容","id":"5a740a0e50f1eed90150f65dcd2c003c","amount":120.0,"beautyId":"5a740a0e50ece5d00150ef96af460011","bookTime":"12:00","bookDate":"2015-11-12","userId":"5a740a0e50c8435a0150cccc6e26000b","beautyAddress":"66777888","gold":0,"createDate":"2015-11-11 19:45:53","expressId":"5a740a0e50ebd7ab0150ec50d56f0008"}],"code":"1"}
					try {
						JSONObject obj = new JSONObject(result);
						String code = obj.getString("code");
						if(code.equals("1")){
							if(pageNo == 1)mList.clear();
							mList .addAll(JSON.parseObject(obj.getJSONArray("result").toString(), new TypeReference<List<HashMap<String,String>>>(){}));
							
						}else swrefresh.setDirection(SwipyRefreshLayoutDirection.TOP);
						myAdapter.notifyDataSetChanged();
						}catch (Exception e) {
						}
				}
			});
			
		
			
		}
		private String[] items = new String[] { "要删除收藏吗？","  删除","  取消" };
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				final int arg2, long arg3) {
			new AlertDialog.Builder(getActivity(),R.style.DialogTheme)
			.setItems(items, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					if(arg1 == 1){
						String ID = mList.get(arg2).get("id");
						removeShopCart(ID);
						pos = arg2;
					}
					dialog.dismiss();
				}
			})
			.show();
			
			return true;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(context, LiRenActivity.class);
				intent.putExtra("data", mList.get(arg2));
				startActivity(intent);
			
		}
}
