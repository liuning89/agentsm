<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
  <%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head path="<%=basePath%>">
    <base href="<%=basePath%>" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
<title></title>
</head>
<body>
	<form id="form" method="post">
        <div style="padding-left:11px; padding-bottom:5px;padding-top:10px;" align="center">
            <table style="table-layout:fixed;padding-top: 5px;">
                   <tr>  
	                    <td style="width:70px;">朝向：</td>
	                    <td style="width:150px;">    
	                       <input id="orientation" class="mini-combobox" name="orientation"
								 textField="text" valueField="id" value="${info.orientation }"
								data="[{id:1,text:'东'},{id:2,text:'南'},{id:3,text:'西'},{id:4,text:'北'},{id:5,text:'西南'},{id:6,text:'东南'},{id:7,text:'东北'},{id:8,text:'西北'},{id:9,text:'南北'}]"
								/>
	                    </td>
	                  	<td style="width:70px;">楼层：</td>
	                    <td>    
	                        <input class="mini-textbox" vtype="int" name="floor" id="floor" value="${info.floor }" />
	                    </td>
                    </tr>
                    <tr>
	                    <td>类型：</td>
	                    <td >  
	                   		<input id="htype" class="mini-combobox" name="htype" onvaluechanged="getChildType"
								 textField="text" valueField="id"  value="${info.htype }"
								data="[{id:1,text:'公寓'},{id:2,text:'别墅'}]"
								/>	   
 							<input id="houseChildType" class="mini-combobox" name="houseChildType"
								 textField="text" valueField="id"  value="${info.houseChildType }"
								/>	                    
						</td>
	                    <td>装修：</td>
	                    <td>    
							 <input id="renovation" class="mini-combobox" name="renovation"
							 textField="text" valueField="id"  value="${info.renovation }"
							data="[{id:1,text:'毛坯'},{id:2,text:'简装'},{id:3,text:'中装'},{id:4,text:'精装'},{id:5,text:'豪装'}]"
							/>	                   
						 </td>
	                </tr>
	                <tr>
	                    <td>钥匙：</td>
	                    <td >
 							<input id="isHaveKey" class="mini-combobox" name="isHaveKey"
								 textField="text" valueField="id"  value="${info.isHaveKey }"
								data="[{id:1,text:'钥匙在同业'},{id:2,text:'借钥匙带看'},{id:3,text:'钥匙在本店'},{id:0,text:'无钥匙'}]"
								/>	           
						</td>
	                    <td>年代：</td>
	                    <td>    
	                        <input class="mini-textbox" vtype="int" name="completed" id="completed"  value="${info.completed }"/>
	                    </td>
	                </tr>
	                <tr>
	                    <td>产权：</td>
	                    <td>
	                  	  <input id="property" class="mini-combobox" name="property"
								 textField="text" valueField="id" value="${info.property }"
								data="[{id:1,text:'产权房'},{id:2,text:'使用权'},{id:3,text:'其他'}]"
								/>	  
	                    </td>
	                    <td>车位：</td>
	                    <td>    
							<input id="carSpace" class="mini-combobox" name="carSpace"
								 textField="text" valueField="id" value="${info.carSpace }"
								data="[{id:0,text:'无'},{id:1,text:'有'}]"
								/>	   	                    
						</td>
	                </tr>
	                <tr>
		                 <td>到手价：</td>
		                 <td>    
		                      <input class="mini-textbox" vtype="float" name="sellPrice" id="sellPrice"  value="${info.sellPrice }"/>
		                 </td>
		                <td><span>各付总价:</span></td>
			 			<td>
			 				<input class="mini-textbox" vtype="float" name="gefuPrice" id="gefuPrice"  value="${info.gefuPrice }"/>
			 			</td>
	                </tr>
	                <tr>
		                 <td>面积：</td>
		                 <td>    
		                      <input class="mini-textbox" vtype="float" name="spaceArea" id="spaceArea"  value="${info.spaceArea }"/>
		                 </td>
		                <td><span>来源:</span></td>
			 			<td>
			 				<input id="source" class="mini-combobox" name="source"
								 textField="text" valueField="id" value="${info.source }"
								data="sourceData"
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
		                <td><span>配套设施:</span></td>
					 			<td colspan="3">
					 				<input id="houseSupporting" class="mini-checkboxlist" name="houseSupporting"
								repeatItems="6" repeatLayout="table" textField="text" value="${sup}"
								valueField="id"
								data="chooseList" 
								/>
			 			</td>
	                </tr>
	                <tr>
		                <td><span>房源卖点:</span></td>
			 			<td colspan="3">
			 				<textarea width="330" height="100" class="mini-textarea" name="sellPoint">${info.sellPoint }</textarea>
			 			</td>
	                </tr>
            </table>
        </div>
        <div style="text-align:center;margin-top: 50px;">               
            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>       
            <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
        </div>        
    </form>
    <script type="text/javascript">
    var sourceData=[{id:0,text:'无'},
                    {id:1,text:'搜房'},
                    {id:2,text:'安居客'},
                    {id:3,text:'利房网'},
                    {id:4,text:'上门'},
                    {id:5,text:'新浪'},
                    {id:6,text:'有房网'},
                    {id:7,text:'400电话'},
                    {id:8,text:'58同城'},
                    {id:9,text:'赶集'},
                    {id:10,text:'易居网'},
                    {id:11,text:'搜狐二手房'},
                    {id:12,text:'微博'},
                    {id:13,text:'朋友介绍'},
                    {id:14,text:'老客户'},
                    {id:15,text:'微信'},
                    {id:16,text:'短信'},
                    {id:17,text:'公司网站'},
                    {id:18,text:'助手'},
                    {id:19,text:'派报'},
                    {id:20,text:'厨窗'},
                    {id:21,text:'陌拜'},
                    {id:22,text:'红布条'},
                    {id:23,text:'Salescall'},
                    {id:24,text:'老客户推荐'}];
    var chooseList = [{
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
		id : 10,
		text : '热水器'
	}  ];
    mini.parse();
    debugger;
	var cType=mini.get("houseChildType");
	var htype=mini.get("htype");
    var data1=[{id:13,text:'老公寓'},{id:14,text:'老式里弄'},{id:15,text:'新公寓'},{id:16,text:'公房'}];
    var data2=[{id:20,text:'独栋别墅'},{id:21,text:'联排别墅'},{id:22,text:'双拼别墅'},{id:23,text:'叠拼别墅'},{id:24,text:'其他'},{id:25,text:'新里'},{id:26,text:'洋房'}];
    if('${info.htype}'==1){
    	cType.setData(data1);
    }else{
    	cType.setData(data2);
    }
        var form = new mini.Form("form");
        
        function SaveData() {
            form.validate();
            if (form.isValid() == false) return;
            var o=form.getData();
            o['houseId']='${houseId}';
            $.ajax({
                url: "houseResource/sell/saveHouse.do",
				type: 'post',
                dataType:'json',
                data: o,
                success: function (text) {
                	var o = mini.decode(text);
                	var status = o['status'];
                	if(status ==1){
                		mini.alert("更新成功","提示",function(){
                			CloseWindow("save");
                		});
                	}else{
                		mini.alert(o['message']);	
                	}
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                    CloseWindow();
                }
            });
        }
        
        function getChildType(e) {
            var id = htype.getValue();
            if(id==1){
            	cType.setData(data1);
            }else{
            	cType.setData(data2);
            }
        }
        function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        
        function onOk(e) {
            SaveData();
        }
        
        function onCancel(e) {
            CloseWindow("cancel");
        }
        
       
        </script>
</body>
</html>