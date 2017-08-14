/**
 * @author carvink
 * @include "lf/lf.common.js"
 */
lc.ns("lf.agentsm.index");
lf.agentsm.index={
	init:function(mainpage){
		if(agent){
			$("#username").html(agent.name);
		}
		var tree = this.tree = mini.get("leftTree");
		tree.on("nodeclick",lc.proxyFn(function(e){
			this.nodeSelect(e);
		},this));
		mini.get("mainTabs").on("activechanged",lc.proxyFn(function(e){
			this.tabsActiveChanged(e);
		},this));
		
		lc.ajax({
			url:lc.rootPath("menuTree.action"),
			success:function(data){
				tree.loadList(mini.decode(data),"id","pid");
			},
			dataType:'text'
		});
	},
	showTab:function(node){
		var tabs = mini.get("mainTabs");
		var showid = node.id;
        var id = "tab$" + node.id;
        var tab = tabs.getTab(id);
        if (!tab) {
            tab = {};
            tab._nodeid = node.id;
            tab.name = id;
            tab.title = node.text;
            tab.showCloseButton = true;
            //这里拼接了url，实际项目，应该从后台直接获得完整的url地址
            //if(node.pid == 'agentMng' || node.pid == 'org'){
        	if(node.pid == 'org'){
            	window.open(node.url);
            	return;
            }else{
            	tab.url = lc.rootPath(node.url);
            }
            tabs.addTab(tab);
        }
        tabs.activeTab(tab);
	},
	nodeSelect:function(e){
		var node = e.node;
        var isLeaf = e.isLeaf;
        if (isLeaf) {
            this.showTab(node);
        }
	},
	tabsActiveChanged:function(e){
		var tabs = e.sender;
        var tab = tabs.getActiveTab();
        if (tab && tab._nodeid) {
            var node = this.tree.getNode(tab._nodeid);
            if (node && !this.tree.isSelectedNode(node)) {
                this.tree.selectNode(node);
            }
        }
	},
	addTabs:function(tab){
		var tabs = mini.get("mainTabs");
        var tabT = tabs.getTab(tab.name);
        if(tabT){
            tabs.updateTab(tabT,tab);
            tabs.activeTab(tabT);
            tabs.reloadTab(tabT);
        }else{
            tabs.addTab(tab);
            tabs.activeTab(tab);
        }
        var tree = mini.get("leftTree");
        var node = tree.getNode(tab._nodeid);
        if(node){
            tree.selectNode(node);
        }

	},
    findNode:function(nodeId){
        return mini.get("leftTree").getNode(nodeId);
    },
	refreshHouseImageListTabs:function(e)
	{
		
		
		var tabs = mini.get("mainTabs");
//		var nodeid = "";
//		
//		for(var i = 0;i<tabs.tabs.length;i++){
//			if(tabs.tabs[i]._nodeid != undefined && tabs.tabs[i]._nodeid != ""){
//				nodeid = tabs.tabs[i]._nodeid;
//				break;
//			}
//		}
//		nodeid = "35";
		var id = "tab$" + e;
		var tab = tabs.getTab(id);
		if(tab)
		{
			var iframe = tabs.getTabIFrameEl(tab);
			iframe.contentWindow.search();
		}
	},
	refreshlandlordImageAuditListTabs:function(e)
	{
		
		var tabs = mini.get("mainTabs");
//		var nodeid = "";
//		
//		for(var i = 0;i<tabs.tabs.length;i++){
//			if(tabs.tabs[i]._nodeid != undefined && tabs.tabs[i]._nodeid != ""){
//				nodeid = tabs.tabs[i]._nodeid;
//				break;
//			}
//		}
//		nodeid = "landlordImageAuditList";
		var id = "tab$" + e;
		var tab = tabs.getTab(id);
		if(tab)
		{
			var iframe = tabs.getTabIFrameEl(tab);
			iframe.contentWindow.search();
		}
	}

};

$(function(){
	lf.agentsm.index.init();
});