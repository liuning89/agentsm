<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>实景图片审核列表</title>
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
    <div id="housefollowUpDiv" style="padding-bottom:5px;">
    <span>城市:</span>
      <input id="cityId" class="mini-combobox" style="width:135px;" textField="name" valueField="code" 
      url="${ctx}/areaorg/getListByLogin.action?level=90" value="" showNullItem="true"  allowInput="true"
      onvalidation="onComboValidation" onvaluechanged="onCityChanged" emptyText="请选择" nullItemText="请选择" />
                           
    
    
        <span>状态：</span>
        <input name="status" id="status" class="mini-combobox"  textField="text" valueField="id" data="[{id:0,text:'全部'},{id:1,text:'待处理'},{id:2,text:'已处理'}]" value="0"/>
                  
        <br />
        <span>举报原因</span>
        <br/>
        
        <div id="repetitionimage" name="repetitionimage" class="mini-checkbox" checked="true" readOnly="false" text="重复图片" ></div><br/>
        <div id="shamimage" name="shamimage" class="mini-checkbox" checked="true"  readOnly="false" text="虚假图片"></div><br/>
        <div id="lowpixel" name="lowpixel" class="mini-checkbox" checked="true" readOnly="false" text="图片质量低"></div><br/>
      
		        
  		<input type="button" value="查找" onclick="search()"/>
        <input type="button" value="重置" onclick="reset()"/>
    </div>
     <!--撑满页面-->
        <div class="mini-fit" style="background:red;height:100px;">
    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" 
        url="${ctx}/inform/getImageInformList.action"  idField="id" allowResize="false"
        sizeList="[20,30,50,100]" pageSize="20">
        <div property="columns">
            <!-- <div type="indexcolumn" style="display: none"></div> -->
            <div field="indexs" width="120" headerAlign="center" align="center" allowSort="true">序号</div>                            
            <div field="houseId" width="100"  align="center" headerAlign="center">房源编号</div>
            <div field="imgKey" width="100" align="center" headerAlign="center">图片</div>
            <div field="beAccusationAgentId" width="120" renderer="onEvaluateBad" align="center" headerAlign="center"  allowSort="false">上传人</div>  
            
            <div field="accusationAgentId" width="100" headerAlign="center" align="center" allowSort="false"  >举报人</div>
            <div field="createTime" width="100" headerAlign="center" align="center" renderer="onTimeRenderer" allowSort="false">举报时间</div>
            <div field="accusationReasonId" width="100" headerAlign="center" align="center" allowSort="false">举报原因</div>
            
            <div field="auditTime"  width="100" headerAlign="center" renderer="onTimeRenderer" align="center" allowSort="false">处理时间</div>
            <div field="auditSatus" width="100" headerAlign="center"  align="center" allowSort="false">状态</div>
            <div field="aname" width="100" headerAlign="center"  align="center" allowSort="false">操作</div>
               
        </div>
    </div>   
    </div>
    <div id="showImgWin" class="mini-window" title="图片查看" style="width:310px;height:310px;"
         showMaxButton="true" showCollapseButton="true" showShadow="true"
         showToolbar="true" showFooter="true" showModal="false" allowResize="true" allowDrag="true">
            <img id="imgAgreementKey" src="#"/>
            <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load({accusationType:2});
        grid.on("drawcell", function (e) {
        	  var record = e.record,
	              column = e.column,
	              field = e.field,
	              value = e.value;
        	
        	if (field  == "accusationReasonId") {
        		if(value == 1){
        			e.cellHtml = "重复图片";
        		}else if(value == 2){
        			e.cellHtml = "虚假图片";
        		}else if(value == 3){
        			e.cellHtml = "图片质量低";
        		}
            }
        	if(field == "imgKey"){
        		e.cellHtml = '<a href="javascript:ajaxShowImgEl(\'' + value + '\')">点击查看图片</a> '
        	}
        	if(field == "indexs"){
        		e.cellHtml = e.rowIndex + 1;
        	}
        	
        	if(field == "beAccusationAgentId"){
        		if(record.positiName == null && record.name == null){
        			e.cellHtml = "";
        		}else{
        			
	        		e.cellHtml = record.positiName+"-"+record.name;
        		}
        	}
			if(field == "accusationAgentId"){
				if(record.positiName1 == null && record.name1 == null){
        			
        			e.cellHtml = "";
        		}else{
	        		e.cellHtml = record.positiName1+"-"+record.name1;
        		}
        		
        	}
        	if(field == "auditSatus"){
				if(value == 1){
					e.cellHtml = "待处理";
				}else{
					e.cellHtml = "已处理";
				}
        	}
        	if(field == "aname"){
        		// e.cellHtml = '<a href="javascript:openDetail(' + e.rowIndex + ',\'' + record.agentId  + '\',\''+record.houseId + '\')">操作</a> '
        		if(record.auditSatus == 1){
        			
        		 	e.cellHtml = '<a href="javascript:checkOk(' + e.rowIndex + ',\'' + record.id +'\')">确定</a>' + ' | ' + 
        		 					'<a href="javascript:deleteData(' + e.rowIndex + ',\'' + record.id +'\')">删除</a>'
        		}else{
        			
        			if(value == null){
            			e.cellHtml = "处理人()";
            		}else{
            			e.cellHtml = "处理人("+record.aname+")";
            		}
        		}
        	}
        });
        
        function search() {
        	
            var status = mini.get("status").getValue();//状态
            var repetitionimage = mini.get("repetitionimage");
            var shamimage = mini.get("shamimage");
            var lowpixel = mini.get("lowpixel");
            
            var accusationReasonId = "";
            var repetition = "";//重复图片
            var sham = "";//虚假图片
            var lowpi = "";//图片质量低
            if(repetitionimage.getValue() == "true"){
            	repetition = 1
            }
            if(shamimage.getValue() == "true"){
            	sham = 2;
            }if(lowpixel.getValue() == "true"){
            	lowpi = 3;
            }
            
            
            if(repetition != "" ){
            	accusationReasonId = 1;
            	if(sham != ""){
            		accusationReasonId += ",2";
            		if(lowpi != ""){
            			accusationReasonId += ",3";
            		}
            	}else if(lowpi != ""){
        			accusationReasonId += ",3";
        		}
            }else if(sham != ""){
            	accusationReasonId = "2";
            	if(lowpi != ""){
            		accusationReasonId += ",3";
            	}
            }else if(lowpi != ""){
            	accusationReasonId = 3;
            }
            var cityCode = mini.get("cityId").getFormValue();//城市
        	
            grid.load({ cityCode:cityCode,accusationType: 2, accusationReasonId: accusationReasonId, auditSatus: status});
        }
        
        function reset()
        {
        	var t = mini.get("status");
            t.setValue("0");
            t.setText("全部");

            var t1 = mini.get("repetitionimage");
            t1.setChecked(true);
       	 
            
            var t2 = mini.get("shamimage");
            t2.setChecked(true);
            
            var t3 = mini.get("lowpixel");
            t3.setChecked(true);
            
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
        function ajaxShowImgEl(imgAgreementKey) {
            var url = "${ctx}/agent/getImageKeyObject.action?key=" + imgAgreementKey;
            $.ajax({
                url: url,
                type: "post",
//                async:true,
                success: function (text) {
                    eval("var result=" + text);
                    showImgEl(result.data.water_cl);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
        }
        function showImgEl(imgUrl){
            var win = mini.get("showImgWin");
            var atEl = document.getElementById("housefollowUpDiv");
            var imgAgreementKey = document.getElementById("imgAgreementKey");
            imgAgreementKey.src = imgUrl;
//            document.getElementById("showImgWin").style.width = (img.width + 20) + "px";
//            document.getElementById("showImgWin").style.height = img.height + "px";
            win.showAtEl(atEl, {
                xAlign: "Center",
                yAlign: "Middle"
            });
        }
       function checkOk(index,id){
    	   mini.confirm("确定要操作吗？", "确定？",
                   function (action) {
                       if (action == "ok") {
                       	lc.mask("正在处理中,请稍后...");
                       	$.ajax({
                       		url : "${ctx}/inform/updateInform.action?id="+id,
                               cache : false,
                               dataType : 'text',
                               success : function(data) {
                           		 if(data == 1){
                           			 lc.unmask();
                           			 mini.alert("处理成功!","成功",function(){search();});
                           		 }else{
                           			 lc.unmask();
                           			 mini.alert("处理失败,请稍后重试!","错误",function(){search();});
                           		 }	
                               }
                           });
                       } else {

							
                       }
                   }
               );
       	
       }
       function deleteData(index,id){
    	   mini.confirm("确定要删除吗？", "确定？",
                   function (action) {
                       if (action == "ok") {
                       	lc.mask("正在处理中,请稍后...");
                       	$.ajax({
                       		url : "${ctx}/inform/deleteInform.action?id="+id,
                               cache : false,
                               dataType : 'text',
                               success : function(data) {
                           		 if(data == 1){
                           			 lc.unmask();
                           			 mini.alert("删除成功!","成功",function(){search();});
                           		 }else{
                           			 lc.unmask();
                           			 mini.alert("删除失败,请稍后重试!","错误",function(){search();});
                           		 }	
                               }
                           });
                       } else {

							
                       }
                   }
               );
       }
       
       function onCityChanged(e) {
           var cityCbo = mini.get("cityId");
          // var areaCbo = mini.get("areaId");
           var id = cityCbo.getValue();
           
          // areaCbo.select(0);
           var url = "${ctx}/areaorg/getListByLogin.action?level=70";
           if(id != null && id != "")
           {
               url += "&parentId=" + id;
           }
       }
       
    </script> 
    <div class="description">
       <%-- <jsp:include page="../common/footer.jsp"></jsp:include> --%>
    </div>
</body>
</html>