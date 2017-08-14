<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>客户列表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
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

<!-- BEGIN BODY -->
<body class="page-header-fixed page-sidebar-fixed page-footer-fixed">
    <!-- 基本信息start -->
    <fieldset style="width:700px;border:solid 1px #aaa;margin-top:8px;position:relative;">
        <legend>客户信息</legend>
        <div id="editForm1" style="padding:5px;">
            <input class="mini-hidden" name="id"/>
            <table style="width:100%;">
                <tr>
                    <td style="width:80px;">姓名：</td>
                    <td style="width:150px;"><input name="name" value="${customer.name}" class="mini-textbox" enabled="false"/></td>
                    <td style="width:80px;">性别：</td>
                    <td style="width:150px;">
                        <c:if test="${customer.gender == 1}">
                            <input name="gender" value="男" class="mini-textbox" enabled="false"/>
                        </c:if>
                        <c:if test="${customer.gender == 2}">
                            <input name="gender" value="女" class="mini-textbox" enabled="false"/>
                        </c:if>
                        <c:if test="${customer.gender != 1 and customer.gender !=2}">
                            <input name="gender" value="保密" class="mini-textbox" enabled="false"/>
                        </c:if>
                    </td>
                    <td style="width:80px;">级别：</td>
                    <td style="width:150px;">
                        <c:choose>
                            <c:when test="${customer.cusLevel == 1}">
                                <input name="cusLevel" class="mini-textbox" value="A"  enabled="false"/>
                            </c:when>
                            <c:when test="${customer.cusLevel == 2}">
                                <input name="cusLevel" class="mini-textbox" value="B"  enabled="false"/>
                            </c:when>
                            <c:when test="${customer.cusLevel == 3}">
                                <input name="cusLevel" class="mini-textbox" value="C"  enabled="false"/>
                            </c:when>
                            <c:otherwise>
                                <input name="cusLevel" class="mini-textbox" value="D"  enabled="false"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td>来源：</td>
                    <td>
                        <c:choose>
                            <c:when test="${customer.source == 25}">
                                <input name="source" class="mini-textbox" value="悟空找房"  enabled="false"/>
                            </c:when>
                            <c:when test="${customer.isCallBackCus == 1}">
                                <input name="source" class="mini-textbox" value="网络来源"  enabled="false"/>
                            </c:when>
                            <c:otherwise>
                                <input name="cusLevel" class="mini-textbox" value="其它"  enabled="false"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>创建日期：</td>
                    <td>
                        <input name="createTime" enabled="false" class="mini-textbox" value="<fmt:formatDate value="${customer.createTime }" pattern="yyyy-MM-dd HH:mm"/>"/>
                    </td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>备注：</td>
                    <td colspan="5"><textarea class="mini-textarea" enabled="false" width="500">${customer.remark}</textarea></td>
                </tr>
            </table>
        </div>
    </fieldset>
    <!-- 基本信息end -->
                
    <!-- 客户需求展示table start -->
    <fieldset style="width:700px;border:solid 1px #aaa;margin-top:8px;position:relative;">
        <legend>客户需求信息</legend>
        <div id="editForm2" style="padding:5px;">
            <input class="mini-hidden" name="id"/>
            <table style="width:100%;">
                <tr>
                    <td style="width:80px;">类型：</td>
                    <td style="width:150px;">
                        <c:choose>
                            <c:when test="${cusReq.hType == 1}">
                                <input name="htype" class="mini-textbox" value="公寓" enabled="false"/>
                            </c:when>
                            <c:when test="${cusReq.hType == 2}">
                                <input name="htype" class="mini-textbox" value="别墅" enabled="false"/>
                            </c:when>
                            <c:otherwise>
                                <input name="htype" class="mini-textbox" value="" enabled="false"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td style="width:80px;">小区：</td>
                    <td style="width:150px;">
                        <input name="name" class="mini-textbox" value="${cusReq.estateName}" enabled="false"/>
                    </td>
                    <td style="width:80px;">区域/板块：</td>
                    <td style="width:150px;">
                        <input name="salary" class="mini-textbox" value="${cusReq.areaName}/${cusReq.townName}" enabled="false"/>
                    </td>
                </tr>
                <tr>
                    <td>价格：</td>
                    <td>
                        <input name="price" class="mini-textbox" value="${cusReq.minPrice}" enabled="false" width="50"/>-
                        <input name="price" class="mini-textbox" value="${cusReq.maxPrice}" enabled="false" width="50"/>
                    </td>
                    <td>面积：</td>
                    <td>
                        <input name="floorage" class="mini-textbox" value="${cusReq.minFloorage}" enabled="false" width="50"/>-
                        <input name="floorage" class="mini-textbox" value="${cusReq.maxFloorage}" enabled="false" width="50"/>
                    </td>
                    <td>户型：</td>
                    <td>
                        <c:choose>
                            <c:when test="${cusReq.bedRoomSum != 0 or cusReq.livingRoomSum != 0 or cusReq.wcSum != 0}">
                                <input name="floorage" enabled="false" class="mini-textbox" value="${cusReq.bedRoomSum}室${cusReq.livingRoomSum}厅${cusReq.wcSum}卫"/>
                            </c:when>
                            <c:otherwise>
                                <input name="floorage" enabled="false" class="mini-textbox" value=""/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>
        </div>
    </fieldset>
    <!-- 客户需求展示table end -->
    
    <script type="text/javascript">
        mini.parse();


    </script>

</body>
<!-- END BODY -->
</html>
