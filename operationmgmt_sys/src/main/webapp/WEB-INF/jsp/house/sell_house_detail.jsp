<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
<!-- <script src="scripts/zxxFile.js" type="text/javascript"></script> -->
<style>
.detail span{font-size:13px;font-weight:bold;
	color:#000;} 
</style>
</head>
<body style="height: 100%;" path="<%=basePath%>">
	<div style="width: 100%;"></div>
	<!--列表-->
	<form name="house" id="house">
		<div style="float: left; width: 60%; height: 85%;">
			<div class="detail"  style="width: 100%; height:10%;" align="center">
				 <h2 align="left">房源信息:</h2>
			 	<hr style="color:black;background-color:black"/>
			 	<table cellpadding="0" cellspacing="0" border="0" width="80%" height="100%">
			 		<tr>
			 			<td><span>房源状态:</span></td>
			 			<td><c:if test="${info.houseState ==2}">有效</c:if><c:if test="${info.houseState ==4}">无效</c:if></td>
			 			<td><span>区域:</span></td>
			 			<td>${info.district}-${info.town}</td>
			 		</tr>
			 	<tr>
			 			<td><span>户型：</span></td>
			 			<td>${info.bedroomSum}室${info.livingRoomSum}厅${info.wcSum}卫</td>
			 			<td><span>面积：</span></td>
			 			<td> ${info.spaceArea}㎡</td>
			 		</tr>
			 		<tr>
			 			<td><span>总价：</span></td>
			 			<td>${info.sellPrice}万 （到手价） |  ${info.gefuPrice}万（各付价）</td>
			 			<td><span>单价：</span></td>
			 			<td>${info.unitPrice} （到手单价） |  ${info.gefuUnitPrice}（各付单价）</td>
			 		</tr>
			 		<tr>
			 			<td><span>地址：</span></td>
			 			<td>${info.district}${info.town}${info.subEstateInitName }</td>
			 		</tr>
			 	</table>
			</div>
			<div  style="width: 100%; height: 35%;">
				 <h2><span>更多信息：<a class="mini-button" style="margin-left:80%;" iconCls="icon-edit" onclick="editHouse()">编辑</a></span></h2>
			 	<hr style="color:black;background-color:black"/>
			 	<div class="detail"  style="width: 100%; height: 100%;" align="center">
			 	<table  cellpadding="0" cellspacing="0" border="0" width="80%" height="70%">
				 	<tr>
				 			<td><span>朝向：</span></td>
				 			<!-- EAST(1,"EAST","东"),
							SOUT(2,"SOUT","南"),
							WEST(3,"WEST","西"),
							NORTH(4,"NORTH","北"),
							XINAN(5,"XINAN","西南"),
							DONGNAN(6,"DONGNAN","东南"),
							DONGBEI(7,"DONGBEI","东北"),
							XIBEI(8,"XIBEI","西北"); -->
				 			<td>
				 			<c:choose>
						    <c:when test="${info.orientation==1}">
						      	东
						    </c:when>
						   	<c:when test="${info.orientation==2}">
						      	南
						    </c:when>
						    <c:when test="${info.orientation==3}">
						      	 西
						    </c:when>
						    <c:when test="${info.orientation==4}">
						      	北
						    </c:when>
						    <c:when test="${info.orientation==5}">
						      	西南
						    </c:when>
						     <c:when test="${info.orientation==6}">
						      	东南
						    </c:when>
						     <c:when test="${info.orientation==7}">
						      	东北
						    </c:when>
						     <c:when test="${info.orientation==8}">
						      	西北
						    </c:when>
						     <c:when test="${info.orientation==9}">
						      	南北
						    </c:when>
				 			</c:choose>
				 			 </td>
				 			<td><span>楼层：</span></td>
				 			<td> ${info.floor }</td>
				 		</tr>
				 		<tr>
				 			<td><span>类型：</span></td>
	<!-- 			 		
						//'房屋子类型 10 普通住宅，11 新里，12 洋房，13 老公寓，14 老式里弄 
						20 独栋别墅,21 联排别墅,22双拼别墅，23 叠拼别墅，24 其他',
	 -->			 			
				 			<td>
				 			<c:choose>
						    <c:when test="${info.houseChildType==13}">
						      	老公寓
						    </c:when>
						    <c:when test="${info.houseChildType==14}">
						      	老式里弄
						    </c:when>
						    <c:when test="${info.houseChildType==15}">
						      	新公寓
						    </c:when>
						   	<c:when test="${info.houseChildType==16}">
						      	公房
						    </c:when>
						     <c:when test="${info.houseChildType==20}">
						      	独栋别墅
						    </c:when>
						     <c:when test="${info.houseChildType==21}">
						      	联排别墅
						    </c:when>
						     <c:when test="${info.houseChildType==22}">
						      	双拼别墅
						    </c:when>
						     <c:when test="${info.houseChildType==23}">
						      	 叠拼别墅
						    </c:when>
						     <c:when test="${info.houseChildType==24}">
						      	其他
						    </c:when>
						     <c:when test="${info.houseChildType==25}">
						      	新里
						    </c:when>
						     <c:when test="${info.houseChildType==26}">
						      	洋房
						    </c:when>
				 			</c:choose>
				 			 </td>
				 			<td><span>装修：</span></td><!-- //1 空房，2 简装，3 中装，4 精装，5 豪装' -->
				 			<td>
				 			<c:choose>
						    <c:when test="${info.renovation==1}">
						      	毛坯
						    </c:when>
						   	<c:when test="${info.renovation==2}">
						      	简装
						    </c:when>
						    <c:when test="${info.renovation==3}">
						      	 中装
						    </c:when>
						    <c:when test="${info.renovation==4}">
						      	精装
						    </c:when>
						    <c:when test="${info.renovation==5}">
						      	豪装
						    </c:when>
				 			</c:choose>
				 		 </td>
				 		</tr>
				 		<tr>
				 			<td><span>钥匙：</span></td>
				 			<td> <c:if test="${info.isHaveKey  ==1}">钥匙在同业</c:if><c:if test="${info.isHaveKey  ==0}">无钥匙</c:if>
				 			<c:if test="${info.isHaveKey  ==2}"> 借钥匙带看</c:if><c:if test="${info.isHaveKey  ==3}">钥匙在本店</c:if>
				 			</td>
				 			<td><span>年代：</span></td>
				 			<td> <c:if test="${info.completed  =='0'}"></c:if>
				 				<c:if test="${info.completed  !='0'}">${info.completed}</c:if>
				 			</td>
				 		</tr>
				 		<tr>
				 			<td><span>产权：</span></td>	
				 			<td> <c:if test="${info.property  ==1}">产权房</c:if>
				 				<c:if test="${info.property  ==2}">使用权</c:if>
				 				<c:if test="${info.property  ==3}">其他</c:if>
				 			</td>
				 			<td><span>车位：</span></td>
				 			<td> <c:if test="${info.carSpace ==1}">有</c:if><c:if test="${info.carSpace==0}">无</c:if></td>
				 		</tr>
				 		<tr>
				 			<td><span>配套设施:</span></td>
				 			<td colspan="3">
				 				<input id="cbl1" class="mini-checkboxlist" name="chooseId"
							repeatItems="6" repeatLayout="table" textField="text"
							valueField="id" value="${sup}"
							data="chooseList" 
							/>
				 			</td>
				 		</tr>
				 		<tr>
				 			<td><span>标签:</span></td>
				 			<td colspan="3">
				 				<input name="isOnlyOne" class="mini-checkbox" <c:if test="${info.isOnlyOne ==1}">checked="true"</c:if>/>满五唯一
				 				<input name="isFiveYears" class="mini-checkbox" <c:if test="${info.isFiveYears ==1}">checked="true"</c:if>/>满二年
				 			</td>
				 		</tr>
				 		<tr>
				 			<td><span>来源:</span></td>
				 			<!-- 1、搜房、2、安居客、3、利房网、4、上门、5、新浪、6、有房网、7、400电话、8、58同城、
//    9、赶集、10、易居网、11、搜狐二手房、12、微博、13、朋友介绍、14、老客户、15、微信、16、短信、
//    17、公司网站、18、助手、19、派报、20、厨窗、21、陌拜、22、红布条、23、Salescall、24、老客户推荐 -->
				 			<td colspan="3">
			 					<c:choose>
								    <c:when test="${info.source==1}">
								      	搜房
								    </c:when>
								   	<c:when test="${info.source==2}">
								      	安居客
								    </c:when>
								    <c:when test="${info.source==3}">
								      	 利房网
								    </c:when>
								    <c:when test="${info.source==4}">
								      	上门
								    </c:when>
								    <c:when test="${info.source==5}">
								      	新浪
								    </c:when>
								    <c:when test="${info.source==6}">
								      	有房网
								    </c:when>
								   	<c:when test="${info.source==7}">
								      	400电话
								    </c:when>
								    <c:when test="${info.source==8}">
								      	 58同城
								    </c:when>
								    <c:when test="${info.source==9}">
								      	赶集
								    </c:when>
								    <c:when test="${info.source==10}">
								      	易居网
								    </c:when>
								    <c:when test="${info.source==11}">
								      	搜狐二手房
								    </c:when>
								   	<c:when test="${info.source==12}">
								      	微博
								    </c:when>
								    <c:when test="${info.source==13}">
								      	 朋友介绍
								    </c:when>
								    <c:when test="${info.source==14}">
								      	老客户
								    </c:when>
								    <c:when test="${info.source==15}">
								      	微信
								    </c:when>
								    <c:when test="${info.source==16}">
								      	短信
								    </c:when>
								   	<c:when test="${info.source==17}">
								      	公司网站
								    </c:when>
								    <c:when test="${info.source==18}">
								      	 助手
								    </c:when>
								    <c:when test="${info.source==19}">
								      	派报
								    </c:when>
								    <c:when test="${info.source==20}">
								      	厨窗
								    </c:when>
								      <c:when test="${info.source==21}">
								      	陌拜
								    </c:when>
								   	<c:when test="${info.source==22}">
								      	红布条
								    </c:when>
								    <c:when test="${info.source==23}">
								      	 Salescall
								    </c:when>
								    <c:when test="${info.source==24}">
								      	老客户推荐
								    </c:when>
					 			</c:choose>
				 			</td>
				 		</tr>
			 	</table>
			 
				</div>
			</div>
			<div  style="width: 100%; height: 5%;" class="detail">
				 <h2>无效原因</h2>
			 	<hr style="color:black;background-color:black"/>
			 	<c:if test="${reason!=null }">
					 <c:forEach var="item" items="${reason }">
				 		<div><span>无效原因：</span>${item.reason }</div>
				 		<div><span>备注:</span>${item.memo }</div>
					 </c:forEach>
				 </c:if>
			</div>
			<div  style="width: 100%; height: 5%;">
				 <h2>房源卖点：</h2>
			 	<hr style="color:black;background-color:black"/>
			 	<div>${info.sellPoint }</div>
			</div>
		</div>
		</form>
		<div style="float: left;margin-top: 45px;"><hr width="1"size="900">
		</div>
		<div style="width: 34%; height: 90%; float: left;">
			<div  style="width: 100%; height: 40%;" >
				 <h2><span>房东信息：</span></h2>
			 	<hr style="color:black;background-color:black"/>
			 	 <div style="margin-left: 20px;width:100%;">
			        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
			            <table style="width:99%;">
			                <tr>
				                <td>
				                        <a class="mini-button" iconCls="icon-add" onclick="add()">增加</a>
				                 </td> 
				                </tr>
			            </table>           
			        </div>
			    </div>
			 	 <div id="contactgrid" class="mini-datagrid"  showPager="false" style="width:100%;height: 40%;margin-left: 20px;" url="houseResource/sell/houseContactData.do?houseId=${houseId}">
	                    <div property="columns">
	                    	<div field="contactId" name="contactId" width="50">contactID</div>
	                    	<div field="companyname" width="50">公司</div>
	                        <div field="hostName" width="50">联系人</div>
	                        <div name="hostMobile" field="hostMobile" width="100" allowSort="true" align="center"
							headerAlign="center">电话</div>
							 <div name="action" width="100" headerAlign="center">操作</div>
	                    </div>
	                </div>
			</div>
			<div  style="width: 100%; height: 75%;">
				 <h2><span>业绩归属人：</span></h2>
			 	<hr style="color:black;background-color:black"/>
			 	<!--  <div style="margin-left: 20px;width:100%;">
			        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
			            <table style="width:99%;">
			                <tr>
				                <td>
				                        <a class="mini-button" iconCls="icon-add" onclick="addOwn()">增加</a>
				                 </td> 
				                </tr>
			            </table>           
			        </div>
			    </div> -->
			 	 <div id="yjgsr" class="mini-datagrid" style="width:100%;height:75%;margin-left: 20px;"                     
	                    showPager="false" url="houseResource/sell/achievementOwnList.do?houseId=${houseId }">
	                    <div property="columns">
	                         <div header="publishagent" name="publishagent" headerAlign="center" field="publishagent" width="50"></div>
	                         <div header="id" name="id" headerAlign="center" field="id" width="50"></div>
            	            <div header="pub" name="pub" headerAlign="center" field="pub" width="50"></div>
            	            <div  header="公司" headerAlign="center" field="abbreviation" width="30"></div>
	                        <div header="业绩人类型" headerAlign="center" field="name" width="30"></div>
	                        <div header="名称" headerAlign="center" field="realName" width="30"></div>
	                         <div header="门店" headerAlign="center" field="storeName" width="30"></div>
	                       <!--  <div field="createTime" width="50" allowSort="true" align="center"
							headerAlign="center" dateFormat="yyyy-MM-dd HH:mm" >时间</div> -->
							 <div name="action" width="50" headerAlign="center">操作</div>
	                    </div>
	                </div>
			</div>
		</div>
</body>
<script type="text/javascript">
	var chooseList = [ {
		id : 1,
		text : '床'
	}, {
		id : 2,
		text : '沙发'
	}, {
		id : 3,
		text : '桌子'
	}, {
		id : 4,
		text : '电视机'
	}, {
		id : 5,
		text : '宽带'
	},  {
		id : 6,
		text : '空调'
	} ,{
		id : 7,
		text : '洗衣机'
	} , {
		id : 8,
		text : '衣柜'
	} , {
		id : 9,
		text : '油烟机'
	} , {
		id :10 ,
		text : '热水器'
	}  ];
	mini.parse();
	var form = new mini.Form("house");
	var flag;
	var mobile;
	var grid = mini.get("yjgsr");
	var contactgrid = mini.get("contactgrid");
	contactgrid.load({'flag':0});
	contactgrid.hideColumn("contactId");
	grid.load();
	grid.hideColumn("pub");
	grid.hideColumn("id");
	grid.hideColumn("publishagent");
	grid.on("drawcell", function(e) {
			var record = e.record, field = e.field, value = e.value, column = e.column;
			if (column.name == "action") {
				var html='';
				if(record.pub==1){
					flag='publishAgent';
				}else if(record.pub==4){
					flag='keyAgent';
				}else if(record.pub==3){
					flag='commAgent';
				}else if(record.pub==5){
					flag='pictureAgent';
				}
				
				if(record.publishagent==0){
					name='新增';
				}else{
					name='编辑';
				}
				if(record.pub==5){
					if(record.publishagent==0){
						html = lc.strFormat('<a class="mini-button" style="margin-left:5px;padding-left:10px;padding-right:10px;" onclick="edit(\'{0}\',{1},{2},{3})">增加</a>',flag,record.publishagent,record.id,record.franchiseeId);
					}else{
						html = lc.strFormat('<a class="mini-button" style="margin-left:5px;padding-left:10px;padding-right:10px;" onclick="deleteAgent(\'{0}\',{2})">删除</a>',flag,record.publishagent,record.id,record.franchiseeId);
					}
				}else if(record.pub==1 && record.publishagent!=0){
					html=lc.strFormat('<a class="mini-button" style="margin-left:5px;padding-left:10px;padding-right:10px;" onclick="edit(\'{0}\',{1},{2},{3})">编辑</a>',flag,record.publishagent,record.id,record.franchiseeId);
				}else if((record.pub==3||record.pub==4) && record.publishagent!=0){
					html=lc.strFormat('<a class="mini-button" style="margin-left:5px;padding-left:10px;padding-right:10px;" onclick="deleteAgent(\'{0}\',{2})">删除</a>',flag,record.publishagent,record.id,record.franchiseeId);
				}
				e.cellHtml = html;

			}
		});
	contactgrid.on("drawcell", function(e) {
		var record = e.record, field = e.field, value = e.value, column = e.column;
		if (column.name == "action") {
			html = lc.strFormat('<a class="mini-button" style="margin-left:5px;padding-left:10px;padding-right:10px;" onclick="editContact(\'{0}\',\'{1}\',\'{2}\')">编辑</a><a class="mini-button" style="margin-left:5px;padding-left:10px;padding-right:10px;" onclick="deleteContact(\'{0}\')">删除</a>',record.contactId,record.hostName,record.hostMobile);
			e.cellHtml = html;
		}else if(column.name=="hostMobile"){
			html = lc.strFormat('<img src="imgs/contact.png" id="img"  alt="点击" onclick="lookContact(\'{0}\',this)" />'+e.value,record.contactId);
			e.cellHtml = html;
		}
	});
	
	function lookContact(contactId,val){
		debugger;
		var v = $(val).parent().parent();
		console.info(v[0].id);
		$.ajax({
			url: "houseResource/sell/getHouseContactDataByContactId.do?contactId="+contactId,
			type: 'post',
			cache: false,
			success: function (text) {
				document.getElementById(v[0].id).innerHTML = text.hostMobile;
		/*		if(text.length>0){
					for(var i=0;i<text.length;i++){
						if(text[i].contactId==contactId){
							$(val).contactId=contactId;
							$(val).remove();
					        $.ajax({
								url: "houseResource/sell/saveHouseContactLog.do?houseId=${houseId}&hostMobile="+text[i].hostMobile,
								type: 'post',
					            cache: false,
					            success: function (text) {
					            },
					            error: function (jqXHR, textStatus, errorThrown) {
					                 mini.alert(jqXHR.responseText);
					             }
					         });
						}
					}
				}*/
			},
			error: function (jqXHR, textStatus, errorThrown) {
				mini.alert(jqXHR.responseText);
			}
		});
		
	}
	 function deleteAgent(flag,id) {
         mini.confirm("确定删除记录？", "提示",
             function (action) {
                 if (action == "ok") {
                     $.ajax({
 						url: "houseResource/sell/deleteAgent.do?houseId=${houseId}&houseAgentId=0"+"&flag="+flag+"&id="+id,
         				type: 'post',
                        cache: false,
                        success: function (text) {
	                       	if(text.message=='success'){
	                                mini.alert("删除成功！");
	                                grid.reload();
	                       	}else{
	                       		mini.alert(text.data);
	                       	}
	                        },
                         error: function (jqXHR, textStatus, errorThrown) {
                             mini.alert(jqXHR.responseText);
                         }
                     });
                 } 
             }
           );
       } 
	 function deleteContact(contactId) {
         mini.confirm("确定删除记录？", "提示",
             function (action) {
                 if (action == "ok") {
                     $.ajax({
 						url: "houseResource/sell/deleteContact.do?contactId="+contactId,
         				type: 'post',
                        cache: false,
                        success: function (text) {
	                       	if(text.status==1){
	                                mini.alert("删除成功！");
	                                contactgrid.reload();
	                       	}
	                     },
	                     error: function (jqXHR, textStatus, errorThrown) {
                             mini.alert(jqXHR.responseText);
                         }
                     });
                 } 
             }
           );
       } 
	function edit(flag,storeid,id,franchiseeId) {
        mini.open({
            url: "houseResource/sell/editPage.do?houseId=${houseId}&flag="+flag+"&storeId="+storeid+"&id="+id+"&franchiseeId="+franchiseeId,
            title: "编辑",
            width: 400, 
            height:400,
            ondestroy: function (action) {
            	if(action=='save'){
            		grid.reload();
            	};
            },
            onload: function () {
	            var iframe = this.getIFrameEl();
	        }
        });
	} 
	function editContact(contactId,hostName,hostMobile) {
        mini.open({
            url: 'houseResource/sell/editContactPage.do?contactId='+contactId+'&hostName='+hostName+'&hostMobile='+hostMobile,
            title: "编辑",
            width: 400, 
            height:400,
            ondestroy: function (action) {
            	if(action=='save'){
	            	contactgrid.reload();
            	}
            		
            },
            onload: function () {
	            var iframe = this.getIFrameEl();
	        }
        });
	}
	function editHouse() {
        mini.open({
            url: 'houseResource/sell/editHousePage.do?houseId=${houseId}',
            title: "编辑",
            width: 500, 
            height:500,
            ondestroy: function (action) {
            	location.reload();
            },
            onload: function () {
	            var iframe = this.getIFrameEl();
	        }
        });
	}
	function add() {
        mini.open({
            url: "houseResource/sell/addContactPage.do?houseId=${houseId}&cityId=${cityId}",
            title: "增加",
            width: 400, 
            height:400,
            ondestroy: function (action) {
            	contactgrid.reload();
            },
            onload: function () {
	            var iframe = this.getIFrameEl();
	        }
        });
	}
	//往业绩归属人表中增加一条记录
	function addOwn(){
		 mini.confirm("确定增加记录？", "提示",
	             function (action) {
	                 if (action == "ok") {
	                     $.ajax({
	 						url: "houseResource/sell/addOwn.do?houseId=${houseId}",
	         				type: 'post',
	                        cache: false,
	                        success: function (text) {
		                       	if(text.status==1){
		                                mini.alert(text.data);
		                                grid.reload();
		                       	}
		                     },
		                     error: function (jqXHR, textStatus, errorThrown) {
	                             mini.alert(jqXHR.responseText);
	                         }
	                     });
	                 } 
	             }
	           );
	}
</script>
</html>
