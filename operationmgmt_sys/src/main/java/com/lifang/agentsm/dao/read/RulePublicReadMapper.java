package com.lifang.agentsm.dao.read;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.lifang.agentsm.model.*;

public interface RulePublicReadMapper {

    RulePublic selectRulePublic();

	List<Customer> findCustomerByTime(Map<String, Object> map);

	List<RuleModel> findHouseSeeByTime(Map<String, Object> map);

	List<LfPubCustomer> findPubCustomerByTime(Map<String, Object> map);

	List<LfPubCustomer> findLpcListBatch(Iterator<Customer> pubIterator);

	CustomerRequirement findCustomerRequirementById(Long id);

	int findCountSee(Long id);

	Date findDateSee(Long id);

	int findPubCustomerByMobile(String mobile);

    List<RuleModel> findFollowByTime(Map<String, Object> map);

    List<LfPubCustomer> findHouseSeeAreaByTime(Map<String, Object> map);

    List<String> findAreaIdBySH();

    List<PublicReport> selectPubReport(String date);

	List<PublicReport> selectStoreCount(String format);

	int findpubReport(Map<String, Object> pars);


	int findisfalgpubReport(Map<String, Object> pars);

	List<PublicReport> selectAreaCount(String format);

	List<HouseCountTown> findHouseSellCount();

	List<HouseCountTown> findHouseAuditcount();

	List<HouseCountTown> findHousePicturecount();

	List<HouseCountTown> findHouseVideocount();

	List<HouseCountTown> findHouseSellPointcount();

	List<HouseCountTown> findHouseCommcount();

	List<HouseCountTown> findHouseKeycount();

	List<HouseCountTown> findHouseStatecount();

}