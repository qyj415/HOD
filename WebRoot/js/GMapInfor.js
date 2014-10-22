/**
 * GIS中显示表计信息窗口
 * @class gMapMcontrolCreate
 * @extends Ext.Window
 */
gMapMcontrolCreate  = Ext.extend(Ext.Window,{
	
		initComponent : function() {

		Ext.apply(this, {
					layout : 'fit',
					title : '表计信息',
					closeAction : 'hide',
					modal : false
		});
		
		Ext.applyIf(this, {
					width : this.dlgWidth ? this.dlgWidth : 850,
					height : this.dlgHeight ? this.dlgHeight : 400,
					closeAction : 'hide'
				});
		this.createGrid();
		gMapMcontrolCreate .superclass.initComponent.call(this);
		this.add(this.grid);
	},
	//创建表格
	createGrid: function()
	{
		 var colModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			{
				name : 'meterName',
				header : '表号',
				width : 70
			},{
				name : 'meterInit',
				header : '表底数',
				width : 50
			},{
				name : 'meterPositionName',
				header : '地址',
				width : 100
			}/*,{
				name : 'valveStatus',
				header : '阀门状态',
				width : 50,
				renderer:function(v)
				{
					if(v==0)
					  return "阀开";
					else if(v==1)
					  return "阀关";
					else
					  return "阀异常";
				}
			}*/,{
				name : 'batteryStatus',
				header : '电池状态',
				width : 80,
				renderer:function(v)
				{
					if(v==0)
					  return "正常";
					else
					  return "欠压";
				}
			},{
				name : 'isOnline',
				header : '在线状态',
				width : 80,
				renderer:function(v)
				{
					if(v==1)
					  return "在线";
					else
					  return "离线";
				}
			},{
				name : 'eepromStatus',
				header : '内存状态',
				width : 80,
				renderer:function(value)
				{
					if(value==0)
						return "正常";
					if(value==1)
						return "错误";
				}
			},{
				name : 'flowsensorStatus',
				header : '流量传感器状态',
				width : 120,
				renderer:function(value)
				{
					if(value==0)
						return "正常";
					if(value==1)
						return "错误";
				}
			},{
				name : 'tepdownStatus',
				header : '供水温度传感器状态',
				width : 120,
				renderer:function(value)
				{
					if(value==0)
						return "正常";
					if(value==1)
						return "错误";
				}
			},{
				name : 'tepupStatus',
				header : '回水温度传感器状态',
				width : 120,
				renderer:function(value)
				{
					if(value==0)
						return "正常";
					if(value==1)
						return "错误";
				}
			}
 		]);
		 var dataRecord = Ext.data.Record.create([
			{name: 'num'},
			{name: 'meterName'},
			{name: 'meterInit'},
			{name: 'meterPositionName'},
			//{name: 'valveStatus'},
			{name: 'batteryStatus'},
			{name: 'isOnline'},
			{name: 'eepromStatus'},
			{name: 'flowsensorStatus'},
			{name: 'tepdownStatus'},
			{name: 'tepupStatus'}
		]);
		this.gridStore = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : 'hodMeter!getMeter.do'
							}),
					reader : new Ext.data.JsonReader({
								root : "result",
								totalProperty : "totalCount",
								id : "id"
							}, dataRecord),
					remoteSort : true
				});
				
				
				
		this.grid = new Ext.grid.GridPanel({
			    	loadMask : true,
    	 			stripeRows: true, 
				    store: this.gridStore,
				    cm: colModel,
				    viewConfig: {
				        forceFit: true
				    },
				    frame:true,
				    loadMask:{msg:'正在载入数据,请稍等...'}
				});
		
	}

});

/**
 * 显示集中器信息 
 */
