package com.newbrain.adapter;



import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.chaxin.R;
import com.squareup.picasso.Picasso;



public class MyBaseAdapter_CollectBeauty  extends BaseAdapter{
	
	
	private Context context;
	private LayoutInflater inflater;
	private List<HashMap<String, String>> list;
	private final static int[] dengji_resources = new int[] {
		
		R.drawable.yy_baiyin, R.drawable.yy_gold, R.drawable.yy_diamonds,R.drawable.yy_emerald };
	
	public  MyBaseAdapter_CollectBeauty(Context context,List<HashMap<String, String>> list)
	{
		this.context = context;
		this.list = list;
		
		inflater = LayoutInflater.from(this.context);
		
	}
	
	
	public List<HashMap<String, String>> getList() {
		return list;
	}




	public void setList(List<HashMap<String, String>> list) {
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
			convertView = inflater.inflate(R.layout.beauty_collect_fragment_listview_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			
			
			convertView.setTag(viewHolder);			
			
		}else
		{
			
			
			viewHolder = (ViewHolder) convertView.getTag();
		}
		//{"message":"数据不为空","result":[{"id":"5a740a0e50e169480150e6a6b1c30004","beautyId":
//		"5a740a0e50a2f08f0150a3109183000e","price":0.0,"memo":"管理局","levels":0,"beautyNo":"303645",
//		"sales":0,"no":"","name":"丽丽姑娘","userId":"5a740a0e4f608aa9014f6373f7660002","image":"","type":1}
//	,{"id":"5a740a0e50f1eed90150f65cbce3003a","beautyId":"5a740a0e50ece5d00150ef96af460011","price":120.0,"memo":"优质泡茶服务","levels":0,"beautyNo":"333943","sales":1,"no":"","name":"南国丽园美容","userId":"5a740a0e50ece5d00150ef91112d000b","image":"http://218.244.138.142:8081/TeaMall/upload/2015/11/11/2a8b4897107b49bbbec073887691bce1.png","type":1},{"id":"5a740a0e50f1eed90150f65cc51c003b","beautyId":"5a740a0e50ece5d00150ef96af460011","price":120.0,"memo":"优质泡茶服务","levels":0,"beautyNo":"333943","sales":1,"no":"","name":"南国丽园美容","userId":"5a740a0e50ece5d00150ef91112d000b","image":"http://218.244.138.142:8081/TeaMall/upload/2015/11/11/2a8b4897107b49bbbec073887691bce1.png","type":1},{"id":"5a740a0e50f72abc0150f777efb9000a","beautyId":"5a740a0e50a1d1cf0150a1d9c4ed0001","price":120.0,"memo":"1111112","levels":0,"beautyNo":"555210","sales":0,"no":"","name":"sdasdasdasd","userId":"402880ed4ef78c65014ef792c30b0002","image":"http://218.244.138.142:8081/TeaMall/upload/2015/11/05/b7eb37d996754b309bb746e50a1cf55a.png","type":1},{"id":"5a740a0e50f9cd6c0150fb998d85001a","beautyId":"5a740a0e50a1d1cf0150a1d9c4ed0001","price":120.0,"memo":"1111112","levels":0,"beautyNo":"555210","sales":0,"no":"","name":"sdasdasdasd","userId":"402880ed4ef78c65014ef792c30b0002","image":"http://218.244.138.142:8081/TeaMall/upload/2015/11/05/b7eb37d996754b309bb746e50a1cf55a.png","type":1},{"id":"5a740a0e50ff74920150ff7733180000","beautyId":"5a740a0e50f1eed90150f63875b90039","price":222.0,"memo":"在乎","levels":0,"beautyNo":"864069","sales":2,"no":"","name":"在乎别人说","userId":"5a740a0e50c8435a0150cccc6e26000b","image":"","type":1}],"code":"1"}
//		我的预约 {paymentMode=1, status=0, ordersNo=1113155256-8412, guestNickName=特殊, msg=, guestImage=, id=5a740a0e50ff74920150ffd53be10009, amount=222, guestSex=1, bookTime=16:00, bookDate=2015-11-13, guestPhoneNo=18682419618, userId=5a740a0e50c8435a0150cccc6e26000b, beautyAddress=66777888, gold=0, createDate=2015-11-13 15:52:56, expressId=5a740a0e50ebd7ab0150ec50d56f0008}
		HashMap<String,String>map = list.get(position);
		
		String num = map.get("sales");
		String price = map.get("price");
		String levels = map.get("levels");
		String name = map.get("name");
		String image = map.get("image");
		
		viewHolder.tv_num.setText(name);
		viewHolder.tv_price.setText("￥"+price);
		viewHolder.tv_volume.setText("成交："+num);
		if(!TextUtils.isEmpty(image)){
			Picasso.with(context).load(image).centerCrop().resize(60, 60).into(viewHolder.iv_imageview);
		}
		if(TextUtils.isEmpty(levels))levels = "0";
		viewHolder.iv_level.setBackgroundResource(dengji_resources[Integer.parseInt(levels)]);
		
		return convertView;
	}
	
	
	
	public class ViewHolder
	{
		
		@ViewInject(R.id.beauty_collect_num)
		private TextView tv_num;
		
		@ViewInject(R.id.beauty_collect_price)
		private TextView tv_price;
		
		@ViewInject(R.id.beauty_collect_volume)
		private TextView tv_volume;
		
		@ViewInject(R.id.beauty_collect_level)
		private ImageView iv_level;
		
		
		@ViewInject(R.id.beauty_collect_imageview)
		private ImageView iv_imageview;
		
		
		
	}

}
