package com.nd.jisou.service;

import com.nd.jisou.utils.LogUtil;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;


public class LogService extends Service implements ILogService {


	@Override
	public IBinder onBind(Intent intent) {
		return serviceBinder;
	}

	@Override
	public void writeFileLog(Context context,String log) {
		LogUtil.writeFileLog(context, log);
		
	}
	
	
	 private ServiceBinder serviceBinder=new ServiceBinder();

	    public class ServiceBinder extends Binder implements ILogService{

			@Override
			public void writeFileLog(Context context, String log) {
				LogUtil.writeFileLog(context, log);
				
			}
	    }
 

}
