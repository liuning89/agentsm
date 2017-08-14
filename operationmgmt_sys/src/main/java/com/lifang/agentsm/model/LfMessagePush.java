package com.lifang.agentsm.model;

import java.util.Date;
import lombok.Data;

@Data
public class LfMessagePush {
    private int id;
    private String title;
    private String pushcontent;
    private Date createTime;
    private Date updateTime;
    private String pushtime;
    private int istiming;
    private int pushstatus;
    private int isdelete;
    private int createUserId;
    private int pushUserId;
    private int iosVersion;
    private int pushPlatform;


}
