package com.nd.jisou.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.nd.jisou.R;
import com.nd.jisou.adapter.HouseListAdapter;
import com.nd.jisou.data.WebApi;
import com.nd.jisou.data.json.HouseJsonData;
import com.nd.jisou.utils.UIUtils;
import com.nd.jisou.utils.ThreadPoolUtil;


public class HouseOldActivity extends BaseActionBarActivity {

	
    private Context mContext;
	private ListView mLVHouse;
	private HouseListAdapter mHouseAdapter;
	private Handler mHandler = new Handler();


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setTitle(R.string.house_old_title);
        setContentView(R.layout.activity_house_old);
		initView();
		loadHouseData();
    }

	

	private void initView() {
		mLVHouse = (ListView)findViewById(R.id.lv_house_list);
		mHouseAdapter = new HouseListAdapter(mLVHouse, mContext);
		mLVHouse.setAdapter(mHouseAdapter);
		UIUtils.showLoading(this);
	}

	
	/**
	 * 加载房产列表
	 */
	private void loadHouseData() {
		
		ThreadPoolUtil.getCachedThreadPool().submit(new Runnable() {
			
			@Override
			public void run() {
			     final  HouseJsonData resultData =	WebApi.getHouseOldList(1, 20);
			    
			    	 mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							 if(resultData!=null){
								mHouseAdapter.getDataList().clear();
								mHouseAdapter.getDataList().addAll(resultData.getData());
								mHouseAdapter.notifyDataSetChanged();
							 }
							 UIUtils.hideLoading(HouseOldActivity.this);
						}
					});
			    
			}
		});
	}
    
}
