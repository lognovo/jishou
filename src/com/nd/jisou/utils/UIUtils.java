package com.nd.jisou.utils;

import com.nd.jisou.R;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public final class UIUtils {
	public static void showLoading(Activity context){
		View vLoading = context.findViewById(R.id.loading);
		if(vLoading!=null){
			vLoading.setVisibility(View.VISIBLE);
		}
	}
	
	public static void hideLoading(Activity context){
		View vLoading = context.findViewById(R.id.loading);
		if(vLoading!=null){
			vLoading.setVisibility(View.GONE);
		}
	}
	
	
	public static void showNoData(Activity context){
		View vNoData = context.findViewById(R.id.no_data);
		if(vNoData!=null){
			vNoData.setVisibility(View.VISIBLE);
		}
	}
	
	public static void hideNoData(Activity context){
		View vNoData = context.findViewById(R.id.no_data);
		if(vNoData!=null){
			vNoData.setVisibility(View.GONE);
		}
	}
	
	
}
