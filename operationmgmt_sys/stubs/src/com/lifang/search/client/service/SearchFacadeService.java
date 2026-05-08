package com.lifang.search.client.service;

import com.lifang.search.client.bean.AcParamBean;
import com.lifang.search.client.bean.AcWordRltBean;

public class SearchFacadeService {
    private String acIp;
    private String sellIp;
    private String rentIp;

    public void setAcIp(String acIp) { this.acIp = acIp; }
    public void setSellIp(String sellIp) { this.sellIp = sellIp; }
    public void setRentIp(String rentIp) { this.rentIp = rentIp; }

    public AcWordRltBean queryAcOnlyEstateRlt(AcParamBean acParamBean) { return null; }
}
