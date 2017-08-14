package com.lifang.bzsm.console.service;

import com.lifang.bzsm.console.model.LfBzAgentReportRequest;
import com.lifang.bzsm.console.report.read.LfBzAgentReportReadMapper;
import com.lifang.bzsm.console.report.read.LfBzAreaReportReadMapper;
import com.lifang.model.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BZAreaReportService {

	@Autowired
	private LfBzAreaReportReadMapper lfBzAreaReportReadMapper;
	
 
    public PageResponse getAreaReportList(LfBzAgentReportRequest req)
    {
        req.setPageIndex(req.getPageIndex() * req.getPageSize());
        PageResponse pageResponse = new PageResponse("success", 1, lfBzAreaReportReadMapper.getBzAreaReportList(req));
        pageResponse.setTotal(lfBzAreaReportReadMapper.getBzAreaReportListCount(req));
        return pageResponse;
    }

}
