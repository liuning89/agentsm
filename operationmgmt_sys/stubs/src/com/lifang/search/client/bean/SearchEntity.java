package com.lifang.search.client.bean;

public class SearchEntity {
    private String estatename;
    private String subestatename;
    private Integer subestateid;

    public SearchEntity() {}
    public String getEstatename() { return estatename; }
    public void setEstatename(String estatename) { this.estatename = estatename; }
    public String getSubestatename() { return subestatename; }
    public void setSubestatename(String subestatename) { this.subestatename = subestatename; }
    public Integer getSubestateid() { return subestateid; }
    public void setSubestateid(Integer subestateid) { this.subestateid = subestateid; }
}
