package com.nd.jisou.activity;

import com.nd.jisou.MyApplication;
import com.nd.jisou.R;
import com.nd.jisou.data.CityData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class MainActivity extends BaseActivity implements OnClickListener {


    private Context mContext;
	private TextView mTVLocCity;
	private TextView mTVErshoufang;
	private TextView mBottomMy;
	private TextView mTVMapInfo;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
		initView();

    }



	private void initView() {
		mTVLocCity = (TextView)findViewById(R.id.tv_city);
		mTVErshoufang = (TextView)findViewById(R.id.tv_ershoufang);
		mBottomMy = (TextView)findViewById(R.id.bottom_my);
		mTVMapInfo = (TextView)findViewById(R.id.tv_map_info);
		mTVLocCity.setOnClickListener(this);
		mTVErshoufang.setOnClickListener(this);
		mBottomMy.setOnClickListener(this);
		mTVMapInfo.setOnClickListener(this);
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		mTVLocCity.setText(CityData.getCurrCity());
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_city: //顶部城市名称
			startActivity(new Intent(mContext, CityListActivity.class));
			break;
		case R.id.tv_ershoufang://二手房
			startActivity(new Intent(mContext, HouseOldActivity.class));
			break;
		case R.id.bottom_my://我的收藏夹
			startActivity(new Intent(mContext, FavoriteActivity.class));
			break;
		case R.id.tv_map_info://地图看房
			startActivity(new Intent(mContext, MapActivity.class));
			break;
		default:
			break;
		}
		
	}




	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

    
}
