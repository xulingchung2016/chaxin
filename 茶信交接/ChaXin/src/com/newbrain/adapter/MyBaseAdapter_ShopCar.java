package com.newbrain.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.chaxin.R;
import com.newbrain.jsonthread.Constant;
import com.newbrain.model.ShopCartGoodsListBean;
import com.newbrain.model.ShopCartGoodsListBean.ShopCartGoodDetail;
import com.newbrain.utils.ToastUtil;
import com.newbrain.xutils.Xutils_BitmapUtils;

public class MyBaseAdapter_ShopCar extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<ShopCartGoodDetail> list;
	private int oldposition = 0;

	public MyBaseAdapter_ShopCar(Context context, List<ShopCartGoodDetail> list) {
		this.context = context;
		this.list = list;

		inflater = LayoutInflater.from(this.context);

	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		if(null != list)
		{
			return list.size();
		}
		else
		{
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		// TODO Auto-generated method stub
		ViewHolder viewHolder;

		if (convertView == null) 
		{
			convertView = inflater.inflate(R.layout.shop_car_listview_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		MyOnClickListener myClicklistener = new MyOnClickListener(position, viewHolder.tvGoodsNum);
		viewHolder.btnGoodsNumReduce.setOnClickListener(myClicklistener);
		viewHolder.btnGoodsNumPlus.setOnClickListener(myClicklistener);
		ShopCartGoodDetail detail = list.get(position);
				
		viewHolder.tvShopName.setText(detail.getStoreName());
		viewHolder.tvGoodsName.setText(detail.getGoodsName());
		viewHolder.tvGoodsPrice.setText(detail.getPrice());
		viewHolder.tvGoodsNum.setText(detail.getNum());
		String image = detail.getGoodsImage();
		if(!TextUtils.isEmpty(image))
		{
			Xutils_BitmapUtils.getbitmapUtils_detail(context).display(viewHolder.goods_img, Constant.TEAMALL_IMAGE_BASE_URL + image);
		}
		if(position > 0)
		{
			
			if((position > oldposition)&&(detail.getStoreName()).equals((list.get(oldposition).getStoreName())))
			{				
				viewHolder.llShopName.setVisibility(View.GONE);
			}
			
			oldposition = position;
		}
		
		if(position == 0)
		{
			viewHolder.llShopName.setVisibility(View.VISIBLE);
		}

		return convertView;
	}

	public class MyOnClickListener implements OnClickListener 
	{
		private int position;
		private TextView tvNum;
		public MyOnClickListener(int position, TextView tvNum) 
		{
			super();
			this.position = position;
			this.tvNum = tvNum;
		}

		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			switch (v.getId()) 
			{
			case R.id.shop_car_reduce:			
				String str = tvNum.getText().toString();			
				int num = Integer.parseInt(str);				
				if(num == 1)
				{					
					ToastUtil.Toast(context, context.getResources().getString(R.string.buy_num_not_less_than_one));				
				}
				else
				{	
					tvNum.setText((--num)+"");			
				}
		
				break;
			case R.id.shop_car_plus:			
				String temp = tvNum.getText().toString();			
				int numPlus = Integer.parseInt(temp);			
				tvNum.setText((++numPlus)+"");
	
				break;

			default:
				break;
			}
		}
	}

	public class ViewHolder 
	{	
		/**店铺名称   第二个与第一相同需隐藏*/
		@ViewInject(R.id.shop_car_linear_shop_name)
		private LinearLayout llShopName;

		@ViewInject(R.id.shop_car_item_check_all)
		private CheckBox checkBoxShop;

		@ViewInject(R.id.shop_car_item_shop_name)
		private TextView tvShopName;

		@ViewInject(R.id.shop_car_item_check)
		private CheckBox checkBoxGoods;
		
		@ViewInject(R.id.shop_car_item_img)
		private ImageView goods_img;

		@ViewInject(R.id.shop_car_item_tea_name)
		private TextView tvGoodsName;

		@ViewInject(R.id.shop_car_item_tea_price)
		private TextView tvGoodsPrice;

		@ViewInject(R.id.shop_car_reduce)
		private Button btnGoodsNumReduce;

		@ViewInject(R.id.shop_car_num)
		private TextView tvGoodsNum;

		@ViewInject(R.id.shop_car_plus)
		private Button btnGoodsNumPlus;

	}

}
