package com.liren_app.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class GetJsonByUrl {
	static ExecutorService pool = Executors.newFixedThreadPool(10);

	public static void getMessage(final int message_key,
			final IJsonListener listener,final int pageNo) {
		pool.submit(new Runnable() {

			@Override
			public void run() {
				try {
					URL url = new URL(GetUrl.getUrlByKey(message_key,pageNo));
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setConnectTimeout(5000);
					if (conn.getResponseCode() == 200) {

						InputStream in = conn.getInputStream();
						String jsonStr = "";

						byte[] buffer = new byte[1024 * 10];
						while (true) {

							int len = in.read(buffer);
							jsonStr += (new String(buffer, "UTF-8")).trim();

							buffer = new byte[1024 * 10];
							if (len == -1) {
								break;
							}
						}
						in.close();
						listener.onGetJson(jsonStr, message_key);
					} else {
						listener.onGetJsonFailed(message_key);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void getMessage(final int message_key, final String params,
			final IJsonListener listener,final int pageNo) {
		pool.submit(new Runnable() {

			@Override
			public void run() {
				try {
					URL url = new URL(GetUrl.getUrlByKey(message_key, params,pageNo));
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setConnectTimeout(5000);
					if (conn.getResponseCode() == 200) {

						InputStream in = conn.getInputStream();
						String jsonStr = "";

						byte[] buffer = new byte[1024 * 10];
						while (true) {

							int len = in.read(buffer);
							jsonStr += (new String(buffer, "UTF-8")).trim();

							buffer = new byte[1024 * 10];
							if (len == -1) {
								break;
							}
						}
						in.close();
						listener.onGetJson(jsonStr, message_key);
					} else {
						listener.onGetJsonFailed(message_key);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static long getJsonByPost(final String _url, final String _params,
			final IJsonListener _listener) {

		final long request_code = new Date().getTime();

		pool.execute(new Runnable() {

			@Override
			public void run() {
				try {

					URL url = new URL(_url);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setConnectTimeout(5000);
					conn.setRequestProperty("Content-Type",
							"application/json;charset=UTF-8");
					conn.setRequestProperty("accept", "*/*");
					conn.setRequestProperty("connection", "Keep-Alive");
					conn.setRequestProperty("user-agent",
							"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
					conn.setDoOutput(true);
					conn.setDoInput(true);
					PrintWriter out = new PrintWriter(new OutputStreamWriter(
							conn.getOutputStream(), "UTF-8"));
					out.print(_params);
					out.flush();

					String result = "";
					BufferedReader in = new BufferedReader(
							new InputStreamReader(conn.getInputStream(),
									"UTF-8"));
					String line;
					while ((line = in.readLine()) != null) {
						result += line;
					}

					_listener.onGetJson(true, result, request_code);

					out.close();
					in.close();

				} catch (Exception e) {
					_listener.onGetJson(false, e.getMessage(), request_code);

				}

			}
		});

		return request_code;

	}

	public static long getJsonByPost(final String _url,
			final String[] _params_name, final String[] _params_value,
			final IJsonListener _listener) {

		final long request_code = new Date().getTime();

		pool.execute(new Runnable() {

			@Override
			public void run() {
				// 第一步，创建HttpPost对象
				HttpPost httpPost = new HttpPost(_url);
				// 设置HTTP POST请求参数必须用NameValuePair对象
				List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

				for (int i = 0; i < _params_name.length; i++) {
					params.add(new BasicNameValuePair(_params_name[i],
							_params_value[i]));
				}

				HttpResponse httpResponse = null;
				try {
					// 设置httpPost请求参数
					httpPost.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					httpResponse = new DefaultHttpClient().execute(httpPost);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						String result = EntityUtils.toString(httpResponse
								.getEntity());
						_listener.onGetJson(true, result, request_code);
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		});

		return request_code;
	}
}
