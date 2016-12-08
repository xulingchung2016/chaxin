package com.liren_app.http;

public interface IJsonListener {
	public void onGetJson(String json, int message_key);

	public void onGetJsonFailed(int message_key);

	public static final String RETURN_FAIL = "RETURN_FAIL";

	void onGetJson(boolean isSuccess, String json, long request_code);
}
