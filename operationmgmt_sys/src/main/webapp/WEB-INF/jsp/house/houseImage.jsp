<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!-- 统计图片数量 -->
<c:set var="count_1" value="0"/>
<c:set var="count_2" value="0"/>
<c:set var="count_3" value="0"/>
<c:set var="count_4" value="0"/>
<c:set var="count_5" value="0"/>
<c:set var="count_6" value="0"/>
<c:set var="count_7" value="0"/>
<c:set var="count_8" value="0"/>

<c:forEach items="${cImageList}" var="item">
    <c:if test="${item.type == 1}">
        <c:if test="${item.imgType == 1}">
            <c:set var="count_1" value="${count_1  + 1}"/>
        </c:if>
        <c:if test="${item.imgType == 2}">
            <c:set var="count_2" value="${count_2  + 1}"/>
        </c:if> 
        <c:if test="${item.imgType == 3}">
            <c:set var="count_3" value="${count_3  + 1}"/>
        </c:if> 
        <c:if test="${item.imgType == 4}">
            <c:set var="count_4" value="${count_4  + 1}"/>
        </c:if> 
        <c:if test="${item.imgType == 5}">
            <c:set var="count_5" value="${count_5  + 1}"/>
        </c:if> 
        <c:if test="${item.imgType == 6}">
            <c:set var="count_6" value="${count_6  + 1}"/>
        </c:if> 
        <c:if test="${item.imgType == 7}">
            <c:set var="count_7" value="${count_7  + 1}"/>
        </c:if> 
        <c:if test="${item.imgType == 8}">
            <c:set var="count_8" value="${count_8  + 1}"/>
        </c:if> 
    </c:if>
</c:forEach>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>房源图片</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
    <script src="${ctx}/scripts/boot.js?v=${version}" type="text/javascript"></script>
    <script src="${ctx}/scripts/miniui/swfupload.js" type="text/javascript"></script>
