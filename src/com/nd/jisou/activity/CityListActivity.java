package com.nd.jisou.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.nd.jisou.R;
import com.nd.jisou.adapter.CityListAdapter;
import com.nd.jisou.data.CityData;


public class CityListActivity extends BaseActionBarActivity implements OnClickListener {

	
    private Context mContext;
	private ListView mCityListView;
	private CityListAdapter mCityListAdapter;
	private TextView mTVCityGps;
	private Handler mHandler=new Handler();


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setTitle(R.string.city_list_title);
        setContentView(R.layout.activity_city_list);
		initView();
		initCityList();
    }

	
	@Override
	protected void onResume() {
		super.onResume();
		/*定位当前城市*/
		mLocationClient.registerLocationListener(mMyLocationListener);
		mLocationClient.start();
		mTVCityGps.setText(R.string.gps_location_request);
		mTVCityGps.setOnClickListener(null);
		Toast.makeText(mContext, R.string.gps_location_request, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mLocationClient.stop();
		mLocationClient.unRegisterLocationListener(mMyLocationListener);
	}

	private void initView() {
		mCityListView = (ListView)findViewById(R.id.lv_city_list);
		mTVCityGps= (TextView)findViewById(R.id.tv_gps_city);
		mCityListView.setOnItemClickListener(onCityItemClickListener);
		
	}

	
	private OnItemClickListener onCityItemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			CityData.setCurrCity(mCityListAdapter.getItem(position));
			CityListActivity.this.finish();
		}
	};
	private LocationClient mLocationClient;
	private MyLocationListener mMyLocationListener;
	

	
	/**
	 * 实现实位回调监听
	 */
    class MyLocationListener implements BDLocationListener {


		@Override
		public void onReceiveLocation(BDLocation location) {
			if(location!=null){ //定位成功
				final String city = location.getCity();
				String cityCode = location.getCityCode();
			    final int errorCode = location.getLocType();
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						
						if(city!=null && city!=""){
							String cityName= city.replace("市", "");
							CityData.setCurrCity(cityName);
							Toast.makeText(mContext, mContext.getString(R.string.gps_location_success, city), Toast.LENGTH_SHORT).show();
							mTVCityGps.setText(cityName);
							mTVCityGps.setOnClickListener(CityListActivity.this);
						}
						else{
							Toast.makeText(mContext, mContext.getString(R.string.gps_location_failed) + " errorcode:"+ errorCode, Toast.LENGTH_SHORT).show();
							mTVCityGps.setText(R.string.gps_location_failed);
					  }
					}
				});
				
				
			}
		}

	
   }
	
	/*初始化城市列表*/
	private void initCityList() {
		mCityListAdapter= new CityListAdapter(mContext);
		mCityListView.setAdapter(mCityListAdapter);
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener=new MyLocationListener();
		LocationClientOption option = new LocationClientOption();
		//option.setLocationMode(LocationMode.NORMAL);//设置定位模式
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
		option.setIsNeedAddress(true);//返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
		mLocationClient.setLocOption(option);
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_gps_city:
			CityData.setCurrCity(mTVCityGps.getText().toString());
			CityListActivity.this.finish();
			break;

		default:
			break;
		}
	}


    
}
