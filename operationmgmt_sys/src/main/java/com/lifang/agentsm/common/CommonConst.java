
package com.lifang.agentsm.common;

public enum CommonConst {
	;

	/**************************************** error code *********************************************/
	// 10xxxx UC

	//公共模块的 error_code
	//tiger
	public static final int COMMON_ESTATE_9000001 = 9000001;//小区名称不能为空
	public static final int COMMON_ESTATE_9000002 = 9000002;//城市id不能为空
	public static final int COMMON_9000051 = 9000051;//系统异常,请检查数据
	public static final int COMMON_9000052 = 9000052;//网络异常,请稍后重试
	public static final int COMMON_9000053 = 9000053;//数据加载出错
	public static final int COMMON_9000054 = 9000054;//请勿重复提交
	
	public static final int HOUSE_PUBLISH_1100032 = 1100032;//请输入业主姓名
	public static final int HOUSE_PUBLISH_1100033 = 1100033;//请输入联系电话
	public static final int HOUSE_PUBLISH_1100034 = 1100034;//请输入价格
	public static final int HOUSE_PUBLISH_1100035 = 1100035;//请输入房型
	public static final int HOUSE_PUBLISH_1100036 = 1100036;//请输入面积
	public static final int HOUSE_PUBLISH_1100037 = 1100037;//请输入房型
	public static final int HOUSE_PUBLISH_1100038 = 1100038;//请输入改盘理由
	public static final int HOUSE_PUBLISH_1100039 = 1100039;//楼栋号须为纯数字
	public static final int HOUSE_PUBLISH_1100040 = 1100040;//室号为3-4位数字
	public static final int HOUSE_PUBLISH_1100041 = 1100041;//该小区不存在,请重新搜索
	public static final int HOUSE_PUBLISH_1100042 = 1100042;//联系人姓名至少包含两个字符
	public static final int HOUSE_PUBLISH_1100051 = 1100051;//楼栋号不能以0开头
	public static final int HOUSE_PUBLISH_1100080 = 1100080;//请输入合法的面积
	public static final int HOUSE_PUBLISH_1100081 = 1100081;//请输入合法的价格
	public static final int HOUSE_PUBLISH_1100082 = 1100082;//楼栋号不能以0开头
	public static final int HOUSE_PUBLISH_1100083 = 1100083;//单元号不能以0开头
	
	public static final int CHECK_PUBLISH_1100043 = 1100043;//该房源已有改盘在审核中
	public static final int CHECK_PUBLISH_1100044 = 1100044;//该房源已有举报在审核中
	public static final int CHECK_PUBLISH_1100045 = 1100045;//该房源已有出售在审核中
	public static final int CHECK_PUBLISH_1100046 = 1100046;//该房源已有出租在审核中
	public static final int CHECK_PUBLISH_1100051 = 1100051;//该房源正在审核中
	
	public static final int CHECK_PUBLISH_1100047 = 1100047;//请选择出租状态
	public static final int CHECK_PUBLISH_1100048 = 1100048;//请选择出售状态
	public static final int CHECK_PUBLISH_1100049 = 1100049;//不能同时选择出租出售状态
	public static final int CHECK_PUBLISH_1100050 = 1100050;//请选择出租或出售状态	
	public static final int CHECK_PUBLISH_1100052 = 1100052;//该小区暂不支持出租
	public static final int CHECK_PUBLISH_1100070 = 1100070;//该小区暂不支持出售
	public static final int CHECK_PUBLISH_1100062 = 1100062;//该小区受限，暂时不能发布
	
	//tiger

	public static final int CHECK_PUBLISH_1100053 = 1100053;//单元号错误
	public static final int CHECK_PUBLISH_1100054 = 1100054;//相同房东号码不能在同一天内提交2次以上
	public static final int COMMON_ERROR_1100055 = 1100055;//出租房源暂不允许改盘
	public static final int COMMON_ERROR_1100056 = 1100056;//出租房源暂不允许举报
	public static final int COMMON_ERROR_1100057 = 1100057;//不允许使用中介号码作为房东
	public static final int COMMON_ERROR_1100058 = 1100058;//错误的楼号或室号
	public static final int COMMON_ERROR_1100059 = 1100059;//同一房东号码不允许发布多套出租房源
	public static final int COMMON_ERROR_1100060 = 1100060;//请输入正确楼号信息
	public static final int COMMON_ERROR_1100061 = 1100061;//该房源已审核失败
	public static final int COMMON_ERROR_1100063 = 1100063;//同一房东手机号在售房源最多2套
	public static final int COMMON_ERROR_1100064 = 1100064;//该楼不存在
	public static final int COMMON_ERROR_1100065 = 1100065;//请加上resource参数
	public static final int COMMON_ERROR_1100066 = 1100066;//同一号码最多只能发布3套出租房源
	public static final int COMMON_ERROR_1100067 = 1100067;//室号格式错误
	public static final int COMMON_ERROR_1100068 = 1100068;//业主姓名格式错误
	public static final int COMMON_ERROR_1100069 = 1100069;//该房源不存在
	/**************************************** error code *********************************************/

	
	public static final int BD_ERROR140000 = 140000;//系统异常，请联系管理员
	public static final int BD_ERROR140001 = 140001;//系统异常，请检查数据
	public static final int BD_ERROR140002 = 140002;//上传图片错误，请稍后再试
}