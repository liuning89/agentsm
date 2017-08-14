/**
 * @author Yang'ushan
 * @created 2015年7月27日 下午2:39:53
 */
lc.ns("lf.menu.list");
lf.menu.list = {
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
				} else if (field == "isLeaf") {
					if (value == "1") {
						e.cellHtml = "是";
					} else if (value == "0") {
						e.cellHtml = "否"
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
				url : "pages/menu/add.jsp",
				title : "新增",
				width : 400,
				height : 330,
				ondestroy : function(action) {
					if(action == "save"){
						lf.menu.list.search();
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
					url : "menu/detail.do?id="+rows[0].id,
					title : "编辑",
					width : 400,
					height : 330,
					ondestroy : function(action) {
						if(action == "save"){
							lf.menu.list.search();
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
				if (confirm("删除菜单，会删除该菜单以下的所有子菜单，是否确认删除？")) {
					$.ajax({
						method: "post",
						data : {id:rows[0].id},
						url : "menu/delete.do",
						dataType : "json",
						success : function(result) {
							if (result.status == 1) {
								alert('删除成功！');
								lf.menu.list.search();
							}
						}
					});
				}
			}
		}
};

$(function(){
	lf.menu.list.init();
});