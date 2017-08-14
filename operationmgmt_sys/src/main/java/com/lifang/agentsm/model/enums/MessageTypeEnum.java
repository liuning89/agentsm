package com.lifang.agentsm.model.enums;

/**
 * Created by Administrator on 15-6-19.
 */
public enum MessageTypeEnum {

    HOUSE_RESOURSE_INVALID(0,"unReadInvalidMsgCount","房源失效"),
    AUDIT(1,"unReadAuditMsgCount","审核"),
    SEE(2,"unReadSeeMsgCount","约看"),
    EVALUATE(3,"unReadEvaluateMsgCount","评价"),
    NOTIFICATION(4,"unReadNoticicationMsgCount","通知");

    private Integer code;
    private String name;
    private String desc;

    public static String getNameByCode(int key){
        switch (key){
            case 0 : return HOUSE_RESOURSE_INVALID.getName();
            case 1 : return AUDIT.getName();
            case 2 : return SEE.getName();
            case 3 : return EVALUATE.getName();
            case 4 : return NOTIFICATION.getName();
            default : return "未知状态";
        }
    }

    private MessageTypeEnum(Integer code, String name, String desc){
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
