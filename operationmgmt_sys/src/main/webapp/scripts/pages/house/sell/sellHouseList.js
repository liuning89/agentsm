/**
 * @author carvink
 * @include "../../../lf/lf.common.js"
 */
lc.ns("lf.house.house.sell.list");
lf.house.house.sell.list={
	init:function(){
		$("#searchBtn").click(lc.proxyFn(function(){
			this.search();
		},this));
		$("#resetBtn").click(lc.proxyFn(function(){
			this.reset();
		},this));
		
		var houseId = mini.get("houseId");
		houseId.on("blur", lc.proxyFn(function() {
			if (houseId.getValue() != "") {
				this.houseIdSelectiveUnvisible();
			} else {
				this.houseIdSelectiveVisible();
			}
		},this));
		var cityId = mini.get("cityId");
		var estateId = mini.get("estateId");
		this.selectedEstate = false;
		estateId.setUrl(lc.rootPath("subEstate/tips.action?cityId="+cityId.getValue()));
		estateId.on("valuechanged",lc.proxyFn(function(e){
			this.selectedEstate = e.selected?true:false;
			this.loadBuildingList(e.value);
		},this));
		cityId.on("valuechanged",lc.proxyFn(function(e){
			if(e.value != "" && e.value != 0){
				estateId.setUrl(lc.rootPath("subEstate/tips.action?cityId="+e.value));
			}
		},this));
		
		/*mini.get("agentType").on("valuechanged",lc.proxyFn(function(e){
			if(e.value == 0){
				this.agentTypeSelectiveUnvisible();
			}else{
				this.agentTypeSelectiveVisible();
			}
		},this));*/
		
		
		/*mini.get("store").on("valuechanged",lc.proxyFn(function(e){
			this.loadAgent(e.value);
		},this));*/
		
		mini.get("startTime").on("drawdate",lc.proxyFn(function(e){
			var currentDate = new Date();
			if (e.date.getTime() > currentDate.getTime()) {
                e.allowSelect = false;
            }
		},this));
		
		
		mini.get("endTime").on("drawdate",lc.proxyFn(function(e){
			var currentDate = new Date();
			if (e.date.getTime() > currentDate.getTime()) {
                e.allowSelect = false;
            }
		},this));
		

		//cityId.setValue(43);
		//this.loadStore();
		this.initGrid();
	},
	initGrid:function(){	
		var grid=this.grid=mini.get("dg");
		//this.search();
		var status={
			4:"无效",
			2:"有效"
		},whether={
			0:"否",
			1:"是"
		};
		grid.on("drawcell", function(e) {
			var record = e.record, field = e.field, value = e.value, column = e.column;
			if (column.name == "action") {
				var lock = record.lockStatus == 0 ?'锁定':'解锁';
				if(record.status != 4){
					var html = lc.strFormat('<a class="mini-button" style="padding-left:10px;padding-right:10px;" onclick="lf.house.house.sell.list.updateHouseStatus({0},{1})">{2}</a>' +
							'<a class="mini-button" style="margin-left:5px;padding-left:10px;padding-right:10px;" onclick="lf.house.house.sell.list.detail({0},{3})">详情</a>',
							record.houseId,record.status,"设置为无效",e.rowIndex);
				}else{
					var html = lc.strFormat(
							'<a class="mini-button" style="padding-left:10px;padding-right:10px;" onclick="lf.house.house.sell.list.detail({0},{3})">详情</a>',
							record.houseId,record.status,"设置为无效",e.rowIndex);
				}
				
				e.cellHtml = html;
			}else if(column.name=="reason"){
				var html = lc.strFormat('<a class="mini-button" style="padding-left:10px;padding-right:10px;" onclick="lf.house.house.sell.list.lookReason({0})">查看</a>',
						record.houseId);
				e.cellHtml = html;
			}else if(field == "status"){
				e.cellHtml = status[value];
			}else if(field == "publishDate"){
				if(value){
					e.cellHtml = lc.dateFormat(value);
				}
			}else if(field == "area"){
				if(!value){
					value = 0;
				}
			}else if(field == "status"){
				//alert(status[value]);
				e.cellHtml = status[value];
			}else if(field == "hasPoint" || field == "hasContour"){
				e.cellHtml = whether[value];
			}else if(column.name=="publisher"){
				var html = lc.strFormat('<a class="mini-button" style="padding-left:10px;padding-right:10px;" onclick="lf.house.house.sell.list.lookPublisher({0})">查看</a>',
						record.houseId);
				e.cellHtml = html;
			}
			
		});
	},
	/**	
	 * 修改房源状态
	 * @param {} houseId
	 * @param {} status
	 */
	updateHouseStatus:function(houseId,status){
		 mini.open({
             url: "houseResource/sell/setHouseResourceStatusPage.action?houseId="+houseId+"&status=4",
             title: "设置无效",
             width: 656, 
             height:307,
             ondestroy: function (action) {
            	 if(action=='save'){
            		 mini.alert("操作成功","提示",function(){
            			 lf.house.house.sell.list.grid.reload();
            		 });
            	 }
             }
         });
	},
	detail:function(houseId,index){
		var row = this.grid.getRow(index);
		console.info(row);
		var title = row.estateName+row.buildingName+row.room;
		parent.lf.agentsm.index.addTabs({
			name:"HouseDetail"+houseId,
			title:title,
			showCloseButton:true,
			url:lc.rootPath("houseResource/sell/tabPage.action?houseId="+houseId+"&cityId="+row.cityId)
		});
	},
	search:function(){
		var houseId = mini.get("houseId");
		if (houseId.value == '') {
			var startTime = mini.get("startTime");
			var endTime = mini.get("endTime");
			if (startTime.getValue() != '' && endTime.getValue() != '') {
				if (endTime.getValue().getTime() < startTime.getValue()
						.getTime()) {
					mini.alert("结束时间不可小于开始时间");
					return;
				}
			}
		}
		var form = new mini.Form("#search");
		var data = form.getData(true);
		/*if(houseId.value != ''){
			lc.removeObjectProperty(data,"startTime,endTime,estateId,estateName,buildingId,room,hostMobile,agentType,mobileOrName,startSpaceArea,endSpaceArea,startSellPrice,endSellPrice,applyInvalid,companyId,auditStatus");
		}else{
			var estateId = mini.get("estateId");
			if(!this.selectedEstate && estateId != ''){
				data.estateName =data.estateId;
				delete data.estateId;
			}
		}*/
		/*
		 * agentType: "-1"
			applyInvalid: "0"
			auditStatus: "-1"
			buildingId: "0"
			cityId: ""
			companyId: ""
			endSellPrice: ""
			endSpaceArea: ""
			endTime: ""
			estateId: ""
			hasAgent: "0"
			hostMobile: ""
			houseId: ""
			isFiveYears: "0"
			isOnlyOne: "0"
			mobileOrName: ""
			room: ""
			startSellPrice: ""
			startSpaceArea: ""
			startTime: ""
			status: "-1"
		 */
		var result =data['agentType']=='-1'
 			&& data['applyInvalid'] == 0
			&& data['auditStatus'] == '-1' && data['buildingId'] == 0
			&& (data['cityId'] == '' || data['cityId'] == null)
			&& (data['companyId'] == "" || data['companyId'] == null)
			&& (data['endSellPrice'] == "" || data['endSellPrice'] == null)
			&& (data['endSpaceArea'] == "" || data['endSpaceArea'] == null)
			&& (data['endTime'] == "" || data['endTime'] == null)
			&& (data['estateId'] == "" || data['estateId'] == null)
			&& data['hasAgent'] == "0" 
			&& (data['hostMobile'] == ""|| data['hostMobile'] == null )
			&& (data['houseId'] == ""|| data['houseId'] == null )
			&& data['isFiveYears'] == "0"
			&& data['isOnlyOne'] == "0" 
			&& (data['mobileOrName'] == ""|| data['mobileOrName'] == null) 
			&&( data['room'] == ''|| data['room'] == null )
			&& (data['startSellPrice'] == ""|| data['startSellPrice'] == null)
			&& (data['startSpaceArea'] == ""|| data['startSpaceArea'] == null) 
			&& (data['startTime'] == ""|| data['startTime'] == null)
			&& data['status'] == "-1";
		if(result){
			mini.alert("请至少选择一个查询条件");
			return;
		}
		lc.removeObjectEmptyValueProperty(data);
		this.grid.load(data);
	},
	reset:function(){
		var form = new mini.Form("#search");
		form.reset();
		//mini.get("cityId").setValue(43);
		mini.get("estateId").setUrl(lc.rootPath("subEstate/tips.action?cityId=43"));
		this.houseIdSelectiveVisible();
	//	this.agentTypeSelectiveVisible();
		this.selectedEstate = false;
		//mini.get("store").select(0);
	},
	houseIdSelectiveUnvisible : function() {
		mini.get("estateId").disable();
		mini.get("room").disable();
		mini.get("buildingId").disable();
		mini.get("startTime").disable();
		mini.get("endTime").disable();
	},
	houseIdSelectiveVisible : function() {
		mini.get("estateId").enable();
		mini.get("room").enable();
		mini.get("buildingId").enable();
		mini.get("startTime").enable();
		mini.get("endTime").enable();
	},
	/*agentTypeSelectiveUnvisible:function(){
		mini.get("store").disable();
		mini.get("agent").disable();
	},
	agentTypeSelectiveVisible:function(){
		mini.get("store").enable();
		mini.get("agent").enable();
	},*/
	loadBuildingList:function(subEstateId){
		var buildingId = mini.get("buildingId");
		if(this.selectedEstate && subEstateId != ''){
			buildingId.load(lc.rootPath(lc.strFormat("subEstate/building/list.action?subEstateId={0}",subEstateId)));
			buildingId.setData([{id:0,text:'全部'}].concat(buildingId.getData()));
		}else{
			buildingId.setData([{
				id : 0,
				text : '全部'
			}]);
		}
		buildingId.select(0);
	},
	
	lookReason:function(houseId){
		 mini.open({
             url: "houseResource/sell/lookReasonPage.action?houseId="+houseId,
             title: "原因查看",
             width: 480, 
             height:300,
             ondestroy: function (action) {
             
             }
         });
	},
	lookPublisher:function(houseId){
		 mini.open({
             url: "houseResource/sell/lookPublisherPage.action?houseId="+houseId,
             title: "历史归属人查看",
             width: 580,
             height:600,
             ondestroy: function (action) {
             
             }
         });
	}
	/*loadStore:function(){
		var store = mini.get("store");
		store.load(lc.rootPath(lc.strFormat("store/simple/list.action?cityId={0}",mini.get("cityId").value)));
		store.setData([{id:0,text:'全部'}].concat(store.getData()));
		store.select(0);
		this.loadAgent(0);
	},
	loadAgent:function(storeId){
		var agentList = mini.get("agent");
		if(storeId != 0){
			agentList.load(lc.rootPath(lc.strFormat("agent/simple/list.action?storeId={0}",storeId)));
			agentList.setData([{id:0,text:'全部'}].concat(agentList.getData()));
		}else{
			agentList.setData([{
				id : 0,
				text : '全部'
			}]);
		}
		agentList.select(0);
	}*/
};

$(function(){
	lf.house.house.sell.list.init();
});
