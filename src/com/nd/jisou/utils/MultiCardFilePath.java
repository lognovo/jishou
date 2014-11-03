package com.nd.jisou.utils;

/**
 * 澶氬瓨鍌ㄥ崱鏂囦欢璺緞淇℃伅
 * @author geolo
 *
 */
public class MultiCardFilePath {
	/**
	 * 杩斿洖OK
	 */
	public static final int RET_OK = 0;
	
	/**
	 * 杩斿洖鍐呭瓨涓嶅棰勮
	 */
	public static final int RET_LIMIT_SPACE_WARNNING = 1;
	
	/**
	 * 鏂囦欢瀹屾暣璺緞
	 */
	private String filePath;
	
	/**
	 * 杩斿洖鐮�
	 */
	private int code;
	
	
	/**
	 * 鑾峰彇鏂囦欢璺緞
	 * @return
	 */
	public String getFilePath() {
		return filePath;
	}
	
	/**
	 * 璁剧疆鏂囦欢璺緞
	 * @param mFilePath
	 */
	public void setFilePath(String mFilePath) {
		this.filePath = mFilePath;
	}
	
	/**
	 * 鑾峰彇杩斿洖鐮�
	 * @return
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 * 璁剧疆杩斿洖鐮�
	 * @param code
	 */
	public void setCode(int code) {
		this.code = code;
	}
}
