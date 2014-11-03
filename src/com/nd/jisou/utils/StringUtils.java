package com.nd.jisou.utils;

import java.util.ArrayList;
import java.util.List;

public final class StringUtils {
	
	public static String getAssertImageUrl(int picIndex){
		return String.format("pic/%1$d/1.png",picIndex);
	}
	
	public static List<String> getAssertImageUrlList(int picIndex){
		List<String> urlList=new ArrayList<String>();
		for(int i=1;i<5;i++){
			urlList.add(String.format("pic/%1$d/%2$d.png",picIndex,i));
		}
		return urlList;
	}
}
