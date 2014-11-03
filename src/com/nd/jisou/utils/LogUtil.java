package com.nd.jisou.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;

public final class LogUtil {
    private static final String LOG_FILE_NAME = "AppErrorLog.log";
    private static final int LOG_MAX_SIZE = 1024 * 1024;//1M
    
	public static void writeFileLog(Context context, String logString){
		BufferedWriter mBufferedWriter = null;
        try {
	        StringBuilder sb = new StringBuilder();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date date = new Date();
	        String timestamp = sdf.format(date);
	        sb.append(">>>>>>>>>>>>>>>>>>>>>>");
	        sb.append(timestamp);//璁板綍姣忎釜error鐨勫彂鐢熸椂闂�
	        sb.append("\n");
	        sb.append(logString);
	        sb.append("\r\n");
            File mFile = new File(StorageUtil.getWritePathIgnoreError(context,LOG_FILE_NAME));
            File pFile = mFile.getParentFile();
            if(!pFile.exists()){//濡傛灉鏂囦欢澶逛笉瀛樺湪锛屽垯鍏堝垱寤烘枃浠跺す
                pFile.mkdirs();
            }
            if (mFile.length() > LOG_MAX_SIZE) {//濡傛灉瓒呰繃鏈�ぇ鏂囦欢澶у皬锛屽垯閲嶆柊鍒涘缓涓�釜鏂囦欢
                mFile.delete();
                mFile.createNewFile();
            }
            mBufferedWriter = new BufferedWriter(new FileWriter(mFile,true));//杩藉姞妯″紡鍐欐枃浠�
            mBufferedWriter.append(sb.toString());
            mBufferedWriter.flush();
        } catch (IOException e) {
        } finally {
            if (mBufferedWriter != null) {
                try {
                    mBufferedWriter.close();
                } catch (IOException e) {
                }
            }
        }
	}
}
