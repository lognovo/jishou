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
import android.widget.Toast;

import com.nd.jisou.R;
import com.nd.jisou.adapter.ViewFlowImageAdapter;
import com.nd.jisou.data.DBAPI;
import com.nd.jisou.data.DBHelper;
import com.nd.jisou.data.json.HouseDetailInfo;
import com.nd.jisou.utils.StringUtils;
import com.nd.jisou.widget.CircleFlowIndicator;
import com.nd.jisou.widget.ViewFlow;


public class HouseDetailActivity extends BaseActionBarActivity {

	
    public static final String EXTRA_HOUSE_NAME = "extra_house_name";
	private Context mContext;
	private ViewFlow mViewFlow;
	private CircleFlowIndicator mIndicator;
	private TextView mTVtitle;
	private TextView mTVtype;
	private TextView mTVsize;
	private TextView mTVprice;
	private ViewFlowImageAdapter mViewFlowImageAdapter;
	private String mHouseName="";
	private HouseDetailInfo mHouseDetail;
	private TextView mTVUnitPrice;
	private TextView mTVToward;
	private TextView mTVApartment;
	private TextView mTVDecoration;
	private TextView mTVFloors;
	private TextView mTVYears;
	private ImageView mIVFavorite;
	

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        if(getIntent().getExtras()!=null){
        	mHouseName = getIntent().getExtras().getString(EXTRA_HOUSE_NAME);
        }
        setTitle(mHouseName);
        setContentView(R.layout.activity_house_detail);
		initView();
		loadHouseData();
    }

	private void initView() {
		mViewFlow = (ViewFlow)findViewById(R.id.viewflow_pic);
		mIndicator = (CircleFlowIndicator)findViewById(R.id.indicator_flow);
		mTVtitle = (TextView) findViewById(R.id.tv_title);
		mTVtype = (TextView) findViewById(R.id.tv_type);
		mTVsize = (TextView) findViewById(R.id.tv_size);
		mTVprice = (TextView) findViewById(R.id.tv_price);
		mTVUnitPrice = (TextView) findViewById(R.id.tv_unit_price);
		mTVToward = (TextView) findViewById(R.id.tv_toward);
		mTVApartment = (TextView) findViewById(R.id.tv_apartment);
		mTVDecoration = (TextView) findViewById(R.id.tv_decoration);
		mTVFloors = (TextView) findViewById(R.id.tv_floors);
		mTVYears =  (TextView) findViewById(R.id.tv_years);
		mIVFavorite = (ImageView) findViewById(R.id.iv_favorite);
		mIVFavorite.setOnClickListener(onFavoriteClickListener);
	}
	
	/**
	 * 点击收藏
	 */
	private OnClickListener onFavoriteClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(mHouseDetail!=null){
				int favorite = mHouseDetail.getFavorite() == 0 ? 1 : 0;
				String action = mHouseDetail.getFavorite() == 0 ? "收藏" : "取消";
				if(DBAPI.updateFavorite(mHouseName,favorite)){
					mHouseDetail.setFavorite(favorite);
					Toast.makeText(mContext, action+"成功", Toast.LENGTH_SHORT).show();
					checkFavoriteStatus();
				}
				else{
					Toast.makeText(mContext, action+"失败", Toast.LENGTH_SHORT).show();
				}
			}
		}
	};

	/**
	 * 加载轮播图片
	 */
	private void loadHousePic(List<String> imageList) {
		mViewFlowImageAdapter = new ViewFlowImageAdapter(mContext, imageList);
		mViewFlowImageAdapter.setClickListener(onImageClickListener);
		mViewFlow.setAdapter(mViewFlowImageAdapter);
		mViewFlow.setTimeSpan(4500);
		mViewFlow.stopAutoFlowTimer();
		mViewFlow.startAutoFlowTimer(); // 启动自动播放
		mViewFlow.setFlowIndicator(mIndicator);
		mViewFlow.setmSideBuffer(imageList.size()); // 实际图片张数
		mViewFlow.setSelection(imageList.size() * 1000); // 设置初始位置
	}


	/**
	 * 轮播图点击事件,点击查看大图
	 */
	OnClickListener onImageClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(v.getTag(R.id.tagkey)!=null){
				int position = Integer.parseInt(v.getTag(R.id.tagkey).toString());
				Intent newIntent =new Intent(mContext, FullImageActivity.class);
				newIntent.putExtra(FullImageActivity.EXTRA_PIC_INDEX, position);
				newIntent.putStringArrayListExtra(FullImageActivity.EXTRA_PIC_LIST,(ArrayList<String>)mViewFlowImageAdapter.getAdvertisementAppList());
				((Activity)mContext).startActivity(newIntent);
			}
		}
	};

	/**
	 * 加载楼盘信息
	 */
	private void loadHouseData() {
		mHouseDetail = DBAPI.queryHouseDetail(mHouseName);
		if(mHouseDetail!=null){
			mTVtitle.setText(mHouseDetail.getTitle());
			mTVtype.setText(mHouseDetail.getType());
			mTVsize.setText(mHouseDetail.getSize() +"平米");
			mTVprice.setText(mHouseDetail.getPrice() +"万");
			mTVUnitPrice.setText(mHouseDetail.getUnit_price()+"元/平");
			mTVToward.setText(mHouseDetail.getToward());
			mTVApartment.setText(mHouseDetail.getApartment());
			mTVDecoration.setText(mHouseDetail.getDecoration());
			mTVFloors.setText(mHouseDetail.getFloors());
			mTVYears.setText(mHouseDetail.getYears() +"年");
			
			checkFavoriteStatus();
			loadHousePic(StringUtils.getAssertImageUrlList(mHouseDetail.getPicIndex()));
		}
	}

	private void checkFavoriteStatus() {
		if(mHouseDetail==null){
			return;
		}
		if(mHouseDetail.getFavorite()==0){ //未收藏
			mIVFavorite.setBackgroundResource(R.drawable.icon_body_favorite);
		}
		else{
			mIVFavorite.setBackgroundResource(R.drawable.icon_body_favorite_done);
		}
	}
    
}
