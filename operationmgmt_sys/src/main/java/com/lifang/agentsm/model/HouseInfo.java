package com.lifang.agentsm.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class HouseInfo {

	private Integer houseId;
	/** 1公寓，2别墅*/
	private Byte htype;
	/** 房屋子类型 10 普通住宅，11 新里，12 洋房，13 老公寓，14 老式里弄 20 独栋别墅,
	 * 	21 联排别墅,22双拼别墅，23 叠拼别墅，24 其他  */
	private Integer houseChildType;
	/** 小区编号*/
	private Integer estateId;
	/** 小区名称*/
	private String estateName;
	/** 板块id*/
	private Integer townid;
	/** 板块*/
	private String town;
	/** 子小区编号*/
	private Integer subEstateId;
	/** 子小区名称*/
	private String subEstateName;
	/** 楼栋编号*/
	private Integer buildingId;
	/** 楼栋名称*/
	private String buildingName;
	/** 占地面积*/
	private Integer coveredArea;
	/** 使用面积*/
	private Double spaceArea;
	/** 产权年限*/
	private String propertyRight;
	/** 楼层*/
	private Integer floor;
	/** 室号*/
	private String room;
	/** 朝向*/
	private Integer orientation;
	/** 阳台*/
	private Integer balconySum;
	/** 卧室*/
	private Integer bedRoomSum;
	/** 客厅*/
	private Integer livingRoomSum;
	/** 卫生间*/
	private Integer wcSum;
	/** 南北通透：0否，1是*/
	private Byte southToNorth;
	/** 景观房，1是，0否*/
	private Byte isLandscape;
	/** 一楼带花园，1是，0否*/
	private Byte isWithGarden;
	/** 有效图片数*/
	private Integer picNum;
	/** 地下室面积*/
	private Double basementArea;
	/** 花园面积*/
	private String gardenArea;
	/** 1 英伦，2 西班牙式，3 文艺复兴式，4 地中海式，5其他*/
	private Byte houseStyle;
	/** 经纪人期待上传图片总数*/
	private Integer hopePicNum;
	/** 经纪人实际上传图片总数*/
	private Integer actualPicNum;
	/** 标签*/
	private String sets;
	private Date createTime;
	private Date updateTime;
	private Integer status;
	private String sourceId;
	
}
