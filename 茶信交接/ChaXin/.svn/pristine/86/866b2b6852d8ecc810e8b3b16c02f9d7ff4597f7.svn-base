package com.newbrain.adapter;



import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.chaxin.R;
import com.newbrain.chaxin.my.MySmallTeaLookActivity;
import com.newbrain.model.MySmallTeaOrderListBean.MySmallTeaOrder;
import com.newbrain.utils.ToastUtil;



public class MyBaseAdapter_MySmallTea  extends BaseAdapter{
	
	
	private Context context;
	private LayoutInflater inflater;
	private List<MySmallTeaOrder> list;
	
	public  MyBaseAdapter_MySmallTea(Context context,List<MySmallTeaOrder> mySmallTeaOrders)
	{
		this.context = context;
		this.list = mySmallTeaOrders;
		
		inflater = LayoutInflater.from(this.context);
		
	}
	public List<MySmallTeaOrder> getList() {
		return list;
	}




	public void setList(List<MySmallTeaOrder> list) {
		this.list = list;
		
		notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
		ViewHolder viewHolder;
		
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.my_small_tea_listview_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			
			
			convertView.setTag(viewHolder);			
			
		}else
		{
			
			
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		final MySmallTeaOrder mySmallTeaOrder = list.get(position);
		viewHolder.tv_id.setText(context.getString(R.string.tea_id)+mySmallTeaOrder.getId());
		viewHolder.tv_num.setText(context.getString(R.string.tea_num)+mySmallTeaOrder.getOrder_count());
		viewHolder.tv_shop_name.setText(mySmallTeaOrder.getManor_name());
//		viewHolder.tv_sort.setText(context.getString(R.string.tea_sort));
		viewHolder.tv_sort_name.setText(context.getString(R.string.tea_sort_name)+mySmallTeaOrder.getVariety_name());
		viewHolder.tv_price.setText(context.getString(R.string.tea_price)+mySmallTeaOrder.getPrice());
		viewHolder.tv_look.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(context,MySmallTeaLookActivity.class);
				intent.putExtra("desc",mySmallTeaOrder.getDesc());
				context.startActivity(intent);
			}
		});
		return convertView;
	}
	
	
	
	public class ViewHolder
	{
		
		@ViewInject(R.id.my_small_tea_shop_name)
		private TextView tv_shop_name;
		

//		@ViewInject(R.id.my_small_tea_sort)
//		private TextView tv_sort;
		

		@ViewInject(R.id.my_small_tea_num)
		private TextView tv_num;

		@ViewInject(R.id.my_small_tea_sort_name)
		private TextView tv_sort_name;
		

		@ViewInject(R.id.my_small_tea_grow_state)
		private TextView tv_grow_state;
		

		@ViewInject(R.id.my_small_tea_id)
		private TextView tv_id;
		
		@ViewInject(R.id.my_small_tea_price)
		private TextView tv_price;
		
		@ViewInject(R.id.my_small_tea_look)
		private TextView tv_look;
		
	}

}
