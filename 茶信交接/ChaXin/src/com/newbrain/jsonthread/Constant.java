package com.newbrain.jsonthread;




public class Constant {
	
	
	
	/**model测试  避免报错*/
	public static final String FLAG_TEST_STRING = "10101";
	
	public static final int FLAG_TEST_INT = 10101;
	/**  按名字搜索茶馆返回到TeaSerchTeaActivity*/
	public static final int SEARCH_BACK=10086;
	
	
	/**请求成功*/
	public static final String REQUEST_SUCCESS = "200";
	
	/**activity之间跳转传值的flag*/
	public static final String FLAG_ACTIVITY_RESULT = "result";
	
	
	public static final String FLAG_INDEX1 = "result";
	
	public static final String FLAG_ACTIVITY_INDEX_FRIST = "first";
	
	public static final String FLAG_ACTIVITY_INDEX_SECOND = "second";
	
	
	public static final String BASE_URL = "http://120.76.41.89/";
//	public static final String BASE_URL = "http://218.244.138.142:8081/";

	/**基于经纬度查找茶馆*/
	public static final int FLAG_FIND_TEAHOUSE_LOACTION = 50001;
	/**通过名称模糊搜索茶馆*/
	public static final int FLAG_SEARCH_TEAHOUSE_NAME = 50002;
	/**获取茶馆详情*/
	public static final int FLAG_GET_TEAHOUSE_DETAIL = 50003;
	/**获取茶馆丽人列表*/
	public static final int FLAG_GET_TEAHOUSE_BEAUTY_LIST = 50004;
	

	/**众筹总金额*/
	public static final int FLAG_GET_CROWDFUNDING = 50101;
	/**我的众筹*/
	public static final int FLAG_GET_MY_CROWDFUNDING = 50102;
	/**我的分红*/
	public static final int FLAG_GET_MY_INCOME = 50103;
	/**设置提现密码*/
	public static final int FLAG_SET_PWD = 50104;
	/**我要众筹*/
	public static final int FLAG_CROWDFUNDING_INVEST = 50105;

	/**获取茶慈善首页信息*/
	public static final int FLAG_GET_CISHAN_LISTPROJECT = 50201;
	/**获取茶慈善我的捐献列表*/
	public static final int FLAG_GET_CISHAN_MYDONATE = 50202;
	/**获取茶慈善我的捐献详细列表*/
	public static final int FLAG_GET_CISHAN_MYDONATE_LIST = 50203;
	/**获取茶慈善项目详情*/
	public static final int FLAG_GET_CISHAN_PROJECT_DETAIL = 50204;
	/**捐款*/
	public static final int FLAG_CISHAN_DONATE = 50205;
	/**获取茶慈善协议*/
	public static final int FLAG_GET_CISHAN_PROTOCOL = 50206;

	/**获取定种茶列表*/
	public static final int FLAG_GET_MANOR_TEATYPE = 51001;
	/**获取茶庄园列表*/
	public static final int FLAG_GET_MANOR_SEARCH = 51002;
	/**获取茶庄园详情*/
	public static final int FLAG_GET_MANOR_DETAIL = 51003;
	/**获取茶庄园品种*/
	public static final int FLAG_GET_MANOR_VARIETY = 51004;
	/**提交订单*/
	public static final int FLAG_GET_MANOR_BOOKING = 51005;
	
	/**提交商品订单*/
	public static final int FLAG_SUBMMIT_GOODS_ORDER = 52001;	
	/**获取购物车列表*/
	public static final int FLAG_GET_SHOPCART_LIST = 52002;
	/**获取订单列表*/
	public static final int FLAG_GET_MYORDER_LIST = 52003;
	/**删除购物车*/
	public static final int FLAG_GET_REMOVE_SHOPCART_LIST = 52004;
	
	/**添加收货地址*/
	public static final int FLAG_GET_ADD_RECEIVE_ADDRESS = 52005;
	
	/**获取收货地址*/
	public static final int FLAG_GET_GET_RECEIVE_ADDRESS = 52006;
	
	/**获取商品列表*/
	public static final int FLAG_GET_GET_GOODS_LIST = 52007;
	
	/**获取商铺列表*/
	public static final int FLAG_GET_GET_SHOPS_LIST = 52008;
	
