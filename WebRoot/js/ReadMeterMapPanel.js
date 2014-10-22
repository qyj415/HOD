
MarkObject =function(){
			next_: null;
			prev_: null;
			segPrev_:null;
			segNext_:null;
			curMarker : null;
		};
//绘制操作内容提示区
function DisplayControl() {
	// 设置默认停靠位置和偏移量  
    this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;  
    this.defaultOffset = new BMap.Size(8, 80);  
};
DisplayControl.prototype = new BMap.Control();
DisplayControl.prototype.initialize = function(map) {
	var container = document.createElement('div');
	txtInfo = document.createElement('div');
	txtInfo.style.font = 'small Arial';
				txtInfo.style.fontWeight = 'bold';
				txtInfo.style.fontSize = '9pt';
				txtInfo.style.color = 'red';
				txtInfo.style.width = '82px';
	map.txtInfo_ = txtInfo;			
	container.appendChild(txtInfo);			
	map.getContainer().appendChild(container);
	return container;
};

Ext.ux.GMapPanel = Ext.extend(Ext.Panel, {
	  	isDrag : false, 	// 是否被拖动

		DrawType : 0, 		// 节点类型 0:楼栋节点 1:灯控器节点 2批量绘制 
		DrawMark : null, 	// 当前绘制的标注信息
		DrawNode : null,  	// 当前绘制的节点信息
		DrawOperation : -1, 		// 绘图操作类型 -1 浏览 0:单独绘制楼栋节点 1:单独绘制灯控器节点 2批量绘制 3 移动标注
		
		ShowTerminal : true, 		// 是否显示楼栋
		
		
		mousePointX   : null,    // 鼠标绝对坐标X
		mousePointY   : null,    // 鼠标绝对坐标Y
		curPoint : null,  			// 当前鼠标指针坐标
		
		map_menu : null,     		// 右键通用右键菜单
		terminal_menu:null,			 //楼栋右键菜单
		draw_menu:null,					 //单点绘制时的右键菜单
		move_menu:null,					 //移动正式标注时所使用的右键菜单
		drawgroup_menu:null,  //批量绘制右键菜单
		event_menu:null,      //事件标注的右键菜单
		
		center_icon:null,				//地图中心
		terminal_icon:null,			//楼栋图标
		terminalDraw_icon:null,			//绘制楼栋图标
		terminalEvent_icon:null,			//楼栋有事件时图标
		
		focusMark	: null,			 	//焦点标注     为右键菜单提供 
		focuslatlng: null,			//焦点标注的坐标	
		focusType  : null,      //焦点标注类型  0:楼栋临时标注 1:灯控器临时标注 
								           //10:楼栋正式标注 11:灯控器正式标注
								           //20:楼栋移动标注 21:灯控器移动标注
		                                   //30:楼栋事件标注 31:灯控器事件标注
		moveindex :-1,					//移动标注在列表中的位置
		mcontrolDlg:null,               //热量表信息显示窗口句柄
		terminalDlg:null,				//集中器信息显示窗口句柄
		eventinforDlg:null,           //事件内容显示窗口
		
	   //txtInfo:null,	//操作提示
		x1 : 0,
		y1 : 0,
		x2 : 0,
		y2 : 0,
 		markInfors	:new Array(),  //标注信息 
 		markTimeHandles:[],  // 标注的定时器
 		timeoutHandle: null, // 通用定时器
 		DrawMarkTime:null,   // 绘制地临时标注定时器
 		DrawMarknum:0,			// 绘制坐标的位置	
 		myobj:null,
 		destroy:function(){	// 回收资源
 			GUnload();
 			this.markTimeHandles = null;	
 			if(this.DrawMarkTime!=null){ // 通用定时器
 				window.clearTimeout(this.DrawMarkTime); 	
 				this.DrawMarkTime = null;
 			}
 			if(this.timeoutHandle!=null){// 绘制地临时标注定时器
 				window.clearTimeout(this.timeoutHandle); 	
 				this.timeoutHandle = null;
 			}
 		},
    afterRender : function(){
        /*
        if (!GBrowserIsCompatible()) {
        	Ext.MessageBox.alert("提示信息", "当前使用的浏览器不支技GoogleMap!");
        	return;
        }
        */
        var wh = this.ownerCt.getSize();
        Ext.applyIf(this, wh);
        
        Ext.ux.GMapPanel.superclass.afterRender.call(this);

				/*
					Ext.Ajax.request({
					url : "syscode/dogetmapcenter.do",
					async : false,
					timeout : 5000,
					success : function(res) {
						var result = Ext.decode(res.responseText);
						if (true == result.success) {
							var Pane = Ext.getCmp('metermap');
							Pane.setCenter.lat = result.lat;
							Pane.setCenter.lng = result.lng;
						}
					}
				});
				*/
        //创建图标
        this.center_icon = this.creatIcon("center.png");	//地图中心
        this.terminal_icon = this.creatIcon("terminal.png");	//楼栋图标
        this.terminalDraw_icon = this.creatIcon("terminaldraw.png");	//绘制楼栋图标
        this.terminalEvent_icon = this.creatIcon("terminalevent.png");	//楼栋有事件时图标
        
        this.gmap = new BMap.Map(this.body.dom);
		 var point = new BMap.Point(this.setCenter.lng,this.setCenter.lat); 
		this.gmap.centerAndZoom(point, 14); //初始化地图
	    this.gmap.enableScrollWheelZoom();  // 开启鼠标滚轮缩放    
	    this.gmap.enableKeyboard();         // 开启键盘控制    
	    this.gmap.enableContinuousZoom();   // 开启连续缩放效果    
	    this.gmap.enableInertialDragging(); // 开启惯性拖拽效果   
	    this.gmap.addControl(new BMap.NavigationControl()); //添加标准地图控件(左上角的放大缩小左右拖拽控件)  
	    this.gmap.addControl(new BMap.ScaleControl());      //添加比例尺控件(左下角显示的比例尺控件)  
	    this.gmap.addControl(new BMap.OverviewMapControl()); // 缩略图控件  
	    this.gmap.addControl(new BMap.MapTypeControl());
        
        myobj = this;
        this.ZoomStart(this.gmap);
        this.ZoomEnd(this.gmap);
        this.MoveStart(this.gmap);
        this.MoveEnd(this.gmap);
        this.MouseMove(this.gmap);
        this.Click(this.gmap);
        //this.MouseOut(this.gmap);
        this.MouseOver(this.gmap);
        this.DblClick(this.gmap);
        this.SingleRightClick(this.gmap);
        this.gmap.addControl(new DisplayControl());
        //需增加 设置浏览区域
        this.DrawAllMark();
        //this.gmap.addEventListener("bounds_changed",function(){
       	//	 this.DrawAllMark();
        //}.createDelegate(this));
    },
    creatIcon:function(image){
    	icon = new BMap.Icon("./images/map/" + image,new BMap.Size(30, 30));
		return icon;
    },
 
    setShowTerminal:function(sign){ 
    	this.ShowTerminal = sign;	
    	this.ShowAllPoint()
    },
    showmap_Menu : function(x,y) { // 通用右键菜单
    	if (this.map_menu==null){
			var zoomInMenu = new Ext.menu.Item({
						iconCls : 'zoomIn',
						text : '放大',
						handler : this.onzoomIn.createDelegate(this)
					});
			var zoomOutMenu = new Ext.menu.Item({
						iconCls : 'zoomOut',
						text : '缩小',
						handler : this.onzoomOut.createDelegate(this)
					});
			var zoomInHereMenu = new Ext.menu.Item({
						iconCls : 'onzoomInHere',
						text : '放置到最大级',
						handler : this.onzoomInHere.createDelegate(this)
					});
			var zoomOutHereMenu = new Ext.menu.Item({
						iconCls : 'oncentreMapHere',
						text : '放置到最小级',
						handler : this.onzoomOutHere.createDelegate(this)
					});
			var menuList = [zoomInMenu, zoomOutMenu, zoomInHereMenu,
					zoomOutHereMenu];
      
			this.map_menu = new Ext.menu.Menu({
						items : menuList
					});
    	};
    	this.map_menu.showAt([x,y]);
    },
    clearFocus:function(){
   		this.focusMark = null;
		this.focuslatlng = null;
		this.focusType = null;
    },
    //显示集中器信息
    showTerminalMenu:function(mark)
    { 
    	/*
    		var result = Ext.getCmp('mymap').DoAjax('control/savebygroup.do?terminal=' + this.terminal + '&group=' + this.group
					+ '&lng=' + lng + '&lat=' + lat + '&forward=' + this.forward);
				switch (result) {
					case -1 :
						Ext.MessageBox.alert("提示信息", "服务器无应答超时!");
						break;
					case 0 :
						Ext.MessageBox.alert("提示信息", "批量标注保存失败!");
						break;
					case 1 :
					{
						Ext.MessageBox.alert("提示信息", "批量标注保存成功!");
						Ext.getCmp('mymap').DrawMarkGroup.reset();
						Ext.getCmp('mymap').initmap();
						Ext.getCmp('mymap').DrawAllMark();
					}
					break;
				}
    	*/
    	if(!this.terminalDlg)
    	{
    		this.terminalDlg = new gMapControlEventCreate;
    	}
    	
    	var index = null;
    	var count = this.markInfors.length;
    	for(var i=0;i<count;i++){
    		if(this.markInfors[i].mark===mark){
    				index = i;
						break;
    		}
    	}
    	if (null != index)
    	{
			   var result = Ext.getCmp('readmetermap').DoAjax('hodMeterInfoTemp!getReadMeter.do?buildingId='+this.markInfors[index].comid);
				switch (result) {
					case -1 :
						Ext.MessageBox.alert("提示信息", "服务器无应答超时!");
						break;
					case 0 :
						Ext.MessageBox.alert("提示信息", "实时抄表失败，集中器不在线!");
						break;
					case 1 :
					{
						//Ext.MessageBox.alert("提示信息", "实时抄表成功!");
						this.terminalDlg.setTitle('抄表信息[' + this.markInfors[index].name + ']') ;
						var task = { 
						    run : function() {   
						        this.terminalDlg.gridStore.load({
									params : {
										buildingId: this.markInfors[index].comid
									}
								});
						    }.createDelegate(this),   
						    interval : 10000   
						} 
						Ext.TaskMgr.start(task);//启动定时器
						this.terminalDlg.show();
					}
					break;
				}
    	}

    	//this.terminalDlg.show();
    },
    
    checkLogin : function()
    {
    	alert('aa');
    },
    

    showterminal_menu : function(x,y) { // 楼栋右键菜单
    	
    	if (this.terminal_menu==null){
			var showTerminalMenu = new Ext.menu.Item({
						iconCls : 'showTerminalMenu',
						text : '实时抄读',
						handler : function(){
						this.terminal_menu.hide();
						this.showTerminalMenu(this.focusMark);
						}.createDelegate(this)
					});		
			var menuList;
			//if (!Ext.global.isEditTerminal)  //2011 01 14 add 添加权限 
			//	menuList = [showMControlMenu];
			//else
			menuList = [showTerminalMenu];
			this.terminal_menu = new Ext.menu.Menu({
						items : menuList
					});
    	};
    	this.terminal_menu.showAt([x,y]);
    },

    CheckPositionAtMenu:function(menu){  //判断当前鼠标是否还在菜单上
    		var Position = menu.getPosition();
				var Width = menu.getWidth();
				var Height = menu.getHeight();
				var x = this.mousePointX ;  // 鼠标绝对坐标X
				var y = this.mousePointY;  		
				if ((x>Position[0]&&x<Position[0]+Width)&&(y>Position[1]&&y<(Position[1]+Height)))
					return true;
				else
					return false;
    },
    DoAjax : function(url) {
		var returnResult = -1; // 服务器无应答
		Ext.Ajax.request({
					url : url,
					async : false,
					timeout : 5000,
					success : function(res) {
						var result = Ext.decode(res.responseText);
						if (true == result.success) {
							returnResult 	= 1;	// 成功
						} else {
							returnResult = 0;		// 失败
						}
					},
					failure : function(res) {
						returnResult = 0;
					}
				});
		return returnResult;	
    },

    DoDeleteMark:function(mark){
    	mark.remove();	
    	this.initmap();
    },

    ZoomStart:function(map){// 缩放事件
		map.addEventListener("zoomstart",function() {
			
		});
    },
    ZoomEnd:function(map){
    	map.addEventListener("zoomend",function() {
			
		});
    },
    MoveStart:function(map){// 移动事件
    	map.addEventListener("movestart",function() {
			if (Ext.getCmp('readmetermap').DrawOperation==-1)	
    			this.initmap();
		}.createDelegate(this));
    },
    MoveEnd:function(map){ 
    	map.addEventListener("moveend",function() {
    		// 需要对标注进行重新绘制
    		var Pane = Ext.getCmp('readmetermap');
    		if (Pane.DrawOperation==-1)	//只有在浏览时，才作整体刷新
    			this.DrawMarkTime = window.setTimeout(Pane.DrawAllMark.createDelegate(Pane), 800);
    	}.createDelegate(this));
    },
    MouseMove:function(map){
    	map.addEventListener("mousemove",function(latlng) {
  			this.curPoint = latlng.point;
  			/*if (this.map_menu!=null)	//通用菜单
  				if (this.map_menu.isVisible)
  					if (this.CheckPositionAtMenu(this.map_menu))
							this.map_menu.hide();*/
				this.CheckCloseMenu(this.map_menu);
				this.CheckCloseMenu(this.terminal_menu);
				this.CheckCloseMenu(this.draw_menu);	
				this.CheckCloseMenu(this.move_menu);
				this.CheckCloseMenu(this.drawgroup_menu);
				this.CheckCloseMenu(this.event_menu);
    	}.createDelegate(this));	
    },
    CheckCloseMenu:function(menu){ //判断右键菜单是否需要关闭
    	if (menu!=null)	
  				if (menu.isVisible)
  					if (this.CheckPositionAtMenu(menu))
							menu.hide();	
    },
    Click:function(map){
    	map.addEventListener("click", function(overlay,latlng,overlaylatlng){
    				
    	})	
    },
    MouseOver:function(map){ // 第一次进入时触发
    	map.addEventListener("mouseover",function(latlng){
    		// 如果拖动节点不为空的话，绘制临时标注
    		// if (this.drapnode==null) return;
    		
    	});		
    },
    MouseOut:function(map){
    	map.addEventListener("mouseout",function(latlng){	
    		this.curPoint = null;
    		this.CheckCloseMenu1(this.map_menu);
				this.CheckCloseMenu1(this.terminal_menu);
				this.CheckCloseMenu1(this.draw_menu);	
				this.CheckCloseMenu1(this.move_menu);
				this.CheckCloseMenu1(this.drawgroup_menu);
				this.CheckCloseMenu1(this.event_menu);
    	}.createDelegate(this));		
    },
    CheckCloseMenu1:function(menu){  //关闭右键菜单
    	if (menu!=null) menu.hide();	
    },
    DblClick:function(map){
    	map.addEventListener("dblclick",function(latlng){	
    		// 如果有临时标注的话，保存临时标注，并改成正式标注
    	})	
    },
    hidemenu:function(){
    	this.map_menu.hide;	
    },
    SingleRightClick:function(map){ //右键单击事件
    	map.addEventListener("rightclick",function(event){
			//var x = this.mousePointX - 5;
			//var y = this.mousePointY - 5;
			var x=event.offsetX+220;
			var y=event.offsetY+25;
			if(this.focusType==null)	
    			this.showmap_Menu(x,y);
    		else if(this.focusType==10)
    			this.showterminal_menu(x,y);//楼栋标注保存之后的右键菜单
    	}.createDelegate(this));	
    },
    DrawAllMark:function(){
    	this.initmap();
    	this.GetAreaPoint();
    	this.ShowAllPoint();
    },
    GetAreaPoint:function(){ // 获取当前地图中所有标注
    	//清空原有的标注
    	var mark;
    	for (var i = 0; i < this.markInfors.length; i++) {
			try {
				mark = this.markInfors[i].mark;
				if (mark != null)
					mark.remove();
			} catch (err) {
			}
		}
		this.markInfors.length = 0;
    	var map = this.getMap();
    	var E = 100;
    	//var mapsize= map.getSize();
    	// 在含有页面地图的 DOM 元素中计算指定像素坐标点的地理坐标。此方法有助于实现自定义控件与地图的交互。
    	/*var G = map.fromContainerPixelToLatLng(new GPoint(64, E/2));	
    	var D = map.fromContainerPixelToLatLng(new GPoint(mapsize.width-E/2, mapsize.height-E/2));
    	*/
    	var latlngbounds = map.getBounds();
			var D = latlngbounds.getSouthWest();
			var G = latlngbounds.getNorthEast();
			//var nelat = northeast.lat();
			//var swlng = southwest.lng();
				  
    	var x1=D.lng;
    	var x2=G.lng;
    	var y1=D.lat;
    	var y2=G.lat;
    	
    	
      var result = null;
      var markobj = new Object();
			var Pane = Ext.getCmp('readmetermap');
    	//获取楼栋坐标
    	Ext.Ajax.request({
					url : "hodBuilding!getmarkbylatlng.do?lng1=" + x1 + "&lng2="
							+ x2 + "&lat1=" + y1 + "&lat2=" + y2,
					async : false,
					timeout : 5000,
					success : function(res) {
						result = Ext.decode(res.responseText);
						if (result != null) {
							for (var i = 0; i < result.length; i++) {
								try {
									markobj = {
										name : result[i].name,
										type : 0,
										point : new BMap.Point(result[i].lng,
												result[i].lat),
										comid : result[i].comid,
										eventid : result[i].eventid,
										mark : null
									};
									Pane.markInfors.push(markobj);
								} catch (err) {
								}
							}
						}
					}
				});
    },
    ShowAllPoint : function() { // 绘制所有的点
    	var DrawMark;
    
		try {
			for (var i = 0; i < this.markInfors.length; i++) {
				if (this.markInfors[i].type == 0) { // 楼栋
					if (this.ShowTerminal == true) {
						if (this.markInfors[i].mark == null)
						{
							DrawMark = new BMap.Marker(this.markInfors[i].point, { //无事件
										title :this.markInfors[i].name,
										icon : this.terminal_icon
									});
									
						}else
							continue;
					} else {
						if (this.markInfors[i].mark != null) {
							this.markInfors[i].mark.remove();
							this.markInfors[i].mark = null;
							
						}
						continue;
					}
				} 
				this.markInfors[i].mark = DrawMark;
				this.AddMyListener(DrawMark);		
			}
			this.timeoutHandle = window.setInterval(this.drawMarker.createDelegate(this),90);
			this.DrawMarknum = 0;
		} catch (err) {
		}
	},
	AddMyListener:function(DrawMark){
		DrawMark.addEventListener("mouseover", function(latlng) { // 设置焦点
							var Pane = Ext.getCmp('readmetermap');
							Pane.focusMark = this;
							Pane.focuslatlng = latlng;
							if (this.getIcon()===Pane.terminal_icon) Pane.focusType = 10;
							if (this.getIcon()===Pane.terminalEvent_icon) Pane.focusType = 30;
		});
		DrawMark.addEventListener("mouseout", function() { // 取消焦点
					var Pane = Ext.getCmp('readmetermap');
					Pane.focusType = null;
		});	
	},
    drawMarker:function(){// 绘制指定的标注
    	while(true){
    		if (this.DrawMarknum>=this.markInfors.length){ //绘制完成 清除定时器
    			window.clearTimeout(this.timeoutHandle); 	
 					this.timeoutHandle = null;
    			break;
    		}
    		if (this.markInfors[this.DrawMarknum].mark!=null){
    			this.getMap().addOverlay(this.markInfors[this.DrawMarknum].mark);
    			this.DrawMarknum = this.DrawMarknum + 1;
    			break;
    		} else{
    			this.DrawMarknum = this.DrawMarknum + 1;	
    		}
    	}
    	/*var markTimeHandle=window.setTimeout(function()
    	{	
    			var Pane = Ext.getCmp('readmetermap');
    			Pane.getMap().addOverlay(mark);
    	}.createDelegate(this),  i* 30)
    	markTimeHandles[i]=markTimeHandle;*/	
    },
    initmap : function() // 初始化
	{
		if (this.DrawMarkTime != null) { // 通用定时器
			window.clearTimeout(this.DrawMarkTime);
			this.DrawMarkTime = null;
		}
		if (this.timeoutHandle != null) {// 绘制地临时标注定时器
			window.clearTimeout(this.timeoutHandle);
			this.timeoutHandle = null;
		}
		this.moveindex = -1;
		this.DrawOperation = -1;
		this.focusType = null;
		if(this.DrawMark!=null)	//清除临时绘制的标注
			this.DrawMark.remove();
		this.gmap.txtInfo_.innerHTML = "";		
		//this.gmap.closeInfoWindow() ;
	},
    onPantToCenter:function(){ //回到地图中心
    	this.getMap().panTo(new BMap.Point(this.setCenter.lng,this.setCenter.lat));
    	this.DrawAllMark();
    },
    onClearOperation:function(){//
    	if(this.DrawOperation==1 &&this.DrawOperation==0){ //单点绘制
    		if (this.DrawMark!=null) 
    			this.DrawMark.remove();
    	}
    	if (this.DrawOperation==3){ //移动操作
    		
			if (this.DrawMark!=null) 
    			this.cancelMoveMark(this.DrawMark,this.focusMark.getLatLng() ,this.moveindex);					
    	}
    	this.initmap();
    },
    onFindTerminal:function(params){// 查询并显示楼栋标注信息
    	var find = false;
    	this.ShowTerminal = true;
    	for (var i = 0; i < this.markInfors.length; i++) {
    		if (this.markInfors[i].type===0) 
    		if (this.markInfors[i].comid==params[0]) {
    			 find = true;
    			 break;
    		}
    	}
    	this.getMap().panTo(new BMap.Point(params[1],params[2]));
    	if(!find)
    		this.DrawAllMark();
    	else
    		this.ShowAllPoint();
    	var infowindow = new BMap.InfoWindow(params[3],{width:250,height:100,title:"楼栋信息："});
    	this.getMap().openInfoWindow(infowindow,new BMap.Point(params[1],params[2]));
    },
    onzoomInHere:function(){ // 局部放大
  		this.getMap().setZoom(19);
  		this.map_menu.hide();
    },
    onzoomOutHere:function(){ // 放置最小
			this.getMap().setZoom(4);
			this.map_menu.hide();
    },
    onzoomIn:function(){
    	this.getMap().zoomIn();
			this.map_menu.hide();
    },
    onzoomOut:function(){
    	this.getMap().zoomOut();
			this.map_menu.hide();
    },
    onMapReady : function(){
        this.addMarkers(this.markers);
        this.addMapControls();
        this.addOptions();  
    },
    onResize : function(w, h){

        if (typeof this.getMap() == 'object') {
            this.gmap.checkResize();
        }
        
        Ext.ux.GMapPanel.superclass.onResize.call(this, w, h);

    },
    setSize : function(width, height, animate){
        
        if (typeof this.getMap() == 'object') {
            this.gmap.checkResize();
        }
        
        Ext.ux.GMapPanel.superclass.setSize.call(this, width, height, animate);
        
    },
    getMap : function(){
        
        return this.gmap;
        
    },
    getCenter : function(){
        
        return this.getMap().getCenter();
        
    },
    getCenterLatLng : function(){
        
        var ll = this.getCenter();
        return {lat: ll.lat(), lng: ll.lng()};
        
    },
    addMarkers : function(markers) {
        
        if (Ext.isArray(markers)){
            for (var i = 0; i < markers.length; i++) {
                var mkr_point = new BMap.Point(markers[i].lng,markers[i].lat);
                this.addMarker(mkr_point,markers[i].marker,false,markers[i].setCenter, markers[i].listeners);
            }
        }
        
    },
    addMarker : function(point, marker, clear, center, listeners){
        
        //Ext.applyIf(marker,G_DEFAULT_ICON);

        if (clear === true){
            this.getMap().clearOverlays();
        }
        if (center === true) {
            this.getMap().setCenter(point, this.zoomLevel);
        }
				
        var mark = new BMap.Marker(point,marker);
        if (typeof listeners === 'object'){
            for (evt in listeners) {
                GEvent.bind(mark, evt, this, listeners[evt]);
            }
        }
        this.getMap().addOverlay(mark);

    },
    addMapControls : function(){
        
        if (this.gmapType === 'map') {
        	
            if (Ext.isArray(this.mapControls)) {
            		//this.getMap().setUIToDefault();
                for(i=0;i<this.mapControls.length;i++){
                    this.addMapControl(this.mapControls[i]);
                }
            }else if(typeof this.mapControls === 'string'){
                this.addMapControl(this.mapControls);
            }else if(typeof this.mapControls === 'object'){
                this.getMap().addControl(this.mapControls);
            }; 
            	
        }
        
    },
    addMapControl : function(mc){
        
        var mcf = window[mc];
        if (typeof mcf === 'function') {
            this.getMap().addControl(new mcf());
        }    
        
    },
    addOptions : function(){
        if (Ext.isArray(this.mapConfOpts)) {
            var mc;
            for(i=0;i<this.mapConfOpts.length;i++){
                this.addOption(this.mapConfOpts[i]);
            }
        }else if(typeof this.mapConfOpts === 'string'){
            this.addOption(this.mapConfOpts);
        }        
        
    },
    addOption : function(mc){
        
        var mcf = this.getMap()[mc];
        if (typeof mcf === 'function') {
            this.getMap()[mc]();
        }    
        
    },
    geoCodeLookup : function(addr) {	// 地址解析请求处理
        
        this.geocoder = new GClientGeocoder();
        this.geocoder.getLocations(addr, this.addAddressToMap.createDelegate(this));
        
    },
    addAddressToMap : function(response) {
        
        if (!response || response.Status.code != 200) {
            Ext.MessageBox.alert('Error', 'Code '+response.Status.code+' Error Returned');
        }else{
            place = response.Placemark[0];
            addressinfo = place.AddressDetails;
            accuracy = addressinfo.Accuracy;
            if (accuracy === 0) {
                Ext.MessageBox.alert('Unable to Locate Address', 'Unable to Locate the Address you provided');
            }else{
                if (accuracy < 7) {
                    Ext.MessageBox.alert('Address Accuracy', 'The address provided has a low accuracy.<br><br>Level '+accuracy+' Accuracy (8 = Exact Match, 1 = Vague Match)');
                }else{
                    point = new BMap.Point(place.Point.coordinates[1], place.Point.coordinates[0]);
                    if (typeof this.setCenter.marker === 'object' && typeof point === 'object'){
                        this.addMarker(point,this.setCenter.marker,this.setCenter.marker.clear,true, this.setCenter.listeners);
                    }
                }
            }
        }
        
    }
 
});

Ext.reg('gmappanel', Ext.ux.GMapPanel); 