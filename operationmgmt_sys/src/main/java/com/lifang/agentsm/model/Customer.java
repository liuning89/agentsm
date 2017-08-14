package com.lifang.agentsm.model;

import java.util.Date;

import lombok.Data;

@Data
public class Customer {
	private Long id;
	/**
	 * 客户姓名
	 */
    private String name;//客户姓名
    private Integer gender;//性别
    /**
     * 客户手机
     */
    private String mobile;//客户手机
    /**
     * '1 本人,2 代理,3亲属',
     */
    private Integer type;// '1 本人,2 代理,3亲属',
    /**
     * 城市id
     */
    private Integer cityId;//城市id
    /**
     * 来源
     */
    private Integer source;//'1、朋友介绍、2、老客户、3、上门、4、安居客、5、搜房、6、有房网、7、400电话、\n8、老客户推荐、9、易居网、10、58同城、11、新浪、12、搜狐二手房、13、赶集网、14、微博、15、寄函、16、短信、17、网站、18、驻守、19、057、20、厨窗、21、SalesCall、22、派报、23、红布条',
    private String remark;//备注
    private Date createTime;//
    private Date updateTime;//
    private Integer status;//'是否可用 ：0 删除（不可用），1正常',
/**
 * 关联经纪人
 */
    private Integer agentId;//关联经纪人
    private Integer cusLevel;//客户级别 
    private Date birthDay;
    private String tempSource;//
    private Integer tempId;
    private Integer isCallBackCus;//1,是网络客户来源 0.不是网络客户来源
    private Integer bonus;//'是否已经获取过100元奖金',
    private Integer wuKongWay;//'悟空找房来源渠道（1.地铁包柱 2.地铁灯箱 3.电梯广告 4.广播电台 5.朋友推荐 6.网路频道）',
}
