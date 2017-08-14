package com.lifang.agentsm.service;

import java.util.List;

import com.lifang.agentsm.model.EstateInfoAboutData;
import com.lifang.model.Response;

public interface ReportService {

    Response getAllData(EstateInfoAboutData req) throws Exception;

    List<EstateInfoAboutData> getAllDataAndNotPage(EstateInfoAboutData req);

}
