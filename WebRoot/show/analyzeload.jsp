<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String jreeChartPath = path + "/servlet/DisplayChart?filename=";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <base href="<%=basePath%>">
    <title>负荷曲线</title>
    <script src="${pageContext.request.contextPath}/common/js/tab.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/tab/show.css">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ext3/resources/css/ext-all.css" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext3/ext-lang-zh_CN.js"></script>
	<script src="${pageContext.request.contextPath}/js/Ext.ld.JsonMeterTree.js"></script>
	<script src="${pageContext.request.contextPath}/js/util.js"></script>
	<script type="text/javascript">
			var code;
			Ext.onReady(function() { 
				var topLoader = new Ext.tree.TreeLoader({
					dataUrl: "hodMeter!doTree.do"
				});
				var topRoot = new Ext.tree.AsyncTreeNode({
						text: '负荷曲线',
						id: '-1' ,
						expanded : true,
						type:'0'
				});
				var topTreePanel = new Ext.ld.JsonMeterTree({
				    	id: 'fuTreepanel',
				        renderTo:"fuTree",
				        width:230,
				        title:'上级表信息',
				        height:210,
				        rootVisible:false,
				     	loader: topLoader,
						root: topRoot,
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
									topTreePanel.expandAll();
			   		  		 	}
			   		  		 }
			   		  	 ]
				 });
				topTreePanel.expandAll();
				//topTreePanel.on('dblclick', onToptreeDbclick, topTreePanel);
				topTreePanel.on('click', onToptreeClick, topTreePanel);
			
				var root=new Ext.tree.AsyncTreeNode({
							text: '城市建筑智慧能源管理系统',
							id: '0',
							leaf:true
						});
				var meterTreePanel = new Ext.ld.JsonMeterTree({
				    	id: 'meterTreepanel',
				        renderTo:"meterTree",
				        width:230,
				        title:'户用表信息',
				        rootVisible:true,
				        height:document.body.clientHeight-325,
						root: root
				 });
				 root.getUI().getIconEl().src = 'images/area/logo.gif'; 
				 meterTreePanel.on('click', onToptreeClick, meterTreePanel);
			});
			
			//单击事件
			function onToptreeClick(node,event)
			{
				var meter_style=node.attributes.meter_style;
				var bottomTree=Ext.getCmp('meterTreepanel');
				if(meter_style==2)
				{
					//展开下面的树
					var meterParent=node.attributes.meter_position;
					if(bottomTree.loader)
					{
						delete bottomTree.loader;
						bottomTree.loader = null;
					}
					
					bottomTree.loader  =  new Ext.tree.TreeLoader({
						dataUrl: "hodMeter!getMeters.do?meterParent="+meterParent,
						listeners: {'load':{fn: onBottomloaderLoad.createDelegate(this)}}
					});
					
					if(bottomTree.root)
					{
						delete bottomTree.root;
					}
				 	var root = new Ext.tree.AsyncTreeNode({
						text: node.text,
						id: '0'
					});
					if(bottomTree)
					{
						bottomTree.setRootNode(root);
						bottomTree.render();
					}
					bottomTree.expandAll();
				}
				else if(meter_style!=1)
				{
					if(bottomTree.loader)
					{
						delete bottomTree.loader;
						bottomTree.loader = null;
					}
					if(bottomTree.root)
					{
						delete bottomTree.root;
					}
				 	var root = new Ext.tree.AsyncTreeNode({
						text: '城市建筑智慧能源管理系统',
						id: '0',
						leaf:true
					});
					if(bottomTree)
					{
						bottomTree.setRootNode(root);
						bottomTree.render();
					}
					root.getUI().getIconEl().src = 'images/area/logo.gif'; 
				}
				code=node.attributes.meter_name;
				checkTime();
			}
			
			function onBottomloaderLoad(tree,  node,  response)
			{
				node.getUI().getIconEl().src = 'images/building.gif';
				var childNodes = node.childNodes;
				for(var i = 0 ; i < childNodes.length; i++)
				{
				  	var child = childNodes[i];
				  	//户用表
				  	if(1 == child.attributes.meter_style)
				  	{
					  	child.getUI().getIconEl().src = 'images/meter.gif';  
				  	}	
				  	//楼栋表
				  	if(2 == child.attributes.type)
				  	{
					  	child.getUI().getIconEl().src = 'images/building.gif';  
				  	}	
				}
			}
			
			//双击事件
			function onToptreeDbclick(node,event)
			{
				var meter_position=node.attributes.meter_name;
				alert(meter_position);
			}
			
			function onToploaderBeforeLoad(loader, node)
			{
				loader.baseParams.type = node.attributes.type;	
				loader.baseParams.id = node.attributes.qtip;
				loader.baseParams.meter_name = node.attributes.meter_name;
			}
			
			function showList()
			{
				if(code == null){
					Ext.MessageBox.alert('提示','请选择要进行分析的表计!');
					return;
				}
				$("#datatable tr").eq(0).nextAll().remove();
				Ext.MessageBox.wait('正在请求，请稍后......', '请稍后');
				//Ext.getBody().mask('loading', 'x-mask-loading');
				Ext.Ajax.request({
					url : "${pageContext.request.contextPath}/jFreeChart!toChart.do",
					params:{  
						utils:$("#utils").val(),
		            	startTime:$("#startTime").val(),
		            	endTime:$("#endTime").val(),
		            	meterPosition:code
		        	},  
					async : false,
					method : 'POST',
					timeout : 180000,
					success : function(res) {
						var result = Ext.decode(res.responseText);
						Ext.MessageBox.hide();
						if (true == result.success) {
							
							var url=result.data;
							var mapStr = result.mapStr;
							document.getElementById('analyzeloadMaps').innerHTML= mapStr; 
							var img = "<img src=<%= jreeChartPath %>" + url +" width=700 height=400 border=0 usemap='#analyzeloadMaps' >"; 
							document.getElementById('analyzeloadChart').innerHTML= img; 
						
						}
						else
						{
							Ext.MessageBox.alert('提示','请求失败!');
						}
						//Ext.getBody().unmask();
					},
					failure : function(res) {
						Ext.MessageBox.hide();
						Ext.MessageBox.alert('提示','请求超时!');
					}
			    });
			}
	</script>
  </head>
  
