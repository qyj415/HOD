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
    
    <title>管网损耗</title>
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
			var meter_style;
			Ext.onReady(function() { 
				var topLoader = new Ext.tree.TreeLoader({
							dataUrl: "hodMeter!doTree.do"
				});
				var topRoot = new Ext.tree.AsyncTreeNode({
						text: '管网损耗',
						id: '-1' ,
						expanded : true,
						type:'0'
				});
				var topTreePanel = new Ext.ld.JsonMeterTree({
				    	id: 'MeterTreepanel',
				        renderTo:"meterTree",
				        width:230,
				        rootVisible:false,
				        title:'表计信息',
				        height:document.body.clientHeight-115,
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
				topTreePanel.on('click', onToptreeDbclick, topTreePanel);
			});
			
			//单击事件
			function onToptreeDbclick(node,event)
			{
				code=node.attributes.meter_name;
				meter_style=node.attributes.meter_style;
				checkTime();
			}
			
			function onToploaderBeforeLoad(loader, node)
			{
				loader.baseParams.type = node.attributes.type;	
				loader.baseParams.id = node.attributes.qtip;
				loader.baseParams.code = node.attributes.meter_name;
				loader.baseParams.meter_style = node.attributes.meter_style;
			}
			
			function showList()
			{
				if(code == null)
				{
					Ext.MessageBox.alert('提示',"请选择要进行分析的楼栋表或换能站!");
					return;
				}
				if(meter_style == 1)
				{
					Ext.MessageBox.alert('提示',"请选择楼栋表或换能站!");
					return;
				}
				$("#datatable tr").eq(0).nextAll().remove();
				Ext.MessageBox.wait('正在请求，请稍后......', '请稍后');
				Ext.Ajax.request({
				url : "${pageContext.request.contextPath}/jFreeChart!toCategory.do",
				params:{  
					utils:$("#utils").val(),
	            	startTime:$("#startTime").val(),
	            	endTime:$("#endTime").val(),
	            	meterName:code
	        	},  
				async : false,
				method : 'POST',
				timeout : 180000,
				success : function(res) {
					var result = Ext.decode(res.responseText);
					Ext.MessageBox.hide();
					if (true == result.success) {
						var url=result.data;
						var img = "<img src=<%= jreeChartPath %>" + url +" width=700 height=400 border=0> "; 
						document.getElementById('toCategory').innerHTML= img; 
					}
					else
					{
						Ext.MessageBox.alert('提示','请求失败!');
					}
				},
				failure : function(res) {
					Ext.MessageBox.hide();
					Ext.MessageBox.alert('提示','请求超时!');
				}
		    });
			}
		</script>
  </head>
  
<body onload="load('toCategory',250),loads('toCategory',120)">
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
														<B>当前位置</B>：[<font color='red'>管网损耗</font>]
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
										<%@include file="/inc/lossSearch.jsp" %>
									</form>
									<div id="meterTree" style="width: 230px;float: left;border-right: 1px solid #2EB4B9;"></div>
									<div id="toCategory" align="center" style="float: left;">
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
