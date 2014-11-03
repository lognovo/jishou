package com.nd.jisou.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.nd.jisou.MyApplication;

import android.app.Application;
import android.content.Context;
import android.net.Proxy;
import android.text.TextUtils;
import android.util.Log;


public class JSONUtil {
	static final String TAG = "JSONUtil";
	// 连接超时
	static final int HTTPCON_TIMEOUT = 15000;
	// 读取数据超时
	static final int SOCKET_TIMEOUT = 15000;
	static final String CACHE_HTTP_DATA_DIR="jisou";

	public static String post(String url, String content) throws ClientProtocolException, IOException, JSONException {
		url = url.replaceAll(" ", "%20");
		HttpPost request = new HttpPost(url);
		
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		request.setEntity(new StringEntity(content));
		return request(url, request);
	}
	
	/**
	 * Post提交,针对b2bserver接口
	 * @author 苏玉城
	 * @date 2014-1-3
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 */
	public static String post(String url, List<NameValuePair> params) throws ClientProtocolException, IOException, JSONException {
		url = url.replaceAll(" ", "%20");
		HttpPost request = new HttpPost(url);
		
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        UrlEncodedFormEntity formEntity = null;
        formEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
		request.setEntity(formEntity);
		String result = request(url, request);
		return result;
	}

	public static String get(String url) throws ClientProtocolException, IOException, JSONException {
		url = url.replaceAll(" ", "%20");
		HttpGet request = new HttpGet(url);
		return request(url, request);
	}

	private static String request(String url, HttpRequestBase request) throws IOException, ClientProtocolException {
		// 设置超时
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, HTTPCON_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, SOCKET_TIMEOUT);
		
		HttpProtocolParams.setContentCharset(params, "UTF-8");
		HttpProtocolParams.setHttpElementCharset(params, "UTF-8");


		request.setParams(params);
		request.addHeader("Accept-Encoding", "gzip");

		String retSrc = cacheFilterBusiness(MyApplication.getContext(), request, url);

