package com.nd.jisou.data;

import java.util.ArrayList;

import com.nd.jisou.R;

public class CityData {
	private static  ArrayList<String> mCityList=new ArrayList<String>();
	private static String mCurrCity="福州";
	public static  ArrayList<String> getCityList(){
		if(mCityList.size()==0){
			mCityList.add("福州");
			mCityList.add("北京");
			mCityList.add("天津");
			mCityList.add("大连");
			mCityList.add("石家庄");
			mCityList.add("太原");
			mCityList.add("沈阳");
			mCityList.add("哈尔滨");
			mCityList.add("常春");
			mCityList.add("保定");
			mCityList.add("厦门");
			mCityList.add("上海");
			mCityList.add("广州");
			mCityList.add("深圳");
			mCityList.add("西安");
			mCityList.add("兰州");
		}
		
		return mCityList;
	}
	
	public static void setCurrCity(String city){
		mCurrCity = city;
	}
	
	public static String getCurrCity(){
		return mCurrCity;
	}
}
