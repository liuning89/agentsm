package com.lifang.search.client.service;

import com.lifang.search.client.bean.AcParamBean;
import com.lifang.search.client.bean.AcWordRltBean;

public interface SearchFacadeService {
    AcWordRltBean queryAcOnlyEstateRlt(AcParamBean acParamBean);
}
