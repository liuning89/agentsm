package com.lifang.sso.entity;

import java.util.List;

public class SsoPositionMenu {
    private Integer id;
    private String name;
    private String url;
    private Integer parentId;
    private List<SsoPositionMenu> childMenu;

    public SsoPositionMenu() {}
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public Integer getParentId() { return parentId; }
    public void setParentId(Integer parentId) { this.parentId = parentId; }
    public List<SsoPositionMenu> getChildMenu() { return childMenu; }
    public void setChildMenu(List<SsoPositionMenu> childMenu) { this.childMenu = childMenu; }
}
