package com.lifang.agentsm.dao.read;

import com.lifang.bzsm.console.entity.LfCustomer;
import com.lifang.bzsm.console.model.*;

import java.util.List;
import java.util.Map;

public interface LfCustomerReadMapper {
    LfCustomer selectByPrimaryKey(Long id);

    /***
     * 获取客户列表
     * @param req
     * @return
     */
    List<LfCustomerInfo> selectCustomerList(LfCustomerRequest req);

    /**
     * 获取列表数据集合
     * @param req
     * @return
     */
    int selectCustomerListCount(LfCustomerRequest req);


    /**
     * 获取客户需求信息
     * @param customerId
     * @return
     */
    LfCustomerRequirementInfo selectCustomerReq(Long customerId);

    /**
     * 获取3A信息
     * @param req
     * @return
     */
    List<LfAgent3aInfo> selectCustomerAMarkList(LfCustomerRequest req);

    int selectCustomerAMarkListCount(LfCustomerRequest req);


    /**
     * 获取客户带看列表
     * @param req
     * @return
     */
    List<LfCustomerHouseSeeInfo> selectCustomerHouseSee(LfCustomerRequest req);

    int selectCustomerHouseSeeCount(LfCustomerRequest req);

    /**
     * 获取客户跟进列表
     * @param req
     * @return
     */
    List<LfCustomerFollowUpInfo> selectCustomerFollow(LfCustomerRequest req);

    int selectCustomerFollowCount(LfCustomerRequest req);


    /**
     * 查找当前用户是否有当前电话号码的客户
     * @param param
     * @return
     */
    int selectAgentCustomer(Map param);

}