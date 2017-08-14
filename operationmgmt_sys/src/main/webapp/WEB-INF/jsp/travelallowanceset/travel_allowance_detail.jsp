<%@page import="com.lifang.agentsm.entity.LfEmployee"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String houseId = request.getParameter("houseId");

%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <title>售房列表查询</title>
    <script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
</head>
<body style="height:100%;">
    <!--列表-->
    
    <div class="mini-fit" style="height:400px">
          <div id="dg" class="mini-datagrid"  allowCellEdit="true" allowCellSelect="true" multiSelect="true"
        allowCellValid="true"  sizeList="[20,30,50,100]" pageSize=20 showPager="false" style="height:100%;" url="travelallow/getDetailList.action?id=${id}" multiSelect="true" showfooter="false">
              <div property="columns">
                  <div type="id" width="10"></div>
                  <div field="typeName"  align="left" headeralign="left" width="30">行程名</div>
                   <div field="wkCoin" vtype="required;int" width="100" allowSort="true" >悟空币额
                		<input property="editor"  class="mini-spinner" minValue="0" maxValue="9999999" value="25" style="width:100%;"/>
            		</div>
                  <div field="status"   align="left" headeralign="left" width="20">状态</div>
                  <div name="action" headeralign="left"  align="left" width="40">操作</div>
              </div>
          </div>
    </div>
    <div style="text-align:center;margin-top: 50px;">               
            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">保存</a>       
            <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
    </div>
</body>
<script type="text/javascript">
	mini.parse();
	var grid = mini.get("dg");
	grid.load();
	grid.on("drawcell", function(e) {
		var record = e.record, field = e.field, value = e.value, column = e.column;
		if (column.name == "action") {
			var str='';
			if(record.status==1){
				str="关闭";
			}else{
				str="开启";
			}
			var html = lc.strFormat(
					'<a class="mini-button" style="padding-left:10px;padding-right:10px;" onclick="updateDetailStatus({0},{1})">{2}</a>',
					record.id,record.status,str);
			e.cellHtml = html;
		}else if(field=='status'){
			if(value == "0"){
				e.cellHtml = "关闭";
			}else{
				e.cellHtml="开启";
			}
		}
	});
	
	 function onCellValidation(e) {
      
         if (e.field == "wkCoin") {
             if (e.value == null||e.value=='') {
                 e.isValid = false;
                 e.errorText = "不能为空";
             }
         }
     }
	 function CloseWindow(action) {            
         if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
         else window.close();            
     }
     
     function onOk(e) {
    	 saveValue();
     }
     
     function onCancel(e) {
         CloseWindow("cancel");
     }
	function updateDetailStatus(id, status) {
		var view = status==1?"确定关闭":"确定开启？";
		var str = status == 1 ? 0 : 1;

        mini.confirm(view, "提示",
                function (action) {
                    if (action == "ok") {
						$.ajax({
							url : "travelallow/updateDetailStatus.do",
							type : 'post',
							data : {
								id : id,
								status : str
							},
							cache : false,
							success : function(text) {
								if (text.message == 'success')
									grid.reload();
							},
							error : function(jqXHR, textStatus, errorThrown) {
								mini.alert(jqXHR.responseText);
							}
						});
                    } 
                }
            );
	};
	function saveValue() {
        var json = grid.getChanges();
       	 	
            if(json.length>0){
            	debugger;
            	for(var i=0;i<json.length;i++){
            		if(json[i].wkCoin==''||json[i].wkCoin==null||json[i]=='null'){
            			console.info(json[i].wkCoin);
            			mini.alert("悟空币为必填");
            			return;
            		}
                }
            	json = mini.encode(json);
	            grid.loading("保存中，请稍后......");
	            $.ajax({
	                url: "travelallow/saveTravelDetail.do",
	                data: { data: json },
	                type: "post",
	                success: function (text) {
	                	if(text.message=='success'){
	                		CloseWindow("save");
	               	}else{
	               		mini.alert(text.data);
	               	}
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert(jqXHR.responseText);
	                }
	            });
        }else{
        	mini.alert("悟空币数据没有更改！");
        }
	}
	
</script>
</html>
