package com.lifang.agentsm.dao.read;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.model.ComboModel;
import com.lifang.agentsm.model.LfAgentFeeSet;
import com.lifang.agentsm.model.LfFranchiseeInfo;
import com.lifang.agentsm.model.LfYfykAmoutSet;
import com.lifang.agentsm.model.LfYfykAmoutSetLog;
import com.lifang.agentsm.model.WkCoinDetailModel;
import com.lifang.agentsm.model.WkCoinGivelModel;
import com.lifang.agentsm.model.WkcoinReport;

public interface LfYfykAmoutSetReadMapper {

    List<LfYfykAmoutSet> getList(LfYfykAmoutSet amoutSet);

    LfYfykAmoutSet getAmoutSetById(int id);

    List<LfAgentFeeSet> getFeeSetList(@Param("id") int id );

    List<LfYfykAmoutSetLog> getFeeSetLogList(Pagination pagination);

    int getCountNum(@Param("status")int status);

    List<WkCoinDetailModel> getWkCoinConsumeList(Pagination pagination);

    List<WkCoinDetailModel> getWkCoinPayList(Pagination pagination);

    LfFranchiseeInfo selectFranchiseeByName(@Param("companyName")String companyName);

    List<WkCoinGivelModel> getWkCoinGiveList(Pagination pagination);

    List<WkCoinGivelModel> getFranchiseeInfoList(Pagination pagination);

    List<WkCoinGivelModel> getWkCoinGiveList(WkCoinGivelModel req);

    List<WkCoinDetailModel> getWkCoinConsumeList(WkCoinDetailModel req);

    List<WkCoinDetailModel> getWkCoinPayList(WkCoinDetailModel req);

    long getWkCoinGiveTotal(WkCoinGivelModel req);

    long getWkCoinPayTotal(WkCoinDetailModel req);

    long getWkCoinConsumeTotal(WkCoinDetailModel req);

    List<ComboModel> getFranchiseeListByCityId();

    List<WkcoinReport> getWkCoinReportList(Pagination pagination);

}
