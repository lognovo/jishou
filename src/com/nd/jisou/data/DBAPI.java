package com.nd.jisou.data;

import java.util.ArrayList;
import java.util.List;

import com.nd.jisou.MyApplication;
import com.nd.jisou.data.json.HouseDetailInfo;
import com.nd.jisou.data.json.HouseInfo;
import com.nd.jisou.service.LogService;

import android.R.bool;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBAPI {
	private static final String TB_HOUSE="house";
	private static final String[] HOUSE_ALL_COLUMNS = new String[]{"house_name","title",  "address", "price", "unit_price", "toward", "apartment", "decoration", "area", "house_type", "floors", "years","favorite","pic_index"};
	private static final String[] HOUSE_LIST_COLUMNS = new String[]{"house_name","title",  "address", "price", "area", "apartment", "pic_index"};
	
	/**
	 * 获取房产信息（列表用到）
	 * @param db
	 * @param houseName
	 * @return
	 */
	public static HouseInfo queryHouse(SQLiteDatabase db, String houseName){

		if(db==null){
			return null;
		}
		MyApplication.writeFileLog("queryHouse:"+houseName);
		Cursor cursor = db.query(TB_HOUSE, HOUSE_LIST_COLUMNS, "house_name=?", new String[]{houseName}, null, null, null);
		if(cursor!=null && cursor.getCount()>0){
			try{
				cursor.moveToFirst();
				return bindHouseInfo(cursor);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				cursor.close();
			}
		}
		return null;
	}
	
	private static HouseInfo bindHouseInfo(Cursor cursor) {
		HouseInfo houseItem =new HouseInfo();
		houseItem.setName(cursor.getString(cursor.getColumnIndex("house_name")));
		houseItem.setTitle(cursor.getString(cursor.getColumnIndex("title")));
		houseItem.setAddress(cursor.getString(cursor.getColumnIndex("address")));
		houseItem.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
		houseItem.setSize(cursor.getString(cursor.getColumnIndex("area")));
		houseItem.setApartment(cursor.getString(cursor.getColumnIndex("apartment")));
		houseItem.setPicIndex(cursor.getInt(cursor.getColumnIndex("pic_index")));
		return houseItem;
	}

	/**
	 * 获取房产详情信息（详情用到）
	 * @param db
	 * @param houseName
	 * @return
	 */
	public static HouseDetailInfo queryHouseDetail(String houseName){
		SQLiteDatabase db = new DBHelper(MyApplication.getContext()).getReadableDatabase();
		if(db==null){
			return null;
		}
		MyApplication.writeFileLog("queryHouseDetail:"+houseName);
		Cursor cursor = db.query(TB_HOUSE, HOUSE_ALL_COLUMNS, "house_name=?", new String[]{houseName}, null, null, null);
		if(cursor!=null && cursor.getCount()>0){
			try{
				cursor.moveToFirst();
				HouseDetailInfo houseItem =new HouseDetailInfo();
				houseItem.setName(cursor.getString(cursor.getColumnIndex("house_name")));
				houseItem.setTitle(cursor.getString(cursor.getColumnIndex("title")));
				houseItem.setAddress(cursor.getString(cursor.getColumnIndex("address")));
				houseItem.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
				houseItem.setSize(cursor.getString(cursor.getColumnIndex("area")));
				houseItem.setType(cursor.getString(cursor.getColumnIndex("house_type")));
				houseItem.setPicIndex(cursor.getInt(cursor.getColumnIndex("pic_index")));
				houseItem.setApartment(cursor.getString(cursor.getColumnIndex("apartment")));
				houseItem.setDecoration(cursor.getString(cursor.getColumnIndex("decoration")));
				houseItem.setFavorite(cursor.getInt(cursor.getColumnIndex("favorite")));
				houseItem.setFloors(cursor.getString(cursor.getColumnIndex("floors")));
				houseItem.setToward(cursor.getString(cursor.getColumnIndex("toward")));
				houseItem.setUnit_price(cursor.getInt(cursor.getColumnIndex("unit_price")));
				houseItem.setYears(cursor.getInt(cursor.getColumnIndex("years")));
				return houseItem;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				cursor.close();
				db.close();
			}
		}
		return null;
	}

	/**
	 * 更新收藏
	 * @param mHouseName
	 * @param isFavorite 1-收藏 0-取消
	 */
	public static boolean updateFavorite(String houseName, int isFavorite) {
		
		SQLiteDatabase db = new DBHelper(MyApplication.getContext()).getWritableDatabase();
		if(db==null){
			return false;
		}
		try{
			MyApplication.writeFileLog("updateFavorite:"+houseName+"(isFavorite="+ isFavorite+")");
			ContentValues updateValues =new ContentValues();
			updateValues.put("favorite", isFavorite);
			return db.update(TB_HOUSE, updateValues, "house_name=?", new String[]{houseName}) >0;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			db.close();
		}
	}

	/**
	 * 查下收藏的二手房
	 * @return
	 */
	public static List<HouseInfo> getFavoriteList() {
		List<HouseInfo> favList=new ArrayList<HouseInfo>();
		SQLiteDatabase db = new DBHelper(MyApplication.getContext()).getReadableDatabase();
		if(db==null){
			return favList;
		}
		
		
		Cursor cursor = db.query(TB_HOUSE, HOUSE_LIST_COLUMNS, "favorite=1", null, null, null, null);
		
		if(cursor!=null && cursor.getCount()>0){
			try{
				while (cursor.moveToNext()) {
					favList.add(bindHouseInfo(cursor));
				}

			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				cursor.close();
				db.close();
			}
		}
		MyApplication.writeFileLog("getFavoriteList:"+ favList.size());
		return favList;
	}

	/**
	 * 清除所有收藏
	 */
	public static boolean clearFavorite() {
		SQLiteDatabase db = new DBHelper(MyApplication.getContext()).getWritableDatabase();
		if(db==null){
			return false;
		}
		try{
			
			ContentValues updateValues =new ContentValues();
			updateValues.put("favorite", 0);
			int result = db.update(TB_HOUSE, updateValues, null, null);
			MyApplication.writeFileLog("clearFavorite:"+ result);
			return result > 0;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			db.close();
		}
	}
}
