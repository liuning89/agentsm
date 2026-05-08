package com.lifang.agentsoa.model;

import java.util.List;
import java.util.Map;

public class AreaOrgModel {
    private List<AreaOrg> cityAreaOrgs;
    private Map<Integer, List<AreaOrg>> map;

    public AreaOrgModel() {}
    public List<AreaOrg> getCityAreaOrgs() { return cityAreaOrgs; }
    public void setCityAreaOrgs(List<AreaOrg> cityAreaOrgs) { this.cityAreaOrgs = cityAreaOrgs; }
    public Map<Integer, List<AreaOrg>> getMap() { return map; }
    public void setMap(Map<Integer, List<AreaOrg>> map) { this.map = map; }
    public Map<Integer, List<AreaOrg>> getModelMap() { return map; }
    public void setModelMap(Map<Integer, List<AreaOrg>> modelMap) { this.map = modelMap; }
}
