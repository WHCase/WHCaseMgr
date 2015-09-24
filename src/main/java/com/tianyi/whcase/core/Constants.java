package com.tianyi.whcase.core;


/**
 * @author lq
 *  
 */

public class Constants {

	public static final String CUSTOMER_TYPE = "customer";
	// session timeout
	public static final String WASHER_TYPE = "org";

	/** 是否本机构查询类型 */
	public static final int PUSH_TYPE_CUSTOMER = 1;// 客户/车主
	public static final int PUSH_TYPE_WASHER = 2;// 洗车工
	
	/*案件接收状态*/
	public static final int RECEIVE_STATUS_NOT_DISTRIBUTE = 1;//未分配
	public static final int RECEIVE_STATUS__DISTRIBUTED = 2;//已分配
	public static final int RECEIVE_STATUS__FEEDBACK = 3;//已反馈
}
