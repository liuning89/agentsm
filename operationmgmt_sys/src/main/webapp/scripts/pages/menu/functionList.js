/**
 * @author Yang'ushan
 * @created 2015年7月27日 下午5:21:53
 */
lc.ns("lf.menu.functionList");
lf.menu.functionList = {
		init : function () {
			mini.parse();
			this.grid = mini.get("dg");
			this.search();
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
		 * 保存
		 */
		save : function() {
			var rows = this.grid.getSelecteds();
			if (rows.length != 1) {
				mini.alert('请选中一条数据！');
			} else {
				if (window.CloseOwnerWindow) {
					window.CloseOwnerWindow(rows[0].id+","+rows[0].name+","+rows[0].appName);
				}
				else {
					window.close();
				}
			}
		}
};

$(function(){
	lf.menu.functionList.init();
});