<body onload="load('toChartDiv',350),loads('toChartDiv',220)">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="30" class="tab_05">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="12" height="30" class="tab_03"></td>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="46%" valign="middle">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td width="5%" class="tb"></td>
													<td width="95%" class="STYLE2">
														<B>当前位置</B>：[<font color='red'>负荷曲线</font>]
													</td>
												</tr>
											</table>
										</td>
										<td width="54%">
											<table border="0" align="right" cellpadding="0"
												cellspacing="0">
												<tr>
													<td width="60"></td>
													<td width="90">
														<table width="99%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td class="STYLE2">
																	<a href="${pageContext.request.contextPath}/main.jsp"><div class="homebtn"></div></a>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
							<td width="16" class="tab_07"></td>
						</tr>
					</table>
				</td>
			</tr>
					
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" style="background-color: white;">
						<tr>
							<td width="8" class="tab_12">
							</td>
							<td>
									<form action="">
										<%@include file="/inc/analyzeLoadSearch.jsp" %>
									</form>
									<div id="fuTree" style="width: 230px;float: left;border-right: 1px solid #2EB4B9;"></div>
									<div id="meterTree" style="width: 230px;border-right: 1px solid #2EB4B9;margin-top: 210px;"></div>
									<div id="toChartDiv" name="toChartDiv" align="center" style="margin-top: -400px;margin-left: 235px;">
										<div id="analyzeloadChart" align="center"></div>
										<map id="analyzeloadMaps" name="analyzeloadMaps"></map>
									</div>

							</td>
							<td width="8" class="tab_15">
								&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="35" class="tab_19">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="12" height="35" class="tab_18">
							</td>
							<td>&nbsp;</td>
							<td width="16" class="tab_20">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