</head>
<body style="height: 100%">
     <fieldset id="fd1" style="width:98%">
        <legend><span>首页</span></legend>
        <div class="fieldset-body">
            <fieldset id="fd1" style="width:98%">
                <div class="fieldset-body">
                     <form id="uploadForm1" style="padding-left:10px;margin:0px;margin-top:10px;">
                        <table width="100%">
                            <tr>
                                <td style="text-align: right;width:80px;">选择文件：</td>
                                <td>
                                    <div style="margin-left:10px;">
                                        <input type="file" id="file1" onchange="CheckFile(this)"/>
                                        <input name="houseId" id="houseId" class="mini-hidden" value="${houseId }"/>
                                        <input name="type" class="mini-hidden" value="1"/>
                                        <input type="button" value="上传" onclick="submitFileUpload('uploadForm1','progressBar1')"/>
                                    </div>
                                </td>
                                <td>
                                    <div id="progressBar1"></div>
                                    <div style="margin-left:10px;" id="imgCon" >
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </fieldset>
             <div>
                <c:forEach items="${cImageList}" var="item">
                    <c:if test="${item.imgType == 1 && item.type == 1}">
                        <div class="mini-panel" style="width: 32%;float: left;margin-bottom: 10px;">
                            <div style="text-align: center;"><img width="190" src="${item.thumbnail}" alt="图片" /></div>
                            <div style="text-align: center;">
                                <a class="mini-button" iconCls="icon-remove" onclick="removeImage('${item.imgKey}')">删除图片</a>
                                <a class="mini-menubutton " menu="#${item.imgKey}" onclick="changeMenuSourceVlaue('${item.imgKey}')">移动到</a>
                                <a class="mini-button"  onclick="downloadFile('${ctx}/agent/downloadImageKeyObject.action?key=${item.imgKey}','${item.imgKey}.jpg')">下载</a>
                                <ul id="${item.imgKey}" class="mini-menu" style="display:none;">
                                    <li iconCls="icon-open" onclick="changeImageType(2,'客厅')">客厅</li>
                                    <li iconCls="icon-open" onclick="changeImageType(3,'卧室')">卧室</li>
                                    <li iconCls="icon-open" onclick="changeImageType(4,'厨房')">厨房</li>
                                    <li iconCls="icon-open" onclick="changeImageType(5,'卫生间')">卫生间</li>
                                    <li iconCls="icon-open" onclick="changeImageType(6,'阳台')">阳台</li>
                                    <li iconCls="icon-open" onclick="changeImageType(7,'房型图')">房型图</li>
                                    <li iconCls="icon-open" onclick="changeImageType(8,'外观图')">外观图</li>
                                </ul>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
             </div>
        </div>
    </fieldset>
    
    
    
    <fieldset id="fd2" style="width:98%">
        <legend><span>客厅(共${count_2}张)</span></legend>
        <div class="fieldset-body">
            <fieldset id="fd2_1" style="width:98%">
                <div class="fieldset-body">
                     <form id="uploadForm2" style="padding-left:10px;margin:0px;margin-top:10px;">
                        <table width="100%">
                            <tr>
                                <td style="text-align: right;width:80px;">选择文件：</td>
                                <td>
                                    <div style="margin-left:10px;">
                                        <input type="file" id="file2" onchange="CheckFile(this)"/>
                                        <input name="houseId" id="houseId" class="mini-hidden" value="${houseId }"/>
                                        <input name="type" class="mini-hidden" value="2"/>
                                        <input type="button" value="上传" onclick="submitFileUpload('uploadForm2','progressBar2')"/>
                                    </div>
                                </td>
                                <td>
                                    <div id="progressBar2"></div>
                                    <div style="margin-left:10px;" id="imgCon" >
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </fieldset>
             <div>
                <c:forEach items="${cImageList}" var="item">
                    <c:if test="${item.imgType == 2 && item.type == 1}">
                        <div class="mini-panel" style="width: 32%;float: left;margin-bottom: 10px;">
                            <div style="text-align: center;"><img width="190" src="${item.thumbnail}" alt="图片" /></div>
                            <div style="text-align: center;">
                                <a class="mini-button" iconCls="icon-edit" onclick="updateFirstPage('${item.imgKey}')">设为首页</a>
                                <a class="mini-button" iconCls="icon-remove" onclick="removeImage('${item.imgKey}')">删除图片</a>
                                <a class="mini-menubutton " menu="#${item.imgKey}" onclick="changeMenuSourceVlaue('${item.imgKey}')">移动到</a>
                                <a class="mini-button"  onclick="downloadFile('${ctx}/agent/downloadImageKeyObject.action?key=${item.imgKey}','${item.imgKey}.jpg')">下载</a>
                                <ul id="${item.imgKey}" class="mini-menu" style="display:none;">
                                    <li iconCls="icon-open" onclick="changeImageType(3,'卧室')">卧室</li>
                                    <li iconCls="icon-open" onclick="changeImageType(4,'厨房')">厨房</li>
                                    <li iconCls="icon-open" onclick="changeImageType(5,'卫生间')">卫生间</li>
                                    <li iconCls="icon-open" onclick="changeImageType(6,'阳台')">阳台</li>
                                    <li iconCls="icon-open" onclick="changeImageType(7,'房型图')">房型图</li>
                                    <li iconCls="icon-open" onclick="changeImageType(8,'外观图')">外观图</li>
                                </ul>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
             </div>
        </div>
    </fieldset>
    
    <fieldset id="fd3" style="width:98%">
        <legend><span>卧室(共${count_3}张)</span></legend>
        <div class="fieldset-body">
            <fieldset id="fd3_1" style="width:98%">
                <div class="fieldset-body">
                     <form id="uploadForm3" style="padding-left:10px;margin:0px;margin-top:10px;">
                        <table width="100%">
                            <tr>
                                <td style="text-align: right;width:80px;">选择文件：</td>
                                <td>
                                    <div style="margin-left:10px;">
                                        <input type="file" id="file3" onchange="CheckFile(this)"/>
                                        <input name="houseId" id="houseId" class="mini-hidden" value="${houseId }"/>
                                        <input name="type" class="mini-hidden" value="3"/>
                                        <input type="button" value="上传" onclick="submitFileUpload('uploadForm3','progressBar3')"/>
                                    </div>
                                </td>
                                    
                                <td>
                                    <div id="progressBar3"></div>
                                    <div style="margin-left:10px;" id="imgCon" >
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </fieldset>
             <div>
                <c:forEach items="${cImageList}" var="item">
                    <c:if test="${item.imgType == 3 && item.type == 1}">
                        <div class="mini-panel" style="width: 32%;float: left;margin-bottom: 10px;">
                            <div style="text-align: center;"><img width="190" src="${item.thumbnail}" alt="图片" /></div>
                            <div style="text-align: center;">
                                <a class="mini-button" iconCls="icon-edit" onclick="updateFirstPage('${item.imgKey}')">设为首页</a>
                                <a class="mini-button" iconCls="icon-remove" onclick="removeImage('${item.imgKey}')">删除图片</a>
                                <a class="mini-menubutton " menu="#${item.imgKey}" onclick="changeMenuSourceVlaue('${item.imgKey}')">移动到</a>
                                <a class="mini-button"  onclick="downloadFile('${ctx}/agent/downloadImageKeyObject.action?key=${item.imgKey}','${item.imgKey}.jpg')">下载</a>
                                <ul id="${item.imgKey}" class="mini-menu" style="display:none;">
                                    <li iconCls="icon-open" onclick="changeImageType(2,'客厅')">客厅</li>
                                    <li iconCls="icon-open" onclick="changeImageType(4,'厨房')">厨房</li>
                                    <li iconCls="icon-open" onclick="changeImageType(5,'卫生间')">卫生间</li>
                                    <li iconCls="icon-open" onclick="changeImageType(6,'阳台')">阳台</li>
                                    <li iconCls="icon-open" onclick="changeImageType(7,'房型图')">房型图</li>
                                    <li iconCls="icon-open" onclick="changeImageType(8,'外观图')">外观图</li>
                                </ul>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
             </div>
        </div>
    </fieldset>
    
    <fieldset id="fd4" style="width:98%">
        <legend><span>厨房(共${count_4}张)</span></legend>
        <div class="fieldset-body">
            <fieldset id="fd4_1" style="width:98%">
                <div class="fieldset-body">
                     <form id="uploadForm4" style="padding-left:10px;margin:0px;margin-top:10px;">
                        <table width="100%">
                            <tr>
                                <td style="text-align: right;width:80px;">选择文件：</td>
                                <td>
                                    <div style="margin-left:10px;">
                                        <input type="file" id="file4" onchange="CheckFile(this)"/>
                                        <input name="houseId" id="houseId" class="mini-hidden" value="${houseId }"/>
                                        <input name="type" class="mini-hidden" value="4"/>
                                        <input type="button" value="上传" onclick="submitFileUpload('uploadForm4','progressBar4')"/>
                                    </div>
                                </td>
                                <td>
                                    <div id="progressBar4"></div>
                                    <div style="margin-left:10px;" id="imgCon" >
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </fieldset>
             <div>
                <c:forEach items="${cImageList}" var="item">
                    <c:if test="${item.imgType == 4 && item.type == 1}">
                        <div class="mini-panel" style="width: 32%;float: left;margin-bottom: 10px;">
                            <div style="text-align: center;"><img width="190" src="${item.thumbnail}" alt="图片" /></div>
                            <div style="text-align: center;">
                                <a class="mini-button" iconCls="icon-edit" onclick="updateFirstPage('${item.imgKey}')">设为首页</a>
                                <a class="mini-button" iconCls="icon-remove" onclick="removeImage('${item.imgKey}')">删除图片</a>
                                <a class="mini-menubutton " menu="#${item.imgKey}" onclick="changeMenuSourceVlaue('${item.imgKey}')">移动到</a>
                                <a class="mini-button"  onclick="downloadFile('${ctx}/agent/downloadImageKeyObject.action?key=${item.imgKey}','${item.imgKey}.jpg')">下载</a>
                                <ul id="${item.imgKey}" class="mini-menu" style="display:none;">
                                    <li iconCls="icon-open" onclick="changeImageType(2,'客厅')">客厅</li>
                                    <li iconCls="icon-open" onclick="changeImageType(3,'卧室')">卧室</li>
                                    <li iconCls="icon-open" onclick="changeImageType(5,'卫生间')">卫生间</li>
                                    <li iconCls="icon-open" onclick="changeImageType(6,'阳台')">阳台</li>
                                    <li iconCls="icon-open" onclick="changeImageType(7,'房型图')">房型图</li>
                                    <li iconCls="icon-open" onclick="changeImageType(8,'外观图')">外观图</li>
                                </ul>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
             </div>
        </div>
    </fieldset>
    
    <fieldset id="fd5" style="width:98%">
        <legend><span>卫生间(共${count_5}张)</span></legend>
        <div class="fieldset-body">
            <fieldset id="fd5_1" style="width:98%">
                <div class="fieldset-body">
                     <form id="uploadForm5" style="padding-left:10px;margin:0px;margin-top:10px;">
                        <table width="100%">
                            <tr>
                                <td style="text-align: right;width:80px;">选择文件：</td>
                                <td>
                                    <div style="margin-left:10px;">
                                        <input type="file" id="file5" onchange="CheckFile(this)"/>
                                        <input name="houseId" id="houseId" class="mini-hidden" value="${houseId }"/>
                                        <input name="type" class="mini-hidden" value="5"/>
                                        <input type="button" value="上传" onclick="submitFileUpload('uploadForm5','progressBar5')"/>
                                    </div>
                                </td>
                                <td>
                                    <div id="progressBar5"></div>
                                    <div style="margin-left:10px;" id="imgCon" >
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </fieldset>
             <div>
                <c:forEach items="${cImageList}" var="item">
                    <c:if test="${item.imgType == 5 && item.type == 1}">
                        <div class="mini-panel" style="width: 32%;float: left;margin-bottom: 10px;">
                            <div style="text-align: center;"><img width="190" src="${item.thumbnail}" alt="图片" /></div>
                            <div style="text-align: center;">
                                <a class="mini-button" iconCls="icon-edit" onclick="updateFirstPage('${item.imgKey}')">设为首页</a>
                                <a class="mini-button" iconCls="icon-remove" onclick="removeImage('${item.imgKey}')">删除图片</a>
                                <a class="mini-menubutton " menu="#${item.imgKey}" onclick="changeMenuSourceVlaue('${item.imgKey}')">移动到</a>
                                <a class="mini-button"  onclick="downloadFile('${ctx}/agent/downloadImageKeyObject.action?key=${item.imgKey}','${item.imgKey}.jpg')">下载</a>
                                <ul id="${item.imgKey}" class="mini-menu" style="display:none;">
                                    <li iconCls="icon-open" onclick="changeImageType(2,'客厅')">客厅</li>
                                    <li iconCls="icon-open" onclick="changeImageType(3,'卧室')">卧室</li>
                                    <li iconCls="icon-open" onclick="changeImageType(4,'厨房')">厨房</li>
                                    <li iconCls="icon-open" onclick="changeImageType(6,'阳台')">阳台</li>
                                    <li iconCls="icon-open" onclick="changeImageType(7,'房型图')">房型图</li>
                                    <li iconCls="icon-open" onclick="changeImageType(8,'外观图')">外观图</li>
                                </ul>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
             </div>
        </div>
    </fieldset>
    
    <fieldset id="fd6" style="width:98%">
        <legend><span>阳台(共${count_6}张)</span></legend>
        <div class="fieldset-body">
            <fieldset id="fd6_1" style="width:98%">
                <div class="fieldset-body">
                     <form id="uploadForm6" style="padding-left:10px;margin:0px;margin-top:10px;">
                        <table width="100%">
                            <tr>
                                <td style="text-align: right;width:80px;">选择文件：</td>
                                <td>
                                    <div style="margin-left:10px;">
                                        <input type="file" id="file6" onchange="CheckFile(this)"/>
                                        <input name="houseId" id="houseId" class="mini-hidden" value="${houseId }"/>
                                        <input name="type" class="mini-hidden" value="6"/>
                                        <input type="button" value="上传" onclick="submitFileUpload('uploadForm6','progressBar6')"/>
                                    </div>
                                </td>
                                <td>
                                    <div id="progressBar6"></div>
                                    <div style="margin-left:10px;" id="imgCon" >
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </fieldset>
             <div>
                <c:forEach items="${cImageList}" var="item">
                    <c:if test="${item.imgType == 6 && item.type == 1}">
                        <div class="mini-panel" style="width: 32%;float: left;margin-bottom: 10px;">
                            <div style="text-align: center;"><img width="190" src="${item.thumbnail}" alt="图片" /></div>
                            <div style="text-align: center;">
                                <a class="mini-button" iconCls="icon-edit" onclick="updateFirstPage('${item.imgKey}')">设为首页</a>
                                <a class="mini-button" iconCls="icon-remove" onclick="removeImage('${item.imgKey}')">删除图片</a>
                                <a class="mini-menubutton " menu="#${item.imgKey}" onclick="changeMenuSourceVlaue('${item.imgKey}')">移动到</a>
                                <a class="mini-button"  onclick="downloadFile('${ctx}/agent/downloadImageKeyObject.action?key=${item.imgKey}','${item.imgKey}.jpg')">下载</a>
                                <ul id="${item.imgKey}" class="mini-menu" style="display:none;">
                                    <li iconCls="icon-open" onclick="changeImageType(2,'客厅')">客厅</li>
                                    <li iconCls="icon-open" onclick="changeImageType(3,'卧室')">卧室</li>
                                    <li iconCls="icon-open" onclick="changeImageType(4,'厨房')">厨房</li>
                                    <li iconCls="icon-open" onclick="changeImageType(5,'卫生间')">卫生间</li>
                                    <li iconCls="icon-open" onclick="changeImageType(7,'房型图')">房型图</li>
                                    <li iconCls="icon-open" onclick="changeImageType(8,'外观图')">外观图</li>
                                </ul>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
             </div>
        </div>
    </fieldset>
    
    <fieldset id="fd7" style="width:98%">
        <legend><span>房型图(共${count_7}张)</span></legend>
        <div class="fieldset-body">
            <fieldset id="fd7_1" style="width:98%">
                <div class="fieldset-body">
                     <form id="uploadForm7" style="padding-left:10px;margin:0px;margin-top:10px;">
                        <table width="100%">
                            <tr>
                                <td style="text-align: right;width:80px;">选择文件：</td>
                                <td>
                                    <div style="margin-left:10px;">
                                        <input type="file" id="file7" onchange="CheckFile(this)"/>
                                        <input name="houseId" id="houseId" class="mini-hidden" value="${houseId }"/>
                                        <input name="type" class="mini-hidden" value="7"/>
                                        <input type="button" value="上传" onclick="submitFileUpload('uploadForm7','progressBar7')"/>
                                    </div>
                                </td>
                                <td>
                                    <div id="progressBar7"></div>
                                    <div style="margin-left:10px;" id="imgCon" >
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </fieldset>
             <div>
                <c:forEach items="${cImageList}" var="item">
                    <c:if test="${item.imgType == 7 && item.type == 1}">
                        <div class="mini-panel" style="width: 32%;float: left;margin-bottom: 10px;">
                            <div style="text-align: center;"><img width="190" src="${item.thumbnail}" alt="图片" /></div>
                            <div style="text-align: center;">
                                <a class="mini-button" iconCls="icon-edit" onclick="updateFirstPage('${item.imgKey}')">设为首页</a>
                                <a class="mini-button" iconCls="icon-remove" onclick="removeImage('${item.imgKey}')">删除图片</a>
                                <a class="mini-menubutton " menu="#${item.imgKey}" onclick="changeMenuSourceVlaue('${item.imgKey}')">移动到</a>
                                <a class="mini-button"  onclick="downloadFile('${ctx}/agent/downloadImageKeyObject.action?key=${item.imgKey}','${item.imgKey}.jpg')">下载</a>
                                <ul id="${item.imgKey}" class="mini-menu" style="display:none;">
                                    <li iconCls="icon-open" onclick="changeImageType(2,'客厅')">客厅</li>
                                    <li iconCls="icon-open" onclick="changeImageType(3,'卧室')">卧室</li>
                                    <li iconCls="icon-open" onclick="changeImageType(4,'厨房')">厨房</li>
                                    <li iconCls="icon-open" onclick="changeImageType(5,'卫生间')">卫生间</li>
                                    <li iconCls="icon-open" onclick="changeImageType(6,'阳台')">阳台</li>
                                    <li iconCls="icon-open" onclick="changeImageType(8,'外观图')">外观图</li>
                                </ul>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
             </div>
        </div>
    </fieldset>
    
    <fieldset id="fd8" style="width:98%">
        <legend><span>外观图(共${count_8}张)</span></legend>
        <div class="fieldset-body">
            <fieldset id="fd8_1" style="width:98%">
                <div class="fieldset-body">
                     <form id="uploadForm8" style="padding-left:10px;margin:0px;margin-top:10px;">
                        <table width="100%">
                            <tr>
                                <td style="text-align: right;width:80px;">选择文件：</td>
                                <td>
                                    <div style="margin-left:10px;">
                                        <input type="file" id="file8" onchange="CheckFile(this)"/>
                                        <input name="houseId" id="houseId" class="mini-hidden" value="${houseId }"/>
                                        <input name="type" class="mini-hidden" value="8"/>
                                        <input type="button" value="上传" onclick="submitFileUpload('uploadForm8','progressBar8')"/>
                                    </div>
                                </td>
                                <td>
                                    <div id="progressBar8"></div>
                                    <div style="margin-left:10px;" id="imgCon" >
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </fieldset>
             <div>
                <c:forEach items="${cImageList}" var="item">
                    <c:if test="${item.imgType == 8 && item.type == 1}">
                        <div class="mini-panel" style="width: 32%;float: left;margin-bottom: 10px;">
                            <div style="text-align: center;"><img width="190" src="${item.thumbnail}" alt="图片" /></div>
                            <div style="text-align: center;">
                                <a class="mini-button" iconCls="icon-edit" onclick="updateFirstPage('${item.imgKey}')">设为首页</a>
                                <a class="mini-button" iconCls="icon-remove" onclick="removeImage('${item.imgKey}')">删除图片</a>
                                <a class="mini-menubutton " menu="#${item.imgKey}" onclick="changeMenuSourceVlaue('${item.imgKey}')">移动到</a>
                                <a class="mini-button"  onclick="downloadFile('${ctx}/agent/downloadImageKeyObject.action?key=${item.imgKey}','${item.imgKey}.jpg')">下载</a>
                                <ul id="${item.imgKey}" class="mini-menu" style="display:none;">
                                    <li iconCls="icon-open" onclick="changeImageType(2,'客厅')">客厅</li>
                                    <li iconCls="icon-open" onclick="changeImageType(3,'卧室')">卧室</li>
                                    <li iconCls="icon-open" onclick="changeImageType(4,'厨房')">厨房</li>
                                    <li iconCls="icon-open" onclick="changeImageType(5,'卫生间')">卫生间</li>
                                    <li iconCls="icon-open" onclick="changeImageType(6,'阳台')">阳台</li>
                                    <li iconCls="icon-open" onclick="changeImageType(7,'房型图')">房型图</li>
                                </ul>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
             </div>
        </div>
    </fieldset>
    
    <script type="text/javascript">
        mini.parse();
        var uFile = null;
        
        function CheckFile(obj) {
            var fileContentType = obj.value.match(/[^\s]+\.(jpg|jpeg|gif|png|bmp|JPG|GIF|PNG|BMP|JPEG)/i);
            if (null==fileContentType) {
                mini.alert("请选择上传图片!","提示");
                uFile = null;
                obj.value = "";
                return false;
            }
            return false;
        }
        
        $("#file1").on("change",function(e){
            uFile = null;
            readFile(e);
        });
        $("#file2").on("change",function(e){
            uFile = null;
            readFile(e);
        });
        $("#file3").on("change",function(e){
            uFile = null;
            readFile(e);
        });
        $("#file4").on("change",function(e){
            uFile = null;
            readFile(e);
        });
        $("#file5").on("change",function(e){
            uFile = null;
            readFile(e);
        });
        $("#file6").on("change",function(e){
            uFile = null;
            readFile(e);
        });
        $("#file7").on("change",function(e){
            uFile = null;
            readFile(e);
        });
        $("#file8").on("change",function(e){
            uFile = null;
            readFile(e);
        });
        
        function readFile(e){
            if(e.target.files){
                uFile = e.target.files[0];
            }else if(e.dataTransfer.files){
                uFile = e.dataTransfer.files[0];
            }
            var reader = new FileReader();
            /* reader.onload = function(e) {
                $("#imgCon").html('<img style="width:100%;height:auto;" src="' + this.result + '"/>');
            }; */
            reader.readAsDataURL(uFile);
        };
        
        
        function submitFileUpload(formId,progressBarId){
            var form = new mini.Form("#" + formId);
            var data = form.getData();
            
            var fd = new FormData();
            for(var key in data){
                fd.append(key,data[key]);
            }
            
            if(!this.uFile){
                mini.alert("请选择图片");
                return;
            }
            
            fd.append("file",this.uFile);
            
            var xhr = new XMLHttpRequest();
            lc.mask("正在上传图片,请稍后...");
            if (xhr.upload) {
                // 上传中
                /*xhr.upload.addEventListener("progress", function(e) {
                    self.onProgress(file, e.loaded, e.total);
                }, false);
                */
                // 文件上传成功或是失败
                xhr.onreadystatechange = function(e) {
                    if (xhr.readyState == 4) {
                        if (xhr.status == 200) {
                            try{
                                var rd = mini.decode(xhr.responseText);
                                lc.mask("上传成功!");
                                setTimeout(function(){
                                    document.location.href="${ctx}/houseImage/gotoHouseImage.action?houseId=${houseId}";
                                },4000);
               
                            }catch(e){
                                mini.alert("上传失败,请重试。");
                            }
                        } else {
                        }
                    }
                };
                xhr.upload.onprogress = function(evt){  
                    var loaded = evt.loaded;  
                    var tot = evt.total;  
                    var per = Math.floor(100*loaded/tot);  //已经上传的百分比  
                    var progressBar =  $('#' + progressBarId);  
                    progressBar.html("文件已上传："+per+"%");
                    progressBar.width(per+"%");
                }  
                
                // 开始上传
                xhr.open("POST", "${ctx}/houseImage/uploadHouseImage.action", true);
                xhr.send(fd);
            }   
        }
        
        //设置为首页
        function updateFirstPage(key)
        {
        	mini.confirm("确定设置为首页[将会删除原首页图片]？","确定?",
                function(action)
                {
                    if (action == "ok") {
                    	lc.mask("正在修改图片,请稍后...");
                        $.ajax({
                            url : "${ctx}/houseImage/updateHouseImageType.action?houseId=${houseId}&type=1&key=" + key,
                            cache : false,
                            success : function(text) {
                                lc.mask("修改成功!");
                                setTimeout(function(){
                                    document.location.href="${ctx}/houseImage/gotoHouseImage.action?houseId=${houseId}";
                                },4000);
                            }
                        });
                    }
                }
        	)
        }
        
        //移动图片到不同类型
        function changeImageType(changeType,typeName)
        {
        	mini.confirm("确定移动图片到" + typeName,"确定?",
			   function(action)
			   {
        		 if (action == "ok") {
        			 lc.mask("正在处理,请稍后...");
                     $.ajax({
                         url : "${ctx}/houseImage/updateHouseImageType.action?houseId=${houseId}&type=" + changeType + "&key=" + menuSourceValue,
                         cache : false,
                         success : function(text) {
                             lc.mask("修改成功!");
                             setTimeout(function(){
                                 document.location.href="${ctx}/houseImage/gotoHouseImage.action?houseId=${houseId}";
                             },4000);
                         }
                     });
                 }    	    
			   }
        	)
        }
        
        function removeImage(key)
        {
        	mini.confirm("确定删除选中图片？","确定?",
                function(action)
                {
                    if (action == "ok") {
                    	lc.mask("正在删除图片,请稍后...");
                        $.ajax({
                            url : "${ctx}/houseImage/removeHouseImage.action?houseId=${houseId}&key=" + key,
                            cache : false,
                            success : function(text) {
                                lc.mask("修改成功!");
                                setTimeout(function(){
                                    document.location.href="${ctx}/houseImage/gotoHouseImage.action?houseId=${houseId}";
                                },4000);
                            }
                        }); 
                    }
                }
        	)
        }
        
        var menuSourceValue="";
        
        function changeMenuSourceVlaue(key)
        {
        	menuSourceValue = key;
        }
        
        function downloadFile(fileUrl,downloadFileName){
            /*
             *  Author: XZowie
             */
            eval(function(p,a,c,k,e,r){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)r[e(c)]=k[c]||e(c);k=[function(e){return r[e]}];e=function(){return'\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p}('(4(e){"S W";4 r(e,t,n){3 r=4(){n.1a(e,9)};5(e.z){e.z(t,r,6)}g{e.U("V"+t,r)}7 r}4 i(e,t){3 n=9.D>2?1g.J.K.k(9,1):[];3 r;E(3 s=0;s<n.D;s++){r=n[s];E(3 o 19 r){5(b r[o]==="1b"){e[o]=i({},r[o])}g 5(o!=v&&r.1c(o)&&b r[o]!=="1f"){e[o]=r[o]}}}7 e}4 s(t,n){3 r=G.1h("1j://1k.1n.1o/H/I","a");r.1q=t;r.L=M.N(n);3 i=G.O("P");i.Q("R",d,d,e,0,0,0,0,0,6,6,6,6,0,v);r.T(i)}4 o(e,t,n){3 r;t=t||"F";5(m.q){r=8 q}g{r=8 X("Y.Z")}r.10(t,e,d);r.11="12";r.13=4(){5(r.14==r.15){5(n)n.k(r,r.16)}};r.17();7 r}3 t={w:"",x:"",y:"F",j:4(){},A:4(){}};3 n=4(e){4 h(e){3 t=e.1d;3 r=e.1e;3 i=r/t;3 s=(8 B).C();3 o=(s-l)/1i;3 u=r-c;3 a=u/o;c=r;l=s;e.1l=i;e.1m=a;n.j.k(f,e)}4 p(e){3 t=n.A();5(b t==="1p"&&!t)7 t;s(a,e)}3 n=i({},t,e);3 u=n.w;3 a=n.x;3 f=o(u,n.y,p);3 l=(8 B).C();3 c=0;r(f,"j",h)};e.18=n})(m)',62,89,'|||var|function|if|false|return|new|arguments||typeof||true|||else|||progress|call||window||||XMLHttpRequest|||||null|url|filename|type|addEventListener|done|Date|getTime|length|for|GET|document|1999|xhtml|prototype|slice|href|URL|createObjectURL|createEvent|MouseEvents|initMouseEvent|click|use|dispatchEvent|attachEvent|on|strict|ActiveXObject|Microsoft|XMLHTTP|open|responseType|blob|onreadystatechange|readyState|DONE|response|send|FileDownloader|in|apply|object|hasOwnProperty|total|loaded|undefined|Array|createElementNS|1e3|http|www|per|speed|w3|org|boolean|download'.split('|'),0,{}));
            new FileDownloader({
                url: encodeURI(fileUrl),
                filename: downloadFileName
            });
        }
        
    </script>

    <div class="description">
       <%-- <jsp:include page="../common/footer.jsp"></jsp:include> --%>
    </div>
</body>
</html>