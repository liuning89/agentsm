<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>合作伙伴列表</title>
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
    <div style="padding-bottom:5px;">

        <span>合作伙伴简称：</span>

        <input id="abbreviation" type="text" emptyText="模糊搜索"  class="mini-textbox" />

        <span>城市<span style="color:red">*</span></span>

        <input id="cityId" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
               url="${ctx}/areaorg/getListByLogin.action?level=90" value="" showNullItem="true"  allowInput="true"
               onvalidation="onComboValidation" emptyText="请选择" nullItemText="请选择" />

        <span>法人代表：</span>

        <input id="corporate" type="text" emptyText="模糊搜索"  class="mini-textbox" />

        <span>法人代表电话：</span>

        <input id="corporatephone" type="text" class="mini-textbox" />

        <div id="repetitionimage" name="repetitionimage" class="mini-checkbox" checked="false" readOnly="false" text="是否种子用户" ></div>
        <input type="button" value="查找" onclick="search()"/>
<br />

 <input type="button" value="添加合作伙伴" onclick="addFranchisee()"/>

    </div>
     <!--撑满页面-->
        <div class="mini-fit" style="background:red;height:100px;">
    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" 
        url="${ctx}/transferMng/getFranchiseeList.action"  idField="id" allowResize="false"
        sizeList="[20,30,50,100]" pageSize="20">
        <div property="columns">
            <div type="indexcolumn" style="display: none"></div>
            <%--<div field="cityName" width="120" headerAlign="center" align="center" allowSort="true">城市</div>--%>
            <div field="abbreviation" width="100"  align="center" headerAlign="center">合作伙伴简称</div>
            <div field="corporate" width="100" align="center" headerAlign="center">法人代表</div>
            <div field="corporatephone" width="120" renderer="onEvaluateBad" align="center" headerAlign="center"  allowSort="false">电话</div>  
            <div field="deposit" width="100" headerAlign="center" align="center" allowSort="false" >保证金（元）</div>
            <div field="brandcost" width="100" headerAlign="center" align="center" allowSort="false">品牌使用费（元/每月）</div>
            <div field="cooperationcost" name="loginNameColumn" width="100" headerAlign="center" align="center" allowSort="false">合作费</div>
            
            <div field="isseed" name="auditTimeColumn" width="100" headerAlign="center" renderer="onTimeRenderer" align="center" allowSort="false">是否种子用户</div>
             <div field="status" name="status" width="100" headerAlign="center" align="center" allowSort="false">状态</div>
            
            <div field="context"  name="contextColumn" width="100" headerAlign="center"  align="center" allowSort="false">操作（合作伙伴)</div>
            <div field="contextstore"  name="contextstoreColumn" width="100" headerAlign="center"  align="center" allowSort="false">操作（门店）</div>
            
            <div field="synchronization"  name="synchronizationColumn" width="100" headerAlign="center"  align="center" allowSort="false">端口同步</div>
               
        </div>
    </div>   
    </div>
    
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load({"repetitionimage":mini.get("repetitionimage").getFormValue()});
        grid.on("drawcell", function (e) {
        	  var record = e.record,
	              column = e.column,
	              field = e.field,
	              value = e.value;
        	
        	if (field  == "isseed") {
        		if(value == 0){
        			e.cellHtml = "否";
        		}else{
        			e.cellHtml = "是";
        		}
        		
            }
        	if(field=='status'){
        		if(value==1){
        			e.cellHtml="有效";
        		}else{
        			e.cellHtml="无效";
        		}
        		
        	}
        	if (field  == "cooperationcost") {
        		e.cellHtml = value+"%";
            }
        	
        	if(field == "context"){
       		 	if(record.status==1){
	       		 	e.cellHtml = "<a href='javascript:void(0)' onclick='openToShow(\"" + record.id+ "\")'>查看</a>  <a href='javascript:void(0)' onclick='editData(\"" + record.id+ "\")'>编辑</a>  <a href='javascript:void(0)' onclick='onStops(\"" + record.id+ "\")'>终止账号</a>";
       		 	}else{
	       		 	e.cellHtml = "<a href='javascript:void(0)' onclick='resumOperator(\"" + record.id+ "\")'>恢复合作</a>";
       		 	}
        		 	
        	}
        	
        	if(field == "contextstore"){
        		if(record.status==1){
	       		 	e.cellHtml = "<a href='javascript:void(0)' onclick='openStoreShow(\"" + record.id+ "\")'>查看</a>";
        		}
       		 	
        		 	
        	}
        	
        	if(field == "synchronization"){//ispush
        		if(record.status==1){
	        		if(record.ispush == 1){
	        			e.cellHtml = "<a href='javascript:void(0)' onclick='openPush(\"" + record.id+ "\",2)'>关闭</a>";
	        		}else{
	        			e.cellHtml = "<a href='javascript:void(0)' onclick='openPush(\"" + record.id+ "\",1)'>开启</a>";
	        		}
        		}
        	}

        });
        
        function search() {
        	debugger;
            var cityId = mini.get("cityId").getFormValue();//城市

            var corporate = mini.get("corporate").getValue();//法人代表
            var corporatephone = mini.get("corporatephone").getValue();//法人代表电话
            var repetitionimage = mini.get("repetitionimage").getFormValue();//是否种子用户
            var abbreviation = mini.get("abbreviation").getFormValue();//简称


            grid.load({ cityId: cityId, corporate: corporate, corporatephone: corporatephone, repetitionimage: repetitionimage,abbreviation:abbreviation});
        }
        

        
        function reset()
        {
        	//小区名
        	
        	var t = mini.get("estateId");
            t.setValue("");
            t.setText("");

        	// mini.get("buildingId").select(0);//座栋号
        	 var b = mini.get("buildingId");
        	 b.load(lc.strFormat("${ctx}/subEstate/building/list.action?subEstateId={0}",0));
        	
        	 b.setValue("0");
             b.setText("全部");
        	 
            document.getElementById("roomId").value = "";
            mini.get("auditStatusId").select(0);
            
            
            mini.get("store").select(0);
            var a = mini.get("agent")
            
            a.load(lc.rootPath(lc.strFormat("${ctx}/agent/simple/list.action?storeId={0}",0)));
            a.setValue("0");
            a.setText("全部");
       	 
        }
        //打开增加加盟商页面
        function addFranchisee(){
   			mini.open({
   				url : "${ctx}/transferMng/addFranchisee.action",
   				title : "添加合作伙伴",
   				width : 600,
   				height : 500,
   				ondestroy : function(action) {
   					if(action == "save"){
   						//lf.areaOrg.area.list.search();
   					}
   				}
   			});
        	
        }
        
        $("#key").bind("keydown", function (e) {
            if (e.keyCode == 13) {
                search();
            }
        });
        
        //////////////////////////////////////////////
        function onComboValidation(e) {
            var items = this.findItems(e.value);
            if (!items || items.length == 0) {
               	this.setValue('');
            }
        }

        function onTimeRenderer(e) {
        	
            var value = e.value;
            if (value){
	            value = new Date(value); 
            	return mini.formatDate(value, 'yyyy-MM-dd HH:mm:ss');
            } 
            return "";
        }
        function openToShow(id){
        	mini.open({
   				url : "${ctx}/transferMng/showFranchisee.action?id="+id,
   				title : "查看合作伙伴",
                width : 700,
                height : 700,
   				ondestroy : function(action) {
   					if(action == "save"){
   						//lf.areaOrg.area.list.search();
   					}
   				}
   			});
        }
        function editData(id){
        	mini.open({
   				url : "${ctx}/transferMng/editFranchisee.action?id="+id,
   				title : "编辑合作伙伴",
                width : 680,
                height : 500,
   				ondestroy : function(action) {
   					if(action == "save"){
   						//lf.areaOrg.area.list.search();
   					}
   				}
   			});
        }
        
        
        //打开加盟商门店
        function openStoreShow(id){
        	mini.open({
   				url : "${ctx}/transferMng/showFranchiseeStore.action?code="+id,
   				title : "合作伙伴门店",
   				width : 550,
   				height : 500,
   				ondestroy : function(action) {
   					if(action == "save"){
   						//lf.areaOrg.area.list.search();
   					}
   				}
   			});
        }
        
        
      //
        function openPush(id,ispush){
            lc.mask("正在处理中,请稍后...");
            $.ajax({
                url : "${ctx}/transferMng/openPush.action?id="+id+"&ispush="+ispush,
                cache : false,
                dataType : 'text',
                success : function(data) {
                    if(data == 1){
                        lc.unmask();
                        mini.alert("操作成功!","成功",function(){search();});
                    }else{
                        lc.unmask();
                        mini.alert("操作失败,请稍后重试!","错误",function(){search();});
                    }
                }
            });
        }

      function resumOperator(id){
    	  mini.confirm("确定恢复合作？", "提示",
                  function (action) {
                      if (action == "ok") {
				            lc.mask("正在处理中,请稍后...");
				            $.ajax({
				                url : "${ctx}/transferMng/resumOperator.action?id="+id,
				                cache : false,
				                dataType : 'json',
				                success : function(data) {
				                    if(data.status == 1){
				                        lc.unmask();
				                        mini.alert("操作成功!","成功",function(){search();});
				                    }
				                }
				            });
                      }
    	  });
      }

        function onStops(id){
        	
        	  mini.confirm("确定终止合作？", "提示",
                      function (action) {
                          if (action == "ok") {
					            lc.mask("正在处理中,请稍后...");
					            $.ajax({
					                url : "${ctx}/transferMng/onStops.action?id="+id,
					                cache : false,
					                dataType : 'json',
					                success : function(data) {
					                    if(data == 1){
					                        lc.unmask();
					                        mini.alert("操作成功!","成功",function(){search();});
					                    }else if(data == 2){
					                        lc.unmask();
					                        mini.alert("该合作伙伴有资源未被转移,不允许终止!","警告",function(){search();});
					                    }else{
					                        lc.unmask();
					                        mini.alert("操作失败,请稍后重试!","错误",function(){search();});
					                    }
					                }
					            });
                          }
        	  });
        }
    </script> 
    <div class="description">
       <%-- <jsp:include page="../common/footer.jsp"></jsp:include> --%>
    </div>
</body>
</html>