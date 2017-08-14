<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>经纪人列表</title>
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

<body>
    <form id="form1" method="post">
        <input name="id" class="mini-hidden" />
        <fieldset style="border: solid 1px #aaa; padding: 3px;">
            <legend>基本信息</legend>
            <div style="padding-left: 11px; padding-bottom: 5px;">
            <form id="form1" style="padding-left:10px;margin:0px;margin-top:10px;">
                <table style="table-layout: fixed;">
                    <tr>
                        <td>
                            <input class="mini-checkbox" id="isPublish" name="type" trueValue="1"   />发布人
                            <input class="mini-checkbox" id="isKey" name="type" trueValue="2"  />钥匙人
                            <input class="mini-checkbox" id="isComm" name="type" trueValue="3" />委托人
                            <input class="mini-checkbox" id="isPicture" name="type" trueValue="4"  />实景人
                            <input class="mini-checkbox" id="isBuy" name="type" trueValue="5"  />客源
                            <input class="mini-checkbox" id="isHouseSee" name="type" trueValue="6"  />约看客户
                        </td>
                    </tr>
                    <tr>
                        <td>转入人公司：</td>
                        <td>
                        <input id="companyId" class="mini-combobox" required="true" style="width:135px;" textField="companyName" valueField="id" 
                                                url="${ctx}/company/getCompany.action" value="" showNullItem="true"  allowInput="true"
                                                onvalidation="onComboValidation" onvaluechanged="onComChanged" />
                        </td>
                    </tr>
                    <tr>
                        <td>转入人门店:</td>
                        <td><input id="storeId" class="mini-combobox" required="true" style="width:135px;" textField="storeName" valueField="id" 
                                                url="${ctx}/store/getStoreListByCompanyId.action" value="" showNullItem="true"  allowInput="true"
                                                onvalidation="onComboValidation" onvaluechanged="onStoreChanged" /></td>
                    </tr>
                    <tr>
                        <td>转入人姓名:</td>
                        <td><input id="newAgentId" class="mini-combobox" required="true" style="width:135px;" textField="realName" valueField="id" 
                                                url="${ctx}/agent/getAgentListByPars.action?storeId=0" value="" showNullItem="true"  allowInput="true"
                                                onvalidation="onComboValidation"  /></td>
                    </tr>
                </table>
                <!-- <input class="mini-hidden" name="oldAgentId" id = "oldAgentId"/> -->
            </form>
                
            </div>
        </fieldset>
        <div style="text-align: center; padding: 10px;">
            <a class="mini-button" onclick="onOk" style="width: 60px; margin-right: 20px;">确定
            </a> <a class="mini-button" onclick="onCancel" style="width: 60px;">取消</a>
        </div>
    </form>
     
    
    <script type="text/javascript">
        mini.parse();
        var form = new mini.Form("form1");
        
        function onOk(e) {
            SaveData();
        }
        
        
        function onComboValidation(e) {
            var items = this.findItems(e.value);
            if (!items || items.length == 0) {
                this.setValue('');
            }
        }
        
        function onCancel(e) {
            CloseWindow("cancel");
        }
        
        function CloseWindow(action) {
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow)
                return window.CloseOwnerWindow(action);
            else
                window.close();
        }
        
        function SaveData() {
        	
        	var types =[];
        	
            if(mini.get("isPublish").getChecked())
            {
                types.push(mini.get("isPublish").getFormValue());
            }
            if(mini.get("isKey").getChecked())
            {
                types.push(mini.get("isKey").getFormValue());
            }
            if(mini.get("isComm").getChecked())
            {
                types.push(mini.get("isComm").getFormValue());
            }
            if(mini.get("isPicture").getChecked())
            {
                types.push(mini.get("isPicture").getFormValue());
            }
            if(mini.get("isBuy").getChecked())
            {
                types.push(mini.get("isBuy").getFormValue());
            }
            if(mini.get("isHouseSee").getChecked())
            {
                types.push(mini.get("isHouseSee").getFormValue());
            }
            
            if(types.length==0){
                mini.alert("请选择转移的资源类型");
                return;
            }
             
            var o = form.getData();
            form.validate();
            if (form.isValid() == false)
                return;
            var json = mini.encode([ o ]);
            $.ajax({
                url : "${ctx}/agent/resourceTransfer.action",
                type : 'post',
                data: "oldAgentId=" + o.id + "&types="+types+"&newAgentId="+mini.get("newAgentId").getValue() ,
                cache : false,
                success : function(text) {
                    CloseWindow("save");
                },
                error : function(jqXHR, textStatus,
                        errorThrown) {
                    alert(jqXHR.responseText);
                    CloseWindow();
                }
            });
        }
        
        function onComChanged(e) {
            var comCbo = mini.get("companyId");
            var storeCbo = mini.get("storeId");
            var id = comCbo.getValue();
            storeCbo.select(0);
            var url = "${ctx}/store/getStoreListByCompanyId.action";
            if(id != null && id != "")
            {
                url += "?companyId=" + id;
            }
            storeCbo.setUrl(url);
            storeCbo.select(0);
            
            var newAgentCbo = mini.get("newAgentId");
            newAgentCbo.setUrl("${ctx}/agent/getAgentListByPars.action?storeId=0");
        }
        
        function onStoreChanged(e) {
        	var storeCbo = mini.get("storeId");
            var newAgentCbo = mini.get("newAgentId");
            var id = storeCbo.getValue();
            newAgentCbo.select(0);
            var url = "${ctx}/agent/getAgentListByPars.action";
            if(id != null && id != "")
            {
                url += "?storeId=" + id;
            }
            newAgentCbo.setUrl(url);
            newAgentCbo.select(0);
        }
         
        //标准方法接口定义
        function SetData(data) {
            if (data.action == "edit") {
                //跨页面传递的数据对象，克隆后才可以安全使用
                data = mini.clone(data);
                $.ajax({
                    url : "${ctx}/agent/getAgentById.action?id=" + data.id,
                    cache : false,
                    async : false,//同步
                    success : function(text) {
                        var o = mini.decode(text);
                        form.setData(o);
                        form.setChanged(false);
                    }
                });
                
                $.ajax({
                    url:'${ctx}/agent/isResourceTransfer.action',
                    type:"POST",
                    dataType:"json",
                    data: "userId=" + data.id,
                    async:false,//同步
                    success : function(data){
                        if(data[0]==1){
                            mini.get("#isPublish").enable();
                            mini.get("#isPublish").setChecked(true);
                        }else{
                        	mini.get("#isPublish").disable();
                        	mini.get("#isPublish").setChecked(false);
                        }
                        if(data[1]==1){
                        	mini.get("#isKey").enable();
                        	mini.get("#isKey").setChecked(true);
                        }else{
                        	mini.get("#isKey").disable();
                        }
                        if(data[2]==1){
                        	mini.get("#isComm").enable();
                        	mini.get("#isComm").setChecked(true);
                        }else{
                        	mini.get("#isComm").disable();
                        }
                        if(data[3]==1){
                        	mini.get("#isPicture").enable();
                        	mini.get("#isPicture").setChecked(true);
                        }else{
                        	mini.get("#isPicture").disable();
                        }
                        if(data[4]==1){
                        	mini.get("#isBuy").enable();
                        	mini.get("#isBuy").setChecked(true);
                        }else{
                        	mini.get("#isBuy").disable();
                        }
                        if(data[5]==1){
                            mini.get("#isHouseSee").enable();
                            mini.get("#isHouseSee").setChecked(true);
                        }else{
                            mini.get("#isHouseSee").disable();
                        }
                    }
                });
            }
        }
    </script>
	
</body>
<!-- END BODY -->
</html>
