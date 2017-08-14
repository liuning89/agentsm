/**
 * @author Yang'ushan
 * @created 2015年7月24日 下午4:38:53
 * @include "../../lf/lf.common.js"
 */
lc.ns("lf.priFunction.urlList");
var deleteUrlIds = new Array();
lf.priFunction.urlList = {
		init : function () {
			mini.parse();
			this.grid = mini.get("dg");
			this.functionId = mini.get("functionId");
			this.functionId.setValue(lc.queryParam("functionId"));
			this.search();
		},
		/**
		 * 搜索
		 */
		search : function() {
			this.grid.load({
				"functionId" : this.functionId.getValue(),
				"name" : mini.get("name").getValue()
			});
		},
		/**
		 * 添加一行
		 */
		add : function() {
			var newRow = {};
			this.grid.addRow(newRow, 0);
			this.grid.beginEditCell(newRow, "name");
		},
		/**
		 * 删除url
		 */
		deleteUrl : function() {
			var rows = this.grid.getSelecteds();
			if (rows.length > 0) {
				// 把这些id放入删除的数据里面
				for (var i = 0; i < rows.length;i ++) {
					if (rows[i].id != null && rows[i].id != "") {
						deleteUrlIds.push(rows[i].id);
					}
				}
				this.grid.removeRows(rows, true);
			} else {
				mini.alert("至少选择一条数据！");
				return ;
			}
		},
		/**
		 * 保存数据
		 */
		save : function() {
			var data = this.grid.getData();
			if (data == "" && deleteUrlIds.length == 0) {
				mini.alert("没有数据可保存！");
				return ;
			}
			if (confirm("是否确认保存？")) { // 确认保存
				$.ajax({
					method: "post",
					data : {
						"json":mini.encode(data),
						"functionId":this.functionId.getValue(),
						"deleteUrlIds":deleteUrlIds.join(",")
					},
					url : "priFunction/url/save.do",
					dataType : "json",
					success : function(result) {
						if (result.status == 1) {
							alert('保存成功！');
							deleteUrlIds = new Array(); // 清空删除编号列表
							lf.priFunction.urlList.CloseWindow("save");
							lf.priFunction.urlList.search();
						}
					}
				});
			}
		},
		/**
		 * 关闭窗口
		 */
		CloseWindow : function(action) {
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}
};

$(function(){
	lf.priFunction.urlList.init();
});