<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>实时抄读</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="http://api.map.baidu.com/api?&v=1.3"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
		<!-- 后加 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
		<script src="${pageContext.request.contextPath}/js/Ext.ld.checkAreaTree.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<!-- <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&language=zh-CN"></script> -->  
		<script type="text/javascript" src="http://api.map.baidu.com/api?&v=1.3"></script>       
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/ReadMeterMapPanel.js"> </script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/GMapInfor.js"> </script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/ext-basex.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"> </script>
		<script type="text/javascript">
			Ext.onReady(function() { 
				var topLoader = new Ext.tree.TreeLoader({
							dataUrl: "hodConcentrator!doTree.do?checkBox=false"
				});
				var topRoot = new Ext.tree.AsyncTreeNode({
						text: '区域管理',
						id: '-1' ,
						expanded : true,
						type:'0'
				});
				var topTreePanel = new Ext.ld.checkAreaTree({
				    	id: 'ReadMeterMapTreePanle',
				        renderTo:"readMeterTreePanel",
				        width:230,
				        height:document.body.clientHeight,
				     	loader: topLoader,
						root: topRoot,
						title:'地址信息',
						tools : [		//在标题栏上，显示图标，告诉用户图标代表什么
			   		  		 {
			   		  	 		id: 'westtools-collapseAll',	//对应的样式.x-tool-westtools-collapseAll
			   		  	 		qtip : '闭合所有节点',
			   		  	 		handler: function()
			   		  	 		{
			   		  	 			topTreePanel.collapseAll();
			   		  	 		}.createDelegate(this)
			   		  		 },{
			   		  	 		id: 'westtools-expandAll',	//对应的样式.x-tool-westtools-expandAll
			   		  	 		qtip : '展开所有节点',
			   		  	 		handler: function()
			   		  	 		{
			   		  	 			topTreePanel.expandAll();
			   		  	 		}.createDelegate(this)
			   		  		 },{
			   		  	 		id: 'westtools-terminal',  //对应的样式..x-tool-westtools-refresh
			   		  	 		qtip : '刷新',
			   		  	 		handler: function()
   		  	 					{
   		  	 						topTreePanel.getRootNode().reload();
									//topTreePanel.expandAll();
			   		  		 	}
			   		  		 }
			   		  	 ]
				 });
				//topTreePanel.expandAll();
			 	topLoader.on('beforeload',onToploaderBeforeLoad, topLoader);
			 	topTreePanel.on('dblclick', onToptreeDbclick, topTreePanel);
			 	
			 	//父节点的复选框选中，子节点也选中
			 	topTreePanel.on('checkchange', function(node, state) {
					node.ui.checkbox.checked = state; 
					node.attributes.checked = state;
					if(node.attributes.leaf)
					{
						var pNode = node.parentNode; 
						if (state || topTreePanel.getChecked(id, pNode) == "") { 
							pNode.ui.checkbox.checked=state;
							pNode.attributes.checked = state; 
						}
					} 
					else
					{
						node.cascade(function(node){ 
							node.ui.checkbox.checked = state; 
							node.attributes.checked = state; 
							return true; 
						}); 
					}
				});
			});
			//屏蔽双击展开或收缩节点事件
			Ext.override(Ext.tree.TreeNodeUI, {
				onDblClick : function(e) {
					e.preventDefault();
					if (this.disabled) {
						return;
					}
					if (this.checkbox) {
						this.toggleCheck();
					}
					if (!this.animating && this.node.hasChildNodes()) {
						var isExpand = this.node.ownerTree.doubleClickExpand;
						if (isExpand) {
							this.node.toggle();
						};
					}
					this.fireEvent("dblclick", this.node, e);
				}
			});
			function onToploaderBeforeLoad(loader, node)
			{
				loader.baseParams.type = node.attributes.type;	
				loader.baseParams.id = node.attributes.qtip;
				loader.baseParams.code = node.attributes.code;
			}
			//双击事件
			function onToptreeDbclick(node,event)
			{
				if(8 != node.attributes.type) return;
				if(!node.isLeaf) return;
				//根据集中器行政编码查询集中器所属楼栋的经纬度
				Ext.Ajax.request({
					url : "${pageContext.request.contextPath}/hodBuilding!getLnglat.do",
					params:{  
						conAddress:node.attributes.code
		        	},  
					async : false,
					method : 'POST',
					timeout : 180000,
					success : function(res) {
						var result = Ext.decode(res.responseText);
						if(result.success==true)
						{
							var params=[result.id,result.lng,result.lat,result.name];
							Ext.getCmp('readmetermap').onFindTerminal(params);//集中器在地图上的定位查询
						}
					},
					failure : function(res) {
						Ext.MessageBox.alert('提示','请求超时!');
					}
			    });
			}
		</script>
		<style type="text/css">  
	        html{height:100%}  
	        body{height:100%;margin:0px;padding:0px}  
	        #container{height:100%;width:100%;}  
		</style>
 </head>
  
  <body onload="load('container',230)">
  <div id="readMeterTreePanel" style="width: 230px;float: left;"></div>
  <div id="container" style="float: left;"></div>
  </body>
  	<script type="text/javascript">
  		//iconCls : 'setmapcenter24',
  		var lng=114.066937;
  		var lat=22.547656;
  		Ext.Ajax.request({
			url : "${pageContext.request.contextPath}/hodBuilding!getMapCenter.do",
			async : false,
			method : 'POST',
			timeout : 60000,
			success : function(res) {
				var result = Ext.decode(res.responseText);
				Ext.MessageBox.hide();
				if (true == result.success) {
					lng=result.lng;
					lat=result.lat;
				}
				else
				{
					Ext.MessageBox.alert('提示',result.msg);
				}
			},
			failure : function(res) {
				Ext.MessageBox.hide();
				Ext.MessageBox.alert('提示','请求超时!');
			}
	    });
  		
    	var p = new Ext.Panel({
        renderTo: 'container',
        height:document.body.clientHeight,
        width:document.body.clientWidth-230,
        items : {
				id : 'readmetermap',
				xtype : 'gmappanel',
				zoomLevel : 14,
				gmapType : 'map',
				setCenter : { //必须有中心
					lng : lng,
					lat : lat
				}
			},
        tbar : new Ext.Toolbar({
						items : [{
									xtype : 'button',
									text : '回到地图中心',
									scale : 'medium',
									iconAlign : 'left',
									handler : function() {
										this.mapObj.onPantToCenter();
									}.createDelegate(this)
								}, '-', {
									xtype : 'button',
									text : '返回',
									scale : 'medium',
									iconAlign : 'left',
									handler : function() {
										location.href='${pageContext.request.contextPath}/show/readmeterlist.jsp';
									}.createDelegate(this)
								}, '-', {
									xtype : 'button',
									text : '首页',
									scale : 'medium',
									iconAlign : 'left',
									handler : function() {
										location.href='${pageContext.request.contextPath}/main.jsp';
									}.createDelegate(this)
								}]
					})
    }); 
    this.mapObj = Ext.getCmp('readmetermap');
    </script>
</html>
