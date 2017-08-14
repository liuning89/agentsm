<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<html>
<head path="<%=basePath%>">
<base href="<%=basePath%>" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
<!-- <script src="scripts/zxxFile.js" type="text/javascript"></script> -->
<style type="text/css">
.mini-radiobuttonlist input {
overflow: hidden;
vertical-align: middle;
margin-left: 40px;
}
</style>
</head>
<body style="height: 100%;" path="<%=basePath%>">
	<div style="width: 100%;"></div>
	<!--列表-->
		<div style="float: left; width: 60%; height: 95%;">
			<div  style="width: 100%; height: 60%;">
				 <h2><span>房源信息：</span></h2>
			 	<hr style="color:black;background-color:black"/>
			 	 <table>
            
            <tr>
                <td>
                    <label for="textbox1$text">房源状态：</label>
                </td>
                <td>
                	有效
                </td>
                <td>
                    <label for="textbox1$text">区域：</label>
                </td>
                <td>
                	黄浦区人民广场
                </td>
            </tr>
            <tr>
                <td>
                    <label for="pwd1$text">户型：</label>
                </td>
                <td>
                    2室1厅
                </td>
                <td>
                    <label for="pwd1$text">面积：</label>
                </td>
                <td>
                    70㎡
                </td>
            </tr>
            <tr>
                <td>
                    <label for="textarea1$text">总价：</label>
                </td>
                <td>
					100万
                </td>
                <td>
                    <label for="textarea1$text">单价：</label>
                </td>
                <td>
                    42857 元/㎡
                </td>
            </tr>
            <tr>
                <td>                   
                    <label for="date1$text">地址：</label>
                </td>
                <td colspan="3">
                	sdffffffffffffffffffffffffffffffffffffffffffff
                </td>
               
            </tr>
            <tr>
                <td>
                    	朝向：
                </td>
                <td>
                  		 －－
                </td>
                <td>
                    	楼层：
                </td>
                <td>
                  		 －－
                </td>
            </tr>
             <tr>
                 <td>
                    	类型：
                </td>
                <td>
                  		 －－
                </td>
                <td>
                    	装修：
                </td>
                <td>
                  		 －－
                </td>
            </tr>
            <tr>
                <td>
                    	钥匙：
                </td>
                <td>
                  		 －－
                </td>
                <td>
                    	年代：
                </td>
                <td>
                  		 －－
                </td>
            </tr>
            <tr>
                <td>
                    	产权：
                </td>
                <td>
                  		 －－
                </td>
                <td>
                    	车位：
                </td>
                <td>
                  		 －－
                </td>
            </tr>
            </table>
			</div>
			<div  style="width: 100%; height: 15%;">
				 <h2><span>配套信息：</span></h2>
			 	<hr style="color:black;background-color:black"/>
			 	<div id="cbl1" class="mini-checkboxlist" name="chooseId"
							repeatItems="6" repeatLayout="table" textField="text"
							valueField="id" value="${SubestateCompare.chooseId}"
							data="chooseList" required="true"
							onvalidation="chooseIdValidation" style="height:120px;"></div>
			</div>
			<div  style="width: 100%; height: 15%;">
				 <h2><span>房源卖点：</span></h2>
			 	<hr style="color:black;background-color:black"/>
			 	<div>姜涛和鲁朋飞生前系海军航空兵学院某飞行训练基地飞行教员和飞行学员，他们在一次飞行训练中，
			 	      面对所驾单发飞机的发动机突然发生空中起火、动力迅速下降的重大险情，果断驾机成功避开人口密集区域后，
			 	      终因高度过低处置时间短促，无法跳伞，壮烈牺牲。</div>
			</div>
		</div>
		<div style="float: left;margin-top: 45px;"><hr width="1"size="1000">
		</div>
		<div style="width: 30%; height: 95%; float: left;">
			<div  style="width: 100%; height: 40%;" >
				 <h2><span>房东信息：</span></h2>
			 	<hr style="color:black;background-color:black"/>
			 	 <div id="mainhousegrid" class="mini-datagrid" style="width:300px;height:200px;margin-left: 20px;"                     
	                    idField="id"  multiSelect="true" showPager="false" url="estatebuild/houseList.do?buildingId=${mainBuildingId}"
	                    allowCellEdit="true" allowCellSelect="true">
	                    <div property="columns">
	                        <div  field=buildingId width="50">房东姓名</div>
	                        <div field="" width="100" allowSort="true" align="center"
							headerAlign="center">房东电话</div>
	                    </div>
	                </div>
			</div>
			<div  style="width: 100%; height: 55%;">
				 <h2><span>业绩归属人：</span></h2>
			 	<hr style="color:black;background-color:black"/>
			 	 <div id="yjgsr" class="mini-datagrid" style="width:400px;height:400px;margin-left: 20px;"                     
	                    idField="id"  multiSelect="true" showPager="false" url="estatebuild/houseList.do?buildingId=${mainBuildingId}"
	                    allowCellEdit="true" allowCellSelect="true">
	                    <div property="columns">
	                        <div header="发布人" headerAlign="center" field=buildingId width="50"></div>
	                        <div field="" width="100" allowSort="true" align="center"
							headerAlign="center">发布时间</div>
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
	}, {
		id : 6,
		text : '空调'
	} , {
		id : 6,
		text : '洗衣机'
	} , {
		id : 6,
		text : '衣柜'
	}, {
		id : 6,
		text : '油烟机'
	} , {
		id : 6,
		text : '热水器'
	}  ];
	mini.parse();
	var grid = mini.get("yjgsr");
	 grid.on("drawcell", function(e) {
			var record = e.record, field = e.field, value = e.value, column = e.column;
			if (column.name == "action") {
				var html = lc.strFormat('<a class="mini-button" style="padding-left:10px;padding-right:10px;" onclick="lock({0},{1});">'+
								name+'</a><a class="mini-button" style="margin-left:5px;padding-left:10px;padding-right:10px;" onclick="openContour({0},\'{2}\',{3})">打点</a>'+
								'<a class="mini-button" style="margin-left:5px;padding-left:10px;padding-right:10px;" onclick="getHouseRoomList({0})">查看室号</a>'+
								'<a class="mini-button" style="margin-left:5px;padding-left:10px;padding-right:10px;" onclick="addHouseRoom({0}, \'{4}\', \'{5}\')">添加室号</a>'
								,record.id,record.lockStatus,record.buildingName,record.subestateId, record.estateName, record.buildingNumber);
				e.cellHtml = html;
			}
		});
	 
</script>
</html>
