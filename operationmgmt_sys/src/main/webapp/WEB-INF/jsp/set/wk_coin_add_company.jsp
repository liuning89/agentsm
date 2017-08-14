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
<body style="height:100%;">
<div style="width:100%;">
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:99%;">
	            <tr>     
                     <td id="search">
                       <span>公司：</span>
                   		<input id="companyName" name="companyName" class="mini-textbox" emptyText="请输公司名称" style="width:150px;" onenter="onKeyEnter"/>
                        <a class="mini-button" id="searchBtn" onclick="search()" iconCls="icon-search">查询</a>
                        <a class="mini-button" id="resetBtn" onclick="reset();" iconCls="icon-cancel">重置</a>
                    </td>
                </tr>
            </table>           
        </div>
    </div>
	<div class="mini-fit" style="height:100px">
          <div id="dg" class="mini-datagrid" sizeList="[20,30,50,100]" pageSize=20  style="height:100%;" url="amoutset/getFranchiseeInfoList.action" multiSelect="true" showfooter="false">
              <div property="columns">
                  <div type="checkcolumn" width="7" field="id"></div>
                  <div field="companyName"  align="left" headeralign="left" width="20">公司名称</div>
                  <div field="agentCount"  align="left" headeralign="left" width="20">经纪人人数</div>
              </div>
          </div>
    </div>
	<form id="form" method="post">
        <div style="padding-left:11px; padding-bottom:5px;padding-top:10px;" align="center">
            <table style="table-layout:fixed;padding-top: 5px;">
                   <tr>  
	                  	<td style="width:100px;">赠送悟空币额：</td>
	                    <td>    
	                        <input class="mini-textbox" vtype="int;range:-9999999,9999999" requiredErrorText="请输入-9999999-9999999之间数字" name="coinNum" onblur="onChangeValue" id="coinNum" required="true"  />
	                    </td>
	                   
                    </tr>
                    <tr>
                     <td style="width:10px;"><span style="color: red">总额：</span></td>
	                    <td>    
	                        <input class="mini-textbox" name="totalNum"  id="totalNum" allowInput="false"  />
	                    </td>
	                </tr>
                   <tr >
						<td>赠送原因：</td>
						<td> <input name="giveReason" class="mini-textarea" style="width:300px;height: 50px;" /></td>
					</tr>
            </table>
        </div>
        <div style="text-align:center;">               
            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>       
            <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
        </div>        
    </form>
    <script type="text/javascript">
   	 mini.parse();
   	 var grid = mini.get("dg");
   	 grid.load();
   	 function onChangeValue(e){
   		 debugger;
   		var totalNum = 0;
   		var rows = grid.getSelecteds();
   		var sumAgent = 0;
   		if(rows.length>0){
   			for (var i = 0, l = rows.length; i < l; i++) {
                var r = rows[i];
                if(r.agentCount>0){
                	sumAgent+=r.agentCount;
                }
            }
   			totalNum = parseInt(mini.getbyName("coinNum").getValue())*parseFloat(sumAgent);
   		}
   		mini.getbyName("totalNum").setValue(totalNum);
   	 }
        var form = new mini.Form("form");
        function SaveData() {
        	var rows = grid.getSelecteds();
        	var ids =[];
            if (rows.length>0) {
            	 var ids = [];
                 for (var i = 0, l = rows.length; i < l; i++) {
                     var r = rows[i];
                     if(r.agentCount>0){
	                     ids.push(r.id);
                     }
                 }
            } else {
                mini.alert("请选中至少一条记录");
                return;
            }
            var companyIds = ids.join(',');
            
            if(companyIds.length>0){
	            form.validate();
	            if (form.isValid() == false) return;
	            var o=form.getData();
	            o['companyIds']=companyIds;
	            lc.mask("正在处理中,请稍后...");
	            $.ajax({
	                url: "amoutset/addByCompanySave.do",
					type: 'post',
	                dataType:'json',
	                data: o,
	                success: function (text) {
	                	 lc.unmask();
	                	var o = mini.decode(text);
	                	var status = o['status'];
	                	if(status ==1){
	                		mini.alert("添加成功","提示",function(){
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
	        }else{
	        	 mini.alert("公司无经纪人");
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
        
        function search(){
    		var form = new mini.Form("#search");
    		var data = form.getData(true);
    		lc.removeObjectEmptyValueProperty(data);
    		grid.load(data);
    	}
        function reset(){
    		var form = new mini.Form("#search");
    		form.reset();
    	}
        </script>
</body>
</html>