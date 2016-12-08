package com.alipay.sdk.pay;

import com.newbrain.jsonthread.Constant;

public class AliPayFunction 
{
	
	//商户PID
	public static final String PARTNER = "2088021239505292";
	//商户收款账号
	public static final String SELLER = "liwenqige1@163.com";
	//商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANglthAB9n2bwdBoy9kSL6DSEVjl7Uo8Zn/Lhd98tK/T49BHU8Ldgn9xHB4uumMW2kqH1ADrjkMzUVtuSOpnQQeBk48N6QSsDoSRCtv91wnZ2uBE0Uczzd2Y4Ilv93I9p+9ZQAZ14753V5FnEsKgvllxSlFbGCyL6+gUJPZxirvTAgMBAAECgYEAqtnsNm0GxLTIl48uio/4e9twgN+GGGbz8lISqNwvCsinS6wFxoNHMqtauXTgOZEHzV/eMTgAxJec2HOtSTOCzxLmyCw1/5E7tJnX/GCDdrUHIlGIgUOihX0xAEo1uv/2BV4EyrkqHUZVHq9EAZ8DjP5DNrHj9ToL61N79yhFnPECQQD1QuQwPWAOekgkPufL7rumZy/Rp5x2Z/+PU2NKNfqVszeN6FEwZUbXKcGEEQcQHvg1Uh7uwrBiU0VobhqeaEz7AkEA4Zx78YztCBGKhb/4DA3WXCS6PHuNtgVX8CXplvKRbdhgBxuhxXZzE7igiZdlRq12wvu+0bTuKjGHJJgc0rNlCQJAJR+66pJft9FKmjHrZDL+tHFqC5ITXNjhFhDJNrJ0+7Pk9NX1dB3gWzE22Ya6SvZlG60SgZF2oWt5VhVUciXXgQJAIhLqPT8VhYRwsWae5eMpHt2FKyFV88m20FRJhTy5biehyTEJK7OKCc52oJE3AIeuSiCPX0i+N60nRgWbRM9HUQJBALjJwUTUW6mdxZAjebar53TyyUsp8mUQ6m820m6XCubrGOkHoSa6nOKFtyk6BkqeiiYGElTKrYOOCFhiKs8wC0o=";
	//支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYJbYQAfZ9m8HQaMvZEi+g0hFY5e1KPGZ/y4XffLSv0+PQR1PC3YJ/cRweLrpjFtpKh9QA645DM1FbbkjqZ0EHgZOPDekErA6EkQrb/dcJ2drgRNFHM83dmOCJb/dyPafvWUAGdeO+d1eRZxLCoL5ZcUpRWxgsi+voFCT2cYq70wIDAQAB";
	public static final int SDK_PAY_FLAG = 1;

	public static final int SDK_CHECK_FLAG = 2;
	
	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public static String getOrderInfo(String subject, String body, String price, String OutTradeNo,int type)
	{
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + OutTradeNo + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";
		if(type ==1){//商品回调

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://120.76.41.89/TeaMall/orders/baoPayment"
				+ "\"";
		}else if(type == 2){//丽人订单回调
			// 服务器异步通知页面路径
			orderInfo += "&notify_url=" + "\"" + "http://120.76.41.89/TeaMall/orders/baoBeautyPayment"
					+ "\"";
		}else if(type == 3){//充值
			// 服务器异步通知页面路径
			orderInfo += "&notify_url=" + "\"" + "http://120.76.41.89/TeaMall/orders/baoRecharge"
								+ "\"";
		}

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}
	
	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public static String sign(String content) 
	{
		return SignUtils.sign(content, RSA_PRIVATE);
	}
	
	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public static String getSignType() 
	{
		return "sign_type=\"RSA\"";
	}
	
	/*private Handler mHandler = new Handler() 
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what) 
			{
			case SDK_PAY_FLAG:
			{
				PayResult payResult = new PayResult((String) msg.obj);
				
				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();
				
				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) 
				{
				} 
				else 
				{
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000"))
					{
					}
					else 
					{
						
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: 
			{
				break;
			}
			
			default:
				break;
			}
		};
	};*/
}
