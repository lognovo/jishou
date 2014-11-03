package com.nd.jisou.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

import android.text.TextUtils;

/**
 * URL解析器</br> 1.为URL提供曾删改参数的方法。</br>2.为URL提供添加域的方法。</br>
 * 
 * @author wen.yugang
 * @since 2011-8-10
 */
public class UrlParse {
	protected Map<String, String> mMap = new LinkedHashMap<String, String>();
	private StringBuilder mHeaderBuilder;

	public UrlParse(String url) {
		iniUrl(url);
	}

	public UrlParse() {

	}

	public StringBuilder getHeaderBuilder() {
		return mHeaderBuilder;
	}

	public void reset() {
		mMap.clear();
		mHeaderBuilder = new StringBuilder("");
	}

	/**
	 * 初始化URL
	 * 
	 * @param url
	 */
	public void iniUrl(String url) {
		if (url == null)
			return;
		mMap.clear();
		int pos = url.indexOf("?");
		if (pos == -1) {
			mHeaderBuilder = new StringBuilder(url);
			return;
		}
		mHeaderBuilder = new StringBuilder(url.substring(0, pos));
		String temp = url.substring(pos + 1);
		StringTokenizer token = new StringTokenizer(temp, "&", false);
		while (token.hasMoreElements()) {
			String[] str = token.nextToken().split("=");
			if (str != null && str.length == 2) {
				putValue(str[0], str[1]);
			}
		}

	}

	/**
	 * 替换URL，保存参数
	 * 
	 * @param url
	 */
	public void replaceUrl(String url) {
		int pos = url.indexOf("?");
		if (pos == -1) {
			mHeaderBuilder = new StringBuilder(url);
			return;
		}
		mHeaderBuilder = new StringBuilder(url.substring(0, pos));
		String temp = url.substring(pos + 1);
		StringTokenizer token = new StringTokenizer(temp, "&", false);
		while (token.hasMoreElements()) {
			String[] str = token.nextToken().split("=");
			if (str.length == 2) {
				putValue(str[0], str[1]);
			}
		}

	}

	protected String decodeUtf8(String str) {
		try {
			if (str == null || "".equals(str)) {
				return str;
			}
			return URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 获取对应的UTF8值。
	 * 
	 * @param key
	 * @return
	 */
	public String getUtf8Value(String key) {
		String temp = mMap.get(key.toLowerCase());
		return decodeUtf8(temp);
	}

	public int getInteger(String key, int def) {
		String value = getValue(key);

		if (TextUtils.isEmpty(value))
			return def;

		try {
			return Integer.valueOf(value);
		} catch (Exception ex) {
			return def;
		}
	}

	/**
	 * 获取对应的值。
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		return mMap.get(key.toLowerCase());
	}

	public boolean containsKey(String key) {
		return mMap.containsKey(key.toLowerCase());
	}

	/**
	 * 设置对应的值,如果其中有一个为空，不设置。</br> 当参数存在的时候，会代替已存在的参数。
	 */
	public UrlParse putValue(String key, String value) {
		if (key == null || value == null)
			return this;
		mMap.put(key.toLowerCase(), value);
		return this;
	}

	/**
	 * 移除值
	 * 
	 * @param key
	 */
	public void removeValue(String key) {
		mMap.remove(key.toLowerCase());
	}

	/**
	 * 获取解析后的URL地址 the method is old instead of toString method.
	 * 
	 * @return URL
	 */
	@Override
	public String toString() {
		return toStringWithoutParam();
	}

	public String toStringWithoutParam() {
		StringBuilder sb = new StringBuilder(mHeaderBuilder);
		sb.append("?");
		sb.append(getUrlParam());
		return sb.toString();
	}

	/**
	 * 获取解析后的URL参数
	 * 
	 * @return URL
	 */
	public String getUrlParam() {
		StringBuilder sb = new StringBuilder();
		Iterator<String> iterator = mMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			sb.append(key);
			sb.append("=");
			sb.append(mMap.get(key));
			sb.append("&");
		}
		if (sb.length() > 0) {
			return sb.substring(0, sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 向URL加上域，这里并不考虑特殊情况</br>例如：http://www.google.com/a.aspx
	 * appendRegion("http://www.google.com/a.aspx","hell.aspx");
	 * 所得到的是：http://www.google.com/a.aspx/hell.aspx.
	 * 
	 * @param region
	 * @return
	 */
	public UrlParse appendRegion(String region) {
		String str = mHeaderBuilder.toString();
		if (str.endsWith("/")) {
			mHeaderBuilder.append(region);
		} else {
			mHeaderBuilder.append("/").append(region);
		}
		return this;
	}
}