gMapTerminalCreate  = Ext.extend(Ext.Window,{ //主控开关事件
		initComponent : function() {
		Ext.apply(this, {
					layout : 'fit',
					closeAction : 'hide',
					modal : false
		});
		
		Ext.applyIf(this, {
					width : this.dlgWidth ? this.dlgWidth : 400,
					height : this.dlgHeight ? this.dlgHeight : 300,
					closeAction : 'hide'
				});
		this.createGrid();
		gMapTerminalCreate .superclass.initComponent.call(this);
		this.add(this.grid);
	},
	//创建表格
	createGrid: function()
	{
		 var colModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			{
				name : 'name',
				header : '集中器编号',
				width : 100
			},{
				name : 'realy',
				header : '安装位置',
				width : 100
			}
 		]);
		 var dataRecord = Ext.data.Record.create([
			{name: 'num'},
			{name: 'conNum'},
			{name: 'positionName'}
		]);
		this.gridStore = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : 'hodConcentrator!getTerminalInfo.do'
							}),
					reader : new Ext.data.JsonReader({
								root : "result",
								totalProperty : "totalCount",
								id : "id"
							}, dataRecord),
					remoteSort : true
				});
							
		this.grid = new Ext.grid.GridPanel({
			    	loadMask : true,
    	 			stripeRows: true, 
				    store: this.gridStore,
				    cm: colModel,
				    viewConfig: {
				        forceFit: true
				    },
				    frame:true,
				    loadMask:{msg:'正在载入数据,请稍等...'}
				});
		
	}
});

/**
 * 显示表计异常状态信息 
 */
gMapMcontrolEventCreate  = Ext.extend(Ext.Window,{ //主控开关事件
		initComponent : function() {
		Ext.apply(this, {
					layout : 'fit',
					title : '表计报警信息',
					closeAction : 'hide',
					modal : false
		});
		
		Ext.applyIf(this, {
					width : this.dlgWidth ? this.dlgWidth : 850,
					height : this.dlgHeight ? this.dlgHeight : 400,
					closeAction : 'hide'
				});
		this.createGrid();
		gMapMcontrolEventCreate .superclass.initComponent.call(this);
		this.add(this.grid);
	},
	//创建表格
	createGrid: function()
	{
		 var colModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			{
				name : 'meterName',
				header : '表号',
				width : 100
			},{
				name : 'positionName',
				header : '地理位置',
				width : 150
			},/*{
				name : 'valveStatus',
				header : '阀门状态',
				width : 80,
				renderer:function(value)
				{
					if(value==0)
						return "阀开";
					if(value==1)
						return "阀关";
					if(value==2)
						return "阀异常";
				}
			},*/{
				name : 'batteryStatus',
				header : '电池状态',
				width : 80,
				renderer:function(value)
				{
					if(value==0)
						return "正常";
					if(value==1)
						return "欠压";
				}
			},{
				name : 'isOnline',
				header : '在线状态',
				width : 80,
				renderer:function(value)
				{
					if(value==1)
						return "在线";
					if(value==2)
						return "离线";
				}
			},{
				name : 'eepromStatus',
				header : '内存状态',
				width : 80,
				renderer:function(value)
				{
					if(value==0)
						return "正常";
					if(value==1)
						return "错误";
				}
			},{
				name : 'flowsensorStatus',
				header : '流量传感器状态',
				width : 120,
				renderer:function(value)
				{
					if(value==0)
						return "正常";
					if(value==1)
						return "错误";
				}
			},{
				name : 'tepdownStatus',
				header : '供水温度传感器状态',
				width : 120,
				renderer:function(value)
				{
					if(value==0)
						return "正常";
					if(value==1)
						return "错误";
				}
			},{
				name : 'tepupStatus',
				header : '回水温度传感器状态',
				width : 120,
				renderer:function(value)
				{
					if(value==0)
						return "正常";
					if(value==1)
						return "错误";
				}
			}
 		]);
		 var dataRecord = Ext.data.Record.create([
			{name: 'num'},
			{name: 'meterName'},
			{name: 'positionName'},
			//{name: 'valveStatus'},
			{name: 'batteryStatus'},
			{name: 'isOnline'},
			{name: 'eepromStatus'},
			{name: 'flowsensorStatus'},
			{name: 'tepdownStatus'},
			{name: 'tepupStatus'}
		]);
		this.gridStore = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : 'hodBuilding!getEventList.do'
							}),
					reader : new Ext.data.JsonReader({
								root : "result",
								totalProperty : "totalCount",
								id : "id"
							}, dataRecord),
					remoteSort : true
				});
							
		this.grid = new Ext.grid.GridPanel({
			    	loadMask : true,
    	 			stripeRows: true, 
				    store: this.gridStore,
				    cm: colModel,
				    viewConfig: {
				        forceFit: true
				    },
				    frame:true,
				    loadMask:{msg:'正在载入数据,请稍等...'}
				});
		
	}
});

