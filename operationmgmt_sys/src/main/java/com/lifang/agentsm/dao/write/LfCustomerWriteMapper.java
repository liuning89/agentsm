package com.lifang.agentsm.dao.write;

import com.lifang.bzsm.console.entity.LfCustomer;

import java.util.Map;

public interface LfCustomerWriteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LfCustomer record);

    int insertSelective(LfCustomer record);

    int updateByPrimaryKeySelective(LfCustomer record);

    int updateByPrimaryKey(LfCustomer record);

    int addCustomerByPubMobile(Map param);
}