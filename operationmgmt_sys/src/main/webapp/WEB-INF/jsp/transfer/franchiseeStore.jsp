<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>门店列表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
    <script src="${ctx}/scripts/miniui/miniui.js" type="text/javascript"></script>
    <script src="${ctx}/scripts/boot.js?v=${version}" type="text/javascript"></script>
    <style type="text/css">
        html,body
        {
            width:100%;
            height:100%;
            border:0;
            margin:0;
            padding:0;
            overflow:visible;
        }
    </style>
</head>
<body >
    
     <!--撑满页面-->
        <div class="mini-fit" style="background:red;height:100px;">
    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" 
        url="${ctx}/transferMng/getFranchiseeStoreList.action"  idField="id" allowResize="false"
        sizeList="[20,30,50,100]" pageSize="20">
        <div property="columns">
            <div type="indexcolumn" style="display: none">序号</div>
            <div field="name" width="120" headerAlign="center" align="center" allowSort="true">门店（组织架构）</div>                            
            <div field="chartername" width="100"  align="center" headerAlign="center">营业执照名称</div>
            <div field="storeaddress" width="100" align="center" headerAlign="center">门店地址</div>
            
            <div field="context"  name="contextColumn" width="100" headerAlign="center"  align="center" allowSort="false">操作</div>
        </div>
    </div>   
    </div>
    
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");

        grid.load({franchiseeId:"${franchiseeId}"});
        grid.on("drawcell", function (e) {
        	  var record = e.record,
	              column = e.column,
	              field = e.field,
	              value = e.value;
        	if(field == "context"){
        		var bid = 0;
        		if(record.storeId != null){
        			bid = record.storeId;
        		}
        		e.cellHtml = "<a href='javascript:void(0)' onclick='editData(\"" + record.id+ "\",\"" + bid+ "\",\"" + record.areaId+"\")'>编辑</a>";
        	}
        });
      function search(){
    	  grid.load({franchiseeId:"${franchiseeId}"});
      }
        $("#key").bind("keydown", function (e) {
            if (e.keyCode == 13) {
                search();
            }
        });
        
        function editData(storeId,id,areaId){
        	//var id = 0;
        	
        	mini.open({
   				url : "${ctx}/transferMng/editFranchiseeStore.action?storeId="+storeId+"&areaId="+areaId+"&franchiseeId=${franchiseeId}&id="+id,
   				title : "编辑合作伙伴门店列表",
   				width : 650,
   				height : 250,
   				ondestroy : function(action) {
   					if(action == "save"){
   						//lf.areaOrg.area.list.search();
   					}
   				}
   			});
        }
       
    </script> 
    <div class="description">
       <%-- <jsp:include page="../common/footer.jsp"></jsp:include> --%>
    </div>
</body>
</html>