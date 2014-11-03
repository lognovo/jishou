package com.nd.jisou.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.test.ActivityUnitTestCase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.nd.jisou.R;
import com.nd.jisou.activity.FullImageActivity;
import com.nd.jisou.utils.AsyncImageLoader;

public class ViewFlowImageAdapter extends BaseAdapter {
	final static String TAG = "ViewFlowImageAdapter";
	private Context mContext;

	private LayoutInflater mInflater;

	private ViewItemHolder itemHolder;

	private List<String> mAdvertisementAppList;
	private AsyncImageLoader mImageLoader;
	private OnClickListener mClickListener;

	public ViewFlowImageAdapter(Context context) {
		mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mImageLoader = new AsyncImageLoader(mContext);

	}

	public ViewFlowImageAdapter(Context context, List<String> advAppList) {
		this(context);
		mAdvertisementAppList = advAppList;
	}

	public void setAdvertisementAppList(List<String> advAppList) {
		mAdvertisementAppList = advAppList;
	}
	
	public List<String> getAdvertisementAppList() {
		return mAdvertisementAppList;
	}
	
	public void setClickListener(OnClickListener listener){
		mClickListener = listener;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public String getItem(int position) {
		int realPosition = position % mAdvertisementAppList.size();
		if (realPosition < mAdvertisementAppList.size()) {
			return mAdvertisementAppList.get(realPosition);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView != null) {
			itemHolder = (ViewItemHolder) convertView.getTag();
		} else {
			convertView = mInflater
					.inflate(R.layout.widget_viewflow_item, null);
			itemHolder = new ViewItemHolder();
			itemHolder.icon = (ImageView) convertView
					.findViewById(R.id.image_advertisement);
			convertView.setTag(itemHolder);

		}
		itemHolder.icon.setImageResource(R.drawable.post_list_thumb_loading);
		convertView.setTag(R.id.tagkey, position);
		convertView.setOnClickListener(mClickListener);
		String imageUrl = getItem(position);
		if (null != imageUrl) {
			itemHolder.icon.setTag(imageUrl);
			mImageLoader.loadAssestImage(mContext, itemHolder.icon, imageUrl);
		}

		return convertView;
	}

	

	private class ViewItemHolder {
		ImageView icon;
	}
	

}