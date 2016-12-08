package com.liren_app.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.alibaba.fastjson.JSONObject;
import com.liren_app.http.GetImgByUrl;
import com.liren_app.http.GetUrl;
import com.liren_app.http.IImageListener;
import com.newbrain.chaxin.R;
import com.newbrain.jsonthread.Constant;
import com.newbrain.xutils.Xutils_BitmapUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LiRenDataAdapte extends BaseAdapter {

	private final static int[] dengji_resources = new int[] {
			
			R.drawable.yy_baiyin, R.drawable.yy_gold, R.drawable.yy_diamonds,R.drawable.yy_emerald };

	public List<HashMap<String,String>>  datas = new 
			ArrayList<HashMap<String,String>>();
	private Context mContext;

	public void ReSetData(List<HashMap<String,String>> liren_list) {
		
		datas.addAll(liren_list);
		this.notifyDataSetChanged();
		
	}

	public LiRenDataAdapte(Context _Context, List<HashMap<String,String>> _LirenList) {
		mContext = _Context;
		this.datas = _LirenList;
	}
	public void clearData(){
		if(datas != null)datas.clear();
	}

	@Override
	public int getCount() {
			return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflater.inflate(R.layout.liren_item, null);

			TextView liren_paihao = (TextView) v
					.findViewById(R.id.txt_lirenpaihao);
			TextView txt_price = (TextView) v.findViewById(R.id.txt_price);
			TextView txt_xiaoliang = (TextView) v
					.findViewById(R.id.txt_xiaoliang);

			ImageView img_dengji = (ImageView) v.findViewById(R.id.img_dengji);
			ImageView liren_logo = (ImageView) v.findViewById(R.id.img_logo);

			try {
//				{"sex":"1","memo":"1111112","beautyNo":"555210","no":"","image":"","certImage":"","backImage":"","phoneNo":"11111","id":"5a740a0e50a1d1cf0150a1d9c4ed0001","area":"","price":"120.0","levels":"","sales":"","name":"你们","userId":"402880ed4ef78c65014ef792c30b0002","age":"","certNo":"12345678","storeId":"","frontImage":""}
//				org.json.JSONObject obj = (org.json.JSONObject)mLirenList.getJSONObject(position);
				HashMap<String,String> obj = datas.get(position);
				liren_paihao.setText(
						 obj.get("name"));
				String sales = obj.get("sales");
				if(TextUtils.isEmpty(sales))sales="0";
				txt_price
						.setText("￥"
								+ obj.get(
										"price"));
				txt_xiaoliang.setText("成交"
						+ sales
						+ "次");
				String levels = obj.get(
						"levels");
				img_dengji.setBackgroundResource(dengji_resources[Integer
						.parseInt(TextUtils.isEmpty(levels)?"0":levels)]);

					String img_url = obj.get("image");
					if(!TextUtils.isEmpty(img_url)){
						Xutils_BitmapUtils.getbitmapUtils_detail(mContext).display(liren_logo, img_url);
					}
			} catch (Exception e) {

				e.printStackTrace();
			}

			return v;
	}
	


}
