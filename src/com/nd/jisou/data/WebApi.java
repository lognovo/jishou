package com.nd.jisou.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nd.jisou.MyApplication;
import com.nd.jisou.data.json.HouseInfo;
import com.nd.jisou.data.json.HouseJsonData;
import com.nd.jisou.service.LogService;
import com.nd.jisou.utils.JSONUtil;

public final class WebApi {
	private static final String TAG = "WebApi";
	public static Gson gson = new GsonBuilder().create(); 
	static final String BASE_WEB_API_URL="http://192.168.52.167/index.php";
	
	public static HouseJsonData getHouseOldList(int pageNum, int pageSize){
		HouseJsonData dataResult =null;
		try {
			/*查询接口*/
			String jsonData = JSONUtil.post(BASE_WEB_API_URL, "{\"cmd\":\"10000\"}");
			MyApplication.writeFileLog("WebApi->getHouseOldList:"+jsonData);
			if(jsonData!=null && jsonData!=""){
				dataResult = gson.fromJson(jsonData, new TypeToken<HouseJsonData>(){}.getType());
				if(dataResult!=null && dataResult.getData()!=null){
					List<HouseInfo> dbList =new ArrayList<HouseInfo>();
					SQLiteDatabase db= new DBHelper(MyApplication.getContext()).getWritableDatabase();
					for (HouseInfo item : dataResult.getData()) {
						/*查询本地数据库*/
						HouseInfo dbItem = DBAPI.queryHouse(db, item.getName());
						if(dbItem!=null){
							dbList.add(dbItem);
						}
					}
					if(db!=null){
						db.close();
					}
					dataResult.getData().clear();
					dataResult.setData(dbList);
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataResult;
	}


}
