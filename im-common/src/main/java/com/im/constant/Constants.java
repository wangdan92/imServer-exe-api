package com.im.constant;

/**
 * Constants 用户权限
 * @author Wulg
 * @date 2010-08-10
 */
public interface Constants {
	public static final String USER_SESSION_KEY="sessionUser";

	/** token有效期（小时）*/
	public static final int TOKEN_EXPIRES_8 = 7;

    /** token有效期（分钟）*/
    public static final int TOKEN_EXPIRES_OA = 7;

	/** 存放Authorization的header字段 */
	public static final String AUTHORIZATION = "token";

	public static final String RESOURCE_MENU = "menu";
	public static final String RESOURCE_BUREAU = "bureau";
	public static final String RESOURCE_BUTTON = "button";
	public static final String RESOURCE_RECORD = "record";
	public static final String SPLIT_UNDERLINE = "_";
	public static final String SPLIT_LINE_THROUGH = "-";

	public static final String METHOD_OPTIONS = "OPTIONS";

	public static final  String METHOD_POWERPOSITION = "powerposition";

	public static final  String TOKEN_OA = "OA";
	public static final  String TOKEN_APP = "APP";
	public static final  String TOKEN_SERVICE = "SERVICE";
	public static final  String TOKEN_PC = "PC";

	/**
	 * 餐饮定时任务
	 * 0 0 10,14,16 * * ? 每天上午10点，下午2点，4点
	 * 0/5 * * * * * 5秒后执行
	 * */
	public static final String PROPERTY_SCHEDULE = "0 0 8 * * ?";
}
