<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>账号列表</title>
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
<body >
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <div style="padding-bottom:5px;">
                <span>员工姓名：</span><input type="text" id="key"  />
                <span>员工电话：</span><input type="text" id="phone"  />
                <input type="button" value="查找" onclick="search()"/>
                <input type="button" value="重置" onclick="reset()"/>
            </div>
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                        <a class="mini-button" iconCls="icon-add" onclick="add()">增加</a>
                        <a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
                        <a class="mini-button" iconCls="icon-edit" onclick="changePassword()">修改密码</a>
                        <a class="mini-button" iconCls="icon-edit" onclick="disab()">冻结</a>
                        <a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>       
                    </td>
                </tr>
            </table>           
        </div>
            <!--撑满页面-->
        <div class="mini-fit" style="background:red;height:100px;">
            <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" 
                url="${ctx}/lfEmployee/getEmployeeList.action"  idField="id" allowResize="false"
                sizeList="[20,30,50,100]" pageSize="20">
                <div property="columns">
                    <!-- <div type="indexcolumn" style="display: none"></div> -->
                    <div field="name" width="120" headerAlign="center" allowSort="false">用户名</div>                            
                    <div field="gender" width="100" renderer="onGenderRenderer" align="center" headerAlign="center">性别</div>
                    <div field="grade" width="100" renderer="onGradeRenderer" align="center" headerAlign="center">角色</div>
                    <div field="mobile" width="100" headerAlign="center" allowSort="false">电话</div>   
                    <div field="createTime" width="120" renderer="onTimeRenderer" align="center" headerAlign="center"  allowSort="false">创建时间</div>  
                    <div field="status" width="100" renderer="onStatusRenderer" align="center" headerAlign="center">角色</div>
                </div>
            </div>   
        </div>
    
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
        function search() {
            var key = document.getElementById("key").value;
            var phone = document.getElementById("phone").value;
            grid.load({ loginName: key, phone:phone});
        }
        
        function reset()
        {
        	document.getElementById("key").value = "";
            document.getElementById("phone").value = "";
        }
        
        $("#key").bind("keydown", function (e) {
            if (e.keyCode == 13) {
                search();
            }
        });
        
        ///////////////////////////////////////////////////////
        var Genders = [{ id: 1, text: '男' }, { id: 2, text: '女'}];
        function onGenderRenderer(e) {
            for (var i = 0, l = Genders.length; i < l; i++) {
                var g = Genders[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        }
        ////////////////////////////////////////////////////
        var Grades = [{ id: 1, text: '普通' }, { id: 2, text: '管理员'}];
        function onGradeRenderer(e) {
            for (var i = 0, l = Grades.length; i < l; i++) {
                var g = Grades[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        }
        ////////////////////////////////////////////////////
        var Statuss = [{ id: 1, text: '正常' }, { id: 2, text: '冻结'},{ id: 0, text: '未知'}];
        function onStatusRenderer(e) {
            for (var i = 0, l = Statuss.length; i < l; i++) {
                var g = Statuss[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        }
        //////////////////////////////////////////////////
        function onTimeRenderer(e) {
            var value = e.value;
            value = new Date(value); 
            if (value) return mini.formatDate(value, 'yyyy-MM-dd HH:mm');
            return "";
        }
        
		function add() {
            mini.open({
                url: "${ctx}/lfEmployee/gotoEmployeeWindow.action",
                title: "新增员工", width: 600, height: 360,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "new"};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
        }
		
        function edit() {
            var row = grid.getSelected();
            if (row) {
                mini.open({
                    url: "${ctx}/lfEmployee/gotoEmployeeWindow.action",
                    title: "编辑员工", width: 600, height: 360,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { action: "edit", id: row.id };
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                        grid.reload();
                    }
                });
            } else {
                alert("请选中一条记录");
            }
        }
        
        function changePassword() {
            var row = grid.getSelected();
            if (row) {
                mini.open({
                    url: "${ctx}/lfEmployee/gotoPasswordChange.action",
                    title: "修改密码", width: 600, height: 360,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { action: "edit", id: row.id };
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                        grid.reload();
                    }
                });
            } else {
                alert("请选中一条记录");
            }
        }
        
        function disab()
        {
        	var row = grid.getSelected();
            if (row) {
            	$.ajax({
                    url: "${ctx}/lfEmployee/updateLfEmployeeGrade.action?id=" + row.id,
                    success: function (text) {
                        grid.reload();
                    },
                    error: function () {
                    }
                });
            } else {
                alert("请选中一条记录");
            }
        }
        
        function remove() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定删除选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.id);
                    }
                    var id = ids.join(',');
                    grid.loading("操作中，请稍后......");
                    $.ajax({
                        url: "${ctx}/lfEmployee/deleteLfEmployee.action?id=" +id,
                        success: function (text) {
                            grid.reload();
                        },
                        error: function () {
                        }
                    });
                }
            } else {
                alert("请选中一条记录");
            }
        }

    </script>

    <div class="description">
       <%-- <jsp:include page="../common/footer.jsp"></jsp:include> --%>
    </div>
</body>
</html>