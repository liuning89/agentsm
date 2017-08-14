package com.lifang.agentsm.dao.read;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.model.EstateInfoAboutData;


public interface ReportReadMapper {

    List<EstateInfoAboutData> getEstateAndDic(Pagination pagination);

    List<EstateInfoAboutData> getyxkcNum( Map paraMap);

    List<EstateInfoAboutData> getllNum( Map paraMap);

    List<EstateInfoAboutData> getsjNum( Map paraMap);

    List<EstateInfoAboutData> getsxNum( Map paraMap);

    List<EstateInfoAboutData> getDtNum( Map paraMap);

    List<EstateInfoAboutData> getysNum( Map paraMap);

    List<EstateInfoAboutData> getMsNum( Map paraMap);

    List<EstateInfoAboutData> getOpenNum( Map paraMap);

    List<EstateInfoAboutData> getDkNum( Map paraMap);

    List<EstateInfoAboutData> getGjNum( Map paraMap);

    List<EstateInfoAboutData> getScNum( Map paraMap);
    

    List<EstateInfoAboutData> getEstateAndDic(Map paraMap);

    

}
