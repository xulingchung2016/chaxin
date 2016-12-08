package com.newbrain.utils;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

public class customRounderTransformation implements Transformation {
	private int type = 0;

	public customRounderTransformation(int type) {
		this.type = type;
	}

	@Override
	public String key() {
		return "customRounderTransformation()";
	}

	@Override
	public Bitmap transform(Bitmap arg0) {
		Bitmap bitmap = null;
		if (type == 0) {
			bitmap = BitmapUtil.toRoundCorner(arg0);
		} else
			bitmap = BitmapUtil.toRoundBitmap(arg0);

		arg0.recycle();
		return bitmap;
	}

}
