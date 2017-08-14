/**
 * @author Yang'ushan
 * @created 2015年7月28日 下午4:17:53
 */
lc.ns("lf.position.list");
lf.position.list = {
		init : function () {
			mini.parse();
			this.grid = mini.get("dg");
			this.gridInit();
			this.search();
			this.isShowCountry = mini.get("isShowCountry");
			if (this.isShowCountry) {
				this.isShowCountry.on("valuechanged", lc.proxyFn(function(e){
					this.changeCountry();
				}, this));
			}
		},
		/**
		 * 修改是否读取国家
		 */
		changeCountry : function() {
			if (this.isShowCountry.getValue() == "1") { // 显示国家
				mini.get("cityId").disable();
			} else { // 显示城市
				mini.get("cityId").enable();
			}
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
				if (field == "department") {
					if (value == "1") {
						e.cellHtml = "业务";
					} else if (value == "2") {
						e.cellHtml = "运营"
					} else if (value == "3") {
						e.cellHtml = "人事"
					} else if (value == "4") {
						e.cellHtml = "财务"
					} else if (value == "5") {
						e.cellHtml = "法务"
					}
				} else if (column.name == "action") {
					var html = lc.strFormat('<a class="mini-button" style="padding-left:10px;padding-right:10px;" onclick="lf.position.list.setUrl({0}, {1}, {2}, \'{3}\')">设置权限</a>' +
							'<a class="mini-button" style="padding-left:10px;padding-right:10px;margin-left:5px;" onclick="lf.position.list.setMenu({0}, {1}, {2}, \'{3}\')">设置菜单</a>'
							, record.department, record.level, record.cityId, record.name);
					e.cellHtml = html;
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
			mini.get("cityId").enable();
		},
		search : function() {
			this.grid.load(new mini.Form("#search").getData());
		},
		/**
		 * 设置URL
		 * @param {部门} department
		 * @param {等级} level
		 * @param {城市编号} cityId
		 * @param {城市} name
		 */
		setUrl : function(department, level, cityId, name) {
			$.ajax({
				method : "post", 
				url : "position/getAppnamesByDepartment.do",
				dataType: "json",
				data : "department="+department,
				success : function(result) {
					if (result.length == 1) { // 只有一个应用
						lf.position.list.openUrlList(result[0], level, department, cityId, name);
					} else { // 选择应用页面
						lf.position.list.openSelect(result, 1, level, department, cityId, name);
					}
				}
			});
		},
		/**
		 * 打开选择app
		 * @param {应用名称} apps
		 * @param {类型：1跳转到URL页面} type
		 * @param {等级} level
		 * @param {部门} department
		 * @param {城市编号} cityId
		 * @param {城市} name
		 */
		openSelect : function(apps, type, level, department, cityId, name) {
			mini.open({
				url : "pages/position2/selectAppname.jsp",
				title : "选择应用",
				width : 300,
				height : 150, 
				onload : function() { // 弹出页面加载完成
					var iframe = this.getIFrameEl();
					// 调用弹出页面方法进行初始化
					iframe.contentWindow.SetData(apps, type, level, department, cityId, name);
				}
			});
		},
		/**
		 * 打开选择权限页面
		 * @param {应用名称} appName
		 * @param {等级} level
		 * @param {部门} department
		 * @param {城市编号} cityId
		 * @param {名称} name
		 */
		openUrlList : function(appName, level, department, cityId, name) {
			var an = encodeURI(encodeURI(appName.id));
			mini.open({
				url : "pages/position2/urlList.jsp?level="+level+"&appName="+an
					+"&department="+department+"&cityId="+cityId,
				title : "查看{"+ appName.text + "}{" + name + "}权限列表",
				width : 1200,
				height : 650,
				ondestroy : function(action) {
					if(action == "save"){
						lf.position.list.search();
					}
				}
			});
		},
		/**
		 * 设置菜单
		 * @param {部门} department
		 * @param {等级} level
		 * @param {城市编号} cityId
		 * @param {名称} name
		 */
		setMenu : function(department, level, cityId, name) {
			$.ajax({
				method : "post", 
				url : "position/getAppnamesByDepartment.do",
				dataType: "json",
				data : "department="+department,
				success : function(result) {
					if (result.length == 1) { // 只有一个应用
						mini.open({
							url : "pages/position2/menuList.jsp?department="+department+"&appName="+encodeURI(encodeURI(result[0].id))
								+"&level="+level+"&cityId="+cityId,
							title : "查看{"+ result[0].text + "}{" + name + "}菜单列表",
							width : 350,
							height : 450,
							ondestroy : function(action) {
								if(action == "save"){
									lf.position.list.search();
								}
							}
						});
					} else { // 选择应用页面
						lf.position.list.openSelect(result, 2, level, department, cityId, name);
					}
				}
			});
		}
};

$(function(){
	lf.position.list.init();
});