/**
 * 显示集中器异常状态信息 
 */
gMapTerminalEventCreate  = Ext.extend(Ext.Window,{ //主控开关事件
		initComponent : function() {
		Ext.apply(this, {
					layout : 'fit',
					title : '集中器报警信息',
					closeAction : 'hide',
					modal : false
		});
		
		Ext.applyIf(this, {
					width : this.dlgWidth ? this.dlgWidth : 700,
					height : this.dlgHeight ? this.dlgHeight : 400,
					closeAction : 'hide'
				});
		this.createGrid();
		gMapTerminalEventCreate .superclass.initComponent.call(this);
		this.add(this.grid);
	},
	//创建表格
	createGrid: function()
	{
		 var colModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			{
				name : 'conNumber',
				header : '集中器编号',
				width : 100
			},{
				name : 'conAddress',
				header : '安装位置',
				width : 100
			},{
				name : 'conFlashStatus',
				header : '内存状态',
				width : 80,
				renderer:function(value)
				{
					if(value==0)
						return "正常";
					if(value==1)
						return "错误";
				}
			},{
				name : 'conCom1Status',
				header : '通讯口1',
				width : 80,
				renderer:function(value)
				{
					if(value==0)
						return "正常";
					if(value==1)
						return "欠压";
				}
			},{
				name : 'conCom2Status',
				header : '通讯口2',
				width : 80,
				renderer:function(value)
				{
					if(value==0)
						return "正常";
					if(value==1)
						return "欠压";
				}
			},{
				name : 'conCom3Status',
				header : '通讯口3',
				width : 80,
				renderer:function(value)
				{
					if(value==0)
						return "正常";
					if(value==1)
						return "欠压";
				}
			},{
				name : 'isOnline',
				header : '在线状态',
				width : 80,
				renderer:function(value)
				{
					if(value==1)
						return "在线";
					if(value==2)
						return "离线";
				}
			},{
				name : 'conStrong',
				header : 'GPRS信号',
				width : 80,
				renderer:function(value)
				{
					if(value==0)
						return "无";
					if(value==1)
						return "弱";
					if(value==2)
						return "一般";
					if(value==3)
						return "强";
				}
			}
 		]);
		 var dataRecord = Ext.data.Record.create([
			{name: 'num'},
			{name: 'conNumber'},
			{name: 'conAddress'},
			{name: 'conFlashStatus'},
			{name: 'conCom1Status'},
			{name: 'conCom2Status'},
			{name: 'conCom3Status'},
			{name: 'isOnline'},
			{name: 'conStrong'}
		]);
		this.gridStore = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : 'hodConcentrator!getEventList.do'
							}),
					reader : new Ext.data.JsonReader({
								root : "result",
								totalProperty : "totalCount",
								id : "id"
							}, dataRecord),
					remoteSort : true
				});
							
		this.grid = new Ext.grid.GridPanel({
			    	loadMask : true,
    	 			stripeRows: true, 
				    store: this.gridStore,
				    cm: colModel,
				    viewConfig: {
				        forceFit: true
				    },
				    frame:true,
				    loadMask:{msg:'正在载入数据,请稍等...'}
				});
		
	}
});

/*
 * 显示抄表信息列表
 * 
 */
