package com.newbrain.jsonthread;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.newbrain.model.AddAddressListBean;
import com.newbrain.model.BaseJsonDataBean;
import com.newbrain.model.Bean;
import com.newbrain.model.CommentsBean;
import com.newbrain.model.CrowdftotalBean;
import com.newbrain.model.FindTeaHouseIntroBean;
import com.newbrain.model.GetAddressListBean;
import com.newbrain.model.GoodsListBean;
import com.newbrain.model.HttpReturnData;
import com.newbrain.model.ManoDetailIntroBean;
import com.newbrain.model.ManoSearchIntroBean;
import com.newbrain.model.ManoTeaTypeIntroBean;
import com.newbrain.model.ManoVarietyIntroBean;
import com.newbrain.model.MyCrowdfBean;
import com.newbrain.model.MyIncomeBean;
import com.newbrain.model.MyOrderListBean;
import com.newbrain.model.MySmallTeaOrderListBean;
import com.newbrain.model.ShopCartGoodsListBean;
import com.newbrain.model.ShopsListBean;
import com.newbrain.model.SummitOrderListBean;
import com.newbrain.model.TeaCiShanListProjectBean;
import com.newbrain.model.TeaCiShanMyDonateBean;
import com.newbrain.model.TeaCiShanMyDonateDetailListBean;
import com.newbrain.model.TeaCiShanProjectDetailBean;
import com.newbrain.model.TeaCiShanProtocolBean;
import com.newbrain.model.TeaHouseBeautyIntroListBean;
import com.newbrain.model.TeaHouseDetailInfoBean;
import com.newbrain.user.User;

public class JsonThread extends Thread {

	private Context mContext;

	private int mFlag;

	private int type;
	private List<Bean> mList;

	private Handler mHandler;

	private boolean isReturnData = true;
	
