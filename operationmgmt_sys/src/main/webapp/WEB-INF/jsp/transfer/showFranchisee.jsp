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
            <h1><strong>合作伙伴信息</strong></h1>
                </div>
                <table style="margin-left:20px;">
                    <%--<tr>--%>
                        <%--<td>--%>
                        <%--城市<span style="color: red">*</span>--%>
                        <%--</td>--%>
                        <%--<td>--%>

                            <%--<input id="cityId" class="mini-textbox" style="width:150px;" textField="name"--%>
                                  <%--value="${laaList[0].cityName }"  readonly="readonly"  />--%>
                        <%--</td>--%>
                        <%--<td style="text-align: right">--%>
                             <%--区域<span style="color: red">*</span>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                                <%--<input id="areaId" class="mini-textbox" style="width:150px;" textField="name" valueField="code"--%>
                               <%--value="${laaList[0].areaName }" showNullItem="true" readonly="readonly" />--%>
                        <%--</td>--%>


                    <%--</tr>--%>
            <%----%>
            <tr>
                <td>
            	    简称
                </td>
                <td>
                    <input id="abbreviation" class="mini-textbox" value="${laaList[0].abbreviation }" readonly="readonly" style="width: 150px;" />
                </td>
                <td style="text-align: right">
           		    公司名称
                </td>
                <td>
                    <input id="companyname" class="mini-textbox" value="${laaList[0].companyname }" readonly="readonly" style="width: 150px;" />
                </td>
            </tr>
            <tr>
                <td width="10%">
          		    法人代表
                </td>
                <td>
                    <input id="corporate" class="mini-textbox" value="${laaList[0].corporate }" readonly="readonly" style="width: 150px;" />
                </td>
                <td style="text-align: right">
          		    电话
                </td>
                <td>
                    <input id="corporatephone" class="mini-textbox" value="${laaList[0].corporatephone }" readonly="readonly" style="width: 150px;" />
                </td>
            </tr>
            <tr>
                <td>
             	    合伙人1
                </td>
                <td>
                    <input id="partner1" class="mini-textbox"  value="${laaList[0].partner1 }" readonly="readonly" style="width: 150px;" />
                </td>
                <td style="text-align: right">
               	     电话
                </td>
                <td>
                    <input id="partnerphone1" class="mini-textbox" value="${laaList[0].partnerphone1 }" readonly="readonly" style="width: 150px;" />
                </td>
            </tr>
                    	 
                    	
               	<tr>
                    <td>
               	        合伙人2
                    </td>
                    <td>

                        <input id="partner2" class="mini-textbox" value="${laaList[0].partner2 }" readonly="readonly" style="width: 150px;" />
                    </td>
               	    <td style="text-align: right">
                        电话
                    </td>
                    <td>
                        <input id="partnerphone2" class="mini-textbox" value="${laaList[0].partnerphone2 }" readonly="readonly" style="width: 150px;" />
                    </td>

                </tr>
                
                 <tr>
                    <td>
                        BD
                    </td>
                    <td>
                        <input id="bd" class="mini-textbox" readonly="readonly" value="${laaList[0].bd }" style="width: 150px;" />
                    </td>
                    <td style="text-align: right">
                        电话
                    </td>
                    <td>
                        <input id="bdphone" class="mini-textbox" readonly="readonly" value="${laaList[0].bdphone }" style="width: 150px;" />
                    </td>
                </tr>

                <tr>
                    <td>
                        BP
                    </td>
                    <td>
                        <input id="bp" class="mini-textbox" readonly="readonly" value="${laaList[0].bp }" style="width: 150px;" />
                    </td>
                    <td style="text-align: right">
                        电话
                    </td>
                    <td>
                        <input id="bpphone" class="mini-textbox" readonly="readonly" value="${laaList[0].bpphone }" style="width: 150px;" />
                    </td>
                </tr>
                
                
                <tr>
                    <td>
                        地址
                    </td>
                    <td colspan="3">
                        <input id="address" class="mini-textbox" value="${laaList[0].address }" readonly="readonly"  style="width:445px;" />
                    </td>

                </tr>
                <tr>
                    <td>
					    合作期限
                    </td>
                    <td>
                        <input id="queryTimeBegin" class="mini-datepicker"  value="${laaList[0].cooperationstart }" readonly="readonly"  showOkButton="true" showClearButton="false"/>
                    </td>
                    <td>
					    <input id="queryTimeEnd" class="mini-datepicker"  value="${laaList[0].cooperationend }" readonly="readonly"  showOkButton="true" showClearButton="false"/>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${laaList[0].isseed == 1}">
                                <input id="isseed" class="mini-checkbox" checked="true" readonly="readonly" style="width: 150px;" />是否属于种子用户
                            </c:when>
                            <c:otherwise>
                                <input id="isseed" class="mini-checkbox" checked="false" readonly="readonly" style="width: 150px;" />是否属于种子用户
                            </c:otherwise>
                      </c:choose>
                    </td>
                </tr>
                <tr>
                    <td>

                	    保证金<span style="color: red">*</span>
                    </td>
                    <td width="180px">
                	    <input id="deposit" class="mini-textbox"  value="${laaList[0].deposit }" readonly="readonly"  style="width: 150px;" />元
                    </td>
                    <td style="text-align: right">
                	    品牌使用费<span style="color: red">*</span>
                    </td>
                    <td width="180px">

                	    <input id="brandcost" class="mini-textbox"  value="${laaList[0].brandcost }" readonly="readonly"  style="width: 150px;" />元
                    </td>
                </tr>
                <tr>
                    <td>
                	    合作费:<span style="color: red">*</span>
                    </td>
                    <td>
                        <input id="cooperationcost"  value="${laaList[0].cooperationcost }" readonly="readonly"  class="mini-textbox"  style="width: 150px;" />%
                    </td>

                </tr>

                        <tr>
                            <td colspan="2">



                                <c:choose>
                                    <c:when test="${laaList[0].franchiseePhone == '0'}">
                                        <input id="isaccounts" class="mini-checkbox" readonly="readonly" checked="false"   style="width: 150px;" />开通合作伙伴人事账号
                                    </c:when>
                                    <c:otherwise>
                                        <input id="isaccounts" class="mini-checkbox" checked="true" readonly="readonly" style="width: 150px;" />开通合作伙伴人事账号
                                    </c:otherwise>
                                </c:choose>


                            </td>

                            <td colspan="4">
                                合作伙伴手机号

                                <c:choose>
                                    <c:when test="${laaList[0].franchiseePhone == '0'}">
                                        <input id="franchiseePhone" value="" class="mini-textbox" readonly="readonly" style="width: 150px;" />
                                    </c:when>
                                    <c:otherwise>
                                        <input id="franchiseePhone" value="${laaList[0].franchiseePhone }" readonly="readonly"  class="mini-textbox"  style="width: 150px;" />
                                    </c:otherwise>
                                </c:choose>


                                <a class="mini-button" onclick="onRePwd()" style="width:80px;margin-right:20px;">重置密码</a>
                                <a class="mini-button" onclick="onReLimit()" style="width:80px;margin-left: 240px;">重置权限</a>
                            </td>

                        </tr>



                        <div style="padding-left:53px;padding-bottom:5px;padding-top:10px;">
                            <table>
                                <tr>
                                    <td style="padding-left: 60px">已选城市
                                        <div id="dg2" class="mini-datagrid" style="width:500px;height:290px;" showCheckBox="true"
                                             multiSelect="true" showPager="false">
                                            <div property="columns">
                                                <div type="checkcolumn" width="10"></div>
                                                <%--<div header="ID" field="cityId" width="10" style="display: block"></div>--%>
                                                <div header="城市" field="text" width="35"></div>

                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </div>



        </table>
    </form>
    <script type="text/javascript">

        mini.parse();
        var dg2 = mini.get("dg2");
        loadDg2();
        //id="+id+"&title="+title+"&pushContent="+istiming+"&pushtime="+pushtime,
      	
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
    /* ********************************************************** */

        function loadDg2() {



            $.ajax({
                method : "post",
                url : "${ctx}/transferMng/getData2.action?cityId=${laaList[0].cityId }",
                success : function(result) {
                    var datas1 = result;
//
                    dg2.setData(datas1);
                }
            });
        }
    function onCancel(e) {
        //CloseWindow("cancel");
    	window.CloseOwnerWindow();
    }
    function onReLimit(){
    	 if(${laaList[0].franchiseePhone == '0'}){
             mini.alert("此合作伙伴没有开通账号");
             return false;
         }
         lc.mask("正在处理中,请稍后...");
         $.ajax({
             method : "post",
             url : "${ctx}/transferMng/resetLimit.action?franchiseePhone=${laaList[0].franchiseePhone }",
             success : function(result) {
                 if(result.status == 1){
                     lc.unmask();
                     mini.alert("重置权限成功!","成功",function(){

                     });
                 }else{
                     lc.unmask();
                     mini.alert("重置权限失败,请稍后重试!","错误",function(){

                     });
                 }
             }
         });
     }

        function onRePwd(){
            if(${laaList[0].franchiseePhone == '0'}){
                mini.alert("此合作伙伴没有开通账号");
                return false;
            }
            lc.mask("正在处理中,请稍后...");
            $.ajax({
                method : "post",
                url : "${ctx}/transferMng/resetPwd.action?franchiseePhone=${laaList[0].franchiseePhone }",
                success : function(result) {
                    if(result == 1){
                        lc.unmask();
                        mini.alert("重置密码成功!","成功",function(){

                        });
                    }else{
                        lc.unmask();
                        mini.alert("重置密码失败,请稍后重试!","错误",function(){

                        });
                    }
                }
            });
        }
    </script>
</body>
</html>