package com.nd.jisou.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	public static Toast makeToast(Context mContext, int resId) {
		return Toast.makeText(mContext, resId, Toast.LENGTH_SHORT);
	}

	public static Toast makeToast(Context mContext, String text) {
		return Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
	}

	public static void showToast(Context mContext, int resId) {
		Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context mContext, String text) {
		Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
	}

	public static void showLongToast(Context mContext, int resId) {
		Toast.makeText(mContext, resId, Toast.LENGTH_LONG).show();
	}

	public static void showLongToast(Context mContext, String text) {
		Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
	}
}