		return retSrc;
	}

	private static String getHttpContent(HttpResponse httpResponse) throws IllegalStateException, IOException {
		Header contentEncoding = httpResponse.getFirstHeader("Content-Encoding");
		String retSrc = null;
		if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
			InputStream inputStream = httpResponse.getEntity().getContent();
			GZIPInputStream zipInputStream = new GZIPInputStream(inputStream);
			InputStreamReader reader = new InputStreamReader(zipInputStream);
			BufferedReader in = new BufferedReader(reader);
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
			reader.close();
			zipInputStream.close();
			inputStream.close();
			retSrc = sb.toString();
			Log.d(TAG, "返回的数据是gzip压缩");
		} else {
			retSrc = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
		}
		return retSrc;
	}

	public static JSONObject loadJSONByPost(String url, String content) {
		UrlParse parse = new UrlParse(url);

		JSONObject json = null;
		try {
			// 网络请求
			json = new JSONObject(post(url, content));

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "请求 json 数据异常,url:" + url);
		}
		return json;
	}

	public static JSONObject loadJSON(String url) {
		return loadJSON(url, null);
	}

	public static JSONObject loadJSON(String url, String filePath) {
		UrlParse parse = new UrlParse(url);
		// 设置通用参数

		url = parse.toString();
		JSONObject json = null;
		try {
			// 网络请求
			String content = get(url);
			json = new JSONObject(content);
			// 本地保存
			JSONUtil.jsonObjectToFile(content, filePath, null);
			int code = json.getInt("Code");
			

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "请求 json 数据异常,url:" + url);
		}
		return json;
	}
	
	public static JSONObject loadJSON2(String url) {
		UrlParse parse = new UrlParse(url);

		url = parse.toString();
		JSONObject json = null;
		try {
			// 网络请求
			String content = get(url);
			json = new JSONObject(content);

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "请求 json 数据异常,url:" + url);
		}
		return json;
	}
	
	public static JSONObject loadJSONWithOutCommonUrlParam(String url) {
		JSONObject json = null;
		try {
			// 网络请求
			String content = get(url);
			Log.d(TAG, content);
			json = new JSONObject(content);
			
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "请求 json 数据异常,url:" + url);
		}
		return json;
	}

	public static void jsonObjectToFile(JSONObject object, String filePath) throws IOException {
		if (filePath == null)
			return;
		File f = FileUtil.createFile(filePath);
		if (!f.exists()) {
			f.createNewFile();
		}
		Writer writer = new FileWriter(filePath);
		writer.write(object.toString());
		writer.close();
	}

	public static void jsonObjectToFile(String object, String filePath, Date date) throws IOException {
		if (filePath == null)
			return;
		File f = FileUtil.createFile(filePath);
		if (f.exists()) {
			f.delete();
		}
		f.createNewFile();
		
		Writer writer = new FileWriter(filePath);
		writer.write(object.toString());
		writer.close();
		if(date != null){
			f.setLastModified(date.getTime());
		}
	}

	// 异常在此处理，不抛出
	public static JSONObject fileToJsonObject(String filePath) {

		JSONObject o = null;
		if (filePath == null)
			return null;
		File f = new File(filePath);
		if (!f.exists()) {
			return null;
		}
		try {
			BufferedReader in = new BufferedReader(new FileReader(f));
			String str = "";
			String tmp;
			while ((tmp = in.readLine()) != null) {
				str += tmp;
			}
			in.close();
			o = new JSONObject(str);
		} catch (Exception e) {
			e.getStackTrace();
			Log.w(TAG, "加载json文件失败" + e.getMessage());
		}
		return o;
	}

	// 异常在此处理，不抛出
	public static String getContentFromFile(String filePath, Date date) {
		if (filePath == null)
			return null;
		File f = new File(filePath);
		if (!f.exists()) {
			return null;
		}

		String str = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(f));

			String tmp;
			while ((tmp = in.readLine()) != null) {
				str += tmp;
			}
			in.close();
			
			if(date != null)
				f.setLastModified(date.getTime());
			
		} catch (Exception e) {
			e.getStackTrace();
			Log.w(TAG, "加载json文件失败" + e.getMessage());
		}
		return str;
	}

	public static String getCommonUrlParam(int act) {
		String urlParam = "act=" + act + "&";
		UrlParse parse = new UrlParse();
		return urlParam += parse.getUrlParam();
	}

	public static String getCommonUrlParam() {
		UrlParse parse = new UrlParse();
		return parse.getUrlParam();
	}
	
	
	public static String getCacheHttpKey(String url){
		// 生成KEY值，由URL的哈希码和ACT组成，型如210:5136479
		StringBuffer key = new StringBuffer();
		{
			int urlCode = url.hashCode();
			UrlParse urlParse = new UrlParse(url);
			String value = urlParse.getValue("act");

			// 如果没有ACT,就直接取用hashCode
			int action = 0;
			if (!TextUtils.isEmpty(value)) {
				action = Integer.valueOf(value);
				key.append(action);
				key.append("_");
			}

			key.append(urlCode);
		}
		return key.toString();
	}
	
	public static String getCacheHttpPath(String url){
		//去除网络类型（nt）对缓存的影响
		UrlParse parse = new UrlParse(url);
		parse.removeValue("nt");
		return	CACHE_HTTP_DATA_DIR + getCacheHttpKey(parse.toStringWithoutParam());
	}

	public static String cacheFilterBusiness(Context context, HttpRequestBase request, String url) {

		String content = null;
		DefaultHttpClient httpclient = null;

		try {
			if (TextUtils.isEmpty(url)) {
				return null;
			}
			String path = getCacheHttpPath(url);
			
			boolean hadRequest = false;
			Date requestTime = null;

			File tempFile = new File(path);
			if (tempFile.exists()) {
				hadRequest = true;
				requestTime = new Date(tempFile.lastModified());
			}

			// 这里会爆出
			// Invalid use of SingleClientConnManager:
			// connection still allocated
			// 所以使用了ThreadSafeClientConnManager方法
			{
				DefaultHttpClient client = new DefaultHttpClient();
				ClientConnectionManager mgr = client.getConnectionManager();
				HttpParams params = client.getParams();
				SchemeRegistry sr = mgr.getSchemeRegistry();

				ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(params, sr);
				httpclient = new DefaultHttpClient(cm, params);
			}

			HttpResponse response = null;

			// 如果有时间,添加If-Modified-Since参数获取状态码
			if (hadRequest) {
				request.addHeader("If-Modified-Since", DateUtils.formatDate(requestTime));
			}

			response = httpclient.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			
			// 当前即有上次的缓存时间也有返回304来要求获取本地数据，这个时候才能读取数据。
			if (hadRequest && statusCode == HttpStatus.SC_NOT_MODIFIED) {
				content = getContentFromFile(path, requestTime);

				boolean correct = false;
				{// 验证数据的合法性
					try {
						new JSONObject(content);
						correct = true;
					} catch (JSONException je) {
						je.printStackTrace();
					}
				}

				if (correct) {
					Header lmHead = response.getFirstHeader("Last-Modified");
					if (lmHead != null) {
						String lmTime = lmHead.getValue();
						tempFile.setLastModified(DateUtils.parseDate(lmTime).getTime());
						return content;
					} else {

					}
				} else {// 不合法就重新请求一次
					response = httpclient.execute(request);
				}
			}

			content = getHttpContent(response);

			// 判断Last-Modified的值是否存在，如果不存在将会删除缓存机制
			Header lmHead = response.getFirstHeader("Last-Modified");
			if (lmHead == null) {
				if (hadRequest) {
					tempFile.delete();
				}
			} else {
				String lmTime = lmHead.getValue();

			

				jsonObjectToFile(content, path, DateUtils.parseDate(lmTime));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		return content;
	}
}