gMapControlEventCreate  = Ext.extend(Ext.Window,{ //路灯事件
		initComponent : function() {
		Ext.apply(this, {
					layout : 'fit',
					title : '实时抄读',
					closeAction : 'hide',
					modal : false
		});
		
		Ext.applyIf(this, {
					width : this.dlgWidth ? this.dlgWidth : 600,
					height : this.dlgHeight ? this.dlgHeight : 500,
					closeAction : 'hide'
				});
		this.createGrid();
		gMapControlEventCreate .superclass.initComponent.call(this);
		this.add(this.grid);
	},
	//创建表格
	createGrid: function()
	{
		 var colModel = new Ext.grid.ColumnModel([
		 	new Ext.grid.RowNumberer(),
			{
				name : 'meterName',
				header : '表号',
				width : 100
			},{
				name : 'currentEnergy',
				header : '累计热量',
				width : 50
			},{
				name : 'accumulateFlow',
				header : '累计流量',
				width : 50
			},{
				name : 'meterFlow',
				header : '瞬时流量',
				width : 50
			},{
				name : 'accumulateTime',
				header : '累计时间',
				width : 50
			},{
				name : 'supplyTemper',
				header : '供水温度',
				width : 50
			},{
				name : 'backTemper',
				header : '回水温度',
				width : 50
			}
 		]);
		 var dataRecord = Ext.data.Record.create([
		 	{name: 'num'},
			{name: 'meterName'},
			{name: 'currentEnergy'},
			{name: 'accumulateFlow'},
			{name: 'meterFlow'},
			{name: 'accumulateTime'},
			{name: 'supplyTemper'},
			{name: 'backTemper'}
		]);
		this.gridStore = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : 'hodMeterInfoTemp!ReadMeter.do'
							}),
					reader : new Ext.data.JsonReader({
								root : "result",
								totalProperty : "totalCount",
								id : "id"
							}, dataRecord),
					remoteSort : true
				});
				
				
				
		this.grid = new Ext.grid.GridPanel({
			    	loadMask : true,
    	 			stripeRows: true, 
				    store: this.gridStore,
				    cm: colModel,
				    viewConfig: {
				        forceFit: true
				    },
				    frame:true,
				    loadMask:{msg:'正在载入数据,请稍等...'}
				});
		
	}
});
/*
 * 用量明细
 * 
 */
gHistoryDetailCreate  = Ext.extend(Ext.Window,{ //路灯事件
		initComponent : function() {
		Ext.apply(this, {
					layout : 'fit',
					title : '历史数据',
					closeAction : 'hide',
					modal : false
		});
		
		Ext.applyIf(this, {
					width : this.dlgWidth ? this.dlgWidth : 700,
					height : this.dlgHeight ? this.dlgHeight : 450,
					closeAction : 'hide'
				});
		this.createGrid();
		gHistoryDetailCreate .superclass.initComponent.call(this);
		this.add(this.grid);
	},
	//创建表格
	createGrid: function()
	{
		 var colModel = new Ext.grid.ColumnModel([
		 	new Ext.grid.RowNumberer(),
			{
				name : 'meterName',
				header : '表号',
				width : 100
			},{
				name : 'currentEnergy',
				header : '累计热量',
				width : 50
			},{
				name : 'accumulateFlow',
				header : '累计流量',
				width : 50
			},{
				name : 'meterFlow',
				header : '瞬时流量',
				width : 50
			},{
				name : 'accumulateTime',
				header : '累计工作时间',
				width : 80
			},{
				name : 'supplyTemper',
				header : '供水温度',
				width : 50
			},{
				name : 'backTemper',
				header : '回水温度',
				width : 50
			},{
				name : 'readTime',
				header : '抄表时间',
				width : 120,
				renderer:function(v)
				{
					if(v!="")
						return v.substring(0,v.length-2);
					else
						return "";
				}
			}
 		]);
		 var dataRecord = Ext.data.Record.create([
		 	{name: 'num'},
			{name: 'meterName'},
			{name: 'currentEnergy'},
			{name: 'accumulateFlow'},
			{name: 'meterFlow'},
			{name: 'accumulateTime'},
			{name: 'supplyTemper'},
			{name: 'backTemper'},
			{name: 'readTime'}
		]);
		this.gridStore = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : 'hodMeterInfoHistory!getHistoryDetail.do'
							}),
					reader : new Ext.data.JsonReader({
								root : "result",
								totalProperty : "totalCount",
								id : "id"
							}, dataRecord),
					remoteSort : true
				});
				
				
				
		this.grid = new Ext.grid.GridPanel({
			    	loadMask : true,
    	 			stripeRows: true, 
				    store: this.gridStore,
				    cm: colModel,
				    viewConfig: {
				        forceFit: true
				    },
				    frame:true,
				    loadMask:{msg:'正在载入数据,请稍等...'}
				});
		
	}
});
