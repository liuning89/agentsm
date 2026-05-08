package com.lifang.agentsoa.model;

import java.util.List;

public class AreaOrg {
    private Integer id;
    private String name;
    private Integer cityId;
    private Integer parentId;
    private Integer franchiseeId;
    private Integer level;
    private List<AreaOrg> childs;

    public AreaOrg() {}
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getCityId() { return cityId; }
    public void setCityId(Integer cityId) { this.cityId = cityId; }
    public Integer getParentId() { return parentId; }
    public void setParentId(Integer parentId) { this.parentId = parentId; }
    public Integer getFranchiseeId() { return franchiseeId; }
    public void setFranchiseeId(Integer franchiseeId) { this.franchiseeId = franchiseeId; }
    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }
    public List<AreaOrg> getChilds() { return childs; }
    public void setChilds(List<AreaOrg> childs) { this.childs = childs; }
}
