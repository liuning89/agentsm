package com.lifang.agentsm.service;

import java.util.List;

import com.lifang.agentsm.base.model.WkCoinConsumeExport;
import com.lifang.agentsm.base.model.WkCoinPayExport;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.ComboModel;
import com.lifang.agentsm.model.LfAgentFeeSet;
import com.lifang.agentsm.model.LfYfykAmoutSet;
import com.lifang.agentsm.model.LfYfykAmoutSetLog;
import com.lifang.agentsm.model.WkCoinDetailModel;
import com.lifang.agentsm.model.WkCoinGiveExport;
import com.lifang.agentsm.model.WkCoinGivelModel;
import com.lifang.agentsm.model.WkCoinReportExport;
import com.lifang.agentsm.model.WkcoinReport;
import com.lifang.model.PageResponse;
import com.lifang.model.Response;

public interface LfYfykAmoutSetService {

    List<LfYfykAmoutSet> getList(LfYfykAmoutSet amoutSet);

    Response updateStatus(int id, int yfykStatus);

    LfYfykAmoutSet getAmoutSetById(int id);

    Response save(LfYfykAmoutSet amoutSet);

    Response add(LfYfykAmoutSet amoutSet);


    List<LfAgentFeeSet> getFeeSetList(int id);

    Response saveFeeSet(LfAgentFeeSet set);

    PageResponse getFeeSetLogList(LfYfykAmoutSetLog req);

    Response getCountNum(int status);

    Response getWkCoinConsumeList(WkCoinDetailModel wkcoin,LfEmployee employee);

    Response getWkCoinPayList(WkCoinDetailModel wkcoin,LfEmployee employee);

    Response getWkCoinGiveList(WkCoinGivelModel wkgive, LfEmployee employee);

    Response getFranchiseeInfoList(WkCoinGivelModel req,LfEmployee employee);

    Response addByCompanySave(WkCoinGivelModel wkgive);

    Response getAgentList(WkCoinGivelModel req,LfEmployee employee);

    Response addByAgentSave(WkCoinGivelModel wkgive);

    List<WkCoinGiveExport> getWkCoinGiveListNotPage(WkCoinGivelModel wkgive, LfEmployee employee);

    List<WkCoinConsumeExport> getWkCoinConsumeListNotPage(WkCoinDetailModel model, LfEmployee employee);

    List<WkCoinPayExport> getWkCoinPayListNotPage(WkCoinDetailModel model, LfEmployee employee);

    Response getWkCoinGiveTotal(WkCoinGivelModel wkgive, LfEmployee employee);

    Response getWkCoinPayTotal(WkCoinDetailModel wkcoin, LfEmployee employee);

    Response getWkCoinConsumeTotal(WkCoinDetailModel wkcoin, LfEmployee employee);

    List<ComboModel> getFranchiseeListByCityId();

    Response getWkCoinReportList(WkcoinReport report, LfEmployee employee);

    List<WkCoinReportExport> getWkCoinReportListNotPage(WkcoinReport model, LfEmployee employee);

}
