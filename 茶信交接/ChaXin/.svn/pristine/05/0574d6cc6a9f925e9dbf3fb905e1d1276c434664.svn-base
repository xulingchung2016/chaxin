package com.liren_app.http;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GetImgByUrl {

	static ExecutorService pool = Executors.newFixedThreadPool(4);

	public static void getLiRenImgs(final IImageListener listener,
			final int key, final String url) {
		pool.submit(new Runnable() {

			@Override
			public void run() {
				Bitmap img = getHttpBitmap(url);
				listener.onGetImg(key, img);
			}
		});
	}

	public static void getImage(final int request_key,
			final IImageListener listener, final String url) {
		pool.submit(new Runnable() {

			@Override
			public void run() {
				Bitmap img = getHttpBitmap(url);
				listener.onGetImg(request_key, img);
			}
		});
	}

	private static Bitmap getHttpBitmap(String url) {
		URL myFileURL;
		Bitmap bitmap = null;
		try {
			myFileURL = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();
			conn.setConnectTimeout(6000);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;

	}

}
