package com.newbrain.chaxin.teamall;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.adapter.MyBaseAdapter_CommentGrid;
import com.newbrain.application.MyApplication;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.chaxin.R;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.Constant;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.CommentsBean;
import com.newbrain.model.CommentsBean.Comments;
import com.newbrain.model.HttpReturnData;
import com.newbrain.swipeRefresh.SwipyRefreshLayout;
import com.newbrain.user.User;
import com.newbrain.utils.SharedPreferencesHelp;
import com.newbrain.viewflow.PullToRefreshListView;

@SuppressLint("HandlerLeak")
public class CommentActivity extends BaseActivity {

	private Context context;
//	@ViewInject(R.id.comment_pull_refresh_list)
	private ListView listview;
	@ViewInject(R.id.tea_mall_radiogroup_choice)
	private RadioGroup rb_select;
	private MyBaseAdapter_CommentGrid myAdapter;
	@ViewInject(R.id.all_actionbar_linear_left)
	private LinearLayout actionbar_ll_left;
	@ViewInject(R.id.comment_all_comment)
	private RadioButton rb_all;
	@ViewInject(R.id.teamall_sales_volume)
	private RadioButton rb_hp;
	@ViewInject(R.id.comment_middle_comment)
	private RadioButton rb_zp;
	@ViewInject(R.id.comment_bad_comment)
	private RadioButton rb_cp;
	@ViewInject(R.id.all_actionbar_textview_back_name)
	private TextView actionbar_tv_back_name_left;
	@ViewInject(R.id.all_actionbar_name)
	private TextView actionbar_tv_name_center;
	@ViewInject(R.id.all_actionbar_button_right_left)
	private Button actionbar_btn_right_left;
	@ViewInject(R.id.all_actionbar_button_right)
	private ImageButton actionbar_imgbtn_right;
	@ViewInject(R.id.swipyrefreshlayout)
	private SwipyRefreshLayout swrefresh;
	private JsonThread mThread;
	private  List<Comments> mCommentsList = new ArrayList<CommentsBean.Comments>();
	private  List<Comments> temps_hp = new ArrayList<CommentsBean.Comments>();//好评
	private  List<Comments> temps_zp = new ArrayList<CommentsBean.Comments>();//中评
	private  List<Comments> temps_cp = new ArrayList<CommentsBean.Comments>();//差评
	List<Bean> requestCommentsCode = new ArrayList<Bean>();
	private int num;
	private int type =0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_activity);
		MyApplication.getInstance().addActivity(this);
		ViewUtils.inject(this);
		context = this;
		listview = (ListView) findViewById(R.id.comment_pull_refresh_list);
		type = getIntent().getIntExtra("type", 0);//0wei商品，1位丽人
		actionbarInit();
		initData();
		rb_select.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkid) {
				switch (checkid) {
				case R.id.comment_all_comment://全部
					myAdapter = new MyBaseAdapter_CommentGrid(context, mCommentsList);
					num = mCommentsList.size();
					break;
				case R.id.teamall_sales_volume://好评
					num = temps_hp.size();
					myAdapter = new MyBaseAdapter_CommentGrid(context, temps_hp);
					
					break;
				case R.id.comment_middle_comment://中评
					num = temps_zp.size();
					myAdapter = new MyBaseAdapter_CommentGrid(context, temps_zp);
					
					break;
				case R.id.comment_bad_comment://差评
					num = temps_cp.size();
					myAdapter = new MyBaseAdapter_CommentGrid(context, temps_cp);
					break;
				default:
					num = mCommentsList.size();
					myAdapter = new MyBaseAdapter_CommentGrid(context, mCommentsList);
					break;
				}
//				if(num>0)
				listview.setAdapter(myAdapter);
				
			}
		});

	}
