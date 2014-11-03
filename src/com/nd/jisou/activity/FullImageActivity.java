package com.nd.jisou.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.nd.jisou.R;
import com.nd.jisou.adapter.ViewFlowImageAdapter;
import com.nd.jisou.data.DBAPI;
import com.nd.jisou.data.DBHelper;
import com.nd.jisou.data.json.HouseDetailInfo;
import com.nd.jisou.utils.StringUtils;
import com.nd.jisou.widget.CircleFlowIndicator;
import com.nd.jisou.widget.ViewFlow;

public class FullImageActivity extends BaseActivity {

	public static final String EXTRA_PIC_INDEX = "extra_pic_index";
	public static final String EXTRA_PIC_LIST = "extra_pic_list";
	private Context mContext;
	private ViewFlow mViewFlow;
	private CircleFlowIndicator mIndicator;
	private ViewFlowImageAdapter mViewFlowImageAdapter;
	private int mPicIndex = 0;
	private List<String> mImageList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;

		setContentView(R.layout.activity_full_image);
		initView();
		if (getIntent().getExtras() != null) {
			mPicIndex = getIntent().getExtras().getInt(EXTRA_PIC_INDEX);
			mImageList = getIntent().getStringArrayListExtra(EXTRA_PIC_LIST);
			loadFullPic();
		}
		
		
	}

	private void initView() {
		mViewFlow = (ViewFlow) findViewById(R.id.viewflow_pic);
		mIndicator = (CircleFlowIndicator) findViewById(R.id.indicator_flow);
	}

	/**
	 * 加载轮播图片
	 */
	private void loadFullPic() {
		if (mImageList != null && mImageList.size() > 0) {
			mViewFlowImageAdapter = new ViewFlowImageAdapter(mContext,
					mImageList);
			mViewFlowImageAdapter.setClickListener(onImageClickListener);
			mViewFlow.setAdapter(mViewFlowImageAdapter);
			mViewFlow.setFlowIndicator(mIndicator);
			mViewFlow.setmSideBuffer(mImageList.size()); // 实际图片张数
			mViewFlow.setSelection(mPicIndex); // 设置初始位置
		}
	}
	
	/**
	 * 轮播图点击事件
	 */
	OnClickListener onImageClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			FullImageActivity.this.finish();
		}
	};


}
