package com.nd.jisou.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

/**
 * 鎵嬫満鍐呯疆瀛樺偍鍗′互鍙婂缃瓨鍌ㄥ崱绠＄悊宸ュ叿绫�
 */
public class StorageUtil {

	/**
	 * 鍒ゆ柇鏄惁鏈塖DCARD,澶栫疆瀛樺偍鍗″強鍐呯疆瀛樺偍鍗�
	 * FIXME 鐢卞瀛樺偍鍗＄被鎻愪緵绌洪棿瀛樺湪鐨勫垽鏂�
	 * @return
	 */
	public static boolean isSDcardExist(Context context) {
		return MultiCard.getInstance(context).isSDCardExist();
	}

	
	/**
	 * 鍒ゆ柇鏄惁鏈夊缃瓨鍌ㄥ崱
	 * @return
	 */
	public static boolean isExternalSDCardExist(Context context) {
		return MultiCard.getInstance(context).isExternalSDCardExist();
	}

	/**
	 * 鏍规嵁鏂囦欢绫诲瀷妫�煡澶栫疆鍙婂唴缃瓨鍌ㄥ崱鏄惁鏈夌┖闂村彲鍐�
	 * @param fileType 鏂囦欢绫诲瀷
	 * @param type 妫�煡鑼冨洿,0琛ㄧず鎵�湁,1琛ㄧず鍙鏌ュ缃�
	 * @return
	 */
	public static boolean isLimitSDCardSpaceForWrite(Context context, int fileType, int type) {
		return MultiCard.getInstance(context).checkSDCardSpace(fileType, type);
	}

	/**
	 * 鏍规嵁鏂囦欢绫诲瀷妫�煡澶栫疆鍙婂唴缃瓨鍌ㄥ崱鏄惁瓒呰繃棰勮绌洪棿
	 * @param fileType 鏂囦欢绫诲瀷
	 * @param type 妫�煡鑼冨洿,0琛ㄧず鎵�湁,1琛ㄧず鍙鏌ュ缃�
	 * @return
	 */
	public static boolean isLimitSDCardSpaceForWriteWarning(Context context, int fileType, int type) {
		return MultiCard.getInstance(context).islimitSpaceWarning(fileType, type);
	}

	/**
	 * 鍒ゆ柇瀛樺偍鍗＄┖闂�澶栫疆銆佸唴缃瓨鍌ㄥ崱鏄惁鏈夌┖闂村彲鍐�
	 * @param context
	 * @param fileType	鏂囦欢绫诲瀷
	 * @param type		妫�煡鑼冨洿,0琛ㄧず鎵�湁,1琛ㄧず澶栫疆
	 * @param bNeedTip	鏄惁瑕佸仛鍑烘彁绀鸿
	 * @return true琛ㄧず鏃犲瓨鍌ㄥ崱鎴栨棤绌洪棿鍙啓,false琛ㄧずok
	 */
	public static boolean isSDCardSapceForWriteWithTip(Context context, int fileType, int type, boolean bNeedTip) {
		if(type == 0 && !isSDcardExist(context)) {
			if(bNeedTip) {
				ToastUtil.showToast(context, "image_save_sdcard_deny");
			}
			return true;			
		} else if(type == 1 && !isExternalSDCardExist(context)) {
			if(bNeedTip) {
				ToastUtil.showToast(context,"image_save_sdcard_deny");
			}
			return true;
		}

		if(!isLimitSDCardSpaceForWrite(context,fileType, type)) {
			if(bNeedTip) {
				ToastUtil.showToast(context, "sdcard_not_enough");
			}
			return true;
		}

		if(!isLimitSDCardSpaceForWriteWarning(context,fileType, type)) {
			if(bNeedTip) {
				ToastUtil.showToast(context, "sdcard_not_enough_warning");
			}
		}

		return false;
	}

	/**
	 * 鑾峰彇鏂囦欢鍐欏叆璺緞锛屾棤瑙嗛敊璇�
	 * @param fileName 鏂囦欢鍏ㄥ悕
	 * @return 杩斿洖璺緞锛岃繑鍥瀗ull鍒欐嫆缁濆啓鍏ユ搷浣�
	 */
	public static String getWritePathIgnoreError(Context context , String fileName) {
		return getWritePath(context, fileName, false, null, null);
	}

	/**
	 * 鑾峰彇鏂囦欢鍐欏叆璺緞
	 * @param fileName 鏂囦欢鍏ㄥ悕
	 * @return 杩斿洖璺緞锛岃繑鍥瀗ull鍒欐嫆缁濆啓鍏ユ搷浣�
	 */
	public static String getWritePath(Context context ,String fileName) {
		return getWritePath(context, fileName, true, null, null);
	}

	/**
	 * 鑾峰彇鏂囦欢鍐欏叆璺緞
	 * @param fileName 鏂囦欢鍏ㄥ悕
	 * @param tip 鏄惁甯︽彁绀鸿
	 * @param warnningTip 璀﹀憡鎻愮ず璇�
	 * @param unwriteTip 鎷掔粷鍐欏叆鎻愮ず璇�
	 * @return 杩斿洖璺緞锛岃繑鍥瀗ull鍒欐嫆缁濆啓鍏ユ搷浣�
	 */
	public static String getWritePath(Context context ,String fileName, boolean tip, String warnningTip, String unwriteTip) {
		try {
			MultiCardFilePath path = MultiCard.getInstance(context).getWritePath(fileName);
			if(path.getCode() == MultiCardFilePath.RET_LIMIT_SPACE_WARNNING) {
				if(tip) {
					if(TextUtils.isEmpty(warnningTip)) {
						ToastUtil.showToast(context, "sdcard_not_enough_warning");
					} else {
						ToastUtil.showToast(context, warnningTip);
					}
				}
			}
			return path.getFilePath();
		} catch (LimitSpaceUnwriteException e) {
			e.printStackTrace();
			if(tip) {
				if(TextUtils.isEmpty(unwriteTip)) {
					ToastUtil.showToast(context, "sdcard_not_enough");
				} else {
					ToastUtil.showToast(context, unwriteTip);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param is
	 * @param filePath
	 * @return 淇濆瓨澶辫触锛岃繑鍥�1
	 */
	public static long save(InputStream is, String filePath) {
		File f = new File(filePath);
		if (!f.getParentFile().exists()) {// 濡傛灉涓嶅瓨鍦ㄤ笂绾ф枃浠跺す
			f.getParentFile().mkdirs();
		}
		try {
			f.createNewFile();
			FileOutputStream out = new FileOutputStream(f);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = is.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			is.close();
			out.flush();
			out.close();
			return f.length();
		} catch (IOException e) {
			if(f!=null && f.exists()){
				f.delete();
			}
			return -1;
		}
	}

	/**
	 * @param bm 
	 * @param filePath
	 * @return 淇濆瓨澶辫触锛岃繑鍥�1
	 */
	public static long save(Bitmap bm, String filePath) {
		InputStream is = Bitmap2IS(bm);
		return save(is , filePath);
	}
	
	/**灏咮itmap瀵硅薄杞寲鎴怚nputStream 瀵硅薄*/
	private static InputStream Bitmap2IS(Bitmap bm){  
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);  
		InputStream sbs = new ByteArrayInputStream(baos.toByteArray());    
		return sbs;  
	}  
}
