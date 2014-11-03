package com.nd.jisou.activity;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.ListView;

import com.nd.jisou.R;
import com.nd.jisou.adapter.HouseListAdapter;
import com.nd.jisou.data.DBAPI;
import com.nd.jisou.data.DBHelper;
import com.nd.jisou.data.WebApi;
import com.nd.jisou.data.json.HouseInfo;
import com.nd.jisou.data.json.HouseJsonData;
import com.nd.jisou.utils.UIUtils;
import com.nd.jisou.utils.ThreadPoolUtil;


public class FavoriteActivity extends BaseActionBarActivity {

	
    private Context mContext;
	private ListView mLVHouse;
	private HouseListAdapter mHouseAdapter;
	private Handler mHandler = new Handler();
	private Button mBtnClear;



	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setTitle(R.string.favorite_title);
        setContentView(R.layout.activity_my_favorite);
		initView();
		
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		loadHouseData();
	}

	

	private void initView() {
		mLVHouse = (ListView)findViewById(R.id.lv_house_list);
		mBtnClear = (Button)findViewById(R.id.btn_clear);
		mBtnClear.setOnClickListener(onClearClickListener);
		mHouseAdapter = new HouseListAdapter(mLVHouse, mContext);
		mLVHouse.setAdapter(mHouseAdapter);
		UIUtils.showLoading(this);
	}

	
	
	/**
	 * 加载收藏列表
	 */
	private void loadHouseData() {
		
		ThreadPoolUtil.getCachedThreadPool().submit(new Runnable() {
			
			@Override
			public void run() {
			     final List<HouseInfo> favList = DBAPI.getFavoriteList();
			    
			    	 mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							 if(favList!=null){
								 if(favList.size()>0){
									 UIUtils.hideNoData(FavoriteActivity.this);
								 }
								 else{
									 UIUtils.showNoData(FavoriteActivity.this);
								 }
								mHouseAdapter.getDataList().clear();
								mHouseAdapter.getDataList().addAll(favList);
								mHouseAdapter.notifyDataSetChanged();
							 }
							 UIUtils.hideLoading(FavoriteActivity.this);
						}
					});
			    
			}
		});
	}
	
	/**
	 * 清除收藏
	 */
	private OnClickListener onClearClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			AlertDialog.Builder builder = new AlertDialog.Builder(FavoriteActivity.this);
			Dialog dialog = null;
			builder.setMessage(R.string.favorite_clear_confirm_content);
			builder.setTitle(R.string.confirm_title);
			builder.setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					    DBAPI.clearFavorite();
						mHouseAdapter.getDataList().clear();
						mHouseAdapter.notifyDataSetChanged();
						UIUtils.showNoData(FavoriteActivity.this);
					
			}});

			builder.setNegativeButton(R.string.confirm_no, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					
			}});
			dialog = builder.create();
			dialog.show();

		}
	};
    
}
