package com.nd.jisou.data.json;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/** 
 返回提示信息JSON数据
 
*/
public class BaseJsonData implements Serializable
{

	private static final long serialVersionUID = 8751091150253382946L;
	/** 
	 返回状态编号
	 
	*/
	@SerializedName("status")
	private int privateStatus = 0;
	public final JsonStateEnum getStatus()
	{
		return JsonStateEnum.forValue(privateStatus);
	}
	public final void setStatus(JsonStateEnum value)
	{
		privateStatus = value.getValue();
	}
	
	@SerializedName("cmd")
	private int privateCmd = 0;
	public final JsonStateEnum getCmd()
	{
		return JsonStateEnum.forValue(privateCmd);
	}
	public final void setCmd(JsonStateEnum value)
	{
		privateCmd = value.getValue();
	}
	
}