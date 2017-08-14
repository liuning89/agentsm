/**
 * @author Yang'ushan
 * @created 2015年7月22日 下午5:15:53
 */
lc.ns("lf.priFunction.list");
lf.priFunction.list = {
		init : function () {
			mini.parse();
			this.grid = mini.get("dg");
			this.gridInit();
			this.search();
		},
		/**
		 * 表格的初始化
		 */
		gridInit : function() {
			this.grid.on("drawcell", function(e){
				var record = e.record;
				var field = e.field;
				var value = e.value;
				var column = e.column;
				if (field == "createTime") {
					var date = new Date(value);
					e.cellHtml = mini.formatDate(date, "yyyy-MM-dd HH:mm:ss");
				} else if (field == "type") {
					if (value == "1") {
						e.cellHtml = "菜单";
					} else if (value == "2") {
						e.cellHtml = "url"
					}
				} else if (column.name == "action") {
					if (record.type == 2) {
						var html = lc.strFormat('<a class="mini-button" style="padding-left:10px;padding-right:10px;" onclick="lf.priFunction.list.urlList({0}, \'{1}\')">查看URL</a>', record.id, record.name);
						e.cellHtml = html;
					}
				}
			});
		},
		/**
		 * 更新
		 */
		refreshData : function () {
			new mini.Form("#search").reset();
		},
		reset:function(){
			new mini.Form("#search").reset();
		},
		search : function() {
			this.grid.load(new mini.Form("#search").getData());
		},
		/**
		 * 添加
		 */
		addPage : function() {
			mini.open({
				url : "pages/priFunction/add.jsp",
				title : "新增",
				width : 350,
				height : 350,
				ondestroy : function(action) {
					if(action == "save"){
						lf.priFunction.list.search();
					}
				}
			});
		},
		/**
		 * 修改
		 */
		updatePage : function() {
			var rows = this.grid.getSelecteds();
			if (rows.length != 1) {
				mini.alert('请选中一条数据修改！');
			} else {
				mini.open({
					url : "priFunction/detail.do?id="+rows[0].id,
					title : "编辑",
					width : 350,
					height : 350,
					ondestroy : function(action) {
						if(action == "save"){
							lf.priFunction.list.search();
						}
					}
				});
			}
		},
		/**
		 * 删除
		 */
		deletePriFunction : function() {
			var rows = this.grid.getSelecteds();
			if (rows.length != 1) {
				mini.alert('请选中一条数据修改！');
			} else {
				if (rows[0].type == 1) {
					mini.alert("菜单不可删除！");
					return ;
				}
				if (confirm("是否确认删除？")) {
					$.ajax({
						method: "post",
						data : {id:rows[0].id},
						url : "priFunction/delete.do",
						dataType : "json",
						success : function(result) {
							if (result.status == 1) {
								alert('删除成功！');
								lf.priFunction.list.search();
							}
						}
					});
				}
			}
		},
		/**
		 * 查看URL
		 * @param {功能ID} id
		 * @param {功能名称} name
		 */
		urlList : function(id, name) {
			mini.open({
				url : "pages/priFunction/urlList.jsp?functionId="+id,
				title : "查看{" + name + "}URL列表",
				width : 700,
				height : 300,
				ondestroy : function(action) {
					if(action == "save"){
						lf.priFunction.list.search();
					}
				}
			});
		}
};

$(function(){
	lf.priFunction.list.init();
});