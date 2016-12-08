package com.liren_app.data;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.liren_app.http.GetImgByUrl;
import com.liren_app.http.GetUrl;
import com.liren_app.http.IImageListener;
import com.newbrain.chaxin.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class JiluDataAdapte extends BaseAdapter implements IImageListener {

	private JSONArray mJiLuData = null;
	private Context mContext;
	private Map<Integer, Bitmap> img_map;

	public JiluDataAdapte(Context _Context, JSONArray _LirenList) {
		mContext = _Context;
		mJiLuData = _LirenList;
	}

	public void reSetData(JSONArray _LirenList) {
		img_map = new HashMap<Integer, Bitmap>();
		mJiLuData = _LirenList;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mJiLuData.length();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.jilu_item, null);
		TextView txt_name = (TextView) v.findViewById(R.id.txt_name);
		TextView txt_chengjiao = (TextView) v.findViewById(R.id.txt_chengjiao);
		TextView txt_duihuan = (TextView) v.findViewById(R.id.txt_duihuan);

		try {
			txt_name.setText(mJiLuData.getJSONObject(position).getString(
					"goodsName"));
			txt_chengjiao.setText(mJiLuData.getJSONObject(position).getString(
					"silver"));
			txt_duihuan.setText(mJiLuData.getJSONObject(position).getString(
					"exchageNum")+"»À“—∂“ªª");
		} catch (JSONException e2) {

			e2.printStackTrace();
		}

		ImageView img_logo = (ImageView) v.findViewById(R.id.img_logo);
		if (1 == 0) {
			try {
				String img_url = GetUrl.getImgUrl(mJiLuData.getJSONObject(
						position).getString("image"));
				Bitmap _img = null;
				try {
					_img = img_map.get(position);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (_img == null) {
					GetImgByUrl.getLiRenImgs(this, position, img_url);
				} else {
					img_logo.setImageBitmap(_img);

				}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return v;
	}

	@Override
	public void onGetImg(int key, Bitmap img) {
		// TODO Auto-generated method stub

	}

}
