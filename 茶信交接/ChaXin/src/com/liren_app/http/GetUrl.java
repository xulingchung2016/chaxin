package com.liren_app.http;

public class GetUrl {
	private final static String base_url = "http://120.76.41.89";
//	private final static String base_url = "http://218.244.138.142:8081";

	public static String getImgUrl(String p_url) {
		return base_url+"/TeaMall" + p_url;

	}

	public static String getImgUrl2(String p_url) {
		return  p_url;

	}
	public static String getUrlByKey(int key,int pageNo) {
		switch (key) {
		case MessageKey.get_liren_info_simp:
			return base_url+"/TeaMall/teaBeauty/teaBeautyList?pageNo="+pageNo+"&pageSize=20";
		case MessageKey.get_quyu_info:
			return base_url+"/get_liren_info.ashx?method=get_quyu_info";
		case MessageKey.get_yingyuan_main:
			return base_url+"/TeaMall/silver/silverList";
		default:
			break;
		}

		return "";
	}

	public static String getUrlByKey(int key, String params,int pageNo) {
		switch (key) {
		case MessageKey.get_liren_paixu:
			return base_url+"/TeaMall/teaBeauty/teaBeautyList?pageNo="+pageNo+"&pageSize=30"
					+ params;
		case MessageKey.get_liren_info_simp:
			return base_url+"/TeaMall/teaBeauty/teaBeautyList?pageNo="+pageNo+"&pageSize=30"
					+ params;
		case MessageKey.get_liren_tuijian:
			return base_url+"/TeaMall/teaBeauty/teaBeautyList?pageSize=2"
					+ params;

		case MessageKey.get_liren_info:
			return base_url+"/TeaMall/teaBeauty/detail?"
					+ params;
		case MessageKey.get_liren_service_area:
			return base_url+"/TeaMall/teaBeauty/getTeaService?"
					+ params;

		default:
			break;
		}

		return "";
	}
}
