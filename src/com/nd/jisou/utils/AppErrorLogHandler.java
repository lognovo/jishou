
package com.nd.jisou.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;

/**
 * 褰撳簲鐢ㄧ▼搴忕敱浜庡紓甯稿嚭鐜癋orce Close鏃讹紝鎶婅繖浜涘紓甯歌褰曞埌sdcard涓嬶紝璺緞涓簓ourPackageName/log/AppErrorLog.txt銆�
 * <p>鍩烘湰鍘熺悊锛氳缃甎I涓荤嚎绋嬬殑UncaughtExceptionHandler锛屼粠鑰屾崟鑾峰埌鍦ㄨ绾跨▼(浠ュ強璇ョ嚎绋嬬殑瀛愮嚎绋�鍑虹幇鐨勫紓甯搞�
 * <p>璋冪敤鏂瑰紡锛氬湪Application::onCreate閲岃皟鐢ㄥ涓嬩唬鐮佸嵆鍙細
 * <pre>
 * AppErrorLogHandler.getInstance(this);
 * </pre>
 * @author geolo
 *
 */
public class AppErrorLogHandler implements UncaughtExceptionHandler {


    


    private UncaughtExceptionHandler mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

    private Context mContext;
    
    private static AppErrorLogHandler instance;

    private AppErrorLogHandler(Context mContext) {
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.mContext = mContext;
    }

    public static AppErrorLogHandler getInstance(Context mContext) {
        if (instance == null) {
            instance = new AppErrorLogHandler(mContext);
        }
        return instance;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        saveCrashInfoToFile(ex);
        mUncaughtExceptionHandler.uncaughtException(thread, ex);
	}

    /**
     * 淇濆瓨寮傚父淇℃伅鍒版枃浠�
     * @param ex
     * @return
     */
    private void saveCrashInfoToFile(final Throwable ex) {
    	Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if(!StorageUtil.isSDcardExist(mContext)){//濡傛灉娌℃湁sdcard锛屽垯涓嶅瓨鍌�
		            return;
		        }
		        Writer writer = null;
		        PrintWriter printWriter = null;
		        String stackTrace = "";
		        try {
		            writer = new StringWriter();
		            printWriter = new PrintWriter(writer);
		            ex.printStackTrace(printWriter);
		            Throwable cause = ex.getCause();
		            while (cause != null) {
		                cause.printStackTrace(printWriter);
		                cause = cause.getCause();
		            }
		            stackTrace = writer.toString();
		        } catch (Exception e) {
		        }finally{
		            if(writer!=null){
		                try {
		                    writer.close();
		                } catch (IOException e) {
		                    e.printStackTrace();
		                }
		            }
		            if(printWriter!=null){
		                printWriter.close();
		            }
		        }
		        StringBuilder sb = new StringBuilder();
		        sb.append(System.getProperty("line.separator"));
		        sb.append(stackTrace);
		        sb.append(System.getProperty("line.separator"));//姣忎釜error闂撮殧2琛�
		        sb.append(System.getProperty("line.separator"));
		        LogUtil.writeFileLog(mContext, sb.toString());
			}
		};
		new Thread(runnable).start();
    }
}
