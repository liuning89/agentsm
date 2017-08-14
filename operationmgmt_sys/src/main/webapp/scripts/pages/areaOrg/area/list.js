/**
 * @author Yang'ushan
 * @created 2015年8月4日 下午4:45:53
 */
lc.ns("lf.areaOrg.area.list");
lf.areaOrg.area.list = {
		init : function () {
			mini.parse();
			this.grid = mini.get("dg");
			this.gridInit();
			var cityId = mini.get("cityId");
			cityId.select(0);
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
					if (value != null && value != "") {
						var date = new Date(value);
						e.cellHtml = mini.formatDate(date, "yyyy-MM-dd HH:mm:ss");
					}
				} else if (field == "type") {
					if (value == null || value == 1) {
						e.cellHtml = "否";
					} else {
						e.cellHtml = "是";
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
			mini.get("cityId").select(0);
		},
		search : function() {
			this.grid.load(new mini.Form("#search").getData());
		},
		/**
		 * 添加
		 */
		addPage : function() {
			mini.open({
				url : "pages/areaOrg/area/add.jsp",
				title : "新增",
				width : 400,
				height : 350,
				ondestroy : function(action) {
					if(action == "save"){
						lf.areaOrg.area.list.search();
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
					url : "areaOrg/area/detail.do?id="+rows[0].id,
					title : "编辑",
					width : 400,
					height : 350,
					ondestroy : function(action) {
						if(action == "save"){
							lf.areaOrg.area.list.search();
						}
					}
				});
			}
		},
		/**
		 * 删除
		 */
		deleteAreaOrg : function() {
			var rows = this.grid.getSelecteds();
			if (rows.length != 1) {
				mini.alert('请选中一条数据修改！');
			} else {
				if (confirm("删除架构，会删除该架构以下的所有子架构，是否确认删除？")) {
					$.ajax({
						method: "post",
						data : {
							"code":rows[0].code,
							"level":rows[0].level
						},
						url : "areaOrg/delete.do",
						dataType : "json",
						success : function(result) {
							if (result.status == 1) {
								alert('删除成功！');
								lf.areaOrg.area.list.search();
							}
						}
					});
				}
			}
		}
};

$(function(){
	lf.areaOrg.area.list.init();
});