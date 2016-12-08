package com.newbrain.adapter;



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
import com.newbrain.model.CommentsBean.Comments;



public class MyBaseAdapter_CommentGrid  extends BaseAdapter{
	
	
	private Context context;
	private LayoutInflater inflater;
	private List<Comments> list;
	
	public  MyBaseAdapter_CommentGrid(Context context,List<Comments> list)
	{
		this.context = context;
		this.list = list;
		
		inflater = LayoutInflater.from(this.context);
		
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
			convertView = inflater.inflate(R.layout.item_comment_layout, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			convertView.setTag(viewHolder);			
			
		}else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Comments comment = list.get(position);
		if(comment !=null){
			String status = comment.getHighOpinion();
			String nickName = comment.getNickName();
//			String phone = comment.getPhoneNo();
			if(status.equals("0"))viewHolder.iv_status.setBackgroundResource(R.drawable.comment_good);
			else if(status.equals("1"))viewHolder.iv_status.setBackgroundResource(R.drawable.comment_middle);
			else viewHolder.iv_status.setBackgroundResource(R.drawable.comment_bad);
			
			if(!TextUtils.isEmpty(nickName))viewHolder.tv_name.setText(nickName);
//			if(!TextUtils.isEmpty(phone))viewHolder.tv_time.setText("联系电话："+phone);
			if(!TextUtils.isEmpty(comment.getContent()))viewHolder.tv_content.setText(comment.getContent());
			
		}
		
		
		
		return convertView;
	}
	
	
	
	public class ViewHolder
	{
		
		@ViewInject(R.id.tv_name)
		TextView tv_name;
		@ViewInject(R.id.tv_content)
		TextView tv_content;
		@ViewInject(R.id.tv_time)
		TextView tv_time;
		@ViewInject(R.id.iv_status)
		ImageView iv_status;
		
		
	}

}
