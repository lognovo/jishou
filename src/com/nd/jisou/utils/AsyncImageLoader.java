package com.nd.jisou.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.util.Log;
import android.util.LruCache;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;


/*
 * 异步加载图片组件
 * author：zrm
 * date：2014.03.06
 */
public class AsyncImageLoader {

	private static final int CONNECT_TIMEOUT = 10000; // 30s 网络下载连接超时时间
	private static final int READ_TIMEOUT = 10000; // 30s 网络下载文件读取超时时间
	private static final int SOFT_CACHE_SIZE = 20; // 软缓存个数
	private static final int MAX_HARD_CACHE = 5 * 1024 * 1024; // 5MB
	private Context mContext;
	private static ExecutorService threadPool;
	private static LinkedHashMap<String, WeakReference<Bitmap>> mSoftBitmapCache;
	private static LruCache<String, Bitmap> mHardBitmapCache;

	private Handler mHanlder = new Handler();
	private AbsListView mListView;
	private BaseAdapter mAdapter;
	private AsyncImageLoader mInstance;
	public final static int ROUND_CORNER_PIX = 20; // 圆角像素
	final static String TAG = "AsyncImageLoader";
	private static final long THREAD_TIME_OUT = 15*1000;//线程超时

	public AsyncImageLoader(Context context) {
		init(context);
	}

	private void init(Context context) {
		mInstance = this;
		mContext = context;
		if (threadPool == null) {
			threadPool = Executors.newCachedThreadPool();
			
		}
		initCache();
	}

	public AsyncImageLoader(Context context, AbsListView lView,
			BaseAdapter adapter) {
		init(context);
		mListView = lView;
		mAdapter = adapter;
	}



	
	private void initCache() {
		if (mHardBitmapCache == null) {
			int cacheSize = (int) (getAvailMemory() / 6); // 硬缓存大小：可用内存的1/6
			if (cacheSize > MAX_HARD_CACHE) {
				cacheSize = MAX_HARD_CACHE;
			}
			mHardBitmapCache = new LruCache<String, Bitmap>(cacheSize) {
				@Override
				protected int sizeOf(String key, Bitmap value) {
					if (value != null)
						return value.getByteCount();
					else
						return 0;
				}

				@Override
				protected void entryRemoved(boolean evicted, String key,
						Bitmap oldValue, Bitmap newValue) {

					if (oldValue != null) {
						addSoftCache(key, oldValue);
					}

					synchronized (mHardBitmapCache) {
						remove(key);
						Log.d(TAG, "mHardBitmapCache->entryRemoved:" + key);
					}
				}
			};

			mSoftBitmapCache = new LinkedHashMap<String, WeakReference<Bitmap>>(
					SOFT_CACHE_SIZE, 0.75f, true) {
				private static final long serialVersionUID = 6040103833179403725L;

				@Override
				protected boolean removeEldestEntry(
						Entry<String, WeakReference<Bitmap>> eldest) {
					if (size() > SOFT_CACHE_SIZE) {
						return true;
					}
					return false;
				}
			};
		}
	}

	private void bindImageView(final ImageView iv, final String filePath, final Bitmap bp) {
		mHanlder.post(new Runnable() {
			@Override
			public void run() {
				if(iv.getTag() == null || (iv.getTag()!=null && iv.getTag().toString().equals(filePath))){
					iv.setImageBitmap(bp);
				}
			}
		});
	}

	/**
	 * 加载assest 里面的图片
	 * @param context
	 * @param ivPic
	 * @param filePath
	 * @param thumbWidth 0-读取原图
	 * @param thumbHeight 0-读取原图
	 */
	public void loadAssestImage(final ImageView ivPic, final String filePath, final int thumbWidth, final  int thumbHeight){
		if(filePath==null || filePath.trim().length()==0){
			return;
		}
		final String cacheKey = filePath +"_"+thumbWidth +"_"+ thumbHeight;
		Bitmap bpCached = getBitmapCache(cacheKey);
		if(bpCached!=null && !bpCached.isRecycled()){
			ivPic.setImageBitmap(bpCached);
			return;
		}
		threadPool.submit(new Runnable() {
			public void run() {
				InputStream iStream=null;
				try {
					 iStream = mContext.getResources().getAssets().open(filePath); 
					 Bitmap bp =null;
					 if(thumbWidth==0 || thumbHeight==0){
						bp = BitmapFactory.decodeStream(iStream); //读取原图
					 }
					 else{
						 /*读取缩略图*/
						 Options opts= new Options();
						 opts.inJustDecodeBounds=true;
						 BitmapFactory.decodeStream(iStream, null, opts);
						 int thumbSize1= opts.outWidth / thumbWidth;
						 int thumbSize2= opts.outHeight / thumbHeight;
						 int thumbSize= Math.min(thumbSize1, thumbSize2);
						 if(thumbSize<1){
							 thumbSize=1;
						 }
						 opts.inJustDecodeBounds=false;
						 opts.inSampleSize = thumbSize;
						 bp = BitmapFactory.decodeStream(iStream, null, opts);
					 }
		
					 bindImageView(ivPic, filePath, bp);
					 addBitmapCache(cacheKey, bp);
					 
				} catch (IOException e) {
					e.printStackTrace();
				}
				finally{
					if(iStream!=null){
						try {
							iStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
	}

	/*加载本地图片*/
	public void loadAssestImage(final Context context, final ImageView ivPic, final String filePath){
		loadAssestImage(ivPic,filePath, 0, 0);
	}

	private void addSoftCache(String imageUrl, Bitmap imageBitmap) {
		synchronized (mSoftBitmapCache) {

			if (mSoftBitmapCache.containsKey(imageUrl)) {
				mSoftBitmapCache.remove(imageUrl);
			}

			mSoftBitmapCache.put(imageUrl, new WeakReference<Bitmap>(
					imageBitmap));
		}

	}

	public interface ListImageLoadCallback {
		public void finish(int position, Bitmap bp);
	}

	public interface ImageLoadCallback {
		public void finish(ImageView iv, Bitmap bp);
	}


	protected void addBitmapCache(String key, Bitmap bit) {
		if (key == null || key == "" || bit == null) {
			return;
		}
		synchronized (mHardBitmapCache) {
			mHardBitmapCache.put(key, bit);
		}

	}

	public Bitmap getBitmapCache(String key) {
		Bitmap bit = null;

		if (key == null || key == "") {
			return null;
		}

		/* 先从硬缓存中查询 */

		bit = getHardBitmapCache(key);
		if (bit != null) {
			return bit;
		}

		/* 再从软缓存中查询 */
		synchronized (mSoftBitmapCache) {
			WeakReference<Bitmap> softBit = mSoftBitmapCache.get(key);
			if (softBit != null) {
				bit = softBit.get();
				if (bit != null && !bit.isRecycled()) {
					return bit;
				} else {
					mSoftBitmapCache.remove(key);
				}
			}
		}
		return bit;
	}

	private Bitmap getHardBitmapCache(String key) {
		Bitmap bit = null;
		if (key == null || key == "") {
			return null;
		}
		synchronized (mHardBitmapCache) {
			bit = mHardBitmapCache.get(key);
			if (bit != null) {
				mHardBitmapCache.remove(key);
				mHardBitmapCache.put(key, bit);
				return bit;
			} else {
				mSoftBitmapCache.remove(key);
			}
		}
		return bit;
	}

	private long getAvailMemory() {// 获取android当前可用内存大小

		ActivityManager am = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo mi = new MemoryInfo();
		am.getMemoryInfo(mi);
		return mi.availMem; // 当前系统的可用内存

	}

}
