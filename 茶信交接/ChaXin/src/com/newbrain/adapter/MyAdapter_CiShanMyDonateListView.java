package com.newbrain.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.chaxin.R;
import com.newbrain.customview.DonateOrCrowdfundingDialog;
import com.newbrain.customview.DonateOrCrowdfundingDialog.OnDonateOrCrowdfundingDialogListener;
import com.newbrain.model.TeaCiShanMyDonateBean.CiShanMyDonateIntro;
import com.newbrain.xutils.Xutils_BitmapUtils;

public class MyAdapter_CiShanMyDonateListView extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<CiShanMyDonateIntro> list;
	private OnDonateOrCrowdfundingDialogListener mOnDialogListener;
	private BitmapUtils bitmapUtils;

	public MyAdapter_CiShanMyDonateListView(Context context,
			List<CiShanMyDonateIntro> list,
			OnDonateOrCrowdfundingDialogListener onDialogListener) {
		this.context = context;
		this.list = list;

		inflater = LayoutInflater.from(this.context);
		bitmapUtils = Xutils_BitmapUtils.getbitmapUtils_detail(context);
		mOnDialogListener = onDialogListener;
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

		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.tea_cishan_my_donate_list_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final CiShanMyDonateIntro ciShanMyDonateIntro = list.get(position);
		bitmapUtils.display(viewHolder.icon, ciShanMyDonateIntro.getImg());
		viewHolder.tv_title.setText(ciShanMyDonateIntro.getTitle());
		viewHolder.tv_donate_money_nu.setText(String.format(
				context.getString(R.string.cishan_donate_gold),
				ciShanMyDonateIntro.getGold()));
		viewHolder.tv_love_nu.setText(String.format(
				context.getString(R.string.cishan_donate_love),
				ciShanMyDonateIntro.getLove_count()));
		if (ciShanMyDonateIntro.getStatus().equals("0")) {
			viewHolder.bt_again_donate.setText("已结束");
			viewHolder.bt_again_donate.setTextColor(0xff000000);
			viewHolder.bt_again_donate.setClickable(false);

			viewHolder.bt_again_donate.setBackgroundColor(0x00000000);
		} else {
			viewHolder.bt_again_donate
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							DonateOrCrowdfundingDialog donateOrCrowdfundingDialog = new DonateOrCrowdfundingDialog(
									context);
							donateOrCrowdfundingDialog
									.setOnDonateOrCrowdfundingDialogListener(mOnDialogListener);
							donateOrCrowdfundingDialog
									.setProjectId(ciShanMyDonateIntro
											.getProject_id());
							donateOrCrowdfundingDialog.show();
						}
					});
		}
		return convertView;
	}

	public class ViewHolder {

		@ViewInject(R.id.icon)
		ImageView icon;

		@ViewInject(R.id.title)
		TextView tv_title;

		@ViewInject(R.id.donate_money_nu)
		TextView tv_donate_money_nu;

		@ViewInject(R.id.love_nu)
		TextView tv_love_nu;

		@ViewInject(R.id.again_donate)
		Button bt_again_donate;

	}

}