	/**获取商铺的商品列表*/
	public static final int FLAG_GET_GET_SHOPS_GOODS_LIST = 52009;
	/**获取商品评价列表*/
	public static final int FLAG_GET_GET_SHOPS_COMMENT_LIST = 52010;
	
//	218.244.138.142:8081
		/*public static final String TEAMALL_BASE_URL = "http://218.244.138.142:8081/TeaMall/";
		public static final String TEAMALL_IMAGE_BASE_URL = "http://218.244.138.142:8081/TeaMall";
		public static final String TEAMALL_SHOP_IMAGE_BASE_URL = "http://218.244.138.142:8081/TeaMall/upload/";*/
		public static final String TEAMALL_BASE_URL = "http://120.76.41.89/TeaMall/";
		public static final String TEAMALL_IMAGE_BASE_URL = "http://120.76.41.89/TeaMall/";
		public static final String TEAMALL_SHOP_IMAGE_BASE_URL = "http://120.76.41.89/TeaMall/upload/";
		/**获取我的茶苗*/
		public static final int FLAG_GET_GET_MY_SMALL_TEA_ORDER = 52011;
		public static final String METHOD_GET_MY_SMALL_TEA_ORDER = TEAMALL_BASE_URL + "interf/manor/findOrder";
		public static final String METHOD_VERITY_NUM =TEAMALL_BASE_URL+ "users/sendMessage?phoneNo=";
		public static final String METHOD_REGISTER =TEAMALL_BASE_URL+ "users/register";
		public static final String METHOD_FORGET_PED =TEAMALL_BASE_URL+ "users/register";
		public static final String METHOD_USER_LOGIN =TEAMALL_BASE_URL+ "users/login?";
		public static final String METHOD_USER_GOODSLIST = TEAMALL_BASE_URL+ "goods/goodsList";
		public static final String METHOD_USER_GOODSDETAIL = TEAMALL_BASE_URL+ "goods/goodsDetail?id=";
		public static final String METHOD_USER_SEARCH_SHOP_GOODS = TEAMALL_BASE_URL+ "goods/goodsListByStore";
		public static final String METHOD_USER_SHOPSLIST = TEAMALL_BASE_URL+ "interf/teahouse/stroeList?";
		public static final String METHOD_USER_ADD_CART_URL = TEAMALL_BASE_URL + "cart/cartSave";
		public static final String METHOD_USER_CART_LIST_URL = TEAMALL_BASE_URL + "cart/cartList?userId=";
		public static final String METHOD_USER_CART_LIST_URL2 = TEAMALL_BASE_URL + "cart/cartList";
		public static final String METHOD_USER_SUBMIT_ORDER_URL = TEAMALL_BASE_URL + "orders/submitOrders";
		public static final String METHOD_USER_GET_ORDER_LIST_URL = TEAMALL_BASE_URL + "orders/ordersList?userId=";
		public static final String METHOD_USER_REMOVE_SHOPCART_URL = TEAMALL_BASE_URL + "cart/delete";
		public static final String METHOD_USER_ADD_RECEIVE_ADDRESS_URL = TEAMALL_BASE_URL + "express/saveOrUpdate";
		public static final String METHOD_USER_GET_ADDRESS_URL = TEAMALL_BASE_URL + "express/expList?userId=";
		public static final String METHOD_USER_DELETE_ADDRESS_URL = TEAMALL_BASE_URL + "express/delete?id=";
		//许>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		public static final String METHOD_USER_GOOD_COMMENT_URL = TEAMALL_BASE_URL + "evaluation/evaluationList";
		public static final String METHOD_USER_GOOD_Collect_URL = TEAMALL_BASE_URL + "collection/saveCollection";
		public static final String METHOD_USER_GOOD_sign_in = TEAMALL_BASE_URL + "userAccount/signin";
		public static final String METHOD_GET_ACCOUNT =TEAMALL_BASE_URL+ "userAccount/detail";
		public static final String METHOD_POST_USERS =TEAMALL_BASE_URL+ "users/updateUsers";
		public static final String METHOD_upload_file =TEAMALL_BASE_URL+ "uploadFile/saveFile";
		public static final String METHOD_order_detail =TEAMALL_BASE_URL+ "orders/ordersDetail";
		//丽人获取服务范围和时间区域传token  userId   （丽人的userId）
		public static final String METHOD_get_areaService =TEAMALL_BASE_URL+ "teaBeauty/getTeaService";
		//删除订单token","userId","orderId"
		public static final String METHOD_delete_orders =TEAMALL_BASE_URL+ "orders/deleteOrder";
//		token","userId","status",@"pageNo",@"pageSize
		public static final String METHOD_beauty_order =TEAMALL_BASE_URL+ "teaBeauty/getBeautyOrders";
		public static final String METHOD_get_banks =TEAMALL_BASE_URL+ "bankCard/bankList";
		public static final String METHOD_bank_delete =TEAMALL_BASE_URL+ "bankCard/delete";
		public static final String METHOD_save_bank =TEAMALL_BASE_URL+ "bankCard/bankCardSave";
		public static final String METHOD_bankCardUpdate =TEAMALL_BASE_URL+ "bankCard/bankCardUpdate";
		public static final String METHOD_submit_order =TEAMALL_BASE_URL+ "teaBeauty/sumbmitOrders";
		public static final String METHOD_isTeaBeauty =TEAMALL_BASE_URL+ "teaBeauty/isTeaBeauty";
		//成为茶丽人
		public static final String METHOD_saveTeaBeauty =TEAMALL_BASE_URL+ "teaBeauty/saveTeaBeauty";
		//修改服务时间
		public static final String METHOD_saveTeaBeauty2 =TEAMALL_BASE_URL+ "teaBeauty/saveTeaService";
		//收藏
		public static final String METHOD_getCollections =TEAMALL_BASE_URL+ "collection/collectionList";
		public static final String METHOD_gdetail=TEAMALL_BASE_URL+ "teaBeauty/detail";
		public static final String METHOD_gupdateTeaBeautyl=TEAMALL_BASE_URL+ "teaBeauty/updateTeaBeauty";
		//我的预约
		public static final String METHOD_getBeautyMyOrders=TEAMALL_BASE_URL+ "teaBeauty/getBeautyMyOrders";
		//删除茶丽人订单
		public static final String METHOD_hideTeaBeautyOrder=TEAMALL_BASE_URL+ "teaBeauty/hideTeaBeautyOrder";  
		//确认完成
		public static final String METHOD_completeService=TEAMALL_BASE_URL+ "teaBeauty/completeService";
		//丽人订单评价
		public static final String METHOD_saveEvaluation=TEAMALL_BASE_URL+ "evaluation/saveEvaluation";
		//取消收藏
		public static final String METHOD_deleteCollection=TEAMALL_BASE_URL+ "collection/deleteCollection";
		//茶丽人推荐
		public static final String METHOD_teaBeautyList=TEAMALL_BASE_URL+ "teaBeauty/teaBeautyList";
		//修改体现密码
		public static final String METHOD_modifyPayPassword=TEAMALL_BASE_URL+ "userAccount/modifyPayPassword";
		//修改手机号码
		public static final String METHOD_modifyPhoneNo=TEAMALL_BASE_URL+ "userAccount/modifyPhoneNo";
		//用户充值
		public static final String METHOD_userRecharge=TEAMALL_BASE_URL+ "userAccount/userRecharge";
		//提现 
		public static final String METHOD_userDeposit=TEAMALL_BASE_URL+ "userAccount/userDeposit";
		//确定收货
		public static final String METHOD_confirmOrder=TEAMALL_BASE_URL+ "orders/confirmOrder";
		//商品评论
		public static final String METHOD_saveGoodsEvaluation=TEAMALL_BASE_URL+ "evaluation/saveGoodsEvaluation";
		//退货接口
		public static final String METHOD_returnedGood=TEAMALL_BASE_URL+ "orders/returnedGood";
		//退货接口
		public static final String METHOD_complain = TEAMALL_BASE_URL+"interf/order/complain";
		//关于茶信
		public static final String METHOD_about = TEAMALL_BASE_URL+"users/getSysAbout";
		//收入支出详细
		public static final String METHOD_getPayDetail = TEAMALL_BASE_URL+"userAccount/getPayDetail";
		//获取微信支付结果
		public static final String METHOD_getOrderState = TEAMALL_BASE_URL+"orders/getOrderState";
		//获取微信支付结果
		public static final String METHOD_getTeaMallIamges = TEAMALL_BASE_URL+"users/getTeaMallIamges";
		
		/**请求失败*/
		public static final int FLAG_HTTP_ERROR = 400;
		
		// 微信支付替换为你的应用从官方网站申请到的合法appId
	    public static final String APP_ID = "wx46c0f861a02329c2";
}