	public JsonThread(Context context, List<Bean> list, Handler mHandler,
			int flag) {
		super();
		this.mContext = context;
		this.mFlag = flag;
		this.mList = list;
		this.mHandler = mHandler;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public void run() {
		String userID = "";
		String usertoken = "";
		String temp = "";
		switch (mFlag) {

		/** 获取天气情况 */
		case Constant.FLAG_TEST_INT:

			break;

		case Constant.FLAG_GET_TEAHOUSE_DETAIL:
			
			requestPamars_get(mFlag, Constant.BASE_URL+"TeaMall/interf/teahouse/detail");
			break;
		case Constant.FLAG_FIND_TEAHOUSE_LOACTION:
			
			requestPamars_get(mFlag, Constant.BASE_URL+"TeaMall/interf/teahouse/find");
			break;
		case Constant.FLAG_SEARCH_TEAHOUSE_NAME:
			
			requestPamars_get(mFlag, Constant.BASE_URL+"TeaMall/interf/teahouse/search");
			break;
		case Constant.FLAG_GET_TEAHOUSE_BEAUTY_LIST:
			requestPamars_get(mFlag, Constant.BASE_URL+"TeaMall/teaBeauty/beautyList");
			break;
		case Constant.FLAG_GET_CROWDFUNDING:
			requestPamars_get(mFlag, Constant.BASE_URL+"TeaMall/interf/crowdf/statistics");
			break;
		case Constant.FLAG_GET_MY_CROWDFUNDING:
			requestPamars_get(mFlag, Constant.BASE_URL+"TeaMall/interf/crowdf/query");
			break;
		case Constant.FLAG_GET_MY_INCOME:
			requestPamars_get(mFlag, Constant.BASE_URL+"TeaMall/interf/crowdf/income");
			break;
		case Constant.FLAG_SET_PWD:
			requestPamars_post(mFlag, Constant.BASE_URL+"TeaMall/interf/crowdf/setPwd");
			break;
		case Constant.FLAG_CROWDFUNDING_INVEST:
			requestPamars_post(mFlag, Constant.BASE_URL+"TeaMall/interf/crowdf/invest");
			break;
		case Constant.FLAG_GET_MANOR_TEATYPE:
			requestPamars_get(mFlag, Constant.BASE_URL+"TeaMall/interf/manor/teaType");
			break;
		case Constant.FLAG_GET_MANOR_SEARCH:
			requestPamars_get(mFlag, Constant.BASE_URL+"TeaMall/interf/manor/search");
			break;
		case Constant.FLAG_GET_MANOR_DETAIL:
			requestPamars_get(mFlag, Constant.BASE_URL+"TeaMall/interf/manor/detail");
			break;
		case Constant.FLAG_GET_MANOR_VARIETY:
			requestPamars_get(mFlag, Constant.BASE_URL+"TeaMall/interf/manor/variety");
			break;
		case Constant.FLAG_GET_MANOR_BOOKING:
			requestPamars_get(mFlag, Constant.BASE_URL+"TeaMall/interf/manor/booking");
			break;
		case Constant.FLAG_CISHAN_DONATE:
			requestPamars_post(mFlag, Constant.BASE_URL+"TeaMall/interf/charity/donate");
			break;
		case Constant.FLAG_GET_CISHAN_LISTPROJECT:
			requestPamars_post(mFlag, Constant.BASE_URL+"TeaMall/interf/charity/listProject");
			break;
		case Constant.FLAG_GET_CISHAN_MYDONATE:
			requestPamars_get(mFlag, Constant.BASE_URL+"TeaMall/interf/charity/listDonate");
			break;
		case Constant.FLAG_GET_CISHAN_MYDONATE_LIST:
			requestPamars_post(mFlag, Constant.BASE_URL+"TeaMall/interf/charity/listRecord");
			break;
		case Constant.FLAG_GET_CISHAN_PROJECT_DETAIL:
			requestPamars_post(mFlag, Constant.BASE_URL+"TeaMall/interf/charity/detail");
			break;
		case Constant.FLAG_GET_CISHAN_PROTOCOL:
			requestPamars_post(mFlag, Constant.BASE_URL+"TeaMall/interf/charity/agreement");
			break;
		case Constant.FLAG_SUBMMIT_GOODS_ORDER:
			Log.e("lijinjin", "nstant.FLAG_GET_GOODS_ORDER");
			requestPamars_post(mFlag, Constant.METHOD_USER_SUBMIT_ORDER_URL);
			break;
		case Constant.FLAG_GET_SHOPCART_LIST:
			Log.e("lijinjin", "nstant.FLAG_GET_GOODS_ORDER");
			userID = User.getInstance().getId();
			usertoken = User.getInstance().getToken();
			temp = userID + "&token=" + usertoken;
			Log.e("lijinjin", "temp: " + temp);
			requestPamars_get(mFlag, Constant.METHOD_USER_CART_LIST_URL + temp);
			break;
		case Constant.FLAG_GET_MYORDER_LIST:
		{
			
			temp = mList.get(0).getValue();
//			temp = userID + "&token=" + usertoken + "&status=" + statu + "&pageNo=" + pageNum + "&pageSize=" + pageSize;
			Log.e("lijinjin", "FLAG_GET_MYORDER_LIST temp: " + temp);
			requestPamars_get(mFlag, Constant.METHOD_USER_GET_ORDER_LIST_URL + temp);
		}
			break;
		case Constant.FLAG_GET_REMOVE_SHOPCART_LIST:
		{
			requestPamars_post(mFlag, Constant.METHOD_USER_REMOVE_SHOPCART_URL);
		}
			break;
		case Constant.FLAG_GET_GET_RECEIVE_ADDRESS:
		{
			userID = User.getInstance().getId();
			usertoken = User.getInstance().getToken();
			
			temp = userID + "&token=" + usertoken;
			Log.e("lijinjin", "FLAG_GET_GET_RECEIVE_ADDRESS temp: " + temp);
			requestPamars_get(mFlag, Constant.METHOD_USER_GET_ADDRESS_URL + temp);
		}
			break;
		case Constant.FLAG_GET_GET_GOODS_LIST:
		{
			requestPamars_get(mFlag, Constant.METHOD_USER_GOODSLIST);
		}
			break;
		case Constant.FLAG_GET_GET_SHOPS_LIST:
		{
			requestPamars_getShopList(mFlag, Constant.METHOD_USER_SHOPSLIST);
		}
			break;
		case Constant.FLAG_GET_GET_SHOPS_COMMENT_LIST:
		{
			requestPamars_post(mFlag, Constant.METHOD_USER_GOOD_COMMENT_URL);
		}
			break;
		case Constant.FLAG_GET_ADD_RECEIVE_ADDRESS:
		{
			requestPamars_post(mFlag, Constant.METHOD_USER_ADD_RECEIVE_ADDRESS_URL);
		}
		break;
		case Constant.FLAG_GET_GET_SHOPS_GOODS_LIST:
		{
			requestPamars_post(mFlag, Constant.METHOD_USER_SEARCH_SHOP_GOODS);
		}
			break;
			/**	获取我的茶苗*/
		case Constant.FLAG_GET_GET_MY_SMALL_TEA_ORDER: {
			requestPamars_get(mFlag, Constant.METHOD_GET_MY_SMALL_TEA_ORDER);
		}
		break;
		}


	}

	@SuppressLint("HandlerLeak")
	public Handler handler_jsonthread = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			String obj = (String) msg.obj;

			LogUtils.e("----jsonthread里面的handler--->" + obj);
			HttpReturnData returnData = null;
			switch (msg.what) {
			case Constant.FLAG_HTTP_ERROR:
				returnData = new HttpReturnData(false,null);
				break;
			/** 天气预报 */
			case Constant.FLAG_TEST_INT:
				
				
				break;
			case Constant.FLAG_GET_TEAHOUSE_DETAIL:
				TeaHouseDetailInfoBean teaHouseDetailInfoBean = JSON.parseObject(obj, TeaHouseDetailInfoBean.class);
				returnData = new HttpReturnData(true,teaHouseDetailInfoBean);
				break;
			case Constant.FLAG_FIND_TEAHOUSE_LOACTION:
			case Constant.FLAG_SEARCH_TEAHOUSE_NAME:
				FindTeaHouseIntroBean searchTeaHouseIntroBean = JSON.parseObject(obj, FindTeaHouseIntroBean.class);
				returnData = new HttpReturnData(true,searchTeaHouseIntroBean);
				break;
			case Constant.FLAG_GET_TEAHOUSE_BEAUTY_LIST:
				TeaHouseBeautyIntroListBean teaHouseBeautyIntroListBean = JSON.parseObject(obj, TeaHouseBeautyIntroListBean.class);
				returnData = new HttpReturnData(true,teaHouseBeautyIntroListBean);
				break;
			case Constant.FLAG_GET_CROWDFUNDING:
				CrowdftotalBean crowdftotalBean = JSON.parseObject(obj, CrowdftotalBean.class);
				returnData = new HttpReturnData(true, crowdftotalBean);
				break;
			case Constant.FLAG_GET_MY_CROWDFUNDING:
				Log.e("test","我的众筹返回   "+obj);
				MyCrowdfBean myCrowdfBean = JSON.parseObject(obj, MyCrowdfBean.class); 
				returnData = new HttpReturnData(true, myCrowdfBean);
				break;
			case Constant.FLAG_GET_MY_INCOME:
				MyIncomeBean myIncomeBean = JSON.parseObject(obj, MyIncomeBean.class); 
				returnData = new HttpReturnData(true, myIncomeBean);
				break;
			case Constant.FLAG_SET_PWD:
			case Constant.FLAG_CROWDFUNDING_INVEST:
			case Constant.FLAG_CISHAN_DONATE:
			case Constant.FLAG_GET_MANOR_BOOKING:
				BaseJsonDataBean baseJsonDataBean = JSON.parseObject(obj, BaseJsonDataBean.class); 
				returnData = new HttpReturnData(true, baseJsonDataBean);
				break;

			case Constant.FLAG_GET_MANOR_TEATYPE:
				ManoTeaTypeIntroBean anoTeaTypIntroBean = JSON.parseObject(obj, ManoTeaTypeIntroBean.class);
				returnData = new HttpReturnData(true, anoTeaTypIntroBean);
				break;
			case Constant.FLAG_GET_MANOR_SEARCH:
				ManoSearchIntroBean manoSearchIntroBean = JSON.parseObject(obj, ManoSearchIntroBean.class);
				returnData = new HttpReturnData(true, manoSearchIntroBean);
				break;
			case Constant.FLAG_GET_MANOR_DETAIL:
				ManoDetailIntroBean manoDetailIntroBean = JSON.parseObject(obj, ManoDetailIntroBean.class);
				returnData = new HttpReturnData(true, manoDetailIntroBean);
				break;
			case Constant.FLAG_GET_MANOR_VARIETY:
				ManoVarietyIntroBean manoVarietyIntroBean = JSON.parseObject(obj, ManoVarietyIntroBean.class);
				returnData = new HttpReturnData(true, manoVarietyIntroBean);
				break;
			case Constant.FLAG_GET_CISHAN_LISTPROJECT:
				TeaCiShanListProjectBean teaCiShanListProjectBean = JSON.parseObject(obj, TeaCiShanListProjectBean.class);
				returnData = new HttpReturnData(true, teaCiShanListProjectBean);
				break;
			case Constant.FLAG_GET_CISHAN_MYDONATE:
				TeaCiShanMyDonateBean teaCiShanMyDonateBean = JSON.parseObject(obj, TeaCiShanMyDonateBean.class);
				returnData = new HttpReturnData(true, teaCiShanMyDonateBean);
				break;
			case Constant.FLAG_GET_CISHAN_MYDONATE_LIST:
				TeaCiShanMyDonateDetailListBean teaCiShanMyDonateDetailListBean = JSON.parseObject(obj, TeaCiShanMyDonateDetailListBean.class);
				returnData = new HttpReturnData(true, teaCiShanMyDonateDetailListBean);
				break;
			case Constant.FLAG_GET_CISHAN_PROJECT_DETAIL:
				TeaCiShanProjectDetailBean teaCiShanProjectDetailBean = JSON.parseObject(obj, TeaCiShanProjectDetailBean.class);
				returnData = new HttpReturnData(true, teaCiShanProjectDetailBean);
				break;
			case Constant.FLAG_GET_CISHAN_PROTOCOL:
				TeaCiShanProtocolBean teaCiShanProtocolBean = JSON.parseObject(obj, TeaCiShanProtocolBean.class);
				returnData = new HttpReturnData(true, teaCiShanProtocolBean);
				break;
			case Constant.FLAG_SUBMMIT_GOODS_ORDER:
			{
				if(type == 3||type == 1){
					mHandler.sendMessage(mHandler.obtainMessage(mFlag,obj));
				}else{
				SummitOrderListBean baseJsonData = JSON.parseObject(obj, SummitOrderListBean.class); 
				returnData = new HttpReturnData(true, baseJsonData);
				}
			}
				break;
			case Constant.FLAG_GET_SHOPCART_LIST:
			{
				ShopCartGoodsListBean shopcart = JSON.parseObject(obj, ShopCartGoodsListBean.class);
				returnData = new HttpReturnData(true, shopcart);
			}
				break;
			case Constant.FLAG_GET_REMOVE_SHOPCART_LIST:
			{
				BaseJsonDataBean baseDataBean = JSON.parseObject(obj, BaseJsonDataBean.class); 
				returnData = new HttpReturnData(true, baseDataBean);
			}
				break;
			case Constant.FLAG_GET_MYORDER_LIST:
			{
				MyOrderListBean myorderlist = JSON.parseObject(obj, MyOrderListBean.class);
				returnData = new HttpReturnData(true, myorderlist);
			}
				break;
			case Constant.FLAG_GET_ADD_RECEIVE_ADDRESS:
			{
				AddAddressListBean baseDataBean = JSON.parseObject(obj, AddAddressListBean.class); 
				returnData = new HttpReturnData(true, baseDataBean);
			}
				break;
				
			case Constant.FLAG_GET_GET_RECEIVE_ADDRESS:
			{
				GetAddressListBean baseDataBean = JSON.parseObject(obj, GetAddressListBean.class); 
				returnData = new HttpReturnData(true, baseDataBean);
			}
				break;
				
			case Constant.FLAG_GET_GET_GOODS_LIST:
			{
				GoodsListBean baseDataBean = JSON.parseObject(obj, GoodsListBean.class); 
				returnData = new HttpReturnData(true, baseDataBean);
			}
				break;
			case Constant.FLAG_GET_GET_SHOPS_LIST:
			{
				ShopsListBean baseDataBean = JSON.parseObject(obj, ShopsListBean.class); 
				returnData = new HttpReturnData(true, baseDataBean);
			}
				break;
			case Constant.FLAG_GET_GET_SHOPS_GOODS_LIST:
			{
				GoodsListBean baseDataBean = JSON.parseObject(obj, GoodsListBean.class); 
				returnData = new HttpReturnData(true, baseDataBean);
			}
				break;
			case Constant.FLAG_GET_GET_SHOPS_COMMENT_LIST:
			{
//				{"message":"查询成功","lowEvaluate":0,"result":[{"onTime":5,"phoneNo":"15608091110","content":"","beautyId":"5a740a0e50f1eed90150f63875b90039","communicate":5,"nickName":"特殊","profession":5,"highOpinion":0}],"highHvaluate":4,"code":"1","middleEvaluate":1}
				CommentsBean baseDataBean = JSON.parseObject(obj, CommentsBean.class); 
				returnData = new HttpReturnData(true, baseDataBean);
			}
			break;
			case Constant.FLAG_GET_GET_MY_SMALL_TEA_ORDER: {
				MySmallTeaOrderListBean baseDataBean = JSON.parseObject(obj,
						MySmallTeaOrderListBean.class);
				returnData = new HttpReturnData(true, baseDataBean);
			}
				break;
	
				
			}
			
			if(returnData != null && isReturnData){
				mHandler.sendMessage(mHandler.obtainMessage(mFlag,returnData));
			}
		}

	};

	/**
	 * @param flag
	 *            接口标识
	 * @param method
	 * 
	 *            get专用
	 * 
	 */
	private void requestPamars_get(int mFlag, String baseUrl) {

		RequestParams params = new RequestParams();

		for (int i = 0; i < mList.size(); i++) {

			params.addQueryStringParameter(mList.get(i).getKey(), mList.get(i)
					.getValue());
		}

		HttpJsonString.httpGetJsonString(mContext, params, handler_jsonthread,
				baseUrl, mFlag, HttpMethod.GET);

	}
	
	private void requestPamars_getShopList(int mFlag, String baseUrl)
	{
		String Temp = "";
		
		RequestParams params = new RequestParams();

		for (int i = 0; i < mList.size(); i++) 
		{
			Temp = Temp + mList.get(i).getKey() + mList.get(i).getValue();
		}
		
		String url = baseUrl + Temp;

		HttpJsonString.httpGetJsonString(mContext, params, handler_jsonthread,
				url, mFlag, HttpMethod.GET);
	}

	private void requestPamars_post(int mFlag, String baseUrl) {

		RequestParams params = new RequestParams();

		for (int i = 0; i < mList.size(); i++) {
			
			Log.e("lijinjin", mList.get(i).getKey() + " " + mList.get(i).getValue());

			params.addBodyParameter(mList.get(i).getKey(), mList.get(i)
					.getValue());

		}

		HttpJsonString.httpGetJsonString(mContext, params, handler_jsonthread,
				baseUrl, mFlag, HttpMethod.POST);

	}

	public void cancleReturnData(){
		isReturnData = false;
	}
	
}
