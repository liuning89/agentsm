/**
 * @author Yang'ushan
 * @created 2015年7月27日 下午6:21:53
 * @include "../../lf/lf.common.js"
 */
lc.ns("lf.menu.parentTreeList");
lf.menu.parentTreeList = {
		init : function () {
			mini.parse();
			this.tree = mini.get("tree2");
			this.tree.on("nodecheck",lc.proxyFn(function(e){
				this.nodeSelect(e);
			},this));
			mini.get("appName").setValue(decodeURI(decodeURI(lc.queryParam("appName"))));
			this.search();
		},
		/**
		 * 搜索
		 */
		search : function() {
			this.tree.load(new mini.Form("#search").getData());
		},
		/**
		 * 节点选中时发生
		 */
		nodeSelect : function(e) {
			if (e.node.checked == true) { // 选中，把其它值node选中的状况清除
				console.log(this.tree.getCheckedNodes(true).length);
				for (var i = 0; i < this.tree.getCheckedNodes(true).length; i++) {
					var node = this.tree.getCheckedNodes(true)[i];
					if (node.id != e.node.id) { // 不是同一个node
						this.tree.uncheckNode(node);
					}
				}
			}
		},
		/**
		 * 保存
		 */
		save : function() {
			var value = this.tree.getValue();
			if (value == "") {
				mini.alert("请选中一条数据！");
				return ;
			} else {
				if (window.CloseOwnerWindow) {
					window.CloseOwnerWindow(this.tree.getValue()+","+this.tree.getText());
				}
				else {
					window.close();
				}
			}
		}
};

$(function(){
	lf.menu.parentTreeList.init();
});