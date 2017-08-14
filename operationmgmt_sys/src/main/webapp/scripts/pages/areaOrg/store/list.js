/**
 * @author Yang'ushan
 * @created 2015年8月5日 上午11:00:53
 */
lc.ns("lf.areaOrg.store.list");
lf.areaOrg.store.list = {
		init : function () {
			mini.parse();
			this.grid = mini.get("dg");
			this.gridInit();
			this.cityId = mini.get("cityId");
			this.districtId = mini.get("districtId");
			this.townId = mini.get("townId");
			this.cityId.on("valuechanged", lc.proxyFn(function(){
				this.loadDistrict();
			},this));
			this.districtId.on("valuechanged", lc.proxyFn(function(){
				this.loadTown();
			},this));
			this.cityId.select(0);
			this.loadDistrict();
			this.search();
		},
		loadDistrict:function() {
			var cityId = this.cityId.getValue();
			this.districtId.load(lc.rootPath(lc.strFormat("dicAreaNew/getDicAreaNewByParentId.do?parentId={0}",cityId)));
			this.districtId.setData([{id:'',text:'所有'}].concat(this.districtId.getData()));
			this.districtId.select(0);
		},
		loadTown:function() {
			var districtId = this.districtId.getValue();
			this.townId.load(lc.rootPath(lc.strFormat("dicAreaNew/getDicAreaNewByParentId.do?parentId={0}",districtId)));
			this.townId.setData([{id:'',text:'所有'}].concat(this.townId.getData()));
			this.townId.select(0);
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
				} else if (field == "longitude_latitude") {
					if (record.longitude != null && record.longitude != "") {
						e.cellHtml = record.longitude + "," + record.latitude
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
			this.cityId.select(0);
		},
		search : function() {
			this.grid.load(new mini.Form("#search").getData());
		},
		/**
		 * 添加
		 */
		addPage : function() {
			mini.open({
				url : "pages/areaOrg/store/add.jsp",
				title : "新增",
				width : 800,
				height : 550,
				ondestroy : function(action) {
					if(action == "save"){
						lf.areaOrg.store.list.search();
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
					url : "areaOrg/store/detail.do?id="+rows[0].id,
					title : "编辑",
					width : 800,
					height : 550,
					ondestroy : function(action) {
						if(action == "save"){
							lf.areaOrg.store.list.search();
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
								lf.areaOrg.store.list.search();
							}
						}
					});
				}
			}
		}
};

$(function(){
	lf.areaOrg.store.list.init();
});