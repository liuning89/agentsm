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
				} else if (field == "department") {
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
					var html = lc.strFormat('<a class="mini-button" style="padding-left:10px;padding-right:10px;" onclick="lf.position.list.setUrl({0}, \'{1}\', {2})">设置权限</a>' +
							'<a class="mini-button" style="padding-left:10px;padding-right:10px;margin-left:5px;" onclick="lf.position.list.setMenu({0}, \'{1}\', {2})">设置菜单</a>'
							, record.id, record.name, record.department);
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
		},
		search : function() {
			this.grid.load(new mini.Form("#search").getData());
		},
		/**
		 * 设置URL
		 * @param {职位ID} id
		 * @param {职位名称} name
		 * @param {部门} department
		 */
		setUrl : function(id, name, department) {
			$.ajax({
				method : "post", 
				url : "position/getAppnamesByDepartment.do",
				dataType: "json",
				data : "department="+department,
				success : function(result) {
					if (result.length == 1) { // 只有一个应用
						lf.position.list.openUrlList(id, name, result[0]);
					} else { // 选择应用页面
						lf.position.list.openSelect(id, name, result, 1);
					}
				}
			});
		},
		/**
		 * 打开选择的页面
		 * @param {功能} id
		 * @param {功能名称} name
		 * @param {应用列表} apps
		 * @param {类型：1跳转到URL页面} type
		 */
		openSelect : function(id, name, apps, type) {
			mini.open({
				url : "pages/position/selectAppname.jsp",
				title : "选择应用",
				width : 300,
				height : 150, 
				onload : function() { // 弹出页面加载完成
					var iframe = this.getIFrameEl();
					// 调用弹出页面方法进行初始化
					iframe.contentWindow.SetData(id, name, apps, type);
				}
			});
		},
		/**
		 * 打开选择权限页面
		 * 
		 * @param {职位ID} id
		 * @param {职位名称} name
		 */
		openUrlList : function(id, name, appName) {
			var an = encodeURI(encodeURI(appName));
			mini.open({
				url : "pages/position/urlList.jsp?positionId="+id+"&appName="+an,
				title : "查看{"+ appName + "}{" + name + "}权限列表",
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
		 * @param {职位ID} id
		 * @param {职位名称} name
		 * @param {部门} department
		 */
		setMenu : function(id, name, department) {
			$.ajax({
				method : "post", 
				url : "position/getAppnamesByDepartment.do",
				dataType: "json",
				data : "department="+department,
				success : function(result) {
					if (result.length == 1) { // 只有一个应用
						mini.open({
							url : "pages/position/menuList.jsp?positionId="+id+"&appName="+encodeURI(encodeURI(result[0])),
							title : "查看{"+ result[0] + "}{" + name + "}菜单列表",
							width : 350,
							height : 450,
							ondestroy : function(action) {
								if(action == "save"){
									lf.position.list.search();
								}
							}
						});
					} else { // 选择应用页面
						lf.position.list.openSelect(id, name, result, 2);
					}
				}
			});
		}
};

$(function(){
	lf.position.list.init();
});