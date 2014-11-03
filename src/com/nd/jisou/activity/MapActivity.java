package com.nd.jisou.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.nd.jisou.R;


public class MapActivity extends BaseActionBarActivity {

	private Context mContext;
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private LocationClient mLocationClient;
	private MyLocationListener mMyLocationListener;
	private Handler mHandler = new Handler();
	public boolean isFirstLoc=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mContext = this;
		setTitle(R.string.map_title);
		SDKInitializer.initialize(getApplicationContext());//注意：这句要在setContentView前面
		setContentView(R.layout.activity_map);
		initView();
		
	}

	private void initView() {
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMyLocationEnabled(true);
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener=new MyLocationListener();
		LocationClientOption option = new LocationClientOption();
		//option.setLocationMode(LocationMode.NORMAL);//设置定位模式
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
		option.setIsNeedAddress(true);//返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
		mLocationClient.setLocOption(option);
		

	}
	
	
	
	
	
	/**
	 * 实现实位回调监听
	 */
    class MyLocationListener implements BDLocationListener {


		@Override
		public void onReceiveLocation(final BDLocation location) {
			if(location!=null){ //定位成功
				
				MyLocationData locData = new MyLocationData.Builder()
				.accuracy(location.getRadius())
				// 此处设置开发者获取到的方向信息，顺时针0-360
				.direction(100).latitude(location.getLatitude())
				.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
				loadHouseData();
			}
			}
		}
    }

	/**
	 * 加载房屋数据
	 */
	private void loadHouseData() {	
	    /*添加文字*/
			LatLng llText1 = new LatLng(26.088407, 119.310243);
			OverlayOptions ooText1 = new TextOptions().bgColor(0xAAFFFF00)
					.fontSize(24).fontColor(0xFFFF00FF).text("鼓楼区6608套")
					.position(llText1);
			LatLng llText2 = new LatLng(26.08685, 119.335252);
			OverlayOptions ooText2 = new TextOptions().bgColor(0xAAFFFF00)
					.fontSize(24).fontColor(0xFFFF00FF).text("晋安区 4825套")
					.position(llText2);
			LatLng llText3 = new LatLng(26.060498, 119.268561);
			OverlayOptions ooText3 = new TextOptions().bgColor(0xAAFFFF00)
					.fontSize(24).fontColor(0xFFFF00FF).text("金山9975套")
					.position(llText3);
			LatLng llText4 = new LatLng(26.059589, 119.316279);
			OverlayOptions ooText4 = new TextOptions().bgColor(0xAAFFFF00)
					.fontSize(24).fontColor(0xFFFF00FF).text("台江区4155套")
					.position(llText4);
			LatLng llText5 = new LatLng(26.040568, 119.314734);
			OverlayOptions ooText5 = new TextOptions().bgColor(0xAAFFFF00)
					.fontSize(24).fontColor(0xFFFF00FF).text("仓山区3087套")
					.position(llText5);
			mBaiduMap.addOverlay(ooText1);
			mBaiduMap.addOverlay(ooText2);
			mBaiduMap.addOverlay(ooText3);
			mBaiduMap.addOverlay(ooText4);
			mBaiduMap.addOverlay(ooText5);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mLocationClient.start();
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mLocationClient.stop();
		mLocationClient.unRegisterLocationListener(mMyLocationListener);
		mMapView.onPause();
	}

}
