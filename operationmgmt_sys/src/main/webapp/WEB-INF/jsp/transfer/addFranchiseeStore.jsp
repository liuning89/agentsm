<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
    Date date=new Date();//取时间
    Date d = date;
    Calendar calendar = Calendar.getInstance();
    
    calendar.setTime(date);
    d = calendar.getTime();
    calendar.add(Calendar.MONTH, -1);
    
    date  = calendar.getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = formatter.format(date);
    String dString = formatter.format(d);
    String startDate = dateString + " 00:00:00";
    String endDate = dString + " 23:59:59";
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

	<form id="contactform" method="post">
        <div style="padding-left:11px; padding-bottom:5px;" align="center">

            
            <div style="text-align: left">
            <h1><strong>门店信息</strong></h1>
            	门店（组织架构）
	              <input id="code" class="mini-textbox" readonly="readonly" value="${name }" style="width: 150px;" /> 
            </div>
            <br/>
            <div style="text-align: left">
            	 营业执照名称<input id="chartername" class="mini-textbox" value="${chartername}" style="width: 150px;" />
           		门店地址<input id="storeaddress" class="mini-textbox" value="${storeaddress}" style="width: 150px;" />
              </div>
        </div>
        <div style="text-align: center">
        	<a class="mini-button" onclick="onSaveData()" style="width:60px;margin-right:20px;">保存</a>
          	<a class="mini-button" onclick="onCancel()" style="width:60px;margin-right:20px;">关闭</a>
           
        </div>
     
    </form>
    <script type="text/javascript">

        mini.parse();
        function CloseWindow(action) {
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }

        function onCancel(e) {
            //CloseWindow("cancel");
        	window.CloseOwnerWindow();
        }
        function ontimeValueChanged(e) {
          //  alert(this.getFormValue());
        }
        
        
      //城市区域下啦选择
    	function onCityChanged(e) {
                var cityCbo = mini.get("cityId");
                var areaCbo = mini.get("areaId");
                var id = cityCbo.getValue();
                
                areaCbo.select(0);
                var url = "${ctx}/areaorg/getListByLogin.action?level=70";
                if(id != null && id != "")
                {
                    url += "&parentId=" + id;
                }
                areaCbo.setUrl(url);
                areaCbo.select(0);
            }
    	 function onAreaChanged(e) {
             var areaCbo = mini.get("areaId");
             var storeCbo = mini.get("storeId");
             var id = areaCbo.getValue();
//             storeCbo.select(0);
             var url = "${ctx}/areaorg/getListByLogin.action?level=60";
             if(id != null && id != "")
             {
                 url += "&parentId=" + id;
             }
         }

    /* ********************************************************** */
        function onSaveData(){
            lc.mask("正在处理中,请稍后...");
            $.ajax({
                url : "${ctx}/transferMng/saveFranchiseeStore.action",
                cache : false,
                dataType : 'text',
                data:{areaId:"${areaId}",franchiseeId:"${franchiseeId}",
                	chartername:mini.get("chartername").getValue(),storeaddress:mini.get("storeaddress").getValue(),
                	id:"${id}",
                    storeId:"${storeId}"
                },
                type: "POST",
                success : function(d) {
                    if(d == 1){
                        lc.unmask();
                        mini.alert("保存成功!","成功",function(){
                        	onCancel();
                        	window.Owner.search();
                        });
                    }else{
                        lc.unmask();
                        mini.alert("保存失败,请稍后重试!","错误",function(){
                        	

                        });
                    }
                }
            });
        }
    function onCancel(e) {
        //CloseWindow("cancel");
    	window.CloseOwnerWindow();
    }
    </script>
</body>
</html>