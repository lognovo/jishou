package com.nd.jisou.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolUtil {
	private static ExecutorService mThreadPool;
	
	public static ExecutorService getCachedThreadPool(){
		if(mThreadPool==null){
			mThreadPool = Executors.newCachedThreadPool();
		}
		return mThreadPool;
	}
	
}
