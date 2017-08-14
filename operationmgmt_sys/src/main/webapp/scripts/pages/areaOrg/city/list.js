/**
 * @author Yang'ushan
 * @created 2015年8月3日 下午6:28:53
 */
lc.ns("lf.areaOrg.city.list");
lf.areaOrg.city.list = {
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
				url : "pages/areaOrg/city/add.jsp",
				title : "新增",
				width : 320,
				height : 270,
				ondestroy : function(action) {
					if(action == "save"){
						lf.areaOrg.city.list.search();
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
					url : "areaOrg/city/detail.do?id="+rows[0].id,
					title : "编辑",
					width : 320,
					height : 270,
					ondestroy : function(action) {
						if(action == "save"){
							lf.areaOrg.city.list.search();
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
								lf.areaOrg.city.list.search();
							}
						}
					});
				}
			}
		}
};

$(function(){
	lf.areaOrg.city.list.init();
});