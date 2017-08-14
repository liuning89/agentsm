package com.lifang.agentsm.model.enums;

import com.lifang.agentsm.utils.NumberUtils;

/**
 * 审核失败 类型
 *
 * 1. 不符合要求（租金低于6000/租期超1月/办公楼/商铺/合租/群租/租售搞错）
 * 2. 中介号码
 * 3. 已租/不租
 * 4. 已售/不售
 * 5. 房源信息错误，小区名错误
 * 6. 房源信息错误，楼号错误
 * 7. 房源信息错误，室号错误
 * 8. 停机/空号/错号
 * 9. 无法联系/无人接听/无法接通/关机/来电提醒/再联系/不配合       ###第一至第五轮无该选项###
 * @author administrator
 *
 */

public enum CheckFaildTypeEnum {
    UNUSED(0, "--"),
    DISCREPANCY(1,"不符合要求（租金低于6000/租期超1月/办公楼/商铺/合租/群租/租售搞错）"),
    AGENT(2,"中介号码"),
    NO_RENT(3,"已租/不租"),
    NO_SELL(4,"已售/不售"),
    ESTATE_ERROR(5,"房源信息错误，小区名错误"),
    BUILDING_ERROR(6,"房源信息错误，楼号错误"),
    ROOM_ERROR(7,"房源信息错误，室号错误"),
    COLSED(8,"停机/空号/错号"),
    CANNOT_CONNECT(9, "无法联系/无人接听/无法接通/关机/来电提醒/再联系/不配合");

    private int value;
    private String desc;

    private CheckFaildTypeEnum(int value , String desc){
        this.value = value;
        this.desc = desc;
    }
    public static CheckFaildTypeEnum getByValue(int value) {
        for (CheckFaildTypeEnum ve : values()) {
            if (NumberUtils.isEqual(value, ve.value)) {
                return ve;
            }
        }
        return UNUSED;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

}
