package com.nd.jisou.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.text.TextUtils;

/**
 * 鍙嶅皠宸ュ叿绫�
 * 閫氳繃鍙嶅皠鑾峰緱瀵瑰簲鍑芥暟鍔熻兘
 * @author geolo
 */
public class ReflectionUtil {

    /**
     * 閫氳繃绫诲悕锛岃繍琛屾寚瀹氭柟娉�
     * @param cName         绫诲悕
     * @param methodName    鏂规硶鍚�
     * @param params        鍙傛暟鍊�
     * @return 澶辫触杩斿洖null
     */
    public static Object invokeByClassName(String cName, String methodName, Object[] params) {
        
    	if(TextUtils.isEmpty(cName)
        		|| TextUtils.isEmpty(methodName)){
        		return null;
        }
    	
        Object retObject = null;
        
        try {
            // 鍔犺浇鎸囧畾鐨勭被
            Class cls = Class.forName(cName);    
            
            // 鍒╃敤newInstance()鏂规硶锛岃幏鍙栨瀯閫犳柟娉曠殑瀹炰緥
            Constructor ct = cls.getConstructor(null);
            Object obj = ct.newInstance(null);    

            // 鏍规嵁鏂规硶鍚嶈幏鍙栨寚瀹氭柟娉曠殑鍙傛暟绫诲瀷鍒楄〃
            Class paramTypes[] = _getParamTypes(cls, methodName);
            
            // 鑾峰彇鎸囧畾鏂规硶
            Method meth = cls.getMethod(methodName, paramTypes);
            meth.setAccessible(true);
            
            // 璋冪敤鎸囧畾鐨勬柟娉曞苟鑾峰彇杩斿洖鍊间负Object绫诲瀷
            if(params == null){
            	retObject = meth.invoke(obj);
            } else {
            	retObject = meth.invoke(obj, params);
            }
            
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        return retObject;
    }
    
    /**
     * 閫氳繃绫诲璞★紝杩愯鎸囧畾鏂规硶
     * @param obj 绫诲璞�
     * @param methodName 鏂规硶鍚�
     * @param params 鍙傛暟鍊�
     * @return 澶辫触杩斿洖null
     */
    public static Object invokeByObject(Object obj, String methodName, Object[] params) {
    	
    	if(obj == null
    		|| TextUtils.isEmpty(methodName)){
    		return null;
    	}
    	
        Class cls = obj.getClass();
        Object retObject = null;
        
        try {

            // 鏍规嵁鏂规硶鍚嶈幏鍙栨寚瀹氭柟娉曠殑鍙傛暟绫诲瀷鍒楄〃
            Class paramTypes[] = _getParamTypes(cls, methodName);
            
            // 鑾峰彇鎸囧畾鏂规硶
            Method meth = cls.getMethod(methodName, paramTypes);
            meth.setAccessible(true);
            
            // 璋冪敤鎸囧畾鐨勬柟娉曞苟鑾峰彇杩斿洖鍊间负Object绫诲瀷
            if(params == null){
            	retObject = meth.invoke(obj);
            } else {
            	retObject = meth.invoke(obj, params);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        return retObject;
    }
    
    /**
     * 鑾峰彇鍙傛暟绫诲瀷锛岃繑鍥炲�淇濆瓨鍦–lass[]涓�
     * @param cls 绫�
     * @param mName 鏂规硶鍚嶅瓧
     * @return 杩斿洖浜х敓绫诲瀷鍒楄〃
     */
    private static Class[] _getParamTypes(Class cls, String mName) {
        Class[] cs = null;
        
        /*
         * Note: 鐢变簬鎴戜滑涓�埇閫氳繃鍙嶅皠鏈哄埗璋冪敤鐨勬柟娉曪紝鏄潪public鏂规硶
         * 鎵�互鍦ㄦ澶勪娇鐢ㄤ簡getDeclaredMethods()鏂规硶
         */
        Method[] mtd = cls.getDeclaredMethods();    
        for (int i = 0; i < mtd.length; i++) {
            if (!mtd[i].getName().equals(mName)) {    // 涓嶆槸鎴戜滑闇�鐨勫弬鏁帮紝鍒欒繘鍏ヤ笅涓�寰幆
                continue;
            }
            
            cs = mtd[i].getParameterTypes();
        }
        return cs;
    }
    
    /**
     * 鑾峰彇鍐呯疆SD鍗¤矾寰�
     * @param context
     * @return
     */
    public static List<String> getInternalSDCardPath(Context context) {
    	List<String> pathes = new ArrayList<String>();
    	Object sm = context.getSystemService("storage");
    	Object[] ob = (Object[])invokeByObject(sm, "getVolumeList", null);
    	if(ob != null) {
    		for(Object o : ob) {
        		final String PATH = "mPath=";
        		final String REMOVABLE = "mRemovable=";
        		String path = null;
        		String removable = null;
        		try {
               		path = o.toString();
            		path = path.substring(path.indexOf(PATH));
            		path = path.substring(PATH.length(), path.indexOf(","));
            		
            		removable = o.toString();
            		removable = removable.substring(removable.indexOf(REMOVABLE));
            		removable = removable.substring(REMOVABLE.length(), removable.indexOf(","));
				} catch (Exception e) {
					e.printStackTrace();
					path = null;
				}
				
				if(!TextUtils.isEmpty(path) && !TextUtils.isEmpty(removable) && removable.equalsIgnoreCase("false")){
	        		pathes.add(path);
				}
   
        	}
    	}
    	return pathes;
    }
}