/**
 * @author Yang'ushan
 * @created 2015年7月29日 下午5:40:53
 * @include "../../lf/lf.common.js"
 */
lc.ns("lf.position.menuTreeList");
lf.position.menuTreeList = {
		init : function () {
			mini.parse();
			this.tree = mini.get("tree");
			mini.get("appName").setValue(decodeURI(decodeURI(lc.queryParam("appName"))));
			mini.get("positionId").setValue(lc.queryParam("positionId"));
			/*this.tree.on("beforenodeselect", lc.proxyFn(function(e){
				this.treeSelect(e);
				window.setTimeout(this.validateSelect, 150); // 判断勾选的是否有问题
			},this));*/
			this.tree.on("drawnode", lc.proxyFn(function(e){
				this.drawnode(e);
			},this));
			this.search();
		},
		/**
		 * 选择时，需要判断是否有子节点
		 * @param {} e
		 */
		treeSelect : function(e) {
			var node = e.node;
			if (node.isLeaf != "1") { // 本不是子节点，才做判断
				var childNodes = this.tree.getAllChildNodes(node); // 获取它的所有子节点
				if (childNodes != null && childNodes.length > 0) {
					for (var i = 0; i < childNodes.length; i++) {
						if (childNodes[i].isLeaf == "1") { // 有子节点
							return ;
						}
					}
				}
			} else {
				return ;
			}
			mini.alert("没有可选菜单！");
		},
		/**
		 * 选择节点之后，判断有些不可以 
		 * @param {} e
		 */
		validateSelect : function() {
			var tree = mini.get("tree");
			var childNodes = tree.getCheckedNodes();
			if (childNodes != null && childNodes.length > 0) {
				for (var i = 0; i < childNodes.length; i++) {
					var c = childNodes[i];
					if (c.isLeaf == "1") {
						continue ;
					}
					var childNodes2 = tree.getAllChildNodes(c);
					var isLeaf = false;
					if (childNodes2 != null && childNodes2.length > 0) {
						for (var j = 0; j < childNodes2.length; j++) {
							if (childNodes2[j].isLeaf == "1") { // 有子节点
								isLeaf = true;
								break;
							}
						}
					}
					if (!isLeaf) {
						tree.uncheckNode(c);
					}
				}
			}
		},
		/**
		 * 绘制时，对一些节点禁用
		 * @param {} e
		 */
		drawnode : function(e) {
			var node = e.node;
			if (node.isLeaf != "1") {
				e.showCheckBox = false;
			}
		},
		/**
		 * 搜索
		 */
		search : function() {
			$.ajax({
				method : "post", 
				url : "position/menuTree.do",
				dataType:'text',
				data : new mini.Form("#search").getData(),
				success : function(result) {
					mini.get("tree").loadList(mini.decode(result), "id", "pid");
				}
			});
		},
		/**
		 * 保存
		 */
		save : function() {
			var nodes = this.tree.getCheckedNodes();
			var leafMenuIds="";
			console.log(nodes.length);
			for (var i = 0; i < nodes.length; i++) {
				var n = nodes[i];
				console.log(n);
				if (n.isLeaf == "1") { // 是子节点
					leafMenuIds += n.id;
					if (i != nodes.length - 1) {
						leafMenuIds += ",";
					}
				}
			}
			$.ajax({
				method : "post", 
				url : "position/saveMenu.do",
				dataType:'json',
				data : {
					"positionId":mini.get("positionId").getValue(),
					"leafMenuIds":leafMenuIds
				},
				success : function(result) {
					if (result.status == "1") {
						mini.alert("保存成功");
						lf.position.menuTreeList.CloseWindow("saveMenu");
					}
				}
			});
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
	lf.position.menuTreeList.init();
});