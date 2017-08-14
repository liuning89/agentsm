<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>加盟商列表</title>
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
  <table>
      <tr style="border-top:1px #DDD solid;">
          <td colspan="3" style="border-top:1px #DDD solid;"><h1>转公客规则</h1></td>
      </tr>
        <tr>
            <td>
                私客转店公客规则：
            </td>
            <td>
                <input id="publicToStore" class="mini-textbox" vtype="int" value=${rulePublic.publicToStore } intErrorText="请输入数字" style="width: 50px;" />
            </td>
            <td>
                天未添加跟进或带看
            </td>
        </tr>
      <tr>
          <td style="border-bottom:1px #DDD solid;">
             店公客转区公客规则
          </td>
          <td style="border-bottom:1px #DDD solid;">
              <input id="storeToArea" class="mini-textbox" vtype="int" intErrorText="请输入数字" value=${rulePublic.storeToArea } style="width: 50px;" />
          </td>
          <td style="border-bottom:1px #DDD solid;">
              天未认领
          </td>
      </tr>


      <tr>
          <td colspan="3" style="border-top:1px #DDD solid;"><h1>认领数规则</h1></td>
      </tr>
      <tr>
          <td colspan="2">
              经纪人每天认领店公客每天认领数：

          </td>
          <td>
              <input id="storeCount" class="mini-textbox" vtype="int" value=${rulePublic.storeCount } intErrorText="请输入数字" style="width: 50px;" />
          </td>
      </tr>
      <tr>
          <td colspan="2" style="border-bottom:1px #DDD solid;">
              经纪人每天认领区公客每天认领数：
          </td>
          <td style="border-bottom:1px #DDD solid;">
              <input id="areaCount" class="mini-textbox" vtype="int" value=${rulePublic.areaCount } intErrorText="请输入数字" style="width: 50px;" />
          </td>
      </tr>
     <!--  <tr style="text-align: center">
          <td colspan="2" >
              <a class="mini-button" onclick="onSaveData()" style="width:60px;margin-right:20px;">保存</a>
          </td>
          <td colspan="2" >
              <a class="mini-button" onclick="onCancel()" style="width:60px;margin-right:20px;">关闭</a>
          </td>

      </tr> -->
      
      
      <!-- 查看数 -->
      <tr>
          <td colspan="3" style="border-top:1px #DDD solid;"><h1>查看手机号次数规则</h1></td>
      </tr>
      <tr>
          <td colspan="2">
              店公客经纪人每天查看手机数：

          </td>
          <td>
              <input id="viewStoreCount" class="mini-textbox" vtype="int" value=${rulePublic.viewStoreCount } intErrorText="请输入数字" style="width: 50px;" />个
          </td>
      </tr>
      <tr>
          <td colspan="2" style="border-bottom:1px #DDD solid;">
              区公客经纪人每天查看手机数：
          </td>
          <td style="border-bottom:1px #DDD solid;">
              <input id="viewAreaCount" class="mini-textbox" vtype="int" value=${rulePublic.viewAreaCount } intErrorText="请输入数字" style="width: 50px;" />个
          </td>
      </tr>
      <tr style="text-align: center">
          <td colspan="2" >
              <a class="mini-button" onclick="onSaveData()" style="width:60px;margin-right:20px;">保存</a>
          </td>
          <td colspan="2" >
              <a class="mini-button" onclick="onCancel()" style="width:60px;margin-right:20px;">关闭</a>
          </td>
      </tr>
  </table>
    
    <script type="text/javascript">
        mini.parse();
        function onSaveData(){
            if(mini.get("publicToStore").getValue() == "")//私客转店公客规则不能为空rulePublic
            {
                mini.alert("私客转店公客规则不能为空");
                return false;
            }
            if(mini.get("storeToArea").getValue() == "")//店公客转区公客规则
            {
                mini.alert("店公客转区公客规则不能为空");
                return false;
            }
            if(mini.get("storeCount").getValue() == "")//经纪人每天认领店公客每天认领数
            {
                mini.alert("经纪人每天认领店公客每天认领数不能为空");
                return false;
            }
            if(mini.get("areaCount").getValue() == "")//经纪人每天认领区公客每天认领数
            {
                mini.alert("经纪人每天认领区公客每天认领数不能为空");
                return false;
            }
            
            if(mini.get("viewStoreCount").getValue() == "")//
            {
                mini.alert("店公客经纪人每天查看手机数不能为空");
                return false;
            }
            if(mini.get("viewAreaCount").getValue() == "")//
            {
                mini.alert("区公客经纪人每天查看手机数不能为空");
                return false;
            }
            
            lc.mask("正在处理中,请稍后...");
            $.ajax({
                url : "${ctx}/ruleMng/savePublicRule.action",
                cache : false,
                dataType : 'text',
                data:{publicToStore:mini.get("publicToStore").getValue(),
                    storeToArea:mini.get("storeToArea").getValue(),storeCount:mini.get("storeCount").getValue(),areaCount:mini.get("areaCount").getValue(),
                    viewStoreCount:mini.get("viewStoreCount").getValue(),viewAreaCount:mini.get("viewAreaCount").getValue()
                },
                type: "POST",
                success : function(d) {
                    if(d == 1){
                        lc.unmask();
                        mini.alert("保存成功!","成功",function(){
                            
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
    <div class="description">
       <%-- <jsp:include page="../common/footer.jsp"></jsp:include> --%>
    </div>
</body>
</html>