package com.nd.jisou.data.json;

/** 
 返回状态枚举
 
*/
public enum JsonStateEnum
{
	/** 
	 成功
	 
	*/
	Success(1),
	/** 
	 失败
	 
	*/
	Failed(0),
	/** 
	 没有权限访问
	 
	*/
	Forbidden(1403),
	/** 
	 你所请求的资源不存在
	 
	*/
	Unfound(1404),
	/** 
	 内部服务器错误
	 
	*/
	InternalError(1500),
	/** 
	 缓存服务器无法访问
	 
	*/
	CacheServerError(1101),
	/** 
	 网络异常
	 
	*/
	NetworkWrong(1102),
	/** 
	 参数无效
	 
	*/
	ArgInvalid(1003),
	/** 
	 警告，前台可以显示msg字段提示给用户
	 
	*/
	Warning(1004),
	/** 
	 签名错误（用户登录验证用到）
	 
	*/
	SignError(1005),
	/** 
	 警告同时可以继续执行下一步的操作
	 
	*/
	WarningAndContinue(1006),
	/** 
	 网络忙
	 
	*/
	Busy(1505),
	/**
	 * 你所提交的资源已存在
	 */
	Exists(1405)
	;

	private int intValue;
	private static java.util.HashMap<Integer, JsonStateEnum> mappings;
	private synchronized static java.util.HashMap<Integer, JsonStateEnum> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, JsonStateEnum>();
		}
		return mappings;
	}

	private JsonStateEnum(int value)
	{
		intValue = value;
		JsonStateEnum.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static JsonStateEnum forValue(int value)
	{
		return getMappings().get(value);
	}
}