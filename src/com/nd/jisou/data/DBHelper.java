package com.nd.jisou.data;

import com.nd.jisou.MyApplication;
import com.nd.jisou.service.LogService;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private static final String TAG = "DBHelper";

	/** 数据库名 **/
	public static final String DATABASE_NAME = "jisou";
	/** 数据库版本号 **/
	public static int DATABASE_VERSION = 1;

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * 数据库创建和数据初始化
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate");
		MyApplication.writeFileLog("DBHelper onCreate");
		try {
				db.execSQL("CREATE TABLE IF NOT EXISTS [house] ([house_name] nvarchar(20), [title] nvarchar(50),  [address] nvarchar(50), [price] INT DEFAULT 0, [unit_price] INT, [toward] nvarchar(20), [apartment] nvarchar(20), [decoration] nvarchar(20), [area] INT, [house_type] CHAR, [floors] CHAR, [years] INT,[favorite] INT DEFAULT 0,[pic_index] INT DEFAULT 0);");
				db.execSQL("insert into house values('港头花园','十二中朝阳路，三南户型，黄金楼层','连江南路',115,9800,'东南','3室2厅1卫','普通装修',117,'普通住宅','6/7',1997,0,1);");
				db.execSQL("insert into house values('领秀新城','火车南站电梯小户型','白湖亭城门',102,13200,'南北',	'2室2厅1卫',	'精装修',	77,	'普通住宅',	'17/18',2011,0,2);");
				db.execSQL("insert into house values('中庚城','南北通透，精装修',	         '建新路',	    118,16400,'南北','2室1厅1卫','精装修',	72,'普通住宅','10/18',2012,0,3);");
				db.execSQL("insert into house values('津泰新村','东街口三坊七巷旁最中心地段', '东街口',	    115,18300,'北',  '2室2厅1卫','精装修',	63,'普通住宅','2/8'	 ,1998,0,4);");
				db.execSQL("insert into house values('江南水都','稀缺两房，仅售112万',	       '闽江大道',  112,15300,'东南','2室2厅1卫','精装修',	73,'普通住宅','1/11'	,2006,0,5);");
				db.execSQL("insert into house values('苍霞新城','白马南路福四中旁' ,          '八一七南路',	112,14700,'东',  '3室2厅1卫','精装修',	76,'普通住宅','3/7' 	,2004,0,6);");
				db.execSQL("insert into house values('仙塔小区','新东街口、特惠房'	,         '东街口',	    105,15900,'南北','2室2厅1卫','精装修',	66,'普通住宅','3/7' 	,2000,0,7);");
				db.execSQL("insert into house values('建中小区','好房不等人，房东急售',	     '金祥路'	,     104,11399,'南北','3室2厅2卫','毛坯',    91,'普通住宅','6/6'	 ,2006,0,8);");
				db.execSQL("insert into house values('闽江新苑','万达背后，1.1万的房子哪里找','闽江大道',	  110,12000,'南北','3室2厅2卫','精装修',	92,'普通住宅','2/6' 	,2004,0,9);");
				db.execSQL("insert into house values('汇福花园','配套设施齐全，性价比高',	   '五一广场'	,   118,15500,'南',  '2室2厅1卫','精装修',	76,'普通住宅','7/8'	 ,2009,0,10);");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d(TAG, "onCreate end");
		MyApplication.writeFileLog("DBHelper onCreate end");
	}

	public boolean isTableExist(SQLiteDatabase db, String tableName) {
		boolean result = false;
		if (tableName == null) {
			return false;
		}

		try {
			Cursor cursor = null;
			String sql = "select count(1) as c from sqlite_master where type ='table' and name ='"
					+ tableName.trim() + "'";
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}

			cursor.close();
		} catch (Exception e) {
		}
		return result;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}



}