//	evaluation/evaluationList

	public void getCommentsList(List <Bean> list)
	{
		if(mThread != null)
		{
			mThread.cancleReturnData();
		}
		CustomProgressDialog.startProgressDialog(context, "loading...");
		mThread = new JsonThread(context, list, mHandler, Constant.FLAG_GET_GET_SHOPS_COMMENT_LIST);
		mThread.start();
		
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			CustomProgressDialog.stopProgressDialog();
			if(msg.what ==Constant.FLAG_GET_GET_SHOPS_COMMENT_LIST ){
			HttpReturnData receiveData = (HttpReturnData) msg.obj;
			if (receiveData.isSuccess()) 
			{
				CommentsBean  baseJsonDataBean = (CommentsBean)receiveData.getObg();
				if (baseJsonDataBean.getCode().equals("1"))
				{
					mCommentsList = baseJsonDataBean.getResult();
//					mCommentsList.add(new Comments("1","2","3","4","5","6","7"));
					int iSize = mCommentsList.size();
					if(iSize>0){
						myAdapter = new MyBaseAdapter_CommentGrid(context, mCommentsList);
						listview.setAdapter(myAdapter);
					for(int iLoop = 0; iLoop < iSize; iLoop++)
					{
						Comments comments = mCommentsList.get(iLoop);
						String highOpinion = comments.getHighOpinion();//好评 0，中评，1，差评 2
						if(highOpinion.equals("0"))temps_hp.add(comments);
						else if(highOpinion.equals("1"))temps_zp.add(comments);
						else temps_cp.add(comments);
					}
					}
					int sizeall = mCommentsList.size();
					int sizehp = temps_hp.size();
					int sizeazp = temps_zp.size();
					int sizeacp = temps_cp.size();
					
					rb_all.setText("全部\n("+sizeall+")");
					rb_hp.setText("好评\n("+sizehp+")");
					rb_zp.setText("中评\n("+sizeazp+")");
					rb_cp.setText("差评\n("+sizeacp+")");
					
				}else{
					showShortToast(baseJsonDataBean.getMessage());
				}
			}
			}
		};
	};
	
	/**
	 * 获取评论列表数据
	 */
	private void initData() {
		
		SharedPreferences sp = context.getSharedPreferences("CHAXIN_CONFIG", context.MODE_PRIVATE);
		String gooddetailID = sp.getString("goodid_for_detail", "");
		String lirenid = SharedPreferencesHelp.getString(context, "lirenID");
		if(type == 1){
			
			requestCommentsCode.add(new Bean("beautyId",lirenid));
			requestCommentsCode.add(new Bean("goodsId",""));
		}
		else{
			requestCommentsCode.add(new Bean("goodsId",gooddetailID));
			requestCommentsCode.add(new Bean("beautyId",""));
		}
//		requestCommentsCode.add(new Bean("userId",User.getInstance().getId()));
//		requestCommentsCode.add(new Bean("token",User.getInstance().getToken()));
		requestCommentsCode.add(new Bean("highOpinion",""));
		requestCommentsCode.add(new Bean("type",type+""));
		requestCommentsCode.add(new Bean("pageNo","1"));
		requestCommentsCode.add(new Bean("pageSize","1000"));
		String url = Constant.METHOD_USER_GOOD_COMMENT_URL+"?userId="+User.getInstance().getId()+"&token="
		+User.getInstance().getToken()+"&type="+type+"&beautyId="+lirenid+"&goodsId="+gooddetailID;
		getCommentsList(requestCommentsCode);
		
	}

//	@ViewInject(R.id.comment_gridview)
//	private GridView mGdComment;
	

	private void actionbarInit() {
		
		actionbar_ll_left.setVisibility(View.VISIBLE);
		actionbar_tv_back_name_left.setVisibility(View.GONE);
		// actionbar_tv_name_center.setVisibility(View.VISIBLE);
		actionbar_btn_right_left.setVisibility(View.GONE);
		actionbar_imgbtn_right.setVisibility(View.GONE);

		actionbar_tv_back_name_left.setText("");
		actionbar_tv_name_center.setText(getString(R.string.set_pl));
		actionbar_btn_right_left.setText(getString(R.string.rule));

		actionbar_ll_left.setOnClickListener(clickListener_actionbar);
		actionbar_imgbtn_right.setOnClickListener(clickListener_actionbar);

	}

	private OnClickListener clickListener_actionbar = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.all_actionbar_linear_left:
				finish();
				break;
			case R.id.all_actionbar_button_right:
				
				break;

			default:
				break;
			}

		}
	};

}
