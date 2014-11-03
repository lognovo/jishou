package com.nd.jisou.data.json;

import com.google.gson.annotations.SerializedName;


public class ListJsonData<T> extends BaseJsonData
{
	

	private static final long serialVersionUID = 5057107789950859321L;

	
	@SerializedName("data")
	private java.util.List<T> privateData;
	
	public final java.util.List<T> getData()
	{
		return privateData;
	}
	public final void setData(java.util.List<T> value)
	{
		privateData = value;
	}
}